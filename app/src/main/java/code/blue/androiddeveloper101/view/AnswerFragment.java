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
import code.blue.androiddeveloper101.viewmodel.CustomAnswerAdapter;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;

public class AnswerFragment extends Fragment {
    private static final String TAG = "AnswerFragment";
    private SharedViewModel sharedViewModel;
    private TextView tvMainQuestion, tvMainAnswer, tvTermTitle, tvQuesTitle;
    private RecyclerView rvTerminologies, rvRelatedQues;
    private CustomAnswerAdapter terminologyAdapter, relatedQuesAdapter;

    public static AnswerFragment newInstance() {
        return new AnswerFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel = ViewModelProviders.of(this.getActivity()).get(SharedViewModel.class);
        initUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.answer_fragment, container, false);
        tvMainQuestion = v.findViewById(R.id.main_question);
        tvMainAnswer = v.findViewById(R.id.tv_main_answer);
        rvTerminologies = v.findViewById(R.id.rv_terminology);
        rvRelatedQues = v.findViewById(R.id.rv_assoc_question);
        tvTermTitle = v.findViewById(R.id.terminologies);
        tvQuesTitle = v.findViewById(R.id.assoc_question);

        return v;
    }

    private void initUI(){

        sharedViewModel.getCurrentQuestion().observe(getViewLifecycleOwner(), new Observer<QuestionPojo>(){

            @Override
            public void onChanged(QuestionPojo questionPojo) {
                tvMainQuestion.setText(questionPojo.question);
                tvMainAnswer.setText(questionPojo.answer);
                if(questionPojo.terminologies == null){
                    Log.d(TAG, "onChanged: no terminology list found");
                    tvTermTitle.setVisibility(View.GONE);
                    rvTerminologies.setVisibility(View.GONE);
                }
                else{
                    Log.d(TAG, "onChanged: terminologies.size() ->" + questionPojo.terminologies.size());
                    tvTermTitle.setVisibility(View.VISIBLE);
                    rvTerminologies.setVisibility(View.VISIBLE);
                    rvTerminologies.setLayoutManager(new LinearLayoutManager(getActivity()));
                    terminologyAdapter = new CustomAnswerAdapter(getActivity(), "terminology");
                    rvTerminologies.setAdapter(terminologyAdapter);
                    terminologyAdapter.setData(questionPojo);

                }
                if(questionPojo.associated_questions == null){
                    Log.d(TAG, "onChanged: no associated questions found");
                    tvQuesTitle.setVisibility(View.GONE);
                    rvRelatedQues.setVisibility(View.GONE);
                }
                else{
                    Log.d(TAG, "onChanged: associated_questions.size() -> " + questionPojo.associated_questions.size());
                    tvQuesTitle.setVisibility(View.VISIBLE);
                    rvRelatedQues.setVisibility(View.VISIBLE);
                    rvRelatedQues.setLayoutManager(new LinearLayoutManager(getActivity()));
                    relatedQuesAdapter = new CustomAnswerAdapter(getActivity(), "related questions");
                    rvRelatedQues.setAdapter(relatedQuesAdapter);
                    relatedQuesAdapter.setData(questionPojo);
                }
            }
        });
    }
}
