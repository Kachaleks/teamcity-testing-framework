package com.example.teamcity;

import com.example.teamcity.requests.checked.CheckedRequests;
import com.example.teamcity.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class BaseTest {

    protected SoftAssert softy;
    protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superUserSpec());

    @BeforeMethod(alwaysRun = true)
    public void BeforeTest() {
        softy = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void AfterTest() {
        softy.assertAll();
    }
}
