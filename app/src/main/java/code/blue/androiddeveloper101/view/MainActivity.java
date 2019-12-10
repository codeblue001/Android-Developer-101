package code.blue.androiddeveloper101.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.QuestionPojo;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;
import code.blue.androiddeveloper101.viewmodel.CustomAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SharedViewModel sharedViewModel;
    RecyclerView recyclerView;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);

        initUI();
        loadQuesAnsList();

        initRecyclerView();
    }

    private void initUI(){
        recyclerView = findViewById(R.id.rv_question_list);
    }

    private void loadQuesAnsList(){
        Log.d(TAG, "DEBUG: loadQuesAnsList()");
        sharedViewModel.loadQuesAns();
    }

    private void initRecyclerView(){
        Log.d(TAG, "DEBUG: initRecyclerView()");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        customAdapter = new CustomAdapter(this);
        recyclerView.setAdapter(customAdapter);
        sharedViewModel.getQuesAnsList().observe(this, new Observer<List<QuestionPojo>>(){

            @Override
            public void onChanged(List<QuestionPojo> questionPojos) {
                Log.d(TAG, "DEBUG: onChanged");
                customAdapter.setData(questionPojos);
            }
        });

    }
}
