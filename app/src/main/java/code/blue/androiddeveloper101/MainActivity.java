package code.blue.androiddeveloper101;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import code.blue.androiddeveloper101.Model.QuestionPojo;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private ArrayList<QuestionPojo> mQuestion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        initRecyclerView();
        insertFakeQuestions();
    }

    private void insertFakeQuestions(){
        for (int i=0; i < 1000; i++){
            QuestionPojo questionPojo = new QuestionPojo("What is an Intent", "An Intent is an Android messaging Object", "1");
            questionPojo.setNumber( i + ".");
            questionPojo.setQuestion("What is an intent?");
            mQuestion.add(questionPojo);
        }
    }
    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        customAdapter = new CustomAdapter(mQuestion);
        recyclerView.setAdapter(customAdapter);
    }
}
