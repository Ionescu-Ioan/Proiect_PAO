package aplicatieBancara.Client;

public class Adresa {

    private String strada;
    private String oras;
    private String tara;
    private int codPostal;

    public Adresa(String strada, String oras, String tara, int codPostal) {
        this.strada = strada;
        this.oras = oras;
        this.tara = tara;
        this.codPostal = codPostal;
    }

    @Override
    public String toString() {
        return "Adresa: " + "\n" +
                "Strada: " + strada + "\n" +
                "Oras: " + oras + "\n" +
                "Tara: " + tara + "\n" +
                "Cod postal: " + codPostal + "\n";
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }
}
