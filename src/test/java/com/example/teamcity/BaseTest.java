package com.example.teamcity;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class BaseTest {

    protected SoftAssert softy;

    @BeforeMethod(alwaysRun = true)
    public void BeforeTest() {
        softy = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void AfterTest() {
        softy.assertAll();
    }
}
