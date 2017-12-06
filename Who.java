package sample;//raist on 09.11.2017

class Who {
    static void work(long chat_id){
        int rnd = (int) (Math.random()*5);
        System.out.println(rnd);
        switch (rnd){
            case 0: TelegramMethods.sendMessage(chat_id, "На заявку отправится явно не Дэн I");
                break;
            case 1: TelegramMethods.sendMessage(chat_id, "Маловероятно, что на заявку отправится Дэн II");
                break;
            case 2: TelegramMethods.sendMessage(chat_id, "Наверное на заявку отправится Дэн III");
                break;
            case 3: TelegramMethods.sendMessage(chat_id, "Возможно поработать придётся Семёну");
                break;
            case 4: TelegramMethods.sendMessage(chat_id, "С великой радостью отправится на заявку Колян!");
                break;
        }
    }
}
