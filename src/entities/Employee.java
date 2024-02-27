package entities;

public class Employee {

    private final String firstName;
    private final String lastName;
    private int id;
    private String email;
    private String password;

    public Employee(String firstName, String lastName,  int id,String email,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
