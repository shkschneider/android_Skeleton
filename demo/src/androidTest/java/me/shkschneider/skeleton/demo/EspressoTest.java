package me.shkschneider.skeleton.demo;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

public class EspressoTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public EspressoTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void test() {
        Espresso.onView(ViewMatchers.withId(R.id.toolbar)).check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.menu_about)).check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.menu_about)).check(ViewAssertions.matches(ViewMatchers.isClickable()));
        Espresso.onView(ViewMatchers.withId(R.id.github)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withId(R.id.github)).check(ViewAssertions.matches(ViewMatchers.isClickable()));
        // Espresso.pressBack();
        // Espresso.onView(ViewMatchers.withContentDescription(R.string.abc_action_bar_up_description)).perform(ViewActions.click());
    }

}