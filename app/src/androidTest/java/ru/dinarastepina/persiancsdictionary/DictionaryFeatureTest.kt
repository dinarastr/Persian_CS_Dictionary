package ru.dinarastepina.persiancsdictionary

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DictionaryFeatureTest: BaseUITest() {
    @Test
    fun displayTitle() {
        assertDisplayed("Dictionary")
    }

    @Test
    fun displayListOfWords() {
        assertRecyclerViewItemCount(R.id.dictionary_rv, 20)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.english_tv),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(withId(R.id.dictionary_rv), 0)
                )
            )
        ).check(ViewAssertions.matches(ViewMatchers.withText(".aero")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.persian_tv),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(withId(R.id.dictionary_rv), 0)
                )
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText("Air,\ntransport industry,\nحوزه اینترنتی صنعت حمل ونقل هوایی")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun navigateToDetailsScreen() {
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.persian_tv),
                ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.dictionary_rv), 0))
            )
        )
            .perform(ViewActions.click())
        assertDisplayed(R.id.word_details_root)
    }
}