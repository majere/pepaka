package sample;//raist on 13.11.2017

import com.google.gson.Gson;
import lombok.Setter;

import java.net.URLEncoder;

public class TeleButtons {
    @Setter private String text;
    @Setter private String callback_data;

    public static String crtButton(String text, String callback_data){
        TeleButtons button = new TeleButtons();
        Gson gson = new Gson();
        try {
            button.setText(URLEncoder.encode(text, "UTF-8"));
            button.setCallback_data(URLEncoder.encode(callback_data, "UTF-8"));
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Ошибка конвертации текста кнопок");
        }
        String buttonJson = gson.toJson(button);
        return buttonJson;
    }

}




// ge?chat_id=111304154&parse_mode=HTML&text=Кнопачки&one_time_keyboard=true&reply_markup={"inline_keyboard":[[{"text":"Помощь","callback_data":"/help"},{"text":"Регистрация","callback_data":"!старт"}]]}