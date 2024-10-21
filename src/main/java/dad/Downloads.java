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
    private final StringProperty processProperty;
    private volatile boolean stopRequested = false;

    @Override
    public void run() {

        int progresoActual = 0;

        while (progresoActual < pesoArchivo && !stopRequested) {
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

                final int finalProgresoActual = progresoActual;
                final int finalVelocidadActual = velocidadActual;

                // Update the progress bar and text on the JavaFX thread
                Platform.runLater(() -> {
                    // Update the progress bar
                    progressProperty.set(progresoPercentage);

                    // Update the download progress text
                    processProperty.setValue(
                            nombre + " - Progreso " + finalProgresoActual + "MB de " + pesoArchivo + " MB "
                                    + "\n" + " | Velocidad -----> " + finalVelocidadActual + " Mb/s"
                    );
                });

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Downloads (String nombre, int velocidadDescarga, int pesoArchivo, IntegerProperty progressProperty, StringProperty processProperty) {
        this.nombre = nombre;
        this.velocidadDescarga = velocidadDescarga;
        this.pesoArchivo = pesoArchivo;
        this.progressProperty = progressProperty;
        this.processProperty = processProperty;
    }

    public void stopDownload() {
        stopRequested = true;
    }

    public String getNombre() {
        return nombre;
    }
}
