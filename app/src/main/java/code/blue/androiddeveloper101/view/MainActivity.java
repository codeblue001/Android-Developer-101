package code.blue.androiddeveloper101.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
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
    SearchView searchView;
    SearchFragment searchFragment;

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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_home_white_24dp);
        }

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHomeScreen();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchViewItem = menu.findItem(R.id.search);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint("Search for Product,Brands...");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "DEBUG: onClick: searchView clicked");
            }
        });

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // This is your adapter that will be filtered
                if(searchFragment == null){
                    Log.d(TAG, "onQueryTextChange: searchFragment == null");
                    searchFragment = SearchFragment.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, searchFragment, "searchFragment")
                            .commit();
                    searchFragment.firstChar = newText;
                }
                else{
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, searchFragment, "searchFragment")
                            .commit();
                    searchFragment.getSearchQuery(newText);
                }

                return true;
            }

            public boolean onQueryTextSubmit(String query) {

                searchFragment.getSearchQuery(query);
                closeKeyBoard();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            goToHomeScreen();
            return true;
        }

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_home_white_24dp);
    }

    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
