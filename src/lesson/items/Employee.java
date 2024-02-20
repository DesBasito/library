package lesson.items;

import java.lang.annotation.Target;

public class Employee {

    private String firstName;
    private String lastName;
    private String isReading;
    private int id;
    private String fullName;

    public Employee(String firstName, String lastName, String isReading,int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isReading = isReading;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIsReading() {
        return isReading;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        fullName = firstName+" "+lastName;
        return fullName;
    }
}
