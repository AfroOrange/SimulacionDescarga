package dad;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgressController implements Initializable {

    private IntegerProperty firstProgressValue = new SimpleIntegerProperty();
    private IntegerProperty secondProgressValue = new SimpleIntegerProperty();
    private IntegerProperty thirdProgressValue = new SimpleIntegerProperty();

    @FXML
    private ProgressBar firstProgressBar;

    @FXML
    private Pane root;

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
        // Bind the ProgressBar's progressProperty to the IntegerProperty (as percentage)
        firstProgressBar.progressProperty().bind(firstProgressValue.divide(100.0));
        secondProgressBar.progressProperty().bind(secondProgressValue.divide(100.0));
        thirdProgressBar.progressProperty().bind(thirdProgressValue.divide(100.0));

        Thread download1 = new Thread(new Downloads("Archivo1.jgp", 5, 50, firstProgressValueProperty()));
        Thread download2 = new Thread(new Downloads("Archivo2.iso", 20, 100, secondProgressValueProperty()));
        Thread download3 = new Thread(new Downloads("Archivo3.mp4", 20, 200, thirdProgressValueProperty()));

        download1.start();
        download2.start();
        download3.start();
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
}
