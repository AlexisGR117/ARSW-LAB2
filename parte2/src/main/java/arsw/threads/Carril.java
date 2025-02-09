package arsw.threads;

import javax.swing.*;
import java.awt.*;

/**
 * Un carril del canodromo
 *
 * @author rlopez
 */
public class Carril {
    private final Color on = Color.CYAN;
    private final Color off = Color.LIGHT_GRAY;
    private final Color stop = Color.red;
    private final Color start = Color.GREEN;
    /**
     * Pasos del carril
     */
    private final JButton[] paso;

    /**
     * Bandera de llegada del carril
     */
    private final JButton llegada;

    private final String name;

    /**
     * Construye un carril
     *
     * @param nPasos Numero de pasos del carril
     * @param name   Nombre del carril
     */
    public Carril(int nPasos, String name) {
        paso = new JButton[nPasos];
        JButton bTmp;
        for (int k = 0; k < nPasos; k++) {
            bTmp = new JButton();
            bTmp.setBackground(off);
            paso[k] = bTmp;
        }
        llegada = new JButton(name);
        llegada.setBackground(start);
        this.name = name;
    }

    /**
     * Tama��o del carril en numero de pasos
     *
     * @return
     */
    public int size() {
        return paso.length;
    }

    public String getName() {
        return llegada.getText();
    }

    /**
     * Retorna el i-esimo paso del carril
     *
     * @param i
     * @return
     */
    public JButton getPaso(int i) {
        return paso[i];
    }

    /**
     * Retorna la bandera de llegada del carril
     *
     * @return
     */
    public JButton getLlegada() {
        return llegada;
    }

    /**
     * Indica que el paso i ha sido utilizado
     *
     * @param i
     */
    public void setPasoOn(int i) {
        paso[i].setText("o");
    }

    /**
     * Indica que el paso i no ha sido utilizado
     *
     * @param i
     */
    public void setPasoOff(int i) {
        paso[i].setText("");
    }

    /**
     * Indica que se ha llegado al final del carril
     */
    public void finish() {
        llegada.setText("!");
    }

    public void displayPasos(int n) {
        llegada.setText(String.valueOf(n));
    }

    /**
     * Reinicia el carril: ningun paso se ha usado, la bandera abajo.
     */
    public void reStart() {
        for (int k = 0; k < paso.length; k++) {
            paso[k].setBackground(off);
        }
        llegada.setBackground(start);
        llegada.setText(name);
    }
}
