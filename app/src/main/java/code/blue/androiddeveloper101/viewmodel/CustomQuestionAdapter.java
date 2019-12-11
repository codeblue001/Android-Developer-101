package code.blue.androiddeveloper101.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;
import code.blue.androiddeveloper101.view.AnswerFragment;

public class CustomQuestionAdapter extends RecyclerView.Adapter<CustomQuestionAdapter.CustomQuestionViewHolder> {
    private static final String TAG = "CustomQuestionAdapter";
    private List<QuestionPojo> mQuestions;
    private SharedViewModel sharedViewModel;
    private Context context;

    public CustomQuestionAdapter(Context context){
        this.context = context;
    }

    public void setData(List<QuestionPojo> questionList){
        mQuestions = questionList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomQuestionAdapter.CustomQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout, parent, false);
        return new CustomQuestionAdapter.CustomQuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomQuestionAdapter.CustomQuestionViewHolder holder, final int position) {
//        Log.d(TAG, "onBindViewHolder: index -> " + Integer.toString(position+1) + " question -> " + mQuestions.get(position).question);
        holder.tvIndex.setText(Integer.toString(position+1));
        holder.tvQuestion.setText(mQuestions.get(position).question);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedViewModel = ViewModelProviders.of((FragmentActivity) context).get(SharedViewModel.class);
                sharedViewModel.setCurrentQuestion(mQuestions.get(position));
                AnswerFragment answerFragment = AnswerFragment.newInstance();
                ((FragmentActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, answerFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestions != null ? mQuestions.size() : 0;
    }

    public class CustomQuestionViewHolder extends RecyclerView.ViewHolder{
        TextView tvIndex, tvQuestion;

        public CustomQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.number_textView);
            tvQuestion = itemView.findViewById(R.id.question_textView);
        }
    }
}
