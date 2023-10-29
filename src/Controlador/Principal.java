package Controlador;

import Constantes.constantes;
import Notificaciones.ConstructorNotificacion;
import Notificaciones.TipoNotificacion;
import Preloader.ElPreloader;
import com.sun.javafx.application.LauncherImpl;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Principal extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(constantes.VIEW_LOGIN));
        Scene scene = new Scene(root, 600, 500);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(constantes.ICONO_PROGRAMA));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            Process p = Runtime.getRuntime().exec("C:/xampp/xampp_start.exe");
            System.out.println("BASE ENCENDIDA");
        } catch (IOException e) {
            System.out.println("NO SE PUDO ENCENDER LA BASE");
        }
        LauncherImpl.launchApplication(Principal.class, ElPreloader.class, args);
    }
    
    @Override
    public void init() throws Exception {
        for (int i = 1; i <= 100; i++) {
            double progress = (double) i / 100;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
            Thread.sleep(50);
        }
    }
}