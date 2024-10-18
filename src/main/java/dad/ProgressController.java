package dad;

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

    private final Thread download1 = new Thread(new Downloads("Archivo1.jgp", 5, 200, firstProgressValueProperty(), firstProcessProperty));
    private final Thread download2 = new Thread(new Downloads("Archivo2.iso", 10, 200, secondProgressValueProperty(), secondProcessProperty));
    private final Thread download3 = new Thread(new Downloads("Archivo3.mp4", 20, 200, thirdProgressValueProperty(), thirdProcessProperty));

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



        Alert mensajeAviso = new Alert(Alert.AlertType.NONE);

        mensajeAviso.setAlertType(Alert.AlertType.CONFIRMATION);
        mensajeAviso.setContentText("Descarga detenida con éxito");
        mensajeAviso.show();
    }

    @FXML
    void onDownloadAction(ActionEvent event) {

        // Activación del Botón
        firstProgressBar.progressProperty().bind(firstProgressValue.divide(100.0));
        secondProgressBar.progressProperty().bind(secondProgressValue.divide(100.0));
        thirdProgressBar.progressProperty().bind(thirdProgressValue.divide(100.0));

        Thread download1 = new Thread(new Downloads("Archivo1.jgp", 5, 200, firstProgressValueProperty(), firstProcessProperty));
        Thread download2 = new Thread(new Downloads("Archivo2.iso", 10, 200, secondProgressValueProperty(), secondProcessProperty));
        Thread download3 = new Thread(new Downloads("Archivo3.mp4", 20, 200, thirdProgressValueProperty(), thirdProcessProperty));

        download1.start();
        download2.start();
        download3.start();
    }
}
