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
 *
 * @author Connor Lewis
 */
public class Logger {

    private FileWriter logFile;
    private File file;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");


    public Logger() throws IOException {
        file = new File(LocalDate.now() + ".log");

        if (!file.exists()) {
            file.createNewFile();
        }

        logFile = new FileWriter(file, true);

        Files.list(Paths.get(".")).forEach(filePath -> {
            if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".log")) {

                try {
                    processLogFile(filePath);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

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

    public void log(String msg) throws IOException {
        logFile = new FileWriter(file, true);
        logFile.append(LocalTime.now().format(formatter) + " - " + msg + "\n");
        logFile.close();
    }

    public void log(Exception e) throws IOException {
        logFile = new FileWriter(file, true);
        logFile.append(LocalTime.now().format(formatter) + " - " + e.getMessage() + "\n");
        logFile.close();
    }

    public void log(Object o) throws IOException {
        logFile = new FileWriter(file, true);
        logFile.append(LocalTime.now().format(formatter) + " - " + o.toString() + "\n");
        logFile.close();
    }

}
