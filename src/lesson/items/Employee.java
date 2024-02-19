package lesson.items;

import java.util.List;

public class Employee {

    private String firstName;
    private String lastName;
    private String isReading;


    public Employee(String firstName, String lastName, String isReading) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isReading = isReading;
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
}
