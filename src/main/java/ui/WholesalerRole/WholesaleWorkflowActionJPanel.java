package ui.WholesalerRole;

import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.Organization.ShippingOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.WholesaleWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/** Reusable CardLayout panels for Wholesaler request creation and order workflow. */
public class WholesaleWorkflowActionJPanel extends JPanel {
    private final JPanel container;
    private final UserAccount account;
    private final Enterprise enterprise;
    private final boolean inventoryOnly;
    private final String fromStatus;
    private final String toStatus;
    private final String action;
    private final WholesaleWorkRequest.RequestType requestType;
    private JTable table;

    private WholesaleWorkflowActionJPanel(JPanel container, UserAccount account, Enterprise enterprise,
            boolean inventoryOnly, String fromStatus, String toStatus, String action,
            WholesaleWorkRequest.RequestType requestType) {
        this.container = container; this.account = account; this.enterprise = enterprise;
        this.inventoryOnly = inventoryOnly; this.fromStatus = fromStatus; this.toStatus = toStatus;
        this.action = action; this.requestType = requestType;
    }

    public static JPanel request(JPanel c, UserAccount a, Enterprise e, WholesaleWorkRequest.RequestType type) {
        WholesaleWorkflowActionJPanel panel = new WholesaleWorkflowActionJPanel(c, a, e, false, null, null, null, type);
        panel.buildRequest(); return panel;
    }
    public static JPanel stage(JPanel c, UserAccount a, Enterprise e, String from, String to, String action) {
        WholesaleWorkflowActionJPanel panel = new WholesaleWorkflowActionJPanel(c, a, e, false, from, to, action, null);
        panel.buildStage(); return panel;
    }
    public static JPanel status(JPanel c, UserAccount a, Enterprise e, boolean inventoryOnly) {
        WholesaleWorkflowActionJPanel panel = new WholesaleWorkflowActionJPanel(c, a, e, inventoryOnly, null, null, null, null);
        panel.buildStatus(); return panel;
    }
    public static JPanel profile(JPanel c, UserAccount a, Enterprise e) {
        WholesaleWorkflowActionJPanel panel = new WholesaleWorkflowActionJPanel(c, a, e, false, null, null, null, null);
        panel.buildProfile(); return panel;
    }

