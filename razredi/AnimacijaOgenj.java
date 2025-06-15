package razredi;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


/**
 * Razred AnimacijaOgenj naloži sliko ognja na koordinatah, ki jih klikne uporabnik.
 * Nato pošlje pet slik ognja proti določenim začetnim točkam rasti rastline.
 */

public class AnimacijaOgenj extends JPanel {

    // Ciljne točke za posamezen ogenj (točke, kam naj vsak ogenj "leti")
    private final int[][] ciljneTocke = {{100, 525}, {300, 525}, {500, 525}, {700, 525}, {900, 525}};

    // Velikost slike ognja, ki jo bomo risali
    private final int sirinaSlike = 30;
    private final int visinaSlike = 40;

    // Slika ognja, ki bo prikazana
    private BufferedImage slikaOgenj;

    // Tabela, ki bo hranila, trenutne pozicije ognjev med animacijo, tabela je dvodimenionalna [5][2] ([ogenj][koordinate ognja])
    private int[][] trenutnePozicije = new int[5][2];

    // Tabela, ki bo povedala, kateri ognji so še aktivni (se premikajo)
    private boolean[] aktivniOgnji = new boolean[5];

    // Hitrost premikanja ognja
    private final int hitrost = 5;

    // Timer, ki sproži posodobitev animacije vsakih 30 ms
    private Timer timer;


    /**
     * Konstruktor, ki naloži sliko ognja ter ustvari timer, ki bo sprožil animacijo.
     */

    public AnimacijaOgenj() {

        // Naloži sliko iz mape slike, če sike ni mogoče naložiti sproži napako
        try {
            slikaOgenj = ImageIO.read(new File("slike/ogenj.png"));
        } catch (IOException e) {
            e.printStackTrace();
            slikaOgenj = null;  
        }

        // Timer, ki kliče metodo posodobiAnimacijo vsakih 30 ms
        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                posodobiAnimacijo();
            }
        });

    }


    /**
     * Metoda, ki začne animacijo vseh ognjev iz začetne točke (x, y).
     * Trenutne pozicije ognjev se nastavijo na ta koordinatni par, ktivirajo se vsi ognji. Prav tako se zažene timer za animacijo.
     */

    public void zacetekAnimacije(int x, int y) {

        // Nastavi začetne pozicije in aktivnost vseh ognjev
        for (int i = 0; i < 5; i++) {

            //Nastavi začetno pozicijo ognja (x, y)
            trenutnePozicije[i][0] = x; 
            trenutnePozicije[i][1] = y; 

            // Nastavi vrednosti true, da je ogenj aktiven
            aktivniOgnji[i] = true;    

        }

        // Zažene animacijski timer
        timer.start(); 

    }


    /**
     * Metoda, ki posodobi pozicije vseh aktivnih ognjev, jih premakne proti njihovim ciljnim točkam in preveri, če so dosegli cilj.
     * Če cilja še niso dosegli, animacijo nadaljuje.
     * Če so dosegli cilj, animacijo ustavi.
     * Preveri, če je kateri ogenj še aktiven, če ni več noben ustvai timer. 
     */

    private void posodobiAnimacijo() {

        // Spremenljivka, ki bo hranila vrednost true, če je še kateri ogenj aktiven, sicer false
        boolean kajAktivno = false; 

        // Zanka za animacijo vseh ognjev
        for (int i = 0; i < 5; i++) {

            // Če ogenj ni aktiven, ga preskoči
            if (!aktivniOgnji[i]) {
                continue; 
            }

            // Trenutne koordinate ognja 
            int trenutniX = trenutnePozicije[i][0];
            int trenutniY = trenutnePozicije[i][1];

            // Ciljne koordinate ognja
            int ciljniX = ciljneTocke[i][0];
            int ciljniY = ciljneTocke[i][1];

            // Izračun razdalje med trenutno koordinato točke ognja ter ciljo točko po Pitagorjevem izreku
            int a = ciljniX - trenutniX;
            int b = ciljniY - trenutniY;
            double razdalja = Math.sqrt(a * a + b * b);

            // Če je ogenj že zelo blizu cilja preneha z animacijo
            if (razdalja < hitrost) {
                aktivniOgnji[i] = false;
            } 
            
            // Če še ni blizu cila izračuna normaliziran vektor premika s hitrostjo ter premakne ogenj po x in y osi
            else {
                // Normaliziran vektor premika s hitrostjo
                double vx = a / razdalja * hitrost;
                double vy = b / razdalja * hitrost;

                // Premik po x in y osi
                trenutnePozicije[i][0] += (int) vx;
                trenutnePozicije[i][1] += (int) vy;

                // Vsaj en ogenj se še premika
                kajAktivno = true; 

            }

        }

        // Če so še aktivni ognji, zahteva ponovno risanje
        if (kajAktivno) {
            repaint();
        } 

        // Če ni več aktivnih ognjev, ustavi timer, ker je animacija končana
        else {
            timer.stop();
        }

    }


    /**
     * Metoda, ki nariše ognj na okno uporabnika.
     */

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g); 

        // Če slike ni mogoče naložii, ne riši nič
        if (slikaOgenj == null) {
            return; 
        }

        for (int i = 0; i < 5; i++) {
            if (aktivniOgnji[i]) {
                // izračunaj zgornji levi kot slike za centriranje
                int x = trenutnePozicije[i][0] - sirinaSlike / 2;
                int y = trenutnePozicije[i][1] - visinaSlike / 2;

                g.drawImage(slikaOgenj, x, y, sirinaSlike, visinaSlike, this);

            }

        }

    }


    /**
     * Metoda, ki preveri, ali je animacija končana (timer ne teče), da se lahko začne animacija rasti rastlin.
     */

    public boolean jeKoncano() {
        return !timer.isRunning();
    }

}