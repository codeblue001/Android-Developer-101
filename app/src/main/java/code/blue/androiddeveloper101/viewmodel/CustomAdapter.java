package code.blue.androiddeveloper101.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import code.blue.androiddeveloper101.model.QuestionPojo;
import code.blue.androiddeveloper101.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<QuestionPojo> mQuestion;

    public void setData(List<QuestionPojo> quesList){
        mQuestion = quesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            holder.question.setText(mQuestion.get(position).question);
            holder.number.setText(Integer.toString(position + 1));
    }

    @Override
    public int getItemCount() {
        return mQuestion != null ? mQuestion.size() : 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView question, answer, number;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question_textView);
            number = itemView.findViewById(R.id.number_textView);
        }
    }
}
