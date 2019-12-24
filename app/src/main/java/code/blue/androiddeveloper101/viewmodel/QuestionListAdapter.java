package code.blue.androiddeveloper101.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.FavQuestion;
import code.blue.androiddeveloper101.model.FavQuestionDatabase;
import code.blue.androiddeveloper101.model.QuestionPojo;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {
    private static final String TAG = "QuestionListAdapter";
    private FavQuestionDatabase appDb;
    private View mDialogView;
    private AlertDialog mAlertDialog;
    private boolean editPressed;
    private List<String> deleteList;
    AlertDialog.Builder alertDialogBuilder;
    RelativeLayout relativeLayout;
    Snackbar sbOnDelete, sbOnRestore;
    CustomAnswerAdapter terminologyAdapter, relatedQuesAdapter;
    TextView tvMainQuestion, tvMainAnswer, tvTermTitle, tvQuesTitle;
    RecyclerView rvTerminologies, rvRelatedQues;
    ImageView ivClose, ivFavorite;
    int displayHeight, dialogWindowHeight;
    DisplayMetrics displayMetrics;
    WindowManager.LayoutParams layoutParams;
    AlertDialog.Builder mBuilder;

    public List<QuestionPojo> questionList;
    private Context context;

    public QuestionListAdapter(Context context){
        this.context = context;
        editPressed = false;
    }

    public void setSavedQuestions(List<QuestionPojo> questionList){
        this.questionList = questionList;
        notifyDataSetChanged();
    }

    public void initDeleteList(){
        if(deleteList == null){
            deleteList = new ArrayList<>();
        }
    }

    public void presentDeleteBtn(){
        editPressed = true;
        notifyDataSetChanged();
    }

    public void editFinished(){
        editPressed = false;
        notifyDataSetChanged();
        if(deleteList.size() > 0){
            deleteFromDB();
        }
        deleteList = null;
    }

    private void deleteFromDB(){
        for(String s : deleteList){
            deleteFavQuestion(s);
        }
    }

    @NonNull
    @Override
    public QuestionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expand_item_layout, parent, false);
        return new QuestionListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionListAdapter.ViewHolder holder, final int position) {
        final QuestionPojo tempQuestionPojo = questionList.get(position);
        appDb = FavQuestionDatabase.getInstance(context);
        holder.tvIndex.setText(Integer.toString(position+1));
        holder.tvQuestion.setText(Html.fromHtml(questionList.get(position).question));
        if(editPressed){
            holder.ivDelete.setVisibility(View.VISIBLE);
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder
                            .setMessage(Html.fromHtml("Deleting question:<br><br>" + questionList.get(position).question))
                            .setCancelable(false)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    deleteItem(position, tempQuestionPojo.question);

                                    sbOnDelete = Snackbar
                                            .make(relativeLayout, "Question Removed", Snackbar.LENGTH_LONG);
                                    sbOnRestore = Snackbar
                                            .make(relativeLayout, "Question Restored", Snackbar.LENGTH_LONG);

                                    sbOnDelete.setAction("UNDO", new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view){
                                            restoreItem(tempQuestionPojo, position);
                                            deleteList.remove(deleteList.size()-1);
                                            sbOnRestore.show();
                                        }
                                    }).show();

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        }
        else{
            holder.ivDelete.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        return questionList != null ? questionList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIndex, tvQuestion;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.number_textView);
            tvQuestion = itemView.findViewById(R.id.question_textView);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
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
        ivFavorite.setVisibility(View.GONE);
    }

    private void deleteItem(int index, String question){
        questionList.remove(index);
        deleteList.add(question);
        notifyDataSetChanged();
    }

    private void restoreItem(QuestionPojo tqp, int index){
        questionList.add(index, tqp);
        notifyDataSetChanged();
    }

}