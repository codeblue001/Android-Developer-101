package code.blue.androiddeveloper101.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;
import code.blue.androiddeveloper101.viewmodel.CustomQuestionAdapter;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;

public class QuestionFragment extends Fragment {
    private static final String TAG = "QuestionFragment";
    private SharedViewModel sharedViewModel;
    private RecyclerView rvQuestions;
    private CustomQuestionAdapter customAdapter;
    private TextView questionCategory;

    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel = ViewModelProviders.of(this.getActivity()).get(SharedViewModel.class);
        initRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.question_fragment, container, false);
        rvQuestions = v.findViewById(R.id.rv_question_list);
        questionCategory = v.findViewById(R.id.tv_category_title);

        return v;
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvQuestions.setLayoutManager(linearLayoutManager);
        customAdapter = new CustomQuestionAdapter(getActivity());
        rvQuestions.setAdapter(customAdapter);
        sharedViewModel.getQuesAnsList().observe(getViewLifecycleOwner(), new Observer<List<QuestionPojo>>(){
            @Override
            public void onChanged(List<QuestionPojo> questionPojos) {
                Log.d(TAG, "onChanged: questionPojos.size() -> " + questionPojos.size());
                customAdapter.setData(questionPojos);
            }
        });
        sharedViewModel.getCurrentDataCategory().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                questionCategory.setText("Interview Questions: " + s);
            }
        });

    }
}
