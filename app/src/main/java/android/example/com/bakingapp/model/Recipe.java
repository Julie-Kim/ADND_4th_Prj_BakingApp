package android.example.com.bakingapp.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Recipe {

    private String mName;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;
    private String mImageUrl;

    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, String imageUrl) {
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }

    public ArrayList<Step> getSteps() {
        return mSteps;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder recipeString = new StringBuilder("Name: " + mName);

        for (Ingredient ingredient : mIngredients) {
            recipeString.append("\n").append(ingredient.toString());
        }

        for (Step step : mSteps) {
            recipeString.append("\n").append(step.toString());
        }

        recipeString.append("\n").append("Image: ").append(mImageUrl);

        return recipeString.toString();
    }
}
