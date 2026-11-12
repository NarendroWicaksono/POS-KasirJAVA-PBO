
package main.model;

public class Member {

    private int    idMember;
    private String namaMember;
    private String noTelepon;
    private int    poin;

    public Member() {
    }

    public Member(int idMember, String namaMember, String noTelepon, int poin) {
        this.idMember   = idMember;
        this.namaMember = namaMember;
        this.noTelepon  = noTelepon;
        this.poin       = poin;
    }

    public int    getIdMember()    { return idMember; }
    public String getNamaMember()  { return namaMember; }
    public String getNoTelepon()   { return noTelepon; }
    public int    getPoin()        { return poin; }

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

    public void tambahPoin(int totalBelanja) {
        tambahPoin((long) totalBelanja);
    }

    public void tambahPoin(long totalBelanja) {
        if (totalBelanja < 0)
            throw new IllegalArgumentException("Total belanja tidak boleh negatif.");
        int poinBaru = (int) (totalBelanja / 10000);
        this.poin += poinBaru;
    }

    public int tukarPoin(int jumlahPoin) {
        if (jumlahPoin <= 0)
            throw new IllegalArgumentException("Jumlah poin harus lebih dari 0.");
        if (jumlahPoin > this.poin)
            throw new IllegalArgumentException(
                "Poin tidak mencukupi. Poin dimiliki: " + this.poin);
        this.poin -= jumlahPoin;
        return (jumlahPoin / 10) * 1000;
    }

    @Override
    public String toString() {
        return "Member{id=" + idMember + ", nama='" + namaMember
                + "', noTelepon='" + noTelepon + "', poin=" + poin + '}';
    }
}

