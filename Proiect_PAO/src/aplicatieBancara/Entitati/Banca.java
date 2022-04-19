package aplicatieBancara.Entitati;

import java.util.Objects;

public class Banca {

    private  String denumire;
    private  String sediulCentral;
    private  String contact;
    private  String codSwift;
    private  String prefixIBAN;


    private static Banca banca;

    private Banca(){
        this.denumire = "Banca Transilvania";
        this.sediulCentral = "Cluj-Napoca, Cluj, str. Calea Dorobantilor nr. 30-36, 400117";
        this.contact = "0264 308 028";
        this.codSwift = "BTRLRO22";
        this.prefixIBAN = "RO05BTRLRONCRT";
    }

    public static Banca getBanca(){
        if(banca == null)
            banca = new Banca();
        return banca;
    }

    public void showBanca(){

        System.out.println("Banca: " + denumire + "\n"
                + "Sediul central: " + sediulCentral + "\n"
                + "Contact: " + contact + "\n"
                + "Cod SWIFT: " + codSwift + "\n");
    }

    public  String getDenumire() {
        return denumire;
    }

    public  String getSediulCentral() {
        return sediulCentral;
    }

    public  String getContact() {
        return contact;
    }

    public  String getCodSwift() {
        return codSwift;
    }

    public  String getPrefixIBAN() {
        return prefixIBAN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banca banca = (Banca) o;
        return denumire.equals(banca.denumire) && sediulCentral.equals(banca.sediulCentral) && contact.equals(banca.contact) && codSwift.equals(banca.codSwift) && prefixIBAN.equals(banca.prefixIBAN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(denumire, sediulCentral, contact, codSwift, prefixIBAN);
    }
}
