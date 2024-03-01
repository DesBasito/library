package entities;

public class Employee {

    private final String firstName;
    private final String lastName;
    private int id;
    private String email;
    private String password;
    private String imagePath;

    public Employee(String firstName, String lastName,  int id,String email,String password,String imagePath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.password = password;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }
}
