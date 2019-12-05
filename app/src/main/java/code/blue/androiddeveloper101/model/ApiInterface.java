package code.blue.androiddeveloper101.model;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterface {
    //https://raw.githubusercontent.com/yuweitseng1993/androidquestions/master/db.json
    @GET("/yuweitseng1993/androidquestions/master/db.json")
    Observable<List<QuestionPojo>> getQuestions();

}
