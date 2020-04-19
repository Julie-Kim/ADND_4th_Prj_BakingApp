package android.example.com.bakingapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

    private static final String PREF_NAME = "pref";
    private static final String PREF_KEY_WIDGET_RECIPE_ID = "widget_recipe_id";
    private static final int RECIPE_ID_DEFAULT_VALUE = 0;

    public static void setWidgetRecipeId(Context context, int recipeId) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_KEY_WIDGET_RECIPE_ID, recipeId);
        editor.apply();
    }

    public static int getWidgetRecipeId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int recipeId = pref.getInt(PREF_KEY_WIDGET_RECIPE_ID, RECIPE_ID_DEFAULT_VALUE);

        if (recipeId < RECIPE_ID_DEFAULT_VALUE) {
            recipeId = RECIPE_ID_DEFAULT_VALUE;
        }
        return recipeId;
    }
}
