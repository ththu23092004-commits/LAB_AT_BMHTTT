
package week_03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class frm_Menu extends JFrame {

    private JButton btn_rsa;
    private JButton btn_ecc;
    private JButton btn_rsaaes;
    private JButton btn_signature;
    private JButton btn_sql;
    private JButton btn_history;

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setTitle("LAB 3 - Mã hóa khóa công khai");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setMinimumSize(new Dimension(620, 530));

        JPanel mainPanel = new JPanel(new BorderLayout(20, 25));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(35, 55, 35, 55)
        );

        JPanel headerPanel = new JPanel(
                new GridLayout(2, 1, 5, 10)
        );

        headerPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel(
                "LAB 3 - MÃ HÓA KHÓA CÔNG KHAI",
                JLabel.CENTER
        );

        title.setFont(
                new Font("Segoe UI", Font.BOLD, 22)
        );

        title.setForeground(new Color(35, 65, 110));

        JLabel subtitle = new JLabel(
                "Chọn một bài để mở form tương ứng "
                + "(bài 3.1 chạy RSADemo.java bằng Run File):",
                JLabel.CENTER
        );

        subtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        subtitle.setForeground(new Color(90, 90, 90));

        headerPanel.add(title);
        headerPanel.add(subtitle);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(
                new GridLayout(6, 1, 0, 12)
        );

        buttonPanel.setBackground(Color.WHITE);

        btn_rsa = new JButton("3.2 RSA (Swing)");
        btn_ecc = new JButton("3.3 ECC");
        btn_rsaaes = new JButton("3.4 RSA + AES");
        btn_signature = new JButton("3.5 Chữ ký ECDSA");
        btn_sql = new JButton("Kết nối SQL Server");
        btn_history = new JButton("Xem lịch sử");

        JButton[] buttons = {
            btn_rsa,
            btn_ecc,
            btn_rsaaes,
            btn_signature,
            btn_sql,
            btn_history
        };

        for (JButton button : buttons) {
            button.setFont(
                    new Font("Segoe UI", Font.BOLD, 14)
            );

            button.setFocusPainted(false);
            button.setBackground(new Color(245, 248, 252));
            button.setForeground(new Color(35, 65, 110));

            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel(
                "RSA - ECC - RSA + AES - ECDSA - SQL Server",
                JLabel.CENTER
        );

        footer.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        footer.setForeground(Color.GRAY);

        mainPanel.add(footer, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        btn_rsa.addActionListener(e -> {
            new frm_RSA().setVisible(true);
        });

        btn_ecc.addActionListener(e -> {
            new frm_ECC().setVisible(true);
        });

        btn_rsaaes.addActionListener(e -> {
            new frm_RSA_AES().setVisible(true);
        });

        btn_signature.addActionListener(e -> {
            new frm_ECC_Signature().setVisible(true);
        });

        btn_sql.addActionListener(e -> {
            new frm_KetNoiSQL().setVisible(true);
        });

        btn_history.addActionListener(e -> {
            new frm_LichSu().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new frm_Menu().setVisible(true);
        });
    }
}
