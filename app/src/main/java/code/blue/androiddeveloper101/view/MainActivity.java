package code.blue.androiddeveloper101.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import code.blue.androiddeveloper101.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
//    private SharedViewModel sharedViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CategoryQuestionFragment categoryQuestionFragment = CategoryQuestionFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, categoryQuestionFragment)
                .addToBackStack(null)
                .commit();
    }
}
