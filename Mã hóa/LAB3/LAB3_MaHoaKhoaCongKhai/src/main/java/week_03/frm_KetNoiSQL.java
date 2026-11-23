
package week_03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class frm_KetNoiSQL extends JFrame {

    private JTextField txt_server;
    private JTextField txt_port;
    private JTextField txt_database;
    private JTextField txt_user;
    private JPasswordField txt_password;

    private JButton btn_test;
    private JButton btn_history;
    private JLabel lbl_status;

    public frm_KetNoiSQL() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Kết nối Microsoft SQL Server");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 470);
        setMinimumSize(new java.awt.Dimension(550, 440));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout(15, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        JLabel title = new JLabel(
                "KẾT NỐI MICROSOFT SQL SERVER",
                JLabel.CENTER
        );

        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(35, 65, 110));

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txt_server = new JTextField("127.0.0.1", 24);
        txt_port = new JTextField("14333", 24);
        txt_database = new JTextField(
                "LAB3_MaHoaKhoaCongKhai", 24
        );
        txt_user = new JTextField("lab3_user", 24);
        txt_password = new JPasswordField(24);

        addField(formPanel, gbc, 0, "Server:", txt_server);
        addField(formPanel, gbc, 1, "Port:", txt_port);
        addField(formPanel, gbc, 2, "Database:", txt_database);
        addField(formPanel, gbc, 3, "User:", txt_user);
        addField(formPanel, gbc, 4, "Password:", txt_password);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 15));
        bottomPanel.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        btn_test = new JButton("Test Connection");
        btn_history = new JButton("Xem lịch sử");

        btn_test.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn_history.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        buttonPanel.add(btn_test);
        buttonPanel.add(btn_history);

        lbl_status = new JLabel("Chưa kết nối.");
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_status.setForeground(Color.GRAY);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(lbl_status, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        btn_test.addActionListener(e -> testConnection());

        btn_history.addActionListener(e -> {
            new frm_LichSu().setVisible(true);
        });
    }

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            javax.swing.JComponent field) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        panel.add(field, gbc);
    }

    private void testConnection() {
        char[] enteredPassword = txt_password.getPassword();

        try {
            int port = Integer.parseInt(
                    txt_port.getText().trim()
            );

            DbConfig.set(
                    txt_server.getText().trim(),
                    port,
                    txt_database.getText().trim(),
                    txt_user.getText().trim(),
                    enteredPassword
            );

            String result = DbConfig.test();

            lbl_status.setText("<html>" + result + "</html>");
            lbl_status.setForeground(new Color(0, 130, 65));

            JOptionPane.showMessageDialog(
                    this,
                    result,
                    "Kết nối thành công",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            DbConfig.clear();

            lbl_status.setText(
                    "<html>Kết nối thất bại: "
                    + ex.getMessage()
                    + "</html>"
            );

            lbl_status.setForeground(Color.RED);

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Không kết nối được SQL Server",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            Arrays.fill(enteredPassword, '\0');
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new frm_KetNoiSQL().setVisible(true);
        });
    }
}
