package dad;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;

import java.util.Random;

public class Downloads implements Runnable {

    private final String nombre;
    private final int velocidadDescarga;
    private final int pesoArchivo;
    private final IntegerProperty progressProperty;


    @Override
    public void run() {

        System.out.println(nombre + " ha comenzado a descargarse");

        int progresoActual = 0;

        while (progresoActual < pesoArchivo) {
            try {
                int modificador = new Random().nextInt(velocidadDescarga);
                if (modificador == 0) {
                    modificador = 1;
                }

                int velocidadActual = velocidadDescarga / modificador;
                if (velocidadActual == 0) {
                    velocidadActual = 1;
                }

                Thread.sleep(velocidadActual);
                progresoActual++;

                final int progresoPercentage = (int) ((double) progresoActual / pesoArchivo * 100);
                Platform.runLater(() -> progressProperty.set(progresoPercentage));

                System.out.println(nombre + " - Progreso " + progresoActual + "MB de " + pesoArchivo + " MB " + " | Velocidad -----> " + velocidadActual + " Mb/s");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(nombre + " ha finalizado");
    }

    public Downloads (String nombre, int velocidadDescarga, int pesoArchivo, IntegerProperty progressProperty) {
        this.nombre = nombre;
        this.velocidadDescarga = velocidadDescarga;
        this.pesoArchivo = pesoArchivo;
        this.progressProperty = progressProperty;
    }
}
