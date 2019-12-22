package code.blue.androiddeveloper101.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;
import code.blue.androiddeveloper101.viewmodel.ExpandableRecyclerViewAdapter;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;

public class SearchFragment extends Fragment {

    private SharedViewModel sharedViewModel;


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel = ViewModelProviders.of(this.getActivity()).get(SharedViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_question_fragment, container, false);

        return v;
    }

//    private void initExpandableView(){
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        expandableRecyclerViewAdapter = new ExpandableRecyclerViewAdapter(getActivity(), mLayoutManager);
//
//        expanderRecyclerView.setLayoutManager(mLayoutManager);
//        expanderRecyclerView.setAdapter(expandableRecyclerViewAdapter);
//
//        sharedViewModel.getCategoryList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
//
//            @Override
//            public void onChanged(List<String> strings) {
////                Log.d(TAG, "onChanged (getCategoryList) : " + strings.size());
//                expandableRecyclerViewAdapter.setCategoryList(strings);
//            }
//        });
//
//        sharedViewModel.getQuestionMap().observe(getViewLifecycleOwner(), new Observer<List<List<QuestionPojo>>>() {
//
//            @Override
//            public void onChanged(List<List<QuestionPojo>> lists) {
////                Log.d(TAG, "onChanged (getQuestionMap) : " + lists.size());
//                expandableRecyclerViewAdapter.setQuestionList(lists);
//
//            }
//        });
//
//    }
}
