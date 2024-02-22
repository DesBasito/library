package entities;

import java.util.Date;

public class Employee {

    private final String firstName;
    private final String lastName;
    private int isReading;
    private int id;
    private String position;
    private String hobby;
    private final Date birthdate;
    private String phone;
    private String email;
    private String password;

    public Employee(String firstName, String lastName, int isReading, int id, String position, String hobby, Date birthdate, String phone,String email,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isReading = isReading;
        this.id = id;
        this.position = position;
        this.hobby = hobby;
        this.birthdate = birthdate;
        this.phone = phone;
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

    public int getIsReading() {
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

    public Date getBirthdate() {
        return birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
