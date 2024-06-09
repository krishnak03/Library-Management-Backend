package com.example.library.management.tool.library.dao;

import com.example.library.management.tool.library.dto.borrow.Borrow;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.example.library.management.tool.library.util.DateUtil.convertStringToDate;

@Repository
public class BorrowDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static final class BorrowRowMapper implements RowMapper<Borrow> {
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

    public ResponseEntity<?> getAllBorrows() {
        String getAllBorrowsQuery = "SELECT * FROM borrow;";
        try {
            List<Borrow> borrows = jdbcTemplate.query(getAllBorrowsQuery, new BorrowRowMapper());
            if (borrows.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse(false, "No borrows found."), HttpStatus.OK);
            }
            return new ResponseEntity<>(borrows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Exception occurred while retrieving borrows list: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ApiResponse addBorrow(Borrow borrow) {
        String addBorrowQuery = "INSERT INTO borrow (user_id, book_id, issue_date) VALUES (?, ?, ?::date);";
        String updateBookQuery = """
            UPDATE book
            SET book_popularity = book_popularity + 1,
                book_available = book_available - 1
            WHERE book_id = ? AND book_available > 0;
        """;
        try {
            // Start transaction
            jdbcTemplate.execute("BEGIN");

            // Update book availability and popularity
            int affectedBookData = jdbcTemplate.update(updateBookQuery, borrow.getBookId());
            if (affectedBookData == 0) {
                // Rollback transaction if the book is not available
                jdbcTemplate.execute("ROLLBACK");
                return new ApiResponse(false, "Book is not available for borrowing.");
            }

            // Insert borrow record
            int affectedBorrowData = jdbcTemplate.update(addBorrowQuery,
                    borrow.getUserId(),
                    borrow.getBookId(),
                    borrow.getIssueDate());
            if (affectedBorrowData > 0) {
                // Commit transaction
                jdbcTemplate.execute("COMMIT");
                return new ApiResponse(true, "Borrow data saved successfully.");
            } else {
                // Rollback transaction if insert fails
                jdbcTemplate.execute("ROLLBACK");
                return new ApiResponse(false, "Could not process borrow request.");
            }
        } catch (Exception e) {
            // Rollback transaction in case of exception
            jdbcTemplate.execute("ROLLBACK");
            return new ApiResponse(false, "Exception occurred while processing borrow request: " + e.getMessage());
        }
    }

    public ApiResponse updateBorrow(Borrow borrow) {
        String updateBorrowQuery = """
            UPDATE borrow
            SET user_id = COALESCE(?, user_id),
                book_id = COALESCE(?, book_id),
                issue_date = COALESCE(?::date, issue_date),
                return_date = COALESCE(?::date, return_date),
                fine = COALESCE(?, fine)
            WHERE borrow_id = ?;
        """;
        String updateBookQuery = """
            UPDATE book
            SET book_available = book_available + 1
            WHERE book_id = ?;
        """;
        try {
            // Start transaction
            jdbcTemplate.execute("BEGIN");

            // Update borrow record
            int affectedBorrowData = jdbcTemplate.update(updateBorrowQuery,
                    borrow.getUserId(),
                    borrow.getBookId(),
                    borrow.getIssueDate(),
                    borrow.getReturnDate(),
                    borrow.getFine(),
                    borrow.getBorrowId());
            if (affectedBorrowData > 0) {
                // Update book availability
                jdbcTemplate.update(updateBookQuery, borrow.getBookId());

                // Commit transaction
                jdbcTemplate.execute("COMMIT");
                return new ApiResponse(true, "Borrow details updated successfully.");
            } else {
                // Rollback transaction if update fails
                jdbcTemplate.execute("ROLLBACK");
                return new ApiResponse(false, "Error occurred while updating (previously borrowed) details.");
            }
        } catch (Exception e) {
            // Rollback transaction in case of exception
            jdbcTemplate.execute("ROLLBACK");
            return new ApiResponse(false, "Exception occurred while updating (previously borrowed) details: " + e.getMessage());
        }
    }

    public boolean verifyUserAndBookId(Borrow borrow) {
        String verifyUserAndBookIdQuery = """
            SELECT EXISTS (
                SELECT 1
                FROM public."user" u, public."book" b
                WHERE u.user_id = ? AND b.book_id = ?
            ) AS exists;
        """;

        Boolean borrowAllowed = jdbcTemplate.queryForObject(
                verifyUserAndBookIdQuery,
                new Object[]{borrow.getUserId(), borrow.getBookId()},
                (rs, rowNum) -> rs.getBoolean("exists")
        );

        return borrowAllowed != null && borrowAllowed;
    }

    public Date getIssueDate(Borrow borrow) {
        String getIssueDateQuery = """
            SELECT issue_date
            FROM public.borrow
            WHERE borrow_id = ?
        """;

        String issueDateString = jdbcTemplate.queryForObject(
                getIssueDateQuery,
                new Object[]{borrow.getBorrowId()},
                (rs, rowNum) -> rs.getString("issue_date")
        );

        return convertStringToDate(issueDateString);
    }
}
