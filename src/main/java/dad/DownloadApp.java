package dad;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DownloadApp extends Application {

    ProgressController progressController = new ProgressController();
    @Override
    public void start(Stage primaryStage) throws Exception {

        ProgressController progressController = new ProgressController();

        // Interfaz
        Scene progressScene = new Scene(progressController.getRoot());

        Stage modificarStage = new Stage();
        modificarStage.setTitle("Gestor de Descargas");
        modificarStage.setScene(progressScene);
        modificarStage.show();
    }
}
