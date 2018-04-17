package com.chijiao.cp188.yl188;

import org.junit.Test;

import static org.slf4j.MDC.get;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        String deviceNO="1";
        int temp_int=Integer.parseInt(deviceNO);
        String url=get("http://www.xxx.com/update_device.asp?device_id="+temp_int);
        System.out.print("TAG+="+url);
        int temp_int1=Integer.parseInt(deviceNO);
        String url1=get("http://www.xxx.com/update_device.asp?device_id=1");
        System.out.print("TAG==="+url1);

    }

}