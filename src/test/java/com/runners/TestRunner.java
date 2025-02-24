
package com.runners;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.stepdefinitions",
    plugin = {
        "pretty",
        "html:target/cucumber-reports.html",
        "json:target/cucumber-reports.json",
        "junit:target/cucumber-reports.xml",
        "rerun:target/rerun.txt"
    },
    monochrome = true,
tags = "@Test44" 
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Test
    public void runCucumberTests() {
    	System.out.println("Running Cucumber tests...");
       
    }
}

