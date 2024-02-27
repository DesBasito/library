package service;

import entities.Book;
import entities.Employee;
import entities.Journal;
import util.FileUtil;

import java.util.List;

public class DataModel {
    private List<Book> books;
    private List<Journal> journals;
    private List<Employee> employees;

    public DataModel() {
        this.books = FileUtil.readBook();
        this.journals = FileUtil.readJournal();
        this.employees = FileUtil.readEmployee();
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Journal> getJournals() {
        return journals;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
