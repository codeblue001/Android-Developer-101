package code.blue.androiddeveloper101.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import code.blue.androiddeveloper101.R;
import code.blue.androiddeveloper101.model.FavQuestionDatabase;
import code.blue.androiddeveloper101.viewmodel.SharedViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FavQuestionDatabase appDb;
    private SharedViewModel sharedViewModel;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "DEBUG: onCreate: savedInstanceState -> " + (savedInstanceState == null));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDb = FavQuestionDatabase.getInstance(this);
        toolbar = findViewById(R.id.toolbar);

        if(savedInstanceState == null){
            sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
            sharedViewModel.loadCategories();
            SplashFragment splashFragment = SplashFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, splashFragment)
                    .commit();
        }
        else{
            Log.d(TAG, "onCreate: after configuration change");
            setSupportActionBar(toolbar);
        }

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHomeScreen();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.search:
                break;
            case R.id.saved_questions:
                FavQuestionListFragment favQuestionListFragment = FavQuestionListFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, favQuestionListFragment, "favQuestionListFragment")
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToHomeScreen(){
        Log.d(TAG, "DEBUG: goToHomeScreen: ");

        CategoryQuestionFragment categoryQuestionFragment = CategoryQuestionFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, categoryQuestionFragment)
                .commit();
        setSupportActionBar(toolbar);
    }
}
