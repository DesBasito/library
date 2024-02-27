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
    private static BookService bookService = new BookService();

    private final static int tenMinute = 600;

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
        registerGet("/takeBook", this::takeBookGet);
        registerPost("/takeBook",this::takeBookPost);
    }


    private void booksHandler(HttpExchange exchange) {
        renderTemplate(exchange, "books.ftlh", getDataModel());
    }

    private void employeesHandler(HttpExchange exchange) {
        renderTemplate(exchange, "employees.ftlh", getDataModel());
    }

    private void journalHandler(HttpExchange exchange) {
        renderTemplate(exchange, "journal.ftlh", getDataModel());
    }

    private void aboutBook(HttpExchange exchange) {
        renderTemplate(exchange, "about.ftlh", getDataModel());
    }

    private void employeeInfo(HttpExchange exchange) {
        renderTemplate(exchange, "info.ftlh", getDataModel());
    }

    private DataModel getDataModel() {
        return new DataModel();
    }


    private void profileGet(HttpExchange exchange) {
        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        try {
            int userId = Integer.parseInt(cookies.get("userId"));
            Employee user = userService.getUserById(userId);
            List<Book> bookOnHand = userService.getBooksOnHandByUserId(userId);
            List<Book> historyBooks = userService.getJournalBooksByUserId(userId);
            ProfileDataModel authorized = new ProfileDataModel(user, bookOnHand, historyBooks);
            renderTemplate(exchange, "profile.ftlh", authorized);
        } catch (NumberFormatException e) {
            redirect303(exchange, "templates/profileError.html");
        }
    }

    private void registrationPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = FileUtil.parseUrlEncoded(raw, "&");
        if (userService.checkRegisteredUser(parsed) &&  userService.checkEmailPassword(parsed)) {
            userService.handleUser(parsed);
            redirect303(exchange, "/login");
        } else {
            registerErr(exchange);
        }
    }

    private void loginPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = FileUtil.parseUrlEncoded(raw, "&");
        boolean checkAuth = userService.checkAuthorizedUser(parsed);
        if (checkAuth) {
            Employee user = userService.getUserById(userService.authorizedUserId(parsed));

            Cookie cookie = Cookie.make("userId", user.getId());
            cookie.setMaxAge(tenMinute);
            cookie.setHttpOnly(true);
            setCookie(exchange, cookie);

            redirect303(exchange, "/profile");
        } else {
            authorisationErr(exchange);
        }
    }

    private void registrationGet(HttpExchange exchange) {
//        Cookie deleteCookie = Cookie.make("userId", ""); // Empty value
//        deleteCookie.setMaxAge(0); // Expire immediately
//        deleteCookie.setHttpOnly(true);
//        setCookie(exchange, deleteCookie);
        Path path = makeFilePath("login/register.ftlh");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login/login.ftlh");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void takeBookGet(HttpExchange exchange) {
        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        if (cookies.containsKey("userId")) {
            renderTemplate(exchange, "takeBook.ftlh", getDataModel());
        } else {
            redirect303(exchange, "templates/profileError.html");
        }
    }


    private void takeBookPost(HttpExchange exchange) {
        Map<String, String> parsed = FileUtil.parseUrlEncoded(getBody(exchange), "&");
        Map<String, String> cookies = Cookie.parse(getCookies(exchange));
        if (!authorizedUser(exchange).checkUserHand()){
            redirect303(exchange, "templates/profileError.html");
            return;
        }

        if (cookies.containsKey("userId")) {
            if (bookService.checkIsBookFree(parsed)){
                bookService.handleBook(Integer.parseInt(parsed.get("bookId")),Integer.parseInt(cookies.get("userId")));
                redirect303(exchange, "/journal");
            }else {
                redirect303(exchange,"/takeBook");
            }

        } else {
            redirect303(exchange, "templates/profileError.html");
        }
    }

    private ProfileDataModel authorizedUser(HttpExchange exchange){
        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        int userId = Integer.parseInt(cookies.get("userId"));
        Employee user = userService.getUserById(userId);
        List<Book> bookOnHand = userService.getBooksOnHandByUserId(userId);
        List<Book> historyBooks = userService.getJournalBooksByUserId(userId);
        ProfileDataModel authorized = new ProfileDataModel(user, bookOnHand, historyBooks);
        return authorized;
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
