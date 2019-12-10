package code.blue.androiddeveloper101.view;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.CategoriesPojo;
import code.blue.androiddeveloper101.viewmodel.CustomCategoryAdapter;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;

public class CategoryFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerView;
    private CustomCategoryAdapter customAdapter;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_fragment, container, false);
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        sharedViewModel.loadCategories();
        recyclerView = v.findViewById(R.id.category_recycler_view);
        initRecyclerView();

        return v;
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        customAdapter = new CustomCategoryAdapter();
        recyclerView.setAdapter(customAdapter);
        sharedViewModel.getCategoryList().observe(getViewLifecycleOwner(), new Observer<List<CategoriesPojo>>(){
            @Override
            public void onChanged(List<CategoriesPojo> categoriesPojos) {
                customAdapter.setData(categoriesPojos);
            }
        });

    }
}
