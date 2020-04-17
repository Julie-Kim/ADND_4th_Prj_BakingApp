package android.example.com.bakingapp;

import android.content.Intent;
import android.example.com.bakingapp.databinding.ActivityDetailBinding;
import android.example.com.bakingapp.model.Ingredient;
import android.example.com.bakingapp.model.Step;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DetailListFragment.OnDetailItemClickListener,
        IngredientsFragment.OnNextButtonClickListener, StepFragment.OnPrevButtonClickListener,
        StepFragment.OnSelectedItemChangeListener {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private ActivityDetailBinding mBinding;

    private boolean mTwoPane;

    private String mRecipeName;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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

        if (mBinding.ingredientsContainer != null && mBinding.stepContainer != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                showIngredientsFragment();
            }
        } else {
            mTwoPane = false;
        }

        setTwoPain();
    }

    private void showIngredientsFragment() {
        if (mTwoPane) {
            if (mIngredients != null) {
                Log.d(TAG, "showIngredientsFragment() ingredients count: " + mIngredients.size());

                showOrHideIngredients(true);

                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(RecipeConstant.KEY_RECIPE_INGREDIENTS, mIngredients);
                ingredientsFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ingredients_container, ingredientsFragment)
                        .commit();
            }
        } else {
            Intent intent = getStepDetailIntent();
            startActivity(intent);
        }
    }

    private void showStepFragment(int stepIndex) {
        if (mTwoPane) {
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
        } else {
            Intent intent = getStepDetailIntent();
            intent.putExtra(RecipeConstant.KEY_SHOW_STEPS, true);
            intent.putExtra(RecipeConstant.KEY_RECIPE_STEP_INDEX, stepIndex);
            startActivity(intent);
        }
    }

    private Intent getStepDetailIntent() {
        Intent intent = new Intent(this, StepDetailActivity.class);
        intent.putExtra(RecipeConstant.KEY_RECIPE_NAME, mRecipeName);
        intent.putParcelableArrayListExtra(RecipeConstant.KEY_RECIPE_INGREDIENTS, mIngredients);
        intent.putExtra(RecipeConstant.KEY_RECIPE_STEPS, mSteps);
        return intent;
    }

    private void showOrHideIngredients(boolean show) {
        if (mBinding.ingredientsContainer == null || mBinding.stepContainer == null) {
            return;
        }

        if (show) {
            mBinding.ingredientsContainer.setVisibility(View.VISIBLE);
            mBinding.stepContainer.setVisibility(View.GONE);
        } else {
            mBinding.ingredientsContainer.setVisibility(View.GONE);
            mBinding.stepContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onIngredientItemSelected() {
        showIngredientsFragment();
    }

    @Override
    public void onStepItemSelected(int stepIndex) {
        showStepFragment(stepIndex);
    }

    @Override
    public void onNextButtonClick() {
        setDetailListSelectedPosition(1);

        showStepFragment(0);
    }

    @Override
    public void onPrevButtonClick() {
        setDetailListSelectedPosition(0);

        showIngredientsFragment();
    }

    private void setTwoPain() {
        DetailListFragment detailListFragment = getDetailListFragment();

        if (detailListFragment != null) {
            detailListFragment.setTwoPane(mTwoPane);
        }
    }

    private void setDetailListSelectedPosition(int position) {
        if (mTwoPane) {
            DetailListFragment detailListFragment = getDetailListFragment();

            if (detailListFragment != null) {
                detailListFragment.setSelectedPosition(position);
            }
        }
    }

    private DetailListFragment getDetailListFragment() {
        return (DetailListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recipe_detail_fragment);
    }

    @Override
    public void onSelectedItemChanged(int selectedPosition) {
        setDetailListSelectedPosition(selectedPosition);
    }
}
