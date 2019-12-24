package code.blue.androiddeveloper101.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import code.blue.androiddeveloper101.model.CategoriesPojo;
import code.blue.androiddeveloper101.model.QuestionPojo;
import code.blue.androiddeveloper101.model.ApiInterface;
import code.blue.androiddeveloper101.model.ResultPojo;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SharedViewModel extends ViewModel {
    private static final String TAG = "SharedViewModel";
    private MutableLiveData<List<String>> categoryList = new MutableLiveData<>();
    private List<String> tempCategoryList = new ArrayList<>();
    private MutableLiveData<List<List<QuestionPojo>>> questionList = new MutableLiveData<>();
    private List<List<QuestionPojo>> tempQuestionList = new ArrayList<>();
    private List<QuestionPojo> tempAllQuestions = new ArrayList<>();
    private MutableLiveData<List<QuestionPojo>> allQuestionList = new MutableLiveData<>();

    public MutableLiveData<List<String>> getCategoryList() {
        categoryList.setValue(tempCategoryList);
        return categoryList;
    }
    public MutableLiveData<List<List<QuestionPojo>>> getQuestionMap(){
        questionList.setValue(tempQuestionList);
        return questionList;
    }
    public MutableLiveData<List<QuestionPojo>> getAllQuestions(){
        allQuestionList.setValue(tempAllQuestions);
        return allQuestionList;
    }

    public void loadCategories(){
        Log.d(TAG, "DEBUG: loadCategories: ");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        retrofit.create(ApiInterface.class).getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CategoriesPojo>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CategoriesPojo> categoriesPojos) {
                        tempCategoryList = new ArrayList<>();
                        tempQuestionList = new ArrayList<>();
                        tempAllQuestions = new ArrayList<>();

                        for(CategoriesPojo cp : categoriesPojos){
                            loadQuesAns(cp.category);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError (loadCategories): " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void loadQuesAns(String category){
//        Log.d(TAG, "loadQuesAns: category -> " + category);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        retrofit.create(ApiInterface.class).getQuestions(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultPojo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultPojo questionPojos) {
                        tempQuestionList.add(questionPojos.ques_ans_list);
                        tempCategoryList.add(questionPojos.data_category);

                        for(QuestionPojo qp : questionPojos.ques_ans_list){
                            tempAllQuestions.add(qp);
                        }

                        categoryList.setValue(tempCategoryList);
                        questionList.setValue(tempQuestionList);
                        allQuestionList.setValue(tempAllQuestions);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError (loadQuesAns): " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