    private void buildRequest() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(28, 38, 28, 38));
        JLabel heading = new JLabel(requestType + " Request");
        heading.setFont(heading.getFont().deriveFont(java.awt.Font.BOLD, 18f));
        add(heading, BorderLayout.NORTH);
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(24, 0, 0, 0));
        GridBagConstraints label = new GridBagConstraints(); label.anchor = GridBagConstraints.WEST; label.insets = new Insets(0, 0, 12, 12);
        GridBagConstraints input = new GridBagConstraints(); input.gridx = 1; input.anchor = GridBagConstraints.WEST; input.insets = new Insets(0, 0, 12, 0);
        JTextField item = new JTextField(); item.setPreferredSize(new Dimension(260, 28));
        JTextField quantity = new JTextField(); quantity.setPreferredSize(new Dimension(90, 28));
        label.gridy = 0; form.add(new JLabel("Item description:"), label); input.gridy = 0; form.add(item, input);
        label.gridy = 1; form.add(new JLabel("Quantity:"), label); input.gridy = 1; form.add(quantity, input);
        JButton submit = new JButton("Submit Request"); submit.addActionListener(event -> submitRequest(item, quantity));
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0)); controls.add(backButton()); controls.add(submit);
        GridBagConstraints buttons = new GridBagConstraints(); buttons.gridx = 0; buttons.gridy = 2; buttons.gridwidth = 2; buttons.anchor = GridBagConstraints.WEST; buttons.insets = new Insets(8, 0, 0, 0); form.add(controls, buttons);
        JPanel content = new JPanel(new BorderLayout()); content.add(form, BorderLayout.NORTH); add(content, BorderLayout.CENTER);
    }

    private void submitRequest(JTextField itemField, JTextField quantityField) {
        ShippingOrganization shipping = shipping();
        String item = itemField.getText().trim(); int quantity;
        if (shipping == null || item.isEmpty()) { JOptionPane.showMessageDialog(this, "A Shipping organization and item description are required.", "Missing information", JOptionPane.WARNING_MESSAGE); return; }
        try { quantity = Integer.parseInt(quantityField.getText().trim()); if (quantity <= 0) throw new NumberFormatException(); }
        catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Quantity must be a positive whole number.", "Invalid quantity", JOptionPane.WARNING_MESSAGE); return; }
        WholesaleWorkRequest request = new WholesaleWorkRequest(requestType, item, quantity); request.setSender(account);
        shipping.getWorkQueue().getWorkRequestList().add(request); account.getWorkQueue().getWorkRequestList().add(request);
        JOptionPane.showMessageDialog(this, requestType + " request sent to Shipping Order Staff.", "Request created", JOptionPane.INFORMATION_MESSAGE); back();
    }

    private void buildStage() {
        setLayout(new BorderLayout(8, 8)); setBorder(BorderFactory.createEmptyBorder(28, 38, 28, 38)); add(new JLabel(action + " - Select an eligible order"), BorderLayout.NORTH);
        table = createTable(); add(new JScrollPane(table), BorderLayout.CENTER); refreshStage();
        JPanel controls = new JPanel(); JButton advance = new JButton(action); advance.addActionListener(event -> advance()); controls.add(backButton()); controls.add(advance); add(controls, BorderLayout.SOUTH);
    }
    private void refreshStage() { DefaultTableModel model = (DefaultTableModel)table.getModel(); model.setRowCount(0); for (WholesaleWorkRequest r : requests(false)) if (fromStatus.equals(r.getStatus())) model.addRow(new Object[]{r, r.getRequestType(), r.getItemDescription(), r.getQuantity(), r.getStatus()}); }
    private void advance() {
        int row = table.getSelectedRow(); if (row < 0) { JOptionPane.showMessageDialog(this, "Select an order first.", "No order selected", JOptionPane.WARNING_MESSAGE); return; }
        WholesaleWorkRequest request = (WholesaleWorkRequest)table.getValueAt(row, 0);
        if (!fromStatus.equals(request.getStatus())) { JOptionPane.showMessageDialog(this, "This order is no longer eligible for this step.", "Order changed", JOptionPane.WARNING_MESSAGE); refreshStage(); return; }
        if (JOptionPane.showConfirmDialog(this, action + " for " + request.getMessage() + "?", "Confirm", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        request.setStatus(toStatus); request.setReceiver(account); if (WholesaleWorkRequest.RECEIVED_INTO_INVENTORY.equals(toStatus)) { request.setResolveDate(new Date()); /* TODO: update wholesaler inventory when implemented. */ }
        JOptionPane.showMessageDialog(this, "Order updated to " + toStatus + ".", "Workflow updated", JOptionPane.INFORMATION_MESSAGE); refreshStage();
    }

    private void buildStatus() { setLayout(new BorderLayout(8, 8)); setBorder(BorderFactory.createEmptyBorder(28, 38, 28, 38)); add(new JLabel("Wholesaler Order Status"), BorderLayout.NORTH); table = createTable(); add(new JScrollPane(table), BorderLayout.CENTER); DefaultTableModel model=(DefaultTableModel)table.getModel(); for(WholesaleWorkRequest r:requests(inventoryOnly)) model.addRow(new Object[]{r,r.getRequestType(),r.getItemDescription(),r.getQuantity(),r.getStatus()}); JPanel controls=new JPanel(new FlowLayout(FlowLayout.LEFT)); controls.add(backButton()); add(controls, BorderLayout.SOUTH); }
    private void buildProfile() { setLayout(new BorderLayout(8,8)); setBorder(BorderFactory.createEmptyBorder(28, 38, 28, 38)); add(new JLabel("My Profile"),BorderLayout.NORTH); String name=account.getEmployee()==null?account.getUsername():account.getEmployee().getName(); JPanel content=new JPanel(new FlowLayout(FlowLayout.LEFT)); content.add(new JLabel("User: "+account.getUsername()+"    Name: "+name+"    Enterprise: "+enterprise.getName())); add(content,BorderLayout.CENTER); JPanel controls=new JPanel(new FlowLayout(FlowLayout.LEFT)); controls.add(backButton()); add(controls,BorderLayout.SOUTH); }
    private JTable createTable() { return new JTable(new DefaultTableModel(new Object[]{"Request","Type","Item","Quantity","Status"},0){ public boolean isCellEditable(int r,int c){return false;} }); }
    private List<WholesaleWorkRequest> requests(boolean own) { List<WorkRequest> source=own?account.getWorkQueue().getWorkRequestList():(shipping()==null?java.util.Collections.emptyList():shipping().getWorkQueue().getWorkRequestList()); return source.stream().filter(WholesaleWorkRequest.class::isInstance).map(WholesaleWorkRequest.class::cast).collect(Collectors.toList()); }
    private ShippingOrganization shipping(){ for(Organization org:enterprise.getOrganizationDirectory().getOrganizationList()) if(org instanceof ShippingOrganization) return (ShippingOrganization)org; return null; }
    private JButton backButton(){ JButton button=new JButton("<< Back"); button.addActionListener(event->back()); return button; }
    private void back(){ container.remove(this); ((CardLayout)container.getLayout()).previous(container); }
}
