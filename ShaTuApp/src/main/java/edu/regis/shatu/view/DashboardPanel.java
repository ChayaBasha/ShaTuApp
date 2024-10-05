/*
 * SHATU: SHA-256 Tutor
 * 
 *  (C) Johanna & Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibited.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.shatu.view;

import edu.regis.shatu.model.TutoringSession;
/**
 * The dashboard screen to be displayed upon user sign in.
 * Enables user to select a mode from the tutor (teach me, practice, quiz me)
 * Tracks user's progress for each mode.
 * @author Ryley M
 */
public class DashboardPanel extends javax.swing.JPanel {
    private TutoringSession tutoringSession; // Reference to current tutoringSession

    public DashboardPanel(TutoringSession tutoringSession) {
        this.tutoringSession = tutoringSession;

        if (tutoringSession == null) {
            System.err.println("TutoringSession is null in DashboardPanel constructor");
        } else if (tutoringSession.getAccount() == null) {
            System.err.println("Account is null in DashboardPanel");
        } else {
            System.out.println("DashboardPanel initialized for user: " + tutoringSession.getAccount().getFirstName());
        }

        initComponents();  // Initialize UI components
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

        headerPanel = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        logOutButton = new javax.swing.JButton();
        backToDashboardButton = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();
        teachMeButton1 = new javax.swing.JButton();
        practiceButton1 = new javax.swing.JButton();
        quizeMeButton1 = new javax.swing.JButton();
        teachMeProgressBar1 = new javax.swing.JProgressBar();
        practiceProgressBar1 = new javax.swing.JProgressBar();
        quizMeProgressBar1 = new javax.swing.JProgressBar();

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLayout(new java.awt.BorderLayout());

        headerPanel.setLayout(new java.awt.BorderLayout());

        welcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeLabel.setText("Welcome!");
        welcomeLabel.setToolTipText("");
        welcomeLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        welcomeLabel.setText("Welcome, " + tutoringSession.getAccount().getFirstName() + "!");
        headerPanel.add(welcomeLabel, java.awt.BorderLayout.CENTER);

        logOutButton.setText("Log Out");
        logOutButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        logOutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutButtonMouseClicked(evt);
            }
        });
        headerPanel.add(logOutButton, java.awt.BorderLayout.LINE_END);

        backToDashboardButton.setText("Back To Dashboard");
        backToDashboardButton.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        headerPanel.add(backToDashboardButton, java.awt.BorderLayout.LINE_START);

        add(headerPanel, java.awt.BorderLayout.NORTH);

        contentPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        contentPanel.setLayout(new java.awt.GridBagLayout());

        teachMeButton1.setText("Teach Me");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 6, 0);
        contentPanel.add(teachMeButton1, gridBagConstraints);

        practiceButton1.setText("Practice");
        practiceButton1.setActionCommand("practice");
        practiceButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                practiceButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(18, 18, 6, 0);
        contentPanel.add(practiceButton1, gridBagConstraints);

        quizeMeButton1.setText("Quiz Me");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(18, 18, 6, 0);
        contentPanel.add(quizeMeButton1, gridBagConstraints);

        teachMeProgressBar1.setString("100%");
        teachMeProgressBar1.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 121;
        gridBagConstraints.ipady = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(61, 6, 0, 0);
        contentPanel.add(teachMeProgressBar1, gridBagConstraints);

        practiceProgressBar1.setString("50%");
        practiceProgressBar1.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 121;
        gridBagConstraints.ipady = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(61, 18, 0, 0);
        contentPanel.add(practiceProgressBar1, gridBagConstraints);

        quizMeProgressBar1.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 121;
        gridBagConstraints.ipady = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(61, 18, 0, 6);
        contentPanel.add(quizMeProgressBar1, gridBagConstraints);

        add(contentPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void practiceButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_practiceButton1ActionPerformed
     SplashFrame.instance().selectPracticeScreen();
    }//GEN-LAST:event_practiceButton1ActionPerformed

    private void logOutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutButtonMouseClicked
     SplashFrame.instance().selectSplash();
    }//GEN-LAST:event_logOutButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backToDashboardButton;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JButton logOutButton;
    private javax.swing.JButton practiceButton1;
    private javax.swing.JProgressBar practiceProgressBar1;
    private javax.swing.JProgressBar quizMeProgressBar1;
    private javax.swing.JButton quizeMeButton1;
    private javax.swing.JButton teachMeButton1;
    private javax.swing.JProgressBar teachMeProgressBar1;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
