import lesson.Lesson44Server;
import java.io.*;


public class Main {

    public static void main(String[] args) {
       try {
           new Lesson44Server("localhost",9999).start();
       }catch (IOException e){
           e.printStackTrace();
       }
    }


}