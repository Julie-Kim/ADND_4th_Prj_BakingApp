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

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity
        implements IngredientsFragment.OnNextButtonClickListener, StepFragment.OnPrevButtonClickListener,
        StepFragment.OnSelectedItemChangeListener {
    private static final String TAG = StepDetailActivity.class.getSimpleName();

    private ActivityStepDetailBinding mBinding;

    private String mRecipeName;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;
    private int mServings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate() there is previously saved state. do not create new fragments.");
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
            mRecipeName = intent.getStringExtra(RecipeConstant.KEY_RECIPE_NAME);
            Log.d(TAG, "onCreate() recipe name: " + mRecipeName);

            setTitle(mRecipeName);
        }

        if (intent.hasExtra(RecipeConstant.KEY_RECIPE_INGREDIENTS)) {
            mIngredients = intent.getParcelableArrayListExtra(RecipeConstant.KEY_RECIPE_INGREDIENTS);
        }

        if (intent.hasExtra(RecipeConstant.KEY_RECIPE_STEPS)) {
            mSteps = intent.getParcelableArrayListExtra(RecipeConstant.KEY_RECIPE_STEPS);
        }

        if (intent.hasExtra(RecipeConstant.KEY_RECIPE_SERVINGS)) {
            mServings = intent.getIntExtra(RecipeConstant.KEY_RECIPE_SERVINGS, 0);
        }

        if (intent.getBooleanExtra(RecipeConstant.KEY_SHOW_STEPS, false)) {
            int stepIndex = intent.getIntExtra(RecipeConstant.KEY_RECIPE_STEP_INDEX, 0);
            showStepFragment(stepIndex);
        } else {
            showIngredientsFragment();
        }
    }

    private void showIngredientsFragment() {
        if (mIngredients != null) {
            Log.d(TAG, "showIngredientsFragment() ingredients count: " + mIngredients.size());

            showOrHideIngredients(true);

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RecipeConstant.KEY_RECIPE_INGREDIENTS, mIngredients);
            bundle.putInt(RecipeConstant.KEY_RECIPE_SERVINGS, mServings);
            ingredientsFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ingredients_container, ingredientsFragment)
                    .commit();
        }
    }

    private void showStepFragment(int stepIndex) {
        if (mSteps != null) {
            Log.d(TAG, "showStepFragment() steps count: " + mSteps.size() + ", step index: " + stepIndex);

            showOrHideIngredients(false);

            StepFragment stepFragment = new StepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RecipeConstant.KEY_RECIPE_STEPS, mSteps);
            bundle.putInt(RecipeConstant.KEY_RECIPE_STEP_INDEX, stepIndex);
            stepFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_container, stepFragment)
                    .commit();
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

    @Override
    public void onNextButtonClick() {
        showStepFragment(0);
    }

    @Override
    public void onPrevButtonClick() {
        showIngredientsFragment();
    }

    @Override
    public void onSelectedItemChanged(int selectedPosition) {
        // no action
    }
}
