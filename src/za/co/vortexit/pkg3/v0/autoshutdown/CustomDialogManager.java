package za.co.vortexit.pkg3.v0.autoshutdown;

/**
 * Manages the display of various types of dialogs (e.g., error, warning, normal) 
 * within the AutoShutdown GUI. Supports modal and non-modal dialogs, as well as 
 * timed and input dialogs.
 */
public class CustomDialogManager {
    
    /** Message type for a normal/info dialog */
    public static final String MSG_NORM = "NORMAL";

    /** Message type for an error dialog */
    public static final String MSG_ERR = "ERROR";

    /** Message type for a warning dialog */
    public static final String MSG_WARN = "WARNING";

    private javax.swing.ImageIcon scaledWarnPic;
    private javax.swing.ImageIcon scaledErrorPic;
    private javax.swing.ImageIcon scaledInfoPic;

    private AutoShutdownGUI parent;

    /**
     * Initializes icon resources from the resources folder.
     * These icons correspond to the message types supported by this class.
     */
    private void initIcons() {
        scaledWarnPic = new javax.swing.ImageIcon(
                AutoShutdownGUI.class.getResource("/resources/images/warn.png"));
        scaledErrorPic = new javax.swing.ImageIcon(
                AutoShutdownGUI.class.getResource("/resources/images/error.png"));
        scaledInfoPic = new javax.swing.ImageIcon(
                AutoShutdownGUI.class.getResource("/resources/images/info.png"));
    }

    /**
     * Constructs a CustomDialogManager for the given GUI.
     *
     * @param parent the parent GUI that owns the dialog components.
     */
    public CustomDialogManager(AutoShutdownGUI parent) {
        this.parent = parent;
        initIcons();
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
     * @param type       the dialog type: {@code "ERROR"}, {@code "NORMAL"}, or {@code "WARNING"}.
     */
    public void showDialog(String title, String msgHeading, String message, String type) {
        new Thread(() -> {
            parent.dlgGeneral.setTitle(title);
            parent.lblDialogTitle.setText(msgHeading);
            parent.lblDialogMsg.setText(message);

            // Set appropriate icon based on dialog type
            switch (type) {
                case MSG_ERR -> parent.lblDialogImg.setIcon(scaledErrorPic);
                case MSG_NORM -> parent.lblDialogImg.setIcon(scaledInfoPic);
                case MSG_WARN -> parent.lblDialogImg.setIcon(scaledWarnPic);
                default -> parent.lblDialogImg.setIcon(scaledInfoPic);
            }

            parent.dlgGeneral.setVisible(true);
        }).start();
    }

    /**
     * Displays a dialog with the specified parameters, optionally running on the main thread.
     * <p>
     * If {@code runOnMainThread} is true, the dialog is shown synchronously; 
     * otherwise, it is shown asynchronously on a new thread.
     *
     * @param title           the dialog title.
     * @param msgHeading      the dialog heading text.
     * @param message         the main message content.
     * @param type            the dialog type: {@code "ERROR"}, {@code "NORMAL"}, or {@code "WARNING"}.
     * @param runOnMainThread if true, dialog shows on the main thread and blocks execution.
     */
    public void showDialog(String title, String msgHeading, String message, String type, boolean runOnMainThread) {
        if (runOnMainThread) {
            parent.dlgGeneral.setTitle(title);
            parent.lblDialogTitle.setText(msgHeading);
            parent.lblDialogMsg.setText(message);

            switch (type) {
                case MSG_ERR -> parent.lblDialogImg.setIcon(scaledErrorPic);
                case MSG_NORM -> parent.lblDialogImg.setIcon(scaledInfoPic);
                case MSG_WARN -> parent.lblDialogImg.setIcon(scaledWarnPic);
                default -> parent.lblDialogImg.setIcon(scaledInfoPic);
            }

            parent.dlgGeneral.setVisible(true);
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
     * @param type             the dialog type: {@code "ERROR"}, {@code "NORMAL"}, or {@code "WARNING"}.
     * @param dismissTimeMills how long (in milliseconds) to show the dialog before hiding it.
     */
    public void showDialog(String title, String msgHeading, String message, String type, int dismissTimeMills) {
        new Thread(() -> {
            parent.dlgGeneral.setTitle(title);
            parent.lblDialogTitle.setText(msgHeading);
            parent.lblDialogMsg.setText(message);

            switch (type) {
                case MSG_ERR -> parent.lblDialogImg.setIcon(scaledErrorPic);
                case MSG_NORM -> parent.lblDialogImg.setIcon(scaledInfoPic);
                case MSG_WARN -> parent.lblDialogImg.setIcon(scaledWarnPic);
                default -> parent.lblDialogImg.setIcon(scaledInfoPic);
            }

            parent.dlgGeneral.setVisible(true);

            try {
                Thread.sleep(dismissTimeMills);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (parent.dlgGeneral.isVisible()) {
                parent.dlgGeneral.setVisible(false);
            }
        }).start();
    }

    /**
     * Displays a modal input dialog with a custom heading and returns the user's input.
     * <p>
     * This method blocks execution until the user submits or closes the dialog.
     *
     * @param heading the heading text shown in the input dialog.
     * @return the input string entered by the user; may be empty but never null.
     */
    public String showInputDialog(String heading) {
        parent.lblInputHeading.setText(heading);
        parent.dlgInputDialog.setVisible(true);
        String input = parent.txfInputDialog.getText();
        parent.txfInputDialog.setText(""); // Clear for next input
        return input;
    }
}