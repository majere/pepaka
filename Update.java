package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

class Update  {

    @Getter @Setter static boolean state = true;

    static void getNewMessage() throws Exception{
        Update state = new Update();
        String answer;
        while(state.isState()){
            answer = TelegramMethods.getUpdates();
            if (answer != null && answer.contains("update_id")){

                System.out.println(answer);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Obj json = gson.fromJson(answer, Obj.class);
                int last = json.getResult().size()-1;
                TelegramMethods.setOffset(json.getResult().get(last).getUpdate_id()+1);

                for(int i=0; i<=last; i++){
                    Message liteJson;

                    //определяем, какой объект содержит данные
                    if(json.getResult().get(i).getMessage() != null){
                        liteJson = json.getResult().get(i).getMessage();
                    }else if(json.getResult().get(i).getEdited_message() != null){
                        liteJson = json.getResult().get(i).getEdited_message();
                    }else{
                        System.out.println("Это не месадж и не правленый мессадж");
                        liteJson = null;
                    }
                    //если пришли инлайн ответы от кнопок
                    if(json.getResult().get(i).getCallback_query() != null){
                        Gamez.callbackQuery = json.getResult().get(i).getCallback_query();
                    }

                    if(liteJson != null) {
                        String first_name = liteJson.getFrom().getFirst_name();
                        String last_name = liteJson.getFrom().getLast_name();
                        String name = Name.nameBuilder(first_name, last_name);
                        String text = liteJson.getText();

                        //если текст не пустой, проверяем на содержание команд
                        if(text != null) {
                            Commands.checkCommand(liteJson, name);
                        }

                        //если кого-то добавили в чат
                        if(liteJson.getNew_chat_participant() != null){
                            System.out.println("кого-то добавили в чат");
                            long chat_id = liteJson.getChat().getId();

                            //если не родной чат, то выходим
                            if(liteJson.getNew_chat_participant().getId() == 384644516 && chat_id != -1001091731443L && chat_id != -307939734L){
                                System.out.println("уходим отседова");
                                TelegramMethods.sendMessage(chat_id, "Как-то стрёмно тут у вас");
                                TelegramMethods.leaveChat(Long.toString(chat_id));
                            }
                        }
                    }
                }
            }
            Thread.sleep(2000);
        }
    }
}

