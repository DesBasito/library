package util;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Util {
    private Util() {
    }

    public static PrintWriter getWriterFrom(HttpExchange exchange) {
        return new PrintWriter(exchange.getResponseBody(), false, UTF_8);
    }

    public static void write(PrintWriter writer, String msg, String method) {
        String data = String.format("%s: %s%n", msg, method);
        writer.write(data);
    }

    public static void writeHeaders(PrintWriter writer, String type, Headers headers) {
        write(writer, type, "");
        headers.forEach((k, v) -> write(writer, "\t" + k, v.toString()));
    }

    public static void writeData(String message, PrintWriter writer, HttpExchange exchange) {
        try (BufferedReader reader = getReader(exchange)) {
            if (!reader.ready()) {
                return;
            }

            write(writer, message, "");
            reader.lines().forEach(v -> write(writer, "\t", v));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedReader getReader(HttpExchange exchange) {
        InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody(), UTF_8);
        return new BufferedReader(inputStreamReader);
    }
}