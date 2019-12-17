package code.blue.androiddeveloper101.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;

public class ExpandableRecyclerViewAdapter extends RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ExpandableRecyclerViewA";
    List<String> categoryList;
    List<List<QuestionPojo>> questionList;
    Context context;
    private InnerRecyclerViewAdapter itemInnerRecyclerView;
    private Resources res;
    private int[] myColors;
    private int index;

    public ExpandableRecyclerViewAdapter(Context context){
        this.context = context;
        res = context.getResources();
        myColors = res.getIntArray(R.array.colors);
    }

    public void setCategoryList(List<String> categoryList){
//        Log.d(TAG, "setCategoryList: categoryList.size() -> " + categoryList.size());
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    public void setQuestionList(List<List<QuestionPojo>> questionList){
//        Log.d(TAG, "setQuestionList: questionList.size() -> " + questionList.size());
        this.questionList = questionList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_collapse_view, parent, false);
        return new ExpandableRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        index = position % 7;
//        Log.d(TAG, "onBindViewHolder: index -> " + index);
        holder.categoryName.setText(categoryList.get(position));
        holder.rvQuestions.setLayoutManager(new LinearLayoutManager(context));
        holder.categoryIcon.setColorFilter(myColors[index]);
        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.rvQuestions.isShown()){
                    holder.rvQuestions.setVisibility(View.GONE);
                }
                else{
                    holder.rvQuestions.setVisibility(View.VISIBLE);
                    itemInnerRecyclerView = new InnerRecyclerViewAdapter(context);
                    holder.rvQuestions.setAdapter(itemInnerRecyclerView);
                    itemInnerRecyclerView.setData(questionList.get(position));
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
