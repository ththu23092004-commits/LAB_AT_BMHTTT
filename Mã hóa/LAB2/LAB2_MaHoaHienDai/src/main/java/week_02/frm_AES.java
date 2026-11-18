
package week_02;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;

public class frm_AES extends JFrame {

    private String currentUser = null;

    private JTextField txt_username;
    private JPasswordField txt_password;
    private JTextField txt_ticket;
    private JTextArea txt_plaintext;
    private JTextField txt_key;
    private JTextArea txt_ciphertext;

    private JButton btn_sql;
    private JButton btn_register;
    private JButton btn_login;
    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JButton btn_save;
    private JButton btn_open;

    private JLabel lbl_status;

    public frm_AES() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setTitle("AES Cipher - Register / Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 800);
        setMinimumSize(new Dimension(700, 650));

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(Color.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(
                20, 30, 20, 30
        ));

        JLabel title = new JLabel(
                "AES-GCM - ĐĂNG KÝ / ĐĂNG NHẬP (SQL SERVER)",
                SwingConstants.CENTER
        );

        title.setFont(new Font(
                "Segoe UI", Font.BOLD, 19
        ));

        title.setForeground(new Color(35, 60, 90));
        main.add(title, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(
                content, BoxLayout.Y_AXIS
        ));

        content.setBackground(Color.WHITE);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createTitledBorder(
                "ĐĂNG KÝ / ĐĂNG NHẬP"
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txt_username = new JTextField("sinhvien01", 25);
        txt_password = new JPasswordField(25);
        txt_ticket = new JTextField(32);
        txt_ticket.setEditable(false);

        addRow(loginPanel, gbc, 0, "Username:", txt_username);
        addRow(loginPanel, gbc, 1, "Password:", txt_password);

        JLabel passNote = new JLabel("(ít nhất 8 ký tự)");
        passNote.setForeground(Color.GRAY);

        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(passNote, gbc);

        JPanel loginButtons = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 10, 5
        ));

        loginButtons.setBackground(Color.WHITE);

        btn_sql = new JButton("Kết nối SQL Server");
        btn_register = new JButton("Register");
        btn_login = new JButton("Login");

        styleButton(btn_sql);
        styleButton(btn_register);
        styleButton(btn_login);

        loginButtons.add(btn_sql);
        loginButtons.add(btn_register);
        loginButtons.add(btn_login);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(loginButtons, gbc);

        gbc.gridwidth = 1;
        addRow(loginPanel, gbc, 4, "Ticket:", txt_ticket);

        loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(loginPanel);

        content.add(Box.createVerticalStrut(15));

        JPanel aesPanel = new JPanel(new GridBagLayout());
        aesPanel.setBackground(Color.WHITE);
        aesPanel.setBorder(BorderFactory.createTitledBorder(
                "MÃ HÓA AES-256-GCM"
        ));

        GridBagConstraints agbc = new GridBagConstraints();
        agbc.insets = new Insets(7, 8, 7, 8);
        agbc.fill = GridBagConstraints.HORIZONTAL;
        agbc.weightx = 1;

