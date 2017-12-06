package sample;

/**
 * Created by raist on 09.11.2017.
 */
public class Dice {
    static void throwDice(long chat_id, long message_id, String name, String text){
        int dice;
        try{
            dice = Integer.parseInt(text.substring(2));
            if (dice > 0){
                int random = ((int) (Math.random() * dice)) + 1;
                String rand = Integer.toString(random);
                TelegramMethods.delMessage(chat_id, message_id);
                TelegramMethods.sendMessage(chat_id, "<b>" + name + "</b> выбросил <b>" + rand + " из "+dice+"</b>.");
                if (random == 1){
                    TelegramMethods.sendMessage(chat_id, "Ха-ха! Етить ты маубедитель!");
                }
            }else{
                TelegramMethods.sendMessage(chat_id, "Кажется я проглотил твой кубик, <b>" + name + "</b>");
            }

        }catch(Exception e){
            TelegramMethods.sendMessage(chat_id, "<b>" + name + "</b> зашвырнул кубик за грань реальности");
        }
    }
}
