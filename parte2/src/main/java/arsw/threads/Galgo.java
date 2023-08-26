package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 *
 * @author rlopez
 */
public class Galgo extends Thread {
    private final RegistroLlegada regl;
    private final Carril carril;
    private int paso;
    private boolean dormido;

    public Galgo(Carril carril, String name, RegistroLlegada reg) {
        super(name);
        this.carril = carril;
        paso = 0;
        this.regl = reg;
    }

    public void corra() throws InterruptedException {
        while (paso < carril.size()) {
            if (dormido) this.dormir();
            Thread.sleep(100);
            carril.setPasoOn(paso++);
            carril.displayPasos(paso);
            if (paso == carril.size()) {
                carril.finish();
                this.registrarLlegada();
            }
        }
    }

    public void registrarLlegada() {
        synchronized (regl) {
            int ubicacion = regl.getUltimaPosicionAlcanzada();
            regl.setUltimaPosicionAlcanzada(ubicacion + 1);
            System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);
            if (ubicacion == 1) regl.setGanador(this.getName());
        }
    }

    public void dormir() {
        synchronized (regl) {
            try {
                regl.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public void cambiarEstadoDormido() {
        dormido = !dormido;
    }

    @Override
    public void run() {

        try {
            corra();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
