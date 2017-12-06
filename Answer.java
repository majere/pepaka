package sample;//raist on 11.11.2017

import java.sql.ResultSet;
import java.sql.SQLException;

public class Answer {
    static String answer(){

        boolean messOk = false;
        String message = null;

        while(!messOk) {
            try {
                ResultSet count = Postgre.statement.executeQuery("SELECT text FROM messages ORDER BY RANDOM() LIMIT 1;");
                    while (count.next()) {
                        message = count.getString(1);
                    }

                if(!message.startsWith("!") && !message.equals(null) && !message.startsWith("htt") && !message.startsWith("/")){
                    messOk = true;
                }
                System.out.println("Пепяка: " + message.trim());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return message;
    }
}
