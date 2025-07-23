package za.co.vortexit.pkg3.v0.autoshutdown;

import java.awt.AWTException;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;

/**
 * Manages the display of various types of dialogs (e.g., error, warning,
 * normal)
 * within the AutoShutdown GUI. Supports modal and non-modal dialogs, as well as
 * timed and input dialogs.
 */
public class CustomDialogManager extends AutoShutdownGUI {

    public CustomDialogManager() throws IOException, AWTException {
        super();
        initIcons();
    }

    /** Message type for a normal/info dialog */
    public static final String MSG_NORM = "NORMAL";

    /** Message type for an error dialog */
    public static final String MSG_ERR = "ERROR";

    /** Message type for a warning dialog */
    public static final String MSG_WARN = "WARNING";

    private static javax.swing.ImageIcon scaledWarnPic;
    private static javax.swing.ImageIcon scaledErrorPic;
    private static javax.swing.ImageIcon scaledInfoPic;

    /**
     * Initializes icon resources from the resources folder.
     * These icons correspond to the message types supported by this class.
     */
    private void initIcons() {
        scaledWarnPic = loadScaledIcon("/resources/images/warn.png", 45, 45);
        scaledErrorPic = loadScaledIcon("/resources/images/error.png", 45, 45);
        scaledInfoPic = loadScaledIcon("/resources/images/info.png", 45, 45);
    }

    private ImageIcon loadScaledIcon(String resourcePath, int width, int height) {
        java.net.URL imgURL = AutoShutdownGUI.class.getResource(resourcePath);
        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL);
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Couldn't find file: " + resourcePath);
            return null;
        }
    }

    /**
     * Displays a non-blocking dialog with the specified title, heading, message,
     * and type on a new thread.
     * <p>
     * This is useful for status popups or alerts that shouldn't freeze the UI.
     *
     * @param title      the title of the dialog window.
     * @param msgHeading the heading shown in the dialog.
     * @param message    the content of the message.
     * @param type       the dialog type: {@code "ERROR"}, {@code "NORMAL"}, or
     *                   {@code "WARNING"}.
     */
    public static void showDialog(String title, String msgHeading, String message, String type) {
        new Thread(() -> {
            dlgGeneral.setTitle(title);
            dlgGeneral.setLocationRelativeTo(dlgGeneral.getParent());
            lblDialogTitle.setText(msgHeading);
            lblDialogMsg.setText("<html>" + message + "</html>");

            // Set appropriate icon based on dialog type
            switch (type) {
                case MSG_ERR -> lblDialogImg.setIcon(scaledErrorPic);
                case MSG_NORM -> lblDialogImg.setIcon(scaledInfoPic);
                case MSG_WARN -> lblDialogImg.setIcon(scaledWarnPic);
                default -> lblDialogImg.setIcon(scaledInfoPic);
            }

            dlgGeneral.setVisible(true);
        }).start();
    }

    /**
     * Displays a dialog with the specified parameters, optionally running on the
     * main thread.
     * <p>
     * If {@code runOnMainThread} is true, the dialog is shown synchronously;
     * otherwise, it is shown asynchronously on a new thread.
     *
     * @param title           the dialog title.
     * @param msgHeading      the dialog heading text.
     * @param message         the main message content.
     * @param type            the dialog type: {@code "ERROR"}, {@code "NORMAL"}, or
     *                        {@code "WARNING"}.
     * @param runOnMainThread if true, dialog shows on the main thread and blocks
     *                        execution.
     */
    public static void showDialog(String title, String msgHeading, String message, String type,
            boolean runOnMainThread) {
        if (runOnMainThread) {
            dlgGeneral.setTitle(title);
            dlgGeneral.setLocationRelativeTo(dlgGeneral.getParent());
            lblDialogTitle.setText(msgHeading);
            lblDialogMsg.setText("<html>" + message + "</html>");

            switch (type) {
                case MSG_ERR -> lblDialogImg.setIcon(scaledErrorPic);
                case MSG_NORM -> lblDialogImg.setIcon(scaledInfoPic);
                case MSG_WARN -> lblDialogImg.setIcon(scaledWarnPic);
                default -> lblDialogImg.setIcon(scaledInfoPic);
            }

            dlgGeneral.setVisible(true);
        } else {
            showDialog(title, msgHeading, message, type);
        }
    }

    /**
     * Displays a non-blocking dialog with a timeout, automatically dismissing it
     * after the specified number of milliseconds.
     *
     * @param title            the dialog title.
     * @param msgHeading       the heading text displayed in the dialog.
     * @param message          the content of the dialog.
     * @param type             the dialog type: {@code "ERROR"}, {@code "NORMAL"},
     *                         or {@code "WARNING"}.
     * @param dismissTimeMills how long (in milliseconds) to show the dialog before
     *                         hiding it.
     */
    public static void showDialog(String title, String msgHeading, String message, String type, int dismissTimeMills) {
        new Thread(() -> {
            dlgGeneral.setTitle(title);
            lblDialogTitle.setText(msgHeading);
            lblDialogMsg.setText("<html>" + message + "</html>");

            switch (type) {
                case MSG_ERR -> lblDialogImg.setIcon(scaledErrorPic);
                case MSG_NORM -> lblDialogImg.setIcon(scaledInfoPic);
                case MSG_WARN -> lblDialogImg.setIcon(scaledWarnPic);
                default -> lblDialogImg.setIcon(scaledInfoPic);
            }

            dlgGeneral.setVisible(true);

            try {
                Thread.sleep(dismissTimeMills);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (dlgGeneral.isVisible()) {
                dlgGeneral.setVisible(false);
            }
        }).start();
    }

    /**
     * Displays a modal input dialog with a custom heading and returns the user's
     * input.
     * <p>
     * This method blocks execution until the user submits or closes the dialog.
     *
     * @param heading the heading text shown in the input dialog.
     * @return the input string entered by the user; may be empty but never null.
     */
    public static String showInputDialog(String heading) {
        lblInputHeading.setText(heading);
        dlgInputDialog.setVisible(true);
        String input = txfInputDialog.getText();
        txfInputDialog.setText(""); // Clear for next input
        return input;
    }
}