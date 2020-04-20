package android.example.com.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.utilities.PreferenceUtils;
import android.util.Log;

public class RecipeWidgetService extends IntentService {
    private static final String TAG = RecipeWidgetService.class.getSimpleName();

    public static final String ACTION_PREV_RECIPE = "android.example.com.bakingapp.widget.action.prev_recipe";
    public static final String ACTION_NEXT_RECIPE = "android.example.com.bakingapp.widget.action.next_recipe";

    public static final String ACTION_UPDATE_RECIPE_WIDGETS = "android.example.com.bakingapp.widget.action.update_recipe_widgets";

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startActionUpdateRecipeWidgets(Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_PREV_RECIPE.equals(action)) {
                handleActionPrevRecipe();
            } else if (ACTION_NEXT_RECIPE.equals(action)) {
                handleActionNextRecipe();
            } else if (ACTION_UPDATE_RECIPE_WIDGETS.equals(action)) {
                handleActionUpdateRecipeWidgets();
            }
        }
    }

    private void handleActionPrevRecipe() {
        int recipeId = PreferenceUtils.getWidgetRecipeId(this) - 1;
        int totalNum = PreferenceUtils.getRecipeTotalNum(this);
        recipeId = (recipeId + totalNum) % (totalNum + 1);
        Log.d(TAG, "handleActionPrevRecipe() recipeId: " + recipeId);
        PreferenceUtils.setWidgetRecipeId(this, recipeId);

        startActionUpdateRecipeWidgets(this);
    }

    private void handleActionNextRecipe() {
        int recipeId = PreferenceUtils.getWidgetRecipeId(this) + 1;
        int totalNum = PreferenceUtils.getRecipeTotalNum(this);
        recipeId = recipeId % (totalNum + 1);
        Log.d(TAG, "handleActionNextRecipe() recipeId: " + recipeId);
        PreferenceUtils.setWidgetRecipeId(this, recipeId);

        startActionUpdateRecipeWidgets(this);
    }

    private void handleActionUpdateRecipeWidgets() {
        int recipeId = PreferenceUtils.getWidgetRecipeId(this);
        String recipeName = PreferenceUtils.getRecipeName(this, recipeId);
        String ingredients = PreferenceUtils.getIngredients(this, recipeId);
        Log.d(TAG, "handleActionUpdateRecipeWidgets() recipeId: " + recipeId + ", recipeName: " + recipeName + ", ingredients: " + ingredients);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_widget_layout);
        RecipeWidgetProvider.updateRecipeWidgets(this, appWidgetManager, recipeId, appWidgetIds);
    }
}
