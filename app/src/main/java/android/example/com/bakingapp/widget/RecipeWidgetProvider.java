package android.example.com.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.example.com.bakingapp.MainActivity;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.utilities.PreferenceUtils;
import android.widget.RemoteViews;

public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int recipeId, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        String recipeName = PreferenceUtils.getRecipeName(context, recipeId);
        String ingredients = PreferenceUtils.getIngredients(context, recipeId);
        views.setTextViewText(R.id.widget_recipe_title, recipeName);
        views.setTextViewText(R.id.widget_ingredient_list, ingredients);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.recipe_widget_layout, mainPendingIntent);

        Intent prevRecipeIntent = new Intent(context, RecipeWidgetService.class);
        prevRecipeIntent.setAction(RecipeWidgetService.ACTION_PREV_RECIPE);
        PendingIntent prevRecipePendingIntent = PendingIntent.getService(context, 0, prevRecipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_prev_button, prevRecipePendingIntent);

        Intent nextRecipeIntent = new Intent(context, RecipeWidgetService.class);
        nextRecipeIntent.setAction(RecipeWidgetService.ACTION_NEXT_RECIPE);
        PendingIntent nextRecipePendingIntent = PendingIntent.getService(context, 0, nextRecipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_next_button, nextRecipePendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeWidgetService.startActionUpdateRecipeWidgets(context);
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int recipeId, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeId, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

