package sample;//raist on 30.11.2017

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class Gamez {

    //объявление переменных, из которые будут переданы в поток
    static long prepChat_id;
    static long prepUser_id;
    static long prepMessage_id;
    static String prepName;
    static String prepUsername;

    //переменная для получения ответа от кнопок
    static CallbackQuery callbackQuery;

    //методы для синхронизации переменных CallbackQuery
    private static synchronized CallbackQuery getSyncCQ(){
        return callbackQuery;
    }
    private static synchronized void setSyncCQ(CallbackQuery cq){
        callbackQuery = cq;
    }

    //метод заполняет переменные, которые будут использованы в отдельном потоке и запускает новый поток
    static void prepareThread(long chat_id, long message_id, long user_id, String name, String username){

        //определенние переменных
        prepChat_id = chat_id;
        prepMessage_id = message_id;
        prepUser_id = user_id;
        prepName = name;
        prepUsername = username;

        //запуск отдельного потока
        NewGameThread gameThread = new NewGameThread();
        gameThread.start();
    }

    //старт отдельного треда
    static class NewGameThread extends Thread{
        @Override public void run(){
            try {
                //создание объекта героя
                Hero hero = new Hero();
                hero.setChat_id(prepChat_id);
                hero.setMessage_id(prepMessage_id);
                hero.setUser_id(prepUser_id);
                hero.setName(prepName);
                hero.setUsername(prepUsername);
                hero.setNewHero(true);

                //проверка, создан ли герой
                hero = checkHero(hero);

                //Если это новый герой
                if(hero.isNewHero()){
                    //создание нового героя
                    System.out.println("Это новый герой.");
                    createHero(hero);

                }else{
                    //уже есть герой, старт игры
                    System.out.println("Герой уже существует");
                    startGame(hero);
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ошибка в потоке игры");
            }
        }
    }

    //проверка есть ли герой в бд
    static Hero checkHero(Hero hero){
        try {
            //зпрос в бд
            ResultSet sqlResult = Postgre.statement.executeQuery("SELECT * FROM heroes WHERE user_id = '" + hero.getUser_id() + "';");

            //если пришел не пустой ответ
            if(sqlResult.next()){

                //заполнение из бд
                hero.setLevel(sqlResult.getInt("level"));
                hero.setKills(sqlResult.getInt("kills"));
                hero.setDeaths(sqlResult.getInt("deaths"));
                hero.setDraws(sqlResult.getInt("draws"));
                hero.setAttempts(sqlResult.getInt("attempts"));
                hero.setAll_battles(sqlResult.getInt("all_battles"));
                hero.setDead(sqlResult.getBoolean("dead"));
                hero.setDate(sqlResult.getInt("date"));
                //отметка, что герой уже создан
                hero.setNewHero(false);

            }else{
                //если нет в бд, то говорим, что герой не создан
                hero.setNewHero(true);
            }

        } catch (SQLException e) {
            //пишем лог в случае ошибки
            System.out.println("Ошибка проверки наличия героя в БД");
            e.printStackTrace();
        }

        return hero;
    }

    //создание нового героя
    static void createHero(Hero hero) throws InterruptedException {
        System.out.println("Создание героя.");
        String text = "Мау, <b>"+hero.getName()+"</b>! Нет времени объяснять! Кем ты будешь?";
        String button = "[" + TeleButtons.crtButton("Котик", "/cat") + "," + TeleButtons.crtButton("Не котик", "/noncat") + "]";

        //отправляется сообщение
        String jsonAnswer = TelegramMethods.sendInlineBtn(hero.getChat_id(), text, button);
        AnswerResult answerObj = JsonConverter.toObj(jsonAnswer);

        //запись message_id ответа бота, чтобы в будущем править это сообщение
        hero.setAnswer_message_id(answerObj.getResult().getMessage_id());

        int count = 0;
        setSyncCQ(null);
        hero.setAnswered(false);
        //начинаем ждать ответа реакции на кнопку
        while(!hero.isAnswered()){
            count++;
            if(getSyncCQ() != null && getSyncCQ().getFrom().getId() == hero.getUser_id() && getSyncCQ().getMessage().getMessage_id() == hero.getAnswer_message_id()){

                //определение выбраного класса
                String hero_class = getSyncCQ().getData();
                System.out.println(hero.getName() + " нажал " + hero_class);
                switch(hero_class){
                    case "/cat":
                        hero.setHero_class("котик");
                        hero.setAnswered(true);
                        break;
                    case "/noncat":
                        hero.setHero_class("не котик");
                        hero.setAnswered(true);
                        break;
                }

                text = "Отлично, <b>" + hero.getHero_class() + "</b>. Теперь победи Злое Арбузло!";
                TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(), text, null);

                //запись нового героя в бд
                try {
                    Postgre.statement.executeUpdate("INSERT INTO heroes (user_id, username, name, class) VALUES ('"+hero.getUser_id()+"', '"+hero.getUsername()+"', '"+hero.getName()+"', '"+hero.getHero_class()+"');");
                } catch (SQLException e) {
                    System.out.println("Не удалось записать героя в бд");
                    e.printStackTrace();
                }

                //переходим к процессу игры
                startGame(hero);
            }

            Thread.sleep(10);
            //если долго нет ответа, то выходим из цикла
            if(count > 4000){
                hero.setAnswered(true);
                System.out.println("Слишком долго небыло ответа");
                TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(), "<b>*Пепяка</b> убежал защищать своё королевство от шуршащих фантиков<b>*</b>", null);
            }
        }
    }

    //старт игрового процесса
    static void startGame(Hero hero) {
        System.out.println("Старт игры");

        //устанавливаем текущую дату
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("MMddHHmm");
        int currentDate = Integer.parseInt(formatForDateNow.format(dateNow));

        if((currentDate - hero.getDate()) < 10){
            System.out.println("герой мёртв");
            hero.setDead(true);
            Hero.updateDead(hero);
        }else{
            System.out.println("герой жив");
            hero.setDead(false);
            Hero.updateDead(hero);
        }
        //если герой жив
        if(!hero.isDead()) {
            String text = "Ну что, расхититель лотка, что делать будешь?";
            String btn1 = TeleButtons.crtButton("Кусь", "/fight");
            String btn2 = TeleButtons.crtButton("Инфо", "/stat");
            String btn3 = TeleButtons.crtButton("Бить Злое Арбузло", "/boss");
            String button = "[" + btn1 + "," + btn2 + "],[" + btn3 + "]";

            //отправка сообщения для запоминания его номера
            String jsonAnswer = TelegramMethods.sendInlineBtn(hero.getChat_id(), text, button);
            AnswerResult answerObj = JsonConverter.toObj(jsonAnswer);

            //запись message_id ответа бота, чтобы в будущем править это сообщение
            hero.setAnswer_message_id(answerObj.getResult().getMessage_id());

            //ожидание нажатия кнопок действия
            int count = 0;
            setSyncCQ(null);
            hero.setAnswered(false);
            while(!hero.isAnswered()){
                count++;
                if(getSyncCQ() != null && getSyncCQ().getFrom().getId() == hero.getUser_id() && getSyncCQ().getMessage().getMessage_id() == hero.getAnswer_message_id()){

                    //определение нажатой кнопки
                    String hero_class = getSyncCQ().getData();
                    System.out.println(hero.getName() + " нажал " + hero_class);

                    switch(hero_class){
                        case "/fight":
                            fight(hero);
                            hero.setAnswered(true);
                            break;
                        case "/stat":
                            text = "<b>Кусь</b> - поиск противника и сражение с ним.\r\n<b>Бить Злое Арбузло</b> - сражение с главным боссом. Могут осилить только самые сильные игроки.\r\nЦель игры - получить левел поболее сражась с разными врагами.";
                            TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(), text, null);
                            hero.setAnswered(true);
                            break;
                        case "/boss":
                            boss(hero);
                            hero.setAnswered(true);
                    }
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(count > 4000){
                    hero.setAnswered(true);
                    System.out.println("Слишком долго небыло ответа");
                    TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(), "<b>*Пепяка</b> убежал защищать своё королевство от шуршащих фантиков<b>*</b>", null);
                }
            }

        }else{
            //если герой не жив
            int time = hero.getDate() - currentDate + 10;
            String min;
            switch (time){
                case 1:
                    min = "минуту";
                    break;
                case 2:
                case 3:
                case 4:
                    min = "минуты";
                    break;
                default:
                    min = "минут";
            }
            TelegramMethods.sendReply(hero.getChat_id(), hero.getMessage_id(), "<i>*стартельно закапывает лапкой мертвечину*</i>\r\nТо что мертво, должно оставаться мёртвым.\r\nХотя бы ещё " + time + " " + min + ".");
        }
    }

    //обработка кнопки кусь. рядовое сражение
    private static void fight(Hero hero){

        //генерация имени противника
        ResultSet result;
        String enemyAdj = null;
        String enemyNoun = null;
        try {
            result = Postgre.statement.executeQuery("SELECT adjective_male FROM adjective_male ORDER BY RANDOM() LIMIT 1;");
            while(result.next()){
                enemyAdj = result.getString(1);
            }
            result = Postgre.statement.executeQuery("SELECT noun_male FROM noun_male ORDER BY RANDOM() LIMIT 1;");
            while(result.next()){
                enemyNoun = result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        hero.setEnemy_name(enemyAdj + " " + enemyNoun);

        //генерация уровня врага
        hero.setEnemy_lvl(hero.getLevel());
        /*if(hero.getLevel() < 4){
            //если герой маленький
            hero.setEnemy_lvl(1);
        }else{
            int min = hero.getLevel()-3;
            int max = hero.getLevel()+3;
            max -= min;
            hero.setEnemy_lvl((int) (Math.random() * ++max) + min);
        }*/

        //пишим лог
        System.out.println("Враг " + hero.getEnemy_name() + " " + hero.getEnemy_lvl() + " уровень");

        //подсчёт разницы в уровне
        //int delta = hero.getLevel() - hero.getEnemy_lvl();

        //бросаем кубики
        int h = ((int) (Math.random() * 6)) + 1;
        int e = ((int) (Math.random() * 6)) + 1;
        System.out.println("h = " + h + ". e = " + e);

        //учитываем разцу в уровне
        //h = h + delta;
        //System.out.println("h + delta " + h);

        if(h > e){

            //победа
            System.out.println("Враг убит");
            hero.setKills(hero.getKills() + 1);
            String text = "Мау, <b>" + hero.getName() + "</b>! Ты, победил адепта Злого Арбузло <b>" + hero.getEnemy_name() + "</b>.";
            TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(), text, null);
            Hero.updateKills(hero);
            lvlUp(hero);

        }else if(e > h){

            //поражение
            System.out.println("Вы умерли");
            hero.setDeaths(hero.getDeaths() + 1);
            String text = "<b>" + hero.getName() + "</b>, ты так дрался c <b>" + hero.getEnemy_name() + "</b>, что аж помер.";
            TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(), text, null);

            //время смерти
            dieTime(hero);

        }else{

            //ничья
            System.out.println("Ничья");
            hero.setDraws(hero.getDraws() + 1);
            String text = "<b>" + hero.getName() + "</b>, вы с <b>" + hero.getEnemy_name() + "</b> долго сражались, но кому досталось больше непонятно.";
            TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(), text, null);
            Hero.updateDraws(hero);
        }
        hero.setAll_battles(hero.getAll_battles() + 1);
        Hero.updateAllBattles(hero);
    }

    private static void boss(Hero hero){

        //отправка сообщения битвы с кнопками
        String btn1 = TeleButtons.crtButton("Присоединиться к битве","/join_battle");
        String btn2 = TeleButtons.crtButton("Начать сражение", "/boss_fight");
        String buttons = btn1 + "," + btn2;
        String text = "В битве учавствуют:\r\n";
        int overall_level = 0;
        TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(),text, buttons);

        ArrayList<Hero> arrayHeroes = new ArrayList<>();

        //ожидание нажатий
        int count = 0;
        setSyncCQ(null);
        hero.setAnswered(false);
        //начинаем ждать ответа реакции на кнопку
        while(!hero.isAnswered()){
            count++;
            if(getSyncCQ() != null && getSyncCQ().getMessage().getMessage_id() == hero.getAnswer_message_id()){

                Hero currentHero;
                String command = getSyncCQ().getData();
                switch(command){

                    //если нажали присоедениться
                    case "/join_battle":
                        //запрос героя из бд, кто нажал
                        currentHero = Postgre.getHero(getSyncCQ().getFrom().getId());

                        //если героя нет в списке, добавляем его
                        if(!text.contains(currentHero.getName())){
                            text = text + "\n\r" + currentHero.getName();
                            TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(),text, buttons);
                            overall_level = overall_level + currentHero.getLevel();
                            arrayHeroes.add(currentHero);
                        }
                        setSyncCQ(null);
                        break;
                    //начало битвы с босом
                    case "/boss_fight":
                        System.out.println("драка с босом");
                        int delta = 100 - overall_level;
                        int rnd = ((int) (Math.random() * delta)) + 1;

                        //если боса убили, то обновляем счётчик
                        if (rnd <= 1) {
                            for (int i = 0; i < arrayHeroes.size(); i++) {
                                arrayHeroes.get(i).setBoss_kills(arrayHeroes.get(i).getBoss_kills() + 1);
                                arrayHeroes.get(i).setAll_battles(arrayHeroes.get(i).getAll_battles() + 1);
                                Hero.updateBossKills(arrayHeroes.get(i));
                                Hero.updateKills(arrayHeroes.get(i));
                            }
                            text = "Злое Арбузло в панике бежит";
                            TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(),text, null);

                        } else {
                            for (int i = 0; i < arrayHeroes.size(); i++){
                                text = "Генерал Пепяка крайне недоволен вами. Вас раскидали аки маленьких котят!";
                                arrayHeroes.get(i).setAll_battles(arrayHeroes.get(i).getAll_battles() + 1);
                                arrayHeroes.get(i).setDeaths(arrayHeroes.get(i).getDeaths() + 1);
                                Hero.updateDeaths(arrayHeroes.get(i));
                                Hero.updateAllBattles(arrayHeroes.get(i));
                                dieTime(arrayHeroes.get(i));
                                System.out.println("№ " + i + " " + arrayHeroes.get(i).getName());
                                System.out.println("lvl " + arrayHeroes.get(i).getLevel());
                                hero.setAnswered(true);
                            }
                            TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(), text, null);
                        }
                        setSyncCQ(null);
                        break;
                }

            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count > 4000){
                hero.setAnswered(true);
                System.out.println("Слишком долго небыло ответа");
                TelegramMethods.editMessageText(hero.getChat_id(), hero.getAnswer_message_id(), "<b>*Пепяка</b> убежал защищать своё королевство от шуршащих фантиков<b>*</b>", null);
            }
        }
    }

    //вычесление получил ли уровень
    private static void lvlUp(Hero hero){

        //получаем рандомное число от ноля до уровня героя
        int random = ((int) (Math.random() * hero.getLevel())) + 1;

        //если выпало меньше, чем число удачных попыток, тогда апаем уровень
        if(random <= hero.getAttempts()){
            hero.setLevel(hero.getLevel()+1);
            String text = "<b>*</b>Генерал Пепяка цалует " + hero.getName() + " в обе щОки и дарует ему <b>" + hero.getLevel() + "</b> уровень<b>*</b>";
            TelegramMethods.sendMessage(hero.getChat_id(), text);
            Hero.updateLvl(hero);
            hero.setAttempts(1);
            Hero.updateAttempts(hero);
        }else{
            hero.setAttempts(hero.getAttempts() + 1);
            Hero.updateAttempts(hero);
        }
    }

    //фиксация времени смерти
    private static void dieTime(Hero hero){
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("MMddHHmm");
        hero.setDate(Integer.parseInt(formatForDateNow.format(dateNow)));
        Hero.updateDate(hero);
        Hero.updateDeaths(hero);
    }
}
