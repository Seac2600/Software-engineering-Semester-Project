package ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class UIStyle {
    public static final Color BACKGROUND = new Color(242, 246, 251);
    public static final Color CARD = Color.WHITE;
    public static final Color PRIMARY = new Color(44, 123, 229);
    public static final Color PRIMARY_DARK = new Color(28, 84, 168);
    public static final Color SUCCESS = new Color(25, 135, 84);
    public static final Color TEXT = new Color(37, 44, 55);
    public static final Color SUBTLE = new Color(108, 117, 125);
    public static final Color BORDER = new Color(222, 229, 238);
    public static final Color DANGER = new Color(220, 53, 69);

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font HEADING_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    private static final Border FIELD_BORDER = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(BORDER),
        BorderFactory.createEmptyBorder(10, 12, 10, 12)
    );

    public static void styleLabel(JLabel label, Font font, Color color) {
        label.setFont(font);
        label.setForeground(color);
    }

    public static void styleTextField(JTextField field) {
        field.setFont(BODY_FONT);
        field.setBorder(FIELD_BORDER);
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT);
    }

    public static void styleButton(JButton button) {
        button.setFont(BODY_FONT);
        button.setFocusPainted(false);
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    }

    public static void styleSuccessButton(JButton button) {
        button.setFont(BODY_FONT);
        button.setFocusPainted(false);
        button.setBackground(SUCCESS);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    }

    public static void styleSecondaryButton(JButton button) {
        button.setFont(BODY_FONT);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(TEXT);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));
    }

    public static void styleDangerButton(JButton button) {
        button.setFont(BODY_FONT);
        button.setFocusPainted(false);
        button.setBackground(DANGER);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    }

    public static void styleCard(JComponent component) {
        component.setBackground(CARD);
        component.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
    }
}
