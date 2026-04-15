package ui;

import data.UserDAO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import services.adminMangment;
import services.appointmentManagement;
import services.loginLogic;
import services.patientManagement;

public abstract class BaseDashboard extends JFrame {
    protected JPanel contentPanel;

    protected void buildBase(String title, String subtitle) {
        setTitle(title);
        setSize(980, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UIStyle.BACKGROUND);
        setLayout(new BorderLayout(18, 18));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(UIStyle.BACKGROUND);
        topPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 0, 18));

        JLabel titleLabel = new JLabel(title);
        JLabel subtitleLabel = new JLabel(subtitle);
        UIStyle.styleLabel(titleLabel, UIStyle.TITLE_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(subtitleLabel, UIStyle.BODY_FONT, UIStyle.SUBTLE);

        JPanel titleWrap = new JPanel(new BorderLayout());
        titleWrap.setBackground(UIStyle.BACKGROUND);
        titleWrap.add(titleLabel, BorderLayout.NORTH);
        titleWrap.add(subtitleLabel, BorderLayout.SOUTH);
        topPanel.add(titleWrap, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        UIStyle.styleSecondaryButton(logoutButton);
        logoutButton.addActionListener(e -> logout());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(UIStyle.BACKGROUND);
        rightPanel.add(logoutButton);
        topPanel.add(rightPanel, BorderLayout.EAST);

        contentPanel = new JPanel(new BorderLayout(12, 12));
        UIStyle.styleCard(contentPanel);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 18, 18, 18),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyle.BORDER),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
            )
        ));

        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    protected void openChildWindow(JFrame childWindow) {
        childWindow.setVisible(true);
        dispose();
    }

    private void logout() {
        for (Window window : Window.getWindows()) {
            if (window instanceof JFrame || window instanceof JDialog) {
                window.dispose();
            }
        }

        loginLogic loginService = new loginLogic(new UserDAO());
        adminMangment adminService = new adminMangment();
        patientManagement patientService = new patientManagement();
        appointmentManagement appointmentService = new appointmentManagement();

        loginInterface loginUI = new loginInterface(
            loginService,
            adminService,
            patientService,
            appointmentService
        );
        loginUI.setVisible(true);
    }
}