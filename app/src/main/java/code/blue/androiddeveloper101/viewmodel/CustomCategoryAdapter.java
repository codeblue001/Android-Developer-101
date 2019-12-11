package code.blue.androiddeveloper101.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.CategoriesPojo;
import code.blue.androiddeveloper101.view.QuestionFragment;

public class CustomCategoryAdapter extends RecyclerView.Adapter<CustomCategoryAdapter.CustomCategoryViewHolder> {
    private SharedViewModel sharedViewModel;
    private List<CategoriesPojo> mCategory;
    private Context context;

    public CustomCategoryAdapter(Context context){
        this.context = context;
    }

    public void setData(List<CategoriesPojo> categoryList){
        mCategory = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomCategoryAdapter.CustomCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
        return new CustomCategoryAdapter.CustomCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomCategoryAdapter.CustomCategoryViewHolder holder, final int position) {
        sharedViewModel = ViewModelProviders.of((FragmentActivity) context).get(SharedViewModel.class);
        holder.category.setText(mCategory.get(position).category);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedViewModel.loadQuesAns(mCategory.get(position).category);
                QuestionFragment questionFragment = QuestionFragment.newInstance();
                ((FragmentActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, questionFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategory != null ? mCategory.size() : 0;
    }

    public class CustomCategoryViewHolder extends RecyclerView.ViewHolder{
        TextView category;

        public CustomCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.tv_category_name);
        }
    }
}
