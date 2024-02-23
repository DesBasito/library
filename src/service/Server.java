package service;

import com.sun.net.httpserver.HttpExchange;
import server.BasicServer;
import server.ContentType;
import server.ResponseCodes;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Server extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    public Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/books",this::booksHandler);
        registerGet("/employees",this::employeesHandler);
        registerGet("/journal",this::journalHandler);
        registerGet("/about",this::aboutBook);
        registerGet("/info",this::employeeInfo);
    }

    private void booksHandler(HttpExchange exchange) {
        renderTemplate(exchange,"books.ftlh",getDataModel());
    }

    private void employeesHandler(HttpExchange exchange){
        renderTemplate(exchange,"employees.ftlh",getDataModel());
    }

    private void journalHandler(HttpExchange exchange){
        renderTemplate(exchange,"journal.ftlh",getDataModel());
    }

    private void aboutBook(HttpExchange exchange){
        renderTemplate(exchange,"about.ftlh",getDataModel());
    }

    private void employeeInfo(HttpExchange exchange) {
        renderTemplate(exchange,"info.ftlh",getDataModel());
    }

    private DataModel getDataModel()  {
        return new DataModel();
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
