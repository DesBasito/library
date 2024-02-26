package service;

import entities.Book;
import entities.Employee;
import entities.Journal;
import server.Cookie;

import java.util.ArrayList;
import java.util.List;

public class ProfileDataModel {
    private static Employee user;
    private List<Book> readingBooks;
    private List<Book> readBooks;

    public ProfileDataModel(Employee user,List<Book> readBooks,List<Book> readingBooks) {
        this.user = user;
        this.readingBooks = readingBooks;
        this.readBooks = readBooks;
    }


    public List<Book> getReadingBooks() {
        return readingBooks;
    }

    public void setReadingBooks(List<Book> readingBooks) {
        this.readingBooks = readingBooks;
    }

    public List<Book> getReadBooks() {
        return readBooks;
    }

    public void setReadBooks(List<Book> readBooks) {
        this.readBooks = readBooks;
    }
}
