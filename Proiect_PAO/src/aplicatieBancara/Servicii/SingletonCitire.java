package aplicatieBancara.Servicii;

import aplicatieBancara.Entitati.Banca;
import aplicatieBancara.Entitati.Card.Card;
import aplicatieBancara.Entitati.Card.CardCredit;
import aplicatieBancara.Entitati.Card.CardDebit;
import aplicatieBancara.Entitati.Card.RepositoryCard;
import aplicatieBancara.Entitati.Client.Adresa;
import aplicatieBancara.Entitati.Client.Client;
import aplicatieBancara.Entitati.Client.RepositoryAdresa;
import aplicatieBancara.Entitati.Client.RepositoryClient;
import aplicatieBancara.Entitati.Cont.Cont;
import aplicatieBancara.Entitati.Cont.RepositoryCont;
import aplicatieBancara.Entitati.RepositoryTranzactie;
import aplicatieBancara.Entitati.TipTranzactie;
import aplicatieBancara.Entitati.Tranzactie;
import aplicatieBancara.Entitati.User.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SingletonCitire{

    private static SingletonCitire instantaSingleton;

    private List<Card> carduri;
    private List<Adresa> adrese;
    private List<Client> clienti;
    private List<Cont> conturi;
    private List<Tranzactie> tranzactii;
    private List<User> users;
    private List<Admin> admins;


    private SingletonCitire()
    {
       carduri = new ArrayList<>();
       adrese = new ArrayList<>();
       clienti = new ArrayList<>();
       conturi = new ArrayList<>();
       tranzactii = new ArrayList<>();
       users = new ArrayList<>();
       admins = new ArrayList<>();
    }

    public static SingletonCitire getInstance(){
        if (instantaSingleton == null)
            instantaSingleton = new SingletonCitire();
        return instantaSingleton;
    }

    public List<Card> getCarduri() {
        return carduri;
    }

    public List<Adresa> getAdrese() {
        return adrese;
    }


    public List<Client> getClienti() {
        return clienti;
    }


    public List<Cont> getConturi() {
        return conturi;
    }


    public List<Tranzactie> getTranzactii() {
        return tranzactii;
    }


    public List<User> getUsers() {
        return users;
    }


    public List<Admin> getAdmins() {
        return admins;
    }


    private static List<String[]> getCSVStrings(String fileName){
        List<String[]> matrice = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(fileName))){
            String linie;
            while((linie = in.readLine()) != null ) {
                String[] campuri = linie.split(",");
                matrice.add(campuri);
            }

        }catch (IOException e) {
            System.out.println("Nu exista date salvate in fisierul " + fileName + "!");
            return matrice;
        }
        return matrice;
    }

    public void construireUsers(){
        try {
            var matrice = SingletonCitire.getCSVStrings("Data/user.csv");
            for (var element : matrice) {
                if(element != null)
                { User userNou = new User(
                        Integer.parseInt(element[0]),
                        element[1],
                        element[2]
                );
                    users.add(userNou);
                }
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        } catch (UsernameException e) {
            e.printStackTrace();
        }
    }

    public void construireUsersJDBC(){
        try {
            UserRepository ur = UserRepository.getInstance();
            var matrice = ur.getAllUsers();
            for (var element : matrice) {
                if(element != null)
                { User userNou = new User(
                        Integer.parseInt(element[0]),
                        element[1],
                        element[2]
                );
                    users.add(userNou);
                }
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        } catch (UsernameException e) {
            e.printStackTrace();
        }
    }

    public void construireAdmins(){

        try {
            var matrice = SingletonCitire.getCSVStrings("Data/admin.csv");
            for (var element : matrice) {
                if(element != null)
                { Admin adminNou = new Admin(
                        Integer.parseInt(element[0]),
                        element[1],
                        element[2]
                );
                    admins.add(adminNou);
                }
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        } catch (UsernameException e) {
            e.printStackTrace();
        }
    }

    public void construireAdminsJDBC(){

        try {
            AdminRepository ar = AdminRepository.getInstance();
            var matrice = ar.getAllAdmins();
            for (var element : matrice) {
                if(element != null)
                { Admin adminNou = new Admin(
                        Integer.parseInt(element[0]),
                        element[1],
                        element[2]
                );
                    admins.add(adminNou);
                }
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        } catch (UsernameException e) {
            e.printStackTrace();
        }
    }

    public void construireCarduri(List<Cont> listaConturi){
        try {
            var matrice = SingletonCitire.getCSVStrings("Data/card.csv");
            for (var element : matrice) {
                Cont cont = null;
                for(var cc : listaConturi)
                {
                    if(element[1].equals(cc.getIBAN()))
                    {
                        cont = cc;
                        break;
                    }

                }
                if(cont == null)
                {
                    System.out.println("Cardul citit nu este asociat niciunui cont existent!");
                    break;
                }
                if(element[2].equals("DEBIT"))
                {
                    CardDebit cardNou = new CardDebit(
                            Integer.parseInt(element[0]),
                            cont
                    );
                    carduri.add(cardNou);
                }
                else if(element[2].equals("CREDIT"))
                {
                    CardCredit cardNou = new CardCredit(
                            Integer.parseInt(element[0]),
                            cont
                    );
                    cardNou.actualizareSumaCreditata(Double.parseDouble(element[3]));
                    carduri.add(cardNou);

                }
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
    }


    public void construireCarduriJDBC(List<Cont> listaConturi){
        try {
            RepositoryCard cr = RepositoryCard.getInstance();
            var matrice = cr.getCarduri();
            for (var element : matrice) {
                Cont cont = null;
                for(var cc : listaConturi)
                {
                    if(element[1].equals(cc.getIBAN()))
                    {
                        cont = cc;
                        break;
                    }

                }
                if(cont == null)
                {
                    System.out.println("Cardul citit nu este asociat niciunui cont existent!");
                    break;
                }
                if(element[2].equals("DEBIT"))
                {
                    CardDebit cardNou = new CardDebit(
                            Integer.parseInt(element[0]),
                            cont
                    );
                    carduri.add(cardNou);
                }
                else if(element[2].equals("CREDIT"))
                {
                    CardCredit cardNou = new CardCredit(
                            Integer.parseInt(element[0]),
                            cont
                    );
                    cardNou.actualizareSumaCreditata(Double.parseDouble(element[3]));
                    carduri.add(cardNou);

                }
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
    }

    public void construireAdrese(){
        try {
            var matrice = SingletonCitire.getCSVStrings("Data/adresa.csv");
            for (var element : matrice) {
                if(element != null)
                { Adresa adresaNoua = new Adresa(
                        element[0],
                        element[1],
                        element[2],
                        element[3]
                );
                    adresaNoua.setIdClient(Integer.parseInt(element[4]));
                    adrese.add(adresaNoua);
                }
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
    }


    public void construireAdreseJDBC(){
        try {
            RepositoryAdresa ar = RepositoryAdresa.getInstance();
            var matrice = ar.getAdrese();
            for (var element : matrice) {
                if(element != null)
                { Adresa adresaNoua = new Adresa(
                        element[1],
                        element[2],
                        element[3],
                        element[4]
                );
                    adresaNoua.setIdClient(Integer.parseInt(element[0]));
                    adrese.add(adresaNoua);
                }
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
    }

    public void construireClienti(List<Adresa> listaAdrese){
        try {
            var matrice = SingletonCitire.getCSVStrings("Data/client.csv");
            for (var element : matrice) {
                Adresa adresa = null;
                int idClient = Integer.parseInt(element[0]);
                for(var adr : listaAdrese)
                {
                    if(adr.getIdClient() == idClient)
                        adresa = adr;
                }
                Client clientNou = new Client(
                        idClient,
                        element[1],
                        element[2],
                        element[3],
                        LocalDate.parse(element[4]),
                        element[5],
                        element[6],
                        adresa
                );
                clienti.add(clientNou);
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
    }


    public void construireClientiJDBC(List<Adresa> listaAdrese){
        try {
            RepositoryClient cr = RepositoryClient.getInstance();
            var matrice = cr.getClienti();
            for (var element : matrice) {
                Adresa adresa = null;
                int idClient = Integer.parseInt(element[0]);
                for(var adr : listaAdrese)
                {
                    if(adr.getIdClient() == idClient)
                        adresa = adr;
                }
                Client clientNou = new Client(
                        idClient,
                        element[1],
                        element[2],
                        element[3],
                        LocalDate.parse(element[4]),
                        element[5],
                        element[6],
                        adresa
                );
                clienti.add(clientNou);
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
    }


    public void construireConturi(){
        try {
            RepositoryCont cr = RepositoryCont.getInstance();
            var matrice = cr.getConturi();
            for (var element : matrice) {
                String numeTitular = element[1] + " " + element[2];
                Cont contNou = new Cont(
                        element[3],
                        numeTitular,
                        Integer.parseInt(element[0]),
                        Banca.getBanca()
                );
                contNou.actualizareSold(Double.parseDouble(element[4]));
                conturi.add(contNou);
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
    }

    public void construireConturiJDBC(){
        try {
            RepositoryCont cr = RepositoryCont.getInstance();
            var matrice = cr.getConturi();
            for (var element : matrice) {
                String numeTitular = element[1];
                Cont contNou = new Cont(
                        element[2],
                        numeTitular,
                        Integer.parseInt(element[0]),
                        Banca.getBanca()
                );
                contNou.actualizareSold(Double.parseDouble(element[3]));
                conturi.add(contNou);
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
    }


    public void construireTranzactii(){
        try {
            var matrice = SingletonCitire.getCSVStrings("Data/tranzactie.csv");
            for (var element : matrice) {
                if(element[0] == null)
                    break;
                TipTranzactie t = null;
                if(element[4].equals("TRANSFER"))
                {
                    t = TipTranzactie.TRANSFER;
                }
                else if(element[4].equals("DEPUNERE"))
                {
                    t = TipTranzactie.DEPUNERE;
                }
                else if(element[4].equals("RETRAGERE"))
                {
                    t = TipTranzactie.RETRAGERE;
                }
                Tranzactie tranzactieNoua = new Tranzactie(
                        element[0],
                        element[1],
                        Double.parseDouble(element[2]),
                        element[3],
                        t
                );
                tranzactieNoua.setData(LocalDate.parse(element[5]));
                tranzactii.add(tranzactieNoua);
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }


    public void construireTranzactiiJDBC(){
        try {
            RepositoryTranzactie tr = RepositoryTranzactie.getInstance();
            var matrice = tr.getTranzactii();
            for (var element : matrice) {
                if(element[0] == null)
                    break;
                TipTranzactie t = null;
                if(element[5].equals("TRANSFER"))
                {
                    t = TipTranzactie.TRANSFER;
                }
                else if(element[5].equals("DEPUNERE"))
                {
                    t = TipTranzactie.DEPUNERE;
                }
                else if(element[5].equals("RETRAGERE"))
                {
                    t = TipTranzactie.RETRAGERE;
                }
                Tranzactie tranzactieNoua = new Tranzactie(
                        element[1],
                        element[2],
                        Double.parseDouble(element[3]),
                        element[4],
                        t
                );
                tranzactieNoua.setData(LocalDate.parse(element[6]));
                tranzactii.add(tranzactieNoua);
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingletonCitire that = (SingletonCitire) o;
        return Objects.equals(getCarduri(), that.getCarduri()) && Objects.equals(getAdrese(), that.getAdrese()) && Objects.equals(getClienti(), that.getClienti()) && Objects.equals(getConturi(), that.getConturi()) && Objects.equals(getTranzactii(), that.getTranzactii());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCarduri(), getAdrese(), getClienti(), getConturi(), getTranzactii());
    }
}
