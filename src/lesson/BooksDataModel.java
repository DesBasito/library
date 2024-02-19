package lesson;

import java.util.List;

public class BooksDataModel {
    private List<Book> books;
    public BooksDataModel() {
        this.books = List.of(
                new Book("Alexander Belyaev", "Lord of world"),
                new Book("Aleksandr Volkov", "Wizard of the Emerald City"),
                new Book("Paulo Coelho", "Alchemist"),
                new Book("Harry Harrison", "Invasion"),
                new Book("Alexander Belyaev", "Professor Dowell's head"),
                new Book("Robert Kiyosaki", "Rich dad, poor dad"),
                new Book("Tologon Kasymbekov", "Broken sword"),
                new Book("Mukay Elebayev", "Long haul"),
                new Book("Aaly Tokombayev", "Bloody years"),
                new Book("Joanne Rowling", "Harry Potter"),
                new Book("Oscar Wilde", "Dorian Grey"),
                new Book("Alexander Pushkin", "Dubrovskiy")
        );
    }
    public List<Book> getBooks() {
        return books;
    }

    public static class Book{
        private String author;
        private String name;
        public Book(String author, String name) {
            this.author = author;
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public String getName() {
            return name;
        }
    }
}
