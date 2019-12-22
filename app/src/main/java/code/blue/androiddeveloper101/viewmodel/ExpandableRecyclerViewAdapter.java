package code.blue.androiddeveloper101.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
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

import java.util.ArrayList;
import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;

public class ExpandableRecyclerViewAdapter extends RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ExpandableRecyclerViewA";
//    List<Pair<String, Boolean>> categoryList;
    List<String> categoryList;
    List<Boolean> expandCondition;
    List<List<QuestionPojo>> questionList;
    Context context;
    private InnerRecyclerViewAdapter itemInnerRecyclerView;
    private Resources res;
    private int[] myColors;
    private int index;
    private RecyclerView.LayoutManager layoutmanager;

    public ExpandableRecyclerViewAdapter(Context context, RecyclerView.LayoutManager layoutmanager){
        this.context = context;
        this.layoutmanager = layoutmanager;
        res = context.getResources();
        myColors = res.getIntArray(R.array.colors);
    }

    public void setCategoryList(List<String> categoryList){
//        Log.d(TAG, "setCategoryList: categoryList.size() -> " + categoryList.size());
        this.categoryList = categoryList;
        initExpandCond();
        notifyDataSetChanged();
    }

    private void initExpandCond(){
        expandCondition = new ArrayList<>();
        for(int i = 0; i < categoryList.size(); i++){
            expandCondition.add(false);
        }
    }

    public void setQuestionList(List<List<QuestionPojo>> questionList){
//        Log.d(TAG, "setQuestionList: questionList.size() -> " + questionList.size());
        this.questionList = questionList;
        notifyDataSetChanged();
    }

    private void changeStateOfItem(int position){
        List<Boolean> tempCondList = new ArrayList<>();
//        Log.d(TAG, "changeStateOfItem: position -> " + position);
        for(int i = 0; i < categoryList.size(); i++){
            if(i == position){
                tempCondList.add(true);
            }
            else{
                tempCondList.add(false);
            }
        }
        expandCondition = tempCondList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_collapse_view, parent, false);
        return new ExpandableRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        index = position % 7;
//        Log.d(TAG, "onBindViewHolder: index -> " + index);
        holder.categoryName.setText(categoryList.get(position));
        holder.rvQuestions.setLayoutManager(new LinearLayoutManager(context));
        holder.categoryIcon.setColorFilter(myColors[index]);
//        Log.d(TAG, "onBindViewHolder: expandCondition.get(position) -> " + expandCondition.get(position));
        if(!expandCondition.get(position)){
            holder.rvQuestions.setVisibility(View.GONE);
        }
        else{
            if(holder.rvQuestions.isShown()){
                holder.rvQuestions.setVisibility(View.GONE);
            }
            else{
                holder.rvQuestions.setVisibility(View.VISIBLE);
                itemInnerRecyclerView = new InnerRecyclerViewAdapter(context);
                holder.rvQuestions.setAdapter(itemInnerRecyclerView);
                itemInnerRecyclerView.setData(questionList.get(position));
            }
            layoutmanager.scrollToPosition(position);
        }
        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG, "onClick: position selected -> " + position);
                if(holder.rvQuestions.isShown()){
                    holder.rvQuestions.setVisibility(View.GONE);
                }
                else{
                    changeStateOfItem(position);
                    notifyDataSetChanged();
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
