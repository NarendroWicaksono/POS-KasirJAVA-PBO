
package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import main.dao.TransaksiDao;
import main.model.DetailTransaksi;
import main.model.Struk;
import main.model.Transaksi;

public class riwayatTransaksi extends JFrame {

    private final TransaksiDao transaksiDao = new TransaksiDao();

    private JTable tblTransaksi;
    private JTable tblDetail;
    private DefaultTableModel modelTransaksi;
    private DefaultTableModel modelDetail;
    private JTextField txtCari;
    private TableRowSorter<DefaultTableModel> sorter;

    public riwayatTransaksi() {
        initComponentsCustom();
        setTitle("RIWAYAT PENJUALAN | Dog-Mart");
        setSize(800, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        muatDataTransaksi();
    }

    private void initComponentsCustom() {
        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBackground(new Color(0, 153, 255));

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(0, 153, 255));

        JButton btnKembali = new JButton("< Kembali");
        btnKembali.setBackground(Color.WHITE);
        btnKembali.setForeground(new Color(0, 153, 255));
        btnKembali.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                dashbordAdmin.main(null);
            }
        });

        JLabel lblJudul = new JLabel("RIWAYAT PENJUALAN TOKO", JLabel.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblJudul.setForeground(Color.WHITE);

        JPanel panelCari = new JPanel();
        panelCari.setBackground(new Color(0, 153, 255));
        JLabel lblCari = new JLabel("Cari Transaksi: ");
        lblCari.setForeground(Color.WHITE);
        txtCari = new JTextField(15);
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (sorter != null) {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtCari.getText()));
                }
            }
        });
        panelCari.add(lblCari);
        panelCari.add(txtCari);

        panelHeader.add(btnKembali, BorderLayout.WEST);
        panelHeader.add(lblJudul, BorderLayout.CENTER);
        panelHeader.add(panelCari, BorderLayout.SOUTH);

        JPanel panelTabel = new JPanel(new BorderLayout(0, 10));
        panelTabel.setBackground(Color.WHITE);

        String[] kolomTransaksi = {"No. Faktur", "Member", "Poin Didapat", "Petugas Kasir", "Total Belanja", "Bayar", "Kembalian", "Waktu Transaksi"};
        modelTransaksi = new DefaultTableModel(kolomTransaksi, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTransaksi = new JTable(modelTransaksi);
        sorter = new TableRowSorter<>(modelTransaksi);
        tblTransaksi.setRowSorter(sorter);
        tblTransaksi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblTransaksi.getSelectedRow();
                if (row != -1) {
                    int modelRow = tblTransaksi.convertRowIndexToModel(row);
                    String noFaktur = modelTransaksi.getValueAt(modelRow, 0).toString();
                    muatDetailTransaksi(noFaktur);
                }
            }
        });

        JScrollPane scrollTransaksi = new JScrollPane(tblTransaksi);
        scrollTransaksi.setPreferredSize(new Dimension(760, 200));

        String[] kolomDetail = {"Kode Barang", "Nama Barang", "Harga Satuan", "Qty", "Subtotal"};
        modelDetail = new DefaultTableModel(kolomDetail, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDetail = new JTable(modelDetail);
        JScrollPane scrollDetail = new JScrollPane(tblDetail);
        scrollDetail.setPreferredSize(new Dimension(760, 180));

        JLabel lblDetail = new JLabel(" Rincian Item Barang (Pilih transaksi di atas):");
        lblDetail.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.add(lblDetail, BorderLayout.NORTH);
        panelBawah.add(scrollDetail, BorderLayout.CENTER);

        panelTabel.add(scrollTransaksi, BorderLayout.NORTH);
        panelTabel.add(panelBawah, BorderLayout.CENTER);

        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(new Color(0, 153, 255));

        JButton btnCetakUlang = new JButton("Cetak Ulang Struk");
        btnCetakUlang.setBackground(Color.WHITE);
        btnCetakUlang.setForeground(new Color(0, 153, 255));
        btnCetakUlang.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCetakUlang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cetakUlangStrukTerpilih();
            }
        });

        panelFooter.add(btnCetakUlang);

        panelUtama.add(panelHeader, BorderLayout.NORTH);
        panelUtama.add(panelTabel, BorderLayout.CENTER);
        panelUtama.add(panelFooter, BorderLayout.SOUTH);

        add(panelUtama);
    }

    public void muatDataTransaksi() {
        modelTransaksi.setRowCount(0);
        try {
            List<Transaksi> list = transaksiDao.ambilRiwayatPenjualan();
            for (Transaksi t : list) {
                String strBayar = t.getJumlahBayar() > 0 ? String.format("Rp. %,d", t.getJumlahBayar()) : "-";
                String strKembali = t.getJumlahBayar() > 0 ? String.format("Rp. %,d", t.getKembalian()) : "-";
                String namaMember = (t.getMember() != null) ? t.getMember().getNamaMember() : "Non-member";
                String strPoin = (t.getMember() != null) ? ("+" + t.getPoinDidapat()) : "-";
                modelTransaksi.addRow(new Object[]{
                    t.getNoTransaksi(),
                    namaMember,
                    strPoin,
                    t.getPetugasKasir(),
                    String.format("Rp. %,d", t.getTotal()),
                    strBayar,
                    strKembali,
                    t.getWaktuTransaksi()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat riwayat: " + e.getMessage());
        }
    }

    private void muatDetailTransaksi(String noFaktur) {
        modelDetail.setRowCount(0);
        try {
            List<DetailTransaksi> list = transaksiDao.ambilDetail(noFaktur);
            for (DetailTransaksi d : list) {
                modelDetail.addRow(new Object[]{
                    d.getKodeBarang(),
                    d.getNamaBarang(),
                    String.format("Rp. %,d", d.getHarga()),
                    d.getQty(),
                    String.format("Rp. %,d", d.getJumlah())
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat detail item: " + e.getMessage());
        }
    }

    private void cetakUlangStrukTerpilih() {
        int row = tblTransaksi.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Silakan pilih salah satu transaksi di tabel terlebih dahulu!");
            return;
        }

        int modelRow = tblTransaksi.convertRowIndexToModel(row);
        String noFaktur = modelTransaksi.getValueAt(modelRow, 0).toString();

        try {

            Transaksi t = transaksiDao.cariByNoTransaksi(noFaktur);
            if (t == null) {
                JOptionPane.showMessageDialog(this, "Data transaksi " + noFaktur + " tidak ditemukan!");
                return;
            }

            List<DetailTransaksi> details = transaksiDao.ambilDetail(noFaktur);
            if (details.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada rincian item untuk transaksi " + noFaktur);
                return;
            }

            for (DetailTransaksi d : details) {
                t.tambahItem(d);
            }

            Struk struk = new Struk(t);
            struk.tampilkanDialogStruk();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencetak ulang struk: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new riwayatTransaksi().setVisible(true);
            }
        });
    }
}

