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

    public static Map<String, List<String>> parseUrlEncoded(String raw, String delimiter) {
        String[] pairs = raw.split(delimiter);

        Stream<Map.Entry<String, List<String>>> stream = Arrays.stream(pairs)
                .map(FileUtil::decode)
                .filter(Optional::isPresent)
                .map(Optional::get);

        return stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
            List<String> merged = new ArrayList<>(v1);
            merged.addAll(v2);
            return merged;
        }));
    }

    static Optional<Map.Entry<String, List<String>>> decode(String kv) {
        if (!kv.contains("=")) {
            return Optional.empty();
        }

        String[] pair = kv.split("=");
        if (pair.length != 2) {
            return Optional.empty();
        }

        Charset utf8 = StandardCharsets.UTF_8;
        String key = URLDecoder.decode(pair[0],utf8);
        String value = URLDecoder.decode(pair[1], utf8);
        return Optional.of(Map.entry(key, Collections.singletonList(value)));
    }


}
