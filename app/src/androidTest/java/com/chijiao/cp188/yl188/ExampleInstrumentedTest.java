package com.chijiao.cp188.yl188;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.yasn.purchase.activity.MyOrderActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import www.xcd.com.mylibrary.utils.SharePrefHelper;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MyOrderActivity> mActivityRule =
            new ActivityTestRule<>(MyOrderActivity.class);

    @Test
    public void demonstrateIntentPrep() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25" +
                "hbWUiOiIxODIxMDg3NDQ1MiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsIn" +
                "RydXN0Il0sImV4cCI6MTUzMDg1NjMxMSwiYXV0aG9yaXRpZXMiOlsiWUFTT" +
                "l9TSE9QX0NMSUVOVCIsIllBU05fU0hPUF9UUlVTVEVEX0NMSUVOVCJdLCJqdG" +
                "kiOiI2ZjUzNjU3MC03YzY2LTQ5NWQtOWU0Mi1hZWQxZGE0MDZjYTMiLCJjbGl" +
                "lbnRfaWQiOiJ5YXNuLXNob3AiLCJtZW1iZXJJZCI6MjQ3ODZ9.0o7L9XgJAmWw" +
                "cCBZrgfUh_H2ct8homic8BGWBBHCrLo";
        SharePrefHelper.getInstance(mActivityRule.getActivity()).putSpString("token",token);
        // 对于Id为R.id.display的View: 检测内容是否是"Text"
        // http://www.manongjc.com/article/1532.html
//        onView(withId(R.id.display)).check(matches(withText("Test")));
    }
}
