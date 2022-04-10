package aplicatieBancara.Servicii;
import aplicatieBancara.Entitati.Banca;
import aplicatieBancara.Entitati.Card.*;
import aplicatieBancara.Entitati.Client.*;
import aplicatieBancara.Entitati.Cont.*;
import aplicatieBancara.Entitati.TipCard;
import aplicatieBancara.Entitati.TipTranzactie;
import aplicatieBancara.Entitati.Tranzactie;

import java.time.LocalDate;
import java.util.*;

import static aplicatieBancara.Entitati.TipCard.*;

public class Servicii {

    //Asocieri intre IBAN-ul unui cont si id-ul clientului
    private Map<String, Integer> IBAN_Client = new HashMap<>();

    //Structura ce memoreaza clientii dupa id-ul de client
    private Map<Integer, Client> Clienti = new HashMap<>();

    //Structura care tine evidenta tuturor conturilor dupa id-ul de client
    private Map<Integer, ArrayList<Cont>> Conturi = new HashMap<>();

    //Structura ce retine lista de carduri asociata fiecarui cont
    private Map<String, ArrayList<Card>> Carduri = new HashMap<>();

    public Client creareClient(String nume, String prenume, String CNP, LocalDate dataNastere, String email, String telefon, String strada, String oras, String tara, String codPostal)
    {
        Adresa adresa = new Adresa(strada, oras, tara, codPostal);
        Client clientNou = new Client(nume,prenume, CNP, dataNastere, email, telefon, adresa);
        int idClient = clientNou.getIdClient();
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


    public void eliminaCont(Cont cont){
        Integer idClent = IBAN_Client.get(cont.getIBAN());
        Conturi.get(idClent).remove(cont);
    }

    public Card creareCard(Cont cont, TipCard tip)
    {
        GeneratorCard generatorCard = new GeneratorCard();
        Card cardNou;
        if(tip == CREDIT)
            cardNou = generatorCard.creareCardCredit(cont);
        else
            cardNou = generatorCard.creareCardDebit(cont);

        String IBAN = cont.getIBAN();
        int idClient = IBAN_Client.get(IBAN);

        //Client client = Clienti.get(idClient);
        for(var c : Conturi.get((Integer)idClient))
        {
            if(c.getIBAN() == IBAN){
                adaugaCard(cardNou, c);
                break;
            }
        }
        return cardNou;
    }

    public void adaugaCard(Card card, Cont cont){
        String IBAN = cont.getIBAN();
        Carduri.get(IBAN).add(card);
    }

    public void adaugaCard(TipCard tip, Cont cont){
        Card cardNou = null;
        GeneratorCard generatorCard = null;
        switch (tip){
            case DEBIT:
                cardNou = generatorCard.creareCardDebit(cont);
                Carduri.get(cont.getIBAN()).add(cardNou);
                break;
            case CREDIT:
                cardNou = generatorCard.creareCardCredit(cont);
                Carduri.get(cont.getIBAN()).add(cardNou);
                break;
        }
    }

    public void eliminaCard(Card card, Cont cont){
        String IBAN = cont.getIBAN();
        Carduri.get(IBAN).remove(card);
    }

    public ArrayList<Card> getCarduri(Cont cont) {
        return Carduri.get(cont.getIBAN());
    }

    public void creareTranzactie(String IBANSursa, String IBANDestinatie , double suma, String descriere, TipTranzactie tipTranzactie, String numarCard) throws Exception {
        Tranzactie tranzactie = new Tranzactie(IBANSursa, IBANDestinatie , suma, descriere, tipTranzactie, numarCard);

        IBANSursa = tranzactie.getIBANSursa();
        IBANDestinatie = tranzactie.getIBANDestinatie();

        if(IBANSursa != "")
        {
            int idClient = IBAN_Client.get(IBANSursa);
            Client client = Clienti.get(idClient);
            for(var cont : Conturi.get((Integer)idClient))
            {
                if(cont.getIBAN() == IBANSursa)
                {
                    cont.actualizareSold(-suma);
                    cont.adaugaTranzactie(tranzactie);
                }
            }
        }

        if(IBANDestinatie != "")
        {
            int idClient = IBAN_Client.get(IBANDestinatie);
            Client client = Clienti.get(idClient);
            for(var cont : Conturi.get((Integer)idClient))
            {
                if(cont.getIBAN() == IBANDestinatie)
                {
                    cont.actualizareSold(suma);
                    cont.adaugaTranzactie(tranzactie);
                }
            }
        }

    }

    public void afisareDateClient(Client client)
    {
        System.out.println(client.toString());
    }

    public void afisareConturiClient(Client client)
    {
        Integer idClient = client.getIdClient();
        for(var cont : Conturi.get(idClient))
        {
            System.out.println(cont.toString());
        }
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
        System.out.println(cont.getSold());
    }

    public void eliminareCard(Card card)
    {
        String IBAN = card.getIBAN();
        int idClient = IBAN_Client.get(IBAN);
        Client client = Clienti.get(idClient);
        ArrayList<Cont> conturi = getConturi(client);

        for(var cont : conturi)
        {
            if(getCarduri(cont).contains(card))
            {
                eliminaCard(card, cont);
            }
        }
    }

}
