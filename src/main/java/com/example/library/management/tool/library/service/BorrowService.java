package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.BorrowDao;
import com.example.library.management.tool.library.dto.borrow.Borrow;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.library.management.tool.library.util.DateUtil.*;

@Service
public class BorrowService {

    @Autowired
    private BorrowDao borrowDao;

    public ResponseEntity<?> getAllBorrows() {
        return borrowDao.getAllBorrows();
    }

    public ApiResponse addBorrow(Borrow borrow) {
        if (borrow.getBookId() == null ||
                borrow.getUserId() == null ||
                ValidatorUtil.isEmptyOrNull(borrow.getIssueDate())) {
            return new ApiResponse(false, "Book Id, User Id and Issue date should not be null or empty.");
        }

        if (!isValidDate(borrow.getIssueDate())) {
            return new ApiResponse(false, "Issue date should not be in valid format (YYYY-MM-DD).");
        }

        Date issueDate = convertStringToDate(borrow.getIssueDate());
        Date currentDate = new Date();

        if (!issueDate.before(currentDate)) {
            return new ApiResponse(false, "Issue date should not be in the past.");
        }

        if (issueDate.after(addDays(currentDate, 2))) {
            return new ApiResponse(false, "Issue date should not be more than 2 days in the future.");
        }

        boolean borrowAllowed = borrowDao.verifyUserAndBookId(borrow);
        if (!borrowAllowed) {
            return new ApiResponse(false, "Invalid User or Book Id.");
        }

        return borrowDao.addBorrow(borrow);
    }

    public ApiResponse updateBorrow(Borrow borrow) {
        if (borrow.getBorrowId() == null ||
                borrow.getUserId() == null ||
                borrow.getBookId() == null ||
                ValidatorUtil.isEmptyOrNull(borrow.getReturnDate())) {
            return new ApiResponse(false,
                    "Borrow Id, User Id, Book Id, Issue Date and Return Date shouldn't be empty or null.");
        }

        Date issueDate = new Date();
        if (ValidatorUtil.isEmptyOrNull(borrow.getIssueDate())) {
            issueDate = borrowDao.getIssueDate(borrow);
            borrow.setIssueDate(new SimpleDateFormat("yyyy-MM-dd").format(issueDate));

            if (!isValidDate(borrow.getReturnDate()) || !isValidDate(borrow.getIssueDate())) {
                return new ApiResponse(false,
                        "Issue date and return date should not be in valid format (YYYY-MM-DD).");
            }
        }

        Date returnDate = convertStringToDate(borrow.getReturnDate());

        if (issueDate.after(returnDate)) {
            return new ApiResponse(false, "Issue date should not be after return date.");
        }

        borrow.setFine(calculateFine(borrow));
        return borrowDao.updateBorrow(borrow);
    }
}
