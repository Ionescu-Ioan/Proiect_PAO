package aplicatieBancara.Entitati.Client;

import aplicatieBancara.Entitati.Card.Card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SingletonAdresa {

    private static SingletonAdresa instanta_singleton;

    private List<Adresa> adrese = new ArrayList<>();
    private Stream<Adresa> streamAdrese = adrese.stream();

    public static SingletonAdresa getInstance(){
        if (instanta_singleton == null)
            instanta_singleton = new SingletonAdresa();
        return instanta_singleton;
    }

    public void setAdrese(List<Adresa> adrese) {
        this.adrese = adrese;
    }

    public List<Adresa> getAdrese() {
        return adrese;
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
            System.out.println("Nu exista adrese salvate!");
        }
        return matrice;
    }

    public void construireAdrese(){
        try {
            var matrice = SingletonAdresa.getCSVStrings("Data/adresa.csv");
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

    public void adaugareAdresa(Adresa adresa)
    {
        adrese.add(adresa);
    }

    public void scriereAdrese(){
        try{
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
//            for(var adresa : this.adrese){
//                writer.write(adresa.toCSV());
//                writer.write("," + adresa.getIdClient());
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
        SingletonAdresa that = (SingletonAdresa) o;
        return Objects.equals(getAdrese(), that.getAdrese());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdrese());
    }
}
