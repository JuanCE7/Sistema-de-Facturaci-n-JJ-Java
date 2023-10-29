package Notificaciones;

import Constantes.constantes;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ConstructorNotificacion {

    private static Image icon;

    private static String title;

    public static void create(TipoNotificacion type, String message) {
        setFunction(type);
        Notifications notifications = Notifications.create();
        notifications.title(title);
        notifications.text(message);
        notifications.graphic(new ImageView(icon));
        notifications.hideAfter(Duration.seconds(6));
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();
    }

    private static void setFunction(TipoNotificacion type) {
        switch (type) {
            case INFORMATION:
                title = "¡Information!";
                icon = new Image(constantes.IMG_INFORMACION);
            break;
            
            case ERROR:
                title = "¡Error!";
                icon = new Image(constantes.IMG_ERROR);
            break;
            
            case SUCCESS:
                title = "¡Success!";
                icon = new Image(constantes.IMG_EXITO);
            break;
            
            case INVALID_ACTION:
                title = "¡Invalid action!";
                icon = new Image(constantes.IMG_ERROR);
            break;
        }
    }
}
