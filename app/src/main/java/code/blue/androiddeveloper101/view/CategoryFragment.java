package code.blue.androiddeveloper101.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.CategoriesPojo;
import code.blue.androiddeveloper101.viewmodel.CustomCategoryAdapter;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;

public class CategoryFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerView;
    private CustomCategoryAdapter customAdapter;
    private TextView questionCategory;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel = ViewModelProviders.of(this.getActivity()).get(SharedViewModel.class);
        sharedViewModel.loadCategories();
        initRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_fragment, container, false);
        questionCategory = v.findViewById(R.id.category_heading_text_view);
        recyclerView = v.findViewById(R.id.category_recycler_view);

        return v;
    }

    private void initRecyclerView(){

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        customAdapter = new CustomCategoryAdapter();
        recyclerView.setAdapter(customAdapter);
        sharedViewModel.getCurrentDataCategory().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                questionCategory.setText("Interview Questions: " + s);
            }
        });
        sharedViewModel.getCategoryList().observe(getViewLifecycleOwner(), new Observer<List<CategoriesPojo>>(){
            @Override
            public void onChanged(List<CategoriesPojo> categoriesPojos) {
                customAdapter.setData(categoriesPojos);
            }
        });

    }
}
