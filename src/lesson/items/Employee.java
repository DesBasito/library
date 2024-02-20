package lesson.items;

import java.lang.annotation.Target;

public class Employee {

    private String firstName;
    private String lastName;
    private String isReading;
    private int id;
    private String position;
    private String hobby;
    private String birthdate;
    private String phone;
    private String fullName;

    public Employee(String firstName, String lastName, String isReading, int id, String position, String hobby, String birthdate, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isReading = isReading;
        this.id = id;
        this.position = position;
        this.hobby = hobby;
        this.birthdate = birthdate;
        this.phone = phone;
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

    public String getPosition() {
        return position;
    }

    public String getHobby() {
        return hobby;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName() {
        fullName = firstName+" "+lastName;
        return fullName;
    }
}
