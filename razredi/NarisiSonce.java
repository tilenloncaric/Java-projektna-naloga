package razredi;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


/**
 * Razred NarisiSonce sprejme koordinate klika uporabnika na glavnem panelu. 
 * Prejete koordinate predstavljo središče slike, ki jo tam izriše.
 * Slika je velikosti 50 * 50.
 */

public class NarisiSonce {

    // Spremenljivka za shranjevanje slike sonca
    private BufferedImage slika;

    // Spremenljivki, ki bosta predstavljali koordinate središča za izris slike
    private int x;
    private int y;

    // Spremenljivki, ki hranita sirino in visino slike, ki jo želimo narisati 
    private final int sirina = 50;
    private final int visina = 50;


    /**
     * Konstruktor razreda, ki naloži sliko iz mape
     */

    public NarisiSonce() {

        try {
            slika = ImageIO.read(getClass().getClassLoader().getResourceAsStream("slike/sonce.png"));        // Naloži sliko "sonce.png" iz mape "slike"
        } catch (IOException e) {
            e.printStackTrace();
            slika = null;                                                                                         // Pomeni, da slike ni
        }

    }


    /**
     * Metoda, ki nastavi koordinate x in y, kjer se bo narisala slika.
     */

    public void nastaviKoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Metoda, ki nariše sliko na platno.
     */

    public void narisi(Graphics g) {
        
        if (slika != null) {                                                                            

            // Izračuna koordinate zgornjega levega kota slike, da se slika narišane centrirano glede na klik uporabnika
            int xZgornjiLevi = x - sirina / 2;
            int yZgornjiLevi = y - visina / 2;

            // Nariše sliko
            g.drawImage(slika, xZgornjiLevi, yZgornjiLevi, sirina, visina, null);

        }

    }

}