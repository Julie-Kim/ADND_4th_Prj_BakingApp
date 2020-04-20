package android.example.com.bakingapp;

import android.example.com.bakingapp.model.Step;
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
public class StepFragmentTest {
    @Rule
    public ActivityTestRule<StepDetailActivity> mActivityTestRule =
            new ActivityTestRule<>(StepDetailActivity.class);

    @Before
    public void setup() {
        ArrayList<Step> steps = new ArrayList<>();
        steps.add(new Step(0, "Recipe Introduction", "Recipe Introduction", "", ""));
        steps.add(new Step(1, "Starting prep", "1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.", "", ""));
        steps.add(new Step(2, "Prep the cookie crust.", "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4", ""));

        StepFragment stepFragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(RecipeConstant.KEY_RECIPE_STEPS, steps);
        bundle.putInt(RecipeConstant.KEY_RECIPE_STEP_INDEX, 2);
        stepFragment.setArguments(bundle);

        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_container, stepFragment)
                .commit();
    }

    @Test
    public void testStepFragment_DisplayShortDescription() {

        onView(ViewMatchers.withId(R.id.tv_step_short_description)).check(matches(withText("Prep the cookie crust.")));
    }

    @Test
    public void testStepFragment_DisplayDescription() {

        onView(ViewMatchers.withId(R.id.tv_step_description)).check(matches(withText("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.")));
    }

    @Test
    public void testStepFragment_ClickPrevButton() {

        onView(ViewMatchers.withId(R.id.prev_button)).check(matches(isClickable()));
    }
}
