/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
//Tes
/**
 *
 * @author keo
 */
public class AdminSignIn extends javax.swing.JFrame {

    /**
     * Creates new form AdminSignIn
     */
    public AdminSignIn() {
        initComponents();
        populateAdminComboBox(); // populate the ComboBox when the form is created
    }
    private void populateAdminComboBox() {
        // Remove default items
        DropDown_ChoosingAdmin.removeAllItems();

        // Connect to the database and populate the ComboBox
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Admin_Name FROM Admin")) {

            while (rs.next()) {
                DropDown_ChoosingAdmin.addItem(rs.getString("Admin_Name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error populating admin list: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Label_ASI = new java.awt.Label();
        DropDown_ChoosingAdmin = new javax.swing.JComboBox<>();
        Button_LogIn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Label_ASI.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        Label_ASI.setText("Admin Sign In");

        DropDown_ChoosingAdmin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        DropDown_ChoosingAdmin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        DropDown_ChoosingAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DropDown_ChoosingAdminActionPerformed(evt);
            }
        });

        Button_LogIn.setText("Log In");
        Button_LogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_LogInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(67, 67, 67)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(Label_ASI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(DropDown_ChoosingAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(99, 99, 99)
                                                .addComponent(Button_LogIn)))
                                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(Label_ASI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(DropDown_ChoosingAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(Button_LogIn)
                                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DropDown_ChoosingAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DropDown_ChoosingAdminActionPerformed
        // Clear existing items in the JComboBox


        // Call the populateAdminNames method from instructions.kt to populate the JComboBox with admin names

    }//GEN-LAST:event_DropDown_ChoosingAdminActionPerformed

    private void Button_LogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_LogInActionPerformed
        String selectedAdminName = (String) DropDown_ChoosingAdmin.getSelectedItem();
        if (selectedAdminName == null || selectedAdminName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an admin.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT Admin_ID FROM Admin WHERE Admin_Name = ?")) {

            stmt.setString(1, selectedAdminName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int adminId = rs.getInt("Admin_ID");
                recordAttendance(adminId);
                // Redirect to the dashboard after recording attendance
                redirectToDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Admin not found.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Login error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void redirectToDashboard() {
        InstructionsKt.redirectToDashboard(this);
    }

    private void recordAttendance(int adminId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Attendance (Date, Admin_ID) VALUES (?, ?)")) {

            stmt.setString(1, currentDate);
            stmt.setInt(2, adminId);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Attendance recorded.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to record attendance.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error recording attendance: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminSignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminSignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminSignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminSignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminSignIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_LogIn;
    private javax.swing.JComboBox<String> DropDown_ChoosingAdmin;
    private java.awt.Label Label_ASI;
    // End of variables declaration//GEN-END:variables
}
