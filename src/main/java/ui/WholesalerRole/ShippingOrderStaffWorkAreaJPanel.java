package ui.WholesalerRole;

import Business.Enterprise.Enterprise;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Main navigation screen for a Wholesaler Shipping Order Staff member.
 */
public class ShippingOrderStaffWorkAreaJPanel extends JPanel {

    private static final Color PORTAL_BLUE = new Color(23, 157, 208);
    private static final Color PAGE_BACKGROUND = new Color(238, 242, 246);

    public ShippingOrderStaffWorkAreaJPanel(Enterprise enterprise) {
        setLayout(new BorderLayout());
        setBackground(PAGE_BACKGROUND);
        add(createHeader(enterprise), BorderLayout.NORTH);
        add(createPortal(), BorderLayout.CENTER);
    }

    private JPanel createHeader(Enterprise enterprise) {
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

        JLabel title = new JLabel("Shipping Order Staff - Work Area");
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

        JLabel title = new JLabel("Shipping Order Staff Portal");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setForeground(new Color(47, 47, 47));
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
        addAction(actions, "Receive Purchase / Transfer Orders", 0, 0);
        addAction(actions, "Issue Picking Orders", 1, 0);
        addAction(actions, "Issue Delivery Request", 0, 1);
        addAction(actions, "View Replenishment / Order Status", 1, 1);
        addAction(actions, "My Profile", 0, 2);
        portal.add(actions, BorderLayout.CENTER);
        return portal;
    }

    private void addAction(JPanel actions, String label, int column, int row) {
        JButton button = new JButton(label);
        button.setBackground(PORTAL_BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 13f));
        button.setPreferredSize(new Dimension(250, 40));
        button.addActionListener(event -> JOptionPane.showMessageDialog(
                this, label + " is not available yet.", "Feature coming soon", JOptionPane.INFORMATION_MESSAGE));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.insets = new Insets(0, column == 0 ? 0 : 18, 12, 0);
        actions.add(button, constraints);
    }
}
