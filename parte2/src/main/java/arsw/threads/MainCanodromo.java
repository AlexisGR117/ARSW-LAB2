package arsw.threads;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainCanodromo {

    private static final RegistroLlegada reg = new RegistroLlegada();
    private static Galgo[] galgos;
    private static Canodromo can;

    public static void main(String[] args) {
        can = new Canodromo(17, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);

        //Acción del botón start
        can.setStartAction(
                new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        //como acción, se crea un nuevo hilo que cree los hilos
                        //'galgos', los pone a correr, y luego muestra los resultados.
                        //La acción del botón se realiza en un hilo aparte para evitar
                        //bloquear la interfaz gráfica.
                        ((JButton) e.getSource()).setEnabled(false);
                        new Thread() {
                            @Override
                            public void run() {
                                for (int i = 0; i < can.getNumCarriles(); i++) {
                                    //crea los hilos 'galgos'
                                    galgos[i] = new Galgo(can.getCarril(i), String.valueOf(i), reg);
                                    //inicia los hilos
                                    galgos[i].start();
                                }
                                for (Galgo galgo : galgos) {
                                    try {
                                        galgo.join();
                                    } catch (InterruptedException ex) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                                can.winnerDialog(reg.getGanador(), reg.getUltimaPosicionAlcanzada() - 1);
                                System.out.println("El ganador fue: " + reg.getGanador());
                            }
                        }.start();

                    }
                }
        );

        can.setStopAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (Galgo galgo : galgos) galgo.cambiarEstadoDormido();
                        System.out.println("Carrera pausada!");
                    }
                }
        );

        can.setContinueAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (Galgo galgo : galgos) galgo.cambiarEstadoDormido();
                        synchronized (reg) {
                            reg.notifyAll();
                        }
                        System.out.println("Carrera reanudada!");
                    }
                }
        );

    }

}
