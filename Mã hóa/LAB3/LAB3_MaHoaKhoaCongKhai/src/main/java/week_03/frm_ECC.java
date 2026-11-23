
package week_03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class frm_ECC extends JFrame {

    private final ECCCipher ecc = new ECCCipher();

    private JTextArea txt_public;
    private JTextArea txt_private;
    private JTextArea txt_plaintext;
    private JTextArea txt_ciphertext;

    private JButton btn_generate;
    private JButton btn_savekeys;
    private JButton btn_loadkeys;
    private JButton btn_encrypt;
    private JButton btn_decrypt;

    private JLabel lbl_status;

    public frm_ECC() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setTitle("ECC Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 780);
        setMinimumSize(new Dimension(750, 650));

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel title = new JLabel(
                "MẬT MÃ ĐƯỜNG CONG ELLIPTIC (ECC - secp256r1)",
                JLabel.CENTER
        );

        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(35, 65, 110));

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 15));
        contentPanel.setBackground(Color.WHITE);

        JPanel keyPanel = new JPanel(new BorderLayout(8, 10));
        keyPanel.setBackground(Color.WHITE);
        keyPanel.setBorder(
                BorderFactory.createTitledBorder("Cặp khóa ECC")
        );

        JPanel keyTextPanel = new JPanel(new GridLayout(2, 1, 8, 10));
        keyTextPanel.setBackground(Color.WHITE);

        txt_public = new JTextArea(2, 40);
        txt_private = new JTextArea(2, 40);

        configureTextArea(txt_public, false);
        configureTextArea(txt_private, false);

        keyTextPanel.add(
                createTextPanel("Public key:", txt_public)
        );

        keyTextPanel.add(
                createTextPanel("Private key:", txt_private)
        );

        keyPanel.add(keyTextPanel, BorderLayout.CENTER);

        JPanel keyButtons = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 12, 5)
        );

        keyButtons.setBackground(Color.WHITE);

        btn_generate = new JButton("Generate Keys");
        btn_savekeys = new JButton("Save Keys");
        btn_loadkeys = new JButton("Load Keys");

        keyButtons.add(btn_generate);
        keyButtons.add(btn_savekeys);
        keyButtons.add(btn_loadkeys);

        keyPanel.add(keyButtons, BorderLayout.SOUTH);

        contentPanel.add(keyPanel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 10, 15));
        textPanel.setBackground(Color.WHITE);

        txt_plaintext = new JTextArea(3, 40);
        txt_ciphertext = new JTextArea(5, 40);

        configureTextArea(txt_plaintext, true);
        configureTextArea(txt_ciphertext, true);

        textPanel.add(
                createTextPanel("Plaintext:", txt_plaintext)
        );

        textPanel.add(
                createTextPanel("Ciphertext:", txt_ciphertext)
        );

        contentPanel.add(textPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 8)
        );

        actionPanel.setBackground(Color.WHITE);

        btn_encrypt = new JButton("Encrypt");
        btn_decrypt = new JButton("Decrypt");

        btn_encrypt.setPreferredSize(new Dimension(130, 35));
        btn_decrypt.setPreferredSize(new Dimension(130, 35));

        actionPanel.add(btn_encrypt);
        actionPanel.add(btn_decrypt);

        contentPanel.add(actionPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        lbl_status = new JLabel(
                "Bấm Generate Keys hoặc Load Keys."
        );

        lbl_status.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        lbl_status.setForeground(new Color(70, 70, 70));

        mainPanel.add(lbl_status, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        btn_generate.addActionListener(e -> generateKeys());
        btn_savekeys.addActionListener(e -> saveKeys());
        btn_loadkeys.addActionListener(e -> loadKeys());
        btn_encrypt.addActionListener(e -> encryptText());
        btn_decrypt.addActionListener(e -> decryptText());
    }

    private void configureTextArea(
            JTextArea area,
            boolean editable) {

        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(editable);

        area.setMargin(
                new java.awt.Insets(8, 8, 8, 8)
        );

        if (!editable) {
            area.setBackground(new Color(245, 248, 252));
        }
    }

    private JPanel createTextPanel(
            String label,
            JTextArea area) {

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(label);
        lbl.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        return panel;
    }

    private void showKeys() {
        txt_public.setText(ecc.getPublicKeyBase64());
        txt_private.setText(ecc.getPrivateKeyBase64());

        txt_public.setCaretPosition(0);
        txt_private.setCaretPosition(0);
    }

    private void generateKeys() {
        try {
            ecc.generateKeys();

            showKeys();

            lbl_status.setText(
                    "Đã tạo cặp khóa mới. "
                    + "Bản mã cũ sẽ không giải mã được bằng khóa mới."
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

    private void saveKeys() {

        if (txt_public.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Chưa có khóa để lưu."
            );
            return;
        }

        String content = CryptoSupport.toKeyFile(
                txt_public.getText(),
                txt_private.getText()
        );

        String path = FileTextIO.saveString(
                this,
                content,
                "ecc_keys.txt"
        );

        if (path != null) {
            lbl_status.setText(
                    "Đã lưu khóa vào: " + path
                    + " (giữ bí mật dòng PRIVATE)."
            );
        }
    }

    private void loadKeys() {

        String content = FileTextIO.openString(this);

        if (content == null) {
            return;
        }

        try {
            String[] keys =
                    CryptoSupport.fromKeyFile(content);

            ecc.loadKeys(keys[0], keys[1]);

            txt_public.setText(keys[0]);
            txt_private.setText(keys[1]);

            txt_public.setCaretPosition(0);
            txt_private.setCaretPosition(0);

            lbl_status.setText(
                    "Đã nạp khóa từ tệp."
            );

            lbl_status.setForeground(new Color(0, 130, 65));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tệp khóa không hợp lệ: "
                    + ex.getMessage(),
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
            String ciphertext = ecc.encrypt(plaintext);

            txt_ciphertext.setText(ciphertext);
            txt_ciphertext.setCaretPosition(0);

            lbl_status.setText(
                    "Đã mã hóa ECC ("
                    + ciphertext.length()
                    + " ký tự Base64). "
                    + HistoryDao.tryInsert(
                            "ECC",
                            "ENCRYPT",
                            plaintext.length(),
                            true
                    )
            );

            lbl_status.setForeground(new Color(0, 130, 65));

        } catch (Exception ex) {

            HistoryDao.tryInsert(
                    "ECC",
                    "ENCRYPT",
                    plaintext.length(),
                    false
            );

            lbl_status.setText("Mã hóa ECC thất bại.");
            lbl_status.setForeground(Color.RED);

            JOptionPane.showMessageDialog(
                    this,
                    "Mã hóa thất bại: "
                    + ex.getMessage(),
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
            String plaintext = ecc.decrypt(ciphertext);

            txt_plaintext.setText(plaintext);
            txt_plaintext.setCaretPosition(0);

            lbl_status.setText(
                    "Đã giải mã ECC. "
                    + HistoryDao.tryInsert(
                            "ECC",
                            "DECRYPT",
                            ciphertext.length(),
                            true
                    )
            );

            lbl_status.setForeground(new Color(0, 130, 65));

        } catch (Exception ex) {

            HistoryDao.tryInsert(
                    "ECC",
                    "DECRYPT",
                    ciphertext.length(),
                    false
            );

            lbl_status.setText(
                    "Giải mã ECC THẤT BẠI "
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
            new frm_ECC().setVisible(true);
        });
    }
}
