package za.co.vortexit.pkg3.v0.autoshutdown;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.*;

/**
 * The {@code StartupManager} class provides platform-specific functionality to enable or disable
 * an application to run automatically on system startup for both Windows and Linux systems.
 * <p>
 * Startup entries are managed as:
 * <ul>
 *   <li>Symbolic links in the Windows Startup folder</li>
 *   <li>.desktop files in the Linux autostart directory</li>
 * </ul>
 * <p>
 * This utility assumes the application is distributed as a JAR or EXE and resides in a known executable path.
 * It uses command-line tools to execute privileged actions when necessary (e.g., creating symbolic links on Windows).
 * 
 * @author Connor Lewis
 */
public class StartupManager {

    /**
     * Enables the application to run at system startup on Windows by creating a symbolic link
     * in the user's Startup folder.
     *
     * @param appPath the absolute path of the application's executable.
     */
    private static void enableStartupWindows(String appPath) {
        String startupFolder = System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
        String shortcutName = new File(appPath).getName() + ".lnk";
        String shortcutPath = startupFolder + "\\" + shortcutName;

        try {
            createSymbolicLink(shortcutPath, appPath);
            System.out.println("Application enabled to run on startup (Windows).");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disables the application's automatic startup on Windows by deleting its shortcut
     * from the Startup folder.
     *
     * @param appPath the absolute path of the application's executable.
     */
    private static void disableStartupWindows(String appPath) {
        String startupFolder = System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
        String shortcutName = new File(appPath).getName() + ".lnk";
        String shortcutPath = startupFolder + "\\" + shortcutName;

        try {
            deleteSymbolicLink(shortcutPath);
            System.out.println("Application disabled from running on startup (Windows).");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a symbolic link to the target executable in the specified location (Windows only).
     *
     * @param saveShortcutAs   the path to save the shortcut.
     * @param targetOfShortcut the full path to the executable file.
     * @throws IOException          if an I/O error occurs.
     * @throws InterruptedException if the command execution is interrupted.
     */
    private static void createSymbolicLink(String saveShortcutAs, String targetOfShortcut)
            throws IOException, InterruptedException {
        String command = String.format("cmd /c mklink \"%s\" \"%s\"", saveShortcutAs, targetOfShortcut);
        runAsAdmin(command);
    }

    /**
     * Deletes a symbolic link from the specified path (Windows only).
     *
     * @param linkPath the path to the symbolic link to delete.
     * @throws IOException          if an I/O error occurs.
     * @throws InterruptedException if the command execution is interrupted.
     */
    private static void deleteSymbolicLink(String linkPath) throws IOException, InterruptedException {
        String command = String.format("cmd /c del \"%s\"", linkPath);
        runAsAdmin(command);
    }

    /**
     * Executes a command as administrator (Windows).
     *
     * @param command the command to run.
     * @throws IOException          if an I/O error occurs.
     * @throws InterruptedException if the command execution is interrupted.
     */
    private static void runAsAdmin(String command) throws IOException, InterruptedException {
        String script = String.format("powershell -Command \"Start-Process cmd -ArgumentList '/c %s' -Verb runAs\"",
                command);
        ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", script);
        Process process = processBuilder.start();
        process.waitFor();
    }

    /**
     * Enables the application to run at system startup on Linux by creating a .desktop file
     * in the autostart directory.
     *
     * @param appPath the absolute path of the application's executable.
     */
    private static void enableStartupLinux(String appPath) {
        String autostartFolder = System.getProperty("user.home") + "/.config/autostart";
        String desktopFileName = new File(appPath).getName() + ".desktop";
        String desktopFilePath = autostartFolder + "/" + desktopFileName;

        try {
            createDesktopFile(appPath, desktopFilePath);
            System.out.println("Application enabled to run on startup (Linux).");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disables the application's automatic startup on Linux by deleting its .desktop file
     * from the autostart directory.
     *
     * @param appPath the absolute path of the application's executable.
     */
    private static void disableStartupLinux(String appPath) {
        String autostartFolder = System.getProperty("user.home") + "/.config/autostart";
        String desktopFileName = new File(appPath).getName() + ".desktop";
        String desktopFilePath = autostartFolder + "/" + desktopFileName;

        try {
            deleteDesktopFile(desktopFilePath);
            System.out.println("Application disabled from running on startup (Linux).");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a .desktop entry in the Linux autostart directory for the application.
     *
     * @param targetPath      the full path to the executable.
     * @param desktopFilePath the full path where the .desktop file will be saved.
     * @throws IOException if file creation fails.
     */
    private static void createDesktopFile(String targetPath, String desktopFilePath) throws IOException {
        String desktopFileContent = "[Desktop Entry]\n" +
                "Type=Application\n" +
                "Exec=" + targetPath + "\n" +
                "Hidden=false\n" +
                "NoDisplay=false\n" +
                "X-GNOME-Autostart-enabled=true\n" +
                "Name[en_US]=" + new File(targetPath).getName() + "\n" +
                "Name=" + new File(targetPath).getName() + "\n" +
                "Comment=Start " + new File(targetPath).getName() + " at login";

        Path autostartFolder = Paths.get(System.getProperty("user.home"), ".config", "autostart");
        if (!Files.exists(autostartFolder)) {
            Files.createDirectories(autostartFolder);
        }

        Path desktopFile = Paths.get(desktopFilePath);
        Files.write(desktopFile, desktopFileContent.getBytes());
    }

    /**
     * Deletes a Linux .desktop file that starts the app on login.
     *
     * @param desktopFilePath the full path to the .desktop file to delete.
     * @throws IOException if deletion fails.
     */
    private static void deleteDesktopFile(String desktopFilePath) throws IOException {
        Path desktopFile = Paths.get(desktopFilePath);
        Files.deleteIfExists(desktopFile);
    }

    /**
     * Enables or disables autostart functionality for Linux.
     * This public wrapper resolves the app's executable path and delegates to platform logic.
     *
     * @param ROS true to enable run on startup; false to disable.
     * @throws UnsupportedEncodingException if the path decoding fails.
     */
    public static void unixROS(boolean ROS) throws UnsupportedEncodingException {
        if (ROS) {
            enableStartupLinux(getExecutablePath().toString());
        } else {
            disableStartupLinux(getExecutablePath().toString());
        }
    }

    /**
     * Enables or disables autostart functionality for Windows.
     * This public wrapper resolves the app's executable path and delegates to platform logic.
     *
     * @param ROS true to enable run on startup; false to disable.
     * @throws UnsupportedEncodingException if the path decoding fails.
     */
    public static void windowsROS(boolean ROS) throws UnsupportedEncodingException {
        if (ROS) {
            enableStartupWindows(getExecutablePath().toString());
        } else {
            disableStartupWindows(getExecutablePath().toString());
        }
    }

    /**
     * Retrieves the absolute path of the currently running JAR or class file.
     * This path is used to create autostart entries.
     *
     * @return the path to the running application's executable (JAR/EXE).
     * @throws UnsupportedEncodingException if UTF-8 decoding fails.
     */
    private static Path getExecutablePath() throws UnsupportedEncodingException {
        String path = AutoShutdownGUI.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");

        // On Windows, remove leading '/' from path if present
        if (decodedPath.startsWith("/") && System.getProperty("os.name").toLowerCase().contains("win")) {
            decodedPath = decodedPath.substring(1);
        }

        return Paths.get(decodedPath);
    }
}