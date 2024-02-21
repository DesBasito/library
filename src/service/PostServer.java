package service;

import com.sun.net.httpserver.HttpExchange;
import server.ContentType;

import java.io.IOException;
import java.nio.file.Path;

public class PostServer extends FreeMarkerServer {
    public PostServer(String host, int port) throws IOException {
        super(host, port);
        registerGet("/login",this::loginGet);
    }

    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login/login.ftlh");
        sendFile(exchange,path, ContentType.TEXT_HTML);
    }
}
