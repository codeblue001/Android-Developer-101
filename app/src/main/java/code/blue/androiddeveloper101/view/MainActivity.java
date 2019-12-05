package code.blue.androiddeveloper101.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;

public class MainActivity extends AppCompatActivity {

    private SharedViewModel sharedViewModel;
    TextView mainText;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);

        initUI();
        loadQuesAnsList();
    }

    private void initUI(){
        mainText = findViewById(R.id.tv_question_title);
        recyclerView = findViewById(R.id.rv_question_list);
    }

    private void loadQuesAnsList(){
        sharedViewModel.loadQuesAns();
    }


}