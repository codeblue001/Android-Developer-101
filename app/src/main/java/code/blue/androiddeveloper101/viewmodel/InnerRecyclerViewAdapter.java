package code.blue.androiddeveloper101.viewmodel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;

public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "InnerRecyclerViewAdapte";
    public List<QuestionPojo> questionList;

    public InnerRecyclerViewAdapter(List<QuestionPojo> questionList){
        this.questionList = questionList;
    }

    public void setData(List<QuestionPojo> questionList){
        this.questionList = questionList;
        Log.d(TAG, "setData: questionList.size() -> " + questionList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expand_item_layout, parent, false);
        return new InnerRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerRecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: question -> " + questionList.get(position).question);
        holder.tvIndex.setText(Integer.toString(position+1));
        holder.tvQuestion.setText(questionList.get(position).question);
        holder.cvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open dialog fragment
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: questionList.size() -> " + questionList.size());
        return questionList != null ? questionList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIndex, tvQuestion;
        CardView cvQuestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.number_textView);
            tvQuestion = itemView.findViewById(R.id.question_textView);
            cvQuestion = itemView.findViewById(R.id.cv_question);
        }
    }
}
