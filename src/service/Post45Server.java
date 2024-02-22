package service;

import com.sun.net.httpserver.HttpExchange;
import server.BasicServer;
import server.ContentType;
import server.ResponseCodes;
import server.RouteHandler;
import util.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Post45Server extends FreeMarkerServer {
    public Post45Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/login",this::loginGet);
        registerPost("/login",this::loginPost);
        registerPost("/books",this::loginPost);
    }

    private void loginPost(HttpExchange exchange) {
        AddingNewUser user = new AddingNewUser(exchange);
        try {
            user.newUser(exchange);
            redirect303(exchange,"/employees");
        }catch (IOException e){
            BasicServer.registrErr(exchange);
        }
    }



    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login/login.ftlh");
        sendFile(exchange,path, ContentType.TEXT_HTML);
    }

    protected void registerPost(String rout, RouteHandler handler){
        getRoutes().put("POST "+rout,handler);

    }
}
