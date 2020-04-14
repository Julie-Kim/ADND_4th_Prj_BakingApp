package android.example.com.bakingapp;

import android.example.com.bakingapp.databinding.IngredientListItemBinding;
import android.example.com.bakingapp.model.Ingredient;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientAdapterViewHolder> {
    private static final String TAG = IngredientsAdapter.class.getSimpleName();

    private ArrayList<Ingredient> mIngredientList = new ArrayList<>();

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {

        private IngredientListItemBinding mBinding;

        public IngredientAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    @NonNull
    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_list_item, parent, false);
        return new IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapterViewHolder holder, int position) {
        Ingredient ingredient = mIngredientList.get(position);

        holder.mBinding.tvIngredientName.setText(ingredient.getIngredient());
        holder.mBinding.tvQuantity.setText(String.valueOf(ingredient.getQuantity()));
        holder.mBinding.tvMeasure.setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        if (mIngredientList.isEmpty()) {
            return 0;
        }
        return mIngredientList.size();
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        mIngredientList.clear();
        mIngredientList.addAll(ingredientList);

        notifyDataSetChanged();
    }
}
