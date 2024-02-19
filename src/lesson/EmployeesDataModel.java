package lesson;

import java.util.List;

public class EmployeesDataModel {
    private List<Employee> employees;
    public EmployeesDataModel() {
        this.employees = List.of(
                new Employee("John", "Smith"),
                new Employee("Emma", "Johnson"),
                new Employee("Michael", "Williams"),
                new Employee("Emily", "Jones"),
                new Employee("Christopher", "Brown"),
                new Employee("Jessica", "Davis"),
                new Employee("Daniel", "Miller"),
                new Employee("Olivia", "Wilson"),
                new Employee("Matthew", "Moore"),
                new Employee("Ava", "Taylor"),
                new Employee("David", "Anderson"),
                new Employee("Sophia", "Thomas")
        );
    }
    public List<Employee> getEmployees() {
        return employees;
    }

    public static class Employee {
        private String firstName;
        private String secondName;

        public Employee(String firstName, String secondName) {
            this.firstName = firstName;
            this.secondName = secondName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getSecondName() {
            return secondName;
        }
    }
}
