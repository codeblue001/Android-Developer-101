package code.blue.androiddeveloper101.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;
import code.blue.androiddeveloper101.viewmodel.ExpandableRecyclerViewAdapter;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;

public class CategoryQuestionFragment extends Fragment {
    private static final String TAG = "CategoryQuestionFragmen";
    private SharedViewModel sharedViewModel;
    private RecyclerView expanderRecyclerView;
    private ExpandableRecyclerViewAdapter expandableRecyclerViewAdapter;

    public static CategoryQuestionFragment newInstance() {
        return new CategoryQuestionFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel = ViewModelProviders.of(this.getActivity()).get(SharedViewModel.class);
        sharedViewModel.loadCategories();
        initExpandableView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_question_fragment, container, false);
        expanderRecyclerView = v.findViewById(R.id.rv_category_question);

        return v;
    }

    private void initExpandableView(){
        expandableRecyclerViewAdapter = new ExpandableRecyclerViewAdapter(getActivity());
        expanderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        expanderRecyclerView.setAdapter(expandableRecyclerViewAdapter);

        sharedViewModel.getCategoryList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {

            @Override
            public void onChanged(List<String> strings) {
//                Log.d(TAG, "onChanged (getCategoryList) : " + strings.size());
                expandableRecyclerViewAdapter.setCategoryList(strings);
            }
        });

        sharedViewModel.getQuestionMap().observe(getViewLifecycleOwner(), new Observer<List<List<QuestionPojo>>>() {

            @Override
            public void onChanged(List<List<QuestionPojo>> lists) {
//                Log.d(TAG, "onChanged (getQuestionMap) : " + lists.size());
                expandableRecyclerViewAdapter.setQuestionList(lists);

            }
        });


    }

}
