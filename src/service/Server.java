package service;

import com.sun.net.httpserver.HttpExchange;
import entities.Book;
import entities.Employee;
import server.BasicServer;
import server.ContentType;
import server.Cookie;
import server.ResponseCodes;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import util.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.*;

public class Server extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();
    private static UserService userService = new UserService();

    private final static int ten = 600;

    public Server(String host, int port) throws IOException {
        super(host, port);
        //lesson 44
        registerGet("/books", this::booksHandler);
        registerGet("/employees", this::employeesHandler);
        registerGet("/journal", this::journalHandler);
        registerGet("/about", this::aboutBook);
        registerGet("/info", this::employeeInfo);
        // lesson 45-46
        registerGet("/register", this::registrationGet);
        registerPost("/register", this::registrationPost);
        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/profile", this::profileGet);
    }

    private void booksHandler(HttpExchange exchange) {
        renderTemplate(exchange, "books.ftlh", getBooksDataModel());
    }

    private void employeesHandler(HttpExchange exchange) {
        renderTemplate(exchange, "employees.ftlh", getDataModel());
    }

    private void journalHandler(HttpExchange exchange) {
        renderTemplate(exchange, "journal.ftlh", getDataModel());
    }

    private void aboutBook(HttpExchange exchange) {
        renderTemplate(exchange, "about.ftlh", getBooksDataModel());
    }

    private void employeeInfo(HttpExchange exchange) {
        renderTemplate(exchange, "info.ftlh", getDataModel());
    }

    private UserService getDataModel() {
        return new UserService();
    }

    private BooksService getBooksDataModel() {
        return new BooksService();
    }

    private void profileGet(HttpExchange exchange) {

        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        int userId = Integer.parseInt(cookies.get("userId"));
        var user = userService.getUserById(userId);
        List<Book> bookOnHand = userService.getBooksOnHandByUserId(userId);
        List<Book> historyBooks = userService.getJournalBooksByUserId(userId);

        ProfileDataModel authorized = new ProfileDataModel(user, bookOnHand, historyBooks);
            renderTemplate(exchange, "profile.ftlh", authorized);
//        if (cookie.getValue().equals(userService.getUser().getEmail())) {
//        } else {
//            redirect303(exchange, "/books");
//        }

    }

    private void registrationPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = FileUtil.parseUrlEncoded(raw, "&");
        if (userService.checkRegisteredUser(parsed)) {
            userService.handleUser(parsed);
            redirect303(exchange, "/login");
        } else {
            registerErr(exchange);
        }
    }

    private void loginPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = FileUtil.parseUrlEncoded(raw, "&");
        if (userService.checkAuthorizedUser(parsed)) {
            Employee user = userService.getUserById(userService.autorizedUserId(parsed));// логика по поиску юзера

            Cookie cookie = Cookie.make("userId", user.getId());
            setCookie(exchange, cookie);

            redirect303(exchange, "/profile");
        } else {
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


    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
            // путь к каталогу в котором у нас хранятся шаблоны
            // это может быть совершенно другой путь, чем тот, откуда сервер берёт файлы
            // которые отправляет пользователю
            cfg.setDirectoryForTemplateLoading(new File("data/templates"));

            // прочие стандартные настройки о них читать тут
            // https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            // Загружаем шаблон из файла по имени.
            // Шаблон должен находится по пути, указанном в конфигурации
            Template temp = freemarker.getTemplate(templateFile);

            // freemarker записывает преобразованный шаблон в объект класса writer
            // а наш сервер отправляет клиенту массивы байт
            // по этому нам надо сделать "мост" между этими двумя системами

            // создаём поток, который сохраняет всё, что в него будет записано в байтовый массив
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // создаём объект, который умеет писать в поток и который подходит для freemarker
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {

                // обрабатываем шаблон заполняя его данными из модели
                // и записываем результат в объект "записи"
                temp.process(dataModel, writer);
                writer.flush();

                // получаем байтовый поток
                var data = stream.toByteArray();

                // отправляем результат клиенту
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
