package android.example.com.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.example.com.bakingapp.MainActivity;
import android.example.com.bakingapp.R;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                String recipeName, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.widget_recipe_title, recipeName);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.recipe_widget_layout, mainPendingIntent);

        Intent prevRecipeIntent = new Intent(context, RecipeNavigatingService.class);
        prevRecipeIntent.setAction(RecipeNavigatingService.ACTION_PREV_RECIPE);
        PendingIntent prevRecipePendingIntent = PendingIntent.getService(context, 0, prevRecipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_prev_button, prevRecipePendingIntent);

        Intent nextRecipeIntent = new Intent(context, RecipeNavigatingService.class);
        nextRecipeIntent.setAction(RecipeNavigatingService.ACTION_NEXT_RECIPE);
        PendingIntent nextRecipePendingIntent = PendingIntent.getService(context, 0, nextRecipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_next_button, nextRecipePendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeNavigatingService.startActionUpdateRecipeWidgets(context);
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, String recipeName, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeName, appWidgetId);
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

