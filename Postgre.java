package sample;//raist on 09.11.2017

import java.sql.*;

public class Postgre {

    static Statement statement = null;

    static void DBconnect(Settings cfg){

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");

            connection = DriverManager.getConnection("jdbc:postgresql://" + cfg.getIp() + "/" + cfg.getBdname(), cfg.getLogin(), cfg.getPswd());
            System.out.println("Соединение установлено");

            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
            statement = null;
        }
    }

    static void writeMsg(long chat_id, long user_id, String name, String text){
        if(!text.equals(null) && statement != null) {
            try {
                statement.executeUpdate("INSERT INTO messages (chat_id, id_tele, name, text) VALUES ('" + chat_id + "', '" + user_id + "', '" + name + "', '" + text + "');");

            } catch (SQLException e) {
                System.out.println("Не схоронил =(");
                e.printStackTrace();
            }
        }
    }

    static Hero getHero(long user_id) {
        Hero hero = new Hero();
        try {
            ResultSet sqlResult = statement.executeQuery("SELECT * FROM heroes WHERE user_id = '" + user_id + "';");
            if(sqlResult.next()) {
                    //заполняем героя с ответа от бд
                    hero.setLevel(sqlResult.getInt("level"));
                    hero.setKills(sqlResult.getInt("kills"));
                    hero.setDeaths(sqlResult.getInt("deaths"));
                    hero.setDraws(sqlResult.getInt("draws"));
                    hero.setAttempts(sqlResult.getInt("attempts"));
                    hero.setAll_battles(sqlResult.getInt("all_battles"));
                    hero.setBoss_kills(sqlResult.getInt("boss_kills"));
                    hero.setName(sqlResult.getString("name"));
            }else{
                hero.setNewHero(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hero;
    }
}
