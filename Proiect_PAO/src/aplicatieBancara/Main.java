package aplicatieBancara;

import aplicatieBancara.Entitati.Banca;
import aplicatieBancara.Entitati.Card.*;
import aplicatieBancara.Entitati.Client.*;
import aplicatieBancara.Entitati.Cont.*;
import aplicatieBancara.Entitati.SingletonTranzactie;
import aplicatieBancara.Entitati.TipTranzactie;
import aplicatieBancara.Entitati.Tranzactie;
import aplicatieBancara.Servicii.Audit;
import aplicatieBancara.Servicii.Servicii;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import static aplicatieBancara.Entitati.TipCard.*;
import static aplicatieBancara.Entitati.TipTranzactie.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Servicii s = new Servicii();
        Audit a = new Audit();
        Banca banca = Banca.getBanca();

        System.out.println("Citire date...");

        SingletonAdresa sadresa = new SingletonAdresa();
        SingletonClient sclient = new SingletonClient();
        SingletonTranzactie stranzactie = new SingletonTranzactie();
        SingletonCont scont = new SingletonCont();
        SingletonCard scard = new SingletonCard();

        sadresa.construireAdrese();
        sclient.construireClienti(sadresa.getAdrese());
        scont.construireConturi();
        scard.construireCarduri(scont.getConturi());
        stranzactie.construireTranzactii();
        List<Cont> conturi = scont.getConturi();
        //asociere tranzactii citite cu conturile corespunzatoare
        List<Tranzactie> tranzactii = stranzactie.getTranzactii();

        for(var tranzactie : tranzactii)
        {
            if(tranzactie.getTipTranzactie() == TRANSFER) {
                String IBANSursa = tranzactie.getIBANSursa();
                String IBANDestinatie = tranzactie.getIBANDestinatie();
                Cont contSursa = s.getContByIBAN(IBANSursa, conturi);
                Cont contDestinatie = s.getContByIBAN(IBANDestinatie, conturi);
                contSursa.getTranzactii().add(tranzactie);
                contDestinatie.getTranzactii().add(tranzactie);
            }
            else if(tranzactie.getTipTranzactie() == DEPUNERE)
            {
                String IBANDestinatie = tranzactie.getIBANDestinatie();
                Cont contDestinatie = s.getContByIBAN(IBANDestinatie, conturi);;
                contDestinatie.getTranzactii().add(tranzactie);
            }
            else if(tranzactie.getTipTranzactie() == RETRAGERE)
            {
                String IBANSursa = tranzactie.getIBANSursa();
                Cont contSursa = s.getContByIBAN(IBANSursa, conturi);;
                contSursa.getTranzactii().add(tranzactie);
            }
        }

        s.mapareClientiId(sclient.getClienti());
        s.mapareIBANClient(scont.getConturi());
        //s.mapareConturiIdClient(scont.getConturi());
        s.mapareConturiIdClient(scont.getConturi(), sclient.getClienti());
        s.mapareCarduriCont(scard.getCarduri());
        a.construireIstoric();

        int idMax1 = 0;
        for(var client : sclient.getClienti())
        {
            int id = client.getIdClient();
            if( id > idMax1)
                idMax1 = id;
        }

        s.setIdMaxClienti(idMax1);

        int idMax2 = 0;
        for(var card : scard.getCarduri())
        {
            int id = card.getCardId();
            if( id > idMax2)
                idMax2 = id;
        }
        s.setIdMaxCarduri(idMax2);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        System.out.println("Date cititie cu succes!");

        while(!exit)
        {
            System.out.println("MENIU");
            System.out.println("-----");
            System.out.println("0.Exit");
            System.out.println("1.Afisare detalii banca");
            System.out.println("2.Creare client");
            System.out.println("3.Afisare date client");
            System.out.println("4.Creare cont");
            System.out.println("5.Listare conturi client");
            System.out.println("6.Creare card bancar");
            System.out.println("7.Efectuare tranzactie");
            System.out.println("8.Interogare sold");
            System.out.println("9.Afisare extras de cont");
            System.out.println("10.Eliminare card asociat unui cont");
            System.out.println("11.Afisare carduri asociat unui cont");

            int optiune = scanner.nextInt();
            scanner.nextLine();
            switch (optiune)
            {
                case 0:
                    exit = true;
                    break;
                case 1:
                    System.out.println("DETALII BANCA:\n");
                    s.afisareDetaliiBanca(banca);
                    a.adaugareActiune("Afisare detalii banca");
                    a.scriereAudit();
                    break;
                case 2:
                    System.out.println("Nume: ");
                    String nume = scanner.nextLine();
                    System.out.println("Prenume: ");
                    String prenume = scanner.nextLine();
                    System.out.println("CNP: ");
                    String CNP = scanner.nextLine();
                    System.out.println("Data nasterii(an luna zi): ");
                    int an = scanner.nextInt();
                    int luna = scanner.nextInt();
                    int zi = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("email: ");
                    String email = scanner.nextLine();
                    System.out.println("telefon: ");
                    String telefon = scanner.nextLine();
                    System.out.println("Adresa: ");
                    System.out.println("Strada: ");
                    String strada = scanner.nextLine();
                    System.out.println("Oras: ");
                    String oras = scanner.nextLine();
                    System.out.println("Tara: ");
                    String tara = scanner.nextLine();
                    System.out.println("Cod postal: ");
                    String codPostal = scanner.nextLine();

                    Client client = s.creareClient(nume, prenume, CNP, LocalDate.of(an, luna, zi), email, telefon,
                            strada, oras, tara, codPostal);
                    Adresa adresa = new Adresa(strada, oras, tara, codPostal);
                    adresa.setIdClient(client.getIdClient());
                    sadresa.adaugareAdresa(adresa);
                    sadresa.scriereAdrese();
                    sclient.adaugareClient(client);
                    sclient.scriereClienti();
                    a.adaugareActiune("Creare client");
                    a.scriereAudit();
                    break;
                case 3:
                    System.out.println("Id-ul clientului: ");
                    Integer id1 = scanner.nextInt();
                    scanner.nextLine();
                    Client client1 = s.getClientById(id1);
                    if (client1 != null) {
                        System.out.println("Date client: ");
                        s.afisareDateClient(client1);
                        a.adaugareActiune("Afisare date client");
                        a.scriereAudit();
                        break;
                    } else {
                        System.out.println("Id-ul specificat nu exista!");
                        break;
                    }
                case 4:
                    System.out.println("Nume complet titular: ");
                    String numeTitular = scanner.nextLine();
                    System.out.println("Id client: ");
                    Integer idClient = scanner.nextInt();
                    scanner.nextLine();
                    Cont c = s.creareCont(numeTitular, idClient, banca);
                    scont.adaugareCont(c);
                    scont.scriereConturi();
                    a.adaugareActiune("Creare cont");
                    a.scriereAudit();
                    break;
                case 5:
                    System.out.println("Id-ul clientului: ");
                    Integer id2 = scanner.nextInt();
                    scanner.nextLine();
                    Client client2 = s.getClientById(id2);
                    if (client2 != null) {
                        System.out.println("Conturi: ");
                        s.afisareConturiClient(client2);
                        a.adaugareActiune("Listare conturi client");
                        a.scriereAudit();
                        break;
                    }
                    else{
                        System.out.println("Id-ul specificat nu exista!");
                        break;
                    }
                case 6:
                    System.out.println("Id-ul clientului: ");
                    Integer id3 = scanner.nextInt();
                    scanner.nextLine();
                    Client client3 = s.getClientById(id3);
                    System.out.println("IBAN-ul contului: ");
                    String iban = scanner.nextLine();

                    System.out.println("Tipul cardului: \n1.DEBIT\n2.CREDIT");
                    int tipCard = scanner.nextInt();
                    scanner.nextLine();
                    ArrayList<Cont> conturiClient = s.getConturi(client3);
                    Cont contCurent = null;
                    for(var cont : conturiClient)
                        if(cont.getIBAN().equals(iban))
                           contCurent = cont;
                    Card cardNou;
                    if(tipCard == 1)
                        cardNou = s.creareCard(contCurent, DEBIT);
                    else if(tipCard == 2)
                        cardNou = s.creareCard(contCurent, CREDIT);
                    else{
                        System.out.println("Tip card invalid!");
                        break;
                    }
                    scard.adaugareCard(cardNou);
                    scard.scriereCarduri();
                    a.adaugareActiune("Creare card bancar");
                    a.scriereAudit();
                    break;

                case 7:
                    System.out.println("Tipul tranzactiei: \n1.DEPUNERE\n2.RETRAGERE\n3.TRANSFER");
                    int tipTranzactie = scanner.nextInt();
                    scanner.nextLine();
                    if (tipTranzactie == 1) {
                        String ibanSursa = "";
                        System.out.println("IBAN-ul contului in care se face depunerea: ");
                        String ibanDestinatie = scanner.nextLine();
                        System.out.println("Suma depusa: ");
                        double suma = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("Descriere: ");
                        String descriere = scanner.nextLine();
                        Tranzactie t = s.creareTranzactie(ibanSursa, ibanDestinatie, suma, descriere, DEPUNERE);
                        stranzactie.adaugareTranzactie(t);
                        stranzactie.scriereTranzactii();
                        scont.scriereConturi();
                    } else if (tipTranzactie == 2) {
                        String ibanDestinatie = "";
                        System.out.println("IBAN-ul contului din care se face retrgerea: ");
                        String ibanSursa = scanner.nextLine();
                        Cont cont = s.getContByIBAN(ibanSursa, conturi);
                        eticheta2:
                        {
                            System.out.println("Suma retrasa: ");
                            double suma = scanner.nextDouble();
                            scanner.nextLine();
                            if (suma > cont.getSold()) {
                                System.out.println("Fonduri insuficiente!");
                                break eticheta2;
                            }
                            System.out.println("Descriere: ");
                            String descriere = scanner.nextLine();
                            Tranzactie t = s.creareTranzactie(ibanSursa, ibanDestinatie, suma, descriere, RETRAGERE);
                            stranzactie.adaugareTranzactie(t);
                            stranzactie.scriereTranzactii();
                            scont.scriereConturi();
                        }
                    } else if(tipTranzactie == 3){

                        System.out.println("IBAN Sursa: ");
                        String ibanSursa = scanner.nextLine();
                        System.out.println("IBAN Destinatie: ");
                        String ibanDestinatie = scanner.nextLine();
                        Cont contSursa = s.getContByIBAN(ibanSursa, conturi);
                        //Cont contDestinatie = s.getContByIBAN(ibanDestinatie);

                        System.out.println("Suma transferata: ");
                        double suma = scanner.nextDouble();
                        scanner.nextLine();
                        if (suma > contSursa.getSold()) {
                            System.out.println("Fonduri insuficiente!");
                            break;
                        }
                        System.out.println("Descriere: ");
                        String descriere = scanner.nextLine();
                        Tranzactie t = s.creareTranzactie(ibanSursa, ibanDestinatie, suma, descriere, TRANSFER);
                        stranzactie.adaugareTranzactie(t);
                        stranzactie.scriereTranzactii();
                        scont.scriereConturi();

                    } else
                        break;
                    a.adaugareActiune("Efectuare tranzactie");
                    a.scriereAudit();
                    break;
                case 8:
                    System.out.println("IBAN-ul contului: ");
                    String iban1 = scanner.nextLine();
                    Cont cont = s.getContByIBAN(iban1, conturi);
                    System.out.println("Sold: ");
                    s.interogareSold(cont);
                    a.adaugareActiune("Interogare sold");
                    a.scriereAudit();
                    break;
                case 9:
                    System.out.println("IBAN-ul contului: ");
                    String iban2 = scanner.nextLine();
                    Cont cont1 = s.getContByIBAN(iban2, conturi);
                    System.out.println("Extras de cont: ");
                    s.afisareExtrasCont(cont1);
                    a.adaugareActiune("Afisare extras de cont");
                    a.scriereAudit();
                    break;
                case 10:
                    System.out.println("IBAN-ul contului: ");
                    String iban3 = scanner.nextLine();
                    Cont cont2 = s.getContByIBAN(iban3, conturi);

                    System.out.println("ID-ul cardului: ");
                    int idCard = scanner.nextInt();
                    scanner.nextLine();
                    ArrayList<Card> carduri = s.getCarduriCont(cont2);
                    boolean ok = false;
                    Card cardSters = null;
                    for (var card : carduri)
                        if (card.getCardId() == idCard) {
                            cardSters = card;
                            ok = true;
                        }
                    if(cardSters != null)
                    {
                        s.eliminaCard(cardSters, cont2);
                    }

                    if (!ok) {
                        System.out.println("ID de card incorect!");
                        break;
                    }
                    System.out.println("Card eliminat cu succes!");
                    a.adaugareActiune("Eliminare card asociat unui cont");
                    a.scriereAudit();
                    break;
                case 11:
                    System.out.println("IBAN-ul contului: ");
                    String iban4 = scanner.nextLine();
                    System.out.println("Carduri:");
                    s.afisareCarduriCont(iban4);
                    a.adaugareActiune("Afisare carduri asociate unui cont");
                    a.scriereAudit();
                    scard.setCarduri(s.getCarduri());
                    scard.scriereCarduri();
                    break;
                default:
                    System.out.println("Va rugam alegeti o optiune valida!");
            }

        }
    }
}
