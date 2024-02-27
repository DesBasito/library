package service;

import entities.Book;
import entities.Journal;
import util.FileUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BookService {
    private List<Book> books;
    private List<Journal> journal;

    public BookService() {
        this.books = FileUtil.readBook();
        this.journal = FileUtil.readJournal();
    }

    public boolean checkIsBookFree(Map<String, String> parsed) {
        int value = Integer.parseInt(parsed.get("bookId"));
        for (Journal jour : journal) {
            if (jour.getBook() == value) {
                if (jour.getReturnedDate() != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void handleBook(int bookId, int userId) {
        List<Journal> journalList = journal;
        LocalDate borDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = borDate.format(formatter);
        Date borrowedDate = parseStringToDate(formattedDate);
        Journal jour = new Journal(bookId,userId,borrowedDate,null,userId);
        journalList.add(jour);
        FileUtil.writeJournal(journalList);
    }


    public static Date parseStringToDate(String dateString) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
