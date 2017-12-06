package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class JsonConverter {
    static AnswerResult toObj(String answer){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(answer, AnswerResult.class);
    }
}
