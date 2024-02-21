package entities;

import java.util.Date;

public class Journal {
    private String book;
    private String author;
    private String borrower;
    private Date borrowed_date;
    private Date returned_date;
    private int id;

    public Journal(String book, String author, String borrower, Date borrowed_date, Date returned_date,int id) {
        this.book = book;
        this.author = author;
        this.borrower = borrower;
        this.borrowed_date = borrowed_date;
        this.returned_date = returned_date;
        this.id = id;
    }

    public String getBook() {
        return book;
    }

    public String getAuthor() {
        return author;
    }

    public String getBorrower() {
        return borrower;
    }

    public Date getBorrowed_date() {
        return borrowed_date;
    }

    public Date getReturned_date() {
        return returned_date;
    }

    public int getId() {
        return id;
    }
}

