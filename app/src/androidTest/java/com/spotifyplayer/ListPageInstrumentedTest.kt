package com.spotifyplayer


import android.content.Intent
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.spotifyplayer.ui.ListWithoutPaging
import com.spotifyplayer.ui.SplashActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


/**
 *
 * Instrumented test, which will execute on an Android device.
 *
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ListPageInstrumentedTest {

    @get:Rule
    var testRule = CountingTaskExecutorRule()

    @get:Rule
    var mActivityRule = ActivityTestRule(SplashActivity::class.java, true, false)

    @Test
    @Throws(InterruptedException::class, TimeoutException::class)
    fun showListWithoutPaging() {
        val intent = ListWithoutPaging.intentFor(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            isTestMode = true
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent)
        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerView)
        MatcherAssert.assertThat(recyclerView.adapter, CoreMatchers.notNullValue())
        waitForAdapterChange(recyclerView)
        MatcherAssert.assertThat(recyclerView.adapter?.itemCount, CoreMatchers.`is`(4))
    }

    private fun waitForAdapterChange(recyclerView: RecyclerView) {
        val latch = CountDownLatch(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            recyclerView.adapter?.registerAdapterDataObserver(
                object : RecyclerView.AdapterDataObserver() {
                    override fun onChanged() {
                        latch.countDown()
                    }

                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        latch.countDown()
                    }
                })
        }
        testRule.drainTasks(1, TimeUnit.SECONDS)
        if (recyclerView.adapter?.itemCount ?: 0 > 0) {
            return
        }
        assertThat(latch.await(10, TimeUnit.SECONDS), CoreMatchers.`is`(true))
    }
}
