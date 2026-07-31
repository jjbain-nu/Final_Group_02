package ui.WholesalerRole;

import Business.Enterprise.Enterprise;
import Business.Hospital.ProcurementRequest;
import Business.Organization.Organization;
import Business.Organization.ShippingOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.WholesaleWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/** Lets a Wholesaler Inventory Admin release incoming procurement requests to Shipping. */
public class InventoryProcurementJPanel extends JPanel {
    private final JPanel container;
    private final UserAccount account;
    private final Organization inventoryOrganization;
    private final Enterprise enterprise;
    private final JTable table;

    public InventoryProcurementJPanel(JPanel container, UserAccount account,
            Organization inventoryOrganization, Enterprise enterprise) {
        this.container = container;
        this.account = account;
        this.inventoryOrganization = inventoryOrganization;
        this.enterprise = enterprise;
        setLayout(new BorderLayout(8, 8));
        add(new JLabel("Incoming Procurement Requests"), BorderLayout.NORTH);
        table = new JTable(new DefaultTableModel(
                new Object[]{"Request", "Medicine", "Quantity", "Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("<< Back"); back.addActionListener(event -> back());
        JButton refresh = new JButton("Refresh"); refresh.addActionListener(event -> refresh());
        JButton forward = new JButton("Send to Shipping"); forward.addActionListener(event -> sendToShipping());
        controls.add(back); controls.add(refresh); controls.add(forward);
        add(controls, BorderLayout.SOUTH);
        refresh();
    }

    private void refresh() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (WorkRequest request : inventoryOrganization.getWorkQueue().getWorkRequestList()) {
            if (request instanceof ProcurementRequest procurement && "Procured".equals(procurement.getStatus())) {
                model.addRow(new Object[]{procurement, procurement.getMedicine().getMedicineName(), procurement.getRequestQty(), procurement.getStatus()});
            }
        }
    }

    private void sendToShipping() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a procurement request first.", "No request selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        WholesaleWorkRequest request = (WholesaleWorkRequest) table.getValueAt(row, 0);
        ShippingOrganization shipping = shippingOrganization();
        if (shipping == null) {
            JOptionPane.showMessageDialog(this, "This wholesaler needs a Shipping Organization before requests can be released.", "Shipping organization missing", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this,
                "Send " + request.getItemDescription() + " to Shipping?",
                "Confirm shipping handoff", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        request.setStatus(WholesaleWorkRequest.REQUESTED);
        request.setReceiver(account);
        if (!shipping.getWorkQueue().getWorkRequestList().contains(request)) {
            shipping.getWorkQueue().getWorkRequestList().add(request);
        }
        JOptionPane.showMessageDialog(this, "Procurement request sent to Shipping Order Staff.", "Request released", JOptionPane.INFORMATION_MESSAGE);
        refresh();
    }

    private ShippingOrganization shippingOrganization() {
        for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
            if (org instanceof ShippingOrganization) return (ShippingOrganization) org;
        }
        return null;
    }

    private void back() {
        container.remove(this);
        ((CardLayout) container.getLayout()).previous(container);
    }
}
