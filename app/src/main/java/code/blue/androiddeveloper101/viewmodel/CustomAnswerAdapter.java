package code.blue.androiddeveloper101.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.blue.androiddeveloper101.model.QuestionPojo;
import code.blue.androiddeveloper101.R;

public class CustomAnswerAdapter extends RecyclerView.Adapter<CustomAnswerAdapter.CustomAnswerViewHolder> {

    private QuestionPojo mQuestion;
    private Context context;
    private String rvType;
    private int termCount, quesCount;

    public CustomAnswerAdapter(Context context, String rvType){
        this.context = context;
        this.rvType = rvType;
    }

    public void setData(QuestionPojo question){
        mQuestion = question;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item_layout, parent, false);
        return new CustomAnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAnswerViewHolder holder, int position) {
        if(rvType.equals("terminology")){
            holder.tvTitle.setText(mQuestion.terminologies.get(position).term);
            holder.tvText.setText(mQuestion.terminologies.get(position).meaning);
        }
        else if(rvType.equals("related questions")){
            holder.tvTitle.setText(mQuestion.associated_questions.get(position).related_question);
            holder.tvText.setText(mQuestion.associated_questions.get(position).related_answer);
        }
    }

    @Override
    public int getItemCount() {
        getRelatedFieldCount();
        if(rvType.equals("terminology")){
            return termCount;
        }
        else if(rvType.equals("related questions")){
            return quesCount;
        }
        return 0;
    }

    public class CustomAnswerViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvText;

        public CustomAnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_card_title);
            tvText = itemView.findViewById(R.id.tv_card_text);
        }
    }

    private void getRelatedFieldCount(){
        termCount = 0;
        quesCount = 0;
        if(mQuestion.terminologies != null){
            termCount = mQuestion.terminologies.size();
        }
        if(mQuestion.associated_questions != null){
            quesCount = mQuestion.associated_questions.size();
        }
    }

}
