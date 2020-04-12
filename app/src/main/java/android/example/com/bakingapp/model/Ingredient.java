package android.example.com.bakingapp.model;

import androidx.annotation.NonNull;

public class Ingredient {

    private float mQuantity;
    private String mMeasure;
    private String mIngredient;

    public Ingredient(float quantity, String measure, String ingredient) {
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    public float getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }

    @NonNull
    @Override
    public String toString() {
        return "Quantity: " + mQuantity +
                "\nMeasure: " + mMeasure +
                "\nIngredient: " + mIngredient;
    }
}
