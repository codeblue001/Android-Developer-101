package code.blue.androiddeveloper101.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
//    private SharedViewModel sharedViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
//        sharedViewModel.loadQuesAns("Basics");

        CategoryFragment categoryFragment = CategoryFragment.newInstance();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, categoryFragment).commit();
    }
}
