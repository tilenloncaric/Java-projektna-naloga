package razredi;

import java.util.Arrays;
import java.util.Collections;


/**
 * Razred rast sprejme tabelo, ki vsebuje razdalje med točkami začetne rasti rastline in koordinatami točke, kjer je na oknu kliknil uporabnik.
 * Razred ustvari novo tabelo, le-ta vsebuje urejene vrednosti razdalj. Vrednosti so urejene od najmanjše do največje.
 * Določi tudi za koliko posamezna rastlina "zraste". Tabelo, ki vsebuje vrednosti, koliko rastlina v posameznem kliku zraste, tudi vrne.
 */

public class Rast {

    /**
     * Glavna metoda, ki koordinira urejanje in dodeljevanje rasti.
     */
    
    public static int[] izracunajRast(double[] neurejeneRazdalje) {

        // Upodaribo Double in ne double, ker uporaba Collections na primitivnem tipu double ne deluje
        // Uredi podatke o razdaljah v padajočem vrstnem redu
        Double[] urejeneRazdalje = urediRazdalje(neurejeneRazdalje);     

        // Dodeli rast vsaki razdalji na osnovi njenega položaja v urejenem seznamu
        return dodeliRasti(neurejeneRazdalje, urejeneRazdalje);

    }


    /**
     * Metoda, ki uredi razdalje v padajočem vrstnem redu.
     */

    private static Double[] urediRazdalje(double[] neurejeneRazdalje) {

        // Ustvari tabelo, ki bo hranila urejene razdalje
        Double[] urejeneRazdalje = new Double[neurejeneRazdalje.length];                 

        // Naredi kopijo tabele z neurejenimi razdaljamai
        for (int i = 0; i < neurejeneRazdalje.length; i++) {
            urejeneRazdalje[i] = neurejeneRazdalje[i];
        }
        
        // Uredi podatke v tabeli v padajočem vrstnem redu
        Arrays.sort(urejeneRazdalje, Collections.reverseOrder());

        return urejeneRazdalje;

    }


    /**
     * Metoda, ki dodeli rast rastlini na podlagi položaja v urejenem polju.
     */

    private static int[] dodeliRasti(double[] neurejeneRazdalje, Double[] urejeneRazdalje) {
        
        // Ustvari tabelo, ki bo hranila dodeljene rasti
        int[] kolikoZraste = new int[neurejeneRazdalje.length];

        // Išče ujemanje 
        for (int i = 0; i < neurejeneRazdalje.length; i++) {
            for (int j = 0; j < urejeneRazdalje.length; j++) {

                // Ko najde ujemanje dodelirast rastlini na podlagi položaja v tabeli z urejenimi rastmi
                if (neurejeneRazdalje[i] == urejeneRazdalje[j]) {
                    kolikoZraste[i] = (j + 1) * 20;
                    break;

                }

            }

        }

        return kolikoZraste;

    }

}