package za.co.vortexit.pkg3.v0.autoshutdown;

import java.io.*;
import java.nio.file.*;

/**
 * The {@code StartupManager} class provides functionality to enable or disable
 * an application
 * to run on system startup for both Windows and Linux platforms.
 * <p>
 * This class supports creating and deleting startup entries in:
 * <ul>
 * <li>Windows Startup folder using symbolic links</li>
 * <li>Linux autostart directory with .desktop files</li>
 * </ul>
 * It uses platform-specific methods to manage these startup configurations.
 * @author Connor Lewis
 */
public class StartupManager {

    /**
     * Enables the application to run on system startup (Windows).
     *
     * @param appPath the absolute path of the application's executable file.
     */
    public static void enableStartupWindows(String appPath) {
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
     * Disables the application from running on system startup (Windows).
     *
     * @param appPath the absolute path of the application's executable file.
     */
    public static void disableStartupWindows(String appPath) {
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
     * Creates a symbolic link (Windows) to enable application startup.
     *
     * @param saveShortcutAs   the path to save the shortcut.
     * @param targetOfShortcut the target executable file path.
     * @throws IOException          if an I/O error occurs.
     * @throws InterruptedException if the process execution is interrupted.
     */
    private static void createSymbolicLink(String saveShortcutAs, String targetOfShortcut)
            throws IOException, InterruptedException {
        String command = String.format("cmd /c mklink \"%s\" \"%s\"", saveShortcutAs, targetOfShortcut);
        runAsAdmin(command);
    }

    /**
     * Deletes a symbolic link (Windows) to disable application startup.
     *
     * @param linkPath the path of the shortcut to delete.
     * @throws IOException          if an I/O error occurs.
     * @throws InterruptedException if the process execution is interrupted.
     */
    private static void deleteSymbolicLink(String linkPath) throws IOException, InterruptedException {
        String command = String.format("cmd /c del \"%s\"", linkPath);
        runAsAdmin(command);
    }

    /**
     * Executes a command with administrative privileges.
     *
     * @param command the command to execute.
     * @throws IOException          if an I/O error occurs.
     * @throws InterruptedException if the process execution is interrupted.
     */
    private static void runAsAdmin(String command) throws IOException, InterruptedException {
        String script = String.format("powershell -Command \"Start-Process cmd -ArgumentList '/c %s' -Verb runAs\"",
                command);
        ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", script);
        Process process = processBuilder.start();
        process.waitFor();
    }

    /**
     * Enables the application to run on system startup (Linux).
     *
     * @param appPath the absolute path of the application's executable file.
     */
    public static void enableStartupLinux(String appPath) {
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
     * Disables the application from running on system startup (Linux).
     *
     * @param appPath the absolute path of the application's executable file.
     */
    public static void disableStartupLinux(String appPath) {
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
     * Creates a .desktop file for Linux autostart functionality.
     *
     * @param targetPath      the path to the application's executable.
     * @param desktopFilePath the path to save the .desktop file.
     * @throws IOException if an I/O error occurs.
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
     * Deletes a .desktop file to disable Linux autostart functionality.
     *
     * @param desktopFilePath the path of the .desktop file to delete.
     * @throws IOException if an I/O error occurs.
     */
    private static void deleteDesktopFile(String desktopFilePath) throws IOException {
        Path desktopFile = Paths.get(desktopFilePath);
        Files.deleteIfExists(desktopFile);
    }
}