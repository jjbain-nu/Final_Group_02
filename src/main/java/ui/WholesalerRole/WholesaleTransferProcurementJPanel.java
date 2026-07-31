package ui.WholesalerRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.WholesalerEnterprise;
import Business.Hospital.Medicine;
import Business.Hospital.MedicineInventory;
import Business.Hospital.ProcurementRequest;
import Business.Network.Network;
import Business.Organization.InventoryOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
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
import javax.swing.JTextField;

/** Sends a wholesaler procurement request to another wholesaler's inventory. */
public class WholesaleTransferProcurementJPanel extends JPanel {
    private final JPanel container;
    private final UserAccount account;
    private final InventoryOrganization inventoryOrganization;
    private final Enterprise enterprise;
    private final EcoSystem system;
    private final JComboBox<WholesalerEnterprise> wholesalerCombo = new JComboBox<>();
    private final JComboBox<Medicine> medicineCombo = new JComboBox<>();
    private final JTextField quantityField = new JTextField(6);

    public WholesaleTransferProcurementJPanel(JPanel container, UserAccount account,
            InventoryOrganization inventoryOrganization, Enterprise enterprise, EcoSystem system) {
        this.container = container; this.account = account; this.inventoryOrganization = inventoryOrganization;
        this.enterprise = enterprise; this.system = system;
        setLayout(new BorderLayout());
        add(new JLabel("Transfer Procurement Request"), BorderLayout.NORTH);
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); c.anchor = GridBagConstraints.WEST; c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Request from:"), c); c.gridx = 1; form.add(new JLabel(enterprise.getName()), c);
        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Request from wholesaler:"), c); c.gridx = 1; form.add(wholesalerCombo, c);
        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Medicine:"), c); c.gridx = 1; form.add(medicineCombo, c);
        c.gridx = 0; c.gridy = 3; form.add(new JLabel("Quantity:"), c); c.gridx = 1; form.add(quantityField, c);
        JButton send = new JButton("Send Transfer Procurement Request"); send.addActionListener(event -> send());
        c.gridx = 0; c.gridy = 4; c.gridwidth = 2; form.add(send, c); add(form, BorderLayout.CENTER);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT)); JButton back = new JButton("<< Back"); back.addActionListener(event -> back()); controls.add(back); add(controls, BorderLayout.SOUTH);
        populateWholesalers(); populateMedicines();
    }

    private void populateWholesalers() {
        for (Network network : system.getNetworkList()) for (Enterprise candidate : network.getEnterpriseDirectory().getEnterpriseList()) {
            if (candidate instanceof WholesalerEnterprise && candidate != enterprise) wholesalerCombo.addItem((WholesalerEnterprise) candidate);
        }
    }
    private void populateMedicines() {
        for (MedicineInventory inventory : inventoryOrganization.getMedicineInventoryDirectory().getInventoryList()) medicineCombo.addItem(inventory.getMedicine());
    }
    private void send() {
        WholesalerEnterprise target = (WholesalerEnterprise) wholesalerCombo.getSelectedItem();
        Medicine medicine = (Medicine) medicineCombo.getSelectedItem(); int quantity;
        try { quantity = Integer.parseInt(quantityField.getText().trim()); if (quantity <= 0) throw new NumberFormatException(); }
        catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Quantity must be a positive whole number.", "Invalid quantity", JOptionPane.WARNING_MESSAGE); return; }
        if (target == null || medicine == null) { JOptionPane.showMessageDialog(this, "Select a target wholesaler and medicine.", "Missing information", JOptionPane.WARNING_MESSAGE); return; }
        InventoryOrganization targetInventory = targetInventory(target);
        if (targetInventory == null) { JOptionPane.showMessageDialog(this, target.getName() + " has no Inventory Organization.", "Target setup incomplete", JOptionPane.WARNING_MESSAGE); return; }
        if (JOptionPane.showConfirmDialog(this, "Request " + quantity + " of " + medicine.getMedicineName() + " from " + target.getName() + "?", "Confirm transfer request", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        ProcurementRequest request = new ProcurementRequest();
        request.setMedicine(medicine);
        request.setRequestQty(quantity);
        request.setStatus("Procured");
        request.setMessage("Transfer procurement from " + enterprise.getName() + " to " + target.getName() + ": " + medicine.getMedicineName());
        request.setSender(account); targetInventory.getWorkQueue().getWorkRequestList().add(request); account.getWorkQueue().getWorkRequestList().add(request);
        quantityField.setText("");
        JOptionPane.showMessageDialog(this, "Transfer procurement request sent to " + target.getName() + " Inventory.", "Request created", JOptionPane.INFORMATION_MESSAGE);
    }
    private InventoryOrganization targetInventory(WholesalerEnterprise target) { for (Organization org : target.getOrganizationDirectory().getOrganizationList()) if (org instanceof InventoryOrganization) return (InventoryOrganization) org; return null; }
    private void back() { container.remove(this); ((CardLayout) container.getLayout()).previous(container); }
}
