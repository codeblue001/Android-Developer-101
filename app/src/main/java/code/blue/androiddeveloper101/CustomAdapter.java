package code.blue.androiddeveloper101;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import code.blue.androiddeveloper101.Model.QuestionPojo;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<QuestionPojo> mQuestion = new ArrayList<>();

    public CustomAdapter(ArrayList<QuestionPojo> mQuestion) {
        this.mQuestion = mQuestion;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            holder.question.setText(mQuestion.get(position).getQuestion());
            holder.number.setText(mQuestion.get(position).getNumber());

    }

    @Override
    public int getItemCount() {
        return mQuestion.size();
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
