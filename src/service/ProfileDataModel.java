package service;

import entities.Book;
import entities.Employee;


import java.util.List;
import java.util.Set;

public class ProfileDataModel {
    private final Employee user;
    private List<Book> readingBooks;
    private Set<Book> readBooks;

    public ProfileDataModel(Employee user,Set<Book> readBooks,List<Book> readingBooks) {
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

    public Set<Book> getReadBooks() {
        return readBooks;
    }

    public  boolean checkUserHand(int bookId){
        return readingBooks.stream().anyMatch(book -> bookId == book.getId());
    }
}
