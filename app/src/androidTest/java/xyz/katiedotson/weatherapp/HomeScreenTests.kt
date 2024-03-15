package xyz.katiedotson.weatherapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test

class HomeScreenTests {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun homeScreenDisplaysAsExpected() {
        // basic text
        onView(withText("Opt in to location sharing for the latest weather updates!"))
            .check(matches(isDisplayed()))

        // enable location button
        onView(
            allOf(
                withText("Enable Location"),
            )
        ).check(matches(allOf(isDisplayed(), isClickable())))

        // skip button
        onView(withText("Skip"))
            .check(matches(allOf(isDisplayed(), isClickable())))

    }

    // in other tests would use a fake impl of permission service to do this
    // but it's also good to test that the permission is actually requested
    @Test
    fun enableLocationNavigation() {
        onView(withText("Enable Location")).perform(click())

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.findObject(By.text("While using the app")).click()

        // ideally would set up idling
        Thread.sleep(1000L)

        onView(withText("lat lng search")).check(matches(isDisplayed()))
        onView(withText("main text")).check(matches(isDisplayed()))
        onView(withText("Wintery mix")).check(matches(isDisplayed()))

    }

}