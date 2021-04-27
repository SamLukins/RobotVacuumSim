package robotvacuum.gui;

/**
 *
 * @author Austen
 */
public class CreateNewVacuum extends javax.swing.JDialog {
    
    public double x, y;
    public int batteryLife, vacuumEfficiency, whiskerEfficiency, vacuumSpeed, algorithmCode;
    public boolean create;
    
    /**
     * Creates new form CreateNewHouse
     */
    public CreateNewVacuum(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        pathingAlgorithmList.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pathingAlgorithmList = new javax.swing.JList<>();
        cancelButton = new javax.swing.JButton();
        createButton = new javax.swing.JButton();
        errorText = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        batteryLifeSlider = new javax.swing.JSlider();
        vacuumSpeedSlider = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        vacuumEfficiencySlider = new javax.swing.JSlider();
        whiskerEfficiencySlider = new javax.swing.JSlider();
        jLabel6 = new javax.swing.JLabel();
        xField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        yField = new javax.swing.JTextField();
        quickTestCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create New Vacuum");

        jLabel1.setText("Battery Life (min):");

        jLabel2.setText("Vacuum Efficiency (%):");

        jLabel3.setText("Pathing Algorithm:");

        pathingAlgorithmList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Random", "Spiral", "Snake", "Wall Follow" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        pathingAlgorithmList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pathingAlgorithmList.setToolTipText("");
        jScrollPane1.setViewportView(pathingAlgorithmList);

        cancelButton.setText("Cancel");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        createButton.setText("Create");
        createButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createButtonMouseClicked(evt);
            }
        });

        jLabel4.setText("Vacuum Speed (cm/s):");

        batteryLifeSlider.setMajorTickSpacing(10);
        batteryLifeSlider.setMaximum(200);
        batteryLifeSlider.setMinimum(90);
        batteryLifeSlider.setMinorTickSpacing(10);
        batteryLifeSlider.setPaintLabels(true);
        batteryLifeSlider.setPaintTicks(true);
        batteryLifeSlider.setToolTipText("");
        batteryLifeSlider.setValue(150);

        vacuumSpeedSlider.setMajorTickSpacing(5);
        vacuumSpeedSlider.setMaximum(45);
        vacuumSpeedSlider.setMinimum(15);
        vacuumSpeedSlider.setMinorTickSpacing(5);
        vacuumSpeedSlider.setPaintLabels(true);
        vacuumSpeedSlider.setPaintTicks(true);
        vacuumSpeedSlider.setValue(30);

        jLabel5.setText("Whisker Efficiency (%):");

        vacuumEfficiencySlider.setMajorTickSpacing(10);
        vacuumEfficiencySlider.setMaximum(90);
        vacuumEfficiencySlider.setMinimum(10);
        vacuumEfficiencySlider.setMinorTickSpacing(10);
        vacuumEfficiencySlider.setPaintLabels(true);
        vacuumEfficiencySlider.setPaintTicks(true);
        vacuumEfficiencySlider.setValue(90);

        whiskerEfficiencySlider.setMajorTickSpacing(5);
        whiskerEfficiencySlider.setMaximum(50);
        whiskerEfficiencySlider.setMinimum(10);
        whiskerEfficiencySlider.setMinorTickSpacing(5);
        whiskerEfficiencySlider.setPaintLabels(true);
        whiskerEfficiencySlider.setPaintTicks(true);
        whiskerEfficiencySlider.setValue(30);

        jLabel6.setText("Vacuum X:");

        jLabel7.setText("Vacuum Y:");

        quickTestCheckBox.setText("Quick Test (much lower battery life)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(errorText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(quickTestCheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(batteryLifeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 10, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(xField)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(vacuumSpeedSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(vacuumEfficiencySlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(whiskerEfficiencySlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(116, 116, 116)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(yField, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(xField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(yField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(batteryLifeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vacuumEfficiencySlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(whiskerEfficiencySlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vacuumSpeedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(16, 16, 16)
                        .addComponent(quickTestCheckBox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createButton)
                    .addComponent(cancelButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(errorText, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createButtonMouseClicked
        try {
            x = Double.parseDouble(xField.getText());
            y = Double.parseDouble(yField.getText());
            batteryLife = batteryLifeSlider.getValue();
            if (quickTestCheckBox.isSelected()) {
                batteryLife = 20;
            }
            vacuumEfficiency = vacuumEfficiencySlider.getValue();
            whiskerEfficiency = whiskerEfficiencySlider.getValue();
            vacuumSpeed = vacuumSpeedSlider.getValue();
            String tempP = pathingAlgorithmList.getSelectedValue();
            switch (tempP) {
                    case "Random": algorithmCode = 1; break;
                    case "Spiral": algorithmCode = 2; break;
                    case "Snake": algorithmCode = 3; break;
                    case "WallFollow": algorithmCode = 4; break;
                    default: algorithmCode = 1; break;
                }
            create = true;
            setVisible(false);
            dispose();
        } catch (NumberFormatException e) {
            errorText.setText("Parameters must be numbers");
        }
    }//GEN-LAST:event_createButtonMouseClicked

    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        create = false;
        setVisible(false);
        dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked
    
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
            java.util.logging.Logger.getLogger(CreateNewVacuum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateNewVacuum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateNewVacuum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateNewVacuum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CreateNewVacuum dialog = new CreateNewVacuum(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSlider batteryLifeSlider;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton createButton;
    private javax.swing.JLabel errorText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> pathingAlgorithmList;
    private javax.swing.JCheckBox quickTestCheckBox;
    private javax.swing.JSlider vacuumEfficiencySlider;
    private javax.swing.JSlider vacuumSpeedSlider;
    private javax.swing.JSlider whiskerEfficiencySlider;
    private javax.swing.JTextField xField;
    private javax.swing.JTextField yField;
    // End of variables declaration//GEN-END:variables
}
