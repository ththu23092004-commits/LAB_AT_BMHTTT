
package week_02;

import java.awt.*;
import javax.swing.*;

public class frm_Twofish extends JFrame {

    private JTextArea txt_plaintext;
    private JTextField txt_key;
    private JTextField txt_iv;
    private JTextArea txt_ciphertext;

    private JButton btn_iv;
    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JButton btn_save;
    private JButton btn_open;

    private JLabel lbl_status;

    public frm_Twofish() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setTitle("Twofish Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(760, 700);
        setMinimumSize(new Dimension(650, 600));

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(Color.WHITE);
        main.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JLabel title = new JLabel(
                "MẬT MÃ TWOFISH (CBC)",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Segoe UI", Font.BOLD, 20)
        );

        title.setForeground(new Color(35, 60, 90));
        main.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;

        txt_plaintext = new JTextArea(3, 40);
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);

        txt_key = new JTextField("Twofish@Lab2");

        txt_iv = new JTextField();
        txt_iv.setFont(
                new Font("Consolas", Font.PLAIN, 13)
        );

        txt_ciphertext = new JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);

        Font inputFont = new Font(
                "Segoe UI", Font.PLAIN, 14
        );

        txt_plaintext.setFont(inputFont);
        txt_key.setFont(inputFont);
        txt_ciphertext.setFont(inputFont);

        gbc.gridy = 0;
        form.add(new JLabel("Plaintext:"), gbc);

        gbc.gridy = 1;
        form.add(new JScrollPane(txt_plaintext), gbc);

        gbc.gridy = 2;
        form.add(new JLabel("Key:"), gbc);

        gbc.gridy = 3;
        form.add(txt_key, gbc);

        JLabel keyNote = new JLabel(
                "Mật khẩu bất kỳ (khóa = SHA-256)"
        );

        keyNote.setFont(
                new Font("Segoe UI", Font.ITALIC, 12)
        );

        keyNote.setForeground(Color.GRAY);

        gbc.gridy = 4;
        form.add(keyNote, gbc);

        gbc.gridy = 5;
        form.add(new JLabel("IV (HEX):"), gbc);

        JPanel ivPanel = new JPanel(new BorderLayout(10, 0));
        ivPanel.setBackground(Color.WHITE);

        btn_iv = new JButton("Generate IV");

        ivPanel.add(txt_iv, BorderLayout.CENTER);
        ivPanel.add(btn_iv, BorderLayout.EAST);

        gbc.gridy = 6;
        form.add(ivPanel, gbc);

        gbc.gridy = 7;
        form.add(new JLabel("Ciphertext:"), gbc);

        gbc.gridy = 8;
        form.add(new JScrollPane(txt_ciphertext), gbc);

        main.add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(10, 15));
        bottom.setBackground(Color.WHITE);

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        buttons.setBackground(Color.WHITE);

        btn_encrypt = new JButton("Encrypt");
        btn_decrypt = new JButton("Decrypt");
        btn_save = new JButton("Save File");
        btn_open = new JButton("Open File");

        JButton[] actions = {
            btn_iv,
            btn_encrypt,
            btn_decrypt,
            btn_save,
            btn_open
        };

        for (JButton button : actions) {
            button.setFont(
                    new Font("Segoe UI", Font.BOLD, 13)
            );
            button.setFocusPainted(false);
        }

        JButton[] bottomButtons = {
            btn_encrypt,
            btn_decrypt,
            btn_save,
            btn_open
        };

        for (JButton button : bottomButtons) {
            button.setPreferredSize(new Dimension(130, 36));
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

        lbl_status.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        bottom.add(buttons, BorderLayout.NORTH);
        bottom.add(lbl_status, BorderLayout.SOUTH);

        main.add(bottom, BorderLayout.SOUTH);
        add(main);

        btn_iv.addActionListener(
                evt -> btn_ivActionPerformed()
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

    private void btn_ivActionPerformed() {

        txt_iv.setText(TwofishCipher.newIv());

        lbl_status.setText(
                "Đã tạo IV ngẫu nhiên 16 byte. Giữ nguyên IV này để giải mã."
        );
    }

    private void btn_encryptActionPerformed() {

        String plaintext = txt_plaintext.getText();

        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hãy nhập Plaintext trước khi mã hóa."
            );
            return;
        }

        try {

            String ciphertext = TwofishCipher.encrypt(
                    plaintext,
                    txt_key.getText(),
                    txt_iv.getText()
            );

            txt_ciphertext.setText(ciphertext);

            lbl_status.setText(
                    "<html>Đã mã hóa Twofish. "
                    + HistoryDao.tryInsert(
                            "Twofish",
                            "ENCRYPT",
                            null,
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
                    "Mã hóa thất bại:\n" + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void btn_decryptActionPerformed() {

        String ciphertext = txt_ciphertext.getText();

        if (ciphertext.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hãy nhập Ciphertext trước khi giải mã."
            );
            return;
        }

        try {

            String plaintext = TwofishCipher.decrypt(
                    ciphertext,
                    txt_key.getText(),
                    txt_iv.getText()
            );

            txt_plaintext.setText(plaintext);

            lbl_status.setText(
                    "<html>Đã giải mã Twofish. "
                    + HistoryDao.tryInsert(
                            "Twofish",
                            "DECRYPT",
                            null,
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

        if (txt_ciphertext.getText().isBlank()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Chưa có bản mã để lưu."
            );
            return;
        }

        String content = TwofishCipher.toFile(
                txt_iv.getText(),
                txt_ciphertext.getText()
        );

        String path = FileTextIO.saveString(
                this,
                content,
                "twofish_ciphertext.txt"
        );

        if (path != null) {
            lbl_status.setText(
                    "Đã lưu IV + bản mã vào: " + path
            );
        }
    }

    private void btn_openActionPerformed() {

        String content = FileTextIO.openString(this);

        if (content == null) {
            return;
        }

        try {

            String[] parts = TwofishCipher.fromFile(content);

            txt_iv.setText(parts[0]);
            txt_ciphertext.setText(parts[1]);

            lbl_status.setText(
                    "Đã mở tệp, khôi phục IV và bản mã. Nhập đúng Key rồi bấm Decrypt."
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Tệp không hợp lệ",
                    JOptionPane.ERROR_MESSAGE
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
            new frm_Twofish().setVisible(true);
        });
    }
}
