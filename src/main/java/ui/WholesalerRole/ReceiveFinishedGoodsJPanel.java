package ui.WholesalerRole;

import Business.Enterprise.Enterprise;
import Business.Hospital.MedicineInventory;
import Business.Organization.InventoryOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.ManufacturerReplenishmentRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/** Receives only manufacturer replenishments whose status is Delivered. */
public class ReceiveFinishedGoodsJPanel extends JPanel {
    private final JPanel container;
    private final UserAccount account;
    private final InventoryOrganization inventoryOrganization;
    private final JTable table;

    public ReceiveFinishedGoodsJPanel(JPanel container, UserAccount account, Enterprise enterprise) {
        this.container = container;
        this.account = account;
        this.inventoryOrganization = inventoryOrganization(enterprise);
        setLayout(new BorderLayout(8, 8));
        add(new JLabel("Receive Finished Goods from Manufacturer"), BorderLayout.NORTH);
        table = new JTable(new DefaultTableModel(new Object[]{"Request", "Medicine", "Quantity", "Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("<< Back"); back.addActionListener(event -> back());
        JButton refresh = new JButton("Refresh"); refresh.addActionListener(event -> refresh());
        JButton receive = new JButton("Receive Product"); receive.addActionListener(event -> receive());
        controls.add(back); controls.add(refresh); controls.add(receive); add(controls, BorderLayout.SOUTH);
        refresh();
    }

    private void refresh() {
        DefaultTableModel model = (DefaultTableModel) table.getModel(); model.setRowCount(0);
        if (inventoryOrganization == null) return;
        for (WorkRequest request : inventoryOrganization.getWorkQueue().getWorkRequestList()) {
            if (request instanceof ManufacturerReplenishmentRequest replenishment
                    && ManufacturerReplenishmentRequest.DELIVERED.equals(replenishment.getStatus())) {
                model.addRow(new Object[]{replenishment, replenishment.getMedicine().getMedicineName(), replenishment.getQuantity(), replenishment.getStatus()});
            }
        }
    }

    private void receive() {
        if (inventoryOrganization == null) { JOptionPane.showMessageDialog(this, "Inventory Organization not found.", "Inventory unavailable", JOptionPane.ERROR_MESSAGE); return; }
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a delivered replenishment request first.", "No request selected", JOptionPane.WARNING_MESSAGE); return; }
        ManufacturerReplenishmentRequest request = (ManufacturerReplenishmentRequest) table.getValueAt(row, 0);
        if (!ManufacturerReplenishmentRequest.DELIVERED.equals(request.getStatus())) { JOptionPane.showMessageDialog(this, "Only delivered replenishment requests can be received.", "Request not delivered", JOptionPane.WARNING_MESSAGE); refresh(); return; }
        if (JOptionPane.showConfirmDialog(this, "Receive " + request.getQuantity() + " of " + request.getMedicine().getMedicineName() + "?", "Confirm product receipt", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        MedicineInventory inventory = findInventory(request);
        if (inventory == null) { JOptionPane.showMessageDialog(this, "No inventory record exists for this medicine.", "Inventory unavailable", JOptionPane.ERROR_MESSAGE); return; }
        inventory.setQuantity(inventory.getQuantity() + request.getQuantity());
        request.setStatus(ManufacturerReplenishmentRequest.RECEIVED);
        request.setReceiver(account);
        request.setResolveDate(new Date());
        JOptionPane.showMessageDialog(this, "Finished goods received and wholesaler inventory updated.", "Product received", JOptionPane.INFORMATION_MESSAGE);
        refresh();
    }

    private MedicineInventory findInventory(ManufacturerReplenishmentRequest request) {
        for (MedicineInventory inventory : inventoryOrganization.getMedicineInventoryDirectory().getInventoryList()) {
            if (inventory.getMedicine().equals(request.getMedicine())) return inventory;
        }
        return null;
    }
    private InventoryOrganization inventoryOrganization(Enterprise enterprise) { for (Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()) if (organization instanceof InventoryOrganization) return (InventoryOrganization) organization; return null; }
    private void back() { container.remove(this); ((CardLayout) container.getLayout()).previous(container); }
}
