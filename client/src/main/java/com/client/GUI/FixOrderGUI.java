package com.client.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.client.WebSocket;
import com.client.model.FixMessage;
import com.client.template.FixMessageGenerator;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FixOrderGUI extends JFrame {

    private JTextField clOrdIdField;
    private JTextField symbolField;
    private JTextField sideField;
    private JTextField orderQtyField;
    private JTextField ordTypeField;
    private JTextField priceField;
    private JTextField timeInForceField;
    private JLabel statusLabel;
    private JTextField deliverToCompIDField;

    private WebSocket webSocket;
    private FixMessageGenerator message;

    public FixOrderGUI(WebSocket webSocket) {
        setTitle("New Order - FIX Protocol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        this.webSocket = webSocket;

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

        statusLabel = new JLabel("");
        statusLabel.setForeground(Color.DARK_GRAY);
        mainPanel.add(statusLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2, 15, 15));
        inputPanel.setOpaque(false);

        Color fieldColor = new Color(230, 230, 250);

        inputPanel.add(new JLabel("ClOrdID:"));
        clOrdIdField = createRoundedTextField(fieldColor);
        inputPanel.add(clOrdIdField);

        inputPanel.add(new JLabel("Symbol:"));
        symbolField = createRoundedTextField(fieldColor);
        inputPanel.add(symbolField);

        inputPanel.add(new JLabel("Side (1=Buy, 2=Sell):"));
        sideField = createRoundedTextField(fieldColor);
        inputPanel.add(sideField);

        inputPanel.add(new JLabel("OrderQty:"));
        orderQtyField = createRoundedTextField(fieldColor);
        inputPanel.add(orderQtyField);

        inputPanel.add(new JLabel("OrdType (1=Market, 2=Limit):"));
        ordTypeField = createRoundedTextField(fieldColor);
        inputPanel.add(ordTypeField);

        inputPanel.add(new JLabel("Price (for Limit):"));
        priceField = createRoundedTextField(fieldColor);
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("DeliverToCompID (Tag 128):"));
        deliverToCompIDField = createRoundedTextField(fieldColor);
        inputPanel.add(deliverToCompIDField);

        inputPanel.add(new JLabel("TimeInForce (0=Day):"));
        timeInForceField = createRoundedTextField(fieldColor);
        inputPanel.add(timeInForceField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton sendButton = createRoundedButton("Send Order", new Color(255, 105, 180));
        JButton clearButton = createRoundedButton("Clear", new Color(65, 105, 225));

        buttonPanel.add(sendButton);
        buttonPanel.add(clearButton);

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        message = new FixMessageGenerator();
        
        sendButton.addActionListener(e -> processOrderFields());

        clearButton.addActionListener(e -> {
            clOrdIdField.setText("");
            symbolField.setText("");
            sideField.setText("");
            orderQtyField.setText("");
            ordTypeField.setText("");
            priceField.setText("");
            timeInForceField.setText("");
            deliverToCompIDField.setText("");
        });

        setVisible(true);
    }

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

    private void processOrderFields() {
        String clOrdId = clOrdIdField.getText();
        String symbol = symbolField.getText();
        String side = sideField.getText();
        String orderQty = orderQtyField.getText();
        String ordType = ordTypeField.getText();
        String price = priceField.getText();
        String timeInForce = timeInForceField.getText();
        String deliverToCompID = deliverToCompIDField.getText();

        int seqNum = webSocket.returnTag34() + 1;

        FixMessageGenerator fixMessageGenerator = new FixMessageGenerator();
        FixMessage fixMessage = new FixMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm");

        fixMessage.addField(8, "FIX.4.2");
        fixMessage.addField(9, "");
        fixMessage.addField(35, "D");
        fixMessage.addField(49, webSocket.returnTag49());
        fixMessage.addField(56, "AXFix");
        fixMessage.addField(52, LocalDateTime.now().format(formatter));
        fixMessage.addField(11, clOrdId);
        fixMessage.addField(34, String.valueOf(seqNum));
        fixMessage.addField(54, side);
        fixMessage.addField(55, symbol);
        fixMessage.addField(38, orderQty);
        fixMessage.addField(40, ordType);
        fixMessage.addField(44, price);
        fixMessage.addField(58, timeInForce);
        fixMessage.addField(128, deliverToCompID);
        fixMessage.addField(10, fixMessageGenerator.generateCheckSum(fixMessage));

        int bodyLength = fixMessageGenerator.calculateBodyLength(fixMessage);
        fixMessage.addField(9, String.valueOf(bodyLength));
        
        String finalMessage = fixMessage.buildFixMessage();
        try {
            webSocket.sendMessage(finalMessage);
            statusLabel.setText("Order sent successfully!");
            statusLabel.setForeground(Color.GREEN);
        } catch (Exception e) {
            statusLabel.setText("Failed to send order.");
            statusLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }

    public void startGUI() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
