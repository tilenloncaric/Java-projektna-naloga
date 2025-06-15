package razredi;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;


/**
 * Razred Cvet dobi tabelo točk, ki vsebuje vrhove stebel rastlin.
 * Preveri, če je katera y koorinata v kateri točki manjša ali enaka 115. Če to velja nariše na stebla cvetove.
 * Na vseh vrhovih, kjer je y koorinata manjša ali enaka 115, nariše cvet (60 * 60), sicer nariše cvet (30 * 30).
 */

public class Cvet extends JPanel {

    // Tabela, ki bo hranila koordinate vrhov stebel rastlin
    private int[][] trenutniVrh;  

    // Spremenljivka, ki bo hranila vrednost true, če bo katera točka vsebovala y koordinato manjšo ali enako 110
    private boolean odgovor = false; 

    // Spremenljivki za zlat in rdeč cvet
    private BufferedImage rdecCvet;  


    /**
     * Konstruktor razreda Cvet.
     * @param trenutniVrh tabela koordinat točk, kjer želimo risati cvetje
     */

    public Cvet(int[][] trenutniVrh) {
        this.trenutniVrh = trenutniVrh;

        // Preveri, če obstaja vsaj ena točka z y koordinato manjšo ali enako 110
        for (int i = 0; i < trenutniVrh.length; i++) {

            // Če takšna koordinata obstaja, nastavi odgovor na true in konča z izvajanjem zanke
            if (trenutniVrh[i][1] <= 115) {
                odgovor = true;  
                break;           
            }

        }

        // Naloži sliko iz mape slike, če nalaganje ne uspe, izpiše napako
        try {      
            rdecCvet = ImageIO.read(new File("slike/rdecCvet.png"));    
        } catch (IOException e) {
            e.printStackTrace();  
        }

    }

    
    /**
     * Metoda za risanje komponent, ki nariše cvetove, če je vrednost spremenljivke odgovor true.
     */

    @Override
    protected void paintComponent(Graphics narisiCvet) {

        super.paintComponent(narisiCvet);

        // Če je vrednost spremenljivke odgovor false, ne rišemo ničesar
        if (!odgovor) {
            return;
        }

        Graphics2D cvet = (Graphics2D) narisiCvet;

        // Sprehodi se čez vse točke in riše ustrezne cvetove
        for (int i = 0; i < trenutniVrh.length; i++) {

            // Inicializira x in y koordinato za risanje cveta
            int x = trenutniVrh[i][0];
            int y = trenutniVrh[i][1];

            // Nariše cvet centrirano glede vrh stebla rastline
            if (y <= 115) {
                if (rdecCvet != null) {
                    cvet.drawImage(rdecCvet, x - 25, y - 25, 60, 60, null);
                }

            // Nariše cvet centrirano glede vrh stebla rastline
            } else {
                if (rdecCvet != null) {
                    cvet.drawImage(rdecCvet, x - 15, y - 15, 30, 30, null);
                }

            }

        }

    }

}