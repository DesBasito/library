package lesson;

import lesson.items.Book;
import lesson.items.Employee;
import lesson.items.Journal;
import util.FileUtil;

import java.io.IOException;
import java.util.List;

public class DataModel {
    private  List<Book> books;
    private  List<Employee> employees;
    private List<Journal> journal;

    public DataModel() throws IOException {
        this.books = FileUtil.readBook();
        this.employees = FileUtil.readEmplyee();
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
}
