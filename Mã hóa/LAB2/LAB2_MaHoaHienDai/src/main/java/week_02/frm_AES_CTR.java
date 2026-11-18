
package week_02;

import java.awt.*;
import javax.swing.*;

public class frm_AES_CTR extends JFrame {

    private JTextArea txt_plaintext;
    private JTextField txt_key;
    private JTextField txt_nonce;
    private JTextArea txt_ciphertext;

    private JButton btn_genkey;
    private JButton btn_gennonce;
    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JButton btn_save;
    private JButton btn_open;

    private JLabel lbl_status;

    public frm_AES_CTR() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setTitle("AES-CTR Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(780, 700);
        setMinimumSize(new Dimension(650, 600));

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(Color.WHITE);
        main.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JLabel title = new JLabel(
                "BÀI NÂNG CAO 01 - AES-256 CHẾ ĐỘ CTR",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Segoe UI", Font.BOLD, 19)
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

        txt_key = new JTextField();
        txt_nonce = new JTextField();

        txt_ciphertext = new JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);

        Font inputFont = new Font(
                "Segoe UI", Font.PLAIN, 14
        );

        txt_plaintext.setFont(inputFont);
        txt_key.setFont(new Font(
                "Consolas", Font.PLAIN, 13
        ));
        txt_nonce.setFont(new Font(
                "Consolas", Font.PLAIN, 13
        ));
        txt_ciphertext.setFont(inputFont);

        gbc.gridy = 0;
        form.add(new JLabel("Plaintext:"), gbc);

        gbc.gridy = 1;
        form.add(new JScrollPane(txt_plaintext), gbc);

        gbc.gridy = 2;
        form.add(new JLabel("Key (Base64):"), gbc);

        JPanel keyPanel = new JPanel(new BorderLayout(10, 0));
        keyPanel.setBackground(Color.WHITE);

        btn_genkey = new JButton("Generate Key");

        keyPanel.add(txt_key, BorderLayout.CENTER);
        keyPanel.add(btn_genkey, BorderLayout.EAST);

        gbc.gridy = 3;
        form.add(keyPanel, gbc);

        gbc.gridy = 4;
        form.add(new JLabel("Nonce (Base64):"), gbc);

        JPanel noncePanel = new JPanel(new BorderLayout(10, 0));
        noncePanel.setBackground(Color.WHITE);

        btn_gennonce = new JButton("Generate Nonce");

        noncePanel.add(txt_nonce, BorderLayout.CENTER);
        noncePanel.add(btn_gennonce, BorderLayout.EAST);

        gbc.gridy = 5;
        form.add(noncePanel, gbc);

        gbc.gridy = 6;
        form.add(new JLabel("Ciphertext:"), gbc);

        gbc.gridy = 7;
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

        JButton[] allButtons = {
            btn_genkey,
            btn_gennonce,
            btn_encrypt,
            btn_decrypt,
            btn_save,
            btn_open
        };

        for (JButton button : allButtons) {

            button.setFont(
                    new Font("Segoe UI", Font.BOLD, 13)
            );

            button.setFocusPainted(false);
        }

        JButton[] actionButtons = {
            btn_encrypt,
            btn_decrypt,
            btn_save,
            btn_open
        };

        for (JButton button : actionButtons) {
            button.setPreferredSize(
                    new Dimension(130, 36)
            );
            buttons.add(button);
        }

        btn_encrypt.setBackground(
                new Color(35, 105, 175)
        );

        btn_encrypt.setForeground(Color.WHITE);

        btn_decrypt.setBackground(
                new Color(40, 140, 90)
        );

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

        btn_genkey.addActionListener(
                evt -> btn_genkeyActionPerformed()
        );

        btn_gennonce.addActionListener(
                evt -> btn_gennonceActionPerformed()
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

    private void btn_genkeyActionPerformed() {

        txt_key.setText(AESCTRCipher.newKey());

        lbl_status.setText(
                "Đã tạo khóa ngẫu nhiên 256 bit."
        );
    }

    private void btn_gennonceActionPerformed() {

        txt_nonce.setText(AESCTRCipher.newNonce());

        lbl_status.setText(
                "Đã tạo nonce ngẫu nhiên 96 bit. Mỗi lần mã hóa nên tạo nonce mới."
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

            String ciphertext = AESCTRCipher.encrypt(
                    plaintext,
                    txt_key.getText(),
                    txt_nonce.getText()
            );

            txt_ciphertext.setText(ciphertext);

            lbl_status.setText(
                    "<html>Đã mã hóa AES-CTR. "
                    + HistoryDao.tryInsert(
                            "AES-CTR",
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

            String plaintext = AESCTRCipher.decrypt(
                    ciphertext,
                    txt_key.getText(),
                    txt_nonce.getText()
            );

            txt_plaintext.setText(plaintext);

            lbl_status.setText(
                    "<html>Đã giải mã AES-CTR. "
                    + HistoryDao.tryInsert(
                            "AES-CTR",
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

        String content = AESCTRCipher.toFile(
                txt_nonce.getText(),
                txt_ciphertext.getText()
        );

        String path = FileTextIO.saveString(
                this,
                content,
                "aes_ctr_ciphertext.txt"
        );

        if (path != null) {

            lbl_status.setText(
                    "Đã lưu nonce + bản mã (không lưu Key) vào: "
                    + path
            );
        }
    }

    private void btn_openActionPerformed() {

        String content = FileTextIO.openString(this);

        if (content == null) {
            return;
        }

        try {

            String[] parts = AESCTRCipher.fromFile(
                    content
            );

            txt_nonce.setText(parts[0]);
            txt_ciphertext.setText(parts[1]);

            lbl_status.setText(
                    "Đã mở tệp. Nhập đúng Key rồi bấm Decrypt."
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
            new frm_AES_CTR().setVisible(true);
        });
    }
}
