package lesson.items;

public class Journal {
    private String book;
    private String author;
    private String borrower;
    private String borrowed_date;
    private String returned_date;

    public Journal(String book, String author, String borrower, String borrowed_date, String returned_date) {
        this.book = book;
        this.author = author;
        this.borrower = borrower;
        this.borrowed_date = borrowed_date;
        this.returned_date = returned_date;
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
}

