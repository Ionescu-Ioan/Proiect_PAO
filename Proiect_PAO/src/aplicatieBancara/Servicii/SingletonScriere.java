package aplicatieBancara.Servicii;

import aplicatieBancara.Entitati.Card.Card;
import aplicatieBancara.Entitati.Card.RepositoryCard;
import aplicatieBancara.Entitati.Client.Adresa;
import aplicatieBancara.Entitati.Client.Client;
import aplicatieBancara.Entitati.Client.RepositoryAdresa;
import aplicatieBancara.Entitati.Client.RepositoryClient;
import aplicatieBancara.Entitati.Cont.Cont;
import aplicatieBancara.Entitati.Cont.RepositoryCont;
import aplicatieBancara.Entitati.RepositoryTranzactie;
import aplicatieBancara.Entitati.Tranzactie;
import aplicatieBancara.Entitati.User.Admin;
import aplicatieBancara.Entitati.User.AdminRepository;
import aplicatieBancara.Entitati.User.User;
import aplicatieBancara.Entitati.User.UserRepository;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SingletonScriere {

    private static SingletonScriere instanta_singleton;

    private List<Card> carduri;
    private List<Adresa> adrese;
    private List<Client> clienti;
    private List<Cont> conturi;
    private List<Tranzactie> tranzactii;
    private List<User> users;
    private List<Admin> admins;


    private SingletonScriere()
    {
        carduri = SingletonCitire.getInstance().getCarduri();
        adrese = SingletonCitire.getInstance().getAdrese();
        clienti = SingletonCitire.getInstance().getClienti();
        conturi = SingletonCitire.getInstance().getConturi();
        tranzactii = SingletonCitire.getInstance().getTranzactii();
        users = SingletonCitire.getInstance().getUsers();
        admins = SingletonCitire.getInstance().getAdmins();
    }

    public static SingletonScriere getInstance(){
        if (instanta_singleton == null)
            instanta_singleton = new SingletonScriere();
        return instanta_singleton;
    }

    public <T> void set(List<T> lista)
    {
        if(!lista.isEmpty() && lista.get(0) instanceof Card)
        {
            this.carduri = (List<Card>) lista;
        }
        else if(!lista.isEmpty() && lista.get(0) instanceof Adresa)
        {
            this.adrese = (List<Adresa>) lista;
        }
        else if(!lista.isEmpty() && lista.get(0) instanceof Client)
        {
            this.clienti = (List<Client>) lista;
        }
        else if(!lista.isEmpty() && lista.get(0) instanceof Cont)
        {
            this.conturi = (List<Cont>) lista;
        }
        else if(!lista.isEmpty() && lista.get(0) instanceof Tranzactie)
        {
            this.tranzactii = (List<Tranzactie>) lista;
        }
        else if(!lista.isEmpty() && lista.get(0) instanceof User)
        {
            this.users = (List<User>) lista;
        }
        else if(!lista.isEmpty() && lista.get(0) instanceof Admin)
        {
            this.admins = (List<Admin>) lista;
        }
        else{
            System.out.println("Lista este vida!");
        }
    }

    public void scriereUsers()
    {
        try{
            Stream<User> streamUsers = users.stream();
            var writer = new FileWriter("Data/user.csv");
            Consumer<User> consumer = user -> {
                try {
                    writer.write(user.toCSV());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamUsers.forEach(consumer);

            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public void scriereAdmins(){
        try{
            Stream<Admin> streamAdmins = admins.stream();
            var writer = new FileWriter("Data/admin.csv");
            Consumer<Admin> consumer = admin -> {
                try {
                    writer.write(admin.toCSV());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamAdmins.forEach(consumer);

            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public void scriereCarduri(){
        try{
            Stream<Card> streamCarduri = carduri.stream();
            var writer = new FileWriter("Data/card.csv");
            Consumer<Card> consumer = card -> {
                try {
                    writer.write(card.toCSV());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamCarduri.forEach(consumer);

            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }


    public void scriereAdrese(){
        try{
            Stream<Adresa> streamAdrese = adrese.stream();
            var writer = new FileWriter("Data/adresa.csv");
            Consumer<Adresa> consumer = adresa -> {
                try {
                    writer.write(adresa.toCSV());
                    writer.write("," + adresa.getIdClient());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamAdrese.forEach(consumer);
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }


    public void scriereClienti(){
        try{
            Stream<Client> streamClienti = clienti.stream();
            var writer = new FileWriter("Data/client.csv");
            Consumer<Client> consumer = client -> {
                try {
                    writer.write(client.toCSV());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamClienti.forEach(consumer);
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public <T> void adaugare(T entitate){
        String fileName = null;
        if(entitate instanceof Card)
        {
            fileName = "Data/card.csv";
            carduri.add((Card) entitate);
        }
        else if(entitate instanceof Adresa)
        {
            fileName = "Data/adresa.csv";
            adrese.add((Adresa) entitate);
        }
        else if(entitate instanceof  Client)
        {
            fileName = "Data/client.csv";
            clienti.add((Client) entitate);
        }
        else if(entitate instanceof  Cont)
        {
            fileName = "Data/cont.csv";
            conturi.add((Cont) entitate);
        }
        else if(entitate instanceof Tranzactie)
        {
            fileName = "Data/tranzactie.csv";
            tranzactii.add((Tranzactie) entitate);
        }
        else if(entitate instanceof User)
        {
            fileName = "Data/user.csv";
            users.add((User) entitate);
        }
        else if(entitate instanceof Admin)
        {
            fileName = "Data/admin.csv";
            admins.add((Admin) entitate);
        }

        try(var writer = new FileWriter(fileName, true)) {
            if(entitate instanceof Card)
                writer.write(((Card) entitate).toCSV());
            else if(entitate instanceof Adresa)
            {
                writer.write(((Adresa) entitate).toCSV());
                writer.write("," + ((Adresa) entitate).getIdClient());
            }
            else if(entitate instanceof Client)
                writer.write(((Client) entitate).toCSV());
            else if(entitate instanceof Cont)
                writer.write(((Cont) entitate).toCSV());
            else if(entitate instanceof Tranzactie)
                writer.write(((Tranzactie) entitate).toCSV());
            else if(entitate instanceof User)
                writer.write(((User) entitate).toCSV());
            else if(entitate instanceof Admin)
                writer.write(((Admin) entitate).toCSV());
            writer.write("\n");

        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    public <T> void adaugareJDBC(T entitate){
        if(entitate instanceof Card)
        {
            RepositoryCard cr = RepositoryCard.getInstance();
            cr.addCard((Card) entitate);
            carduri.add((Card) entitate);
        }
        else if(entitate instanceof Adresa)
        {
            RepositoryAdresa ar = RepositoryAdresa.getInstance();
            ar.addAdresa((Adresa) entitate);
            adrese.add((Adresa) entitate);
        }
        else if(entitate instanceof  Client)
        {
            RepositoryClient cr = RepositoryClient.getInstance();
            cr.addClient((Client) entitate);
            clienti.add((Client) entitate);
        }
        else if(entitate instanceof  Cont)
        {
            RepositoryCont cr = RepositoryCont.getInstance();
            cr.addCont((Cont) entitate);
            conturi.add((Cont) entitate);
        }
        else if(entitate instanceof Tranzactie)
        {
            RepositoryTranzactie tr = RepositoryTranzactie.getInstance();
            tr.addTranzactie((Tranzactie) entitate);
            tranzactii.add((Tranzactie) entitate);
        }
        else if(entitate instanceof User)
        {
            UserRepository ur = UserRepository.getInstance();
            ur.addUser((User) entitate);
            users.add((User) entitate);
        }
        else if(entitate instanceof Admin)
        {
            AdminRepository ar = AdminRepository.getInstance();
            ar.addAdmin((Admin) entitate);
            admins.add((Admin) entitate);
        }

    }

    public void scriereConturi(){
        try{
            Stream<Cont> streamConturi = conturi.stream();
            var writer = new FileWriter("Data/cont.csv");
            Consumer<Cont> consumer = cont -> {
                try {
                    writer.write(cont.toCSV());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamConturi.forEach(consumer);
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public void scriereTranzactii(){
        try{
            Stream<Tranzactie> streamTranzactii = tranzactii.stream();
            var writer = new FileWriter("Data/tranzactie.csv");
            Consumer<Tranzactie> consumer = tranzactie -> {
                try {
                    writer.write(tranzactie.toCSV());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamTranzactii.forEach(consumer);
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingletonScriere that = (SingletonScriere) o;
        return Objects.equals(carduri, that.carduri) && Objects.equals(adrese, that.adrese) && Objects.equals(clienti, that.clienti) && Objects.equals(conturi, that.conturi) && Objects.equals(tranzactii, that.tranzactii);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carduri, adrese, clienti, conturi, tranzactii);
    }
}
