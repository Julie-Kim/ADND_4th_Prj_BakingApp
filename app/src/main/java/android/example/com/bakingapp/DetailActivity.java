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

        if (intent.hasExtra(RecipeConstant.KEY_RECIPE_NAME)) {
            String recipeName = intent.getStringExtra(RecipeConstant.KEY_RECIPE_NAME);
            Log.d(TAG, "onCreate() recipe: " + recipeName);

            setTitle(recipeName);
        }

        // TODO : TwoPane for Tablet

        mTwoPane = false;   // single-pane mode for phone
    }

    @Override
    public void onItemSelected(int position) {
        // TODO
    }
}
