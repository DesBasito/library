package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lesson.items.Book;
import lesson.items.Employee;
import lesson.items.Journal;

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

    private static final Path BOOKS_PATH = Paths.get("vendor/books.json");
    private static final Path EMPLOYEES_PATH = Paths.get("vendor/employees.json");
    private static final Path JOURNAL_PATH = Paths.get("vendor/journal.json");


    public static List<Book> readBook() throws IOException {
        String str = Files.readString(BOOKS_PATH);
        return GSON.fromJson(str, new TypeToken<List<Book>>() {}.getType());
    }

    public static List<Employee> readEmplyee() throws IOException {
        String str = Files.readString(EMPLOYEES_PATH);
        return GSON.fromJson(str, new TypeToken<List<Employee>>() {}.getType());
    }

    public static List<Journal> readJournal()throws IOException{
        String str = Files.readString(JOURNAL_PATH);
        return GSON.fromJson(str,new TypeToken<List<Journal>>() {}.getType());
    }

//    public static void writeFile(List<Task> tasks) {
//        String json = GSON.toJson(tasks);
//        try {
//            Files.writeString(PATH, json);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//    }
}
