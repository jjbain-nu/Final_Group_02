package ui.WholesalerRole;

import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.Organization.ShippingOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.WholesaleWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Wholesaler-only workflow UI for Inventory and Shipping roles.
 */
public class WholesalerWorkflowJPanel extends JPanel {

    public enum View {
        INVENTORY_ADMIN("Inventory Administrator"),
        SHIPPING_ORDER_STAFF("Shipping Order Staff"),
        SHIPPING_OPERATOR("Shipping Operator");

        private final String title;

        View(String title) {
            this.title = title;
        }
    }

    private static final Color PORTAL_BLUE = new Color(23, 157, 208);
    private static final Color PAGE_BACKGROUND = new Color(238, 242, 246);
    private final View view;
    private final JPanel userProcessContainer;
    private final UserAccount account;
    private final Enterprise enterprise;
    private final Organization organization;

    public WholesalerWorkflowJPanel(JPanel userProcessContainer, View view, UserAccount account, Organization organization, Enterprise enterprise) {
        this.userProcessContainer = userProcessContainer;
        this.view = view;
        this.account = account;
        this.enterprise = enterprise;
        this.organization = organization;
        setLayout(new BorderLayout());
        setBackground(PAGE_BACKGROUND);
        add(createHeader(), BorderLayout.NORTH);
        add(createPortal(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(14, 0));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 226, 232)),
                BorderFactory.createEmptyBorder(26, 34, 18, 34)));
        JPanel accent = new JPanel();
        accent.setBackground(PORTAL_BLUE);
        accent.setPreferredSize(new Dimension(8, 32));
        header.add(accent, BorderLayout.WEST);
        JPanel text = new JPanel();
        text.setBackground(Color.WHITE);
        text.setLayout(new javax.swing.BoxLayout(text, javax.swing.BoxLayout.Y_AXIS));
        JLabel title = new JLabel(view.title + " - Work Area");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setForeground(new Color(26, 51, 79));
        JLabel organization = new JLabel("Organization: " + enterprise.getName());
        organization.setFont(organization.getFont().deriveFont(14f));
        organization.setForeground(new Color(75, 83, 91));
        text.add(title);
        text.add(javax.swing.Box.createVerticalStrut(7));
        text.add(organization);
        header.add(text, BorderLayout.CENTER);
        return header;
    }

    private JPanel createPortal() {
        JPanel portal = new JPanel(new BorderLayout());
        portal.setBackground(PAGE_BACKGROUND);
        portal.setBorder(BorderFactory.createEmptyBorder(26, 42, 42, 42));
        JLabel title = new JLabel(view.title + " Portal");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        JLabel path = new JLabel("Home  /  Work Area");
        path.setForeground(new Color(132, 140, 148));
        path.setFont(path.getFont().deriveFont(12f));
        JPanel heading = new JPanel();
        heading.setBackground(PAGE_BACKGROUND);
        heading.setLayout(new javax.swing.BoxLayout(heading, javax.swing.BoxLayout.Y_AXIS));
        heading.add(title);
        heading.add(javax.swing.Box.createVerticalStrut(5));
        heading.add(path);
        portal.add(heading, BorderLayout.NORTH);

        JPanel actions = new JPanel(new GridBagLayout());
        actions.setBackground(PAGE_BACKGROUND);
        actions.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
        if (view == View.INVENTORY_ADMIN) {
            addAction(actions, "Check Inventory", 0, 0, this::showInventoryTodo);
            addAction(actions, "Review Procurement Requests", 1, 0, () -> navigate(new InventoryProcurementJPanel(userProcessContainer, account, organization, enterprise)));
            addAction(actions, "Check Another Wholesaler Inventory", 0, 1, this::showOtherWholesalerTodo);
            addAction(actions, "Transfer Order Request", 1, 1, () -> navigate(WholesaleWorkflowActionJPanel.request(userProcessContainer, account, enterprise, WholesaleWorkRequest.RequestType.TRANSFER)));
            addAction(actions, "View Replenishment / Order Status", 0, 2, () -> navigate(WholesaleWorkflowActionJPanel.status(userProcessContainer, account, enterprise, true)));
        } else if (view == View.SHIPPING_ORDER_STAFF) {
            addAction(actions, "Receive Purchase / Transfer Orders", 0, 0, () -> navigate(WholesaleWorkflowActionJPanel.stage(userProcessContainer, account, enterprise, WholesaleWorkRequest.REQUESTED, WholesaleWorkRequest.RECEIVED_BY_STAFF, "Receive orders")));
            addAction(actions, "Issue Picking Orders", 1, 0, () -> navigate(WholesaleWorkflowActionJPanel.stage(userProcessContainer, account, enterprise, WholesaleWorkRequest.RECEIVED_BY_STAFF, WholesaleWorkRequest.PICKING, "Issue picking order")));
            addAction(actions, "Issue Delivery Request", 0, 1, this::showDeliveryTodo);
            addAction(actions, "View Replenishment / Order Status", 1, 1, () -> navigate(WholesaleWorkflowActionJPanel.status(userProcessContainer, account, enterprise, false)));
            addAction(actions, "My Profile", 0, 2, () -> navigate(WholesaleWorkflowActionJPanel.profile(userProcessContainer, account, enterprise)));
        } else {
            addAction(actions, "Picking", 0, 0, () -> navigate(WholesaleWorkflowActionJPanel.stage(userProcessContainer, account, enterprise, WholesaleWorkRequest.PICKING, WholesaleWorkRequest.PICKED, "Complete picking")));
            addAction(actions, "Packing", 1, 0, () -> navigate(WholesaleWorkflowActionJPanel.stage(userProcessContainer, account, enterprise, WholesaleWorkRequest.PICKED, WholesaleWorkRequest.PACKED, "Complete packing")));
            addAction(actions, "Shipping", 0, 1, () -> navigate(WholesaleWorkflowActionJPanel.stage(userProcessContainer, account, enterprise, WholesaleWorkRequest.PACKED, WholesaleWorkRequest.SHIPPED, "Confirm shipment")));
            addAction(actions, "Receive Finished Goods", 1, 1, this::showReceiveFinishedGoodsTodo);
            addAction(actions, "View Replenishment / Order Status", 0, 2, () -> navigate(WholesaleWorkflowActionJPanel.status(userProcessContainer, account, enterprise, false)));
            addAction(actions, "My Profile", 1, 2, () -> navigate(WholesaleWorkflowActionJPanel.profile(userProcessContainer, account, enterprise)));
        }
        portal.add(actions, BorderLayout.CENTER);
        return portal;
    }

    private void navigate(JPanel panel) {
        userProcessContainer.add(panel);
        ((java.awt.CardLayout) userProcessContainer.getLayout()).next(userProcessContainer);
    }

    private void createRequest(WholesaleWorkRequest.RequestType type) {
        ShippingOrganization shipping = shippingOrganization();
        if (shipping == null) {
            JOptionPane.showMessageDialog(this, "Create a Shipping organization before submitting requests.", "Shipping organization missing", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String item = JOptionPane.showInputDialog(this, "Item description:", type + " request", JOptionPane.QUESTION_MESSAGE);
        if (item == null) return;
        item = item.trim();
        if (item.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an item description.", "Missing item", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String quantityText = JOptionPane.showInputDialog(this, "Quantity:", "1");
        if (quantityText == null) return;
        int quantity;
        try {
            quantity = Integer.parseInt(quantityText.trim());
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a positive whole number.", "Invalid quantity", JOptionPane.WARNING_MESSAGE);
            return;
        }
        WholesaleWorkRequest request = new WholesaleWorkRequest(type, item, quantity);
        request.setSender(account);
        shipping.getWorkQueue().getWorkRequestList().add(request);
        account.getWorkQueue().getWorkRequestList().add(request);
        JOptionPane.showMessageDialog(this, type + " request sent to Shipping Order Staff.", "Request created", JOptionPane.INFORMATION_MESSAGE);
    }

    private void transition(String currentStatus, String nextStatus, String action) {
        List<WholesaleWorkRequest> eligible = shippingRequests().stream()
                .filter(request -> currentStatus.equals(request.getStatus()))
                .collect(Collectors.toList());
        if (eligible.isEmpty()) {
            JOptionPane.showMessageDialog(this, "There are no orders ready for this step.", "No eligible orders", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        WholesaleWorkRequest selected = (WholesaleWorkRequest) JOptionPane.showInputDialog(
                this, "Select an order:", action, JOptionPane.QUESTION_MESSAGE, null,
                eligible.toArray(), eligible.get(0));
        if (selected == null) return;
        if (JOptionPane.showConfirmDialog(this, action + " for:\n" + selected.getMessage() + "?", action,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION) return;
        selected.setStatus(nextStatus);
        selected.setReceiver(account);
        if (WholesaleWorkRequest.RECEIVED_INTO_INVENTORY.equals(nextStatus)) {
            selected.setResolveDate(new Date());
            // TODO: Add the received quantity to the wholesaler inventory when inventory management is implemented.
        }
        JOptionPane.showMessageDialog(this, "Order status updated to '" + nextStatus + "'.", "Workflow updated", JOptionPane.INFORMATION_MESSAGE);
    }

    private ShippingOrganization shippingOrganization() {
        for (Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()) {
            if (organization instanceof ShippingOrganization) return (ShippingOrganization) organization;
        }
        return null;
    }

    private List<WholesaleWorkRequest> shippingRequests() {
        ShippingOrganization shipping = shippingOrganization();
        if (shipping == null) return java.util.Collections.emptyList();
        return shipping.getWorkQueue().getWorkRequestList().stream()
                .filter(WholesaleWorkRequest.class::isInstance)
                .map(WholesaleWorkRequest.class::cast)
                .collect(Collectors.toList());
    }

    private void showStatus() {
        List<WholesaleWorkRequest> requests = view == View.INVENTORY_ADMIN
                ? account.getWorkQueue().getWorkRequestList().stream().filter(WholesaleWorkRequest.class::isInstance)
                        .map(WholesaleWorkRequest.class::cast).collect(Collectors.toList())
                : shippingRequests();
        String text = requests.isEmpty() ? "No wholesale requests found." : requests.stream()
                .map(WholesaleWorkRequest::toString).collect(Collectors.joining("\n"));
        JOptionPane.showMessageDialog(this, text, "Wholesaler order status", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInventoryTodo() {
        // TODO: Implement wholesaler material catalog and inventory quantity management.
        JOptionPane.showMessageDialog(this, "Inventory catalog and stock levels are not implemented yet.", "Inventory TODO", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showOtherWholesalerTodo() {
        // TODO: Implement cross-wholesaler inventory visibility and transfer routing.
        JOptionPane.showMessageDialog(this, "Other wholesaler inventory lookup is outside the current Wholesaler-only scope.", "Cross-wholesaler TODO", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showDeliveryTodo() {
        // TODO: Implement transporter integration and delivery requests.
        JOptionPane.showMessageDialog(this, "Transporter delivery requests are outside the current Wholesaler-only scope.", "Transporter TODO", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showReceiveFinishedGoodsTodo() {
        // TODO: Implement manufacturer-to-wholesaler finished-goods receiving and inventory updates.
        JOptionPane.showMessageDialog(this,
                "Receiving finished goods from a manufacturer is not implemented yet.",
                "Manufacturer receiving TODO", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showProfile() {
        JOptionPane.showMessageDialog(this, "Signed in as " + account.getUsername() + ".", "My profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addAction(JPanel actions, String label, int column, int row, Runnable action) {
        JButton button = new JButton(label);
        button.setBackground(PORTAL_BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 13f));
        button.setPreferredSize(new Dimension(250, 40));
        button.addActionListener(event -> action.run());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.insets = new Insets(0, column == 0 ? 0 : 18, 12, 0);
        actions.add(button, constraints);
    }
}
