package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.BorrowDao;
import com.example.library.management.tool.library.dto.borrow.Borrow;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.exceptions.CustomLibraryException;
import com.example.library.management.tool.library.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BorrowService {

    @Autowired
    private BorrowDao borrowDao;

    public List<Borrow> getAllBorrows() {
        return borrowDao.getAllBorrows();
    }

    public ApiResponse addBorrow(Borrow borrow){
        if (borrow.getUserId() == 0 || borrow.getBookId() == 0 ||
                Validator.isEmptyOrNull(borrow.getIssueDate())) {
            return new ApiResponse(false, "UserId, BookId and Issue Date shouldn't be empty or null.");
        }
        return borrowDao.addBorrow(borrow);
    }

    public ApiResponse updateBorrow(Borrow borrow) {
        if (borrow.getUserId() == 0 || borrow.getBookId() == 0 ||
                Validator.isEmptyOrNull(borrow.getIssueDate())) {
            return new ApiResponse(false, "UserId, BookId and Issue Date shouldn't be empty or null.");
        }
        int fine = calculateFine(borrow);
        if(fine > 0){
            //to do - pending
            System.out.println(fine);
        }
        return borrowDao.updateBorrow(borrow);
    }

    // Method to convert string date in yyyy-mm-dd format to Date object
    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // Method to calculate difference between two dates in days
    public static long calculateDateDifference(Borrow borrow) {
        Date issueDate = convertStringToDate(borrow.getIssueDate());
        Date returnDate = convertStringToDate(borrow.getReturnDate());

        // Check if both dates are successfully parsed
        if (issueDate != null && returnDate != null) {
            long diffInMillies = Math.abs(returnDate.getTime() - issueDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return diff;
        } else {
            System.out.println("Unable to Calculate number of days book was borrowed");
            return -1;
        }
    }

    public int calculateFine(Borrow borrow) {
        long daysBorrowed  = calculateDateDifference(borrow);

        if (daysBorrowed <= 7) {
            return 0;
        } else {
            long overdueDays = daysBorrowed - 7;
            return (int) (5 * Math.ceil(overdueDays / 7.0));
        }
    }

}
