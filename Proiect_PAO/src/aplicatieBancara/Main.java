package aplicatieBancara;

import aplicatieBancara.Entitati.Banca;
import aplicatieBancara.Entitati.Card.*;
import aplicatieBancara.Entitati.Client.*;
import aplicatieBancara.Entitati.Cont.*;
import aplicatieBancara.Servicii.Servicii;

import java.time.LocalDate;
import java.util.ArrayList;


import static aplicatieBancara.Entitati.TipCard.*;
import static aplicatieBancara.Entitati.TipTranzactie.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Servicii s = new Servicii();

        Banca banca = Banca.getBanca();

        //Afisare detalii banca
        System.out.println("DETALII BANCA:\n");
        s.afisareDetaliiBanca(banca);

        //Creare client
        Client c1 = s.creareClient("Popescu", "Alexandru", "5623476212141",  LocalDate.of(2000, 9, 20), "popescua@gmail.com", "0767309787",
                "Valea Cricovului", "Bucuresti", "Romania", "061985");

        Client c2 = s.creareClient("Ionescu", "Andrei", "5423487212132",  LocalDate.of(2000, 11, 18), "ionescua@gmail.com", "0764509287",
                "Valea Furcii", "Bucuresti", "Romania", "061986");

        //Afisare date client
        s.afisareDateClient(c1);
        s.afisareDateClient(c2);

        //Creare cont
        Cont cont1 = s.creareCont("Popescu Alexandru", c1.getIdClient(), banca);
        Cont cont12 = s.creareCont("Popescu Alexandru", c1.getIdClient(), banca);
        Cont cont2 = s.creareCont("Ionescu Andrei", c2.getIdClient(), banca);

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
        System.out.println("\n");
        s.creareTranzactie("", cont2.getIBAN(), 150, "depunere 1", DEPUNERE, s.getCarduri(cont2).get(0).toString());

        //Interogare sold
        System.out.println("INTEROGARE SOLD:");
        System.out.println("Sold client2:");
        s.interogareSold(cont2);

        //Efectuare tranzactie
        s.creareTranzactie(cont2.getIBAN(), cont1.getIBAN(), 50, "transfer prin iban 1", TRANSFER, s.getCarduri(cont2).get(0).toString());

        //Interogare sold
        System.out.println("Sold client2 dupa tranzactie:");
        s.interogareSold(cont2);
        System.out.println("Sold client1:");
        s.interogareSold(cont1);

        //Extras de cont
        System.out.println("EXTRAS DE CONT");
        System.out.println("Extras de cont pentru contul2(asociat clientului2)");
        s.afisareExtrasCont(cont2);
        System.out.println();
        //Eliminare card asociat unui cont

        ArrayList<Card> carduri_cont1 = s.getCarduri(cont1);
        ArrayList<Card> carduri_cont12 = s.getCarduri(cont12);
        ArrayList<Card> carduri_cont2 = s.getCarduri(cont2);
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
