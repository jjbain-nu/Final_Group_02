package ui.WholesalerRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.WholesalerEnterprise;
import Business.Hospital.MedicineInventory;
import Business.Network.Network;
import Business.Organization.InventoryOrganization;
import Business.Organization.Organization;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/** Read-only inventory lookup limited to wholesalers in the current network. */
public class OtherWholesalerInventoryJPanel extends JPanel {
    private final JPanel container;
    private final Enterprise currentEnterprise;
    private final JComboBox<WholesalerEnterprise> wholesalerCombo = new JComboBox<>();
    private final JLabel heading = new JLabel("Other Wholesaler Inventory");
    private final JTable table;

    public OtherWholesalerInventoryJPanel(JPanel container, Enterprise currentEnterprise, EcoSystem system) {
        this.container = container;
        this.currentEnterprise = currentEnterprise;
        setLayout(new BorderLayout(8, 8));
        add(heading, BorderLayout.NORTH);
        table = new JTable(new DefaultTableModel(new Object[]{"Wholesaler", "Medicine ID", "Medicine", "Stock Qty", "Standard Qty", "Shortage Qty", "Inventory Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(new JLabel("Wholesaler:")); controls.add(wholesalerCombo);
        JButton view = new JButton("View Inventory"); view.addActionListener(event -> refresh());
        JButton back = new JButton("<< Back"); back.addActionListener(event -> back());
        controls.add(view); controls.add(back); add(controls, BorderLayout.SOUTH);
        populateWholesalers(system);
        wholesalerCombo.addActionListener(event -> refresh());
        if (wholesalerCombo.getItemCount() > 0) refresh();
    }

    private void populateWholesalers(EcoSystem system) {
        for (Network network : system.getNetworkList()) {
            if (!network.getEnterpriseDirectory().getEnterpriseList().contains(currentEnterprise)) continue;
            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                if (enterprise instanceof WholesalerEnterprise && enterprise != currentEnterprise) wholesalerCombo.addItem((WholesalerEnterprise) enterprise);
            }
            return;
        }
    }

    private void refresh() {
        DefaultTableModel model = (DefaultTableModel) table.getModel(); model.setRowCount(0);
        WholesalerEnterprise wholesaler = (WholesalerEnterprise) wholesalerCombo.getSelectedItem();
        if (wholesaler == null) { heading.setText("Other Wholesaler Inventory"); return; }
        heading.setText("Other Wholesaler Inventory - " + wholesaler.getName());
        for (Organization organization : wholesaler.getOrganizationDirectory().getOrganizationList()) {
            if (organization instanceof InventoryOrganization inventoryOrganization) {
                for (MedicineInventory inventory : inventoryOrganization.getMedicineInventoryDirectory().getInventoryList()) {
                    String status = inventory.isShortage() ? "Shortage"
                            : inventory.isExcess() ? "Surplus" : "At Standard";
                    model.addRow(new Object[]{wholesaler.getName(), inventory.getMedicine().getMedicineId(), inventory.getMedicine().getMedicineName(), inventory.getQuantity(), inventory.getStandardStock(), inventory.getShortageQty(), status});
                }
                return;
            }
        }
    }

    private void back() { container.remove(this); ((CardLayout) container.getLayout()).previous(container); }
}
