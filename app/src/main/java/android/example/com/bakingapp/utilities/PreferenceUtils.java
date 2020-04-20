package android.example.com.bakingapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.example.com.bakingapp.model.Ingredient;

import java.util.ArrayList;

public class PreferenceUtils {

    private static final String PREF_RECIPE = "pref_recipe";
    private static final String PREF_KEY_WIDGET_RECIPE_ID = "widget_recipe_id";
    private static final String RECIPE_TOTAL_NUM = "recipe_total_num";
    private static final int RECIPE_ID_DEFAULT_VALUE = 1;

    private static final String PREF_RECIPE_NAME = "pref_recipe_name";

    private static final String PREF_INGREDIENTS = "pref_ingredients";

    public static void setRecipeTotalNum(Context context, int totalNum) {
        SharedPreferences pref = context.getSharedPreferences(PREF_RECIPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(RECIPE_TOTAL_NUM, totalNum);
        editor.apply();
    }

    public static int getRecipeTotalNum(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_RECIPE, Context.MODE_PRIVATE);
        return pref.getInt(RECIPE_TOTAL_NUM, 0);
    }

    public static void setWidgetRecipeId(Context context, int recipeId) {
        SharedPreferences pref = context.getSharedPreferences(PREF_RECIPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_KEY_WIDGET_RECIPE_ID, recipeId);
        editor.apply();
    }

    public static int getWidgetRecipeId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_RECIPE, Context.MODE_PRIVATE);
        int recipeId = pref.getInt(PREF_KEY_WIDGET_RECIPE_ID, RECIPE_ID_DEFAULT_VALUE);

        if (recipeId < RECIPE_ID_DEFAULT_VALUE) {
            recipeId = RECIPE_ID_DEFAULT_VALUE;
        }
        return recipeId;
    }

    public static void setRecipeName(Context context, int recipeId, String recipeName) {
        SharedPreferences pref = context.getSharedPreferences(PREF_RECIPE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(String.valueOf(recipeId), recipeName);
        editor.apply();
    }

    public static String getRecipeName(Context context, int recipeId) {
        SharedPreferences pref = context.getSharedPreferences(PREF_RECIPE_NAME, Context.MODE_PRIVATE);
        return pref.getString(String.valueOf(recipeId), "");
    }

    public static void setIngredientList(Context context, int recipeId, ArrayList<Ingredient> ingredients) {
        StringBuilder ingredientsString = new StringBuilder(ingredients.get(0).toStringForWidget());
        for (int i = 1; i < ingredients.size(); i++) {
            ingredientsString.append("\n").append(ingredients.get(i).toStringForWidget());
        }

        SharedPreferences pref = context.getSharedPreferences(PREF_INGREDIENTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(String.valueOf(recipeId), ingredientsString.toString());
        editor.apply();
    }

    public static String getIngredients(Context context, int recipeId) {
        SharedPreferences pref = context.getSharedPreferences(PREF_INGREDIENTS, Context.MODE_PRIVATE);
        return pref.getString(String.valueOf(recipeId), "");
    }
}
