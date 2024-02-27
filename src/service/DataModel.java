package service;

import entities.Book;
import entities.Journal;
import util.FileUtil;

import java.util.List;

public class BooksService {
    private List<Book> books;
    private List<Journal> journals;

    public BooksService() {
        this.books = FileUtil.readBook();
        this.journals = FileUtil.readJournal();
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Journal> getJournals() {
        return journals;
    }
}
