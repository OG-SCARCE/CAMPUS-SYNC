package com.campussync.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger Utility
 * 
 * Centralized logging for CampusSync application.
 * Handles info, warning, error, and debug level messages.
 * Logs are written to both console and log file for persistence.
 * 
 * Responsibilities:
 * - Provide consistent logging interface across application
 * - Include timestamps and severity levels
 * - Support file-based logging for debugging
 * - Provide silent error handling (won't throw exceptions from logging)
 * 
 * Usage:
 * Logger.info("User logged in: " + userId);
 * Logger.error("Database connection failed", exception);
 * Logger.debug("Processing request with params: " + params);
 */
public class Logger {

    private static final String LOG_FILE = "logs/campussync.log";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Log levels
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";
    public static final String DEBUG = "DEBUG";

    /**
     * Log an informational message
     */
    public static void info(String message) {
        log(INFO, message, null);
    }

    /**
     * Log a warning message
     */
    public static void warn(String message) {
        log(WARN, message, null);
    }

    /**
     * Log an error message with exception details
     */
    public static void error(String message, Throwable throwable) {
        log(ERROR, message, throwable);
    }

    /**
     * Log an error message without exception
     */
    public static void error(String message) {
        log(ERROR, message, null);
    }

    /**
     * Log a debug message (useful for development)
     */
    public static void debug(String message) {
        log(DEBUG, message, null);
    }

    /**
     * Core logging method - handles actual logging to console and file
     * 
     * @param level Log level (INFO, WARN, ERROR, DEBUG)
     * @param message Message to log
     * @param throwable Optional exception to include
     */
    private static void log(String level, String message, Throwable throwable) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String logMessage = String.format("[%s] %s - %s", timestamp, level, message);

        // Log to console
        System.out.println(logMessage);

        // Include exception details if provided
        if (throwable != null) {
            System.err.println("Exception: " + throwable.getMessage());
            throwable.printStackTrace(System.err);
        }

        // Log to file (non-blocking, errors won't crash application)
        writeToFile(logMessage, throwable);
    }

    /**
     * Writes log message to file
     * Uses silent error handling to prevent logging from crashing application
     */
    private static void writeToFile(String logMessage, Throwable throwable) {
        try {
            // Create logs directory if it doesn't exist
            Files.createDirectories(Paths.get("logs"));

            // Append message to log file
            StringBuilder fullMessage = new StringBuilder(logMessage).append("\n");

            if (throwable != null) {
                fullMessage.append("Exception: ").append(throwable.getMessage()).append("\n");
                // Add stack trace
                for (StackTraceElement element : throwable.getStackTrace()) {
                    fullMessage.append("  at ").append(element.toString()).append("\n");
                }
            }

            Files.write(
                    Paths.get(LOG_FILE),
                    fullMessage.toString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            // Silently fail - don't want logging to crash the app
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}
