package com.itgura.service.impl;




import com.itgura.service.YoutubePermissionService;


import jakarta.xml.bind.SchemaOutputResolver;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


@Service
public class YoutubePermissionServiceImpl implements YoutubePermissionService {


    private static final String COOKIES_YOUTUBE_COOKIES_DATA = "cookies/youtube_cookies.data";
    private static final String channelId = "UC1l4Zf3f5d2Xg1J9A6ZJ9xg";
    @Override
    public void grantPermission(String url, List<String> emails) {
      // ToDo:Implement Selenium code here
        System.out.println("Selenium Permission Part Initializing.......");
        WebDriver driver = new ChromeDriver();

        loadCookies(driver, COOKIES_YOUTUBE_COOKIES_DATA);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!isLoggedInYoutube(driver)) {
            System.out.println("Please manually log in to continue...");
            waitForManualLoginYoutube(driver);
            saveCookies(driver, COOKIES_YOUTUBE_COOKIES_DATA);
        }
        // Split the URL by "/" and get the last part
        String videoId = url.substring(url.lastIndexOf("/") + 1);
        driver.get("https://studio.youtube.com/video/${videoId}/edit");
        //wait for the page to load
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement visibilityDropDownButton = driver.findElement(
                By.xpath("//ytcp-icon-button[@id='select-button' and @aria-label='Edit video visibility status']")
        );
        visibilityDropDownButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement editMailsButton = driver.findElement(
                By.xpath("//ytcp-button[@aria-label='Share privately' and contains(@class, 'private-share-edit-button')]")
        );
        editMailsButton.click();

        WebElement inviteesInput = driver.findElement(
                By.xpath("//input[@id='text-input' and @aria-label='Invitees']")
        );
        inviteesInput.sendKeys(String.join(",", emails));
        inviteesInput.sendKeys(Keys.ENTER);

        WebElement doneButton = driver.findElement(
                By.xpath("//ytcp-button[@aria-label='Done']")
        );
        doneButton.click();
        WebElement nextDoneButton = driver.findElement(
                By.xpath("//ytcp-button[@aria-label='Done']")
        );
        nextDoneButton.click();
        WebElement saveButton = driver.findElement(
                By.xpath("//ytcp-button[@aria-label='Save']")
        );
        saveButton.click();

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void saveCookies(WebDriver driver,String filePath) {
        File file = new File(filePath);
        File directory = file.getParentFile();

        if (directory != null && !directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            Set<Cookie> cookies = driver.manage().getCookies();
            outputStream.writeObject(cookies);
            System.out.println("Cookies have been saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForManualLoginYoutube(WebDriver driver) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please manually log in to the site, then press Enter to continue...");

        // Wait for user input in the command line to confirm they've logged in
        scanner.nextLine();

        // Now check if the user is logged in
        if (isLoggedInYoutube(driver)) {
            System.out.println("Login successful, continuing with automation...");
        } else {
            System.out.println("Login not detected. Please log in and press Enter again.");
            waitForManualLoginYoutube(driver);  // Recursively wait until login is detected
        }
    }

    private boolean isLoggedInYoutube(WebDriver driver) {
        driver.get("https://www.youtube.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        try {
            // Attempt to find the specific input field that indicates a successful login
            WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@aria-label=\"Sign in\"]") ));

            return !loginButton.isDisplayed();
        } catch (Exception e) {
            // If the element is not found, it means the login was successful
            return true;
        }
    }

    private void loadCookies(WebDriver driver, String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
                Set<Cookie> cookies = (Set<Cookie>) inputStream.readObject();
                // Navigate to the correct domain before adding cookies
                    driver.get("https://www.youtube.com/");

                for (Cookie cookie : cookies) {
                    driver.manage().addCookie(cookie);
                }
                System.out.println("Cookies loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void revokePermission(String url, List<String> emails) {
        //ToDo:Implement Selenium code here
    }
}


