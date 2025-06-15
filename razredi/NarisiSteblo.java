package razredi;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * Razred NarisiSteblo nariše stebla rastlin, glede na prejete točke oziroma energijo. 
 * Animacija rasti stebel se začne šele, ko je končana animacija ognjev.
 * Steble rastejo iz očk, ki so shranjene v tebeli trenutniVrh, proti točkam, ki so shranjene v tabeli noviVrh. 
 */

public class NarisiSteblo extends JPanel {

    // Tabela, ki bo hranila začetne točke posameznih stebel
    private int[][] trenutniVrh;

    // Tabela, ki bo hranila ciljne točke posameznih stebel
    private int[][] noviVrh;

    // Tabela, ki bo hranila trenutno dolžina posameznega stebla za animacijo rasti
    private int[] trenutnaDolzina;

    // Spremenljivka, ki bo hranila vrednost true, če se je animacija ognjev že končala oziroma false, če se še ni, 
    // da se animacija rasti stebel ne bo začela, še preden se konča animacija ognjev 
    private boolean animacijaZagnana = false;

    // Timer za animacijo rasti stebel
    private Timer timer;

    // Hitrost rasti stebla 
    private final int hitrost = 5;

    // Referenca na razred, ki izvaja animacijo ognja
    private AnimacijaOgenj ogenjAnimacija;


    /**
     * Konstruktor, ki inicializira podatke o točkah stebel ter vzpostavi timer, ki se bo zagnal po koncu animacije ognja.
     */

    public NarisiSteblo(int[][] trenutniVrh, int[][] noviVrh, AnimacijaOgenj ogenjAnimacija) {
        this.trenutniVrh = trenutniVrh;
        this.noviVrh = noviVrh;
        this.trenutnaDolzina = new int[trenutniVrh.length];
        this.ogenjAnimacija = ogenjAnimacija;

        // Timer kliče vsakih 30 ms metodo za rast
        timer = new Timer(30, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Če animacija ogenja še ni končana, počakamo z izvajanjem
                if (!ogenjAnimacija.jeKoncano()) {
                    return;
                }

                // Animacija stebla se lahko začne
                animacijaZagnana = true;

                // Spremenljivka, ki sledi ali še kakšno steblo mora rasti
                boolean kajSeRaste = false;

                
                for (int i = 0; i < trenutniVrh.length; i++) {

                    // Izračun dolžine črte po pitagorjevem izreku
                    int a = noviVrh[i][0] - trenutniVrh[i][0];
                    int b = noviVrh[i][1] - trenutniVrh[i][1];
                    double dolzina = Math.sqrt(a * a + b * b);

                    // Če še nismo dosegli ciljne dolžine, povečaj dolžino
                    if (trenutnaDolzina[i] < dolzina) {
                        trenutnaDolzina[i] += hitrost;

                        // Če preseže cilj, jo omejimo na maksimalno dolžino
                        if (trenutnaDolzina[i] > dolzina) {
                            trenutnaDolzina[i] = (int) dolzina;
                        }

                        // Označi, da še vedno nekaj rase
                        kajSeRaste = true;
                    }
                }

                // Če se nič več ne rase, ustavi timer, animacija se konča
                if (!kajSeRaste) {
                    timer.stop();
                }

                // Posodobi prikaz na zaslonu
                repaint();

            }

        });

        // Začetek animacije
        timer.start();

    }


    /**
     * Metoda, ki vrne true, če je animacija rasti stebel končana.
     */

    public boolean jeKončana() {
        return !timer.isRunning();
    }


    /**
     * Metoda, ki nariše stebla rastlin. 
     */

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        // Če se še animacija ognja ni končala, še nismo zaželi z animacijo rasti stebel, zato se še ne riše nič
        if (!animacijaZagnana) {
            return;
        } 

        Graphics2D steblo = (Graphics2D) g;

        steblo.setColor(new Color(0, 100, 0));                                           // Nastavi barvo stebla na temno zeleno
        steblo.setStroke(new BasicStroke(4));                                            // Nastavi debelino črte na 4 piksle

        // Za vsako steblo posebej izračunamo vmesno točko glede na trenutno dolžino
        for (int i = 0; i < trenutniVrh.length; i++) {
            int a = noviVrh[i][0] - trenutniVrh[i][0];
            int b = noviVrh[i][1] - trenutniVrh[i][1];
            double dolzina = Math.sqrt(a * a + b * b);                                        // Izračuna razdaljo med točkama po pitagorjevem zreku

            // Faktor (od 0 do 1), koliko črte naj se trenutno prikaže
            double faktor = trenutnaDolzina[i] / dolzina;
            if (faktor > 1) {
                faktor = 1;
            }

            // Izračun končne (trenutne) točke za animirano črto
            int x2 = (int) (trenutniVrh[i][0] + a * faktor);
            int y2 = (int) (trenutniVrh[i][1] + b * faktor);
            
            steblo.drawLine(trenutniVrh[i][0], trenutniVrh[i][1], x2, y2);                   // Nariše črto od začetne do trenutne (animirane) točke

        }

    }

}