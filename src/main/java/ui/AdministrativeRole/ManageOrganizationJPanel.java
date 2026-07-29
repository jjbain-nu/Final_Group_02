package ui.AdministrativeRole;

import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.Organization.Organization.Type;
import Business.Organization.OrganizationDirectory;
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

/** Organization management restricted to the current enterprise's allowed types. */
public class ManageOrganizationJPanel extends JPanel {
    private final JPanel userProcessContainer;
    private final Enterprise enterprise;
    private final OrganizationDirectory directory;
    private final JComboBox<Type> organizationJComboBox = new JComboBox<>();
    private final JTable organizationJTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Name"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    });

    public ManageOrganizationJPanel(JPanel userProcessContainer, Enterprise enterprise) {
        this.userProcessContainer = userProcessContainer;
        this.enterprise = enterprise;
        directory = enterprise.getOrganizationDirectory();
        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(organizationJTable), BorderLayout.CENTER);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(new JLabel("Organization Type:")); controls.add(organizationJComboBox);
        JButton add = new JButton("Add Organization"); add.addActionListener(event -> addOrganization());
        JButton back = new JButton("<< Back"); back.addActionListener(event -> back());
        controls.add(add); controls.add(back); add(controls, BorderLayout.SOUTH);
        populateCombo(); populateTable();
    }

    private void populateCombo() {
        for (Type type : Type.values()) if (enterprise.supportsOrganization(type)) organizationJComboBox.addItem(type);
    }
    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) organizationJTable.getModel(); model.setRowCount(0);
        for (Organization organization : directory.getOrganizationList()) model.addRow(new Object[]{organization.getOrganizationID(), organization.getName()});
    }
    private void addOrganization() {
        Type type = (Type) organizationJComboBox.getSelectedItem();
        if (type == null || !enterprise.supportsOrganization(type)) { JOptionPane.showMessageDialog(this, "This organization type is not allowed for the selected enterprise.", "Organization type not allowed", JOptionPane.WARNING_MESSAGE); return; }
        for (Organization organization : directory.getOrganizationList()) if (organization.getName().equals(type.getValue())) { JOptionPane.showMessageDialog(this, "This enterprise already has a " + type.getValue() + ".", "Duplicate organization", JOptionPane.WARNING_MESSAGE); return; }
        directory.createOrganization(type); populateTable(); JOptionPane.showMessageDialog(this, type.getValue() + " created successfully.", "Organization created", JOptionPane.INFORMATION_MESSAGE);
    }
    private void back() { userProcessContainer.remove(this); ((CardLayout) userProcessContainer.getLayout()).previous(userProcessContainer); }
}
