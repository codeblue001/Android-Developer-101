package code.blue.androiddeveloper101.model;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterface {
    //https://raw.githubusercontent.com/yuweitseng1993/androidquestions/master/categories.json
    @GET("yuweitseng1993/androidquestions/master/categories.json")
    Observable<List<CategoriesPojo>> getCategories();

    //https://raw.githubusercontent.com/yuweitseng1993/androidquestions/master/{category_name}.json
    @GET("/yuweitseng1993/androidquestions/master/{category}.json")
    Observable<ResultPojo> getQuestions();

}
