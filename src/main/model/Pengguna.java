
package main.model;

public abstract class Pengguna {

    private int    id;
    private String username;
    private String password;
    private String email;

    public Pengguna() {
    }

    public Pengguna(int id, String username, String password, String email) {
        this.id       = id;
        this.username = username;
        this.password = password;
        this.email    = email;
    }

    public int    getId()       { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail()    { return email; }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID harus lebih dari 0.");
        this.id = id;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("Username tidak boleh kosong.");
        this.username = username.trim();
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password tidak boleh kosong.");
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract String getHakAkses();

    public abstract String[] tampilkanMenu();

    public abstract String getDashboardTujuan();

    public boolean validasiDataLogin() {
        return username != null && !username.isEmpty()
            && password != null && !password.isEmpty();
    }

    @Override
    public String toString() {
        return getHakAkses() + "{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + '}';
    }
}

