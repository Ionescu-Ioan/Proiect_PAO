package aplicatieBancara;
import aplicatieBancara.Card.*;
import aplicatieBancara.Client.*;
import aplicatieBancara.Cont.*;

import java.lang.ref.Cleaner;
import java.util.*;

import static aplicatieBancara.TipCard.*;

public class Servicii {

    //Asocieri intre IBAN-ul unui cont si id-ul clientului
    private Map<String, Integer> IBAN_Client = new HashMap<>();

    //Structura ce memoreaza clientii dupa id-ul de client
    private Map<Integer, Client> Clienti = new HashMap<>();


    public Client creareClient(String nume, String prenume, String CNP, Date dataNastere, String email, String telefon, String strada, String oras, String tara, String codPostal)
    {
        Adresa adresa = new Adresa(strada, oras, tara, codPostal);
        Client clientNou = new Client(nume,prenume, CNP, dataNastere, email, telefon, adresa);
        int idClient = clientNou.getIdClient();
        Clienti.put((Integer) idClient, clientNou);
        return clientNou;
    }

    public Cont creareCont(String numeTitular, int idClient)
    {
        Cont contNou = new Cont(numeTitular, idClient);
        String IBAN = contNou.getIBAN();
        Client client = Clienti.get(idClient);
        client.adaugaCont(contNou);
        IBAN_Client.put(IBAN, (Integer) idClient);
        return contNou;
    }

    public Card creareCard(Cont cont, TipCard tip)
    {
        GeneratorCard g = new GeneratorCard();
        Card cardNou;
        if(tip == CREDIT)
            cardNou = g.creareCardCredit(cont);
        else
            cardNou = g.creareCardDebit(cont);

        String IBAN = cont.getIBAN();
        int idClient = IBAN_Client.get(IBAN);

        Client client = Clienti.get(idClient);
        for(var c : client.getConturi())
        {
            if(c.getIBAN() == IBAN){
                c.adaugaCard(cardNou);
                break;
            }
        }
        return cardNou;
    }

    public void creareTranzactie(String IBANSursa, String IBANDestinatie , double suma, String descriere, TipTranzactie tipTranzactie, String numarCard) throws Exception {
        Tranzactie tranzactie = new Tranzactie(IBANSursa, IBANDestinatie , suma, descriere, tipTranzactie, numarCard);

        IBANSursa = tranzactie.getIBANSursa();
        IBANDestinatie = tranzactie.getIBANDestinatie();

        if(IBANSursa != "")
        {
            int idClient = IBAN_Client.get(IBANSursa);
            Client client = Clienti.get(idClient);
            for(var cont : client.getConturi())
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
            for(var cont : client.getConturi())
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
        for(var cont : client.getConturi())
        {
            System.out.println(cont.toString());
        }
    }

    public void afisareDetaliiBanca()
    {
        Banca.showBanca();
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
        System.out.println(cont.interogareSold());
    }

    public void eliminareCard(Card card)
    {
        String IBAN = card.getIBAN();
        int idClient = IBAN_Client.get(IBAN);
        Client client = Clienti.get(idClient);
        ArrayList<Cont> conturi = client.getConturi();

        for(var cont : conturi)
        {
            if(cont.getCarduri().contains(card))
            {
                cont.eliminaCard(card);
            }
        }
    }

}
