
package week_02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class frm_DES extends JFrame {

    private JTextArea txt_plaintext;
    private JTextField txt_key;
    private JTextArea txt_ciphertext;

    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JButton btn_save;
    private JButton btn_open;

    private JLabel lbl_status;

    public frm_DES() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setTitle("DES Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(720, 620);
        setMinimumSize(new Dimension(620, 550));

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(Color.WHITE);
        main.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JLabel title = new JLabel(
                "MẬT MÃ DES",
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

        Font labelFont = new Font(
                "Segoe UI", Font.PLAIN, 14
        );

        Font inputFont = new Font(
                "Segoe UI", Font.PLAIN, 14
        );

        txt_plaintext = new JTextArea(3, 40);

        txt_plaintext.setFont(inputFont);
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);

        JScrollPane scrollPlain = new JScrollPane(
                txt_plaintext
        );

        scrollPlain.setPreferredSize(
                new Dimension(570, 90)
        );

        txt_key = new JTextField("12345678");

        txt_key.setFont(inputFont);
        txt_key.setPreferredSize(
                new Dimension(300, 30)
        );

        txt_ciphertext = new JTextArea(3, 40);

        txt_ciphertext.setFont(inputFont);
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);

        JScrollPane scrollCipher = new JScrollPane(
                txt_ciphertext
        );

        scrollCipher.setPreferredSize(
                new Dimension(570, 90)
        );

        JLabel lblPlain = new JLabel("Plaintext:");
        lblPlain.setFont(labelFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(lblPlain, gbc);

        gbc.gridy = 1;
        form.add(scrollPlain, gbc);

        JLabel lblKey = new JLabel("Key:");
        lblKey.setFont(labelFont);

        gbc.gridy = 2;
        form.add(lblKey, gbc);

        gbc.gridy = 3;
        form.add(txt_key, gbc);

        JLabel lblKeyNote = new JLabel(
                "Đúng 8 ký tự không dấu (8 byte)"
        );

        lblKeyNote.setFont(
                new Font("Segoe UI", Font.ITALIC, 12)
        );

        lblKeyNote.setForeground(Color.GRAY);

        gbc.gridy = 4;
        form.add(lblKeyNote, gbc);

        JLabel lblCipher = new JLabel("Ciphertext:");
        lblCipher.setFont(labelFont);

        gbc.gridy = 5;
        form.add(lblCipher, gbc);

        gbc.gridy = 6;
        form.add(scrollCipher, gbc);

        main.add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(
                new BorderLayout(10, 15)
        );

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
            btn_encrypt,
            btn_decrypt,
            btn_save,
            btn_open
        };

        for (JButton button : allButtons) {

            button.setFont(
                    new Font("Segoe UI", Font.BOLD, 13)
            );

            button.setPreferredSize(
                    new Dimension(130, 36)
            );

            button.setFocusPainted(false);

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

        lbl_status.setForeground(
                new Color(80, 80, 80)
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

            String ciphertext = DESCipher.encrypt(
                    plaintext,
                    txt_key.getText()
            );

            txt_ciphertext.setText(ciphertext);

            lbl_status.setText(
                    "<html>Đã mã hóa DES. "
                    + HistoryDao.tryInsert(
                            "DES",
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
                    "Mã hóa thất bại:\n"
                    + ex.getMessage(),
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

            String plaintext = DESCipher.decrypt(
                    ciphertext,
                    txt_key.getText()
            );

            txt_plaintext.setText(plaintext);

            lbl_status.setText(
                    "<html>Đã giải mã DES. "
                    + HistoryDao.tryInsert(
                            "DES",
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

        String path = FileTextIO.saveText(
                this,
                txt_ciphertext,
                "des_ciphertext.txt"
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
            new frm_DES().setVisible(true);
        });
    }
}
