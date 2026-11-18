
package week_02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class frm_LichSu extends JFrame {

    private JTable tbl_history;
    private JButton btn_refresh;
    private JLabel lbl_status;

    public frm_LichSu() {
        initComponents();
        setLocationRelativeTo(null);
        tbl_history.setModel(HistoryDao.emptyModel());
        loadData();
    }

    private void initComponents() {

        setTitle("Lịch sử thao tác (SQL Server)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(900, 550);
        setMinimumSize(new Dimension(760, 450));

        JPanel main = new JPanel(new BorderLayout(15, 20));
        main.setBackground(Color.WHITE);
        main.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        JLabel title = new JLabel(
                "LỊCH SỬ THAO TÁC - BẢNG DBO.CRYPTO_HISTORY",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Segoe UI", Font.BOLD, 19)
        );

        title.setForeground(new Color(35, 60, 90));

        main.add(title, BorderLayout.NORTH);

        tbl_history = new JTable();

        tbl_history.setModel(
                HistoryDao.emptyModel()
        );

        tbl_history.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        tbl_history.setRowHeight(30);
        tbl_history.setShowGrid(true);
        tbl_history.setGridColor(new Color(225, 225, 225));
        tbl_history.setSelectionBackground(
                new Color(210, 230, 250)
        );

        tbl_history.setSelectionForeground(Color.BLACK);

        tbl_history.setAutoCreateRowSorter(true);
        tbl_history.setFillsViewportHeight(true);

        tbl_history.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        tbl_history.getTableHeader().setPreferredSize(
                new Dimension(0, 35)
        );

        tbl_history.getTableHeader().setBackground(
                new Color(235, 240, 245)
        );

        tbl_history.getTableHeader().setForeground(
                new Color(35, 60, 90)
        );

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        tbl_history.setDefaultRenderer(
                Object.class,
                centerRenderer
        );

        JScrollPane scrollPane = new JScrollPane(tbl_history);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(215, 220, 225)
                )
        );

        main.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(
                new BorderLayout(10, 10)
        );

        bottom.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

        buttonPanel.setBackground(Color.WHITE);

        btn_refresh = new JButton("Tải lại");

        btn_refresh.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        btn_refresh.setPreferredSize(
                new Dimension(120, 35)
        );

        btn_refresh.setFocusPainted(false);

        btn_refresh.setBackground(
                new Color(35, 105, 175)
        );

        btn_refresh.setForeground(Color.WHITE);

        buttonPanel.add(btn_refresh);

        lbl_status = new JLabel(
                "Đang tải...",
                SwingConstants.CENTER
        );

        lbl_status.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        lbl_status.setForeground(
                new Color(90, 90, 90)
        );

        bottom.add(buttonPanel, BorderLayout.NORTH);
        bottom.add(lbl_status, BorderLayout.SOUTH);

        main.add(bottom, BorderLayout.SOUTH);

        add(main);

        btn_refresh.addActionListener(evt -> {
            btn_refreshActionPerformed();
        });
    }

    private void loadData() {

        try {

            tbl_history.setModel(
                    HistoryDao.loadLatest(50)
            );

            lbl_status.setForeground(
                    new Color(0, 130, 60)
            );

            lbl_status.setText(
                    "Đã tải "
                    + tbl_history.getRowCount()
                    + " dòng mới nhất từ dbo.crypto_history."
            );

        } catch (SQLException ex) {

            lbl_status.setForeground(Color.RED);

            lbl_status.setText(
                    "Không đọc được lịch sử: "
                    + ex.getMessage()
            );
        }
    }

    private void btn_refreshActionPerformed() {
        loadData();
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ex) {
        }

        SwingUtilities.invokeLater(() -> {
            new frm_LichSu().setVisible(true);
        });
    }
}
