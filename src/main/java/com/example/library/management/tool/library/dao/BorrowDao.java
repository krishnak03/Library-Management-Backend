package com.example.library.management.tool.library.dao;

import com.example.library.management.tool.library.dto.admin.Admin;
import com.example.library.management.tool.library.dto.borrow.Borrow;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.exceptions.CustomLibraryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class BorrowDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private  static final class BorrowRowMapper implements RowMapper<Borrow> {
        @Override
        public Borrow mapRow(ResultSet rs, int rowNo) throws SQLException {
            Borrow borrow = new Borrow();
            borrow.setBorrowId(rs.getInt("borrow_id"));
            borrow.setBookId(rs.getInt("book_id"));
            borrow.setUserId(rs.getInt("user_id"));
            borrow.setIssueDate(rs.getString("issue_date"));
            borrow.setReturnDate(rs.getString("return_date"));
            borrow.setFine(rs.getInt("fine"));
            return borrow;
        }
    }

    public List<Borrow> getAllBorrows() {
        String getAllBorrowsQuery = "SELECT * FROM borrow;";
        try {
            List<Borrow> borrows = jdbcTemplate.query(getAllBorrowsQuery, new BorrowRowMapper());
            if (borrows.isEmpty()) {
                throw new CustomLibraryException("No borrows found.", 500);
            }
            return borrows;
        } catch (Exception e) {
            System.out.println("Exception occurred while retrieving borrowers list" + e.getMessage());
            return Collections.emptyList();
        }
    }

    public ApiResponse addBorrow(Borrow borrow) {
        String addBorrowQuery = "INSERT INTO borrow (user_id, book_id, issue_date) VALUES (?, ?, ?);";
        try {
            int affectedBorrowData = jdbcTemplate.update(addBorrowQuery, borrow.getUserId(), borrow.getBookId(), borrow.getIssueDate());
            if (affectedBorrowData > 0) {
                return new ApiResponse(true, "Borrowed book successfully.");
            } else {
                return new ApiResponse(false, "Failed to borrow book.");
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Exception occurred while borrowing book: " + e.getMessage());
        }
    }


    public ApiResponse updateBorrow(Borrow borrow) {
        String updateBorrowQuery = """
                UPDATE "borrow"
                SET user_id = COALESCE(?, user_id),
                    book_id = COALESCE(?, book_id),
                    issue_date = COALESCE(?::date, issue_date),
                    return_date = COALESCE(?::date, return_date),
                    fine = COALESCE(?, fine)
                WHERE borrow_id = ? AND book_id = ? AND user_id = ?;
                """;
        try {
            int affectedAdminData = jdbcTemplate.update(updateBorrowQuery,
                    borrow.getUserId(),
                    borrow.getBookId(),
                    borrow.getIssueDate(),
                    borrow.getReturnDate(),
                    borrow.getFine());
            if(affectedAdminData > 0) {
                return new ApiResponse(true, "Borrow details updated successfully.");
            }else{
                return new ApiResponse(false, "Error occurred while updating (previously borrowed) details.");
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Exception occurred while updating (previously borrowed) details. " + e.getMessage());
        }
    }
}
