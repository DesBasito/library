package service;

import entities.Book;
import entities.Employee;
import entities.Journal;
import util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

public class UserService {
    private  List<Employee> employees;

    public UserService(){
        this.employees = FileUtil.readEmployee();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void handleUser(Map<String,String> parsed) {
        int n = employees.size() + 1;
        String name = parsed.getOrDefault("firstName","user");
        String lastName = parsed.getOrDefault("lastName","abuser");
        String email = parsed.get("email");
        String password = parsed.get("user-password");
        Employee emp = new Employee(name,lastName,n,email , password,null);
        List<Employee> employeeList = employees;
        employeeList.add(emp);
        FileUtil.writeEmployee(employeeList);
    }


    public boolean checkAuthorizedUser(Map<String, String> parsed) {
        String providedPassword = parsed.get("user-password");
        String providedEmail = parsed.get("email");
        return employees.stream().filter(em -> em.getEmail().equalsIgnoreCase(providedEmail)).findFirst().filter(em -> em.getPassword().equals(providedPassword)).isPresent();
    }

    public boolean checkRegisteredUser(Map<String, String> parsed) {
        return employees.stream().noneMatch(em -> em.getEmail().equalsIgnoreCase(parsed.get("email")));
    }

    public int authorizedUserId(Map<String,String> parsed){
        return employees.stream().filter(em -> em.getEmail().equalsIgnoreCase(parsed.get("email"))).findFirst().map(Employee::getId).orElse(0);
    }

    public Employee getUserById(int id){
        return employees.stream().filter(u -> u.getId() == id).findFirst().orElseThrow();
    }

    public List<Book> getBooksOnHandByUserId(int userId) {
        List<Journal> journals = FileUtil.readJournal();
        List<Book> booksServices = FileUtil.readBook();
        List<Book> books = journals.stream().filter(journal1 -> journal1.getReturnedDate() == null).filter(journal1 -> userId == journal1.getBorrower()).map(journal1 -> booksServices.get(journal1.getBook() - 1)).collect(Collectors.toList());
        return books;
    }

    public Set<Book> getJournalBooksByUserId(int userId) {
        List<Journal> journals = FileUtil.readJournal();
        List<Book> booksServices = FileUtil.readBook();
        Set<Book> books = new HashSet<>();

        for (Journal journal : journals) {
            if (journal.getReturnedDate() != null && userId == journal.getBorrower()) {
                int bookIndex = journal.getBook() - 1;
                if (bookIndex >= 0 && bookIndex < booksServices.size()) {
                    books.add(booksServices.get(bookIndex));
                }
            }
        }
        return books;
    }


    public boolean checkEmailPassword(Map<String, String> parsed) {
        return !parsed.get("email").isBlank() || !parsed.get("user-password").isBlank();
    }
}
