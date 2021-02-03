package com.blazeyannmanion.flyme.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.blazeyannmanion.flyme.R
import junit.framework.TestCase

class MainActivityTest : TestCase() {

    fun test_isFragmentContainerVisible() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()))
    }
}