/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;
import java.math.BigInteger;
import java.sql.*;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ACER
 */
public class tambahBarang extends javax.swing.JFrame {
     
    // [TAMBAHAN] Inisialisasi DAO untuk akses data barang dan supplier
    private final main.dao.BarangDao barangDao = new main.dao.BarangDao();
    private final main.dao.SupplierDao supplierDao = new main.dao.SupplierDao();

    // [TAMBAHAN] Komponen relasi Supplier: combo box dan daftar supplier
    private javax.swing.JComboBox<String> cbSupplier;
    private java.util.List<main.model.Supplier> listSupplier = new java.util.ArrayList<>();

    /**
     * Creates new form tambahBarang
     */
    public tambahBarang() {
        initComponents();
        setTitle("DATA BARANG");
        setLocationRelativeTo(null);
        initSupplierComponents(); // [TAMBAHAN] Pasang komponen combo box supplier
        getData();
    }
    
    public void clear(){
        namaBarang.setText("");
        stokBarang.setText("");
        codeBarang.setText("");
        hargaBeli.setText("");
        hargaJual.setText("");
        if (cbSupplier != null && cbSupplier.getItemCount() > 0) {
            cbSupplier.setSelectedIndex(0);
        }
    }

    /**
     * [TAMBAHAN] Menginisialisasi komponen combo box supplier dan menambahkannya
     * ke antarmuka pengguna tanpa memodifikasi blok auto-generated initComponents.
     */
    private void initSupplierComponents() {
        cbSupplier = new javax.swing.JComboBox<>();
        cbSupplier.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        cbSupplier.setBackground(java.awt.Color.WHITE);
        cbSupplier.setForeground(java.awt.Color.BLACK);

        javax.swing.JLabel lblSupplier = new javax.swing.JLabel("Supplier :");
        lblSupplier.setForeground(java.awt.Color.WHITE);
        lblSupplier.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

        // Letakkan di panel jPanel1 tepat di atas tabel (sisi kiri)
        lblSupplier.setBounds(20, 230, 65, 25);
        cbSupplier.setBounds(85, 230, 195, 25);
        jPanel1.add(lblSupplier);
        jPanel1.add(cbSupplier);

        // Tambahkan kolom Supplier pada model tabel jika belum ada
        DefaultTableModel model = (DefaultTableModel) tbl_data.getModel();
        if (model.getColumnCount() == 5) {
            model.addColumn("Supplier");
        }

        muatSupplierCombo();
    }

