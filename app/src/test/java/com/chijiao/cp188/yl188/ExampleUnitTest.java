package com.chijiao.cp188.yl188;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public  int b[];
    public  int totalcount=0;
    //a[]是被抽取的数组
    //except是抽取的个数
    //count2是抽取第几次，一共只能抽except次
    //count是对每次抽取的数进行不重复处理

    @Test
    public void addition_isCorrect() throws Exception {
        int a[]={1,2,3,4};
        for (int i = 0; i < a.length; i++) {
            permutation(a,0,0,i);
        }
        System.out.println(goodsID.toString());
        for (List<Integer> IDs:goodsID) {
//            IDs.get()
        }
    }
    List<List<Integer>> goodsID = new ArrayList<>();
    public  void permutation(int a[],int count,int count2,int except){
        if(count2==except) {
            try {
                if (b!=null&&b.length!=0){
//                    String toString = Arrays.toString(b);
//                    goodsID.add(toString);
                    List<Integer> list = new ArrayList<Integer>();
                    for(int i : b){
                        list.add(i);
                    }
                    goodsID.add(list);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
//            System.out.println(Arrays.toString(b));
            totalcount++;
        }
        else
        {
            if(count2==0)
            {
                b=new int[except];
            }

            for(int i=count;i<a.length;i++){
                b[count2]=a[i];
                permutation(a,i+1,count2+1,except);
            }
        }
    }
}