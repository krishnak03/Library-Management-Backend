package com.example.library.management.tool.library.util;

import com.example.library.management.tool.library.dto.borrow.Borrow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    // Method to convert string date in yyyy-MM-dd format to Date object
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
            System.out.println("Unable to calculate the number of days book was borrowed");
            return -1;
        }
    }

    // Method to add days to a date
    public static Date addDays(Date date, int days) {
        long milliseconds = date.getTime() + TimeUnit.DAYS.toMillis(days);
        return new Date(milliseconds);
    }

    // Method to calculate fine
    public static int calculateFine(Borrow borrow) {
        long daysBorrowed = calculateDateDifference(borrow);

        if (daysBorrowed <= 7) {
            return 0;
        } else {
            long overdueDays = daysBorrowed - 7;
            return (int) (100 * Math.ceil(overdueDays / 7.0));
        }
    }

    public static boolean isValidDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return false;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);

        try {
            formatter.parse(dateString);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }
}
