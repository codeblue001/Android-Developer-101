package code.blue.androiddeveloper101.view;

import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;
import code.blue.androiddeveloper101.viewmodel.SearchAdapter;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private SharedViewModel sharedViewModel;
    private SearchAdapter searchAdapter;
    private RecyclerView searchView;
    public String firstChar = "";


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public void getSearchQuery(String query){
        searchAdapter.getFilter().filter(query);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "DEBUG: onCreate: ");
        super.onCreate(savedInstanceState);
        sharedViewModel = ViewModelProviders.of(this.getActivity()).get(SharedViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedViewModel.getAllQuestions().observe(getViewLifecycleOwner(), new Observer<List<QuestionPojo>>() {
            @Override
            public void onChanged(List<QuestionPojo> questionPojos) {
                searchAdapter.setQuestionList(questionPojos);
                if(!firstChar.equals("")){
                    searchAdapter.getFilter().filter(firstChar);
                    firstChar = "";
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_question_fragment, container, false);
        searchView = v.findViewById(R.id.rv_category_question);
        searchView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchAdapter = new SearchAdapter(getActivity());
        searchView.setAdapter(searchAdapter);
        return v;
    }

}
