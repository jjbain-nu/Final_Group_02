package ui.WholesalerRole;

import Business.Enterprise.Enterprise;
import Business.UserAccount.UserAccount;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Shared landing screen for wholesale roles while their specialized workflows
 * are implemented.
 */
public class WholesalerWorkAreaJPanel extends JPanel {

    public WholesalerWorkAreaJPanel(UserAccount account, Enterprise enterprise, String title) {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(48, 48, 48, 48));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 24f));
        add(titleLabel, BorderLayout.NORTH);

        String employeeName = account.getEmployee() == null ? account.getUsername() : account.getEmployee().getName();
        JLabel welcomeLabel = new JLabel(
                "Welcome, " + employeeName + " — " + enterprise.getName(), SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(16f));
        add(welcomeLabel, BorderLayout.CENTER);
    }
}
