/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author keo
 */
public class Regular extends javax.swing.JFrame {
// ===================================================================================
private void loadRegularEmployeeDetails() {
    Connection connection = null;
    try {
        connection = DatabaseConnector.getConnection();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    if (connection == null) {
        // Handle connection failure
        return;
    }

    try {
        String query = "SELECT r.Employee_ID, e.Employee_Name, dtr.Time_In, dtr.Break_Out, dtr.Break_In, dtr.Time_Out, ot.Time_In AS Overtime_Time_In, ot.Time_Out AS Overtime_Time_Out "
                + "FROM regular r "
                + "JOIN employees e ON r.Employee_ID = e.Employee_ID "
                + "JOIN dtr ON r.Employee_ID = dtr.Employee_ID "
                + "JOIN attendance a ON dtr.Attendance_ID = a.Attendance_ID "
                + "LEFT JOIN overtime ot ON dtr.Attendance_ID = ot.Attendance_ID";

        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int employeeId = resultSet.getInt("Employee_ID");
            String employeeName = resultSet.getString("Employee_Name");
            String timeIn = resultSet.getString("Time_In");
            String breakOut = resultSet.getString("Break_Out");
            String breakIn = resultSet.getString("Break_In");
            String timeOut = resultSet.getString("Time_Out");
            String overtimeTimeIn = resultSet.getString("Overtime_Time_In");
            String overtimeTimeOut = resultSet.getString("Overtime_Time_Out");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.addRow(new Object[]{employeeId, employeeName, timeIn, breakOut, breakIn, timeOut, overtimeTimeIn, overtimeTimeOut});
        }

        resultSet.close();
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    //=================================================================================
    /**
     * Creates new form Regular
     */
    public Regular() {
        initComponents();
        loadRegularEmployeeDetails();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Button_back = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        EmployeeID = new javax.swing.JLabel();
        EmployeeID1 = new javax.swing.JLabel();
        EmployeeName = new javax.swing.JLabel();
        EmployeeID3 = new javax.swing.JLabel();
        EmployeeID4 = new javax.swing.JLabel();
        EmployeeID5 = new javax.swing.JLabel();
        TField_EmployeeID = new javax.swing.JTextField();
        Dropdown_EName = new javax.swing.JComboBox<>();
        TField_breakout = new javax.swing.JTextField();
        TField_breakin = new javax.swing.JTextField();
        TField_timeout = new javax.swing.JTextField();
        TField_timein = new javax.swing.JTextField();
        Button_cancel = new javax.swing.JButton();
        Button_update = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Button_delete = new javax.swing.JButton();
        EmployeeID2 = new javax.swing.JLabel();
        EmployeeID6 = new javax.swing.JLabel();
        TField_timeout1 = new javax.swing.JTextField();
        TField_breakin1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Button_back.setText("<Back");
        Button_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_backActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        EmployeeID.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        EmployeeID.setText("Employee ID:");

        EmployeeID1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        EmployeeID1.setText("Time Out");

        EmployeeName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        EmployeeName.setText("Employee Name:");

        EmployeeID3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        EmployeeID3.setText("Break In");

        EmployeeID4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        EmployeeID4.setText("Time In");

        EmployeeID5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        EmployeeID5.setText("Break Out");

        Dropdown_EName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Button_cancel.setText("Cancel");

        Button_update.setText("Update");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Employee Name", "Time In", "Break Out", "Break In", "Time Out", "Overtime Time In", "Overtime Time Out"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        Button_delete.setBackground(new java.awt.Color(51, 0, 0));
        Button_delete.setText("Delete");
        Button_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteActionPerformed(evt);
            }
        });

        EmployeeID2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        EmployeeID2.setText("Overtime Time In");

        EmployeeID6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        EmployeeID6.setText("Overtime Time Out");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(EmployeeID6)
                            .addComponent(EmployeeID1)
                            .addComponent(EmployeeID3)
                            .addComponent(EmployeeID5)
                            .addComponent(EmployeeID)
                            .addComponent(EmployeeName)
                            .addComponent(EmployeeID4)
                            .addComponent(TField_breakin)
                            .addComponent(TField_breakout)
                            .addComponent(TField_timein)
                            .addComponent(Dropdown_EName, 0, 294, Short.MAX_VALUE)
                            .addComponent(TField_EmployeeID)
                            .addComponent(TField_timeout)
                            .addComponent(TField_timeout1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(EmployeeID2)
                            .addComponent(TField_breakin1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(Button_update)
                        .addGap(35, 35, 35)
                        .addComponent(Button_cancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Button_delete)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EmployeeID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TField_EmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EmployeeName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Dropdown_EName, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EmployeeID4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TField_timein, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EmployeeID5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TField_breakout, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EmployeeID3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TField_breakin, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EmployeeID1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TField_timeout, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EmployeeID2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TField_timeout1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EmployeeID6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TField_breakin1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Button_cancel)
                            .addComponent(Button_update)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(Button_delete)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Button_back)
                .addContainerGap(1617, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Button_back)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Button_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_backActionPerformed
        InstructionsKt.redirectToDashboard(this);
    }//GEN-LAST:event_Button_backActionPerformed

    private void Button_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Button_deleteActionPerformed

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
            java.util.logging.Logger.getLogger(Regular.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Regular.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Regular.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Regular.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Regular().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_back;
    private javax.swing.JButton Button_cancel;
    private javax.swing.JButton Button_delete;
    private javax.swing.JButton Button_update;
    private javax.swing.JComboBox<String> Dropdown_EName;
    private javax.swing.JLabel EmployeeID;
    private javax.swing.JLabel EmployeeID1;
    private javax.swing.JLabel EmployeeID2;
    private javax.swing.JLabel EmployeeID3;
    private javax.swing.JLabel EmployeeID4;
    private javax.swing.JLabel EmployeeID5;
    private javax.swing.JLabel EmployeeID6;
    private javax.swing.JLabel EmployeeName;
    private javax.swing.JTextField TField_EmployeeID;
    private javax.swing.JTextField TField_breakin;
    private javax.swing.JTextField TField_breakin1;
    private javax.swing.JTextField TField_breakout;
    private javax.swing.JTextField TField_timein;
    private javax.swing.JTextField TField_timeout;
    private javax.swing.JTextField TField_timeout1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}