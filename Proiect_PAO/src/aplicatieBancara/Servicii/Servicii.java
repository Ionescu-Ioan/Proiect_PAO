package aplicatieBancara.Servicii;
import aplicatieBancara.Entitati.*;
import aplicatieBancara.Entitati.Card.*;
import aplicatieBancara.Entitati.Client.*;
import aplicatieBancara.Entitati.Cont.*;

import java.time.LocalDate;
import java.util.*;

import static aplicatieBancara.Entitati.TipCard.*;

public class Servicii {

    static int idClienti = 0;
    static int idCarduri = 0;

    public void setIdMaxClienti(int nr)
    {
        idClienti = nr;
    }
    public void setIdMaxCarduri(int nr)
    {
        idCarduri = nr;
    }

    //Asocieri intre IBAN-ul unui cont si id-ul clientului
    private Map<String, Integer> IBAN_Client = new HashMap<>();

    public void mapareIBANClient(List<Cont> listaConturi)
    {
        for(var cont : listaConturi)
        {
            String iban = cont.getIBAN();
            int idClient = cont.getIdClient();
            IBAN_Client.put(iban, idClient);
        }
    }

    //Structura ce memoreaza clientii dupa id-ul de client
    private Map<Integer, Client> Clienti = new HashMap<>();

    public void mapareClientiId(List<Client> listaClienti)
    {
        for(var client : listaClienti)
        {
            int idClient = client.getIdClient();
            Clienti.put(idClient, client);
        }
    }

    //Structura care tine evidenta tuturor conturilor dupa id-ul de client
    private Map<Integer, ArrayList<Cont>> Conturi = new HashMap<>();

//    public void mapareConturiIdClient(List<Cont> listaConturi)
//    {
//        for(var cont : listaConturi)
//        {
//            ArrayList<Cont> temp = new ArrayList<>();
//            Integer idClient = cont.getIdClient();
//            if(!Conturi.containsKey(idClient))
//            {
//                temp.add(cont);
//                Conturi.put(idClient, temp);
//            }
//            else
//            {
//                Conturi.get(idClient).add(cont);
//            }
//
//        }
//    }

    public void mapareConturiIdClient(List<Cont> listaConturi, List<Client> listaClienti)
    {
        for(var client : listaClienti)
            Conturi.put(client.getIdClient(), new ArrayList<Cont>());

        for(var client : listaClienti)
        {
            Integer idClient = client.getIdClient();
            for(var cont : listaConturi)
                if(idClient == cont.getIdClient())
                    Conturi.get(idClient).add(cont);
        }
    }

    //Structura ce retine lista de carduri asociata fiecarui cont
    private Map<String, ArrayList<Card>> Carduri = new HashMap<>();

    public void mapareCarduriCont(List<Card> listaCarduri)
    {
        for(var card : listaCarduri)
        {
            String iban = card.getIBAN();
            if(!Carduri.containsKey(iban))
            {
                ArrayList<Card> temp = new ArrayList<Card>();
                temp.add(card);
                Carduri.put(iban, temp);
            }
            else
                Carduri.get(iban).add(card);
        }

    }

    public Client creareClient(String nume, String prenume, String CNP, LocalDate dataNastere, String email, String telefon, String strada, String oras, String tara, String codPostal)
    {
        idClienti = idClienti + 1;
        Adresa adresa = new Adresa(strada, oras, tara, codPostal);
        Client clientNou = new Client(idClienti,nume,prenume, CNP, dataNastere, email, telefon, adresa);
        int idClient = clientNou.getIdClient();
        adresa.setIdClient(idClient);
        Clienti.put((Integer) idClient, clientNou);
        Conturi.put((Integer) idClient, new ArrayList<Cont>());
        return clientNou;
    }

    public Cont creareCont(String numeTitular, int idClient, Banca banca)
    {
        Cont contNou = new Cont(numeTitular, idClient, banca);
        String IBAN = contNou.getIBAN();
        Client client = Clienti.get(idClient);
        Conturi.get((Integer) idClient).add(contNou);
        IBAN_Client.put(IBAN, (Integer) idClient);
        Carduri.put(IBAN, new ArrayList<Card>());
        return contNou;
    }

    public ArrayList<Cont> getConturi(Client client)
    {
        return Conturi.get(client.getIdClient());
    }

//    public Cont getContByIBAN(String iban)
//    {
//        ArrayList<Cont> conturiClient;
//        for(int i = 1; i <= idClienti; ++i)
//        {
//            conturiClient = Conturi.get(i);
//            for(var cont : conturiClient)
//                if(cont.getIBAN().equals(iban))
//                    return cont;
//        }
//        return null;
//    }

    public Cont getContByIBAN(String iban, List<Cont> conturi)
    {
        for(var cont : conturi)
        {
            if(cont.getIBAN().equals(iban))
                return cont;
        }
        return null;
    }

    public void eliminaCont(Cont cont){
        Integer idClent = IBAN_Client.get(cont.getIBAN());
        Conturi.get(idClent).remove(cont);
    }

    public Card creareCard(Cont cont, TipCard tip)
    {
        GeneratorCard generatorCard = new GeneratorCard();
        Card cardNou;
        if(tip == CREDIT)
            cardNou = generatorCard.creareCardCredit(cont, idCarduri);
        else
            cardNou = generatorCard.creareCardDebit(cont, idCarduri);


        String IBAN = cont.getIBAN();
        int idClient = IBAN_Client.get(IBAN);

        //Client client = Clienti.get(idClient);
        for(var c : Conturi.get((Integer)idClient))
        {
            if(c.getIBAN().equals(IBAN)){

                adaugaCard(cardNou, c);
                break;
            }
        }
        return cardNou;
    }