    /**
     * [TAMBAHAN] Memuat daftar supplier dari SupplierDao ke combo box.
     */
    private void muatSupplierCombo() {
        cbSupplier.removeAllItems();
        cbSupplier.addItem("Tanpa supplier");
        listSupplier.clear();
        try {
            listSupplier = supplierDao.ambilSemua();
            for (main.model.Supplier s : listSupplier) {
                cbSupplier.addItem(s.getNama() + " (ID: " + s.getId() + ")");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat daftar supplier: " + e.getMessage());
        }
    }

    /**
     * [TAMBAHAN] Mengambil objek Supplier yang sedang dipilih di combo box.
     * @return objek Supplier atau null jika memilih "Tanpa supplier"
     */
    private main.model.Supplier getSelectedSupplier() {
        int index = cbSupplier.getSelectedIndex();
        if (index > 0 && (index - 1) < listSupplier.size()) {
            return listSupplier.get(index - 1);
        }
        return null;
    }

    /**
     * [TAMBAHAN] Memilih item supplier di combo box berdasarkan nama supplier.
     */
    private void pilihSupplierDiCombo(String namaSup) {
        if (namaSup == null || namaSup.isEmpty() || namaSup.equalsIgnoreCase("Tanpa supplier") || namaSup.equals("-")) {
            cbSupplier.setSelectedIndex(0);
            return;
        }
        for (int i = 0; i < listSupplier.size(); i++) {
            if (listSupplier.get(i).getNama().equalsIgnoreCase(namaSup)) {
                cbSupplier.setSelectedIndex(i + 1);
                return;
            }
        }
        cbSupplier.setSelectedIndex(0);
    }
    
    private void getData() {
        DefaultTableModel model = (DefaultTableModel) tbl_data.getModel();
        model.setRowCount(0);
        
        try {
            // [TAMBAHAN] Mengambil semua data barang via BarangDao beserta relasi supplier
            java.util.List<main.model.Barang> list = barangDao.ambilSemua();
            for (main.model.Barang b : list) {
                String namaSup = (b.getSupplier() != null && b.getSupplier().getNama() != null)
                        ? b.getSupplier().getNama() : "Tanpa supplier";
                Object[] rowData = {
                    b.getKodeBarang(),
                    b.getNamaBarang(),
                    b.getStokBarang(),
                    b.getHargaBeli(),
                    b.getHargaJual(),
                    namaSup
                };
                model.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data barang: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        codeBarang = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        stokBarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        namaBarang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        hargaBeli = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        hargaJual = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        update = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        back = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_data = new javax.swing.JTable();
        cari = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Code Barang");

        codeBarang.setBackground(new java.awt.Color(255, 255, 255));
        codeBarang.setForeground(new java.awt.Color(0, 0, 0));
        codeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeBarangActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Stok");

        stokBarang.setBackground(new java.awt.Color(255, 255, 255));
        stokBarang.setForeground(new java.awt.Color(0, 0, 0));
        stokBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stokBarangActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Barang");

        namaBarang.setBackground(new java.awt.Color(255, 255, 255));
        namaBarang.setForeground(new java.awt.Color(0, 0, 0));
        namaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaBarangActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Harga Beli");

        hargaBeli.setBackground(new java.awt.Color(255, 255, 255));
        hargaBeli.setForeground(new java.awt.Color(0, 0, 0));
        hargaBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaBeliActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Harga Jual");

        hargaJual.setBackground(new java.awt.Color(255, 255, 255));
        hargaJual.setForeground(new java.awt.Color(0, 0, 0));
        hargaJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaJualActionPerformed(evt);
            }
        });

        add.setBackground(new java.awt.Color(255, 255, 255));
        add.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        add.setForeground(new java.awt.Color(0, 153, 255));
        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        update.setBackground(new java.awt.Color(255, 255, 255));
        update.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        update.setForeground(new java.awt.Color(0, 153, 255));
        update.setText("Update");
        update.setToolTipText("");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        delete.setBackground(new java.awt.Color(255, 255, 255));
        delete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        delete.setForeground(new java.awt.Color(0, 153, 255));
        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        back.setBackground(new java.awt.Color(255, 255, 255));
        back.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        back.setForeground(new java.awt.Color(0, 153, 255));
        back.setText("<");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("DATA BARANG");

        tbl_data.setBackground(new java.awt.Color(255, 255, 255));
        tbl_data.setForeground(new java.awt.Color(0, 0, 0));
        tbl_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code Barang", "Nama Barang", "Stok", "Harga Beli", "Harga Jual"
            }
        ));
        tbl_data.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_dataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_data);

        cari.setBackground(new java.awt.Color(255, 255, 255));
        cari.setForeground(new java.awt.Color(0, 0, 0));
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });
        cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cariKeyReleased(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cari");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(codeBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                            .addComponent(namaBarang))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)
                                        .addGap(4, 4, 4)
                                        .addComponent(stokBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(hargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(26, 26, 26)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5))))
                                .addGap(89, 89, 89)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(update)
                                    .addComponent(add)
                                    .addComponent(delete)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(back)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(328, 328, 328))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(back)
                    .addComponent(jLabel6))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(codeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(stokBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(update)
                        .addGap(13, 13, 13)
                        .addComponent(delete)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stokBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stokBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stokBarangActionPerformed

    private void namaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaBarangActionPerformed
        // TODO add your handling code here:
        hargaBeli.requestFocus();
    }//GEN-LAST:event_namaBarangActionPerformed

    private void hargaBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaBeliActionPerformed
        // TODO add your handling code here:
        hargaJual.requestFocus();
    }//GEN-LAST:event_hargaBeliActionPerformed

    private void hargaJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaJualActionPerformed
        // TODO add your handling code here:
        stokBarang.requestFocus();
    }//GEN-LAST:event_hargaJualActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        dashbordAdmin.main(null);
        dispose();
    }//GEN-LAST:event_backActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        // [TAMBAHAN] Menambah barang baru beserta relasi Supplier via BarangDao
        try {
            long kode = Long.parseLong(codeBarang.getText().trim());
            String nama = namaBarang.getText().trim();
            int hbeli = Integer.parseInt(hargaBeli.getText().trim());
            int hjual = Integer.parseInt(hargaJual.getText().trim());
            int stok = Integer.parseInt(stokBarang.getText().trim());
            main.model.Supplier sup = getSelectedSupplier();

            main.model.Barang barang = new main.model.Barang(kode, nama, hbeli, hjual, stok, sup);
            barangDao.simpan(barang);

            JOptionPane.showMessageDialog(null, "Data Barang : BERHASIL DITAMBAHKAN");
            clear();
            getData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Pastikan Kode, Harga, dan Stok berupa angka!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Validasi Gagal: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        }
    }//GEN-LAST:event_addActionPerformed

    private void tbl_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_dataMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbl_data.getSelectedRow();
        if(selectedRow != -1){
            String code = tbl_data.getValueAt(selectedRow, 0).toString();
            String nama = tbl_data.getValueAt(selectedRow, 1).toString();
            String stok = tbl_data.getValueAt(selectedRow, 2).toString();
            String Hbeli = tbl_data.getValueAt(selectedRow, 3).toString();
            String Hjual = tbl_data.getValueAt(selectedRow, 4).toString();

            codeBarang.setText(code);
            namaBarang.setText(nama);
            hargaBeli.setText(Hbeli); 
            hargaJual.setText(Hjual);
            stokBarang.setText(stok);

            // [TAMBAHAN] Sinkronkan pilihan combo box supplier sesuai baris yang dipilih
            if (tbl_data.getColumnCount() > 5 && tbl_data.getValueAt(selectedRow, 5) != null) {
                pilihSupplierDiCombo(tbl_data.getValueAt(selectedRow, 5).toString());
            } else {
                pilihSupplierDiCombo(null);
            }
        }
    }//GEN-LAST:event_tbl_dataMouseClicked

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        // [TAMBAHAN] Memperbarui barang beserta relasi Supplier via BarangDao
        int selectedRow = tbl_data.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "PILIH DATA YANG AKAN DI UPDATE !!");
            return;
        }

        try {
            long kode = Long.parseLong(codeBarang.getText().trim());
            String nama = namaBarang.getText().trim();
            int hbeli = Integer.parseInt(hargaBeli.getText().trim());
            int hjual = Integer.parseInt(hargaJual.getText().trim());
            int stok = Integer.parseInt(stokBarang.getText().trim());
            main.model.Supplier sup = getSelectedSupplier();

            main.model.Barang barang = new main.model.Barang(kode, nama, hbeli, hjual, stok, sup);
            barangDao.ubah(barang);

            JOptionPane.showMessageDialog(null, "Data Barang : BERHASIL DI UPDATE");
            clear();
            getData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Pastikan Kode, Harga, dan Stok berupa angka!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Validasi Gagal: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        }
    }//GEN-LAST:event_updateActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // [TAMBAHAN] Menghapus barang via BarangDao
        int selectedRow = tbl_data.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "PILIH DATA YANG AKAN DI HAPUS !!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin Ingin Menghapus Data Ini?", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                long kode = Long.parseLong(tbl_data.getValueAt(selectedRow, 0).toString());
                barangDao.hapus(kode);

                JOptionPane.showMessageDialog(null, "Data Barang : TELAH DI HAPUS");
                clear();
                getData();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_deleteActionPerformed

    private void codeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeBarangActionPerformed
        // TODO add your handling code here:
        namaBarang.requestFocus();
    }//GEN-LAST:event_codeBarangActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariActionPerformed

    private void cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariKeyReleased
        // TODO add your handling code here:
        DefaultTableModel ob = (DefaultTableModel) tbl_data.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        tbl_data.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(cari.getText()));
    }//GEN-LAST:event_cariKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(tambahBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tambahBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tambahBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tambahBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tambahBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton back;
    private javax.swing.JTextField cari;
    private javax.swing.JTextField codeBarang;
    private javax.swing.JButton delete;
    private javax.swing.JTextField hargaBeli;
    private javax.swing.JTextField hargaJual;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField namaBarang;
    private javax.swing.JTextField stokBarang;
    private javax.swing.JTable tbl_data;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables

    
}
