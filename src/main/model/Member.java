// [TAMBAHAN] - Member.java
// Class model untuk entitas Member (pelanggan terdaftar) dalam sistem kasir.
// Menerapkan: Encapsulation (atribut private + getter/setter bervalidasi).
package main.model;

/**
 * Class model yang merepresentasikan data Member (pelanggan) dalam sistem kasir.
 *
 * ENCAPSULATION: Semua atribut bersifat private. Setter memvalidasi
 * bahwa data yang masuk selalu dalam kondisi valid (tidak kosong, tidak negatif).
 *
 * @author Tambahan OOP
 */
public class Member {

    // [TAMBAHAN] Atribut private — Encapsulation
    private int    idMember;
    private String namaMember;
    private String noTelepon;
    private int    poin;

    // ── Constructor kosong ────────────────────────────────────
    // [TAMBAHAN]
    public Member() {
    }

    // ── Constructor lengkap ───────────────────────────────────
    // [TAMBAHAN]
    /**
     * @param idMember   ID unik member
     * @param namaMember nama member/pelanggan
     * @param noTelepon  nomor telepon/WhatsApp
     * @param poin       poin loyalitas yang dimiliki saat ini
     */
    public Member(int idMember, String namaMember, String noTelepon, int poin) {
        this.idMember   = idMember;
        this.namaMember = namaMember;
        this.noTelepon  = noTelepon;
        this.poin       = poin;
    }

    // ── Getter ────────────────────────────────────────────────
    // [TAMBAHAN]
    public int    getIdMember()    { return idMember; }
    public String getNamaMember()  { return namaMember; }
    public String getNoTelepon()   { return noTelepon; }
    public int    getPoin()        { return poin; }

    // ── Setter dengan validasi — Encapsulation ────────────────
    // [TAMBAHAN]
    public void setIdMember(int idMember) {
        if (idMember <= 0)
            throw new IllegalArgumentException("ID Member harus lebih dari 0.");
        this.idMember = idMember;
    }

    public void setNamaMember(String namaMember) {
        if (namaMember == null || namaMember.trim().isEmpty())
            throw new IllegalArgumentException("Nama member tidak boleh kosong.");
        this.namaMember = namaMember.trim();
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public void setPoin(int poin) {
        if (poin < 0)
            throw new IllegalArgumentException("Poin tidak boleh negatif.");
        this.poin = poin;
    }

    // ── Method bisnis domain ──────────────────────────────────

    /**
     * [TAMBAHAN] Menambahkan poin loyalitas berdasarkan total belanja (tipe int).
     * Aturan: setiap Rp 10.000 mendapatkan 1 poin (pembulatan ke bawah).
     *
     * @param totalBelanja total belanja dalam rupiah
     */
    public void tambahPoin(int totalBelanja) {
        tambahPoin((long) totalBelanja);
    }

    /**
     * [TAMBAHAN] Menambahkan poin loyalitas berdasarkan total belanja (tipe long).
     * Aturan: setiap Rp 10.000 mendapatkan 1 poin (pembulatan ke bawah).
     *
     * @param totalBelanja total belanja dalam rupiah
     */
    public void tambahPoin(long totalBelanja) {
        if (totalBelanja < 0)
            throw new IllegalArgumentException("Total belanja tidak boleh negatif.");
        int poinBaru = (int) (totalBelanja / 10000);
        this.poin += poinBaru;
    }

    /**
     * [TAMBAHAN] Menukarkan poin menjadi nominal diskon.
     * Aturan: setiap 10 poin = Rp 1.000 diskon.
     *
     * @param jumlahPoin jumlah poin yang ingin ditukarkan
     * @return nilai diskon dalam rupiah
     * @throws IllegalArgumentException jika poin tidak mencukupi
     */
    public int tukarPoin(int jumlahPoin) {
        if (jumlahPoin <= 0)
            throw new IllegalArgumentException("Jumlah poin harus lebih dari 0.");
        if (jumlahPoin > this.poin)
            throw new IllegalArgumentException(
                "Poin tidak mencukupi. Poin dimiliki: " + this.poin);
        this.poin -= jumlahPoin;
        return (jumlahPoin / 10) * 1000; // 10 poin = Rp 1.000
    }

    // [TAMBAHAN]
    @Override
    public String toString() {
        return "Member{id=" + idMember + ", nama='" + namaMember
                + "', noTelepon='" + noTelepon + "', poin=" + poin + '}';
    }
}

