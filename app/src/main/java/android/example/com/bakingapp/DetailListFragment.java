package android.example.com.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.example.com.bakingapp.databinding.FragmentDetailListBinding;
import android.example.com.bakingapp.model.Ingredient;
import android.example.com.bakingapp.model.Step;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

public class DetailListFragment extends Fragment implements DetailListAdapter.DetailOnClickHandler {
    private static final String TAG = DetailListFragment.class.getSimpleName();

    private FragmentDetailListBinding mBinding;
    private DetailListAdapter mAdapter;

    private boolean mTwoPane;

    private OnDetailItemClickListener mDetailItemClickListener;

    public interface OnDetailItemClickListener {
        void onIngredientItemSelected();

        void onStepItemSelected(int stepIndex);
    }

    public DetailListFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mDetailItemClickListener = (OnDetailItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDetailItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_list, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.rvDetailList.setLayoutManager(layoutManager);
        mBinding.rvDetailList.setHasFixedSize(true);

        mAdapter = new DetailListAdapter(this);
        mBinding.rvDetailList.setAdapter(mAdapter);

        if (getActivity() != null) {
            Intent intent = getActivity().getIntent();

            if (intent != null) {
                if (intent.hasExtra(RecipeConstant.KEY_RECIPE_INGREDIENTS)) {
                    ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra(RecipeConstant.KEY_RECIPE_INGREDIENTS);
                    if (ingredients != null) {
                        Log.d(TAG, "onCreateView() ingredients count: " + ingredients.size());

                        mAdapter.setIngredientList(ingredients);
                    }
                }

                if (intent.hasExtra(RecipeConstant.KEY_RECIPE_STEPS)) {
                    ArrayList<Step> steps = intent.getParcelableArrayListExtra(RecipeConstant.KEY_RECIPE_STEPS);
                    if (steps != null) {
                        Log.d(TAG, "onCreateView() steps count: " + steps.size());

                        mAdapter.setStepList(steps);
                    }
                }
            }
        }

        return mBinding.getRoot();
    }

    @Override
    public void onClick() {
        mDetailItemClickListener.onIngredientItemSelected();
    }

    @Override
    public void onClick(int stepIndex) {
        mDetailItemClickListener.onStepItemSelected(stepIndex);
    }

    void setTwoPane(boolean twoPane) {
        mTwoPane = twoPane;
        mAdapter.setTwoPane(mTwoPane);
    }

    void setSelectedPosition(int position) {
        mBinding.rvDetailList.smoothScrollToPosition(position);
        mAdapter.setSelectedPosition(position);
    }
}
