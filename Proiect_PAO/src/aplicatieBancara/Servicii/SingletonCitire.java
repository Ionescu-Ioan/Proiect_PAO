package aplicatieBancara.Servicii;

import aplicatieBancara.Entitati.Banca;
import aplicatieBancara.Entitati.Card.Card;
import aplicatieBancara.Entitati.Card.CardCredit;
import aplicatieBancara.Entitati.Card.CardDebit;
import aplicatieBancara.Entitati.Client.Adresa;
import aplicatieBancara.Entitati.Client.Client;
import aplicatieBancara.Entitati.Cont.Cont;
import aplicatieBancara.Entitati.TipTranzactie;
import aplicatieBancara.Entitati.Tranzactie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SingletonCitire{

    private static SingletonCitire instanta_singleton;

    private List<Card> carduri = new ArrayList<>();
    private List<Adresa> adrese = new ArrayList<>();
    private List<Client> clienti = new ArrayList<>();
    private List<Cont> conturi = new ArrayList<>();
    private List<Tranzactie> tranzactii = new ArrayList<>();


    public static SingletonCitire getInstance(){
        if (instanta_singleton == null)
            instanta_singleton = new SingletonCitire();
        return instanta_singleton;
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
        }
        return matrice;
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



    public void construireConturi(){
        try {
            var matrice = SingletonCitire.getCSVStrings("Data/cont.csv");
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
