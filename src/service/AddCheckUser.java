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

public class AddCheckUser {
    private String raw;
    private Map<String, String> parsed;

    public AddCheckUser(HttpExchange exchange) {
        this.raw = getBody(exchange);
        this.parsed = FileUtil.parseUrlEncoded(raw, "&");
    }

    public void newUser(HttpExchange ex) {
        int n = 0;
        DataModel model = new DataModel();
        n = model.getEmployees().size()+1;
        String birth = parsed.get("birthdate");
        String position = parsed.getOrDefault("position", null);
        String hobby = parsed.getOrDefault("hobby", null);
        try {
            for (Employee em : model.getEmployees()){
                if (em.getEmail().equalsIgnoreCase(parsed.get("email"))){
                    throw new IOException();
                }
            }
        }catch (IOException e){
            BasicServer.registrErr(ex);
            return;
        }


        Employee emp = new Employee(parsed.get("firstName"), parsed.get("lastName"), 0, n, position, hobby, birthDate(birth), parsed.get("phone"), parsed.get("email"), parsed.get("password"));
        List<Employee> employeeList = model.getEmployees();
        employeeList.add(emp);
        model.setEmployees(employeeList);
        FileUtil.writeFile(model.getEmployees());
    }

    private Date birthDate(String birth){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date birthDate = null;
        try {
        Date date = inputFormat.parse(birth);
        String formattedDate = outputFormat.format(date);
        birthDate = outputFormat.parse(formattedDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return birthDate;
    }

    public void checkUser(HttpExchange exchange) {

    }
}
