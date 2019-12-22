package code.blue.androiddeveloper101.view;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.FavQuestionDatabase;
import code.blue.androiddeveloper101.model.QuestionPojo;
import code.blue.androiddeveloper101.viewmodel.QuestionListAdapter;

public class FavQuestionListFragment extends Fragment {
    private static final String TAG = "FavQuestionListFragment";
    private FavQuestionDatabase appDb;
    private RecyclerView savedQuesRecyclerView;
    private QuestionListAdapter questionListAdapter;
    Button btnEdit, btnDone;

    public static FavQuestionListFragment newInstance() {
        return new FavQuestionListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        appDb = FavQuestionDatabase.getInstance(getActivity());
        initList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_question_fragment, container, false);
        savedQuesRecyclerView = v.findViewById(R.id.rv_category_question);
        btnEdit = v.findViewById(R.id.btn_edit);
        btnDone = v.findViewById(R.id.btn_done);
        btnEdit.setVisibility(View.VISIBLE);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionListAdapter.initDeleteList();
                questionListAdapter.presentDeleteBtn();
                btnDone.setVisibility(View.VISIBLE);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionListAdapter.editFinished();
                btnDone.setVisibility(View.GONE);
            }
        });

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
                questionListAdapter.setSavedQuestions(questionList);
            }
        }.execute();
    }
}