    public void adaugaCard(Card card, Cont cont){
        String IBAN = cont.getIBAN();
        if(Carduri.containsKey(IBAN))
            Carduri.get(IBAN).add(card);
        else
        {
            Carduri.put(IBAN, new ArrayList<Card>());
            Carduri.get(IBAN).add(card);
        }
    }

    public void adaugaCard(TipCard tip, Cont cont){
        Card cardNou = null;
        GeneratorCard generatorCard = null;
        switch (tip){
            case DEBIT:
                cardNou = generatorCard.creareCardDebit(cont, idCarduri);
                Carduri.get(cont.getIBAN()).add(cardNou);
                break;
            case CREDIT:
                cardNou = generatorCard.creareCardCredit(cont, idCarduri);
                Carduri.get(cont.getIBAN()).add(cardNou);
                break;
        }
    }

    public void eliminaCard(Card card, Cont cont){

        String IBAN = cont.getIBAN();
        Carduri.get(IBAN).remove(card);
        if(Carduri.get(IBAN).isEmpty())
            Carduri.remove(IBAN);
    }

    public void afisareCarduriCont(String iban)
    {

        if(!Carduri.containsKey(iban))
        {
            System.out.println("Nu exista carduri asociate acestui cont!");
        }
        else{
            for(var card : Carduri.get(iban))
                System.out.println(card);
        }
    }

    public ArrayList<Card> getCarduriCont(Cont cont) {
        return Carduri.get(cont.getIBAN());
    }
    public ArrayList<Card> getCarduri()
    {
        ArrayList<Card> carduri = new ArrayList<>();
        for(Map.Entry<String, ArrayList<Card>> entry : Carduri.entrySet())
        {
            carduri.addAll(entry.getValue());
        }
        return carduri;
    }

    public Tranzactie creareTranzactie(String IBANSursa, String IBANDestinatie , double suma, String descriere, TipTranzactie tipTranzactie) throws Exception {
        Tranzactie tranzactie = new Tranzactie(IBANSursa, IBANDestinatie , suma, descriere, tipTranzactie);

        IBANSursa = tranzactie.getIBANSursa();
        IBANDestinatie = tranzactie.getIBANDestinatie();

        if(!IBANSursa.equals(""))
        {
            int idClient = IBAN_Client.get(IBANSursa);
            Client client = Clienti.get(idClient);
            for(var cont : Conturi.get((Integer)idClient))
            {
                if(cont.getIBAN().equals(IBANSursa))
                {
                    cont.actualizareSold(-suma);
                    cont.adaugaTranzactie(tranzactie);
                }
            }
        }
        else if(!IBANDestinatie.equals(""))
        {
            int idClient = IBAN_Client.get(IBANDestinatie);
            Client client = Clienti.get(idClient);
            for(var cont : Conturi.get((Integer)idClient))
            {
                if(cont.getIBAN().equals(IBANDestinatie))
                {
                    cont.actualizareSold(suma);
                    cont.adaugaTranzactie(tranzactie);
                }
            }
        }
        else if(!IBANDestinatie.equals("") && !IBANSursa.equals("")) {

            int idClient1 = IBAN_Client.get(IBANDestinatie);
            int idClient2 = IBAN_Client.get(IBANSursa);
            Client client1 = Clienti.get(idClient1);
            Client client2 = Clienti.get(idClient2);

            for (var cont : Conturi.get((Integer) idClient1)) {
                if (cont.getIBAN().equals(IBANDestinatie)) {
                    cont.actualizareSold(suma);
                    cont.adaugaTranzactie(tranzactie);
                }
            }

            for (var cont : Conturi.get((Integer) idClient2)) {
                if (cont.getIBAN().equals(IBANSursa)) {
                    cont.actualizareSold(-suma);
                    cont.adaugaTranzactie(tranzactie);
                }
            }
        }
        return tranzactie;
    }

    public void afisareDateClient(Client client)
    {
        System.out.println(client.toString());
    }

    public void afisareConturiClient(Client client)
    {
        Integer idClient = client.getIdClient();
        if(Conturi.get(idClient).isEmpty())
        {
            System.out.println("Nu exista conturi asociate acestui client!");
        }
        else{
            for(var cont : Conturi.get(idClient))
            {
                System.out.println(cont.toString());
            }
        }

    }

    public Client getClientById(Integer id)
    {
        return Clienti.get(id);
    }

    public void afisareDetaliiBanca(Banca banca)
    {
        banca.showBanca();
    }

    public void afisareExtrasCont(Cont cont)
    {
        for(var tranzactie : cont.getTranzactii())
        {
            System.out.println(tranzactie.toString());
        }
    }

    public void interogareSold(Cont cont)
    {
        if(cont == null)
        {
            System.out.println("IBAN incorect!");
        }
        else
            System.out.println(cont.getSold() + " RONI");
    }

    public void eliminareCard(Card card)
    {
        String IBAN = card.getIBAN();
        int idClient = IBAN_Client.get(IBAN);
        Client client = Clienti.get(idClient);
        ArrayList<Cont> conturi = getConturi(client);

        for(var cont : conturi)
        {
            if(getCarduriCont(cont).contains(card))
            {
                eliminaCard(card, cont);
            }
        }
    }

    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servicii servicii = (Servicii) o;
        return Objects.equals(IBAN_Client, servicii.IBAN_Client) && Objects.equals(Clienti, servicii.Clienti) && Objects.equals(Conturi, servicii.Conturi) && Objects.equals(Carduri, servicii.Carduri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN_Client, Clienti, Conturi, Carduri);
    }
}
