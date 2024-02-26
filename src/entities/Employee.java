package entities;

import service.BooksService;

import java.util.Date;

public class Employee {

    private final String firstName;
    private final String lastName;
    private int isReading;
    private int id;
    private String email;
    private String password;

    public Employee(String firstName, String lastName, int isReading, int id,String email,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isReading = isReading;
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public void setIsReading(int isReading) {
        this.isReading = isReading;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIsReading() {
        for (Book book : new BooksService().getBooks()){
            if (book.getId() == this.isReading){
                return book.getTitle();
            }
        }
        return "On tik tok";
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
