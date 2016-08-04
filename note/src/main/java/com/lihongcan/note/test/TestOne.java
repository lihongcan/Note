package com.lihongcan.note.test;

/**
 * Created by lihongcan on 2016/7/15.
 */
public class TestOne {
    private static TestOne instance;
    private TestOne(){

    }
    public static TestOne getInstance(){
        if (null==instance)
        {
            instance=new TestOne();
        }
        return instance;
    }
    public static void  main(String[] args){
        TestOne testOne=TestOne.getInstance();
        System.out.println(testOne);
    }
}
