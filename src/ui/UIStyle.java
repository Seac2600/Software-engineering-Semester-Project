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
    public static final Color BACKGROUND = new Color(243, 247, 252);
    public static final Color CARD = Color.WHITE;
    public static final Color PRIMARY = new Color(52, 120, 246);
    public static final Color PRIMARY_DARK = new Color(33, 90, 196);
    public static final Color TEXT = new Color(35, 43, 58);
    public static final Color SUBTLE = new Color(108, 117, 125);
    public static final Color BORDER = new Color(220, 226, 234);
    public static final Color DANGER = new Color(220, 53, 69);

    public static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 26);
    public static final Font HEADING_FONT = new Font("SansSerif", Font.BOLD, 18);
    public static final Font BODY_FONT = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("SansSerif", Font.PLAIN, 12);

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