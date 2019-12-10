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
import code.blue.androiddeveloper101.view.AnswerActivity;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<QuestionPojo> mQuestion;
    private Context context;

    public CustomAdapter(Context context){
        this.context = context;
    }

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

            //Todo onClick method for the cardView
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AnswerActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestion != null ? mQuestion.size() : 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        public TextView question, answer, number;
        //Todo Declare variable for cardview
        public CardView cardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question_textView);
            number = itemView.findViewById(R.id.number_textView);
            //Todo Instantiate the cardview
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}
