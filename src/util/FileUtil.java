package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entities.Book;
import entities.Employee;
import entities.Journal;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {
    private FileUtil() {
    }

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("dd.MM.yyyy")
            .create();

    private static final Path BOOKS_PATH = Paths.get("data/books.json");
    private static final Path EMPLOYEES_PATH = Paths.get("data/employees.json");
    private static final Path JOURNAL_PATH = Paths.get("data/journal.json");


    public static List<Book> readBook() {
        List<Book> books = new ArrayList<>();
        try {
            String str = Files.readString(BOOKS_PATH);
            books = GSON.fromJson(str, new TypeToken<List<Book>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static List<Employee> readEmployee() {
        List<Employee> employees = new ArrayList<>();
        try {
            String str = Files.readString(EMPLOYEES_PATH);
            employees = GSON.fromJson(str, new TypeToken<List<Employee>>() {
            }.getType());
        }catch (IOException e){
            e.printStackTrace();
        }
       return employees;
    }

    public static List<Journal> readJournal() {
        List<Journal> journal = new ArrayList<>();
        try {
            String str = Files.readString(JOURNAL_PATH);
            journal =  GSON.fromJson(str, new TypeToken<List<Journal>>() {
            }.getType());
        }catch (IOException e){
            e.printStackTrace();
        }
        return journal;
    }

    public static void writeEmployee(List<Employee> employees) {
        String json = GSON.toJson(employees);
        try {
            Files.writeString(EMPLOYEES_PATH, json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void writeJournal(List<Journal> journal) {
        String json = GSON.toJson(journal);
        try {
            Files.writeString(JOURNAL_PATH, json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

        public static void writeBook(List<Book> book) {
        String json = GSON.toJson(book);
        try {
            Files.writeString(BOOKS_PATH, json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static Map<String, String> parseUrlEncoded(String raw, String delimiter) {
        String[] pairs = raw.split(delimiter);

        Stream<Map.Entry<String, String>> stream = Arrays.stream(pairs)
                .map(FileUtil::decode)
                .filter(Optional::isPresent)
                .map(Optional::get);

        return stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Boolean parseUrlEncodedBool(String raw, String delimiter) {
        String[] pairs = raw.split(delimiter);

        Stream<Map.Entry<String, String>> stream = Arrays.stream(pairs)
                .map(FileUtil::decode)
                .filter(Optional::isPresent)
                .map(Optional::get);

        var bool = stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return Boolean.parseBoolean(bool.get("true"));
    }

    static Optional<Map.Entry<String, String>> decode(String kv) {
        if (!kv.contains("=")) {
            return Optional.empty();
        }

        String[] pair = kv.split("=");
        if (pair.length != 2) {
            return Optional.empty();
        }

        Charset utf8 = StandardCharsets.UTF_8;
        String key = URLDecoder.decode(pair[0], utf8).strip();
        String value = URLDecoder.decode(pair[1], utf8).strip();
        return Optional.of(Map.entry(key, value));
    }


}
