package code.blue.androiddeveloper101.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = FavQuestion.class, exportSchema = false, version = 2)
@TypeConverters({TermConverter.class, AssoQuesConverter.class})
public abstract class FavQuestionDatabase extends RoomDatabase {
    private static final String dbName = "local_data_db";
    private static FavQuestionDatabase instance;

    public static synchronized FavQuestionDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), FavQuestionDatabase.class, dbName)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract FavQuestionDao favQuestionDao();
}
