package android.example.com.bakingapp;

import android.content.Context;
import android.example.com.bakingapp.databinding.FragmentIngredientsBinding;
import android.example.com.bakingapp.model.Ingredient;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {
    private static final String TAG = IngredientsFragment.class.getSimpleName();

    private FragmentIngredientsBinding mBinding;
    private IngredientsAdapter mAdapter;

    private OnNextButtonClickListener mNextButtonClickListener;

    public interface OnNextButtonClickListener {
        void onNextButtonClick();
    }

    public IngredientsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mNextButtonClickListener = (OnNextButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnNextButtonClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.rvIngredient.setLayoutManager(layoutManager);
        mBinding.rvIngredient.setHasFixedSize(true);

        mAdapter = new IngredientsAdapter();
        mBinding.rvIngredient.setAdapter(mAdapter);

        if (getArguments() != null) {
            ArrayList<Ingredient> ingredients = getArguments().getParcelableArrayList(RecipeConstant.KEY_RECIPE_INGREDIENTS);
            mAdapter.setIngredientList(ingredients);

            int servings = getArguments().getInt(RecipeConstant.KEY_RECIPE_SERVINGS);
            setServingsText(servings);
        }

        mBinding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNextButtonClickListener.onNextButtonClick();
            }
        });

        return mBinding.getRoot();
    }

    private void setServingsText(int servings) {
        if (servings > 0) {
            mBinding.tvServings.setText(getString(R.string.servings, servings));
            mBinding.tvServings.setVisibility(View.VISIBLE);
        } else {
            mBinding.tvServings.setVisibility(View.GONE);
        }
    }
}
