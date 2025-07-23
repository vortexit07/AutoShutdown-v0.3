package za.co.vortexit.pkg3.v0.autoshutdown;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for shutting down the system.
 * <p>
 * This class provides methods to programmatically shut down Windows and
 * Unix-based systems.
 * </p>
 * 
 * @author Connor Lewis
 */
public class ShutdownUtils {

    /**
     * Shuts down a Windows system immediately.
     * <p>
     * This method uses the Windows `shutdown` command to initiate an immediate
     * system shutdown.
     * </p>
     *
     * @throws IOException          if an I/O error occurs while executing the
     *                              shutdown command.
     * @throws InterruptedException if the process is interrupted while waiting for
     *                              it to start.
     */
    private static void shutdownWindows() throws IOException, InterruptedException {
        // Create a ProcessBuilder to execute the Windows shutdown command
        ProcessBuilder builder = new ProcessBuilder("shutdown", "-s", "-t", "0");
        builder.start(); // Start the shutdown process
    }

    /**
     * Shuts down a Unix-based system immediately.
     * <p>
     * This method attempts to shut down the system using the `shutdown` or
     * `systemctl` commands,
     * depending on availability. It determines the appropriate shell to use for
     * executing the command.
     * </p>
     *
     * @throws IOException if an I/O error occurs while executing the shutdown
     *                     command.
     */
    private static void shutdownUnix() throws IOException {
        // Default shutdown command for Unix-based systems
        String shutdownCommand = "shutdown -h now";
        String[] cmd;

        // Check if `systemctl` is available (preferred on modern Linux distributions)
        if (isCommandAvailable("systemctl")) {
            shutdownCommand = "systemctl poweroff";
        }

        // Determine the shell to use for executing the command
        if (isCommandAvailable("/bin/bash")) {
            // Use bash shell if available
            cmd = new String[] { "/bin/bash", "-c", shutdownCommand };
        } else {
            // Fallback to sh shell
            cmd = new String[] { "/bin/sh", "-c", shutdownCommand };
        }

        Runtime.getRuntime().exec(cmd); // Execute the shutdown command
    }

    /**
     * Shuts down the system based on the specified operating system type.
     * <p>
     * This method provides a unified interface to shut down either Windows or
     * Unix-based systems.
     * </p>
     *
     * @param os an integer representing the operating system type:
     *           <ul>
     *           <li>1 - Windows</li>
     *           <li>2 - Linux/Unix</li>
     *           </ul>
     */
    public static void shutdown() {
        int os = getOS(); // Determine the operating system type
        switch (os) {
            case 1: // Windows
                try {
                    ShutdownUtils.shutdownWindows();
                } catch (IOException | InterruptedException e) {
                    System.err.println("Failed to shut down Windows system.");
                    e.printStackTrace();
                }
                break;
            case 2: // Linux/Unix
                try {
                    ShutdownUtils.shutdownUnix();
                } catch (IOException e) {
                    System.err.println("Failed to shut down Unix-based system.");
                    e.printStackTrace();
                }
                break;
            default:
                System.err.println("Invalid operating system type specified.");
        }
    }

    /**
     * Checks if a specified command is available on the system.
     * <p>
     * This method uses the `which` command to determine if the given command is
     * available in the system's PATH. It is useful for checking dependencies
     * before executing system commands.
     * </p>
     *
     * @param command the name of the command to check for availability.
     * @return {@code true} if the command is available, {@code false} otherwise.
     */
    private static boolean isCommandAvailable(String command) {
        try {
            // Execute the `which` command to check for the command's availability
            Process process = Runtime.getRuntime().exec(new String[] { "which", command });
            int exitCode = process.waitFor(); // Wait for the process to complete
            return exitCode == 0; // Return true if the command is found (exit code 0)
        } catch (IOException | InterruptedException e) {
            // Print stack trace for debugging and return false if an error occurs
            System.err.println("Error checking command availability: " + command);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates the conditions for triggering a system shutdown.
     * <p>
     * This method checks the current time against specified shutdown times, updates
     * the shutdown schedule a minute before a scheduled shutdown and
     * evaluates whether the system should shut down. If the conditions are met,
     * the method invokes the appropriate shutdown command based on the
     * operating system.
     *
     * @param t1    the first time to trigger a shutdown, or null if not
     *              applicable.
     * @param t2    the second time to trigger a shutdown, or null if not
     *              applicable.
     * @param t3    the third time to trigger a shutdown, or null if not
     *              applicable.
     * @param t4    the fourth time to trigger a shutdown, or null if not
     *              applicable.
     * @param atMid a boolean flag indicating whether a shutdown should occur
     *              at/before midnight.
     * @throws IOException          if an error occurs during the execution of a
     *                              shutdown
     *                              command.
     * @throws InterruptedException if the shutdown process is interrupted
     *                              (Windows only).
     */
    public static int timeUntilNextShutdown(int timeBefore, LocalTime t1, LocalTime t2, LocalTime t3, LocalTime t4,
            boolean atMid) {
        // Get current time with second-level precision
        LocalTime now = LocalTime.now();

        // Midnight adjusted with buffer
        LocalTime midnight = LocalTime.of(23, 59).minusMinutes(timeBefore).plusMinutes(1);

        if (t1 != null || t2 != null || t3 != null || t4 != null || atMid) {

            // Combine all shutdown times into a list for easier handling
            List<LocalTime> shutdownTimes = new ArrayList<>();
            if (t1 != null)
                shutdownTimes.add(t1);
            if (t2 != null)
                shutdownTimes.add(t2);
            if (t3 != null)
                shutdownTimes.add(t3);
            if (t4 != null)
                shutdownTimes.add(t4);
            if (atMid)
                shutdownTimes.add(midnight);

            for (LocalTime shutdownTime : shutdownTimes) {
                Duration untilShutdown = Duration.between(now, shutdownTime);
                int secondsUntil = (int) untilShutdown.getSeconds();

                return secondsUntil;
            }
        }
        // If no shutdown times are specified, return -999 to indicate no shutdown scheduled
        return -999;
    }

    /**
     * Determines the operating system of the host machine.
     * <p>
     * This method retrieves the operating system's name from the system
     * properties, converts it to lowercase, and checks if it contains specific
     * keywords to identify whether the OS is Windows, Linux/Unix/Mac, or an
     * unsupported type.
     *
     * @return 1 if the operating system is Windows, 2 if it is Linux/Unix/Mac,
     *         and -1 if the operating system is unsupported.
     */
    static int getOS() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        if (operatingSystem.contains("win")) {
            return 1;
        } else if (operatingSystem.contains("nux") || operatingSystem.contains("nix")
                || operatingSystem.contains("mac")) {
            return 2;
        }

        return -1;
    }
}