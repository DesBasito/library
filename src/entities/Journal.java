package entities;

import java.util.Date;

public class Journal {
    private int id;
    private int book;
    private int borrower;
    private Date borrowedDate;
    private Date returnedDate;
    public Journal(int book, int borrower, Date borrowedDate, Date returnedDate,int id) {
        this.book = book;
        this.borrower = borrower;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
        this.id = id;
    }

    public int getBook() {
       return book;
    }


    public int getBorrower() {
        return borrower;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public int getId() {
        return id;
    }
}

