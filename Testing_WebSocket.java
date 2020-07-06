package com.selenium.testing;


import java.util.logging.Level;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;



public class Testing_WebSocket {
	private static WebDriver driver;
    public static void main(String[] args) throws InterruptedException {
	
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\driver\\chromedriver_win32\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable( LogType.PERFORMANCE, Level.ALL );
        options.setCapability( "goog:loggingPrefs", logPrefs );

        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
       
        driver = new ChromeDriver(options);
        driver.navigate().to("https://web-demo.adaptivecluster.com/");
        Thread.sleep(6000);
        LogEntries logEntries = driver.manage().logs().get(LogType.PERFORMANCE);

        driver.close();
        driver.quit();
        logEntries.forEach(entry->{
            JSONObject messageJSON = new JSONObject(entry.getMessage());
            String method = messageJSON.getJSONObject("message").getString("method");
            if(method.equalsIgnoreCase("Network.webSocketFrameSent")){
                System.out.println("Message Sent: " + messageJSON.getJSONObject("message").getJSONObject("params").getJSONObject("response").getString("payloadData"));
            }else if(method.equalsIgnoreCase("Network.webSocketFrameReceived")){
                System.out.println("Message Received: " + messageJSON.getJSONObject("message").getJSONObject("params").getJSONObject("response").getString("payloadData"));
            }
        });
    }
}
