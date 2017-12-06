package sample;

import java.sql.SQLException;

class Commands {
    static void checkCommand(Message msg, String name){
        long chat_id = msg.getChat().getId();
        long message_id = msg.getMessage_id();
        long user_id = msg.getFrom().getId();
        String username = msg.getFrom().getUsername();
        String text = msg.getText().toLowerCase();
        Message reply = msg.getReply_to_message();
        boolean triggered = false;
        System.out.println(msg.getChat().getId() + " | " + name + ": " + msg.getText());
        Postgre.writeMsg(chat_id, user_id, name, text.replaceAll("'", ""));

        if(chat_id == -1001091731443L && text.startsWith("/game")){
            //TelegramMethods.sendSticker(chat_id, "CAADAgAD_wADFnxoAwbXtGNHGAyKAg");
        }


        if(chat_id != -91001091731443L) {

            //проверка на наличие команд в репли, если есть, то не отправляется репли, а делается команда
            if(reply != null && !text.startsWith("!") && text.charAt(0) != '/'){
                triggered = true;
                if(reply.getFrom().getId() == 384644516){
                    TelegramMethods.sendReply(chat_id, message_id, Answer.answer());
                }
            }
            if(text.startsWith("!д") && chat_id != -1001091731443L){
                Dice.throwDice(chat_id, message_id, name, text);
            }
            if(text.contains("!кубик") && chat_id != -1001091731443L){
                TelegramMethods.sendBtnMessage(chat_id, "Я тут вам, мау, кубиков принёс");
            }
            if(text.contains("!прибраться")){
                TelegramMethods.removeKeyboard(chat_id, "<b>*Пепяка</b> прибрал кубики к лапкам<b>*</b>");
            }
            if (text.startsWith("!м ")){
                //только ползователь 82177479 имеет право отправлять эту команду
                if (user_id == 82177479){
                    TelegramMethods.delMessage(chat_id, message_id);
                    TelegramMethods.sendMessage(chat_id, "<b>" + text.substring(3) + "</b>");
                }else{
                    TelegramMethods.sendMessage(chat_id, name + ", ты еще слишком мау для таких развлечений");
                }
            }
            if(text.startsWith("!мну")){
                TelegramMethods.delMessage(chat_id, message_id);
                text = "<b>*" + name + "</b>" + text.substring(4) + "<b>*</b>";
                TelegramMethods.sendMessage(chat_id, text);
            }
            if(text.startsWith("/game")){
                Gamez.prepareThread(chat_id, message_id, user_id, name, username);
            }
            if(text.startsWith("/stat")){
                Hero.getStat(chat_id, message_id, user_id);
            }

            if(text.startsWith("/me") && chat_id != -1001091731443L){
                TelegramMethods.sendMessage(chat_id, name + ", ты меня с кем-то путаешь.");
            }
            if(text.contains("кто") && text.contains("заяв")){
                Who.work(chat_id);
            }
            if(text.contains("пепя") && !triggered){
                TelegramMethods.sendReply(chat_id, message_id, Answer.answer());
            }
            if(text.startsWith("!п ") && user_id == 111304154){
                System.out.println("Пепяка: " + text.substring(3));
                TelegramMethods.sendMessage(460655515L, text.substring(3));
            }
            if(text.startsWith("!выйти ") && user_id == 111304154){
                System.out.println("Выход из " + text.substring(7));
                TelegramMethods.sendMessage(chat_id, "Как-то стрёмно тут у вас");
                TelegramMethods.leaveChat(text.substring(7));
            }
            if(text.startsWith("!читай") && user_id == 111304154){
                try {
                    FileToBD.toBD();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(text.startsWith("!стоп") &&  user_id == 111304154){
                Update.setState(false);
            }
            if(text.startsWith("!тест")){

            }
        }
    }
}
