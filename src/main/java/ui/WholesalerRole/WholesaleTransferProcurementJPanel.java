package ui.WholesalerRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.WholesalerEnterprise;
import Business.Hospital.ProcurementRequest;
import Business.Network.Network;
import Business.Organization.InventoryOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.WorkRequest;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/** Redirects an incoming hospital ProcurementRequest to another wholesaler. */
public class WholesaleTransferProcurementJPanel extends JPanel {
    private final JPanel container;
    private final InventoryOrganization inventoryOrganization;
    private final Enterprise enterprise;
    private final JComboBox<WholesalerEnterprise> wholesalerCombo = new JComboBox<>();
    private final JTable table;

    public WholesaleTransferProcurementJPanel(JPanel container, UserAccount account,
            InventoryOrganization inventoryOrganization, Enterprise enterprise, EcoSystem system) {
        this.container = container;
        this.inventoryOrganization = inventoryOrganization;
        this.enterprise = enterprise;
        setLayout(new BorderLayout(8, 8));
        add(new JLabel("Transfer Incoming Hospital Procurement Request"), BorderLayout.NORTH);
        table = new JTable(new DefaultTableModel(new Object[]{"Request", "Medicine", "Quantity", "Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(new JLabel("Redirect to wholesaler:")); controls.add(wholesalerCombo);
        JButton transfer = new JButton("Transfer Procurement Request"); transfer.addActionListener(event -> transfer());
        JButton refresh = new JButton("Refresh"); refresh.addActionListener(event -> refresh());
        JButton back = new JButton("<< Back"); back.addActionListener(event -> back());
        controls.add(transfer); controls.add(refresh); controls.add(back); add(controls, BorderLayout.SOUTH);
        populateWholesalers(system); refresh();
    }

    private void populateWholesalers(EcoSystem system) {
        for (Network network : system.getNetworkList()) {
            if (!network.getEnterpriseDirectory().getEnterpriseList().contains(enterprise)) continue;
            for (Enterprise candidate : network.getEnterpriseDirectory().getEnterpriseList()) {
                if (candidate instanceof WholesalerEnterprise && candidate != enterprise) wholesalerCombo.addItem((WholesalerEnterprise) candidate);
            }
            return;
        }
    }

    private void refresh() {
        DefaultTableModel model = (DefaultTableModel) table.getModel(); model.setRowCount(0);
        for (WorkRequest request : inventoryOrganization.getWorkQueue().getWorkRequestList()) {
            if (request instanceof ProcurementRequest procurement && "Procured".equals(procurement.getStatus())) {
                model.addRow(new Object[]{procurement, procurement.getMedicine().getMedicineName(), procurement.getRequestQty(), procurement.getStatus()});
            }
        }
    }

    private void transfer() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select an incoming hospital procurement request first.", "No request selected", JOptionPane.WARNING_MESSAGE); return; }
        WholesalerEnterprise target = (WholesalerEnterprise) wholesalerCombo.getSelectedItem();
        if (target == null) { JOptionPane.showMessageDialog(this, "Select another wholesaler.", "No wholesaler selected", JOptionPane.WARNING_MESSAGE); return; }
        InventoryOrganization targetInventory = targetInventory(target);
        if (targetInventory == null) { JOptionPane.showMessageDialog(this, target.getName() + " has no Inventory Organization.", "Target setup incomplete", JOptionPane.WARNING_MESSAGE); return; }
        ProcurementRequest request = (ProcurementRequest) table.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Redirect " + request.getMedicine().getMedicineName() + " to " + target.getName() + "?", "Confirm procurement transfer", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        inventoryOrganization.getWorkQueue().getWorkRequestList().remove(request);
        targetInventory.getWorkQueue().getWorkRequestList().add(request);
        request.setMessage("Hospital procurement redirected from " + enterprise.getName() + " to " + target.getName() + ": " + request.getMedicine().getMedicineName());
        JOptionPane.showMessageDialog(this, "The hospital procurement request was redirected to " + target.getName() + " Inventory.", "Request transferred", JOptionPane.INFORMATION_MESSAGE);
        refresh();
    }

    private InventoryOrganization targetInventory(WholesalerEnterprise target) { for (Organization org : target.getOrganizationDirectory().getOrganizationList()) if (org instanceof InventoryOrganization) return (InventoryOrganization) org; return null; }
    private void back() { container.remove(this); ((CardLayout) container.getLayout()).previous(container); }
}
