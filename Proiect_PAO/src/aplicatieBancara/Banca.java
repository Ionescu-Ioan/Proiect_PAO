package aplicatieBancara;

public class Banca {

    private static String denumire;
    private static String sediulCentral;
    private static String contact;
    private static String codSwift;

    private static Banca banca;

    private Banca(){
        denumire = "Banca Transilvania";
        sediulCentral = "Cluj-Napoca, Cluj, str. Calea Dorobantilor nr. 30-36, 400117";
        contact = "0264 308 028";
        codSwift = "BTRLRO22";
    }

    public static Banca getBanca(){
        if(banca == null)
            banca = new Banca();
        return banca;
    }

    public static void showBanca(){

        System.out.println("Banca: " + denumire + "\n"
                + "Sediul central: " + sediulCentral + "\n"
                + "Contact: " + contact + "\n"
                + "Cod SWIFT: " + codSwift + "\n");
    }

    public static String getDenumire() {
        return denumire;
    }

    public static void setDenumire(String denumire) {
        Banca.denumire = denumire;
    }

    public static String getSediulCentral() {
        return sediulCentral;
    }

    public static void setSediulCentral(String sediulCentral) {
        Banca.sediulCentral = sediulCentral;
    }

    public static String getContact() {
        return contact;
    }

    public static void setContact(String contact) {
        Banca.contact = contact;
    }

    public static String getCodSwift() {
        return codSwift;
    }

    public static void setCodSwift(String codSwift) {
        Banca.codSwift = codSwift;
    }


}
