package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import main.dao.PenggunaDao;
import main.model.Admin;
import main.model.Kasir;
import main.model.Pengguna;

// [TAMBAHAN] Form untuk menampilkan seluruh akun pengguna (Admin dan Kasir) serta aksi hapus
public class dataPengguna extends JFrame {

    private final PenggunaDao penggunaDao = new PenggunaDao();
    private JTable tabelPengguna;
    private DefaultTableModel modelTabel;
    private JButton btnHapus;
    private JButton btnKembali;

    public dataPengguna() {
        // [TAMBAHAN] Inisialisasi tampilan dataPengguna secara programatik
        inisialisasiKomponen();
        setTitle("DATA PENGGUNA (ADMIN & KASIR)");
        setSize(650, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // [TAMBAHAN] Muat data pengguna ke dalam tabel
        muatDataPengguna();
    }

    // [TAMBAHAN] Method bantu untuk membangun tata letak antarmuka pengguna
    private void inisialisasiKomponen() {
        JPanel panelUtama = new JPanel();
        panelUtama.setBackground(new Color(0, 153, 255));
        panelUtama.setLayout(null);

        btnKembali = new JButton("<");
        btnKembali.setBackground(Color.WHITE);
        btnKembali.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnKembali.setForeground(new Color(0, 153, 255));
        btnKembali.setBounds(20, 20, 45, 30);
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // [TAMBAHAN] Kembali ke dashboard admin
                kembaliKeDashboard();
            }
        });
        panelUtama.add(btnKembali);

        JLabel lblJudul = new JLabel("DAFTAR AKUN PENGGUNA");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblJudul.setForeground(Color.WHITE);
        lblJudul.setBounds(80, 22, 350, 25);
        panelUtama.add(lblJudul);

        // [TAMBAHAN] Definisi kolom tabel tanpa kolom password untuk keamanan
        String[] namaKolom = {"ID", "Username", "Email", "Peran"};
        modelTabel = new DefaultTableModel(namaKolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabel hanya baca
            }
        };

        tabelPengguna = new JTable(modelTabel);
        tabelPengguna.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelPengguna.setRowHeight(24);
        tabelPengguna.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelPengguna.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tabelPengguna);
        scrollPane.setBounds(20, 70, 595, 300);
        panelUtama.add(scrollPane);

        btnHapus = new JButton("Hapus Akun");
        btnHapus.setBackground(new Color(204, 0, 0));
        btnHapus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setBounds(485, 385, 130, 35);
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // [TAMBAHAN] Aksi hapus akun yang dipilih
                hapusAkunTerpilih();
            }
        });
        panelUtama.add(btnHapus);

        getContentPane().add(panelUtama);
    }

    // [TAMBAHAN] Method untuk memuat semua akun (Admin & Kasir) secara polimorfik
    private void muatDataPengguna() {
        modelTabel.setRowCount(0);
        try {
            List<Pengguna> daftarPengguna = penggunaDao.ambilSemua();
            for (Pengguna p : daftarPengguna) {
                // [TAMBAHAN] Peran diambil secara polimorfik melalui getHakAkses() atau tipe objeknya
                String peran = (p.getHakAkses() != null && !p.getHakAkses().isEmpty()) 
                        ? p.getHakAkses() 
                        : (p instanceof Admin ? "Admin" : "Kasir");

                Object[] baris = {
                    p.getId(),
                    p.getUsername(),
                    p.getEmail(),
                    peran
                };
                modelTabel.addRow(baris);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat daftar pengguna: " + e.getMessage(), "Error Basis Data", JOptionPane.ERROR_MESSAGE);
        }
    }

    // [TAMBAHAN] Method untuk menghapus akun yang dipilih dengan validasi keamanan
    private void hapusAkunTerpilih() {
        int barisTerpilih = tabelPengguna.getSelectedRow();
        if (barisTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih akun yang ingin dihapus terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(modelTabel.getValueAt(barisTerpilih, 0).toString());
        String username = modelTabel.getValueAt(barisTerpilih, 1).toString();
        String peran = modelTabel.getValueAt(barisTerpilih, 3).toString();

        // [TAMBAHAN] Cegah admin menghapus akunnya sendiri yang sedang login menggunakan koneksi.username
        if (koneksi.username != null && koneksi.username.equalsIgnoreCase(username)) {
            JOptionPane.showMessageDialog(this, 
                    "Anda tidak dapat menghapus akun Anda sendiri (" + username + ") yang sedang aktif digunakan!", 
                    "Akses Ditolak", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // [TAMBAHAN] Dialog konfirmasi sebelum menghapus akun
        int konfirmasi = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menghapus akun '" + username + "' dengan peran " + peran + "?", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                // [TAMBAHAN] Hapus akun spesifik sesuai perannya melalui PenggunaDao
                if ("Admin".equalsIgnoreCase(peran)) {
                    penggunaDao.hapusAdmin(id);
                } else if ("Kasir".equalsIgnoreCase(peran)) {
                    penggunaDao.hapusKasir(id);
                } else {
                    penggunaDao.hapus(id);
                }

                JOptionPane.showMessageDialog(this, "Akun '" + username + "' berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                muatDataPengguna();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus akun: " + e.getMessage(), "Error Basis Data", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // [TAMBAHAN] Method navigasi kembali ke dashboard admin
    private void kembaliKeDashboard() {
        dispose();
        dashbordAdmin.main(null);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new dataPengguna().setVisible(true);
            }
        });
    }
}

