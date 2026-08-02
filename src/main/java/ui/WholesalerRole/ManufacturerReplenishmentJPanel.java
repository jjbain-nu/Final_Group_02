package ui.WholesalerRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Hospital.Medicine;
import Business.Hospital.MedicineInventory;
import Business.Network.Network;
import Business.Organization.InventoryOrganization;
import Business.Organization.Organization;
import Business.Organization.ProductionOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.ManufacturerReplenishmentRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/** Creates and routes wholesaler requests to Manufacturer Production. */
public class ManufacturerReplenishmentJPanel extends JPanel {
    private final JPanel container;
    private final UserAccount account;
    private final InventoryOrganization organization;
    private final Enterprise enterprise;
    private final EcoSystem system;
    private final JComboBox<Medicine> medicineCombo = new JComboBox<>();
    private final JTextField quantityField = new JTextField(6);
    private final JTable table;

    public ManufacturerReplenishmentJPanel(JPanel container, UserAccount account, InventoryOrganization organization, Enterprise enterprise, EcoSystem system) {
        this.container = container;
        this.account = account;
        this.organization = organization;
        this.enterprise = enterprise;
        this.system = system;
        setLayout(new BorderLayout(8, 8));
        add(new JLabel("Manufacturer Replenishment Requests"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(4, 4, 4, 4); c.anchor = GridBagConstraints.WEST;
        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Medicine:"), c);
        c.gridx = 1; form.add(medicineCombo, c);
        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Quantity:"), c);
        c.gridx = 1; form.add(quantityField, c);
        JButton submit = new JButton("Send Replenishment Request"); submit.addActionListener(event -> submit());
        c.gridx = 0; c.gridy = 2; c.gridwidth = 2; form.add(submit, c);

        table = new JTable(new DefaultTableModel(new Object[]{"Request", "Medicine", "Quantity", "Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        });
        JPanel content = new JPanel(new BorderLayout(8, 8)); content.add(form, BorderLayout.NORTH); content.add(new JScrollPane(table), BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("<< Back"); back.addActionListener(event -> back());
        JButton refresh = new JButton("Refresh"); refresh.addActionListener(event -> refresh());
        controls.add(back); controls.add(refresh); add(controls, BorderLayout.SOUTH);
        medicineCombo.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean selected, boolean focus) {
                super.getListCellRendererComponent(list, value, index, selected, focus);
                if (value instanceof Medicine medicine) setText(medicine.getMedicineName());
                return this;
            }
        });
        populateMedicineCombo(); refresh();
    }

    private void populateMedicineCombo() {
        for (MedicineInventory inventory : organization.getMedicineInventoryDirectory().getInventoryList()) medicineCombo.addItem(inventory.getMedicine());
    }

    private void submit() {
        Medicine medicine = (Medicine) medicineCombo.getSelectedItem();
        int quantity;
        try { quantity = Integer.parseInt(quantityField.getText().trim()); if (quantity <= 0) throw new NumberFormatException(); }
        catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Quantity must be a positive whole number.", "Invalid quantity", JOptionPane.WARNING_MESSAGE); return; }
        if (medicine == null) { JOptionPane.showMessageDialog(this, "No medicine is available to request.", "No medicine", JOptionPane.WARNING_MESSAGE); return; }
        ProductionOrganization production = productionOrganization();
        if (production == null) { JOptionPane.showMessageDialog(this, "No Production Organization exists in this network.", "Production organization missing", JOptionPane.WARNING_MESSAGE); return; }
        if (JOptionPane.showConfirmDialog(this, "Send a replenishment request for " + medicine.getMedicineName() + " to the manufacturer?", "Confirm request", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        ManufacturerReplenishmentRequest request = new ManufacturerReplenishmentRequest(medicine, quantity, enterprise.getName());
        request.setSender(account);
        organization.getWorkQueue().getWorkRequestList().add(request);
        production.getWorkQueue().getWorkRequestList().add(request);
        if (!account.getWorkQueue().getWorkRequestList().contains(request)) account.getWorkQueue().getWorkRequestList().add(request);
        quantityField.setText("");
        JOptionPane.showMessageDialog(this, "Replenishment request sent to the Production Organization work queue.", "Request created", JOptionPane.INFORMATION_MESSAGE);
        refresh();
    }

    private void refresh() {
        DefaultTableModel model = (DefaultTableModel) table.getModel(); model.setRowCount(0);
        for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) if (request instanceof ManufacturerReplenishmentRequest replenishment) {
            model.addRow(new Object[]{replenishment, replenishment.getMedicine().getMedicineName(), replenishment.getQuantity(), replenishment.getStatus()});
        }
    }

    private ProductionOrganization productionOrganization() {
        for (Network network : system.getNetworkList()) {
            if (!network.getEnterpriseDirectory().getEnterpriseList().contains(enterprise)) continue;
            for (Enterprise candidate : network.getEnterpriseDirectory().getEnterpriseList()) {
                for (Organization candidateOrganization : candidate.getOrganizationDirectory().getOrganizationList()) {
                    if (candidateOrganization instanceof ProductionOrganization) return (ProductionOrganization) candidateOrganization;
                }
            }
        }
        return null;
    }

    private void back() { container.remove(this); ((CardLayout) container.getLayout()).previous(container); }
}
