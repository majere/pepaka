package sample;//raist on 27.11.2017

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

public class FileToBD {
    public static void toBD() throws SQLException {
        String path = "C:\\Users\\serya\\OneDrive\\java\\pepyakabot\\word_rus1.txt";
        ArrayList<String> array = new ArrayList<String>();
        try {
            Files.lines(Paths.get(path), StandardCharsets.UTF_8).forEach(line -> array.add(line));
            int count = 0;
            for (String anArray : array) {
                if(!anArray.endsWith("у") && !anArray.endsWith("е") && !anArray.endsWith("ы") && !anArray.endsWith("а") && !anArray.endsWith("о") && !anArray.endsWith("э") && !anArray.endsWith("я") && !anArray.endsWith("и") && !anArray.endsWith("ю") && !anArray.endsWith("ё") && !anArray.endsWith("ь")){
                    count++;
                    System.out.println(count + " " + anArray);
                    Postgre.statement.executeUpdate("INSERT INTO noun_male (noun_male) VALUES ('" + anArray + "');");
                }

                //Postgre.statement.executeUpdate("INSERT INTO noun_male (noun_male) VALUES ('" + anArray + "');");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
