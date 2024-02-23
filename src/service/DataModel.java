package service;

import entities.Book;
import entities.Employee;
import entities.Journal;
import util.FileUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DataModel {
    private  List<Book> books;
    private  List<Employee> employees;
    private List<Journal> journal;

    public DataModel() {
        this.books = FileUtil.readBook();
        this.employees = FileUtil.readEmployee();
        this.journal = FileUtil.readJournal();
    }

    public List<Book> getBooks() {
        return books;
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

    public void addUser(Map<String,String> parsed) {
        int n = employees.size() + 1;
        String birth = parsed.get("birthdate");
        String position = parsed.getOrDefault("position", null);
        String hobby = parsed.getOrDefault("hobby", null);
        String name = parsed.get("firstName");
        String lastName = parsed.get("lastName");
        Date birthdate = birthDate(birth);
        String phone = parsed.get("phone");
        String email = parsed.get("email");
        String password = parsed.get("user-password");
        Employee emp = new Employee(name,lastName, 0, n, position, hobby, birthdate, phone,email , password);
        List<Employee> employeeList = employees;
        employeeList.add(emp);
        FileUtil.writeFile(employeeList);
    }


    public boolean checkUser(Map<String, String> parsed) {
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

    public boolean check(Map<String, String> parsed) {
        for (Employee em : employees) {
            if (em.getEmail().equalsIgnoreCase(parsed.get("email"))) {
                return false;
            }
        }
        return true;
    }

}
