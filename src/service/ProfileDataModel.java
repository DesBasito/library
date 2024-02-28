package service;

import entities.Book;
import entities.Employee;


import java.util.List;

public class ProfileDataModel {
    private final Employee user;
    private List<Book> readingBooks;
    private List<Book> readBooks;

    public ProfileDataModel(Employee user,List<Book> readBooks,List<Book> readingBooks) {
        this.user = user;
        this.readingBooks = readingBooks;
        this.readBooks = readBooks;
    }

    public Employee getUser() {
        return user;
    }

    public List<Book> getReadingBooks() {
        return readingBooks;
    }

    public List<Book> getReadBooks() {
        return readBooks;
    }

    public  boolean checkUserHand(){
        return readingBooks.size() < 2;
    }
}
