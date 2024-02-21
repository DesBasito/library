package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entities.Book;
import entities.Employee;
import entities.Journal;

import java.util.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    private FileUtil() {
    }

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("dd.MM.yyyy") // Updated date format pattern
            .create();

    private static final Path BOOKS_PATH = Paths.get("data/books.json");
    private static final Path EMPLOYEES_PATH = Paths.get("data/employees.json");
    private static final Path JOURNAL_PATH = Paths.get("data/journal.json");


    public static List<Book> readBook() throws IOException {
        String str = Files.readString(BOOKS_PATH);
        return GSON.fromJson(str, new TypeToken<List<Book>>() {
        }.getType());
    }

    public static List<Employee> readEmployee() throws IOException {
        String str = Files.readString(EMPLOYEES_PATH);
        return GSON.fromJson(str, new TypeToken<List<Employee>>() {
        }.getType());
    }

    public static List<Journal> readJournal() throws IOException {
        String str = Files.readString(JOURNAL_PATH);
        return GSON.fromJson(str, new TypeToken<List<Journal>>() {
        }.getType());
    }

    public static void writeFile(List<Journal> tasks) {
        String json = GSON.toJson(tasks);
        try {
            Files.writeString(JOURNAL_PATH, json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
