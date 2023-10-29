package Preloader;

import Constantes.constantes;
import Controlador.PreloaderControl;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ElPreloader extends Preloader {

    private Stage preloaderStage;
    private Scene scene;

    public ElPreloader() {
    }

    @Override
    public void init() throws Exception {

        Parent root1 = FXMLLoader.load(getClass().getResource("/Vista/splashScreen.fxml"));
        scene = new Scene(root1);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.getIcons().add(new Image(constantes.ICONO_PROGRAMA));
        preloaderStage.show();
    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {

        if (info instanceof ProgressNotification) {
            PreloaderControl.label.setText("Cargando " + ((ProgressNotification) info).getProgress() * 100 + "%");
            PreloaderControl.statProgressBar.setProgress(((ProgressNotification) info).getProgress());
            PreloaderControl.statProgressBar1.setProgress(((ProgressNotification) info).getProgress());
        }
    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_START:
                preloaderStage.hide();
                break;
        }
    }
}
