package com.stepdefinitions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtility {

    public static void takeScreenshot(WebDriver driver) {
        TakesScreenshot screenshotTaker = (TakesScreenshot) driver;
        File screenshot = screenshotTaker.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File destination = new File("screenshots/screenshot_" + timestamp + ".png");

        try {
            FileUtils.copyFile(screenshot, destination);
            System.out.println("Screenshot saved at: " + destination.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
