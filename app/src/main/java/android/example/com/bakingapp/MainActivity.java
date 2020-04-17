package android.example.com.bakingapp;

import android.content.Intent;
import android.example.com.bakingapp.databinding.ActivityMainBinding;
import android.example.com.bakingapp.model.Recipe;
import android.example.com.bakingapp.utilities.RetrofitConnection;
import android.example.com.bakingapp.utilities.RetrofitInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeOnClickHandler {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DEFAULT_RECIPE_WIDTH = 800;

    private ActivityMainBinding mBinding;
    private RecipeListAdapter mAdapter;

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

        RetrofitInterface retrofitInterface = RetrofitConnection.getRetrofitInstance().create(RetrofitInterface.class);
        Call<ArrayList<Recipe>> call = retrofitInterface.getRecipeList();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Recipe> recipeList = response.body();
                    Log.d(TAG, "onResponse() size of recipeList: " + recipeList.size());

                    updateRecipeList(recipeList);
                } else {
                    showOrHideLoadingIndicator(false);
                    Log.e(TAG, "onResponse() response is not successful.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                showOrHideLoadingIndicator(false);

                Log.e(TAG, "onFailure() : " + t.toString());
            }
        });

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
        intent.putExtra(RecipeConstant.KEY_RECIPE_NAME, recipe.getName());
        intent.putParcelableArrayListExtra(RecipeConstant.KEY_RECIPE_INGREDIENTS, recipe.getIngredients());
        intent.putParcelableArrayListExtra(RecipeConstant.KEY_RECIPE_STEPS, recipe.getSteps());
        intent.putExtra(RecipeConstant.KEY_RECIPE_SERVINGS, recipe.getServings());

        startActivity(intent);
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
