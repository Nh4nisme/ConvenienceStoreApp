package Components;

import javax.swing.*;
import java.awt.*;

public class RoundedInputField extends JTextField {
    private int arc;

    public RoundedInputField(int arc, boolean isPassword) {
        super();
        this.arc = arc;

        if (isPassword) {
            // Đổi thành echo char để thành password field
            setEchoChar('*');
        }

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private void setEchoChar(char c) {
        // Biến JTextField thành dạng password
        putClientProperty("JPasswordField.echoChar", c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
}
