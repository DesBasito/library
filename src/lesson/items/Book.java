package lesson.items;

import java.time.Year;

public class Book {
    private String author;
    private String title;
    private String where;
    private int id;
    private String description;
    private String written_date;

    public Book(String author, String name,String where,int id,String description, String written_date) {
        this.author = author;
        this.title = name;
        this.where = where;
        this.id = id;
        this.description = description;
        this.written_date = written_date;
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

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getWritten_date() {
        return written_date;
    }
}
