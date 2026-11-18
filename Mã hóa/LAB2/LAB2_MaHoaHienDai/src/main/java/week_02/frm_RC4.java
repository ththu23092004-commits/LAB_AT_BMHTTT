
package week_02;

import java.awt.*;
import javax.swing.*;

public class frm_RC4 extends JFrame {

    private JTextArea txt_plaintext;
    private JTextField txt_key;
    private JTextArea txt_ciphertext;

    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JButton btn_save;
    private JButton btn_open;

    private JLabel lbl_status;

    public frm_RC4() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setTitle("RC4 Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(720, 620);
        setMinimumSize(new Dimension(620, 550));

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(Color.WHITE);
        main.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JLabel title = new JLabel(
                "MẬT MÃ DÒNG RC4",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
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
        txt_plaintext.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        txt_key = new JTextField("Key");
        txt_key.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        txt_ciphertext = new JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);
        txt_ciphertext.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        gbc.gridy = 0;
        form.add(new JLabel("Plaintext:"), gbc);

        gbc.gridy = 1;
        form.add(new JScrollPane(txt_plaintext), gbc);

        gbc.gridy = 2;
        form.add(new JLabel("Key:"), gbc);

        gbc.gridy = 3;
        form.add(txt_key, gbc);

        JLabel keyNote = new JLabel(
                "1 - 256 byte; bản mã hiển thị dạng HEX"
        );

        keyNote.setFont(
                new Font("Segoe UI", Font.ITALIC, 12)
        );

        keyNote.setForeground(Color.GRAY);

        gbc.gridy = 4;
        form.add(keyNote, gbc);

        gbc.gridy = 5;
        form.add(new JLabel("Ciphertext:"), gbc);

        gbc.gridy = 6;
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
            btn_encrypt, btn_decrypt, btn_save, btn_open
        };

        for (JButton button : actions) {
            button.setFont(
                    new Font("Segoe UI", Font.BOLD, 13)
            );
            button.setPreferredSize(new Dimension(130, 36));
            button.setFocusPainted(false);
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
            String ciphertext = RC4Cipher.encrypt(
                    plaintext,
                    txt_key.getText()
            );

            txt_ciphertext.setText(ciphertext);

            lbl_status.setText(
                    "<html>Đã mã hóa RC4. "
                    + HistoryDao.tryInsert(
                            "RC4",
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
            String plaintext = RC4Cipher.decrypt(
                    ciphertext,
                    txt_key.getText()
            );

            txt_plaintext.setText(plaintext);

            lbl_status.setText(
                    "<html>Đã giải mã RC4. "
                    + HistoryDao.tryInsert(
                            "RC4",
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
                    "Giải mã thất bại:\n" + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void btn_saveActionPerformed() {

        String path = FileTextIO.saveText(
                this,
                txt_ciphertext,
                "rc4_ciphertext.txt"
        );

        if (path != null) {
            lbl_status.setText("Đã lưu tệp: " + path);
        }
    }

    private void btn_openActionPerformed() {

        String path = FileTextIO.openText(
                this,
                txt_ciphertext
        );

        if (path != null) {
            lbl_status.setText("Đã mở tệp: " + path);
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
            new frm_RC4().setVisible(true);
        });
    }
}
