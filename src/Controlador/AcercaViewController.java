package Controlador;

import Animaciones.Animacion;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class AcercaViewController implements Initializable {
    
    private final String GITHUB = "https://github.com/JuanCE7";
    
    private final String FACEBOOK = "https://www.facebook.com/juandiegocarre/";
    
    private final String GMAIL = "https://www.google.com/";
    
    private final String YOUTUBE = "https://www.youtube.com/channel/UCtMgkCES0lMXNACaKkC6Szw";
    
    @FXML
    private Text nombreTienda;

    @FXML
    private TextFlow version;

    @FXML
    private TextFlow corporacion;

    @FXML
    private TextFlow copy;

    @FXML
    private VBox desarrollo;

    @FXML
    private Text desarrollado;

    @FXML
    private Text juancarreño;

    @FXML
    private MaterialDesignIconView facebook;

    @FXML
    private MaterialDesignIconView youtube;

    @FXML
    private MaterialDesignIconView github;

    @FXML
    private MaterialDesignIconView google;

    @FXML
    private Text juancastillo;

    @FXML
    private ImageView Logo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAnimations();
        setURL();
    }

    private void setURL() {
        url(GITHUB, github);
        url(FACEBOOK, facebook);
        url(GMAIL, google);
        url(YOUTUBE, youtube);
    }

    private void setAnimations() {
        transition(nombreTienda, 0);
        transition(Logo, 2);
        transition(version, 3);
        transition(corporacion, 4);
        transition(copy, 5);
        transition(desarrollo, 6);
        transition(juancarreño,7);
        transition(juancastillo,7);
        transition(facebook, 8);
        transition(youtube, 9);
        transition(github, 10);
        transition(google, 11);        
    }
    
    private void transition(Node node, int duration) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), node);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), node);
        fadeTransition.setFromValue(2);
        fadeTransition.setToValue(0.5);

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(duration));
        pauseTransition.setOnFinished(ev -> {
            PauseTransition pauseTransition2 = new PauseTransition();
            pauseTransition2.setDuration(Duration.seconds(0.1));
            pauseTransition2.setOnFinished(ev2 -> {
                node.setVisible(true);
            });

            pauseTransition2.play();
            Animacion.fadeInUp(node);
            fadeTransition.play();
        });

        pauseTransition.play();

        node.setOnMouseEntered(ev -> {
            fadeTransition.setToValue(1);
            fadeTransition.playFromStart();

            scaleTransition.setRate(1.0);
            scaleTransition.play();
        });

        node.setOnMouseExited(ev -> {
            fadeTransition.setDuration(Duration.millis(100));
            fadeTransition.setToValue(0.5);
            fadeTransition.playFromStart();

            scaleTransition.setRate(-1.0);
            scaleTransition.play();
        });
    }

    private void url(String url, Node node) {
        node.setOnMouseClicked(ev -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger(AcercaViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }  
    
}
