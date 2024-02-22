package service;

import com.sun.net.httpserver.HttpExchange;
import server.BasicServer;
import server.ContentType;
import server.RouteHandler;

import java.io.IOException;
import java.nio.file.Path;

public class Post45Server extends FreeMarkerServer {
    public Post45Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/register",this::registerGet);
        registerPost("/register",this::registerPost);
        registerGet("/login",this::loginGet);
        registerPost("/login",this::loginPost);
    }

    private void registerPost(HttpExchange exchange) {
        AddCheckUser user = new AddCheckUser(exchange);
        user.newUser(exchange);
//        redirect303(exchange,"/employees");
        BasicServer.registrErr(exchange);
    }

    private void loginPost(HttpExchange exchange) {
        AddCheckUser user = new AddCheckUser(exchange);
        user.checkUser(exchange);
        //        redirect303(exchange,"/employees");
        BasicServer.registrErr(exchange);
    }



    private void registerGet(HttpExchange exchange) {
        Path path = makeFilePath("login/register.ftlh");
        sendFile(exchange,path, ContentType.TEXT_HTML);
    }


    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login/login.ftlh");
        sendFile(exchange,path, ContentType.TEXT_HTML);
    }

    protected void registerPost(String rout, RouteHandler handler){
        getRoutes().put("POST "+rout,handler);
    }
}
