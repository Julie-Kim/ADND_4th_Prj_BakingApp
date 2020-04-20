package android.example.com.bakingapp;

import android.example.com.bakingapp.model.Ingredient;
import android.os.Bundle;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class IngredientsFragmentTest {
    @Rule
    public ActivityTestRule<StepDetailActivity> mActivityTestRule =
            new ActivityTestRule<>(StepDetailActivity.class);

    @Before
    public void setup() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient((float) 2.0, "CUP", "Graham Cracker crumbs"));
        ingredients.add(new Ingredient((float) 6.0, "TBLSP", "unsalted butter"));
        ingredients.add(new Ingredient((float) 0.5, "CUP", "granulated sugar"));

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(RecipeConstant.KEY_RECIPE_INGREDIENTS, ingredients);
        bundle.putInt(RecipeConstant.KEY_RECIPE_SERVINGS, 8);
        ingredientsFragment.setArguments(bundle);

        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.ingredients_container, ingredientsFragment)
                .commit();
    }

    @Test
    public void testIngredientFragment_DisplayTitle() {

        onView(ViewMatchers.withId(R.id.tv_recipe_ingredients_title)).check(matches(withText("Recipe Ingredients")));
    }

    @Test
    public void testIngredientFragment_DisplayServings() {

        onView(ViewMatchers.withId(R.id.tv_servings)).check(matches(withText("(8 Servings)")));
    }

    @Test
    public void testIngredientFragment_ClickNextButton() {

        onView(ViewMatchers.withId(R.id.next_button)).check(matches(isClickable()));
    }
}
