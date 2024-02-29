package service;

import entities.Book;
import entities.Employee;
import entities.Journal;
import util.FileUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class BookService {
    private List<Book> books;
    private List<Journal> journal;

    public BookService() {
        this.books = FileUtil.readBook();
        this.journal = FileUtil.readJournal();
    }

    public boolean checkIsBookFree(Map<String, String> parsed) {
        int value = Integer.parseInt(parsed.get("bookId"));
        for (Book book:books){
            if (book.getId() == value){
                return book.isFree();
            }
        }
        return false;
    }

    public void handleBook(int bookId, int userId) {
        List<Journal> journalList = journal;
        Date borrowedDate = currentDate();
        int id = journalList.size()+1;
        Journal jour = new Journal(bookId,userId,borrowedDate,null,id);
        journalList.add(jour);
        books.stream().filter(book -> book.getId() == bookId).forEach(book -> book.setFree(false));
        FileUtil.writeBook(books);
        FileUtil.writeJournal(journalList);
    }

    public void returnBook(int bookId) {
        List<Book> bookList = books;
        Book book = bookList.stream().filter(book1 -> bookId == book1.getId()).findFirst().orElseThrow();
        bookList.get(bookId-1).setFree(true);
        List<Journal> jrl = journal;
        for (Journal jour : jrl){
            if (jour.getBook()==book.getId()){
                jour.setReturnedDate(currentDate());
            }
        }
        FileUtil.writeJournal(jrl);
        FileUtil.writeBook(bookList);
    }


    public static Date currentDate() {
        try {
            LocalDate borDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDate = borDate.format(formatter);
            return new SimpleDateFormat("dd.MM.yyyy").parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
