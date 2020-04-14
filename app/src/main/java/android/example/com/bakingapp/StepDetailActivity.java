package android.example.com.bakingapp;

import android.content.Intent;
import android.example.com.bakingapp.databinding.ActivityStepDetailBinding;
import android.example.com.bakingapp.model.Ingredient;
import android.example.com.bakingapp.model.Step;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {
    private static final String TAG = StepDetailActivity.class.getSimpleName();

    private ActivityStepDetailBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate() there is previously saved state. do not create new fragments."); // TODO : check!
            return;
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_step_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        if (intent == null) {
            Log.e(TAG, "onCreate() intent is null.");
            return;
        }

        if (intent.hasExtra(RecipeConstant.KEY_RECIPE_NAME)) {
            String recipeName = intent.getStringExtra(RecipeConstant.KEY_RECIPE_NAME);
            Log.d(TAG, "onCreate() recipe: " + recipeName);

            setTitle(recipeName);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (intent.hasExtra(RecipeConstant.KEY_RECIPE_INGREDIENTS)) {
            ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra(RecipeConstant.KEY_RECIPE_INGREDIENTS);
            if (ingredients != null) {
                Log.d(TAG, "onCreate() ingredients count: " + ingredients.size());

                showOrHideIngredients(true);

                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(RecipeConstant.KEY_RECIPE_INGREDIENTS, ingredients);
                ingredientsFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.ingredients_container, ingredientsFragment)
                        .commit();
            }
        }

        if (intent.hasExtra(RecipeConstant.KEY_RECIPE_STEP)) {
            Step step = intent.getParcelableExtra(RecipeConstant.KEY_RECIPE_STEP);
            if (step != null) {
                Log.d(TAG, "onCreate() step: " + step.getShortDescription());

                showOrHideIngredients(false);

                StepFragment stepFragment = new StepFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(RecipeConstant.KEY_RECIPE_STEP, step);
                stepFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.step_container, stepFragment)
                        .commit();
            }
        }
    }

    private void showOrHideIngredients(boolean show) {
        if (show) {
            mBinding.ingredientsContainer.setVisibility(View.VISIBLE);
            mBinding.stepContainer.setVisibility(View.GONE);
        } else {
            mBinding.ingredientsContainer.setVisibility(View.GONE);
            mBinding.stepContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
