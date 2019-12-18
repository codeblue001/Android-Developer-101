package code.blue.androiddeveloper101.view;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.FavQuestionDatabase;
import code.blue.androiddeveloper101.model.QuestionPojo;
import code.blue.androiddeveloper101.viewmodel.CustomAnswerAdapter;
import code.blue.androiddeveloper101.viewmodel.ExpandableRecyclerViewAdapter;
import code.blue.androiddeveloper101.viewmodel.QuestionListAdapter;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;

public class FavQuestionListFragment extends Fragment {
    private static final String TAG = "FavQuestionListFragment";
    private FavQuestionDatabase appDb;
    private RecyclerView savedQuesRecyclerView;
    private QuestionListAdapter questionListAdapter;


    public static FavQuestionListFragment newInstance() {
        return new FavQuestionListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appDb = FavQuestionDatabase.getInstance(getActivity());
        initList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_question_fragment, container, false);
        savedQuesRecyclerView = v.findViewById(R.id.rv_category_question);

        return v;
    }

    private void initList(){
        questionListAdapter = new QuestionListAdapter(getActivity());
        savedQuesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        savedQuesRecyclerView.setAdapter(questionListAdapter);
        getFavQuestion();
    }

    @SuppressLint("StaticFieldLeak")
    private void getFavQuestion(){
        new AsyncTask<Void, Void, List<QuestionPojo>>(){

            @Override
            protected List<QuestionPojo> doInBackground(Void... voids) {
                return appDb.favQuestionDao().getFavQuestionList();
            }

            @Override
            protected void onPostExecute(List<QuestionPojo> questionList){
//                super.onPostExecute(voids);
                questionListAdapter.setSavedQuestions(questionList);
            }
        }.execute();
    }
}
