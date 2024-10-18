package dad;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;

import java.util.Random;

public class Downloads implements Runnable {

    private final String nombre;
    private final int velocidadDescarga;
    private final int pesoArchivo;
    private final IntegerProperty progressProperty;
    private StringProperty processProperty;

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

                processProperty.setValue(nombre + " - Progreso " + progresoActual + "MB de " + pesoArchivo + " MB " + " | Velocidad -----> " + velocidadActual + " Mb/s" + "\n");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(nombre + " ha finalizado");
    }

    public Downloads (String nombre, int velocidadDescarga, int pesoArchivo, IntegerProperty progressProperty, StringProperty processProperty) {
        this.nombre = nombre;
        this.velocidadDescarga = velocidadDescarga;
        this.pesoArchivo = pesoArchivo;
        this.progressProperty = progressProperty;
        this.processProperty = processProperty;
    }
}
