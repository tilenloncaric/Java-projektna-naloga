package razredi;

import java.awt.*;
import javax.swing.*;


/**
 * Razred NarisiTla nariše:
 * - rjav pravokotnik (tla) velikosti 1000 * 50 (dolžina * višina),
 * - zeleno črto (rob dal, ki predstavlja travo),
 * - 5 korenin, iz katerih bodo rastle rastline.
 */

public class NarisiTla extends JPanel {        
    
    // Koordinate zgornje leve točke pravokotnika (tla)
    private final int x = 0;                     
    private final int y = 0;        

    // Dimenzije pravokotnika, ki predstavlja tla
    private final int visina = 50;              
    private final int sirina = 1000;  

    // Tabela koordinat točk (na spodnjem panelu), iz katerih bodo rastle rastline in s pomočjo katerih narišemo korenine 
    private int[][] korenine = {{100, 0}, {300, 0}, {500, 0}, {700, 0}, {900, 0}};


    /**
     * Glavna metoda za risanje tal, korenin in trave.
     */

    @Override
    protected void paintComponent(Graphics risbaTal) {

        super.paintComponent(risbaTal); 
        Graphics2D tla = (Graphics2D) risbaTal; 

        // Risanje tal - rjav pravokotnik
        Color svetloRjava = new Color(205, 133, 63);                                    // Definirana svetlo rjavo barvo
        tla.setColor(svetloRjava);                                                            // Nastavi svetlo rjavo barvo
        tla.fillRect(x, y, sirina, visina);                                                   // Nariše pravokotnik in ga pobarva s svetlo rjavo barvo

        // Risanje korenin
        tla.setStroke(new BasicStroke(2));                                              // Nastavi debelino črt za risanje korenin
        Color rjava = new Color(139, 69, 19);                                           // Definirana temno rjavo barva, da se korenine razlikuje od "zemlje"
        tla.setColor(rjava);                                                                  // Nastavi temno rjavo barvo

        // Zanka se sprehodi čez vse točke iz tabele ter nariše korenine 
        for (int[] tocka : korenine) {
            int x = tocka[0];                                                                 // X koordinata točke iz katere izrašča korenina
            int y = tocka[1];                                                                 // Y koordinata točke iz katere izrašča korenina

            // Nariše glavno korenino
            tla.drawLine(x, y, x, y + 30);

            // Nariše stranske korenine
            tla.drawLine(x, y + 10, x - 8, y + 17);  
            tla.drawLine(x, y + 10, x + 8, y + 17);    
            tla.drawLine(x, y + 17, x - 5, y + 22);  
            tla.drawLine(x, y + 17, x + 5, y + 22);    

        }

        // Risanje trate - zelena črta
        tla.setColor(Color.GREEN);                                                           // Nastavi zeleno barvo
        tla.setStroke(new BasicStroke(3));                                             // Nastavi debelino črte
        tla.drawLine(x, y, sirina, y);                                                       // Nariše črto

    }

}