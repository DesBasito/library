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
        registerGet("/books/about", this::aboutBook);
        registerGet("/employees/info", this::employeeInfo);
        // lesson 45-46
        registerGet("/register", this::registrationGet);
        registerPost("/register", this::registrationPost);
        registerGet("/login", this::loginGet);
        registerGet("/logout", this::logoutGet);
        registerPost("/login", this::loginPost);
        registerPost("/logout", this::logoutPost);
        registerGet("/profile", this::profileGet);
        registerGet("/takeBook", this::takeBookGet);
        registerGet("/giveBack", this::giveBackGet);
        registerPost("/takeBook", this::takeBookPost);
        registerPost("/giveBack", this::giveBackPost);
        registerGet("/notification", this::notificationGet);
    }

    private void notificationGet(HttpExchange exchange) {
        renderTemplate(exchange, "authWarning.ftlh", null);
    }

    private void logoutGet(HttpExchange exchange) {
        try {
//            checkIsCookiePresent(exchange);
            renderTemplate(exchange, "exit.ftlh", authorizedUser(exchange));
        } catch (NumberFormatException e) {
            redirect303(exchange, "/login");
        }
    }

    private void logoutPost(HttpExchange exchange) {
        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        if (cookies.containsKey("userId")) {
            Cookie deleteCookie = Cookie.make("userId", "");
            deleteCookie.setMaxAge(0);
            deleteCookie.setHttpOnly(true);
            setCookie(exchange, deleteCookie);
            redirect303(exchange, "/login");
        } else {
            registerErr(exchange);
        }
    }

    private void booksHandler(HttpExchange exchange) {
        renderTemplate(exchange, "books.ftlh", getModel());
    }

    private void employeesHandler(HttpExchange exchange) {
        renderTemplate(exchange, "employees.ftlh", getModel());
    }

    private void journalHandler(HttpExchange exchange) {
        renderTemplate(exchange, "journal.ftlh", getModel());
    }

    private void aboutBook(HttpExchange exchange) {
        String query = getQueryParams(exchange);
        Map<String, String> params = FileUtil.parseUrlEncoded(query, "&");
        int id = Integer.parseInt(params.getOrDefault("id", null));
        try {
            Book book = FileUtil.readBook().stream()
                    .filter(e -> e.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new NumberFormatException("Book not found!"));
            Map<String, Object> data = new HashMap<>();
            data.put("book", book);
            data.put("employee", FileUtil.readEmployee());
            data.put("journal", FileUtil.readJournal());
            renderTemplate(exchange, "about.ftlh", data);
        } catch (NumberFormatException e) {
            redirect303(exchange, "/books");
        }

    }

    private void employeeInfo(HttpExchange exchange) {
        if (getCookies(exchange).contains("userId")) {
            String query = getQueryParams(exchange);
            Map<String, String> params = FileUtil.parseUrlEncoded(query, "&");
            int id = Integer.parseInt(params.getOrDefault("id", null));
            Optional<Employee> employees = FileUtil.readEmployee().stream()
                    .filter(e -> e.getId() == id).findFirst();
            if (employees.isEmpty()) {
                redirect303(exchange, "/employees");
                return;
            }
            Employee employee = employees.get();
            Map<String, Object> data = new HashMap<>();
            data.put("employee", employee);
            data.put("journals", userService.getJournalBooksByUserId(employee.getId()));
            data.put("books", userService.getBooksOnHandByUserId(employee.getId()));
            renderTemplate(exchange, "info.ftlh", data);
        } else {
            redirect303(exchange, "/notification");
        }
    }

    private Map<String, Object> getModel() {
        Map<String, Object> model = new HashMap<>();
        model.put("employees", FileUtil.readEmployee());
        model.put("books", FileUtil.readBook());
        model.put("journals", FileUtil.readJournal());
        return model;
    }

    private void profileGet(HttpExchange exchange) {
        try {
            ProfileDataModel user = authorizedUser(exchange);
            renderTemplate(exchange, "profile.ftlh", user);
        } catch (NumberFormatException e) {
            redirect303(exchange, "/notification");
        }
    }

    private void registrationPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = FileUtil.parseUrlEncoded(raw, "&");
        if (userService.checkRegisteredUser(parsed) && userService.checkEmailPassword(parsed)) {
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
            var model = getModel();
            int readingBooksCount = authorizedUser(exchange).getReadingBooks().size();
            boolean hasReadingBooks = readingBooksCount > 1;
            model.put("readingBooks", hasReadingBooks);
            renderTemplate(exchange, "takeBook.ftlh", model);
        } else {
            redirect303(exchange, "/notification");
        }
    }

    private void giveBackGet(HttpExchange exchange) {
        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        if (cookies.containsKey("userId")) {
            var model = new HashMap<>();
            var user = authorizedUser(exchange);
            int readingBooksCount = user.getReadingBooks().size();
            boolean hasReadingBooks = readingBooksCount < 1;
            model.put("readingBooks", hasReadingBooks);
            model.put("user", user);
            renderTemplate(exchange, "giveBack.ftlh", model);
        } else {
            redirect303(exchange, "/notification");
        }
    }


    private void takeBookPost(HttpExchange exchange) {
        try {
            Map<String, String> parsed = FileUtil.parseUrlEncoded(getBody(exchange), "&");
            ProfileDataModel user = authorizedUser(exchange);
            if (userService.getEmployees().contains(user.getUser())) {
                if (bookService.checkIsBookFree(parsed) && user.getReadingBooks().size() < 2) {
                    bookService.handleBook(Integer.parseInt(parsed.get("bookId")), user.getUser().getId());
                    ProfileDataModel modifiedUser = authorizedUser(exchange);
                    renderTemplate(exchange, "profile.ftlh", modifiedUser);
                }
            } else {
                redirect303(exchange, "/notification");
            }
        } catch (NumberFormatException e) {
            redirect303(exchange, "/takeBook");
        }
    }

    private void giveBackPost(HttpExchange exchange) {
        try {
            Map<String, String> parsed = FileUtil.parseUrlEncoded(getBody(exchange), "&");
            ProfileDataModel user = authorizedUser(exchange);
            int bookId = Integer.parseInt(parsed.get("bookId"));
            if (userService.getEmployees().contains(user.getUser())) {
                bookService.returnBook(bookId);
                redirect303(exchange, "/profile");
            } else {
                redirect303(exchange, "/notification");
            }

        } catch (NumberFormatException e) {
            redirect303(exchange, "/giveBack");
        }
    }

    private ProfileDataModel authorizedUser(HttpExchange exchange) {
        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        int userId = Integer.parseInt(cookies.get("userId"));
        Employee user = userService.getUserById(userId);
        Set<Book> bookOnHand = userService.getJournalBooksByUserId(userId);
        List<Book> historyBooks = userService.getBooksOnHandByUserId(userId);
        return new ProfileDataModel(user, bookOnHand, historyBooks);
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
