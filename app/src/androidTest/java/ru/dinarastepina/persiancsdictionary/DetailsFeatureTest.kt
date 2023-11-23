package ru.dinarastepina.persiancsdictionary

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class DetailsFeatureTest : BaseUITest() {
    @Test
    fun displaysWordDetails() {
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.english_tv),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        ViewMatchers.withId(R.id.dictionary_rv),
                        0
                    )
                )
            )
        ).perform(ViewActions.click())

        assertDisplayed(".aero")
        assertDisplayed("Air,\ntransport industry,\nحوزه اینترنتی صنعت حمل ونقل هوایی")
    }
}