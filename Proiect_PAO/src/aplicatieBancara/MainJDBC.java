package aplicatieBancara;

import aplicatieBancara.Config.DatabaseConfiguration;
import aplicatieBancara.Entitati.Banca;
import aplicatieBancara.Entitati.Card.*;
import aplicatieBancara.Entitati.Client.*;
import aplicatieBancara.Entitati.Cont.*;
import aplicatieBancara.Entitati.RepositoryTranzactie;
import aplicatieBancara.Entitati.Tranzactie;
import aplicatieBancara.Entitati.User.*;
import aplicatieBancara.Servicii.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static aplicatieBancara.Entitati.TipCard.*;
import static aplicatieBancara.Entitati.TipTranzactie.*;

public class MainJDBC {
    public static void main(String[] args) throws Exception {

        //creare tabele
        RepositoryAudit auditRep = RepositoryAudit.getInstance();
        auditRep.createTable();

        AdminRepository admRep = AdminRepository.getInstance();
        admRep.createTable();

        UserRepository userRep = UserRepository.getInstance();
        userRep.createTable();

        RepositoryAdresa adresaRep = RepositoryAdresa.getInstance();
        adresaRep.createTable();

        RepositoryCard cardRep = RepositoryCard.getInstance();
        cardRep.createTable();

        RepositoryClient clientRep = RepositoryClient.getInstance();
        clientRep.createTable();

        RepositoryCont contRep = RepositoryCont.getInstance();
        contRep.createTable();

        RepositoryTranzactie tranzactieRep = RepositoryTranzactie.getInstance();
        tranzactieRep.createTable();

        Servicii s = new Servicii();
        Audit a = new Audit();
        Banca banca = Banca.getBanca();

        System.out.println("Citire date...");

        SingletonScriere scriere = SingletonScriere.getInstance();
        SingletonCitire citire = SingletonCitire.getInstance();

        citire.construireAdreseJDBC();
        List<Adresa> adrese = citire.getAdrese();
        citire.construireClientiJDBC(adrese);
        List<Client> clienti = citire.getClienti();
        citire.construireConturiJDBC();
        List<Cont> conturi = citire.getConturi();
        citire.construireCarduriJDBC(conturi);
        List<Card> carduri = citire.getCarduri();
        citire.construireTranzactiiJDBC();
        //asociere tranzactii citite cu conturile corespunzatoare
        List<Tranzactie> tranzactii = citire.getTranzactii();
        citire.construireUsersJDBC();
        List<User> users = citire.getUsers();
        citire.construireAdminsJDBC();
        List<Admin> admins = citire.getAdmins();

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

        s.mapareClientiId(clienti);
        s.mapareIBANClient(conturi);
        //s.mapareConturiIdClient(scont.getConturi());
        s.mapareConturiIdClient(conturi, clienti);
        s.mapareCarduriCont(carduri);
        s.setareUseri(users);
        s.setareAdmini(admins);
        //a.construireIstoric();

        int idMax1 = 0;
        for(var client : clienti)
        {
            int id = client.getIdClient();
            if( id > idMax1)
                idMax1 = id;
        }

        s.setIdMaxClienti(idMax1);

        int idMax2 = 0;
        for(var card : carduri)
        {
            int id = card.getCardId();
            if( id > idMax2)
                idMax2 = id;
        }
        s.setIdMaxCarduri(idMax2);

        int idMax3 = 0;
        for(var admin : admins)
        {
            int id = admin.getId();
            if( id > idMax3)
                idMax3 = id;
        }
        s.setIdMaxAdmini(idMax3);

        Scanner scanner = new Scanner(System.in);
        boolean exit0 = false;

        System.out.println("Date cititie cu succes!");

        while(!exit0)
        {
            System.out.println("------------------LOGIN-----------------");
            System.out.println("Alegeti tipul de cont:");
            System.out.println("1.Admin");
            System.out.println("2.Client");
            System.out.println("3.Nu am cont, doresc sa ma inregistrez");
            System.out.println("4.Exit");

            int op = scanner.nextInt();
            scanner.nextLine();
            switch(op)
            {
                case 1:
                    System.out.println("Username: ");
                    String username = scanner.nextLine();
                    String parola="";
                    boolean ok0 = false;
                    for(var admin : admins)
                        if(admin.getUsername().equals(username))
                        {
                            ok0 = true;
                            parola = admin.getParola();

                        }
                    if(!ok0)
                    {
                        System.out.println("Username-ul specificat nu exista!");
                        break;
                    }
                    System.out.println("Parola: ");
                    String pass = scanner.nextLine();
                    if(!parola.equals(pass))
                    {
                        System.out.println("Parola incorecta!");
                        break;
                    }

                    System.out.println("V-ati autentificat cu succes!");
                    auditRep.addActiune("Autentificare admin");
                    //a.adaugareActiune("Autentificare admin");
                    boolean exit1 = false;

                    while(!exit1)
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
                        System.out.println("7.Interogare sold");
                        System.out.println("8.Afisare extras de cont");
                        System.out.println("9.Eliminare card asociat unui cont");
                        System.out.println("10.Afisare carduri asociat unui cont");
                        System.out.println("11.Inregistrare admin");

                        int optiune = scanner.nextInt();
                        scanner.nextLine();
                        switch (optiune)
                        {
                            case 0:
                                exit1 = true;
                                break;
                            case 1:
                                System.out.println("DETALII BANCA:\n");
                                s.afisareDetaliiBanca(banca);
                                auditRep.addActiune("Afisare detalii banca");
                                //a.adaugareActiune("Afisare detalii banca");
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
                                scriere.adaugareJDBC(adresa);
                                scriere.adaugareJDBC(client);
                                auditRep.addActiune("Creare client");
                                //a.adaugareActiune("Creare client");
                                break;
                            case 3:
                                System.out.println("Id-ul clientului: ");
                                Integer id1 = scanner.nextInt();
                                scanner.nextLine();
                                Client client1 = s.getClientById(id1);
                                if (client1 != null) {
                                    System.out.println("Date client: ");
                                    s.afisareDateClient(client1);
                                    auditRep.addActiune("Afisare date client");
                                    //a.adaugareActiune("Afisare date client");
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
                                scriere.adaugareJDBC(c);
                                auditRep.addActiune("Creare cont");
                                //a.adaugareActiune("Creare cont");
                                break;
                            case 5:
                                System.out.println("Id-ul clientului: ");
                                Integer id2 = scanner.nextInt();
                                scanner.nextLine();
                                Client client2 = s.getClientById(id2);
                                if (client2 != null) {
                                    System.out.println("Conturi: ");
                                    s.afisareConturiClient(client2);
                                    auditRep.addActiune("Listare conturi client");
                                    //a.adaugareActiune("Listare conturi client");
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
                                if(client3 == null)
                                {
                                    System.out.println("Id-ul specificat nu exista!");
                                    break;
                                }
                                System.out.println("IBAN-ul contului: ");
                                String iban = scanner.nextLine();


                                ArrayList<Cont> conturiClient = s.getConturi(client3);
                                Cont contCurent = null;
                                for(var cont : conturiClient)
                                    if(cont.getIBAN().equals(iban))
                                        contCurent = cont;

                                if(contCurent == null)
                                {
                                    System.out.println("IBAN-ul specificat este incorect!");
                                    break;
                                }

                                System.out.println("Tipul cardului: \n1.DEBIT\n2.CREDIT");
                                int tipCard = scanner.nextInt();
                                scanner.nextLine();

                                Card cardNou;
                                if(tipCard == 1)
                                    cardNou = s.creareCard(contCurent, DEBIT);
                                else if(tipCard == 2)
                                    cardNou = s.creareCard(contCurent, CREDIT);
                                else{
                                    System.out.println("Tip card invalid!");
                                    break;
                                }
                                scriere.adaugareJDBC(cardNou);
                                auditRep.addActiune("Creare card bancar");
                                //a.adaugareActiune("Creare card bancar");
                                break;

                            case 7:
                                System.out.println("IBAN-ul contului: ");
                                String iban1 = scanner.nextLine();
                                Cont cont = s.getContByIBAN(iban1, conturi);
                                if(cont == null)
                                {
                                    System.out.println("IBAN-ul introdus este incorect!");
                                    break;
                                }
                                System.out.println("Sold: ");
                                s.interogareSold(cont);
                                auditRep.addActiune("Interogare sold");
                                //a.adaugareActiune("Interogare sold");
                                break;
                            case 8:
                                System.out.println("IBAN-ul contului: ");
                                String iban2 = scanner.nextLine();
                                Cont cont1 = s.getContByIBAN(iban2, conturi);
                                if(cont1 == null)
                                {
                                    System.out.println("IBAN-ul introdus este incorect!");
                                    break;
                                }
                                System.out.println("Extras de cont: ");
                                s.afisareExtrasCont(cont1);
                                auditRep.addActiune("Afisare extras de cont");
                                //a.adaugareActiune("Afisare extras de cont");
                                break;
                            case 9:
                                System.out.println("IBAN-ul contului: ");
                                String iban3 = scanner.nextLine();
                                Cont cont2 = s.getContByIBAN(iban3, conturi);
                                if (cont2 == null) {
                                    System.out.println("IBAN-ul introdus este incorect!");
                                    break;
                                }

                                System.out.println("ID-ul cardului: ");
                                int idCard = scanner.nextInt();
                                scanner.nextLine();
                                ArrayList<Card> carduri1 = s.getCarduriCont(cont2);
                                boolean ok = false;

                                Card cardSters = null;
                                for (var card : carduri1)
                                    if (card.getCardId() == idCard) {
                                        cardSters = card;
                                        ok = true;
                                    }
                                if(cardSters != null)
                                {
                                    s.eliminaCard(cardSters, cont2, idCard);
                                    scriere.set(s.getCarduri());
                                    //scriere.scriereCarduri();
                                }
                                if (!ok) {
                                    System.out.println("ID de card incorect!");
                                    break;
                                }
                                System.out.println("Card eliminat cu succes!");
                                auditRep.addActiune("Eliminare card asociat unui cont");
                                //a.adaugareActiune("Eliminare card asociat unui cont");
                                break;
                            case 10:
                                System.out.println("IBAN-ul contului: ");
                                String iban4 = scanner.nextLine();
                                Cont cont4 = s.getContByIBAN(iban4, conturi);
                                if (cont4 == null) {
                                    System.out.println("IBAN-ul introdus este incorect!");
                                    break;
                                }
                                System.out.println("Carduri:");
                                s.afisareCarduriCont(iban4);
                                auditRep.addActiune("Afisare carduri asociate unui cont");
                                //a.adaugareActiune("Afisare carduri asociate unui cont");
                                break;
                            case 11:
                                System.out.println("Username: ");
                                String un = scanner.nextLine();
                                Admin ad;
                                try{
                                    ad = s.creareAdmin(un, "");
                                }catch(UsernameException e)
                                {
                                    System.out.println("Username-ul specificat exista deja!");
                                    break;
                                }
                                System.out.println("Parola: ");
                                String p = scanner.nextLine();
                                ad.setParola(p);
                                scriere.adaugareJDBC(ad);
                                System.out.println("Admin inregistrat cu succes!");
                                auditRep.addActiune("Inregistrare admin");
                                //a.adaugareActiune("Inregistrare admin");
                                break;
                            default:
                                System.out.println("Va rugam alegeti o optiune valida!");
                        }
                    }

                    break;

                case 2:
                    System.out.println("Username: ");
                    String username1 = scanner.nextLine();
                    String parola1="";
                    int idUser = -1;
                    boolean ok1 = false;
                    for(var user : users)
                        if(user.getUsername().equals(username1))
                        {
                            ok1 = true;
                            parola1 = user.getParola();
                            idUser = user.getId();
                        }
                    if(!ok1)
                    {
                        System.out.println("Username-ul specificat nu exista!");
                        break;
                    }
                    System.out.println("Parola: ");
                    String pass1 = scanner.nextLine();
                    if(!parola1.equals(pass1))
                    {
                        System.out.println("Parola incorecta!");
                        break;
                    }

                    System.out.println("V-ati autentificat cu succes!");
                    auditRep.addActiune("Autentificare user");
                    //a.adaugareActiune("Autentificare user");

                    //aflam clientul corespunzator user-ului
                    Client clientCurent = s.getClientById(idUser);

                    //aflam conturile clientului
                    List<Cont> conturiClientCurent = s.getConturi(clientCurent);

                    boolean exit2 = false;

                    while(!exit2)
                    {
                        System.out.println("MENIU");
                        System.out.println("-----");
                        System.out.println("0.Exit");
                        System.out.println("1.Afisare detalii banca");
                        System.out.println("2.Efectuare tranzactie");
                        System.out.println("3.Afisare date personale");
                        System.out.println("4.Listare conturi personale");
                        System.out.println("5.Interogare sold");
                        System.out.println("6.Afisare extras de cont");

                        int optiune = scanner.nextInt();
                        scanner.nextLine();
                        switch (optiune)
                        {
                            case 0:
                                exit2 = true;
                                break;
                            case 1:
                                System.out.println("DETALII BANCA:\n");
                                s.afisareDetaliiBanca(banca);
                                auditRep.addActiune("Afisare detalii banca");
                                //a.adaugareActiune("Afisare detalii banca");
                                break;
                            case 2:
                                System.out.println("Tipul tranzactiei: \n1.DEPUNERE\n2.RETRAGERE\n3.TRANSFER");
                                int tipTranzactie = scanner.nextInt();
                                scanner.nextLine();
                                if (tipTranzactie == 1) {
                                    String ibanSursa = "";
                                    System.out.println("IBAN-ul contului in care se face depunerea: ");
                                    String ibanDestinatie = scanner.nextLine();

                                    boolean c = false;
                                    for(var cont : conturiClientCurent)
                                        if(cont.getIBAN().equals(ibanDestinatie))
                                            c = true;
                                    if(!c)
                                    {
                                        System.out.println("Nu s-a gasit cont cu IBAN-ul specificat, asociate actualului client!");
                                        break;
                                    }

                                    System.out.println("Suma depusa: ");
                                    double suma = scanner.nextDouble();
                                    scanner.nextLine();
                                    System.out.println("Descriere: ");
                                    String descriere = scanner.nextLine();
                                    Tranzactie t = s.creareTranzactie(ibanSursa, ibanDestinatie, suma, descriere, DEPUNERE);
                                    scriere.adaugareJDBC(t);
                                    scriere.set(conturi);
                                    //scriere.scriereConturi();
                                } else if (tipTranzactie == 2) {
                                    String ibanDestinatie = "";
                                    System.out.println("IBAN-ul contului din care se face retrgerea: ");
                                    String ibanSursa = scanner.nextLine();

                                    boolean c = false;
                                    for(var cont : conturiClientCurent)
                                        if(cont.getIBAN().equals(ibanSursa))
                                            c = true;
                                    if(!c)
                                    {
                                        System.out.println("Nu s-a gasit cont cu IBAN-ul specificat, asociat actualului client!");
                                        break;
                                    }

                                    Cont cont = s.getContByIBAN(ibanSursa, conturi);

                                    System.out.println("Suma retrasa: ");
                                    double suma = scanner.nextDouble();
                                    scanner.nextLine();
                                    if (suma > cont.getSold()) {
                                        System.out.println("Fonduri insuficiente!");
                                        break;
                                    }
                                    System.out.println("Descriere: ");
                                    String descriere = scanner.nextLine();
                                    Tranzactie t = s.creareTranzactie(ibanSursa, ibanDestinatie, suma, descriere, RETRAGERE);
                                    scriere.adaugareJDBC(t);
                                    scriere.set(conturi);
                                    //scriere.scriereConturi();

                                } else if(tipTranzactie == 3){

                                    System.out.println("IBAN Sursa: ");
                                    String ibanSursa = scanner.nextLine();

                                    boolean c = false;
                                    for(var cont : conturiClientCurent)
                                        if(cont.getIBAN().equals(ibanSursa))
                                            c = true;
                                    if(!c)
                                    {
                                        System.out.println("Nu s-a gasit cont cu IBAN-ul specificat, asociat actualului client!");
                                        break;
                                    }

                                    System.out.println("IBAN Destinatie: ");
                                    String ibanDestinatie = scanner.nextLine();
                                    Cont contSursa = s.getContByIBAN(ibanSursa, conturi);
                                    Cont contDestinatie = s.getContByIBAN(ibanDestinatie, conturi);
                                    if(!conturi.contains(contDestinatie))
                                    {
                                        System.out.println("IBAN-ul destinatie nu exista!");
                                        break;
                                    }
                                    else
                                    {
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
                                        scriere.adaugareJDBC(t);
                                        scriere.set(conturi);
                                        //scriere.scriereConturi();
                                    }

                                } else
                                    break;
                                auditRep.addActiune("Efectuare tranzactie");
                                //a.adaugareActiune("Efectuare tranzactie");
                                break;
                            case 3:
                                Client client1 = s.getClientById(idUser);
                                if (client1 != null) {
                                    System.out.println("Date client: ");
                                    s.afisareDateClient(client1);
                                    auditRep.addActiune("Afisare date client");
                                    //a.adaugareActiune("Afisare date client");
                                    break;
                                } else {
                                    System.out.println("Id-ul specificat nu exista!");
                                    break;
                                }
                            case 4:
                                Client client = s.getClientById(idUser);
                                if (client != null) {
                                    System.out.println("Conturi: ");
                                    s.afisareConturiClient(client);
                                    auditRep.addActiune("Listare conturi client");
                                    //a.adaugareActiune("Listare conturi client");
                                    break;
                                }
                                else{
                                    System.out.println("Id-ul specificat nu exista!");
                                    break;
                                }
                            case 5:
                                System.out.println("IBAN-ul contului: ");
                                String iban1 = scanner.nextLine();

                                boolean c = false;
                                for(var cont : conturiClientCurent)
                                    if(cont.getIBAN().equals(iban1))
                                        c = true;
                                if(!c)
                                {
                                    System.out.println("Nu s-a gasit cont cu IBAN-ul specificat, asociat actualului client!");
                                    break;
                                }

                                Cont cont = s.getContByIBAN(iban1, conturi);
                                System.out.println("Sold: ");
                                s.interogareSold(cont);
                                auditRep.addActiune("Interogare sold");
                                //a.adaugareActiune("Interogare sold");
                                break;
                            case 6:
                                System.out.println("IBAN-ul contului: ");
                                String iban2 = scanner.nextLine();

                                boolean c2 = false;
                                for(var cont2 : conturiClientCurent)
                                    if(cont2.getIBAN().equals(iban2))
                                        c2 = true;
                                if(!c2)
                                {
                                    System.out.println("Nu s-a gasit cont cu IBAN-ul specificat, asociat actualului client!");
                                    break;
                                }

                                Cont cont1 = s.getContByIBAN(iban2, conturi);
                                if(cont1 == null)
                                {
                                    System.out.println("IBAN-ul introdus este incorect!");
                                    break;
                                }
                                System.out.println("Extras de cont: ");
                                s.afisareExtrasCont(cont1);
                                auditRep.addActiune("Afisare extras de cont");
                                //a.adaugareActiune("Afisare extras de cont");
                                break;

                            default:
                                System.out.println("Va rugam alegeti o optiune valida!");
                        }
                    }

                    break;
                case 3:
                    System.out.println("Id de client: ");
                    Integer idc = scanner.nextInt();
                    scanner.nextLine();
                    UserRepository ur = UserRepository.getInstance();
                    List<Integer> usedIds = ur.getAllUsedIds();

                    if(usedIds.contains(idc))
                    {
                        System.out.println("Exista deja un cont asociat clientului cu id-ul specificat!");
                        break;
                    }

                    Client cl = s.getClientById(idc);
                    if (cl!= null)
                    {

                        System.out.println("Username: ");
                        String un = scanner.nextLine();
                        User u;
                        try{
                            u = s.creareUser(idc, un, "");
                        }catch(UsernameException e)
                        {
                            System.out.println("Username-ul specificat exista deja!");
                            break;
                        }
                        System.out.println("Parola: ");
                        String p = scanner.nextLine();
                        u.setParola(p);
                        System.out.println("V-ati inregistrat cu succes!");
                        scriere.adaugareJDBC(u);
                        auditRep.addActiune("Inregistrare user");
                        //a.adaugareActiune("Inregistrare user");
                        break;
                    }
                    else{
                        System.out.println("Id-ul specificat nu exista!");
                        break;
                    }

                case 4:
                    exit0 = true;
                    break;

                default:
                    System.out.println("Va rugam alegeti o optiune valida!");

            }
        }

        DatabaseConfiguration.closeDatabaseConfiguration();
    }
}
