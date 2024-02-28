package entities;


public class Book {
    private String author;
    private String title;
    private int id;
    private String description;
    private int writtenDate;
private String imagePath;
    private boolean free;

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isFree() {
        return free;
    }

    public Book(String author, String name, int id, String description, int writtenDate, boolean free,String imagePath) {
        this.author = author;
        this.title = name;
        this.id = id;
        this.description = description;
        this.writtenDate = writtenDate;
        this.free = free;
        this.imagePath = imagePath;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
       return id;
    }

    public String getDescription() {
        return description;
    }

    public int getWrittenDate() {
        return writtenDate;
    }

    public String getImagePath() {
        return imagePath;
    }
}
