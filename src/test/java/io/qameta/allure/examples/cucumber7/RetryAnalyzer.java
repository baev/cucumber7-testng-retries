package io.qameta.allure.examples.cucumber7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = 2; // Set the number of retries you want
    private static final Logger logger = LoggerFactory.getLogger(RetryAnalyzer.class);

    @Override
    public boolean retry(ITestResult result) {
        logger.info("Retry Analyzer Retry Method is invoked here - Status and Test Name are: " + result.getStatus() + " ," + result.getTestName());
        if (!result.isSuccess() && retryCount < maxRetryCount) {
            retryCount++;
            return true;  // Retry
        }
        return false;  // No retry
    }
}
