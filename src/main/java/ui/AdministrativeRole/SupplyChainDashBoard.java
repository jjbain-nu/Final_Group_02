/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.AdministrativeRole;

import ui.SupplierAdminRole.*;
import ui.DoctorRole.*;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.HospitalEnterprise;
import Business.Enterprise.ManufacturerEnterprise;
import Business.Enterprise.WholesalerEnterprise;
import Business.Hospital.Medicine;
import Business.Hospital.MedicineInventory;
import Business.Network.Network;
import Business.Organization.DoctorOrganization;
import Business.Organization.InventoryOrganization;
import Business.Organization.Organization;
import Business.Organization.PharmacyOrganization;
import Business.Organization.ProductionOrganization;
import Business.Organization.SupplierOrganization;
import Business.Production.ProductionOrder;
import Business.Supplier.MaterialInventory;
import Business.Supplier.MaterialRequest;
import Business.Supplier.PickingOrder;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.LabTestWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raunak
 */
public class SupplyChainDashBoard extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private Enterprise enterprise;
    private UserAccount userAccount;
    private EcoSystem ecosystem;
    /**
     * Creates new form DoctorWorkAreaJPanel
     */
    public SupplyChainDashBoard(JPanel userProcessContainer, UserAccount account,Enterprise enterprise, EcoSystem ecosystem) {
        initComponents();
        
        this.userProcessContainer = userProcessContainer;
        this.enterprise = enterprise;
        this.ecosystem = ecosystem;
        this.userAccount = account;
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        
        tblSCInventory.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tblSCInventory.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        
        enterpriseValueLabel1.setText(enterprise.getName());
        userValueLabel1.setText(account.getUsername());
        
        
        populateSCInventoryTable();
   
    }
    
    public void populateSCInventoryTable(){
        DefaultTableModel model = (DefaultTableModel) tblSCInventory.getModel();
        
        model.setRowCount(0);
        
        
        for(Medicine medicine : ecosystem.getMedicineCatalog().getMedicineList()){
            
            MedicineInventory wsmiA = null;
            MedicineInventory wsmiB = null;
            MedicineInventory wsmiC = null;
            MedicineInventory hospitalA = null;
            MedicineInventory hospitalB = null;
            MedicineInventory hospitalC = null;
            
            int totalInventory = 0;
            int totalShortage = 0;
            int totalSurplus = 0;
            int pendingProduction = 0;
            int readyToShip =0;
            
        
        for (Network network : ecosystem.getNetworkList()){
            
            for (Enterprise ent : network.getEnterpriseDirectory().getEnterpriseList()){
                
                //Wholesaler Inventory
                if(ent instanceof WholesalerEnterprise){
                    for(Organization org : ent.getOrganizationDirectory().getOrganizationList()){
                        if(org instanceof InventoryOrganization){
                            
                            InventoryOrganization inventoryOrg =(InventoryOrganization)org;
                            
                            MedicineInventory mi = inventoryOrg.getMedicineInventoryDirectory().findInventoryByMedicine(medicine);
                            
                            if(ent.getName().equals("Wholesaler A")){
                                wsmiA =mi;
                                
                            }else if(ent.getName().equals("Wholesaler B")){
                                wsmiB =mi;
                            
                            }else if(ent.getName().equals("Wholesaler C")){
                                wsmiC =mi;
                            
                            }
                        
                        }                    
                    }
                
                }
            
                //Hospital Inventory
                if(ent instanceof HospitalEnterprise){
                    for(Organization org : ent.getOrganizationDirectory().getOrganizationList()){
                        if(org instanceof PharmacyOrganization){
                            
                            PharmacyOrganization pharmacyOrg =(PharmacyOrganization)org;
                            
                            MedicineInventory mi = pharmacyOrg.getMedicineInventoryDirectory().findInventoryByMedicine(medicine);
                            
                            if(ent.getName().equals("Hospital A")){
                                hospitalA =mi;
                                
                            }else if(ent.getName().equals("Hospital B")){
                                hospitalB =mi;
                            
                            }else if(ent.getName().equals("Hospital C")){
                                hospitalC =mi;
                            
                            }
                        
                        }                    
                    }
                
                }
                //Manufacturer Production Orders
                if(ent instanceof ManufacturerEnterprise){
                    
                    for(Organization org : ent.getOrganizationDirectory().getOrganizationList()){
                        
                        if(org instanceof ProductionOrganization){
                            
                            for(WorkRequest wr : org.getWorkQueue().getWorkRequestList()){
                                
                                if(wr instanceof ProductionOrder){
                                    
                                    ProductionOrder po = (ProductionOrder)wr;
                                    
                                    if(po.getMedicine()==null|| !medicine.equals(po.getMedicine())){
                                        continue;
                                    }
                                    
                                    String status = po.getStatus();
                                    
                                    if("Pending".equals(status)||"Sent".equals(status)){
                                        
                                        pendingProduction += po.getQty();
                                        
                                    }else if("Ready to Ship".equals(status)){
                                        readyToShip += po.getQty();
                                    }
                               }
                            }
                        }
                    }
                }
            }
                
                if(wsmiA != null){
                    totalInventory += wsmiA.getQuantity();
                    totalSurplus += wsmiA.getSurplus();
                    totalShortage += wsmiA.getShortageQty();
                }
                
                if(wsmiB != null){
                    totalInventory += wsmiB.getQuantity();
                    totalSurplus += wsmiB.getSurplus();
                    totalShortage += wsmiB.getShortageQty();
                }
                
                if(wsmiC != null){
                    totalInventory += wsmiC.getQuantity();
                    totalSurplus += wsmiC.getSurplus();
                    totalShortage += wsmiC.getShortageQty();
                }
                
                if(hospitalA != null){
                    totalInventory += hospitalA.getQuantity();
                    totalSurplus += hospitalA.getSurplus();
                    totalShortage += hospitalA.getShortageQty();
                }
                
                if(hospitalB != null){
                    totalInventory += hospitalB.getQuantity();
                    totalSurplus += hospitalB.getSurplus();
                    totalShortage += hospitalB.getShortageQty();
                }
                
                if(hospitalC != null){
                    totalInventory += hospitalC.getQuantity();
                    totalSurplus += hospitalC.getSurplus();
                    totalShortage += hospitalC.getShortageQty();
                }
        
             
        Object[]row = new Object[7];
                
        row[0]=medicine;
        row[1]=medicine.getMedicineName();
        row[2]=totalInventory;
        row[3]=totalSurplus;
        row[4]=totalShortage;
        row[5]=pendingProduction;
        row[6]=readyToShip;
                
        model.addRow(row);
        
        }
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        enterpriseLabel = new javax.swing.JLabel();
        lblEnterprise1 = new javax.swing.JLabel();
        enterpriseValueLabel = new javax.swing.JLabel();
        lblOrganization = new javax.swing.JLabel();
        organizationValueLabel = new javax.swing.JLabel();
        lblUser1 = new javax.swing.JLabel();
        userValueLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSCInventory = new javax.swing.JTable();
        btnSelectMedicine = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSelectedMedicineDetail = new javax.swing.JTable();
        lblMaterialRequest = new javax.swing.JLabel();
        lblPickingOrder = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        enterpriseLabel1 = new javax.swing.JLabel();
        lblEnterprise = new javax.swing.JLabel();
        enterpriseValueLabel1 = new javax.swing.JLabel();
        lblUser2 = new javax.swing.JLabel();
        userValueLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 80));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 80));
        jPanel1.setPreferredSize(new java.awt.Dimension(1400, 80));

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 7, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 53, Short.MAX_VALUE)
        );

        enterpriseLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 22)); // NOI18N
        enterpriseLabel.setText("Supplier Admin - Work Area");

        lblEnterprise1.setForeground(new java.awt.Color(102, 102, 102));
        lblEnterprise1.setText("Enterprise : ");

        enterpriseValueLabel.setBackground(new java.awt.Color(255, 255, 255));
        enterpriseValueLabel.setForeground(new java.awt.Color(102, 102, 102));

        lblOrganization.setForeground(new java.awt.Color(102, 102, 102));
        lblOrganization.setText("Organization : ");

        organizationValueLabel.setBackground(new java.awt.Color(255, 255, 255));
        organizationValueLabel.setForeground(new java.awt.Color(102, 102, 102));

        lblUser1.setForeground(new java.awt.Color(102, 102, 102));
        lblUser1.setText("User : ");

        userValueLabel.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblEnterprise1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enterpriseValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblOrganization))
                    .addComponent(enterpriseLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(organizationValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(627, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(enterpriseLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(enterpriseValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEnterprise1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(organizationValueLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblOrganization, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblUser1)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(userValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        tblSCInventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Medicine ID", "Medicine", "Total Inventory", "Total Surplus", "Total Shortage", "Pending Production", "Ready To Ship"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblSCInventory);
        if (tblSCInventory.getColumnModel().getColumnCount() > 0) {
            tblSCInventory.getColumnModel().getColumn(0).setResizable(false);
            tblSCInventory.getColumnModel().getColumn(1).setResizable(false);
            tblSCInventory.getColumnModel().getColumn(2).setResizable(false);
            tblSCInventory.getColumnModel().getColumn(3).setResizable(false);
            tblSCInventory.getColumnModel().getColumn(4).setResizable(false);
            tblSCInventory.getColumnModel().getColumn(5).setResizable(false);
            tblSCInventory.getColumnModel().getColumn(6).setResizable(false);
        }

        btnSelectMedicine.setText("Select Medicine");
        btnSelectMedicine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectMedicineActionPerformed(evt);
            }
        });

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        tblSelectedMedicineDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Medicine ID", "Medicine", "Location", "Current Stock", "Standard Stock", "Difference", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblSelectedMedicineDetail);
        if (tblSelectedMedicineDetail.getColumnModel().getColumnCount() > 0) {
            tblSelectedMedicineDetail.getColumnModel().getColumn(0).setResizable(false);
            tblSelectedMedicineDetail.getColumnModel().getColumn(1).setResizable(false);
            tblSelectedMedicineDetail.getColumnModel().getColumn(2).setResizable(false);
            tblSelectedMedicineDetail.getColumnModel().getColumn(3).setResizable(false);
            tblSelectedMedicineDetail.getColumnModel().getColumn(4).setResizable(false);
            tblSelectedMedicineDetail.getColumnModel().getColumn(5).setResizable(false);
            tblSelectedMedicineDetail.getColumnModel().getColumn(6).setResizable(false);
        }

        lblMaterialRequest.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        lblMaterialRequest.setText("Supply Chain Inventory Summary");

        lblPickingOrder.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        lblPickingOrder.setText("Selected Medicine Detail");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 80));
        jPanel3.setMinimumSize(new java.awt.Dimension(0, 80));
        jPanel3.setPreferredSize(new java.awt.Dimension(1400, 80));

        jPanel4.setBackground(new java.awt.Color(0, 0, 153));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 7, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 53, Short.MAX_VALUE)
        );

        enterpriseLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 22)); // NOI18N
        enterpriseLabel1.setForeground(new java.awt.Color(0, 0, 153));
        enterpriseLabel1.setText("Supply Chain Dashboard");

        lblEnterprise.setForeground(new java.awt.Color(102, 102, 102));
        lblEnterprise.setText("Enterprise : ");

        enterpriseValueLabel1.setBackground(new java.awt.Color(255, 255, 255));
        enterpriseValueLabel1.setForeground(new java.awt.Color(102, 102, 102));

        lblUser2.setForeground(new java.awt.Color(102, 102, 102));
        lblUser2.setText("User : ");

        userValueLabel1.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(enterpriseLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblEnterprise)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enterpriseValueLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userValueLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(enterpriseLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(enterpriseValueLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEnterprise, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUser2)
                            .addComponent(userValueLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaterialRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1376, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnBack)
                                .addComponent(lblPickingOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSelectMedicine, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMaterialRequest)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSelectMedicine)
                .addGap(36, 36, 36)
                .addComponent(lblPickingOrder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBack)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectMedicineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectMedicineActionPerformed
        
       int selectedRow = tblSCInventory.getSelectedRow();
        
        if(selectedRow <0){
            JOptionPane.showMessageDialog(null,"Please select a request","Warning",
    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Medicine selectedMedicine = (Medicine)tblSCInventory.getValueAt(selectedRow, 0);
       
        populateSelectedMedicineTable(selectedMedicine);
        
    }//GEN-LAST:event_btnSelectMedicineActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
        
    }//GEN-LAST:event_btnBackActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSelectMedicine;
    private javax.swing.JLabel enterpriseLabel;
    private javax.swing.JLabel enterpriseLabel1;
    private javax.swing.JLabel enterpriseValueLabel;
    private javax.swing.JLabel enterpriseValueLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblEnterprise;
    private javax.swing.JLabel lblEnterprise1;
    private javax.swing.JLabel lblMaterialRequest;
    private javax.swing.JLabel lblOrganization;
    private javax.swing.JLabel lblPickingOrder;
    private javax.swing.JLabel lblUser1;
    private javax.swing.JLabel lblUser2;
    private javax.swing.JLabel organizationValueLabel;
    private javax.swing.JTable tblSCInventory;
    private javax.swing.JTable tblSelectedMedicineDetail;
    private javax.swing.JLabel userValueLabel;
    private javax.swing.JLabel userValueLabel1;
    // End of variables declaration//GEN-END:variables

    private void populateSelectedMedicineTable(Medicine medicine) {
       DefaultTableModel model = (DefaultTableModel) tblSelectedMedicineDetail.getModel();
       model.setRowCount(0);
        
    
       Map<String,MedicineInventory>inventoryMap = new LinkedHashMap<>();
       
       
       for (Network network : ecosystem.getNetworkList()){
            
            for (Enterprise ent : network.getEnterpriseDirectory().getEnterpriseList()){
                
                //Wholesaler Inventory
                if(ent instanceof WholesalerEnterprise){
                    for(Organization org : ent.getOrganizationDirectory().getOrganizationList()){
                        if(org instanceof InventoryOrganization){
                            
                            InventoryOrganization inventoryOrg =(InventoryOrganization)org;
                            
                            MedicineInventory mi = inventoryOrg.getMedicineInventoryDirectory().findInventoryByMedicine(medicine);
                            
                            if(mi!=null){
                                inventoryMap.put(ent.getName(),mi);
                            }
                        }                    
                    }
                }
            
                //Hospital Inventory
                if(ent instanceof HospitalEnterprise){
                    for(Organization org : ent.getOrganizationDirectory().getOrganizationList()){
                        if(org instanceof PharmacyOrganization){
                            
                            PharmacyOrganization pharmacyOrg =(PharmacyOrganization)org;
                            
                            MedicineInventory mi = pharmacyOrg.getMedicineInventoryDirectory().findInventoryByMedicine(medicine);
                            
                            if(mi!=null){
                                inventoryMap.put(ent.getName(),mi);
                            }
                            
                        }
                        
                    }                    
                }
            }
       }
    
        String[] locations ={
           "Wholesaler A",
           "Wholesaler B",
           "Wholesaler C",
           "Hospital A",
           "Hospital B",
           "Hospital C"
           
       };
               
        for(String location : locations){
            
            MedicineInventory mi = inventoryMap.get(location);
            
            if(mi == null){
                continue;
            }
            
            int difference = mi.getQuantity()-mi.getStandardStock();
            
            String status;
            
            if(difference < 0){
                status ="Shortage";
            }else if(difference >0 ){
                status ="Surplus";
            }else {
                status ="Balance";
            
            }
                
            Object[]row = new Object[7];
            
            row[0]= medicine;
            row[1]= medicine.getMedicineName();
            row[2]= location;
            row[3]= mi.getQuantity();
            row[4]= mi.getStandardStock();
            row[5]= difference;
            row[6]= status;
            
            model.addRow(row);
            }
        }
    }
