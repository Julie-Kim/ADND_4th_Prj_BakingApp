package android.example.com.bakingapp;

import android.example.com.bakingapp.databinding.DetailListItemBinding;
import android.example.com.bakingapp.model.Ingredient;
import android.example.com.bakingapp.model.Step;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailListAdapterViewHolder> {
    private static final String TAG = DetailListAdapter.class.getSimpleName();

    private final DetailOnClickHandler mOnClickHandler;

    private boolean mTwoPane;
    private int mSelectedPosition;

    public interface DetailOnClickHandler {
        void onClick();

        void onClick(int stepIndex);
    }

    private ArrayList<Ingredient> mIngredientList = new ArrayList<>();
    private ArrayList<Step> mStepList = new ArrayList<>();

    DetailListAdapter(DetailOnClickHandler onClickHandler) {
        mOnClickHandler = onClickHandler;
    }

    public class DetailListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private DetailListItemBinding mBinding;

        DetailListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            setSelectedPosition(getAdapterPosition());

            if (mSelectedPosition == 0) {
                Log.d(TAG, "onClick(), ingredient list");

                mOnClickHandler.onClick();
            } else {
                int stepIndex = mSelectedPosition - 1;
                Log.d(TAG, "onClick(), clicked step index: " + stepIndex);

                mOnClickHandler.onClick(stepIndex);
            }
        }
    }

    @NonNull
    @Override
    public DetailListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.detail_list_item, parent, false);
        return new DetailListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailListAdapterViewHolder holder, int position) {
        setSelectedState(holder, position);

        if (position == 0) {
            holder.mBinding.tvRecipeDetail.setText(R.string.recipe_ingredients);
        } else {
            String stepDescription = mStepList.get(position - 1).getShortDescription();

            Log.d(TAG, "onBindViewHolder() stepDescription: " + stepDescription);
            holder.mBinding.tvRecipeDetail.setText(stepDescription);
        }
    }

    private void setSelectedState(@NonNull DetailListAdapterViewHolder holder, int position) {
        if (mTwoPane) {
            if (mSelectedPosition == position) {
                holder.mBinding.tvRecipeDetail.setSelected(true);
            } else {
                holder.mBinding.tvRecipeDetail.setSelected(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mIngredientList.isEmpty()) {
            return mStepList.size();
        }
        return mStepList.size() + 1;
    }

    void setIngredientList(ArrayList<Ingredient> ingredientList) {
        mIngredientList.clear();
        mIngredientList.addAll(ingredientList);

        notifyDataSetChanged();
    }

    void setStepList(ArrayList<Step> stepList) {
        mStepList.clear();
        mStepList.addAll(stepList);

        notifyDataSetChanged();
    }

    void setTwoPane(boolean twoPane) {
        mTwoPane = twoPane;
    }

    void setSelectedPosition(int position) {
        if (mTwoPane) {
            mSelectedPosition = position;
            Log.d(TAG, "setSelectedPosition() position: " + position);

            notifyDataSetChanged();
        }
    }
}
