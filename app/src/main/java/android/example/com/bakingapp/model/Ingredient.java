package android.example.com.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    @SerializedName("quantity")
    private float mQuantity;

    @SerializedName("measure")
    private String mMeasure;

    @SerializedName("ingredient")
    private String mIngredient;

    public Ingredient(float quantity, String measure, String ingredient) {
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    protected Ingredient(Parcel in) {
        mQuantity = in.readFloat();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

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

    public String toStringForWidget() {
        return " • " + mIngredient + " : " + mQuantity + " " + mMeasure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }
}
