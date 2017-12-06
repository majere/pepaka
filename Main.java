package sample;

public class Main {

    public static void main(String[] args) {

        //Получение настроек
        Settings cfg = Settings.chkCfg();

        TelegramMethods.setTelegramUrl("https://api.telegram.org/bot" + cfg.getToken());

        //соединение с бд
        Postgre.DBconnect(cfg);

        //запуск проверки новых сообщений
        try {
            Update.getNewMessage();
        } catch (Exception e) {
            System.out.println("Ошибка запуска Update.getNewMessage()");
            e.printStackTrace();
        }
    }
}
