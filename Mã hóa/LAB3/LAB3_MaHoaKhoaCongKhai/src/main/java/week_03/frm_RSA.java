
package week_03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class frm_RSA extends JFrame {

    private final RSACipher rsa = new RSACipher();

    private JComboBox<String> cbo_bits;
    private JButton btn_generate;
    private JButton btn_encrypt;
    private JButton btn_decrypt;

    private JTextArea txt_plaintext;
    private JTextArea txt_ciphertext;

    private JLabel lbl_keyinfo;
    private JLabel lbl_status;

    public frm_RSA() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setTitle("RSA Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 650);
        setMinimumSize(new Dimension(700, 580));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JLabel title = new JLabel(
                "MẬT MÃ RSA (OAEP - SHA-256)",
                JLabel.CENTER
        );

        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(35, 65, 110));

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(Color.WHITE);

        JPanel keyPanel = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 12, 10
        ));

        keyPanel.setBackground(new Color(245, 248, 252));
        keyPanel.setBorder(
                BorderFactory.createTitledBorder("Cấu hình khóa RSA")
        );

        JLabel lblBits = new JLabel("Key size:");
        lblBits.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        cbo_bits = new JComboBox<>(
                new String[]{"2048", "3072"}
        );

        cbo_bits.setPreferredSize(new Dimension(100, 30));

        btn_generate = new JButton("Generate Keys");

        btn_generate.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        lbl_keyinfo = new JLabel("Chưa có khóa.");
        lbl_keyinfo.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        keyPanel.add(lblBits);
        keyPanel.add(cbo_bits);
        keyPanel.add(btn_generate);
        keyPanel.add(lbl_keyinfo);

        contentPanel.add(keyPanel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel(new BorderLayout(10, 15));
        textPanel.setBackground(Color.WHITE);

        JPanel plainPanel = new JPanel(new BorderLayout(5, 8));
        plainPanel.setBackground(Color.WHITE);

        JLabel lblPlaintext = new JLabel("Plaintext:");
        lblPlaintext.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        txt_plaintext = new JTextArea(4, 40);
        txt_plaintext.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);
        txt_plaintext.setMargin(
                new java.awt.Insets(10, 10, 10, 10)
        );

        JScrollPane plainScroll = new JScrollPane(txt_plaintext);

        plainPanel.add(lblPlaintext, BorderLayout.NORTH);
        plainPanel.add(plainScroll, BorderLayout.CENTER);

        JPanel cipherPanel = new JPanel(new BorderLayout(5, 8));
        cipherPanel.setBackground(Color.WHITE);

        JLabel lblCiphertext = new JLabel("Ciphertext:");
        lblCiphertext.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        txt_ciphertext = new JTextArea(6, 40);
        txt_ciphertext.setFont(
                new Font("Consolas", Font.PLAIN, 13)
        );

        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);
        txt_ciphertext.setMargin(
                new java.awt.Insets(10, 10, 10, 10)
        );

        JScrollPane cipherScroll = new JScrollPane(txt_ciphertext);

        cipherPanel.add(lblCiphertext, BorderLayout.NORTH);
        cipherPanel.add(cipherScroll, BorderLayout.CENTER);

        textPanel.add(plainPanel, BorderLayout.NORTH);
        textPanel.add(cipherPanel, BorderLayout.CENTER);

        contentPanel.add(textPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 10)
        );

        actionPanel.setBackground(Color.WHITE);

        btn_encrypt = new JButton("Encrypt");
        btn_decrypt = new JButton("Decrypt");

        btn_encrypt.setPreferredSize(new Dimension(130, 35));
        btn_decrypt.setPreferredSize(new Dimension(130, 35));

        btn_encrypt.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        btn_decrypt.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        actionPanel.add(btn_encrypt);
        actionPanel.add(btn_decrypt);

        contentPanel.add(actionPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        lbl_status = new JLabel(
                "Bấm Generate Keys để tạo cặp khóa."
        );

        lbl_status.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        lbl_status.setForeground(new Color(70, 70, 70));
        lbl_status.setBorder(
                BorderFactory.createEmptyBorder(10, 5, 5, 5)
        );

        mainPanel.add(lbl_status, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        btn_generate.addActionListener(e -> generateKeys());
        btn_encrypt.addActionListener(e -> encryptText());
        btn_decrypt.addActionListener(e -> decryptText());
    }

    private void generateKeys() {

        int bits = Integer.parseInt(
                (String) cbo_bits.getSelectedItem()
        );

        try {
            long start = System.currentTimeMillis();

            rsa.generateKeys(bits);

            long ms = System.currentTimeMillis() - start;

            lbl_keyinfo.setText(
                    "RSA-" + bits
                    + ": tạo khóa " + ms
                    + " ms, tối đa "
                    + rsa.maxPlaintextBytes()
                    + " byte"
            );

            lbl_status.setText(
                    "Đã tạo cặp khóa. Khóa công khai dùng để mã hóa, "
                    + "khóa riêng để giải mã."
            );

            lbl_status.setForeground(new Color(0, 130, 65));

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Không tạo được khóa: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void encryptText() {

        String plaintext = txt_plaintext.getText();

        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hãy nhập Plaintext trước khi mã hóa."
            );
            return;
        }

        try {
            String ciphertext = rsa.encrypt(plaintext);

            txt_ciphertext.setText(ciphertext);
            txt_ciphertext.setCaretPosition(0);

            lbl_status.setText(
                    "Đã mã hóa RSA ("
                    + ciphertext.length()
                    + " ký tự Base64). "
                    + HistoryDao.tryInsert(
                            "RSA",
                            "ENCRYPT",
                            plaintext.length(),
                            true
                    )
            );

            lbl_status.setForeground(new Color(0, 130, 65));

        } catch (Exception ex) {

            String history = HistoryDao.tryInsert(
                    "RSA",
                    "ENCRYPT",
                    plaintext.length(),
                    false
            );

            lbl_status.setText(
                    "Mã hóa RSA thất bại. " + history
            );

            lbl_status.setForeground(Color.RED);

            JOptionPane.showMessageDialog(
                    this,
                    "Mã hóa thất bại: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void decryptText() {

        String ciphertext = txt_ciphertext.getText();

        if (ciphertext.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hãy nhập Ciphertext trước khi giải mã."
            );
            return;
        }

        try {
            String plaintext = rsa.decrypt(ciphertext);

            txt_plaintext.setText(plaintext);
            txt_plaintext.setCaretPosition(0);

            lbl_status.setText(
                    "Đã giải mã RSA. "
                    + HistoryDao.tryInsert(
                            "RSA",
                            "DECRYPT",
                            ciphertext.length(),
                            true
                    )
            );

            lbl_status.setForeground(new Color(0, 130, 65));

        } catch (Exception ex) {

            HistoryDao.tryInsert(
                    "RSA",
                    "DECRYPT",
                    ciphertext.length(),
                    false
            );

            lbl_status.setText(
                    "Giải mã RSA THẤT BẠI "
                    + "(sai khóa hoặc bản mã bị sửa)."
            );

            lbl_status.setForeground(Color.RED);

            JOptionPane.showMessageDialog(
                    this,
                    "Giải mã thất bại: sai khóa riêng "
                    + "hoặc bản mã bị sửa.\n"
                    + ex.getMessage(),
                    "Giải mã thất bại",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new frm_RSA().setVisible(true);
        });
    }
}
