package dad;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgressController implements Initializable {

    private final IntegerProperty firstProgressValue = new SimpleIntegerProperty();
    private final IntegerProperty secondProgressValue = new SimpleIntegerProperty();
    private final IntegerProperty thirdProgressValue = new SimpleIntegerProperty();

    private final StringProperty firstProcessProperty = new SimpleStringProperty();
    private final StringProperty secondProcessProperty = new SimpleStringProperty();
    private final StringProperty thirdProcessProperty = new SimpleStringProperty();;

    private final Downloads download1 = new Downloads("Mi_Imagen_Perfil.jgp", 5, 200, firstProgressValueProperty(), firstProcessProperty);
    private final Downloads download2 = new Downloads("Mi_Película_Bichada.mp4", 10, 500, secondProgressValueProperty(), secondProcessProperty);
    private final Downloads download3 = new Downloads("Mi_Juego_Elamigos.torrent", 20, 1000, thirdProgressValueProperty(), thirdProcessProperty);

    private final Thread downloadtask1 = new Thread(download1);
    private final Thread downloadtask2 = new Thread(download2);
    private final Thread downloadtask3 = new Thread(download3);

    @FXML
    private Pane root;

    @FXML
    private ProgressBar firstProgressBar;

    @FXML
    private TextArea firstProcess;

    @FXML
    private TextArea secondProcess;

    @FXML
    private TextArea thirdProcess;

    @FXML
    private ProgressBar secondProgressBar;

    @FXML
    private ProgressBar thirdProgressBar;

    @FXML
    private Label firstProcessLabel;

    @FXML
    private Label secondProcessLabel;

    @FXML
    private Label thirdProcessLabel;

    public ProgressController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProgressBarView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstProcess.textProperty().bind(firstProcessProperty);
        secondProcess.textProperty().bind(secondProcessProperty);
        thirdProcess.textProperty().bind(thirdProcessProperty);

        firstProgressBar.progressProperty().bind(firstProgressValue.divide(100.0));
        secondProgressBar.progressProperty().bind(secondProgressValue.divide(100.0));
        thirdProgressBar.progressProperty().bind(thirdProgressValue.divide(100.0));

        firstProgressValue.addListener(((observable, oldValue, newValue) -> onDescargaFinalizada()));
        secondProgressValue.addListener(((observable, oldValue, newValue) -> onDescargaFinalizada()));
        thirdProgressValue.addListener(((observable, oldValue, newValue) -> onDescargaFinalizada()));
    }

    private void onDescargaFinalizada() {
        if (firstProgressValue.get() == 100 &&
                secondProgressValue.get() == 100 &&
                thirdProgressValue.get() == 100) {

            Alert mensajeAviso = new Alert(Alert.AlertType.CONFIRMATION);
            mensajeAviso.setContentText("Descarga finalizada con éxito");
            mensajeAviso.show();
        }
    }

    public IntegerProperty firstProgressValueProperty() {
        return firstProgressValue;
    }

    public IntegerProperty secondProgressValueProperty() {
        return secondProgressValue;
    }

    public IntegerProperty thirdProgressValueProperty() {
        return thirdProgressValue;
    }

    public Pane getRoot() {
        return root;
    }

    @FXML
    void onStopAction(ActionEvent event) {

        download1.stopDownload();
        download2.stopDownload();
        download3.stopDownload();

        try {
            downloadtask1.join();
            downloadtask2.join();
            downloadtask3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            firstProgressBar.progressProperty().unbind();
            secondProgressBar.progressProperty().unbind();
            thirdProgressBar.progressProperty().unbind();

            firstProcessProperty.set("");
            secondProcessProperty.set("");
            thirdProcessProperty.set("");

            // Reset the progress bars to 0
            firstProgressBar.setProgress(0);
            secondProgressBar.setProgress(0);
            thirdProgressBar.setProgress(0);

            // Show confirmation alert
            Alert mensajeAviso = new Alert(Alert.AlertType.WARNING);
            mensajeAviso.setContentText("Descarga detenida");
            mensajeAviso.show();
            });
    }

    @FXML
    void onDownloadAction(ActionEvent event) {

        // Bind the progress bars to the new progress values
        firstProgressBar.progressProperty().bind(firstProgressValue.divide(100.0));
        secondProgressBar.progressProperty().bind(secondProgressValue.divide(100.0));
        thirdProgressBar.progressProperty().bind(thirdProgressValue.divide(100.0));

        // Create new download task instances
        Downloads download1 = new Downloads("Imagen_Nueva.jgp", 5, 200, firstProgressValueProperty(), firstProcessProperty);
        Downloads download2 = new Downloads("Música_Fiesta.mp4", 10, 200, secondProgressValueProperty(), secondProcessProperty);
        Downloads download3 = new Downloads("Drivers_GPU.exe", 20, 200, thirdProgressValueProperty(), thirdProcessProperty);

        firstProcessLabel.setText(download1.getNombre());
        secondProcessLabel.setText(download2.getNombre());
        thirdProcessLabel.setText(download3.getNombre());

        // Create new threads to execute the downloads
        Thread downloadTask1 = new Thread(download1);
        Thread downloadTask2 = new Thread(download2);
        Thread downloadTask3 = new Thread(download3);

        // Start the new threads
        downloadTask1.start();
        downloadTask2.start();
        downloadTask3.start();
    }
}
