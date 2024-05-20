package za.co.vortexit.pkg3.v0.autoshutdown;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author Connor Lewis
 */
public class AutoShutdownGUI extends javax.swing.JFrame {

    private final javax.swing.ImageIcon scaledOnButtonPic;
    private final javax.swing.ImageIcon scaledOnButtonPressedPic;

    private final javax.swing.ImageIcon scaledOffButtonPic;
    private final javax.swing.ImageIcon scaledOffButtonPressedPic;

    private static javax.swing.ImageIcon scaledWarnPic;
    private static javax.swing.ImageIcon scaledErrorPic;
    private static javax.swing.ImageIcon scaledInfoPic;

    private final Color BACKGROUND = new Color(30, 30, 30);
    private final Color FONT = new Color(73, 79, 91);

    private static final String NORMAL = "NORMAL";
    private static final String ERROR = "ERROR";
    private static final String WARNING = "WARNING";

    /**
     * Creates new form AutoShutdownGUI
     */
    public AutoShutdownGUI() {
        initComponents();

        setIconImage(new javax.swing.ImageIcon("src/main/resources/icon.png").getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH));
        getContentPane().setBackground(BACKGROUND);

        javax.swing.ImageIcon onButtonPic = new javax.swing.ImageIcon("src/main/resources/on.png");
        scaledOnButtonPic = new javax.swing.ImageIcon(onButtonPic.getImage().getScaledInstance(btnKillSwitch.getPreferredSize().width, btnKillSwitch.getPreferredSize().height, Image.SCALE_SMOOTH));

        javax.swing.ImageIcon offButtonPic = new javax.swing.ImageIcon("src/main/resources/off.png");
        scaledOffButtonPic = new javax.swing.ImageIcon(offButtonPic.getImage().getScaledInstance(btnKillSwitch.getPreferredSize().width, btnKillSwitch.getPreferredSize().height, Image.SCALE_SMOOTH));

        javax.swing.ImageIcon onButtonPressedPic = new javax.swing.ImageIcon("src/main/resources/on_pressed.png");
        scaledOnButtonPressedPic = new javax.swing.ImageIcon(onButtonPressedPic.getImage().getScaledInstance(btnKillSwitch.getPreferredSize().width, btnKillSwitch.getPreferredSize().height, Image.SCALE_SMOOTH));

        javax.swing.ImageIcon offButtonPressedPic = new javax.swing.ImageIcon("src/main/resources/off_pressed.png");
        scaledOffButtonPressedPic = new javax.swing.ImageIcon(offButtonPressedPic.getImage().getScaledInstance(btnKillSwitch.getPreferredSize().width, btnKillSwitch.getPreferredSize().height, Image.SCALE_SMOOTH));

        javax.swing.ImageIcon warn = new javax.swing.ImageIcon("src/main/resources/warn.png");
        scaledWarnPic = new javax.swing.ImageIcon(warn.getImage().getScaledInstance(lblDialogImg.getPreferredSize().width, lblDialogImg.getPreferredSize().height, Image.SCALE_SMOOTH));

        javax.swing.ImageIcon err = new javax.swing.ImageIcon("src/main/resources/error.png");
        scaledErrorPic = new javax.swing.ImageIcon(err.getImage().getScaledInstance(lblDialogImg.getPreferredSize().width, lblDialogImg.getPreferredSize().height, Image.SCALE_SMOOTH));

        javax.swing.ImageIcon info = new javax.swing.ImageIcon("src/main/resources/info.png");
        scaledInfoPic = new javax.swing.ImageIcon(info.getImage().getScaledInstance(lblDialogImg.getPreferredSize().width, lblDialogImg.getPreferredSize().height, Image.SCALE_SMOOTH));

        btnKillSwitch.setIcon(scaledOnButtonPic);
        btnKillSwitch.setSelectedIcon(scaledOffButtonPic);

        cboTimeSelector.getEditor().getEditorComponent().setBackground(BACKGROUND);
        cboTimeSelector.getEditor().getEditorComponent().setForeground(FONT);

