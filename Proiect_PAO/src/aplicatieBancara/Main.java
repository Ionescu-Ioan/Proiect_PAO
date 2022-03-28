package aplicatieBancara;

import aplicatieBancara.Card.*;
import aplicatieBancara.Client.*;
import aplicatieBancara.Cont.*;

import java.util.ArrayList;
import java.util.Date;

import static aplicatieBancara.TipCard.*;
import static aplicatieBancara.TipTranzactie.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Servicii s = new Servicii();

        Banca banca = Banca.getBanca();

        //Afisare detalii banca
        s.afisareDetaliiBanca();

        //Creare client
        Client c1 = s.creareClient("Popescu", "Alexandru", "5623476212141", new Date(100, 9, 20), "popescua@gmail.com", "0767309787",
                "Valea Cricovului", "Bucuresti", "Romania", "061985");

        Client c2 = s.creareClient("Ionescu", "Andrei", "5423487212132", new Date(100, 11, 18), "ionescua@gmail.com", "0764509287",
                "Valea Furcii", "Bucuresti", "Romania", "061986");

        //Afisare date client
        s.afisareDateClient(c1);
        s.afisareDateClient(c2);

        //Creare cont
        Cont cont1 = s.creareCont("Popescu Alexandru", c1.getIdClient());
        Cont cont12 = s.creareCont("Popescu Alexandru", c1.getIdClient());
        Cont cont2 = s.creareCont("Ionescu Andrei", c2.getIdClient());

        //Listare conturi client
        s.afisareConturiClient(c1);
        System.out.println("---------------");
        s.afisareConturiClient(c2);

        //Creare card bancar
        Card card1 = s.creareCard(cont1, DEBIT);
        Card card12 = s.creareCard(cont1, DEBIT);
        Card card121 =s.creareCard(cont12, CREDIT);
        Card card2 = s.creareCard(cont2, DEBIT);

        //Efectuare tranzactie
        s.creareTranzactie("", cont2.getIBAN(), 150, "depunere 1", DEPUNERE, cont2.getCarduri().get(0).toString());

        //Interogare sold
        s.interogareSold(cont2);

        //Efectuare tranzactie
        s.creareTranzactie(cont2.getIBAN(), cont1.getIBAN(), 50, "transfer prin iban 1", TRANSFER, cont2.getCarduri().get(0).toString());

        //Interogare sold
        s.interogareSold(cont2);
        s.interogareSold(cont1);

        //Extras de cont
        s.afisareExtrasCont(cont2);
        System.out.println();
        //Eliminare card asociat unui cont

        ArrayList<Card> carduri_cont1 = cont1.getCarduri();
        ArrayList<Card> carduri_cont12 = cont12.getCarduri();
        ArrayList<Card> carduri_cont2 = cont2.getCarduri();
        for(var card : carduri_cont1)
        {
            System.out.println(card);
        }
        System.out.println("-----------------");
        s.eliminareCard(card12);

        for(var card : carduri_cont1)
        {
            System.out.println(card);
        }
        System.out.println("-----------------");

        for(var card : carduri_cont12)
        {
            System.out.println(card);
        }
        System.out.println("-----------------");

        for(var card : carduri_cont2)
        {
            System.out.println(card);
        }

    }
}
