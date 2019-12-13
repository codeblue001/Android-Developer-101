package code.blue.androiddeveloper101.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;

public class ExpandableRecyclerViewAdapter extends RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ExpandableRecyclerViewA";
    List<String> categoryList;
    List<List<QuestionPojo>> questionList;
    Context context;

    public ExpandableRecyclerViewAdapter(Context context){
        this.context = context;
    }

    public void setData(List<String> categoryList, List<List<QuestionPojo>> questionList){
        this.categoryList = categoryList;
        this.questionList = questionList;
        notifyDataSetChanged();
//        Log.d(TAG, "setData: questionList.size() -> " + questionList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_collapse_view, parent, false);
        return new ExpandableRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        Log.d(TAG, "onBindViewHolder: category index -> " + position);
        holder.categoryName.setText(categoryList.get(position));

//        itemInnerRecyclerView.setData(questionList.get(position));
        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.rvQuestions.isShown()){
                    holder.rvQuestions.setVisibility(View.GONE);
                }
                else{
                    holder.rvQuestions.setVisibility(View.VISIBLE);
                    InnerRecyclerViewAdapter itemInnerRecyclerView = new InnerRecyclerViewAdapter(questionList.get(position));
                    itemInnerRecyclerView.setData(questionList.get(position));
                    holder.rvQuestions.setAdapter(itemInnerRecyclerView);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryIcon;
        TextView categoryName;
        RecyclerView rvQuestions;
        CardView categoryCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.iv_category_icon);
            categoryName  = itemView.findViewById(R.id.tv_category_name);
            rvQuestions = itemView.findViewById(R.id.rv_question_list);
            categoryCard = itemView.findViewById(R.id.cv_category);
        }
    }
}
