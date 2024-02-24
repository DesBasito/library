package entities;

import service.UserService;

public class Book {
    private String author;
    private String title;
    private int where;
    private int id;
    private String description;
    private int writtenDate;

    public Book(String author, String name,int where,int id,String description, int writtenDate) {
        this.author = author;
        this.title = name;
        this.where = where;
        this.id = id;
        this.description = description;
        this.writtenDate = writtenDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getWhere() {
        for (Employee emp : new UserService().getEmployees()){
            if (emp.getId()==this.where){
                return emp.getFirstName() +" "+ emp.getLastName();
            }
        }
        return "in library";
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
}
