
package main;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class dashbordKasir extends javax.swing.JFrame {

    private final main.dao.TransaksiDao transaksiDao = new main.dao.TransaksiDao();
    private final main.dao.BarangDao    barangDao    = new main.dao.BarangDao();

    private final main.dao.MemberDao    memberDao    = new main.dao.MemberDao();
    private javax.swing.JComboBox<String> cbMember;
    private javax.swing.JLabel            lblMemberPoin;
    private java.util.List<main.model.Member> listMember = new java.util.ArrayList<>();

    public dashbordKasir() {
        initComponents();
        setTitle("Menu Kasir");

        setLocationRelativeTo(null);
        txtkasir.setText("Kasir : " + koneksi.username);
        txtNoTransaksi.setText(noFaktur());

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                txtJam.setText(jam());
            }
        });
        timer.start();
        txtJam.setText(jam());

        initMemberComponents();
    }

    public String noFaktur() {
        String faktur = "TSK001";
        try {
            String noTransaksi = transaksiDao.ambilNoTransaksiTerakhir();
            if (noTransaksi != null && noTransaksi.length() > 3) {
                String prefix = noTransaksi.substring(0, 3);
                int numberPart = Integer.parseInt(noTransaksi.substring(3)) + 1;
                faktur = prefix + String.format("%03d", numberPart);
            }
        } catch (Exception ex) {
            Logger.getLogger(dashbordKasir.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(faktur);
        return faktur;
    }

    public String jam() {

        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        return "<html>" + currentDateTime.format(timeFormatter) + "<br>" + currentDateTime.format(dateFormatter) + "</html>";
    }

    public void clear() {
        txtKodeBarang.setText("");
        totalAtas.setText("");
        txtBayar.setText("");
        txtKembali.setText("");
        if (cbMember != null && cbMember.getItemCount() > 0) {
            cbMember.setSelectedIndex(0);
        }
        if (lblMemberPoin != null) {
            lblMemberPoin.setText("Status: Non-member");
        }
    }

    private void initMemberComponents() {
        javax.swing.JLabel lblPilihMember = new javax.swing.JLabel("Member :");
        lblPilihMember.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblPilihMember.setForeground(java.awt.Color.WHITE);
        lblPilihMember.setBounds(380, 420, 70, 25);

        cbMember = new javax.swing.JComboBox<>();
        cbMember.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        cbMember.setBackground(java.awt.Color.WHITE);
        cbMember.setForeground(java.awt.Color.BLACK);
        cbMember.setBounds(455, 420, 185, 25);

        lblMemberPoin = new javax.swing.JLabel("Status: Non-member");
        lblMemberPoin.setFont(new java.awt.Font("Segoe UI", java.awt.Font.ITALIC, 11));
        lblMemberPoin.setForeground(java.awt.Color.WHITE);
        lblMemberPoin.setBounds(455, 448, 185, 20);

        cbMember.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateStatusMember();
            }
        });

        jPanel1.add(lblPilihMember);
        jPanel1.add(cbMember);
        jPanel1.add(lblMemberPoin);

        muatMemberCombo();
    }

    private void muatMemberCombo() {
        if (cbMember == null) return;
        cbMember.removeAllItems();
        cbMember.addItem("Non-member");
        listMember.clear();
        try {
            listMember = memberDao.ambilSemua();
            for (main.model.Member m : listMember) {
                cbMember.addItem(m.getNamaMember() + " (ID: " + m.getIdMember() + ")");
            }
        } catch (SQLException e) {
            System.out.println("Gagal memuat daftar member: " + e.getMessage());
        }
    }

    private void updateStatusMember() {
        main.model.Member m = getSelectedMember();
        if (m != null) {
            lblMemberPoin.setText("Poin saat ini: " + m.getPoin() + " Poin");
        } else {
            lblMemberPoin.setText("Status: Non-member");
        }
    }

    private main.model.Member getSelectedMember() {
        if (cbMember == null) return null;
        int index = cbMember.getSelectedIndex();
        if (index > 0 && (index - 1) < listMember.size()) {
            return listMember.get(index - 1);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        totalAtas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbls = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtKembali = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtKodeBarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNoTransaksi = new javax.swing.JTextField();
        txtJam = new javax.swing.JLabel();
        txtkasir = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jButton3.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 255));
        jLabel4.setText("DASBOARD KASIR");

        jButton2.setBackground(new java.awt.Color(0, 153, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("List Barang");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(204, 0, 0));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("<- Log-Out");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addGap(36, 36, 36))
        );

        totalAtas.setBackground(new java.awt.Color(255, 255, 255));
        totalAtas.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        totalAtas.setForeground(new java.awt.Color(255, 255, 255));
        totalAtas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalAtas.setText("Rp. 0");

        tbls.setBackground(new java.awt.Color(255, 255, 255));
        tbls.setForeground(new java.awt.Color(0, 0, 0));
        tbls.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama", "Harga", "QTY", "Jumlah"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbls.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tbls.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tbls.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblsMouseClicked(evt);
            }
        });
        tbls.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblsKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblsKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tbls);
        if (tbls.getColumnModel().getColumnCount() > 0) {
            tbls.getColumnModel().getColumn(0).setResizable(false);
            tbls.getColumnModel().getColumn(0).setHeaderValue("Nama");
            tbls.getColumnModel().getColumn(1).setResizable(false);
            tbls.getColumnModel().getColumn(1).setHeaderValue("Harga");
            tbls.getColumnModel().getColumn(2).setResizable(false);
            tbls.getColumnModel().getColumn(2).setHeaderValue("QTY");
            tbls.getColumnModel().getColumn(3).setResizable(false);
            tbls.getColumnModel().getColumn(3).setHeaderValue("Jumlah");
        }

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Bayar");

        txtBayar.setBackground(new java.awt.Color(255, 255, 255));
        txtBayar.setForeground(new java.awt.Color(0, 0, 0));
        txtBayar.setMinimumSize(new java.awt.Dimension(100, 26));
        txtBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBayarActionPerformed(evt);
            }
        });
        txtBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBayarKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Kembali");

        txtKembali.setBackground(new java.awt.Color(255, 255, 255));
        txtKembali.setForeground(new java.awt.Color(0, 0, 0));
        txtKembali.setMinimumSize(new java.awt.Dimension(100, 26));
        txtKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKembaliActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("TRANSAKSI");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("No. Faktur");

        txtKodeBarang.setBackground(new java.awt.Color(255, 255, 255));
        txtKodeBarang.setForeground(new java.awt.Color(0, 0, 0));
        txtKodeBarang.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtKodeBarang.setCaretColor(new java.awt.Color(0, 0, 0));
        txtKodeBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtKodeBarang.setMinimumSize(new java.awt.Dimension(68, 26));
        txtKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeBarangActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Kode Barang");

        txtNoTransaksi.setEditable(false);
        txtNoTransaksi.setBackground(new java.awt.Color(255, 255, 255));
        txtNoTransaksi.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtNoTransaksi.setForeground(new java.awt.Color(0, 0, 0));
        txtNoTransaksi.setActionCommand("<Not Set>");
        txtNoTransaksi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtNoTransaksi.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNoTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtNoTransaksi.setMinimumSize(new java.awt.Dimension(68, 26));
        txtNoTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoTransaksiActionPerformed(evt);
            }
        });

        txtJam.setFont(new java.awt.Font("ArianLT-Demi", 1, 14)); // NOI18N
        txtJam.setForeground(new java.awt.Color(255, 255, 255));
        txtJam.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtJam.setText("Date Time");

        txtkasir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtkasir.setForeground(new java.awt.Color(255, 255, 255));
        txtkasir.setText("Nama Petugas Kasir");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 255, 0));
        jButton1.setText("BAYAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNoTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(totalAtas, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(27, 27, 27))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtJam, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(415, 415, 415)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtkasir, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel5))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(15, 15, 15)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNoTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(totalAtas)))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtkasir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJam, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addData(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            return;
        }

        long kode;
        try {
            kode = Long.parseLong(productId.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Kode barang harus berupa angka!", "Input Tidak Valid", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel tabel = (DefaultTableModel) tbls.getModel();
        try {
            main.model.Barang barang = barangDao.cari(kode);
            if (barang != null) {
                String nama = barang.getNamaBarang();
                int harga = barang.getHargaJual();
                int qty = 1;
                int jumlah = harga * qty;

                boolean productExists = false;
                for (int i = 0; i < tabel.getRowCount(); i++) {
                    if (tabel.getValueAt(i, 0).equals(nama)) {
                        int currentQuantity = (int) tbls.getValueAt(i, 2);
                        tabel.setValueAt(currentQuantity + 1, i, 2);
                        tabel.setValueAt((currentQuantity + 1) * harga, i, 3);
                        productExists = true;
                        break;
                    }
                }

                if (!productExists) {
                    tabel.addRow(new Object[]{nama, harga, qty, jumlah});
                }

                updateTotal();
            } else {
                JOptionPane.showMessageDialog(this, "Produk dengan kode " + kode + " tidak ditemukan!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(dashbordKasir.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Gagal mengambil data produk: " + ex.getMessage());
        }
    }

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        dispose();
        MenuUtama.main(null);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        dispose();
        listBarang.main(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBayarActionPerformed

    }//GEN-LAST:event_txtBayarActionPerformed

    private void txtKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKembaliActionPerformed

    }//GEN-LAST:event_txtKembaliActionPerformed

    private void txtKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeBarangActionPerformed

        String productId = txtKodeBarang.getText();
        addData(productId);
        txtKodeBarang.setText(" ");
        txtKodeBarang.requestFocusInWindow();

    }//GEN-LAST:event_txtKodeBarangActionPerformed

    private void txtNoTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoTransaksiActionPerformed

    }//GEN-LAST:event_txtNoTransaksiActionPerformed

    private void tblsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblsKeyReleased

    }//GEN-LAST:event_tblsKeyReleased

    private void tblsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblsKeyPressed

        int updateRow = tbls.getSelectedRow();

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int harga = Integer.parseInt(tbls.getValueAt(updateRow, 1).toString());
            int qty = Integer.parseInt(tbls.getValueAt(updateRow, 2).toString());
            tbls.setValueAt(qty, updateRow, 2);

            int jumlah = harga * qty;

            tbls.setValueAt(jumlah, updateRow, 3);
            updateTotal();
        }
    }//GEN-LAST:event_tblsKeyPressed

    private void tblsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblsKeyTyped

    }//GEN-LAST:event_tblsKeyTyped

    private void tblsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblsMouseClicked

        int updateRow = tbls.getSelectedRow();

        int harga = Integer.parseInt(tbls.getValueAt(updateRow, 1).toString());
        int qty = Integer.parseInt(tbls.getValueAt(updateRow, 2).toString());
        tbls.setValueAt(qty, updateRow, 2);

        int jumlah = harga * qty;

        tbls.setValueAt(jumlah, updateRow, 3);
        updateTotal();

    }//GEN-LAST:event_tblsMouseClicked

    private void txtBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyPressed

        String totalString = totalAtas.getText().replace("Rp. ", "").trim();
        String bayarString = txtBayar.getText().replace("Rp. ", "").trim();

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {

                int total = Integer.parseInt(totalString);
                int bayar = Integer.parseInt(bayarString);

                int kembalian = bayar - total;

                String hasil = String.valueOf(kembalian);
                txtKembali.setText(hasil);
            } catch (NumberFormatException e) {

                System.out.println("Error: " + e.getMessage());
            }
        }

    }//GEN-LAST:event_txtBayarKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        DefaultTableModel tabel = (DefaultTableModel) tbls.getModel();
        int rowCount = tabel.getRowCount();

        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Keranjang belanja masih kosong!");
            return;
        }

        String totalString = totalAtas.getText().replace("Rp. ", "").replace(".", "").replace(",", "").trim();
        String bayarString = txtBayar.getText().replace("Rp. ", "").replace(".", "").replace(",", "").trim();

        if (bayarString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Silakan masukkan nominal pembayaran pelanggan!");
            txtBayar.requestFocus();
            return;
        }

        long total;
        long bayar;
        try {
            total = Long.parseLong(totalString);
            bayar = Long.parseLong(bayarString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format angka pembayaran tidak valid!");
            return;
        }

        if (bayar < total) {
            JOptionPane.showMessageDialog(this, "Uang pembayaran kurang! Kurang: Rp. " + (total - bayar));
            return;
        }

        long kembalian = bayar - total;
        txtKembali.setText(String.valueOf(kembalian));

        String noFaktur = txtNoTransaksi.getText();
        String petugas = txtkasir.getText().replace("Kasir : ", "").trim();

        String waktu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        main.model.Transaksi transaksi = new main.model.Transaksi(noFaktur, petugas, waktu);

        main.model.Member memberTerpilih = getSelectedMember();
        if (memberTerpilih != null) {
            transaksi.setMember(memberTerpilih);
        }

        for (int i = 0; i < rowCount; i++) {
            String namaBarang = tabel.getValueAt(i, 0).toString();
            int harga = Integer.parseInt(tabel.getValueAt(i, 1).toString());
            int qty = Integer.parseInt(tabel.getValueAt(i, 2).toString());

            long kodeBarang = 0;
            try {
                main.model.Barang b = barangDao.cari(namaBarang);
                if (b != null) {
                    kodeBarang = b.getKodeBarang();
                }
            } catch (SQLException ex) {
                Logger.getLogger(dashbordKasir.class.getName()).log(Level.SEVERE, null, ex);
            }

            main.model.DetailTransaksi detail = new main.model.DetailTransaksi(noFaktur, kodeBarang, namaBarang, harga, qty);
            transaksi.tambahItem(detail);
        }

        transaksi.setJumlahBayar(bayar);

        try {
            transaksiDao.simpan(transaksi);

            main.model.Struk struk = new main.model.Struk(transaksi);
            struk.tampilkanDialogStruk();

            clear();
            muatMemberCombo();
            tabel.setNumRows(0);
            txtNoTransaksi.setText(noFaktur());
            JOptionPane.showMessageDialog(this, "Transaksi berhasil diproses dan disimpan!");

        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memproses transaksi: " + e.getMessage(), "Kesalahan Transaksi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void updateTotal() {
        int rowCount = tbls.getRowCount();
        Integer total = 0;
        for (int i = 0; i < rowCount; i++) {
            total += Integer.parseInt(tbls.getValueAt(i, 3).toString());
        }
        totalAtas.setText("Rp. " + total.toString());

    }

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dashbordKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashbordKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashbordKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashbordKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashbordKasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbls;
    private javax.swing.JLabel totalAtas;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JLabel txtJam;
    private javax.swing.JTextField txtKembali;
    private javax.swing.JTextField txtKodeBarang;
    private javax.swing.JTextField txtNoTransaksi;
    private javax.swing.JLabel txtkasir;
    // End of variables declaration//GEN-END:variables
}

