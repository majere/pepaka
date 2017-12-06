package sample;

import lombok.Setter;

import java.net.URLEncoder;

class TelegramMethods {
    @Setter private static String telegramUrl;

    static String getUpdates(){
        try {
            return Http.sendGet(telegramUrl + "/getUpdates");
        }catch(Exception e){return null;}
    }

    static void setOffset(long update_id){
        try {
            Http.sendGet(telegramUrl + "/getUpdates?offset=" + update_id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    static String sendMessage(long chat_id, String text){
        String jsonAnswer = null;
        try {
            jsonAnswer = Http.sendGet(telegramUrl + "/sendMessage?chat_id=" + chat_id + "&parse_mode=HTML&text=" + URLEncoder.encode(text, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonAnswer;
    }

    static void sendBtnMessage(long chat_id, String text){
        try {
            Http.sendGet(telegramUrl + "/sendMessage?chat_id=" + chat_id + "&one_time_keyboard=true&reply_markup={\"keyboard\":[[{\"text\":\"!d4\"},{\"text\":\"!d6\"}],[{\"text\":\"!d8\"},{\"text\":\"!d20\"}]]}&parse_mode=HTML&text=" + URLEncoder.encode(text, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String sendInlineBtn(long chat_id, String text, String button){
        String answer = null;
        try {
            answer = Http.sendGet(telegramUrl + "/sendMessage?chat_id=" + chat_id + "&parse_mode=HTML&text=" + text + "&one_time_keyboard=true&reply_markup={\"inline_keyboard\":[" + button + "]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }

    static void removeKeyboard(long chat_id, String text){
        try {
            Http.sendGet(telegramUrl + "/sendMessage?chat_id=" + chat_id + "&parse_mode=HTML&reply_markup={\"remove_keyboard\":true}&text=" + text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String editMessageText(long chat_id, long message_id, String text, String button){
        String answer = null;
        try {
            if(button != null) {
                answer = Http.sendGet(telegramUrl + "/editMessageText?chat_id=" + chat_id + "&message_id=" + message_id + "&parse_mode=HTML&text=" + URLEncoder.encode(text, "UTF-8") + "&one_time_keyboard=true&reply_markup={\"inline_keyboard\":[[" + button + "]]}");
            }else{
                answer = Http.sendGet(telegramUrl + "/editMessageText?chat_id=" + chat_id + "&message_id=" + message_id + "&parse_mode=HTML&text=" + URLEncoder.encode(text, "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }

    static void sendReply(long chat_id, long message_id, String text){
        try {
            Http.sendGet(telegramUrl + "/sendMessage?chat_id=" + chat_id + "&parse_mode=HTML&reply_to_message_id=" + message_id + "&text=" + URLEncoder.encode(text, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void delMessage(long chat_id, long message_id){
        try {
            Http.sendGet(telegramUrl + "/deleteMessage?chat_id=" + chat_id + "&message_id=" + message_id);
        } catch (Exception e) {
            System.out.printf("Не удалить сообщение");
        }
    }

    static void getChatMember(long chat_id){
        try {
            Http.sendGet(telegramUrl + "/getChatMember?chat_id=" + chat_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void leaveChat(String chat_id){
        try {
            Http.sendGet(telegramUrl + "/leaveChat?chat_id=" + chat_id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    static void sendSticker(long chat_id, String sticker_id){
        try {
            Http.sendGet(telegramUrl + "/sendSticker?chat_id=" + chat_id + "&sticker=" + sticker_id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    static void sendInvoice(long chat_id, String title, String description, String payload, String provider_token, String start_parameter, String currency, LabeledPrice labeledPrice){
        try {
            Http.sendGet(telegramUrl + "/chat_id?chat_id=" + chat_id + "&title=" + title + "&description=" + description + "&payload=" + payload + "&provider_token=" + provider_token + "&start_parameter=" + start_parameter + "currency=" + currency);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
