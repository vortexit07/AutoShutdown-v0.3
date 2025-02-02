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
import java.util.logging.Level;

/**
 * Utility class for logging messages to a file.
 * Logs are appended to a file named with the current date in the format yyyy-MM-dd.log.
 * Old log files older than 7 days are automatically deleted upon initialization.
 * Each log entry includes a timestamp in the format hh:mm:ss.
 * 
 * @author Connor Lewis
 */
public class Logger {

    private FileWriter logFile;
    private File file;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");

    /**
     * Constructs a Logger object and initializes the log file.
     * 
     * @throws IOException if an I/O error occurs.
     */
    public Logger() throws IOException {
        file = new File(LocalDate.now() + ".log");

        if (!file.exists()) {
            file.createNewFile();
        }

        logFile = new FileWriter(file, true);

        // Process existing log files
        Files.list(Paths.get(".")).forEach(filePath -> {
            if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".log")) {
                try {
                    processLogFile(filePath);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        logFile.close();
    }

    private void processLogFile(Path logFile) throws IOException {
        String strLogFile = "" + logFile;
        strLogFile = strLogFile.replace(".log", "");
        strLogFile = strLogFile.replace(".\\", "");
        LocalDate logFileCreationDate = LocalDate.parse(strLogFile);
        Period fileAge = Period.between(logFileCreationDate, LocalDate.now());

        if (fileAge.getDays() > 7) {
            File file = new File("" + logFile);
            log("Deleting old log file " + logFile);
            file.delete();
        }
    }

    /**
     * Logs a message with a timestamp.
     * 
     * @param msg the message to log.
     * @throws IOException if an I/O error occurs.
     */
    public void log(String msg) throws IOException {
        logFile = new FileWriter(file, true);
        logFile.append(LocalTime.now().format(formatter) + " - " + msg + "\n");
        logFile.close();
    }

    /**
     * Logs an exception message with a timestamp.
     * 
     * @param e the exception to log.
     * @throws IOException if an I/O error occurs.
     */
    public void log(Exception e) throws IOException {
        logFile = new FileWriter(file, true);
        logFile.append(LocalTime.now().format(formatter) + " - " + e.getMessage() + "\n");
        logFile.close();
    }

    /**
     * Logs an object's string representation with a timestamp.
     * 
     * @param o the object to log.
     * @throws IOException if an I/O error occurs.
     */
    public void log(Object o) throws IOException {
        logFile = new FileWriter(file, true);
        logFile.append(LocalTime.now().format(formatter) + " - " + o.toString() + "\n");
        logFile.close();
    }
}