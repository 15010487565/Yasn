package com.chijiao.cp188.yl188;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.yasn.purchasetest.activity.MainActivityNew;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivityNew> mActivityRule =
            new ActivityTestRule<>(MainActivityNew.class);

    @Test
    public void useAppContext() throws Exception {
    }
}
