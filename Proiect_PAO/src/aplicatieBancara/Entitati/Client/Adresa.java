package aplicatieBancara.Entitati.Client;

import java.util.Objects;

public class Adresa {

    private String strada;
    private String oras;
    private String tara;
    private String codPostal;

    public Adresa(String strada, String oras, String tara, String codPostal) {
        this.strada = strada;
        this.oras = oras;
        this.tara = tara;
        this.codPostal = codPostal;
    }

    @Override
    public String toString() {
        return "Strada: " + strada + "\n" +
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

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresa adresa = (Adresa) o;
        return strada.equals(adresa.strada) && oras.equals(adresa.oras) && tara.equals(adresa.tara) && codPostal.equals(adresa.codPostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strada, oras, tara, codPostal);
    }
}
