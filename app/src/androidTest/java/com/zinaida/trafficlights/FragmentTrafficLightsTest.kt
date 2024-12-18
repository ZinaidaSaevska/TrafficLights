package com.zinaida.trafficlights

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith

private const val LIGHT_DURATION = 4000L
private const val ORANGE_LIGHT_DURATION = 1000L

@RunWith(AndroidJUnit4::class)
class FragmentTrafficLightsTest {

    @Test
    fun testFragmentArguments() {
        val carModel = "Audi"
        val args = FragmentTrafficLightsArgs(carModel)

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInContainer<FragmentTrafficLights>(fragmentArgs = args.toBundle()).onFragment { fragment ->
            navController.setGraph(R.navigation.main_nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.fragmentTrafficLights, args.toBundle())
        }

        onView(withId(R.id.tv_car_model)).check(matches(withText(carModel)))
    }

    @Test
    fun testRedLightIsActiveForFourSeconds() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val inactiveColor = ContextCompat.getColor(context, R.color.red_inactive)
        val activeColor = ContextCompat.getColor(context, R.color.red_active)

        val carModel = "Audi"
        val args = FragmentTrafficLightsArgs(carModel)

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInContainer<FragmentTrafficLights>(fragmentArgs = args.toBundle()).onFragment { fragment ->
            navController.setGraph(R.navigation.main_nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.fragmentTrafficLights, args.toBundle())
        }

        onView(withId(R.id.iv_red_light)).check(matches(withColorFilter(activeColor)))

        Thread.sleep(LIGHT_DURATION)

        onView(withId(R.id.iv_red_light)).check(matches(withColorFilter(inactiveColor)))
    }


    @Test
    fun testOrangeLightIsActiveForOneSecond() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val inactiveColor = ContextCompat.getColor(context, R.color.orange_inactive)
        val activeColor = ContextCompat.getColor(context, R.color.orange_active)

        val carModel = "Audi"
        val args = FragmentTrafficLightsArgs(carModel)

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInContainer<FragmentTrafficLights>(fragmentArgs = args.toBundle()).onFragment { fragment ->
            navController.setGraph(R.navigation.main_nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.fragmentTrafficLights, args.toBundle())
        }

        Thread.sleep(LIGHT_DURATION)

        onView(withId(R.id.iv_orange_light)).check(matches(withColorFilter(activeColor)))

        Thread.sleep(ORANGE_LIGHT_DURATION)

        onView(withId(R.id.iv_orange_light)).check(matches(withColorFilter(inactiveColor)))
    }

    @Test
    fun testGreenLightIsActiveForFourSeconds() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val inactiveColor = ContextCompat.getColor(context, R.color.green_inactive)
        val activeColor = ContextCompat.getColor(context, R.color.green_active)

        val carModel = "Audi"
        val args = FragmentTrafficLightsArgs(carModel)

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInContainer<FragmentTrafficLights>(fragmentArgs = args.toBundle()).onFragment { fragment ->
            navController.setGraph(R.navigation.main_nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.fragmentTrafficLights, args.toBundle())
        }

        Thread.sleep(LIGHT_DURATION.plus(ORANGE_LIGHT_DURATION))

        onView(withId(R.id.iv_green_light)).check(matches(withColorFilter(activeColor)))

        Thread.sleep(LIGHT_DURATION)

        onView(withId(R.id.iv_green_light)).check(matches(withColorFilter(inactiveColor)))
    }
}

fun withColorFilter(expectedColor: Int): Matcher<View> {
    return object : BoundedMatcher<View, View>(ImageView::class.java) {

        override fun describeTo(description: Description?) {
            description?.appendText("with color filter: $expectedColor")
        }

        override fun matchesSafely(item: View?): Boolean {
            val colorFilter = PorterDuffColorFilter(expectedColor, PorterDuff.Mode.SRC_ATOP)
            val defaultColor = (item as ImageView).colorFilter
            return defaultColor == colorFilter
        }
    }
}