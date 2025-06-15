package razredi;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


/**
 * Razred GlavniDel riše ter iračunava vse glavne komponente za izvajanje igre. 
 */

public class GlavniDel extends JPanel {

    // Objekti za risanje sonca, animacijo ognja in risanja cvetov
    private NarisiSonce narisiSonce;
    private AnimacijaOgenj animacijaOgenj;
    private Cvet cvet;

    // Seznam vseh stebel (da se ob novem kliku ponovno narišejo, brez animacije)
    private final java.util.List<NarisiSteblo> vsaStebla = new ArrayList<>();

    // Tabela, ki bo hranila razdalje med koordinatami klika uporabnika in začetnimi točami rasti rastlin
    private double[] razdalje;

    // Tabela, ki bo hranila vrednosti, za koliko bo posamezna rastlika zrastla ob kliku uporabnika
    private int[] kolikoZraste;

    // Tabela+, ki hrani trenutne koordinate vrhov stebel (x, y)
    private int[][] trenutniVrh = {{100, 515}, {300, 515}, {500, 515}, {700, 515}, {900, 515}};

    // Tabela, ki hrani koordinate vrhov stebel po rasti (posodablja se ob vsakem kliku)
    private int[][] noviVrh = {{100, 515}, {300, 515}, {500, 515}, {700, 515}, {900, 515}};


    /**
     * Konstruktor, ki izvaja glavni del igre.
     */

    public GlavniDel() {

        // Inicializacija objektov 
        narisiSonce = new NarisiSonce();
        animacijaOgenj = new AnimacijaOgenj();
        cvet = new Cvet(trenutniVrh);

        // Dodajanje poslušalca za klik miške
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent klikMiske) {

                // Pridobiti koordinate klika miške uporabnika
                int x = klikMiske.getX();
                int y = klikMiske.getY();

                // Nariše Sonce glese na koodrinate klika
                narisiSonce.nastaviKoordinate(x, y);

                // Animacija ognja
                animacijaOgenj.zacetekAnimacije(x, y);

                // Izračun razdalj od sonca do posameznih stebel
                Razdalja r = new Razdalja(x, y);
                razdalje = r.getRazdalje();

                // Izračun rasti stebel glede na razdalje
                kolikoZraste = Rast.izracunajRast(razdalje);

                // Posodobi vrednosti v tabeli noviVrhovi glede na izračunano rast
                for (int i = 0; i < noviVrh.length; i++) {
                    noviVrh[i][1] = trenutniVrh[i][1] - kolikoZraste[i];
                }

                // Ustvari stebla
                NarisiSteblo novoSteblo = new NarisiSteblo(trenutniVrh, noviVrh, animacijaOgenj);
                vsaStebla.add(novoSteblo);

                // Inicializacija objekta
                cvet = new Cvet(noviVrh);

                // Posodobi vrednosti v tabeli trenutniVrh
                trenutniVrh = kopiraj(noviVrh);
                

                // Osveži prikaz
                repaint();

            }

        });

        // Timer za periodično osveževanje zaslona in animacijo 
        Timer osvezi = new Timer(30, e -> repaint());
        osvezi.start();

    }


    /**
     * Metoda, ki riše komponente na zaslon.
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Nariše Sonce
        narisiSonce.narisi(g);

        // Nariše animacijo ognja
        animacijaOgenj.paintComponent(g);

        // Nariše stebla (vsako posebej)
        for (NarisiSteblo steblo : vsaStebla) {
            steblo.paintComponent(g);
        }

        // Nariše cvetove šele, ko so animacije stebel končane
        if (cvet != null && jeKončana()) {
            cvet.paintComponent(g);
        }

    }


    /**
     * Metoda za kopiranje.
     */

    private int[][] kopiraj (int[][] original) {
        
        int[][] kopija = new int[original.length][];

        for (int i = 0; i < original.length; i++) {
            kopija[i] = original[i].clone();
        }

        return kopija;

    }


    /**
     * Metoda, ki vrne true, če so vse animacije stebel končane.
     */
    public boolean jeKončana() {
        // Če ni nobenega stebla, vrnemo true
        if (vsaStebla.isEmpty()) {
            return true;
        }

        // Preverimo, ali so vse animacije stebel končane
        for (NarisiSteblo steblo : vsaStebla) {
            if (!steblo.jeKončana()) {
                return false;
            }
        }

        return true;
    }

}