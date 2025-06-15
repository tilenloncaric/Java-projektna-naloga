package razredi;


/**
 * Razred Razdalja dobi koordinate klika uporabnika in izračuna razdalje med točko klika ter točkami iz katerih rastejo rastline.
 * Izračunane razdelje zapiše v tabelo razdalje in le-o tudi vrne.
 */

public class Razdalja {

    // Spremenljivki, ki predstavljata koordinate klika uporabnika
    private int x1;
    private int y1;

    // Tabela, ki vsebuje točke, iz katerih rastejo rastline
    private int[][] zacetneTockeRasti = {{100, 510}, {300, 510}, {500, 510}, {700, 510}, {900, 510}};

    // Tabela, ki bo hranila izračunane razdalje
    private double[] razdalje;


    /**
     * Konstruktor, ki inicializira prejeti koordinati in seznam točk, nato izračuna razdalje med točkami ter jih zapiše v tabelo.
     */

    public Razdalja(int x, int y) {
        this.x1 = x;
        this.y1 = y;
        this.razdalje = new double[zacetneTockeRasti.length];                                                 // Določi velikost tabele

        // Izračuna razdalje med točkami
        for (int i = 0; i < zacetneTockeRasti.length; i++) {
            int x2 = zacetneTockeRasti[i][0];                                                                 // X koordinata točke iz katere izrašča rastlina
            int y2 = zacetneTockeRasti[i][1];                                                                 // Y koordinata točke iz katere izrašča rastlina

            double razdalja = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));                     // Izračuna razdaljo med točkama (x1, y1) in (x2, y2)
            razdalje[i] = razdalja;                                                                           // Zapiše zračunano razdaljo v tabelo
 
        }

    }


    /**
     * Metoda, ki vrne tabelo.
     */

    public double[] getRazdalje() {
        return razdalje;
    }

}