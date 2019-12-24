package code.blue.androiddeveloper101.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.FavQuestion;
import code.blue.androiddeveloper101.model.FavQuestionDatabase;
import code.blue.androiddeveloper101.model.QuestionPojo;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "SearchAdapter";
    private Context context;
    private List<QuestionPojo> questionList;
    private List<QuestionPojo> questionListFiltered;
    private FavQuestionDatabase appDb;
    private View mDialogView;
    private AlertDialog mAlertDialog;
    private int rowNum;
    CustomAnswerAdapter terminologyAdapter, relatedQuesAdapter;
    TextView tvMainQuestion, tvMainAnswer, tvTermTitle, tvQuesTitle;
    RecyclerView rvTerminologies, rvRelatedQues;
    ImageView ivClose, ivFavorite;
    int displayHeight, dialogWindowHeight;
    DisplayMetrics displayMetrics;
    WindowManager.LayoutParams layoutParams;
    AlertDialog.Builder mBuilder;
    private Drawable mDrawable;
    private Toast mToast;
    private FavQuestion favQuestion;

    public SearchAdapter(Context context){
        this.context = context;
    }

    public void setQuestionList(List<QuestionPojo> matchQuestions){
        questionList = matchQuestions;
        questionListFiltered = matchQuestions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expand_item_layout, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        if(questionListFiltered != null){
            final QuestionPojo tempQuestionPojo = questionList.get(position);
            appDb = FavQuestionDatabase.getInstance(context);
            holder.tvIndex.setText(Integer.toString(position+1));
            holder.tvQuestion.setText(Html.fromHtml(questionListFiltered.get(position).question));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hasExistance(tempQuestionPojo.question);

                    prepareAnswerDialog();

                    tvMainQuestion.setText(Html.fromHtml(tempQuestionPojo.question));
                    tvMainAnswer.setText(tempQuestionPojo.answer);
                    if (tempQuestionPojo.terminologies == null) {
                        Log.d(TAG, "onChanged: no terminology list found");
                        tvTermTitle.setVisibility(View.GONE);
                        rvTerminologies.setVisibility(View.GONE);
                    } else {
                        Log.d(TAG, "onChanged: terminologies.size() ->" + tempQuestionPojo.terminologies.size());
                        tvTermTitle.setVisibility(View.VISIBLE);
                        rvTerminologies.setVisibility(View.VISIBLE);
                        rvTerminologies.setLayoutManager(new LinearLayoutManager(context));
                        terminologyAdapter = new CustomAnswerAdapter(context, "terminology");
                        rvTerminologies.setAdapter(terminologyAdapter);
                        terminologyAdapter.setData(tempQuestionPojo);
                    }
                    if (tempQuestionPojo.associated_questions == null) {
                        Log.d(TAG, "onChanged: no associated questions found");
                        tvQuesTitle.setVisibility(View.GONE);
                        rvRelatedQues.setVisibility(View.GONE);
                    } else {
                        Log.d(TAG, "onChanged: associated_questions.size() -> " + tempQuestionPojo.associated_questions.size());
                        tvQuesTitle.setVisibility(View.VISIBLE);
                        rvRelatedQues.setVisibility(View.VISIBLE);
                        rvRelatedQues.setLayoutManager(new LinearLayoutManager(context));
                        relatedQuesAdapter = new CustomAnswerAdapter(context, "related questions");
                        rvRelatedQues.setAdapter(relatedQuesAdapter);
                        relatedQuesAdapter.setData(tempQuestionPojo);
                    }

                    if(rowNum > 0){
                        mDrawable = context.getDrawable(R.drawable.ic_favorite_pink_32dp);
                        ivFavorite.setImageDrawable(mDrawable);
                        ivFavorite.setTag("R.drawable.ic_favorite_pink_32dp");
                    }

                    ivClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAlertDialog.dismiss();
                        }
                    });

                    ivFavorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            favQuestion = new FavQuestion(0, tempQuestionPojo.question, tempQuestionPojo.answer,
                                    tempQuestionPojo.terminologies, tempQuestionPojo.associated_questions);
                            ImageView imageView = (ImageView) view;

                            if (imageView.getTag() != null) {
                                String tag = (String) imageView.getTag();
                                if (tag.equals("R.drawable.ic_favorite_border_pink_32dp")) {
                                    insertFavQuestion(favQuestion);
                                    mDrawable = context.getDrawable(R.drawable.ic_favorite_pink_32dp);
                                    imageView.setImageDrawable(mDrawable);
                                    imageView.setTag("R.drawable.ic_favorite_pink_32dp");
                                    mToast = Toast.makeText(context, "Question added to favorite list.", Toast.LENGTH_SHORT);
                                    mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    mToast.show();
                                } else if (tag.equals("R.drawable.ic_favorite_pink_32dp")) {
                                    deleteFavQuestion(tempQuestionPojo.question);
                                    mDrawable = context.getDrawable(R.drawable.ic_favorite_border_pink_32dp);
                                    imageView.setImageDrawable(mDrawable);
                                    imageView.setTag("R.drawable.ic_favorite_border_pink_32dp");
                                    mToast = Toast.makeText(context, "Question removed from favorite list.", Toast.LENGTH_SHORT);
                                    mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    mToast.show();
                                }
                            }
                        }
                    });
                }
            });
        }
        if(questionListFiltered.size() == 0){
            Log.d(TAG, "DEBUG: onBindViewHolder: no matching result");
        }
    }

    @Override
    public int getItemCount() {
        return questionListFiltered != null ? questionListFiltered.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    questionListFiltered = questionList;
                }
                else{
                    List<QuestionPojo> filteredList = new ArrayList<>();
                    for(QuestionPojo qp : questionList){
                        if(qp.question.toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(qp);
                        }
                    }
                    questionListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = questionListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                questionListFiltered = (ArrayList<QuestionPojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIndex, tvQuestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.number_textView);
            tvQuestion = itemView.findViewById(R.id.question_textView);
        }
    }

    private void prepareAnswerDialog(){
        displayMetrics = context.getResources().getDisplayMetrics();
        displayHeight = displayMetrics.heightPixels;
        layoutParams = new WindowManager.LayoutParams();

        mDialogView = LayoutInflater.from(context).inflate(R.layout.answer_dialog, null);
        mBuilder = new AlertDialog.Builder(context)
                .setView(mDialogView);
        mAlertDialog = mBuilder.show();
        mAlertDialog.setCanceledOnTouchOutside(true);

        layoutParams.copyFrom(mAlertDialog.getWindow().getAttributes());
        dialogWindowHeight = (int) (displayHeight * 0.8f);
        layoutParams.height = dialogWindowHeight;
        mAlertDialog.getWindow().setAttributes(layoutParams);
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);

        tvMainQuestion = mDialogView.findViewById(R.id.main_question);
        tvMainAnswer = mDialogView.findViewById(R.id.tv_main_answer);
        rvTerminologies = mDialogView.findViewById(R.id.rv_terminology);
        rvRelatedQues = mDialogView.findViewById(R.id.rv_assoc_question);
        tvTermTitle = mDialogView.findViewById(R.id.terminologies);
        tvQuesTitle = mDialogView.findViewById(R.id.assoc_question);
        ivClose = mDialogView.findViewById(R.id.iv_close);
        ivFavorite = mDialogView.findViewById(R.id.iv_fav_icon);
    }

    @SuppressLint("StaticFieldLeak")
    private void insertFavQuestion(final FavQuestion favQuestion){
        new AsyncTask<FavQuestion, Void, Void>(){

            @Override
            protected Void doInBackground(FavQuestion... favQuestions) {
                appDb.favQuestionDao().insertFavQuestion(favQuestion);
                return null;
            }

            @Override
            protected void onPostExecute(Void voids){
                super.onPostExecute(voids);
            }
        }.execute(favQuestion);
    }

    @SuppressLint("StaticFieldLeak")
    private void deleteFavQuestion(final String question){
        new AsyncTask<String, Void, Void>(){

            @Override
            protected Void doInBackground(String... strings) {
                appDb.favQuestionDao().deleteFavQuestion(question);
                return null;
            }

        }.execute(question);
    }

    @SuppressLint("StaticFieldLeak")
    private void hasExistance(final String question){
        new AsyncTask<String, Void, Integer>(){

            @Override
            protected Integer doInBackground(String... strings) {
                setMatchCount(appDb.favQuestionDao().checkExistance(question));
                return appDb.favQuestionDao().checkExistance(question);
            }

        }.execute(question);
    }

    private void setMatchCount(int num){
        rowNum = num;
    }
}
