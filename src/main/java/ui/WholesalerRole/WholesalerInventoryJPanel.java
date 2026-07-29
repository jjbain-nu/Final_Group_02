package ui.WholesalerRole;

import Business.Hospital.MedicineInventory;
import Business.Organization.InventoryOrganization;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/** Read-only inventory view for a wholesaler inventory organization. */
public class WholesalerInventoryJPanel extends JPanel {
    private final JPanel container;
    private final InventoryOrganization organization;
    private final JTable table;

    public WholesalerInventoryJPanel(JPanel container, InventoryOrganization organization) {
        this.container = container;
        this.organization = organization;
        setLayout(new BorderLayout(8, 8));
        add(new JLabel("Wholesaler Medicine Inventory"), BorderLayout.NORTH);
        table = new JTable(new DefaultTableModel(
                new Object[]{"Medicine ID", "Medicine", "Stock Qty", "Standard Qty", "Shortage Qty"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("<< Back"); back.addActionListener(event -> back());
        JButton refresh = new JButton("Refresh"); refresh.addActionListener(event -> refresh());
        controls.add(back); controls.add(refresh);
        add(controls, BorderLayout.SOUTH);
        refresh();
    }

    private void refresh() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (MedicineInventory inventory : organization.getMedicineInventoryDirectory().getInventoryList()) {
            model.addRow(new Object[]{inventory.getMedicine().getMedicineId(),
                inventory.getMedicine().getMedicineName(), inventory.getQuantity(),
                inventory.getStandardStock(), inventory.getShortageQty()});
        }
    }

    private void back() {
        container.remove(this);
        ((CardLayout) container.getLayout()).previous(container);
    }
}
