package service;

import entities.Book;
import entities.Employee;
import entities.Journal;
import util.FileUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserService {
    private  List<Employee> employees;
    private List<Journal> journal;

    public UserService() {
        this.employees = FileUtil.readEmployee();
        this.journal = FileUtil.readJournal();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Journal> getJournal() {
        return journal;
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
        Employee emp = new Employee(name,lastName, 0, n,email , password);
        addUser(emp);
    }

    private void addUser(Employee emp){
        List<Employee> employeeList = employees;
        employeeList.add(emp);
        FileUtil.writeFile(employeeList);
    }



    public boolean checkAuthorizedUser(Map<String, String> parsed) {
        String providedPassword = parsed.get("user-password");
        String providedEmail = parsed.get("email");
        for (Employee em : employees) {
            if (em.getEmail().equalsIgnoreCase(providedEmail)) {
                if (em.getPassword().equals(providedPassword)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    private static Date birthDate(String birth) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date birthDate = null;
        try {
            Date date = inputFormat.parse(birth);
            String formattedDate = outputFormat.format(date);
            birthDate = outputFormat.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthDate;
    }

    public boolean checkRegisteredUser(Map<String, String> parsed) {
        for (Employee em : employees) {
            if (em.getEmail().equalsIgnoreCase(parsed.get("email"))) {
                return false;
            }
        }
        return true;
    }

    public int autorizedUserId(Map<String,String> parsed){
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

    public List<Book> getBooksOnHandByUserId(int userId) {
        List<Book> books = new ArrayList<>();
        List<Journal> journals = new UserService().getJournal();
        List<Book> booksServices = new BooksService().getBooks();
        for (Journal journal : journals){
            if (userId == journal.getBorrower() && journal.getReturnedDate() == null){
                books.add(booksServices.get(journal.getBook().getId()));
            }
        }
        return books;
    }

    public List<Book> getJournalBooksByUserId(int userId) {
        List<Book> books = new ArrayList<>();
        List<Journal> journals = new UserService().getJournal();
        List<Book> booksServices = new BooksService().getBooks();
        for (Journal journal : journals){
            if (userId == journal.getBorrower() && journal.getReturnedDate() != null){
                books.add(booksServices.get(journal.getBook().getId()));
            }
        }
        return books;
    }
}
