package aplicatieBancara.Entitati;

import aplicatieBancara.Entitati.Client.Client;
import aplicatieBancara.Entitati.Cont.Cont;
import aplicatieBancara.Entitati.Cont.SingletonCont;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static aplicatieBancara.Entitati.TipTranzactie.*;

public class SingletonTranzactie {

    private static SingletonTranzactie instanta_singleton;

    private List<Tranzactie> tranzactii = new ArrayList<>();


    public static SingletonTranzactie getInstance(){
        if (instanta_singleton == null)
            instanta_singleton = new SingletonTranzactie();
        return instanta_singleton;
    }

    public void setTranzactii(List<Tranzactie> tranzactii) {
        this.tranzactii = tranzactii;
    }

    public List<Tranzactie> getTranzactii() {
        return tranzactii;
    }

    private static List<String[]> getCSVStrings(String fileName){
        List<String[]> matrice = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(fileName))){
            String linie;
            while((linie = in.readLine()) != null ) {
                String[] campuri = linie.replaceAll(" ", "").split(",");
                matrice.add(campuri);
            }

        }catch (IOException e) {
            System.out.println("Nu exista conturi salvate!");
        }
        return matrice;
    }

    public void construireTranzactii(){
        try {
            var matrice = SingletonTranzactie.getCSVStrings("Data/tranzactie.csv");
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

    public void adaugareTranzactie(Tranzactie tranzactie)
    {
        tranzactii.add(tranzactie);
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
//            for(var tranzactie : this.tranzactii){
//                writer.write(tranzactie.toCSV());
//                writer.write("\n");
//            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingletonTranzactie that = (SingletonTranzactie) o;
        return Objects.equals(getTranzactii(), that.getTranzactii());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTranzactii());
    }
}
