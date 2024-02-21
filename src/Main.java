import Service.Lesson44Server;
import java.io.*;


public class Main {

    public static void main(String[] args) {
       try {
           new Lesson44Server("localhost",8999).start();
       }catch (IOException e){
           e.printStackTrace();
       }
    }


}