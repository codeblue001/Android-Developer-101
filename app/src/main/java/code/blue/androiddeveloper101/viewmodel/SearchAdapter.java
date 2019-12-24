package code.blue.androiddeveloper101.viewmodel;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<QuestionPojo> questionList;
    private List<QuestionPojo> questionListFiltered;

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
            holder.tvIndex.setText(Integer.toString(position+1));
            holder.tvQuestion.setText(Html.fromHtml(questionListFiltered.get(position).question));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
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
}
