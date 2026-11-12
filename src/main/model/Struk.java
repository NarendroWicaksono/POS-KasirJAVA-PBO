
package main.model;

import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Struk {

    private final Transaksi transaksi;
    private final NumberFormat formatRupiah;

    public Struk(Transaksi transaksi) {
        this.transaksi = transaksi;
        this.formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    }

    public String buatTeksStruk() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("               DOG-MART POS              \n");
        sb.append("       Jl. Raya Kampus No. 12, PBO       \n");
        sb.append("=========================================\n");
        sb.append(String.format("No. Faktur : %s\n", transaksi.getNoTransaksi()));
        sb.append(String.format("Kasir      : %s\n", transaksi.getPetugasKasir()));

        if (transaksi.getMember() != null) {
            sb.append(String.format("Member     : %s\n", transaksi.getMember().getNamaMember()));
        } else {
            sb.append("Member     : Non-member\n");
        }
        sb.append(String.format("Waktu      : %s\n", transaksi.getWaktuTransaksi()));
        sb.append("-----------------------------------------\n");
        sb.append(String.format("%-18s %4s %8s %9s\n", "Item", "Qty", "Harga", "Total"));
        sb.append("-----------------------------------------\n");

        for (DetailTransaksi d : transaksi.getListDetail()) {
            String nama = d.getNamaBarang();
            if (nama.length() > 18) {
                nama = nama.substring(0, 15) + "...";
            }
            sb.append(String.format("%-18s %4d %8d %9d\n",
                    nama,
                    d.getQty(),
                    d.getHarga(),
                    d.getJumlah()));
        }

        sb.append("-----------------------------------------\n");
        sb.append(String.format("Total Belanja : Rp. %,d\n", transaksi.getTotal()));
        if (transaksi.getDiskon() > 0) {
            sb.append(String.format("Diskon (%d%%)  : Ya\n", transaksi.getDiskon()));
        }

        if (transaksi.getJumlahBayar() > 0) {
            sb.append(String.format("Bayar         : Rp. %,d\n", transaksi.getJumlahBayar()));
            sb.append(String.format("Kembalian     : Rp. %,d\n", transaksi.getKembalian()));
        } else {
            sb.append("Bayar         : -\n");
            sb.append("Kembalian     : -\n");
        }

        if (transaksi.getMember() != null) {
            sb.append("-----------------------------------------\n");
            sb.append(String.format("Poin Didapat  : +%d Poin\n", transaksi.getPoinDidapat()));
            sb.append(String.format("Total Poin    : %d Poin\n", transaksi.getMember().getPoin()));
        }
        sb.append("=========================================\n");
        sb.append("     Terima Kasih Atas Kunjungan Anda    \n");
        sb.append("      Barang yang dibeli tidak dapat     \n");
        sb.append("           ditukar / dikembalikan        \n");
        sb.append("=========================================\n");

        return sb.toString();
    }

    public void tampilkanDialogStruk() {
        String teksStruk = buatTeksStruk();

        JTextArea textArea = new JTextArea(teksStruk);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(380, 420));

        Object[] opsi = {"Simpan ke File (.txt)", "Tutup"};
        int pilihan = JOptionPane.showOptionDialog(
                null,
                scrollPane,
                "Struk Pembayaran - " + transaksi.getNoTransaksi(),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opsi,
                opsi[1]
        );

        if (pilihan == 0) {
            simpanKeFile(teksStruk);
        }
    }

    public void simpanKeFile(String isiStruk) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("Struk_" + transaksi.getNoTransaksi() + ".txt"));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter fw = new FileWriter(fileToSave)) {
                fw.write(isiStruk);
                JOptionPane.showMessageDialog(null,
                        "Struk berhasil disimpan di:\n" + fileToSave.getAbsolutePath(),
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Gagal menyimpan struk: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

