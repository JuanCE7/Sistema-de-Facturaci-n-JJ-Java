package Animaciones;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInUp;
import animatefx.animation.Shake;
import javafx.scene.Node;

public class Animacion {

    public static void fadeInUp(Node node) {
        new FadeInUp(node).play();
    }
    
    public static void shake(Node node) {
        new Shake(node).play();
    }

    public static void tooltip(Node node, Node tooltip) {
        node.setOnMouseEntered(ev -> {
            FadeIn fadeIn = new FadeIn(tooltip);
            fadeIn.setSpeed(3);
            fadeIn.play();
            tooltip.setVisible(true);
        });

        node.setOnMouseExited(ev -> tooltip.setVisible(false));
    }
}