        txt_plaintext = new JTextArea(3, 40);
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);

        txt_key = new JTextField(30);

        txt_ciphertext = new JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);

        addArea(aesPanel, agbc, 0,
                "Plaintext:", txt_plaintext);

        addField(aesPanel, agbc, 2,
                "Key AES:", txt_key);

        JLabel keyNote = new JLabel(
                "Mật khẩu để tạo khóa AES-256"
        );

        keyNote.setFont(new Font(
                "Segoe UI", Font.ITALIC, 12
        ));

        keyNote.setForeground(Color.GRAY);

        agbc.gridx = 0;
        agbc.gridy = 4;
        aesPanel.add(keyNote, agbc);

        addArea(aesPanel, agbc, 5,
                "Ciphertext:", txt_ciphertext);

        aesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(aesPanel);

        JScrollPane contentScroll = new JScrollPane(content);
        contentScroll.setBorder(null);
        contentScroll.getVerticalScrollBar().setUnitIncrement(16);

        main.add(contentScroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(10, 10));
        bottom.setBackground(Color.WHITE);

        JPanel buttons = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 10, 5
        ));

        buttons.setBackground(Color.WHITE);

        btn_encrypt = new JButton("Encrypt");
        btn_decrypt = new JButton("Decrypt");
        btn_save = new JButton("Save File");
        btn_open = new JButton("Open File");

        JButton[] actions = {
            btn_encrypt, btn_decrypt, btn_save, btn_open
        };

        for (JButton button : actions) {
            styleButton(button);
            button.setPreferredSize(new Dimension(130, 35));
            buttons.add(button);
        }

        btn_encrypt.setBackground(new Color(35, 105, 175));
        btn_encrypt.setForeground(Color.WHITE);

        btn_decrypt.setBackground(new Color(40, 140, 90));
        btn_decrypt.setForeground(Color.WHITE);

        lbl_status = new JLabel(
                "Sẵn sàng.",
                SwingConstants.CENTER
        );

        lbl_status.setFont(new Font(
                "Segoe UI", Font.PLAIN, 13
        ));

        bottom.add(buttons, BorderLayout.NORTH);
        bottom.add(lbl_status, BorderLayout.SOUTH);

        main.add(bottom, BorderLayout.SOUTH);
        add(main);

        btn_sql.addActionListener(
                evt -> btn_sqlActionPerformed()
        );

        btn_register.addActionListener(
                evt -> btn_registerActionPerformed()
        );

        btn_login.addActionListener(
                evt -> btn_loginActionPerformed()
        );

        btn_encrypt.addActionListener(
                evt -> btn_encryptActionPerformed()
        );

        btn_decrypt.addActionListener(
                evt -> btn_decryptActionPerformed()
        );

        btn_save.addActionListener(
                evt -> btn_saveActionPerformed()
        );

        btn_open.addActionListener(
                evt -> btn_openActionPerformed()
        );
    }

    private void styleButton(JButton button) {
        button.setFont(new Font(
                "Segoe UI", Font.BOLD, 13
        ));
        button.setFocusPainted(false);
    }

    private void addRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JComponent field) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;

        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panel.add(field, gbc);
    }

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JComponent field) {

        gbc.gridx = 0;
        gbc.gridy = row;

        panel.add(new JLabel(label), gbc);

        gbc.gridy = row + 1;
        panel.add(field, gbc);
    }

    private void addArea(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JTextArea area) {

        addField(
                panel,
                gbc,
                row,
                label,
                new JScrollPane(area)
        );
    }

    private void btn_sqlActionPerformed() {
        new frm_KetNoiSQL().setVisible(true);
    }

    private void btn_registerActionPerformed() {

        try {

            String user = txt_username.getText().trim();

            UserDao.register(
                    user,
                    txt_password.getPassword()
            );

            HistoryDao.tryInsert(
                    "AES", "REGISTER", user, 0, 0
            );

            lbl_status.setText(
                    "Register thành công: "
                    + user
                    + ". Bấm Login để đăng nhập."
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Register thành công: " + user
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Dữ liệu không hợp lệ",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi SQL Server: " + ex.getMessage(),
                    "Lỗi SQL Server",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void btn_loginActionPerformed() {

        currentUser = null;
        txt_ticket.setText("");

        try {

            String user = txt_username.getText().trim();

            if (UserDao.login(
                    user,
                    txt_password.getPassword())) {

                currentUser = user;

                txt_ticket.setText(
                        UserDao.newTicket()
                );

                HistoryDao.tryInsert(
                        "AES", "LOGIN", user, 0, 0
                );

                lbl_status.setText(
                        "Login thành công: "
                        + user
                        + ". Có thể dùng Encrypt/Decrypt."
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Login thành công!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Sai username hoặc mật khẩu.",
                        "Login thất bại",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi SQL Server: " + ex.getMessage(),
                    "Lỗi SQL Server",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void btn_encryptActionPerformed() {

        if (currentUser == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Hãy Login trước khi dùng AES."
            );

            return;
        }

        String plaintext = txt_plaintext.getText();

        if (plaintext.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Hãy nhập Plaintext trước khi mã hóa."
            );

            return;
        }

        try {

            String ciphertext = AESCipher.encrypt(
                    plaintext,
                    txt_key.getText()
            );

            txt_ciphertext.setText(ciphertext);

            lbl_status.setText(
                    "<html>Đã mã hóa AES. "
                    + HistoryDao.tryInsert(
                            "AES",
                            "ENCRYPT",
                            currentUser,
                            plaintext.length(),
                            ciphertext.length()
                    )
                    + "</html>"
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Dữ liệu không hợp lệ",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Mã hóa thất bại:\n"
                    + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void btn_decryptActionPerformed() {

        if (currentUser == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Hãy Login trước khi dùng AES."
            );

            return;
        }

        String ciphertext = txt_ciphertext.getText();

        if (ciphertext.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Hãy nhập Ciphertext trước khi giải mã."
            );

            return;
        }

        try {

            String plaintext = AESCipher.decrypt(
                    ciphertext,
                    txt_key.getText()
            );

            txt_plaintext.setText(plaintext);

            lbl_status.setText(
                    "<html>Đã giải mã AES. "
                    + HistoryDao.tryInsert(
                            "AES",
                            "DECRYPT",
                            currentUser,
                            ciphertext.length(),
                            plaintext.length()
                    )
                    + "</html>"
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Dữ liệu không hợp lệ",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Giải mã thất bại: sai Key hoặc bản mã bị sửa.\n"
                    + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void btn_saveActionPerformed() {

        String path = FileTextIO.saveText(
                this,
                txt_ciphertext,
                "aes_ciphertext.txt"
        );

        if (path != null) {
            lbl_status.setText(
                    "Đã lưu tệp: " + path
            );
        }
    }

    private void btn_openActionPerformed() {

        String path = FileTextIO.openText(
                this,
                txt_ciphertext
        );

        if (path != null) {
            lbl_status.setText(
                    "Đã mở tệp: " + path
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
            new frm_AES().setVisible(true);
        });
    }
}
