package code.blue.androiddeveloper101.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TermConverter{
    @TypeConverter
    public String fromTerminologyList(List<TerminologyPojo> terminologies){
        if(terminologies == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<TerminologyPojo>>(){}.getType();
        String json = gson.toJson(terminologies, type);
        return json;
    }

    @TypeConverter
    public List<TerminologyPojo> toTerminologyList(String terminologiesString){
        if(terminologiesString == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<TerminologyPojo>>(){}.getType();
        List<TerminologyPojo> terminologyList = gson.fromJson(terminologiesString, type);
        return terminologyList;
    }
}
