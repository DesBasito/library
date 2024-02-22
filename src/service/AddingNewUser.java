package service;

import com.sun.net.httpserver.HttpExchange;
import entities.Employee;
import server.BasicServer;
import util.FileUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static server.BasicServer.getBody;
import static server.BasicServer.getContentType;

public class AddingNewUser {
    private String raw;
    private Map<String, String> parsed;

    public AddingNewUser(HttpExchange exchange) {
        this.raw = getBody(exchange);
        this.parsed = FileUtil.parseUrlEncoded(raw, "&");
    }

    public void newUser( HttpExchange exchange) throws IOException{
        int n = 0;
        try {
            DataModel model = new DataModel();
            n = model.getEmployees().size();
            String birth = parsed.get("birthdate");


            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = inputFormat.parse(birth);
            String formattedDate = outputFormat.format(date);
            Date birthDate = outputFormat.parse(formattedDate);

            String position = parsed.getOrDefault("position", null);
            String hobby = parsed.getOrDefault("hobby", null);


            for (Employee em : model.getEmployees()){
                if (em.getEmail().equalsIgnoreCase(parsed.get("email"))){
                    throw new IOException();
                }
            }

            Employee emp = new Employee(parsed.get("firstName"), parsed.get("lastName"), 0, n + 1, position, hobby, birthDate, parsed.get("phone"), parsed.get("email"), parsed.get("password"));
            List<Employee> employeeList = model.getEmployees();
            employeeList.add(emp);
            model.setEmployees(employeeList);
            FileUtil.writeFile(model.getEmployees());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
