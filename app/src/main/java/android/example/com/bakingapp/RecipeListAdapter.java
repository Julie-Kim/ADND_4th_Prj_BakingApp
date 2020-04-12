package android.example.com.bakingapp;

import android.example.com.bakingapp.databinding.RecipeListItemBinding;
import android.example.com.bakingapp.model.Recipe;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListAdapterViewHolder> {
    private static final String TAG = RecipeListAdapter.class.getSimpleName();

    private final RecipeOnClickHandler mOnClickHandler;

    public interface RecipeOnClickHandler {
        void onClick(Recipe recipe);
    }

    private ArrayList<Recipe> mRecipeList = new ArrayList<>();

    public RecipeListAdapter(RecipeOnClickHandler onClickHandler) {
        mOnClickHandler = onClickHandler;
    }

    public class RecipeListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecipeListItemBinding mBinding;

        public RecipeListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = mRecipeList.get(getAdapterPosition());
            Log.d(TAG, "onClick(), clicked recipe: " + recipe.getName());

            mOnClickHandler.onClick(recipe);
        }
    }

    @NonNull
    @Override
    public RecipeListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapterViewHolder holder, int position) {
        String recipeName = mRecipeList.get(position).getName();

        Log.d(TAG, "onBindViewHolder() recipe: " + recipeName);
        holder.mBinding.tvRecipeName.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        if (mRecipeList.isEmpty()) {
            return 0;
        }
        return mRecipeList.size();
    }

    public void setRecipeList(ArrayList<Recipe> recipeList) {
        mRecipeList.clear();
        mRecipeList.addAll(recipeList);

        notifyDataSetChanged();
    }
}
