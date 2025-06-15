import razredi.*;

import java.awt.*;
import javax.swing.*;


// Glavna koda za izvajanje programa - Rastline

// Uporabniku se odpre okno, ki je razdeljeno na dva dela - glavni ter spodnji del.
// Spodnji del predstavlja podlago, iz katere rastejo rastline. Na njem so narisane korenine, ki predstavljajo točko, iz katere raste rastlina.
// Glavni del je namenjen uporabniku, po njem lahko poljubno klika. Ob kliku se pojavi Sonce, ki pošlje "energijo" koreninam rastline, da lahko le-ta raste.
// Korenina rastline, ki je najbližje Soncu dobi največ energije (100), korenina rastline, ki pa je najbolj oddaljena od Sonca, pa je dobi najmanj (20).
// Število enot energije, ki jih korenina prejme, predstavlja število enot, za katere bo rastlina zrastla.
// V igri "nastopa" pet rastlin. Na prvi rastlini, ki doseže 400 ali več enot, "zraste" zlat cvet. Na ostalih zraste rdeč. 
// Če ob istem kliku več rastlin doseže ali preseže velikost 400, zraste zlat cvet na vseh teh steblih rastlin.
// Program se zaključi, ko vsaj ena rastlina doseže velikost 400.


public class Rastline {
    public static void main(String[] args) {
        
        JFrame okno = new JFrame("Rastline");                           // Ustvari novo okno z naslovom "Rastline" 
        okno.setSize(1000, 600);                                 // Nastavi velikost okna na širino 1000 in višino 600 pikslov
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                  // Program se zapre, ko uporabnik zapre okno
        okno.setLocationRelativeTo(null);                                   // Okno postavi na sredino zaslona
        okno.setResizable(false);                                   // Onemogoči spreminjanje velikosti okna
        
        GlavniDel glavniDel = new GlavniDel();                                // Ustvari glavni del okna
        okno.add(glavniDel);                                                  // Doda glavni del v okno

        NarisiTla narisiTla = new NarisiTla();                                // Narise tla igre
        narisiTla.setPreferredSize(new Dimension(1000, 50));     // Doloci velikost tal
        okno.add(narisiTla, BorderLayout.SOUTH);                              // Doda tla na spodnjem delu okna

        okno.setVisible(true);                                              // Pokaže okno na zaslonu

    }

}