package service;

import entities.Book;
import entities.Employee;
import entities.Journal;
import util.FileUtil;

import java.io.IOException;
import java.util.List;

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
}
