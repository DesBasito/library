package service;

import com.sun.net.httpserver.HttpExchange;
import server.ContentType;
import server.Cookie;
import server.RouteHandler;
import util.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
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
        // lesson 46
        registerGet("/lesson46",this::lesson46Handler);
        registerGet("/cookie",this::cookieHandler);
    }

    private void cookieHandler(HttpExchange exchange) {
        String incomeCookie = getCookies(exchange);
        System.out.printf("cookie: %s%n",incomeCookie);
        incomeCookie = Cookie.parse(incomeCookie).get("userId");
        System.out.printf("cookie: %s%n",incomeCookie);
    }


    private void lesson46Handler(HttpExchange exchange) {
        Map<String, Object> data = new HashMap<>();
        String name = "times";

        Cookie c1 = Cookie.make("user%Id", "456");
        setCookie(exchange, c1);

        Cookie c2 = Cookie.make("user-email", "example@mail");
        setCookie(exchange, c2);

        Cookie c3 = Cookie.make("restricted()<>@,;:\\\"/[]?={}", "()<>@,;:\\\"/[]?={}");
        setCookie(exchange, c3);


        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);

        String cookieValue = cookies.getOrDefault(name,"0");
        int times  =Integer.parseInt(cookieValue)+1;

        Cookie cookie = Cookie.make(name,times);
        setCookie(exchange,cookie);

        data.put(name,times);
        data.put("cookies", cookies);


        renderTemplate(exchange, "cookie.ftlh", data);
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
