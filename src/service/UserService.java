package service;

import entities.Book;
import entities.Employee;
import entities.Journal;
import util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

public class UserService {
    private  List<Employee> employees;
    private List<Journal> journal;

    public UserService(){
        this.employees = FileUtil.readEmployee();
        this.journal = FileUtil.readJournal();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void handleUser(Map<String,String> parsed) {
        int n = employees.size() + 1;
        String name = parsed.get("firstName");
        String lastName = parsed.get("lastName");
        String email = parsed.get("email");
        String password = parsed.get("user-password");
        Employee emp = new Employee(name,lastName,n,email , password);
        addUser(emp);
    }

    private void addUser(Employee emp){
        List<Employee> employeeList = employees;
        employeeList.add(emp);
        FileUtil.writeEmployee(employeeList);
    }



    public boolean checkAuthorizedUser(Map<String, String> parsed) {
        String providedPassword = parsed.get("user-password");
        String providedEmail = parsed.get("email");
        for (Employee em : employees) {
            if (em.getEmail().equalsIgnoreCase(providedEmail)) {
                return em.getPassword().equals(providedPassword);
            }
        }
        return false;
    }

    public boolean checkRegisteredUser(Map<String, String> parsed) {
        for (Employee em : employees) {
            if (em.getEmail().equalsIgnoreCase(parsed.get("email"))) {
                return false;
            }
        }
        return true;
    }

    public int authorizedUserId(Map<String,String> parsed){
        for (Employee em : employees) {
            if (em.getEmail().equalsIgnoreCase(parsed.get("email"))) {
                return em.getId();
            }
        }
        return 0;
    }

    public Employee getUserById(int id){
        return employees.stream().filter(u -> u.getId() == id).findFirst().orElseThrow();
    }

    public List<Book> getJournalBooksByUserId(int userId) {
        List<Journal> journals = journal;
        List<Book> booksServices = FileUtil.readBook();
        List<Book> books = new ArrayList<>();
        for (Journal journal1 : journals) {
            if (journal1.getReturnedDate() == null){
                if (userId == journal1.getBorrower()) {
                    books.add(booksServices.get(journal1.getBook()-1));
                }
            }

        }
        return books;
    }

    public List<Book> getBooksOnHandByUserId(int userId) {
        List<Journal> journals = journal;
        List<Book> booksServices = FileUtil.readBook();
        List<Book> books = new ArrayList<>();
        for (Journal journal1 : journals) {
            if (userId == journal1.getBorrower()) {
                books.add(booksServices.get(journal1.getBook()-1));
            }
        }
        return books;
    }
}
