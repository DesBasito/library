package lesson.items;

public class Journal {
    private String book;
    private String author;
    private String borrower;
    private String borrowed_date;
    private String returned_date;
    private int id;

    public Journal(String book, String author, String borrower, String borrowed_date, String returned_date,int id) {
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

    public String getBorrowed_date() {
        return borrowed_date;
    }

    public String getReturned_date() {
        return returned_date;
    }

    public int getId() {
        return id;
    }
}

