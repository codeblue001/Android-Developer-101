package code.blue.androiddeveloper101.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {
    private static final String TAG = "QuestionListAdapter";

    public List<QuestionPojo> questionList;
    private Context context;

    public QuestionListAdapter(Context context){
        this.context = context;
    }

    public void setSavedQuestions(List<QuestionPojo> questionList){
        this.questionList = questionList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expand_item_layout, parent, false);
        return new QuestionListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionListAdapter.ViewHolder holder, int position) {
        holder.tvIndex.setText(Integer.toString(position+1));
        holder.tvQuestion.setText(questionList.get(position).question);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList != null ? questionList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIndex, tvQuestion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.number_textView);
            tvQuestion = itemView.findViewById(R.id.question_textView);
        }
    }
}
