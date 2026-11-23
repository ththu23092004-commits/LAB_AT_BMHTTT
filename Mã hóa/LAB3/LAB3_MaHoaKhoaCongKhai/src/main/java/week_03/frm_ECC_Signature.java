
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

public class frm_ECC_Signature extends JFrame {

    private final ECCSignature ecdsa = new ECCSignature();

    private JTextArea txt_public;
    private JTextArea txt_private;
    private JTextArea txt_message;
    private JTextArea txt_signature;

    private JButton btn_generate;
    private JButton btn_savekeys;
    private JButton btn_loadkeys;
    private JButton btn_sign;
    private JButton btn_verify;

    private JLabel lbl_result;
    private JLabel lbl_status;

    public frm_ECC_Signature() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setTitle("ECDSA Signature");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 720);
        setMinimumSize(new Dimension(750, 620));

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel title = new JLabel(
                "CHỮ KÝ SỐ ECDSA (SHA256withECDSA)",
                JLabel.CENTER
        );

        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(35, 65, 110));

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 12));
        contentPanel.setBackground(Color.WHITE);

        JPanel keyPanel = new JPanel(new BorderLayout(8, 10));
        keyPanel.setBackground(Color.WHITE);
        keyPanel.setBorder(
                BorderFactory.createTitledBorder("Cặp khóa ECDSA")
        );

        JPanel keyTextPanel = new JPanel(
                new GridLayout(2, 1, 8, 8)
        );

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

        JPanel textPanel = new JPanel(
                new GridLayout(2, 1, 10, 12)
        );

        textPanel.setBackground(Color.WHITE);

        txt_message = new JTextArea(3, 40);
        txt_signature = new JTextArea(3, 40);

        configureTextArea(txt_message, true);
        configureTextArea(txt_signature, true);

        textPanel.add(
                createTextPanel("Message:", txt_message)
        );

        textPanel.add(
                createTextPanel("Signature:", txt_signature)
        );

        contentPanel.add(textPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 8)
        );

        actionPanel.setBackground(Color.WHITE);

        btn_sign = new JButton("Sign");
        btn_verify = new JButton("Verify");

        btn_sign.setPreferredSize(new Dimension(110, 35));
        btn_verify.setPreferredSize(new Dimension(110, 35));

        lbl_result = new JLabel("");
        lbl_result.setFont(
                new Font("Segoe UI", Font.BOLD, 20)
        );

        actionPanel.add(btn_sign);
        actionPanel.add(btn_verify);
        actionPanel.add(lbl_result);

        contentPanel.add(actionPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        lbl_status = new JLabel(
                "Bấm Generate Keys hoặc Load Keys."
        );

        lbl_status.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        mainPanel.add(lbl_status, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        btn_generate.addActionListener(e -> generateKeys());
        btn_savekeys.addActionListener(e -> saveKeys());
        btn_loadkeys.addActionListener(e -> loadKeys());
        btn_sign.addActionListener(e -> signMessage());
        btn_verify.addActionListener(e -> verifySignature());
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
        txt_public.setText(ecdsa.getPublicKeyBase64());
        txt_private.setText(ecdsa.getPrivateKeyBase64());

        txt_public.setCaretPosition(0);
        txt_private.setCaretPosition(0);
    }

    private void generateKeys() {
        try {
            ecdsa.generateKeys();
            showKeys();

            lbl_result.setText("");

            lbl_status.setText(
                    "Đã tạo cặp khóa mới. "
                    + "Chữ ký cũ sẽ không hợp lệ với khóa mới."
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
                "ecdsa_keys.txt"
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

            ecdsa.loadKeys(keys[0], keys[1]);

            txt_public.setText(keys[0]);
            txt_private.setText(keys[1]);

            txt_public.setCaretPosition(0);
            txt_private.setCaretPosition(0);

            lbl_result.setText("");
            lbl_status.setText("Đã nạp khóa từ tệp.");

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

    private void signMessage() {

        String message = txt_message.getText();

        try {
            txt_signature.setText(
                    ecdsa.sign(message)
            );

            txt_signature.setCaretPosition(0);
            lbl_result.setText("");

            lbl_status.setText(
                    "Đã ký bằng KHÓA RIÊNG. "
                    + HistoryDao.tryInsert(
                            "ECDSA",
                            "SIGN",
                            message.length(),
                            true
                    )
            );

            lbl_status.setForeground(new Color(0, 130, 65));

        } catch (Exception ex) {

            HistoryDao.tryInsert(
                    "ECDSA",
                    "SIGN",
                    message.length(),
                    false
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Ký thất bại: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void verifySignature() {

        String message = txt_message.getText();

        try {
            boolean ok = ecdsa.verify(
                    message,
                    txt_signature.getText()
            );

            lbl_result.setText(
                    ok ? "VALID" : "INVALID"
            );

            lbl_result.setForeground(
                    ok ? new Color(0, 128, 0) : Color.RED
            );

            lbl_status.setText(
                    "Đã xác minh bằng KHÓA CÔNG KHAI. "
                    + HistoryDao.tryInsert(
                            "ECDSA",
                            "VERIFY",
                            message.length(),
                            ok
                    )
            );

        } catch (Exception ex) {

            HistoryDao.tryInsert(
                    "ECDSA",
                    "VERIFY",
                    message.length(),
                    false
            );

            lbl_result.setText("INVALID");
            lbl_result.setForeground(Color.RED);

            JOptionPane.showMessageDialog(
                    this,
                    "Xác minh thất bại: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new frm_ECC_Signature().setVisible(true);
        });
    }
}
