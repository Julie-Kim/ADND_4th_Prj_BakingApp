package android.example.com.bakingapp.utilities;

import android.example.com.bakingapp.model.Ingredient;
import android.example.com.bakingapp.model.Recipe;
import android.example.com.bakingapp.model.Step;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeJsonUtils {
    private static final String TAG = RecipeJsonUtils.class.getSimpleName();

    private static final String JSON_TAG_NAME = "name";

    private static final String JSON_TAG_INGREDIENTS = "ingredients";
    private static final String JSON_TAG_QUANTITY = "quantity";
    private static final String JSON_TAG_MEASURE = "measure";
    private static final String JSON_TAG_INGREDIENT = "ingredient";

    private static final String JSON_TAG_STEPS = "steps";
    private static final String JSON_TAG_ID = "id";
    private static final String JSON_TAG_SHORTDESCRIPTION = "shortDescription";
    private static final String JSON_TAG_DESCRIPTION = "description";
    private static final String JSON_TAG_VIDEOURL = "videoURL";
    private static final String JSON_TAG_THUMBNAILURL = "thumbnailURL";

    private static final String JSON_TAG_IMAGE = "image";

    public static ArrayList<Recipe> parseRecipeJson(String json) {
        ArrayList<Recipe> recipeList = new ArrayList<>();

        if (json == null || TextUtils.isEmpty(json)) {
            Log.e(TAG, "parseRecipeJson() json string is empty.");
            return recipeList;
        }

        try {
            JSONArray recipeArray = new JSONArray(json);
            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject recipeObject = recipeArray.optJSONObject(i);

                String name = recipeObject.optString(JSON_TAG_NAME);

                JSONArray ingredientsArray = recipeObject.optJSONArray(JSON_TAG_INGREDIENTS);
                ArrayList<Ingredient> ingredientList = new ArrayList<>();
                if (ingredientsArray != null) {
                    for (int j = 0; j < ingredientsArray.length(); j++) {
                        JSONObject ingredientObject = ingredientsArray.optJSONObject(j);
                        float quantity = (float) ingredientObject.optDouble(JSON_TAG_QUANTITY);
                        String measure = ingredientObject.optString(JSON_TAG_MEASURE);
                        String ingredient = ingredientObject.optString(JSON_TAG_INGREDIENT);

                        ingredientList.add(new Ingredient(quantity, measure, ingredient));
                    }
                }

                JSONArray stepsArray = recipeObject.optJSONArray(JSON_TAG_STEPS);
                ArrayList<Step> stepList = new ArrayList<>();
                if (stepsArray != null) {
                    for (int k = 0; k < stepsArray.length(); k++) {
                        JSONObject stepObject = stepsArray.optJSONObject(k);
                        int id = stepObject.optInt(JSON_TAG_ID);
                        String shortDescription = stepObject.optString(JSON_TAG_SHORTDESCRIPTION);
                        String description = stepObject.optString(JSON_TAG_DESCRIPTION);
                        String videoUrl = stepObject.optString(JSON_TAG_VIDEOURL);
                        String thumbnailUrl = stepObject.optString(JSON_TAG_THUMBNAILURL);

                        stepList.add(new Step(id, shortDescription, description, videoUrl, thumbnailUrl));
                    }
                }

                String image = recipeObject.optString(JSON_TAG_IMAGE);

                Recipe recipe = new Recipe(name, ingredientList, stepList, image);
                Log.d(TAG, "parseRecipeJson, [" + i + "] " + recipe.toString());
                recipeList.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }
}
