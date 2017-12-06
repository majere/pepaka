package sample;


import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;

class Hero {
    @Getter @Setter long user_id;
    @Getter @Setter long chat_id;
    @Getter @Setter long message_id;
    @Getter @Setter long answer_message_id;
    @Getter @Setter int level;
    @Getter @Setter int kills;
    @Getter @Setter int deaths;
    @Getter @Setter int draws;
    @Getter @Setter int attempts;
    @Getter @Setter int all_battles;
    @Getter @Setter int boss_kills;
    @Getter @Setter String name;
    @Getter @Setter String username;
    @Getter @Setter String hero_class;
    @Getter @Setter String enemy_name;
    @Getter @Setter int enemy_lvl;
    @Getter @Setter boolean newHero;
    @Getter @Setter boolean answered;
    @Getter @Setter boolean dead;
    @Getter @Setter int date;

    static void getStat(long chat_id, long message_id, long user_id){
        Hero hero = Postgre.getHero(user_id);
        if(hero.getLevel() > 0) {
            String text = "Уровень: " + hero.getLevel() + "\r\n****\r\nПобед: " + hero.getKills() + "\r\nПоражений: " + hero.getDeaths() + "\r\nНичьих: " + hero.getDraws() + "\r\n****\r\nВсего сражений: " + hero.getAll_battles();
            TelegramMethods.sendReply(chat_id, message_id, text);
        }else{
            String text = "Ты тыкаешь /stat, но делаешь это без уважения. Поиграй кота сначала /game@pepakaBot";
            TelegramMethods.sendReply(chat_id, message_id, text);
        }
    }

    static void updateLvl(Hero hero){
        try {
            Postgre.statement.executeUpdate("UPDATE heroes SET \"level\"=" + hero.getLevel() + " WHERE user_id=" + hero.getUser_id() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateKills(Hero hero){
        try {
            System.out.println("Убийств " + hero.getKills());
            Postgre.statement.executeUpdate("UPDATE heroes SET \"kills\"=" + hero.getKills() + " WHERE user_id=" + hero.getUser_id() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateDeaths(Hero hero){
        try {
            Postgre.statement.executeUpdate("UPDATE heroes SET \"deaths\"=" + hero.getDeaths() + " WHERE user_id=" + hero.getUser_id() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateDraws(Hero hero){
        try {
            Postgre.statement.executeUpdate("UPDATE heroes SET \"draws\"=" + hero.getDraws() + " WHERE user_id=" + hero.getUser_id() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateAttempts(Hero hero){
        try {
            Postgre.statement.executeUpdate("UPDATE heroes SET \"attempts\"=" + hero.getAttempts() + " WHERE user_id=" + hero.getUser_id() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateAllBattles(Hero hero){
        try {
            Postgre.statement.executeUpdate("UPDATE heroes SET \"all_battles\"=" + hero.getAll_battles() + " WHERE user_id=" + hero.getUser_id() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateBossKills(Hero hero){
        try {
            Postgre.statement.executeUpdate("UPDATE heroes SET \"boss_kills\"=" + hero.getBoss_kills() + " WHERE user_id=" + hero.getUser_id() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateDead(Hero hero){
        try {
            Postgre.statement.executeUpdate("UPDATE heroes SET \"dead\"=" + hero.isDead() + " WHERE user_id=" + hero.getUser_id() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateDate(Hero hero){
        try {
            Postgre.statement.executeUpdate("UPDATE heroes SET \"date\"=" + hero.getDate() + " WHERE user_id=" + hero.getUser_id() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
