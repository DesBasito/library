package service;

import com.sun.net.httpserver.HttpExchange;
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
    }

    private void loginPost(HttpExchange exchange) {
        String cType = getContentType(exchange);
        String raw = getBody(exchange);

        Map<String,List<String>> parsed = FileUtil.parseUrlEncoded(raw, "&");

        String fmt = "<p>Необработанные данные: <b>%s</b></p>"
                + "<p>Content-type: <b>%s</b></p>"
                + "<p>После обработки: <b>%s</b></p>";
        String data = String.format(fmt, raw, cType, parsed);
        try {
            sendByteData(exchange, ResponseCodes.OK,
                    ContentType.TEXT_HTML, data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        redirect303(exchange,"/employees");
    }



    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login/login.ftlh");
        sendFile(exchange,path, ContentType.TEXT_HTML);
    }

    protected void registerPost(String rout, RouteHandler handler){
        getRoutes().put("POST "+rout,handler);

    }
}
