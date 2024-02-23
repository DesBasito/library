package service;

import com.sun.net.httpserver.HttpExchange;
import server.ContentType;
import server.RouteHandler;
import util.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class PostServer extends Server {

    private static DataModel model = new DataModel();
    public PostServer(String host, int port) throws IOException {
        super(host, port);
        registerGet("/register", this::registrationGet);
        registerPost("/register", this::registrationPost);
        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/profile",this::profileGet);
    }

    private void profileGet(HttpExchange exchange) {
        Path path = makeFilePath("templates/profile.ftlh");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void registrationPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String,String> parsed = FileUtil.parseUrlEncoded(raw,"&");
        if (model.check(parsed)){
            model.addUser(parsed);
            redirect303(exchange,"/employees");
        }else {
            registerErr(exchange);
        }
    }

    private void loginPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String,String> parsed = FileUtil.parseUrlEncoded(raw,"&");
        if (model.checkUser(parsed)){
            redirect303(exchange,"/profile");
        }else {
            authorisationErr(exchange);
        }
    }


    private void registrationGet(HttpExchange exchange) {
        Path path = makeFilePath("login/register.ftlh");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }


    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login/login.ftlh");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    protected void registerPost(String rout, RouteHandler handler) {
        getRoutes().put("POST " + rout, handler);
    }
}
