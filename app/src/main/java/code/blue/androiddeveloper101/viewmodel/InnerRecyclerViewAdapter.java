package code.blue.androiddeveloper101.viewmodel;

import android.content.Context;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;

public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "InnerRecyclerViewAdapte";
    public List<QuestionPojo> questionList;
    private Context context;

    public InnerRecyclerViewAdapter(Context context){
        this.context = context;
    }

    public void setData(List<QuestionPojo> questionList){
        this.questionList = questionList;
//        Log.d(TAG, "setData: questionList.size() -> " + questionList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expand_item_layout, parent, false);
        return new InnerRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerRecyclerViewAdapter.ViewHolder holder, final int position) {
//        Log.d(TAG, "onBindViewHolder: question -> " + questionList.get(position).question);
        final QuestionPojo tempQuestionPojo = questionList.get(position);
        holder.tvIndex.setText(Integer.toString(position+1));
        holder.tvQuestion.setText(Html.fromHtml(tempQuestionPojo.question));
        holder.cvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open dialog fragment
                final View mDialogView = LayoutInflater.from(context).inflate(R.layout.answer_dialog, null);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context)
                        .setView(mDialogView);
                final AlertDialog mAlertDialog = mBuilder.show();
                mAlertDialog.setCanceledOnTouchOutside(true);

                CustomAnswerAdapter terminologyAdapter, relatedQuesAdapter;
                TextView tvMainQuestion = mDialogView.findViewById(R.id.main_question);
                TextView tvMainAnswer = mDialogView.findViewById(R.id.tv_main_answer);
                RecyclerView rvTerminologies = mDialogView.findViewById(R.id.rv_terminology);
                RecyclerView rvRelatedQues = mDialogView.findViewById(R.id.rv_assoc_question);
                TextView tvTermTitle = mDialogView.findViewById(R.id.terminologies);
                TextView tvQuesTitle = mDialogView.findViewById(R.id.assoc_question);
                ImageView ivClose = mDialogView.findViewById(R.id.iv_close);

                tvMainQuestion.setText(tempQuestionPojo.question);
                tvMainAnswer.setText(tempQuestionPojo.answer);
                if(tempQuestionPojo.terminologies == null){
                    Log.d(TAG, "onChanged: no terminology list found");
                    tvTermTitle.setVisibility(View.GONE);
                    rvTerminologies.setVisibility(View.GONE);
                }
                else{
                    Log.d(TAG, "onChanged: terminologies.size() ->" + tempQuestionPojo.terminologies.size());
                    tvTermTitle.setVisibility(View.VISIBLE);
                    rvTerminologies.setVisibility(View.VISIBLE);
                    rvTerminologies.setLayoutManager(new LinearLayoutManager(context));
                    terminologyAdapter = new CustomAnswerAdapter(context, "terminology");
                    rvTerminologies.setAdapter(terminologyAdapter);
                    terminologyAdapter.setData(tempQuestionPojo);

                }
                if(tempQuestionPojo.associated_questions == null){
                    Log.d(TAG, "onChanged: no associated questions found");
                    tvQuesTitle.setVisibility(View.GONE);
                    rvRelatedQues.setVisibility(View.GONE);
                }
                else{
                    Log.d(TAG, "onChanged: associated_questions.size() -> " + tempQuestionPojo.associated_questions.size());
                    tvQuesTitle.setVisibility(View.VISIBLE);
                    rvRelatedQues.setVisibility(View.VISIBLE);
                    rvRelatedQues.setLayoutManager(new LinearLayoutManager(context));
                    relatedQuesAdapter = new CustomAnswerAdapter(context, "related questions");
                    rvRelatedQues.setAdapter(relatedQuesAdapter);
                    relatedQuesAdapter.setData(tempQuestionPojo);
                }

                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAlertDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount: questionList.size() -> " + questionList.size());
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
