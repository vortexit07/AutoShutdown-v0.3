package za.co.vortexit.pkg3.v0.autoshutdown;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.formdev.flatlaf.FlatDarkLaf;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Connor Lewis
 */
public class AutoShutdownGUI extends javax.swing.JFrame {

    private final javax.swing.ImageIcon scaledOnButtonPic;
    private final javax.swing.ImageIcon scaledOnButtonPressedPic;

    private final javax.swing.ImageIcon scaledOffButtonPic;
    private final javax.swing.ImageIcon scaledOffButtonPressedPic;

    private final Color BACKGROUND = new Color(30, 30, 30);
    private final Color FONT = new Color(73, 79, 91);

    private static Logger logger;
    private static CustomDialogManager dialogManager;

    private static boolean timesUpdated = false;

    private static TrayIcon trayIcon;

    /**
     * Creates new form AutoShutdownGUI
     *
     * @throws java.io.IOException
     * @throws java.awt.AWTException
     */
    public AutoShutdownGUI() throws IOException, AWTException {
        initComponents();

        // Load images using the class loader
        setIconImage(new ImageIcon(AutoShutdownGUI.class.getResource("/resources/images/icon.png")).getImage()
                .getScaledInstance(500, 500, Image.SCALE_SMOOTH));
        getContentPane().setBackground(BACKGROUND);

        // Button images
        ImageIcon onButtonPic = new ImageIcon(AutoShutdownGUI.class.getResource("/resources/images/on.png"));
        scaledOnButtonPic = new ImageIcon(onButtonPic.getImage().getScaledInstance(
                btnKillSwitch.getPreferredSize().width, btnKillSwitch.getPreferredSize().height, Image.SCALE_SMOOTH));

        ImageIcon offButtonPic = new ImageIcon(AutoShutdownGUI.class.getResource("/resources/images/off.png"));
        scaledOffButtonPic = new ImageIcon(offButtonPic.getImage().getScaledInstance(
                btnKillSwitch.getPreferredSize().width, btnKillSwitch.getPreferredSize().height, Image.SCALE_SMOOTH));

        ImageIcon onButtonPressedPic = new ImageIcon(
                AutoShutdownGUI.class.getResource("/resources/images/on_pressed.png"));
        scaledOnButtonPressedPic = new ImageIcon(onButtonPressedPic.getImage().getScaledInstance(
                btnKillSwitch.getPreferredSize().width, btnKillSwitch.getPreferredSize().height, Image.SCALE_SMOOTH));

        ImageIcon offButtonPressedPic = new ImageIcon(
                AutoShutdownGUI.class.getResource("/resources/images/off_pressed.png"));
        scaledOffButtonPressedPic = new ImageIcon(offButtonPressedPic.getImage().getScaledInstance(
                btnKillSwitch.getPreferredSize().width, btnKillSwitch.getPreferredSize().height, Image.SCALE_SMOOTH));

        btnKillSwitch.setIcon(scaledOnButtonPic);
        btnKillSwitch.setSelectedIcon(scaledOffButtonPic);

        cmbTimeSelector.getEditor().getEditorComponent().setBackground(BACKGROUND);
        cmbTimeSelector.getEditor().getEditorComponent().setForeground(FONT);

        java.awt.Point p = getLocation();

        p.x += getWidth() / 2 - dlgInsertToken.getWidth() / 2;
        p.y += getHeight() / 2 - dlgInsertToken.getHeight() / 2;
        dlgInsertToken.setLocation(p);

        p = getLocation();
        p.x += getWidth() / 2 - dlgGeneral.getWidth() / 2;
        p.y += getHeight() / 2 - dlgGeneral.getHeight() / 2;
        dlgGeneral.setLocation(p);

        p = getLocation();
        p.x += getWidth() / 4 - dlgSettingsMenu.getWidth() / 4;
        p.y += getHeight() / 4 - dlgSettingsMenu.getHeight() / 4;
        dlgSettingsMenu.setLocation(p);

        p = getLocation();
        p.x += getWidth() / 2 - dlgInputDialog.getWidth() / 2;
        p.y += getHeight() / 2 - dlgInputDialog.getHeight() / 2;
        dlgInputDialog.setLocation(p);

        dlgSettingsMenu.setSize(dlgSettingsMenu.getPreferredSize());

        lblEvent1.setText(null);
        lblEvent2.setText(null);
        lblEvent3.setText(null);
        lblEvent4.setText(null);
        lblEvent5.setText(null);
        lblEventsTitle.setText(null);

        if (SystemTray.isSupported()) {
            // Create a popup menu
            PopupMenu popup = new PopupMenu();
            MenuItem exitItem = new MenuItem("Exit");
            MenuItem showItem = new MenuItem("Show");
            exitItem.addActionListener((ActionEvent e) -> {
                System.exit(0);
            });
            showItem.addActionListener((ActionEvent e) -> {
                setVisible(true);
                setExtendedState(javax.swing.JFrame.NORMAL);
            });
            popup.add(showItem);
            popup.add(exitItem);

            // Create a tray icon
            ImageIcon icon = new ImageIcon(
                    new javax.swing.ImageIcon(getClass().getResource("/resources/images/icon.png")).getImage()
                            .getScaledInstance(16, 16, Image.SCALE_SMOOTH));

            trayIcon = new TrayIcon(icon.getImage(), "Auto Shutdown", popup);
            trayIcon.setImageAutoSize(true);

            // Add the tray icon to the system tray
            SystemTray tray = SystemTray.getSystemTray();

            tray.add(trayIcon);

            // Restore the frame when the tray icon is double-clicked
            trayIcon.addActionListener((ActionEvent e) -> {
                setVisible(true);
                setExtendedState(javax.swing.JFrame.NORMAL);
            });

            dialogManager = new CustomDialogManager(this);

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dlgAreaSearch = new javax.swing.JDialog();
        lblAreaSearchBarTitle = new javax.swing.JLabel();
        txfAreaSearchBar = new javax.swing.JTextField();
        dlgInsertToken = new javax.swing.JDialog();
        lblEnterTokenTitle = new javax.swing.JLabel();
        txfEnterTokenField = new javax.swing.JTextField();
        dlgGeneral = new javax.swing.JDialog();
        pnlGeneralDlgText = new javax.swing.JPanel();
        lblDialogTitle = new javax.swing.JLabel();
        lblDialogMsg = new javax.swing.JLabel();
        lblDialogImg = new javax.swing.JLabel();
        dlgSettingsMenu = new javax.swing.JDialog();
        chbRunOnStart = new javax.swing.JCheckBox();
        chbStartMin = new javax.swing.JCheckBox();
        btnApplySettings = new javax.swing.JButton();
        btnCancelSettings = new javax.swing.JButton();
        lblSettingsHeader = new javax.swing.JLabel();
        lblAboutHeader = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        lblVersionNo = new javax.swing.JLabel();
        lblUpdated = new javax.swing.JLabel();
        lblUpdatedDate = new javax.swing.JLabel();
        btnProjectLink = new javax.swing.JButton();
        dlgInputDialog = new javax.swing.JDialog();
        lblInputHeading = new javax.swing.JLabel();
        txfInputDialog = new javax.swing.JTextField();
        cmbTimeSelector = new javax.swing.JComboBox<>();
        pnlEventsPanel = new javax.swing.JPanel();
        lblEventsTitle = new javax.swing.JLabel();
        lblEvent1 = new javax.swing.JLabel();
        lblEvent2 = new javax.swing.JLabel();
        lblEvent3 = new javax.swing.JLabel();
        lblEvent4 = new javax.swing.JLabel();
        lblEvent5 = new javax.swing.JLabel();
        pnlCenterButtons = new javax.swing.JPanel();
        btnKillSwitch = new javax.swing.JButton();
        btnAreaSelector = new javax.swing.JButton();
        lblCountdown = new javax.swing.JLabel();
        mnbOptionsBar = new javax.swing.JMenuBar();
        mnuSettings = new javax.swing.JMenu();
        mnuHelp = new javax.swing.JMenu();

        dlgAreaSearch.setTitle("Search For Area");
        dlgAreaSearch.setAlwaysOnTop(true);
        dlgAreaSearch.setBackground(BACKGROUND);
        dlgAreaSearch.setBounds(new java.awt.Rectangle(0, 0, 350, 210));
        dlgAreaSearch.setLocation(new java.awt.Point(0, 0));
        dlgAreaSearch.setModal(true);
        dlgAreaSearch.setResizable(false);
        dlgAreaSearch.setType(java.awt.Window.Type.POPUP);
        dlgAreaSearch.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                dlgAreaSearchComponentHidden(evt);
            }
        });
        dlgAreaSearch.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                dlgAreaSearchWindowClosing(evt);
            }
        });
        dlgAreaSearch.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblAreaSearchBarTitle.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblAreaSearchBarTitle.setText("Enter Area Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        dlgAreaSearch.getContentPane().add(lblAreaSearchBarTitle, gridBagConstraints);

        txfAreaSearchBar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txfAreaSearchBar.setToolTipText("Press <Enter> to search");
        txfAreaSearchBar.setMinimumSize(new java.awt.Dimension(64, 30));
        txfAreaSearchBar.setPreferredSize(new java.awt.Dimension(140, 25));
        txfAreaSearchBar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txfAreaSearchBarKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 26, 0);
        dlgAreaSearch.getContentPane().add(txfAreaSearchBar, gridBagConstraints);

        dlgInsertToken.setTitle("Search For Area");
        dlgInsertToken.setAlwaysOnTop(true);
        dlgInsertToken.setBackground(BACKGROUND);
        dlgInsertToken.setBounds(new java.awt.Rectangle(0, 0, 350, 210));
        dlgInsertToken.setLocation(new java.awt.Point(0, 0));
        dlgInsertToken.setMinimumSize(new java.awt.Dimension(350, 210));
        dlgInsertToken.setModal(true);
        dlgInsertToken.setResizable(false);
        dlgInsertToken.setType(java.awt.Window.Type.POPUP);
        dlgInsertToken.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                dlgInsertTokenComponentHidden(evt);
            }
        });
        dlgInsertToken.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                dlgInsertTokenWindowClosing(evt);
            }
        });
        dlgInsertToken.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblEnterTokenTitle.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblEnterTokenTitle.setText("Insert ESP token");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        dlgInsertToken.getContentPane().add(lblEnterTokenTitle, gridBagConstraints);

        txfEnterTokenField.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txfEnterTokenField.setToolTipText("Press <Enter> to submit");
        txfEnterTokenField.setMinimumSize(new java.awt.Dimension(64, 30));
        txfEnterTokenField.setPreferredSize(new java.awt.Dimension(140, 25));
        txfEnterTokenField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txfEnterTokenFieldKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 26, 0);
        dlgInsertToken.getContentPane().add(txfEnterTokenField, gridBagConstraints);

        dlgInsertToken.getAccessibleContext().setAccessibleName("Enter ESP API Token");

        dlgGeneral.setTitle("Search For Area");
        dlgGeneral.setAlwaysOnTop(true);
        dlgGeneral.setBackground(BACKGROUND);
        dlgGeneral.setBounds(new java.awt.Rectangle(0, 0, 350, 210));
        dlgGeneral.setLocation(new java.awt.Point(0, 0));
        dlgGeneral.setModal(true);
        dlgGeneral.setResizable(false);
        dlgGeneral.setType(java.awt.Window.Type.POPUP);
        dlgGeneral.getContentPane().setLayout(new java.awt.GridBagLayout());

        pnlGeneralDlgText.setLayout(new java.awt.GridBagLayout());

        lblDialogTitle.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblDialogTitle.setText("Insert ESP token");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        pnlGeneralDlgText.add(lblDialogTitle, gridBagConstraints);

        lblDialogMsg.setText("woobus loobus");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlGeneralDlgText.add(lblDialogMsg, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 16, 0);
        dlgGeneral.getContentPane().add(pnlGeneralDlgText, gridBagConstraints);

        lblDialogImg.setOpaque(true);
        lblDialogImg.setPreferredSize(new java.awt.Dimension(50, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 12, 0);
        dlgGeneral.getContentPane().add(lblDialogImg, gridBagConstraints);

        dlgSettingsMenu.setAlwaysOnTop(true);
        dlgSettingsMenu.setModal(true);
        dlgSettingsMenu.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        dlgSettingsMenu.setResizable(false);
        dlgSettingsMenu.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                dlgSettingsMenuWindowClosing(evt);
            }
        });

        chbRunOnStart.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        chbRunOnStart.setText("Run on Startup");
        chbRunOnStart.setFocusPainted(false);

        chbStartMin.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        chbStartMin.setText("Start Minimized");
        chbStartMin.setFocusPainted(false);

        btnApplySettings.setText("Apply");
        btnApplySettings.setFocusPainted(false);
        btnApplySettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplySettingsActionPerformed(evt);
            }
        });

        btnCancelSettings.setText("Cancel");
        btnCancelSettings.setFocusPainted(false);
        btnCancelSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelSettingsActionPerformed(evt);
            }
        });

        lblSettingsHeader.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblSettingsHeader.setText("Settings");

        lblAboutHeader.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblAboutHeader.setText("About");

        lblVersion.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        lblVersion.setText("Version: ");

        lblVersionNo.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        lblVersionNo.setText("0.3.1");

        lblUpdated.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        lblUpdated.setText("Updated:");

        lblUpdatedDate.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        lblUpdatedDate.setText("22/07/2025");

        btnProjectLink.setBackground(new java.awt.Color(60, 63, 65));
        btnProjectLink.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        btnProjectLink.setForeground(new java.awt.Color(0, 102, 204));
        btnProjectLink.setText("vortexit.co.za");
        btnProjectLink.setBorder(null);
        btnProjectLink.setBorderPainted(false);
        btnProjectLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProjectLink.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnProjectLink.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnProjectLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProjectLinkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dlgSettingsMenuLayout = new javax.swing.GroupLayout(dlgSettingsMenu.getContentPane());
        dlgSettingsMenu.getContentPane().setLayout(dlgSettingsMenuLayout);
        dlgSettingsMenuLayout.setHorizontalGroup(
            dlgSettingsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgSettingsMenuLayout.createSequentialGroup()
                .addContainerGap(192, Short.MAX_VALUE)
                .addComponent(btnCancelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnApplySettings, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(dlgSettingsMenuLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(dlgSettingsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dlgSettingsMenuLayout.createSequentialGroup()
                        .addGroup(dlgSettingsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblVersion)
                            .addComponent(lblUpdated))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dlgSettingsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUpdatedDate)
                            .addComponent(lblVersionNo)))
                    .addComponent(lblSettingsHeader)
                    .addGroup(dlgSettingsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblAboutHeader)
                        .addComponent(chbRunOnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chbStartMin, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnProjectLink))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dlgSettingsMenuLayout.setVerticalGroup(
            dlgSettingsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlgSettingsMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSettingsHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbRunOnStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbStartMin)
                .addGap(18, 18, 18)
                .addComponent(lblAboutHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgSettingsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVersion)
                    .addComponent(lblVersionNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgSettingsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdated)
                    .addComponent(lblUpdatedDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProjectLink, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(dlgSettingsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnApplySettings)
                    .addComponent(btnCancelSettings))
                .addContainerGap())
        );

        dlgInputDialog.setTitle("Search For Area");
        dlgInputDialog.setAlwaysOnTop(true);
        dlgInputDialog.setBackground(BACKGROUND);
        dlgInputDialog.setBounds(new java.awt.Rectangle(0, 0, 350, 210));
        dlgInputDialog.setLocation(new java.awt.Point(0, 0));
        dlgInputDialog.setModal(true);
        dlgInputDialog.setResizable(false);
        dlgInputDialog.setType(java.awt.Window.Type.POPUP);

        lblInputHeading.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblInputHeading.setText("Insert ESP token");

        txfInputDialog.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txfInputDialog.setToolTipText("Press <Enter> to submit");
        txfInputDialog.setMinimumSize(new java.awt.Dimension(64, 30));
        txfInputDialog.setPreferredSize(new java.awt.Dimension(140, 25));
        txfInputDialog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txfInputDialogKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout dlgInputDialogLayout = new javax.swing.GroupLayout(dlgInputDialog.getContentPane());
        dlgInputDialog.getContentPane().setLayout(dlgInputDialogLayout);
        dlgInputDialogLayout.setHorizontalGroup(
            dlgInputDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlgInputDialogLayout.createSequentialGroup()
                .addContainerGap(114, Short.MAX_VALUE)
                .addGroup(dlgInputDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblInputHeading)
                    .addComponent(txfInputDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96))
        );
        dlgInputDialogLayout.setVerticalGroup(
            dlgInputDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgInputDialogLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblInputHeading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txfInputDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Auto Shutdown");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(51, 51, 51));
        setLocation(getLocation());
        setLocationByPlatform(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        cmbTimeSelector.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        cmbTimeSelector.setForeground(new java.awt.Color(153, 153, 153));
        cmbTimeSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 Minute", "2 Minutes", "4 Minutes", "5 Minutes", "Custom" }));
        cmbTimeSelector.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(80, 82, 84), 1, true));
        cmbTimeSelector.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbTimeSelector.setFocusable(false);
        cmbTimeSelector.setMaximumSize(new java.awt.Dimension(108, 24));
        cmbTimeSelector.setMinimumSize(new java.awt.Dimension(108, 24));
        cmbTimeSelector.setPreferredSize(new java.awt.Dimension(108, 24));
        cmbTimeSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTimeSelectorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(128, 11, 10, 3);
        getContentPane().add(cmbTimeSelector, gridBagConstraints);

        pnlEventsPanel.setBackground(BACKGROUND);
        pnlEventsPanel.setLayout(new java.awt.GridBagLayout());

        lblEventsTitle.setBackground(BACKGROUND);
        lblEventsTitle.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblEventsTitle.setForeground(FONT);
        lblEventsTitle.setText("Upcoming Events: (Stage 0)");
        lblEventsTitle.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        pnlEventsPanel.add(lblEventsTitle, gridBagConstraints);

        lblEvent1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEvent1.setForeground(FONT);
        lblEvent1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEvent1.setText("None");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 0, 179);
        pnlEventsPanel.add(lblEvent1, gridBagConstraints);

        lblEvent2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEvent2.setForeground(FONT);
        lblEvent2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEvent2.setText("16:00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 179);
        pnlEventsPanel.add(lblEvent2, gridBagConstraints);

        lblEvent3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEvent3.setForeground(FONT);
        lblEvent3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 179);
        pnlEventsPanel.add(lblEvent3, gridBagConstraints);

        lblEvent4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEvent4.setForeground(FONT);
        lblEvent4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 179);
        pnlEventsPanel.add(lblEvent4, gridBagConstraints);

        lblEvent5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEvent5.setForeground(FONT);
        lblEvent5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 179);
        pnlEventsPanel.add(lblEvent5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 39, 8);
        getContentPane().add(pnlEventsPanel, gridBagConstraints);

        pnlCenterButtons.setBackground(BACKGROUND);
        pnlCenterButtons.setLayout(new java.awt.GridBagLayout());

        btnKillSwitch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/on.png")));
        btnKillSwitch.setToolTipText("Toggle shutdown feature");
        btnKillSwitch.setBorder(null);
        btnKillSwitch.setBorderPainted(false);
        btnKillSwitch.setContentAreaFilled(false);
        btnKillSwitch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKillSwitch.setFocusPainted(false);
        btnKillSwitch.setMaximumSize(new java.awt.Dimension(180, 180));
        btnKillSwitch.setMinimumSize(new java.awt.Dimension(180, 180));
        btnKillSwitch.setPreferredSize(new java.awt.Dimension(110, 110));
        btnKillSwitch.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/off.png")));
        btnKillSwitch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnKillSwitchMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnKillSwitchMouseReleased(evt);
            }
        });
        btnKillSwitch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKillSwitchActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 3.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 137, 7);
        pnlCenterButtons.add(btnKillSwitch, gridBagConstraints);

        btnAreaSelector.setBackground(BACKGROUND);
        btnAreaSelector.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAreaSelector.setForeground(FONT);
        btnAreaSelector.setText("Search for Area");
        btnAreaSelector.setBorder(null);
        btnAreaSelector.setBorderPainted(false);
        btnAreaSelector.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAreaSelector.setFocusPainted(false);
        btnAreaSelector.setMaximumSize(new java.awt.Dimension(160, 19));
        btnAreaSelector.setMinimumSize(new java.awt.Dimension(160, 19));
        btnAreaSelector.setPreferredSize(new java.awt.Dimension(160, 19));
        btnAreaSelector.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAreaSelectorMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 3.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(232, 60, 231, 62);
        pnlCenterButtons.add(btnAreaSelector, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 158, 78, 30);
        getContentPane().add(pnlCenterButtons, gridBagConstraints);

        lblCountdown.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCountdown.setForeground(new java.awt.Color(81, 81, 81));
        lblCountdown.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCountdown.setText("Next: ");
        lblCountdown.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(265, 0, 0, 26);
        getContentPane().add(lblCountdown, gridBagConstraints);

        mnuSettings.setText("Settings");
        mnuSettings.setDelay(150);
        mnuSettings.setFocusable(false);
        mnuSettings.setRequestFocusEnabled(false);
        mnuSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuSettingsMouseClicked(evt);
            }
        });
        mnbOptionsBar.add(mnuSettings);

        mnuHelp.setText("Help");
        mnuHelp.setFocusable(false);
        mnuHelp.setRequestFocusEnabled(false);
        mnuHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuHelpMouseClicked(evt);
            }
        });
        mnbOptionsBar.add(mnuHelp);

        setJMenuBar(mnbOptionsBar);

        setSize(new java.awt.Dimension(816, 508));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnuHelpMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_mnuHelpMouseClicked
        try {
            Desktop.getDesktop().browse(AutoShutdownGUI.class.getResource("/resources/docs/help.html").toURI());
        } catch (IOException | URISyntaxException e) {
            logger.log("Unable to open help documentation", e);
        }
    }// GEN-LAST:event_mnuHelpMouseClicked

    private void mnuSettingsMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_mnuSettingsMouseClicked
        JSONManager settings = new JSONManager(new File("settings.json"));
        chbRunOnStart.setSelected(settings.getBool("runOnStartup"));
        chbStartMin.setSelected(settings.getBool("startMinimized"));
        mnuSettings.setSelected(false);
        dlgSettingsMenu.setVisible(true);
    }// GEN-LAST:event_mnuSettingsMouseClicked

    private void cmbTimeSelectorActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmbTimeSelectorActionPerformed
        JSONManager settingsJson = new JSONManager(new File("settings.json"));
        int selectedIndex = cmbTimeSelector.getSelectedIndex();
        int lastInList = cmbTimeSelector.getItemCount() - 1;

        // Handle custom time input
        if (selectedIndex == lastInList) {
            if (cmbTimeSelector.getItemCount() == 6) {
                cmbTimeSelector.removeItemAt(4);
            }

            while (true) {
                String input = dialogManager.showInputDialog("Enter time (minutes)");
                if (input.isBlank()) {
                    restorePreviousTime(settingsJson);
                    break;
                } else if (org.apache.commons.lang3.StringUtils.isNumeric(input)) {
                    int inputtedInt = Integer.parseInt(input);
                    updateTimeBefore(settingsJson, inputtedInt);

                    // Insert new option if not standard
                    switch (inputtedInt) {
                        case 1, 2 -> cmbTimeSelector.setSelectedIndex(inputtedInt - 1);
                        case 4, 5 -> cmbTimeSelector.setSelectedIndex(inputtedInt - 2);
                        default -> {
                            cmbTimeSelector.insertItemAt(inputtedInt + " minutes", 4);
                            cmbTimeSelector.setSelectedIndex(4);
                        }
                    }
                    updateCountdownLabel();
                    fiveMinuteWarning = false;
                    oneMinuteWarning = false;
                    break;
                } else {
                    dialogManager.showDialog("Error", "Invalid Integer", "The value you entered is invalid",
                            CustomDialogManager.MSG_ERR);
                }
            }
            return;
        }

        // Handle predefined times
        int[] predefinedTimes = { 1, 2, 4, 5 };
        if (selectedIndex >= 0 && selectedIndex < predefinedTimes.length) {
            updateTimeBefore(settingsJson, predefinedTimes[selectedIndex]);

            if (cmbTimeSelector.getItemCount() == 6) {
                cmbTimeSelector.removeItemAt(4);
            }
            updateCountdownLabel();
            return;
        }

        // Handle custom entry still in dropdown
        if (selectedIndex == 4) {
            int parsed = parseMinutes(cmbTimeSelector.getSelectedItem().toString());
            updateTimeBefore(settingsJson, parsed);
            updateCountdownLabel();
        }

    }// GEN-LAST:event_cmbTimeSelectorActionPerformed

    private void updateTimeBefore(JSONManager settingsJson, int newTime) {
        settingsJson.appendFile("timeBefore", newTime);
        timeBefore = newTime;
        timesUpdated = true;
    }

    private void updateCountdownLabel() {
        LocalTime next = getNextEventFromSettings();
        if (next != null) {
            lblCountdown.setText("Next: " + next.minusMinutes(timeBefore));
        } else {
            lblCountdown.setText("");
        }
    }

    private void restorePreviousTime(JSONManager settingsJson) {
        int previous = settingsJson.getInt("timeBefore");
        switch (previous) {
            case 1, 2 -> cmbTimeSelector.setSelectedIndex(previous - 1);
            case 4, 5 -> cmbTimeSelector.setSelectedIndex(previous - 2);
            default -> {
                cmbTimeSelector.insertItemAt(previous + " minutes", 4);
                cmbTimeSelector.setSelectedIndex(4);
            }
        }
        timeBefore = previous;
        updateCountdownLabel();
    }

    private static LocalTime getNextEventFromSettings() {
        JSONManager settingsFile = new JSONManager(new File("settings.json"));
        LocalTime time1, time2, time3, time4, startTime, endTime;
        time1 = settingsFile.getTime("time1");
        time2 = settingsFile.getTime("time2");
        time3 = settingsFile.getTime("time3");
        time4 = settingsFile.getTime("time4");
        startTime = settingsFile.getTime("startTime");
        endTime = settingsFile.getTime("endTime");
        if (time1 != null && (time1.isAfter(startTime) && time1.isBefore(endTime))) {
            return time1;
        } else if (time2 != null && (time2.isAfter(startTime) && time2.isBefore(endTime))) {
            return time2;
        } else if (time3 != null && (time3.isAfter(startTime) && time3.isBefore(endTime))) {
            return time3;
        } else if (time4 != null && (time4.isAfter(startTime) && time4.isBefore(endTime))) {
            return time4;
        } else if (settingsFile.getBool("atMidnight")) {
            return LocalTime.parse("00:00");
        } else
            return null;

    }

    private static int parseMinutes(String timeString) {
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(timeString);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalArgumentException("No integer found in the input string.");
    }

    private void txfInputDialogKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txfInputDialogKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            dlgInputDialog.setVisible(false);
        }
    }// GEN-LAST:event_txfInputDialogKeyPressed

    private void btnApplySettingsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnApplySettingsActionPerformed
        JSONManager settings = new JSONManager(new File("settings.json"));
        settings.appendFile("runOnStartup", chbRunOnStart.isSelected());
        settings.appendFile("startMinimized", chbStartMin.isSelected());
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                StartupManager.windowsROS(chbRunOnStart.isSelected());
            } else if (os.contains("nix")) {
                StartupManager.unixROS(chbRunOnStart.isSelected());
            }
        } catch (UnsupportedEncodingException e) {
            logger.log(e);
            dialogManager.showDialog("Error", "Unsupported OS",
                    "Your operating system is not supported for this feature, see logs for details",
                    CustomDialogManager.MSG_ERR);
        }
        dlgSettingsMenu.setVisible(false);
    }// GEN-LAST:event_btnApplySettingsActionPerformed

    private void btnCancelSettingsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelSettingsActionPerformed
        JSONManager settings = new JSONManager(new File("settings.json"));
        chbRunOnStart.setSelected(settings.getBool("runOnStartup"));
        chbStartMin.setSelected(settings.getBool("startMinimized"));
        dlgSettingsMenu.setVisible(false);
    }// GEN-LAST:event_btnCancelSettingsActionPerformed

    private void btnProjectLinkActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnProjectLinkActionPerformed
        try {
            Desktop.getDesktop().browse(new URI("https://www.projects.vortexit.co.za/projects/auto-shutdown"));
        } catch (IOException | URISyntaxException e) {
            logger.log(e);
        }
        dlgSettingsMenu.setVisible(false);
    }// GEN-LAST:event_btnProjectLinkActionPerformed

    private void formWindowIconified(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowIconified
        setVisible(false);
    }// GEN-LAST:event_formWindowIconified

    private void dlgSettingsMenuWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_dlgSettingsMenuWindowClosing
        JSONManager settings = new JSONManager(new File("settings.json"));
        chbRunOnStart.setSelected(settings.getBool("runOnStartup"));
        chbStartMin.setSelected(settings.getBool("startMinimized"));
    }// GEN-LAST:event_dlgSettingsMenuWindowClosing

    private void dlgInsertTokenWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_dlgInsertTokenWindowClosing
        logger.log("Closing\n\n\n");
        System.exit(0);
    }// GEN-LAST:event_dlgInsertTokenWindowClosing

    private LocalTime lastOn = null;

    private void btnKillSwitchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnKillSwitchActionPerformed
        btnKillSwitch.setSelected(!btnKillSwitch.isSelected());

        // Program Inactive
        if (btnKillSwitch.isSelected()) {
            trayIcon.displayMessage("Auto Shutdown", "Automatic shutdown is inactive", MessageType.INFO);
            lblCountdown.setVisible(false);
            lastOn = LocalTime.now();
        }
        // Program Active
        else {
            trayIcon.displayMessage("Auto Shutdown", "Automatic shutdown is active", MessageType.INFO);
            lblCountdown.setVisible(true);
            // Refresh schedule if application has been inactive for more than 1 hour
            if (Duration.between(lastOn, LocalTime.now()).toHours() > 1) {
                try {
                    getScheduleAndUpdateGUI();
                } catch (JsonProcessingException | HttpTimeoutException | InterruptedException e) {
                    logger.log(e);
                    e.printStackTrace();
                }
            }
        }
    }// GEN-LAST:event_btnKillSwitchActionPerformed

    private void btnKillSwitchMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnKillSwitchMouseReleased
        btnKillSwitch.setIcon(scaledOnButtonPic);
        btnKillSwitch.setSelectedIcon(scaledOffButtonPic);
    }// GEN-LAST:event_btnKillSwitchMouseReleased

    private void btnKillSwitchMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnKillSwitchMousePressed
        btnKillSwitch.setSelectedIcon(scaledOffButtonPressedPic);
        btnKillSwitch.setIcon(scaledOnButtonPressedPic);
    }// GEN-LAST:event_btnKillSwitchMousePressed

    private void txfAreaSearchBarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txfAreaSearchBarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String input = txfAreaSearchBar.getText();

                APIManager api = new APIManager();
                JSONManager jsonFile = new JSONManager(new File("settings.json"));

                String areaInfo = api.getAreaInfo(input);
                JSONManager json = new JSONManager(areaInfo);

                if (!json.has("error")) {
                    String areaID = json.getString("areaID"), areaName = json.getString("areaName");

                    if (areaID != null && areaName != null) {
                        jsonFile.appendFile("areaID", areaID);
                        jsonFile.appendFile("areaName", areaName);
                    } else {
                        logger.log("Error: Get area info returned null");
                        dialogManager.showDialog("An error occurred", "Error",
                                "An unexpected error occurred, see logs for details",
                                CustomDialogManager.MSG_ERR);
                    }

                    btnAreaSelector.setText(areaName);
                    getScheduleAndUpdateGUI();

                    logger.log("Setting area name - " + areaName
                            + "\n\tSetting area id - " + areaID);
                } else {
                    btnAreaSelector.setText(jsonFile.getString("areaName"));
                    dialogManager.showDialog("An error occurred", "Error " + json.getInt("error"),
                            HTTP.get(json.getInt("error")),
                            CustomDialogManager.MSG_ERR);
                    logger.log("API Error " + json.getInt("error"));
                }

                dlgAreaSearch.setVisible(false);
                txfAreaSearchBar.setText("");
            } catch (IOException | InterruptedException e) {
                logger.log(e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dlgAreaSearch.setVisible(false);
            txfAreaSearchBar.setText("");
            btnAreaSelector.setText(new JSONManager(new File("settings.json")).getString("areaName"));
        }

    }// GEN-LAST:event_txfAreaSearchBarKeyPressed

    private void btnAreaSelectorMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnAreaSelectorMouseClicked
        java.awt.Point p = getLocation();
        p.x += getWidth() / 2 - dlgAreaSearch.getWidth() / 2;
        p.y += getHeight() / 2 - dlgAreaSearch.getHeight() / 2;
        btnAreaSelector.setText("loading...");
        dlgAreaSearch.setLocation(p);
        dlgAreaSearch.setEnabled(true);
        dlgAreaSearch.setVisible(true);
    }// GEN-LAST:event_btnAreaSelectorMouseClicked

    private void dlgAreaSearchComponentHidden(java.awt.event.ComponentEvent evt) {// GEN-FIRST:event_dlgAreaSearchComponentHidden
        txfAreaSearchBar.setText("");
    }// GEN-LAST:event_dlgAreaSearchComponentHidden

    private void txfEnterTokenFieldKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txfEnterTokenFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String input = txfEnterTokenField.getText();

            JSONManager json = new JSONManager(new File("settings.json"));
            json.appendFile("token", input);
            btnAreaSelector.setText(json.getString("areaName"));

            dlgInsertToken.setVisible(false);
            txfEnterTokenField.setText("");
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dlgInsertToken.setVisible(false);
            txfEnterTokenField.setText("");
        }
    }// GEN-LAST:event_txfEnterTokenFieldKeyPressed

    private void dlgInsertTokenComponentHidden(java.awt.event.ComponentEvent evt) {// GEN-FIRST:event_dlgInsertTokenComponentHidden
        txfEnterTokenField.setText("");
    }// GEN-LAST:event_dlgInsertTokenComponentHidden

    private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
        logger.log("Closing\n\n\n");
    }// GEN-LAST:event_formWindowClosing

    private void dlgAreaSearchWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_dlgAreaSearchWindowClosing
        btnAreaSelector.setText(new JSONManager(new File("settings.json")).getString("areaName"));
    }// GEN-LAST:event_dlgAreaSearchWindowClosing

    private static int timeBefore = 1;

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws InterruptedException, IOException {
        logger = new Logger();
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutoShutdownGUI.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new AutoShutdownGUI().setVisible(true);
            } catch (IOException | AWTException e) {
                logger.log("Error initializing GUI", e);
                dialogManager.showDialog("Initialization Error", "Error",
                        "An error occurred while initializing the GUI, see logs for details",
                        CustomDialogManager.MSG_ERR);
                System.exit(1);
            }
        });

        Thread.sleep(150);
        logger.log("Starting");

        JSONManager settingsJson;
        File settingsFile = new File("settings.json");
        boolean tokenValid = false;

        // Determine if operating system is supported
        if (ShutdownUtils.getOS() == -1) {
            logger.log("Unsupported operating system \"" + System.getProperty("os.name") + "\"");
            dialogManager.showDialog("Unsuported operating system", "System Error",
                    "This operating system is not supported",
                    CustomDialogManager.MSG_ERR,
                    true);
            System.exit(0);
        }

        // Check if settings file exists, create one if not
        if (settingsFile.length() != 0) {
            settingsJson = new JSONManager(settingsFile);
        } else {
            settingsJson = new JSONManager(settingsFile, true);
        }

        // Get the shutdown timing from the settings file and update GUI accordingly
        timeBefore = settingsJson.getInt("timeBefore");
        switch (timeBefore) {
            case 1, 2 ->
                cmbTimeSelector.setSelectedIndex(timeBefore - 1);
            case 4, 5 ->
                cmbTimeSelector.setSelectedIndex(timeBefore - 2);
            default -> {
                cmbTimeSelector.insertItemAt(timeBefore + " minutes", 4);
                cmbTimeSelector.setSelectedIndex(4);
            }

        }

        APIManager espAPI = new APIManager();
        int apiStatus = -1;
        try {
            apiStatus = espAPI.getStatus();
        } catch (HttpTimeoutException e) {
            logger.log("API Response Timed Out", e);
            dialogManager.showDialog("API Error", "API Response Timed Out",
                    "The ESP API is unresponsive, please try again later",
                    CustomDialogManager.MSG_ERR);
        }

        logger.log("Checking API Status (" + apiStatus + " - " + HTTP.get(apiStatus) + ")");

        if (apiStatus != 200) {
            if (apiStatus != 403) {
                dialogManager.showDialog("API Status", "API Status", apiStatus + " - " + HTTP.get(apiStatus),
                        CustomDialogManager.MSG_ERR,
                        6000);
                tokenValid = true;
            }
        } else {
            tokenValid = true;
        }

        String token = espAPI.getToken();
        boolean tokenEntered = false;
        if (!tokenValid) {
            try {
                Desktop.getDesktop().browse(AutoShutdownGUI.class.getResource("/resources/docs/help.html").toURI());
            } catch (IOException | URISyntaxException e) {
                logger.log(e);
            }
        }
        while (!tokenValid) {
            if (tokenEntered) {
                dialogManager.showDialog("Warning", "Token Invalid", "The token you have entered is invalid",
                        CustomDialogManager.MSG_WARN);
            }
            dlgInsertToken.setVisible(true);
            token = espAPI.getToken();

            tokenValid = !(token == null || token.isEmpty() || token.isBlank() || token.length() < 35
                    || token.length() > 35);

            tokenEntered = true;
        }

        String areaName = !settingsJson.getString("areaName").isBlank() ? settingsJson.getString("areaName") : null;

        if (areaName == null) {
            btnAreaSelector.setText("Search for Area");
        } else {
            btnAreaSelector.setText(areaName);
            getScheduleAndUpdateGUI();
        }

        boolean atMidnight = settingsJson.getBool("atMidnight");

        LocalTime time1, time2, time3, time4;
        time1 = settingsJson.getTime("time1") != null ? settingsJson.getTime("time1").minusMinutes(timeBefore) : null;
        time2 = settingsJson.getTime("time2") != null ? settingsJson.getTime("time2").minusMinutes(timeBefore) : null;
        time3 = settingsJson.getTime("time3") != null ? settingsJson.getTime("time3").minusMinutes(timeBefore) : null;
        time4 = settingsJson.getTime("time4") != null ? settingsJson.getTime("time4").minusMinutes(timeBefore) : null;

        timesUpdated = false;

        // Main application loop
        logger.log("Starting check loop");
        trayIcon.displayMessage("Auto Shutdown", "Automatic shutdown is active", MessageType.INFO);
        while (true) {
            while (!btnKillSwitch.isSelected()) {

                settingsJson.setFile(settingsFile);
                timeBefore = settingsJson.getInt("timeBefore");

                if (timesUpdated) {
                    atMidnight = settingsJson.getBool("atMidnight");
                    time1 = settingsJson.getTime("time1") != null
                            ? settingsJson.getTime("time1").minusMinutes(timeBefore)
                            : null;
                    time2 = settingsJson.getTime("time2") != null
                            ? settingsJson.getTime("time2").minusMinutes(timeBefore)
                            : null;
                    time3 = settingsJson.getTime("time3") != null
                            ? settingsJson.getTime("time3").minusMinutes(timeBefore)
                            : null;
                    time4 = settingsJson.getTime("time4") != null
                            ? settingsJson.getTime("time4").minusMinutes(timeBefore)
                            : null;
                    timesUpdated = false;
                }

                runScheduleUpdater();
                // Update schedule at 00:00 and 05:00 sharp
                LocalTime now = LocalTime.now();
                if (now.getHour() == 0 && now.getMinute() == 0 && now.getSecond() == 0
                        || now.getHour() == 5 && now.getMinute() == 0 && now.getSecond() == 0) {
                    try {
                        getScheduleAndUpdateGUI();
                    } catch (JsonProcessingException | HttpTimeoutException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int nextShutdown = ShutdownUtils.timeUntilNextShutdown(timeBefore, time1, time2, time3, time4,
                        atMidnight);

                if (nextShutdown <= 300 && nextShutdown > 298 && !fiveMinuteWarning) {
                    fiveMinuteWarning = true;
                    trayIcon.displayMessage("Auto Shutdown", "Shutting down in 5 minutes", MessageType.INFO);
                }

                if (nextShutdown <= 60 && nextShutdown > 45 && !oneMinuteWarning) {
                    oneMinuteWarning = true;
                    trayIcon.displayMessage("Auto Shutdown", "Shutting down in 1 minute", MessageType.INFO);
                }

                if (nextShutdown <= 0 && nextShutdown >= -60) {
                    logger.log("Shutting down now");
                    ShutdownUtils.shutdown();
                }

                Thread.sleep(3000);

            }
            Thread.sleep(10000);
        }
    }

    private static boolean fiveMinuteWarning = false, oneMinuteWarning = false;

    /**
     * Runs the schedule updater at specific times during the day.
     * <p>
     * This method checks the current time and triggers the schedule update if it
     * matches one of the predefined times (05:01, 12:01, 16:01, or 22:01).
     */
    private static void runScheduleUpdater() {
        LocalTime now = LocalTime.now();
        if (now == LocalTime.parse("05:01") || now == LocalTime.parse("12:01") || now == LocalTime.parse("16:01")
                || now == LocalTime.parse("22:01")) {
            try {
                getScheduleAndUpdateGUI();
            } catch (JsonProcessingException | HttpTimeoutException | InterruptedException e) {
                logger.log(e);
            }
        }
    }

    /**
     * Gets the current day's loadshedding schedule, filters out invalid times
     * and updates the GUI accordingly
     *
     * @throws JsonMappingException
     * @throws JsonProcessingException
     * @throws InterruptedException
     * @throws HttpTimeoutException
     */
    private static void getScheduleAndUpdateGUI()
            throws JsonMappingException, JsonProcessingException, InterruptedException, HttpTimeoutException {

        APIManager api = new APIManager();
        JSONManager schedule = new JSONManager(api.getSchedule());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        if (!schedule.has("error")) {

            int stage = schedule.getInt("stage");

            if (stage > 0) {

                LocalTime now = LocalTime.now();
                LocalTime startTime = schedule.has("startTime")
                        ? LocalTime.parse(schedule.getString("startTime"), formatter)
                        : null;
                LocalTime endTime = schedule.has("endTime") ? LocalTime.parse(schedule.getString("endTime"), formatter)
                        : null;

                if (endTime != null && endTime.equals(LocalTime.MIDNIGHT)) {
                    endTime = LocalTime.of(23, 59);
                }

                List<LocalTime> events = new ArrayList<>();

                // Add only valid and upcoming times
                for (String key : List.of("time1", "time2", "time3", "time4")) {
                    if (schedule.has(key) && !schedule.getString(key).isEmpty()) {
                        LocalTime time = LocalTime.parse(schedule.getString(key), formatter);
                        if ((startTime == null || !time.isBefore(startTime)) &&
                                (endTime == null || time.isBefore(endTime)) &&
                                time.isAfter(now)) {
                            events.add(time);
                        }
                    }
                }

                boolean atMidnight = schedule.getBool("atMidnight");

                // Sort the events chronologically
                Collections.sort(events);

                // Add midnight (00:00) as the last item if requested
                if (atMidnight) {
                    events.add(LocalTime.MIDNIGHT);
                }

                // Display
                lblEventsTitle.setText("Upcoming Events (Stage " + stage + "):");

                JLabel[] labels = { lblEvent1, lblEvent2, lblEvent3, lblEvent4, lblEvent5 };
                for (int i = 0; i < labels.length; i++) {
                    if (i < events.size()) {
                        labels[i].setText(events.get(i).format(formatter));
                    } else {
                        labels[i].setText("");
                    }
                }

                if (events.size() > 0) {
                    LocalTime firstEvent = events.get(0).equals(LocalTime.MIDNIGHT)
                            ? LocalTime.of(23, 59).minusMinutes(timeBefore).plusMinutes(1)
                            : events.get(0).minusMinutes(timeBefore);
                    lblCountdown.setText("Next: " + firstEvent.format(formatter));
                }

                // Fallback if no events are set
                if (events.isEmpty()) {
                    lblEvent1.setText("None");
                }

                // Persist to JSON
                JSONManager settingsJson = new JSONManager(new File("settings.json"));
                settingsJson.appendFile("time1", schedule.optString("time1", null));
                settingsJson.appendFile("time2", schedule.optString("time2", null));
                settingsJson.appendFile("time3", schedule.optString("time3", null));
                settingsJson.appendFile("time4", schedule.optString("time4", null));
                settingsJson.appendFile("stage", stage);
                settingsJson.appendFile("startTime", startTime);
                settingsJson.appendFile("endTime", endTime);
                settingsJson.appendFile("atMidnight", atMidnight);
            } else {

                JSONManager settingsJson = new JSONManager(new File("settings.json"));
                settingsJson.appendFile("time1", null);
                settingsJson.appendFile("time2", null);
                settingsJson.appendFile("time3", null);
                settingsJson.appendFile("time4", null);
                settingsJson.appendFile("stage", 0);
                settingsJson.appendFile("startTime", null);
                settingsJson.appendFile("endTime", null);
                settingsJson.appendFile("atMidnight", false);

                lblEventsTitle.setMinimumSize(new Dimension(213, 21));
                lblEventsTitle.setText("No Loadshedding! :D");
                lblEvent1.setText(null);
                lblEvent2.setText(null);
                lblEvent3.setText(null);
                lblEvent4.setText(null);
                lblEvent5.setText(null);
            }

            timesUpdated = true;

        } else {
            int error = schedule.getInt("error");
            if (error == -1) {
                return;
            }
            dialogManager.showDialog("API Status", "API Status", error + " - " + HTTP.get(error),
                    CustomDialogManager.MSG_ERR, 6000);
        }
    }

    // Variables declaration - do not modify
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApplySettings;
    private static javax.swing.JButton btnAreaSelector;
    private javax.swing.JButton btnCancelSettings;
    private static javax.swing.JButton btnKillSwitch;
    private javax.swing.JButton btnProjectLink;
    private static javax.swing.JCheckBox chbRunOnStart;
    private static javax.swing.JCheckBox chbStartMin;
    private static javax.swing.JComboBox<String> cmbTimeSelector;
    private javax.swing.JDialog dlgAreaSearch;
    protected javax.swing.JDialog dlgGeneral;
    protected javax.swing.JDialog dlgInputDialog;
    private static javax.swing.JDialog dlgInsertToken;
    private static javax.swing.JDialog dlgSettingsMenu;
    private javax.swing.JLabel lblAboutHeader;
    private javax.swing.JLabel lblAreaSearchBarTitle;
    private static javax.swing.JLabel lblCountdown;
    protected javax.swing.JLabel lblDialogImg;
    protected javax.swing.JLabel lblDialogMsg;
    protected javax.swing.JLabel lblDialogTitle;
    private javax.swing.JLabel lblEnterTokenTitle;
    private static javax.swing.JLabel lblEvent1;
    private static javax.swing.JLabel lblEvent2;
    private static javax.swing.JLabel lblEvent3;
    private static javax.swing.JLabel lblEvent4;
    private static javax.swing.JLabel lblEvent5;
    private static javax.swing.JLabel lblEventsTitle;
    protected javax.swing.JLabel lblInputHeading;
    private javax.swing.JLabel lblSettingsHeader;
    private javax.swing.JLabel lblUpdated;
    private javax.swing.JLabel lblUpdatedDate;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JLabel lblVersionNo;
    private javax.swing.JMenuBar mnbOptionsBar;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenu mnuSettings;
    private javax.swing.JPanel pnlCenterButtons;
    private javax.swing.JPanel pnlEventsPanel;
    private javax.swing.JPanel pnlGeneralDlgText;
    private javax.swing.JTextField txfAreaSearchBar;
    private javax.swing.JTextField txfEnterTokenField;
    protected javax.swing.JTextField txfInputDialog;
    // End of variables declaration//GEN-END:variables
}