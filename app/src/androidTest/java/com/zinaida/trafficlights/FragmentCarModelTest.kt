package com.zinaida.trafficlights

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FragmentCarModelTest {

    @Test
    fun testCardModelInputError() {
        launchFragmentInContainer<FragmentCarModel>()
        onView(withId(R.id.et_car_model)).perform(typeText("au"), closeSoftKeyboard())
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.et_car_model)).check(matches(hasErrorText(containsString("The minimum length for the model is 3 symbols"))))
    }

    @Test
    fun testCardModelInputCorrect() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInContainer<FragmentCarModel>().onFragment { fragment ->
            navController.setGraph(R.navigation.main_nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.et_car_model)).perform(typeText("audi"), closeSoftKeyboard())
        onView(withId(R.id.btn_start)).perform(click())
        Assert.assertTrue(navController.currentDestination?.id == R.id.fragmentTrafficLights)
    }
}