        java.awt.Point p = getLocation();
        p.x += getWidth() / 2 - dlgInsertToken.getWidth() / 2;
        p.y += getHeight() / 2 - dlgInsertToken.getHeight() / 2;
        dlgInsertToken.setLocation(p);
        dlgGeneral.setLocation(p);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
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
        jPanel1 = new javax.swing.JPanel();
        lblDialogTitle = new javax.swing.JLabel();
        lblDialogMsg = new javax.swing.JLabel();
        lblDialogImg = new javax.swing.JLabel();
        cboTimeSelector = new javax.swing.JComboBox<>();
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

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
        dlgInsertToken.setModal(true);
        dlgInsertToken.setResizable(false);
        dlgInsertToken.setType(java.awt.Window.Type.POPUP);
        dlgInsertToken.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                dlgInsertTokenComponentHidden(evt);
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
        dlgGeneral.setPreferredSize(new java.awt.Dimension(200, 100));
        dlgGeneral.setResizable(false);
        dlgGeneral.setType(java.awt.Window.Type.POPUP);
        dlgGeneral.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                dlgGeneralComponentHidden(evt);
            }
        });
        dlgGeneral.getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblDialogTitle.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblDialogTitle.setText("Insert ESP token");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(lblDialogTitle, gridBagConstraints);

        lblDialogMsg.setText("woobus loobus");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(lblDialogMsg, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 16, 0);
        dlgGeneral.getContentPane().add(jPanel1, gridBagConstraints);

        lblDialogImg.setOpaque(true);
        lblDialogImg.setPreferredSize(new java.awt.Dimension(50, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 12, 0);
        dlgGeneral.getContentPane().add(lblDialogImg, gridBagConstraints);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Auto Shutdown");
        setBackground(new java.awt.Color(30, 30, 30));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(51, 51, 51));
        setLocation(getLocation());
        setLocationByPlatform(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        cboTimeSelector.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        cboTimeSelector.setForeground(new java.awt.Color(153, 153, 153));
        cboTimeSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 Minute", "2 Minutes", "3 Minutes", "4 Minutes" }));
        cboTimeSelector.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        cboTimeSelector.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(128, 2, 10, 12);
        getContentPane().add(cboTimeSelector, gridBagConstraints);

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
        lblEvent1.setText("16:00");
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
        lblEvent3.setText("16:00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 179);
        pnlEventsPanel.add(lblEvent3, gridBagConstraints);

        lblEvent4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEvent4.setForeground(FONT);
        lblEvent4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEvent4.setText("16:00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 179);
        pnlEventsPanel.add(lblEvent4, gridBagConstraints);

        lblEvent5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEvent5.setForeground(FONT);
        lblEvent5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEvent5.setText("16:00");
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
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 39, 8);
        getContentPane().add(pnlEventsPanel, gridBagConstraints);

        pnlCenterButtons.setBackground(BACKGROUND);
        pnlCenterButtons.setLayout(new java.awt.GridBagLayout());

        btnKillSwitch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/on.png"))); // NOI18N
        btnKillSwitch.setToolTipText("Toggle shutdown feature");
        btnKillSwitch.setBorder(null);
        btnKillSwitch.setBorderPainted(false);
        btnKillSwitch.setContentAreaFilled(false);
        btnKillSwitch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKillSwitch.setFocusPainted(false);
        btnKillSwitch.setMaximumSize(new java.awt.Dimension(180, 180));
        btnKillSwitch.setMinimumSize(new java.awt.Dimension(180, 180));
        btnKillSwitch.setPreferredSize(new java.awt.Dimension(110, 110));
        btnKillSwitch.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/off.png"))); // NOI18N
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
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 137, 0);
        pnlCenterButtons.add(btnKillSwitch, gridBagConstraints);

        btnAreaSelector.setBackground(BACKGROUND);
        btnAreaSelector.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAreaSelector.setForeground(FONT);
        btnAreaSelector.setBorder(null);
        btnAreaSelector.setBorderPainted(false);
        btnAreaSelector.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAreaSelector.setFocusPainted(false);
        btnAreaSelector.setLabel("Area: ");
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
        gridBagConstraints.insets = new java.awt.Insets(232, 63, 231, 55);
        pnlCenterButtons.add(btnAreaSelector, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 141, 78, 47);
        getContentPane().add(pnlCenterButtons, gridBagConstraints);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(816, 508));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnKillSwitchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKillSwitchActionPerformed
        System.out.println(btnKillSwitch.isSelected());
        btnKillSwitch.setSelected(!btnKillSwitch.isSelected());
    }//GEN-LAST:event_btnKillSwitchActionPerformed

    private void btnKillSwitchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKillSwitchMouseReleased

        btnKillSwitch.setIcon(scaledOnButtonPic);
        btnKillSwitch.setSelectedIcon(scaledOffButtonPic);
    }//GEN-LAST:event_btnKillSwitchMouseReleased

    private void btnKillSwitchMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKillSwitchMousePressed

        btnKillSwitch.setSelectedIcon(scaledOffButtonPressedPic);
        btnKillSwitch.setIcon(scaledOnButtonPressedPic);
    }//GEN-LAST:event_btnKillSwitchMousePressed

    private void txfAreaSearchBarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfAreaSearchBarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String input = txfAreaSearchBar.getText();

            System.out.println(input);

            dlgAreaSearch.setVisible(false);
            txfAreaSearchBar.setText("");
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dlgAreaSearch.setVisible(false);
            txfAreaSearchBar.setText("");
        }

    }//GEN-LAST:event_txfAreaSearchBarKeyPressed

    private void btnAreaSelectorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAreaSelectorMouseClicked
        java.awt.Point p = getLocation();
        p.x += getWidth() / 2 - dlgAreaSearch.getWidth() / 2;
        p.y += getHeight() / 2 - dlgAreaSearch.getHeight() / 2;
        dlgAreaSearch.setLocation(p);
        dlgAreaSearch.setEnabled(true);
        dlgAreaSearch.setVisible(true);
        btnAreaSelector.setText("loading...");
    }//GEN-LAST:event_btnAreaSelectorMouseClicked

    private void dlgAreaSearchComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_dlgAreaSearchComponentHidden
        txfAreaSearchBar.setText("");
    }//GEN-LAST:event_dlgAreaSearchComponentHidden

    private void txfEnterTokenFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfEnterTokenFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String input = txfEnterTokenField.getText();

            JSONManager json = new JSONManager(new File("settings.json"));
            json.appendFile("token", input);

            dlgInsertToken.setVisible(false);
            txfEnterTokenField.setText("");
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dlgInsertToken.setVisible(false);
            txfEnterTokenField.setText("");
        }
    }//GEN-LAST:event_txfEnterTokenFieldKeyPressed

    private void dlgInsertTokenComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_dlgInsertTokenComponentHidden
        txfEnterTokenField.setText("");
    }//GEN-LAST:event_dlgInsertTokenComponentHidden

    private void dlgGeneralComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_dlgGeneralComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_dlgGeneralComponentHidden

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            Logger logger = new Logger();
            logger.log("Closing");
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(AutoShutdownGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws InterruptedException, IOException {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }

            javax.swing.UIManager.setLookAndFeel(new FlatDarkLaf());

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutoShutdownGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AutoShutdownGUI().setVisible(true);
        });

        Thread.sleep(250);

        APIManager api = new APIManager();
        JSONManager jsonSettings = new JSONManager(new File("settings.json"));
        Logger logger = new Logger();
        logger.log("Starting");

        String token = api.getToken();

        
            if (token == null || token.isEmpty() || token.isBlank() || api.getStatus() == 403) {
                dlgInsertToken.setVisible(true);
            }
        
        int apiStatus = api.getStatus();
        
        logger.log("Checking API Status  (" + apiStatus + " - " + HTTP.get(apiStatus) + ")");

    }

    private static void showDialog(String title, String msgTitle, String message, String type) {
        dlgGeneral.setTitle(title);
        lblDialogTitle.setText(msgTitle);
        lblDialogMsg.setText(message);

        switch (type) {
            case ERROR:
                lblDialogImg.setIcon(scaledErrorPic);
                break;
            case NORMAL:
                lblDialogImg.setIcon(scaledInfoPic);
                break;
            case WARNING:
                lblDialogImg.setIcon(scaledWarnPic);
                break;
            default:
                lblDialogImg.setIcon(scaledInfoPic);
                break;
        }

        dlgGeneral.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAreaSelector;
    private javax.swing.JButton btnKillSwitch;
    private javax.swing.JComboBox<String> cboTimeSelector;
    private javax.swing.JDialog dlgAreaSearch;
    private static javax.swing.JDialog dlgGeneral;
    private static javax.swing.JDialog dlgInsertToken;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAreaSearchBarTitle;
    private static javax.swing.JLabel lblDialogImg;
    private static javax.swing.JLabel lblDialogMsg;
    private static javax.swing.JLabel lblDialogTitle;
    private javax.swing.JLabel lblEnterTokenTitle;
    private javax.swing.JLabel lblEvent1;
    private javax.swing.JLabel lblEvent2;
    private javax.swing.JLabel lblEvent3;
    private javax.swing.JLabel lblEvent4;
    private javax.swing.JLabel lblEvent5;
    private javax.swing.JLabel lblEventsTitle;
    private javax.swing.JPanel pnlCenterButtons;
    private javax.swing.JPanel pnlEventsPanel;
    private javax.swing.JTextField txfAreaSearchBar;
    private javax.swing.JTextField txfEnterTokenField;
    // End of variables declaration//GEN-END:variables
}
