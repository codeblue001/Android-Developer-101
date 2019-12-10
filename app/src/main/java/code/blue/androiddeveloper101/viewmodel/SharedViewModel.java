package code.blue.androiddeveloper101.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
    private MutableLiveData<List<CategoriesPojo>> categoryList = new MutableLiveData<>();
    private MutableLiveData<List<QuestionPojo>> quesAnsPairs = new MutableLiveData<>();

    public MutableLiveData<List<CategoriesPojo>> getCategoryList() {
        return categoryList;
    }
    public MutableLiveData<List<QuestionPojo>> getQuesAnsList(){
        return quesAnsPairs;
    }

    public void loadCategories(){
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
                        categoryList.setValue(categoriesPojos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void loadQuesAns(String category){
        Log.d(TAG, "loadQuesAns: ");
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
                        quesAnsPairs.setValue(questionPojos.ques_ans_list);
                        for(QuestionPojo qp : questionPojos.ques_ans_list){
                            if(qp.terminologies != null){
                                Log.d(TAG, "onNext: question -> " + qp.question + " answer -> " + qp.answer + " add_on -> " + qp.terminologies.get(0).term + " " + qp.terminologies.get(0).meaning);
                            }
                            else{
                                Log.d(TAG, "onNext: question -> " + qp.question + " answer -> " + qp.answer);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
