package aplicatieBancara.Cont;

import aplicatieBancara.Tranzactie;

import java.util.Comparator;

public class ComparatorTranzactii implements Comparator<Tranzactie> {

    @Override
    public int compare(Tranzactie t1, Tranzactie t2) {
        return t1.getData().compareTo(t2.getData());
    }
}
