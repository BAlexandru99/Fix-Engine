package com.client.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FixOrderGUI extends JFrame {

    public FixOrderGUI() {
        // Setare proprietăți fereastră
        setTitle("New Order - FIX Protocol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Panel principal cu gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Color.MAGENTA, getWidth(), getHeight(), Color.CYAN);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Panel de input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2, 15, 15));
        inputPanel.setOpaque(false);

        // Culoare pentru text field-uri și bordură rotunjită
        Color fieldColor = new Color(230, 230, 250);

        inputPanel.add(new JLabel("ClOrdID:"));
        JTextField clOrdIdField = createRoundedTextField(fieldColor);
        inputPanel.add(clOrdIdField);

        inputPanel.add(new JLabel("Symbol:"));
        JTextField symbolField = createRoundedTextField(fieldColor);
        inputPanel.add(symbolField);

        inputPanel.add(new JLabel("Side (1=Buy, 2=Sell):"));
        JTextField sideField = createRoundedTextField(fieldColor);
        inputPanel.add(sideField);

        inputPanel.add(new JLabel("OrderQty:"));
        JTextField orderQtyField = createRoundedTextField(fieldColor);
        inputPanel.add(orderQtyField);

        inputPanel.add(new JLabel("OrdType (1=Market, 2=Limit):"));
        JTextField ordTypeField = createRoundedTextField(fieldColor);
        inputPanel.add(ordTypeField);

        inputPanel.add(new JLabel("Price (for Limit):"));
        JTextField priceField = createRoundedTextField(fieldColor);
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("TimeInForce (0=Day):"));
        JTextField timeInForceField = createRoundedTextField(fieldColor);
        inputPanel.add(timeInForceField);

        // Panel pentru butoane
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton sendButton = createRoundedButton("Send Order", new Color(255, 105, 180));
        JButton clearButton = createRoundedButton("Clear", new Color(65, 105, 225));

        buttonPanel.add(sendButton);
        buttonPanel.add(clearButton);

        // Adăugare paneluri la fereastră
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Funcționalitate pentru butonul Clear
        clearButton.addActionListener(e -> {
            clOrdIdField.setText("");
            symbolField.setText("");
            sideField.setText("");
            orderQtyField.setText("");
            ordTypeField.setText("");
            priceField.setText("");
            timeInForceField.setText("");
        });

        // Afișare fereastră
        setVisible(true);
    }

    // Creare JTextField cu colțuri rotunjite și fundal colorat
    private JTextField createRoundedTextField(Color backgroundColor) {
        JTextField textField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(backgroundColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }

        };
        textField.setForeground(Color.DARK_GRAY);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return textField;
    }

    // Creare JButton cu colțuri rotunjite și fundal colorat
    private JButton createRoundedButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }

     
        };
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setContentAreaFilled(false);
        return button;
    }

    public void startGUI() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
