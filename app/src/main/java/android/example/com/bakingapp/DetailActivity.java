package android.example.com.bakingapp;

import android.content.Intent;
import android.example.com.bakingapp.databinding.ActivityDetailBinding;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class DetailActivity extends AppCompatActivity implements DetailListFragment.OnDetailItemClickListener {
    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String KEY_RECIPE_NAME = "name";
    public static final String KEY_RECIPE_INGREDIENTS = "ingredients";
    public static final String KEY_RECIPE_STEPS = "steps";

    private ActivityDetailBinding mBinding;

    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            Log.e(TAG, "onCreate() intent is null.");
            return;
        }

        if (intent.hasExtra(KEY_RECIPE_NAME)) {
            String recipeName = intent.getStringExtra(KEY_RECIPE_NAME);
            Log.d(TAG, "onCreate() recipe: " + recipeName);
            setTitle(recipeName);
        }

//        if (intent.hasExtra(KEY_RECIPE_INGREDIENTS)) {
//            ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra(KEY_RECIPE_INGREDIENTS);
//            if (ingredients != null) {
//                Log.d(TAG, "onCreate() ingredients count: " + ingredients.size());
//            }
//        }
//
//        if (intent.hasExtra(KEY_RECIPE_STEPS)) {
//            ArrayList<Step> steps = intent.getParcelableArrayListExtra(KEY_RECIPE_STEPS);
//            if (steps != null) {
//                Log.d(TAG, "onCreate() steps count: " + steps.size());
//            }
//        }

        // TODO : TwoPane for Tablet

        mTwoPane = false;   // single-pane mode for phone
    }

    @Override
    public void onItemSelected(int position) {
        // TODO
    }
}
