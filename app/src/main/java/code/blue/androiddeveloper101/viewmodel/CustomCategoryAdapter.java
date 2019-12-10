package code.blue.androiddeveloper101.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.CategoriesPojo;

public class CustomCategoryAdapter extends RecyclerView.Adapter<CustomCategoryAdapter.CustomCategoryViewHolder> {

    private List<CategoriesPojo> mCategory;

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
    public void onBindViewHolder(@NonNull CustomCategoryAdapter.CustomCategoryViewHolder holder, int position) {
        holder.category.setText(mCategory.get(position).category);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
