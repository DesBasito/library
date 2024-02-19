package lesson.items;

public class Book {
    private String author;
    private String title;
    private String where;

    public Book(String author, String name,String where) {
        this.author = author;
        this.title = name;
        this.where = where;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getWhere() {
        return where;
    }

}
