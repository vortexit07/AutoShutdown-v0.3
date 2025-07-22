package za.co.vortexit.pkg3.v0.autoshutdown;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * A lightweight logging utility that writes timestamped log messages to a daily file.
 * <p>
 * Log files are named using the current date (e.g., <code>2025-07-22.log</code>),
 * and stored in the working directory. Upon instantiation, any log files older
 * than 7 days are automatically deleted to prevent log bloat.
 * <p>
 * Each log entry includes a time-of-day timestamp in the format <code>hh:mm:ss</code>.
 * Supports logging of strings, exceptions, and object string representations.
 * 
 * @author Connor Lewis
 */
public class Logger {

    private FileWriter logFile;
    private File file;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");

    /**
     * Constructs a new {@code Logger}, initializing the log file for today's date
     * and removing any log files older than 7 days.
     */
    public Logger() {
        try {
            file = new File(LocalDate.now() + ".log");

            if (!file.exists()) {
                file.createNewFile();
            }

            logFile = new FileWriter(file, true);

            // Delete old log files (> 7 days)
            Files.list(Paths.get(".")).forEach(filePath -> {
                if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".log")) {
                    processLogFile(filePath);
                }
            });

            logFile.close();
        } catch (IOException e) {
            System.err.println("Error initializing logger: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Checks the age of a log file and deletes it if it's older than 7 days.
     *
     * @param logFile the {@link Path} to the log file to evaluate.
     */
    private void processLogFile(Path logFile) {
        try {
            String strLogFile = logFile.toString().replace(".log", "").replace(".\\", "");
            LocalDate logFileCreationDate = LocalDate.parse(strLogFile);
            Period fileAge = Period.between(logFileCreationDate, LocalDate.now());

            if (fileAge.getDays() > 7) {
                File file = new File(logFile.toString());
                log("Deleting old log file " + logFile);
                file.delete();
            }
        } catch (Exception e) {
            System.err.println("Error processing log file: " + logFile + " - " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Logs a simple message with a timestamp.
     *
     * @param msg the message to log.
     */
    public void log(String msg) {
        try {
            logFile = new FileWriter(file, true);
            logFile.append(LocalTime.now().format(formatter))
                   .append(" - ")
                   .append(msg)
                   .append("\n");
            logFile.close();
        } catch (IOException e) {
            System.err.println("Error logging message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Logs the message of an {@link Exception} with a timestamp.
     *
     * @param e the exception to log.
     */
    public void log(Exception e) {
        try {
            logFile = new FileWriter(file, true);
            logFile.append(LocalTime.now().format(formatter))
                   .append(" - ")
                   .append(e.getMessage())
                   .append("\n");
            logFile.close();
        } catch (IOException ex) {
            System.err.println("Error logging exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Logs an object's {@code toString()} value with a timestamp.
     *
     * @param o the object to log.
     */
    public void log(Object o) {
        try {
            logFile = new FileWriter(file, true);
            logFile.append(LocalTime.now().format(formatter))
                   .append(" - ")
                   .append(o.toString())
                   .append("\n");
            logFile.close();
        } catch (IOException e) {
            System.err.println("Error logging object: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Logs a message and an exception stack trace with a timestamp.
     *
     * @param msg a message to accompany the exception.
     * @param e   the exception to log.
     */
    public void log(String msg, Exception e) {
        try {
            logFile = new FileWriter(file, true);
            logFile.append(LocalTime.now().format(formatter))
                   .append(" - ")
                   .append(msg)
                   .append("\n")
                   .append(e.toString())
                   .append("\n");
            logFile.close();
        } catch (IOException f) {
            System.err.println("Error logging exception with message: " + f.getMessage());
            f.printStackTrace();
        }
    }
}