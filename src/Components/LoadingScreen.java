package Components;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JDialog {
    private JLabel lblLoading;
    private JProgressBar progressBar;

    public LoadingScreen(Frame parent) {
        super(parent, "Đang đăng nhập", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        lblLoading = new JLabel("Đang đăng nhập...", SwingConstants.CENTER);
        lblLoading.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblLoading.setForeground(new Color(67, 141, 184));

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);  // Chế độ chạy không xác định
        progressBar.setPreferredSize(new Dimension(250, 20));

        add(lblLoading, BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);
    }
}

