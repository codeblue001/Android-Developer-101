package code.blue.androiddeveloper101.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavQuestionDao {
    @Query("Select * from favQuestion")
    List<QuestionPojo> getFavQuestionList();

    @Insert(onConflict = REPLACE)
    void insertFavQuestion(FavQuestion favQuestion);

    @Update
    void updateFavQuestion(FavQuestion favQuestion);

    @Delete
    void deleteFavQuestion(FavQuestion favQuestion);
}
