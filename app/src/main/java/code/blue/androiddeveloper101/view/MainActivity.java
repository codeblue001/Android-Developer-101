package code.blue.androiddeveloper101.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.FavQuestionDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FavQuestionDatabase appDb;
//    private SharedViewModel sharedViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDb = FavQuestionDatabase.getInstance(this);

        CategoryQuestionFragment categoryQuestionFragment = CategoryQuestionFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, categoryQuestionFragment)
                .addToBackStack(null)
                .commit();
    }
}
