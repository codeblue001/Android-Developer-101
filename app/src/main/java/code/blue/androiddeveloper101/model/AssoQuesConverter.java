package code.blue.androiddeveloper101.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AssoQuesConverter {
    @TypeConverter
    public String fromAssoQuesList(List<AssoQuestionPojo> associatedQuestions){
        if(associatedQuestions == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<AssoQuestionPojo>>(){}.getType();
        String json = gson.toJson(associatedQuestions, type);
        return json;
    }

    @TypeConverter
    public List<AssoQuestionPojo> toAssoQuesList(String associatedQuestionString){
        if(associatedQuestionString == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<AssoQuestionPojo>>(){}.getType();
        List<AssoQuestionPojo> assoQuesList = gson.fromJson(associatedQuestionString, type);
        return assoQuesList;
    }
}
