
package week_02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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
        setSize(570, 510);
        setMinimumSize(new java.awt.Dimension(500, 470));

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(Color.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel(
                "KẾT NỐI MICROSOFT SQL SERVER",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Segoe UI", Font.BOLD, 19));
        title.setForeground(new Color(35, 60, 90));

        main.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txt_server = new JTextField("127.0.0.1", 22);
        txt_port = new JTextField("1433", 22);
        txt_database = new JTextField("LAB2_MaHoaHienDai", 22);
        txt_user = new JTextField("lab2_user", 22);
        txt_password = new JPasswordField(22);

        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        txt_server.setFont(inputFont);
        txt_port.setFont(inputFont);
        txt_database.setFont(inputFont);
        txt_user.setFont(inputFont);
        txt_password.setFont(inputFont);

        addRow(form, gbc, 0, "Server:", txt_server);
        addRow(form, gbc, 1, "Port:", txt_port);
        addRow(form, gbc, 2, "Database:", txt_database);
        addRow(form, gbc, 3, "User:", txt_user);
        addRow(form, gbc, 4, "Password:", txt_password);

        main.add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(10, 15));
        bottom.setBackground(Color.WHITE);

        JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);

        btn_test = new JButton("Test Connection");
        btn_history = new JButton("Xem lịch sử");

        btn_test.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn_history.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        btn_test.setBackground(new Color(35, 105, 175));
        btn_test.setForeground(Color.WHITE);
        btn_test.setFocusPainted(false);

        btn_history.setFocusPainted(false);

        buttons.add(btn_test);
        buttons.add(btn_history);

        lbl_status = new JLabel(
                "Chưa kết nối.",
                SwingConstants.CENTER
        );

        lbl_status.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        lbl_status.setForeground(new Color(100, 100, 100));

        bottom.add(buttons, BorderLayout.NORTH);
        bottom.add(lbl_status, BorderLayout.CENTER);

        main.add(bottom, BorderLayout.SOUTH);

        add(main);

        btn_test.addActionListener(
                (ActionEvent evt) -> btn_testActionPerformed(evt)
        );

        btn_history.addActionListener(
                (ActionEvent evt) -> btn_historyActionPerformed(evt)
        );
    }

    private void addRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            javax.swing.JComponent component) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panel.add(component, gbc);
    }

    private void btn_testActionPerformed(ActionEvent evt) {

        btn_test.setEnabled(false);
        lbl_status.setForeground(new Color(100, 100, 100));
        lbl_status.setText("Đang kiểm tra kết nối...");

        SwingUtilities.invokeLater(() -> {

            try {
                int port = Integer.parseInt(
                        txt_port.getText().trim()
                );

                DbConfig.set(
                        txt_server.getText().trim(),
                        port,
                        txt_database.getText().trim(),
                        txt_user.getText().trim(),
                        txt_password.getPassword()
                );

                String result = DbConfig.test();

                lbl_status.setForeground(
                        new Color(0, 130, 60)
                );

                lbl_status.setText(
                        "<html><center>"
                        + result
                        + "</center></html>"
                );

                JOptionPane.showMessageDialog(
                        this,
                        result,
                        "Kết nối thành công",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception ex) {

                DbConfig.clear();

                lbl_status.setForeground(Color.RED);

                lbl_status.setText(
                        "<html><center>Kết nối thất bại: "
                        + ex.getMessage()
                        + "</center></html>"
                );

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Không kết nối được SQL Server",
                        JOptionPane.ERROR_MESSAGE
                );

            } finally {
                btn_test.setEnabled(true);
            }
        });
    }

    private void btn_historyActionPerformed(ActionEvent evt) {

        try {
            Class<?> historyClass = Class.forName(
                    "week_02.frm_LichSu"
            );

            JFrame historyForm = (JFrame) historyClass
                    .getDeclaredConstructor()
                    .newInstance();

            historyForm.setVisible(true);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Chưa tạo form frm_LichSu.\n"
                    + "Form này sẽ được tạo ở bước tiếp theo.",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ex) {
        }

        SwingUtilities.invokeLater(() -> {
            new frm_KetNoiSQL().setVisible(true);
        });
    }
}
