package service;

import entities.Book;
import util.FileUtil;

import java.util.List;

public class BooksService {
    private List<Book> books;

    public BooksService() {
        this.books = FileUtil.readBook();
    }

    public List<Book> getBooks() {
        return books;
    }
}
