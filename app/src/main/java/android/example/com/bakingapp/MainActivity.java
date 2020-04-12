package android.example.com.bakingapp;

import android.content.Intent;
import android.example.com.bakingapp.databinding.ActivityMainBinding;
import android.example.com.bakingapp.model.Recipe;
import android.example.com.bakingapp.utilities.NetworkUtils;
import android.example.com.bakingapp.utilities.RecipeJsonUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeOnClickHandler {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DEFAULT_RECIPE_WIDTH = 800;
    private static final String RECIPE_LIST_JSON_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private ActivityMainBinding mBinding;
    private RecipeListAdapter mAdapter;
    private RecyclerView mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, DEFAULT_RECIPE_WIDTH);
        mBinding.rvRecipeList.setLayoutManager(layoutManager);
        mBinding.rvRecipeList.setHasFixedSize(true);

        mAdapter = new RecipeListAdapter(this);
        mBinding.rvRecipeList.setAdapter(mAdapter);

        loadRecipeList();
    }

    private void loadRecipeList() {
        showOrHideRecipeList(true);

        new FetchRecipeListTask(this).execute();
    }

    private void showOrHideRecipeList(boolean show) {
        if (show) {
            mBinding.tvEmptyMessage.setVisibility(View.INVISIBLE);
            mBinding.rvRecipeList.setVisibility(View.VISIBLE);
        } else {
            mBinding.rvRecipeList.setVisibility(View.INVISIBLE);
            mBinding.tvEmptyMessage.setVisibility(View.VISIBLE);
        }
    }

    private void showOrHideLoadingIndicator(boolean show) {
        if (show) {
            mBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
        } else {
            mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_RECIPE_NAME, recipe.getName());

        startActivity(intent);
    }

    private static class FetchRecipeListTask extends AsyncTask<Void, Void, ArrayList<Recipe>> {

        private WeakReference<MainActivity> mActivityReference;

        FetchRecipeListTask(MainActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MainActivity activity = mActivityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e(TAG, "FetchRecipeListTask, onPreExecute() activity is null or is finishing.");
                return;
            }

            activity.showOrHideLoadingIndicator(true);
        }

        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {
            try {
                String recipeJsonResponse = NetworkUtils.getJsonResponse(new URL(RECIPE_LIST_JSON_URL));

                if (recipeJsonResponse != null) {
                    ArrayList<Recipe> recipeList = RecipeJsonUtils.parseRecipeJson(recipeJsonResponse);
                    Log.d(TAG, "FetchRecipeListTask, size of recipeList: " + recipeList.size());

                    return recipeList;
                } else {
                    Log.e(TAG, "FetchRecipeListTask, No json response.");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            super.onPostExecute(recipes);

            MainActivity activity = mActivityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e(TAG, "FetchRecipeListTask, onPostExecute() activity is null or is finishing.");
                return;
            }

            activity.updateRecipeList(recipes);
        }
    }

    private void updateRecipeList(ArrayList<Recipe> recipes) {
        showOrHideLoadingIndicator(false);

        if (recipes != null && !recipes.isEmpty()) {
            showOrHideRecipeList(true);
            mAdapter.setRecipeList(recipes);
        } else {
            showOrHideRecipeList(false);
        }
    }
}
