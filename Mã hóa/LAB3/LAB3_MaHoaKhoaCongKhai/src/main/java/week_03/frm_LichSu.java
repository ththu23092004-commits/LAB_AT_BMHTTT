
package week_03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

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
        setSize(900, 500);
        setMinimumSize(new java.awt.Dimension(760, 400));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout(15, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JLabel title = new JLabel(
                "LỊCH SỬ THAO TÁC - BẢNG DBO.HISTORY",
                JLabel.CENTER
        );

        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(35, 65, 110));

        mainPanel.add(title, BorderLayout.NORTH);

        tbl_history = new JTable();
        tbl_history.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        tbl_history.setRowHeight(28);
        tbl_history.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        tbl_history.setFillsViewportHeight(true);
        tbl_history.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(tbl_history);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.WHITE);

        btn_refresh = new JButton("Tải lại");
        btn_refresh.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        lbl_status = new JLabel("Đang tải...");
        lbl_status.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );
        lbl_status.setForeground(Color.GRAY);

        bottomPanel.add(btn_refresh, BorderLayout.WEST);
        bottomPanel.add(lbl_status, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        btn_refresh.addActionListener(e -> loadData());
    }

    private void loadData() {
        try {
            tbl_history.setModel(
                    HistoryDao.loadLatest(50)
            );

            lbl_status.setText(
                    "Đã tải " + tbl_history.getRowCount()
                    + " dòng mới nhất từ dbo.History."
            );

            lbl_status.setForeground(new Color(0, 130, 65));

        } catch (java.sql.SQLException ex) {
            lbl_status.setText(
                    "Không đọc được lịch sử: "
                    + ex.getMessage()
            );

            lbl_status.setForeground(Color.RED);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new frm_LichSu().setVisible(true);
        });
    }
}
