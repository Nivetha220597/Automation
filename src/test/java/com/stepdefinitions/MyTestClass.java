package com.stepdefinitions;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MyTestClass {

    @Test(groups = "Test")
    public void testMethod1() {
        Assert.assertTrue(true);
    }

}
