package Herramientas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

public class contextoMenu {

    MaterialDesignIcon ICON_EDIT = MaterialDesignIcon.TABLE_EDIT;

    MaterialDesignIcon ICON_DELETE = MaterialDesignIcon.DELETE_VARIANT;

    MaterialDesignIcon ICON_REFRESH = MaterialDesignIcon.REFRESH;

    private final JFXPopup popup;

    private final Node node;

    private JFXButton editar;

    private JFXButton eliminar;

    private JFXButton refrescar;

    public contextoMenu(Node node) {
        this.node = node;

        popup = new JFXPopup();
        popup.setPopupContent(getContent());
    }

    public void setAccionEditar(EventHandler action) {
        editar.setOnAction(action);
    }

    public void setAccionEliminar(EventHandler action) {
        eliminar.setOnAction(action);
    }

    public void setAccionRefrescar(EventHandler action) {
        refrescar.setOnAction(action);
    }

    public void mostrar() {
        node.setOnMouseClicked(ev -> {
            if (ev.getButton().equals(MouseButton.SECONDARY)) {
                popup.show(node);
                popup.setAnchorX(ev.getScreenX());
                popup.setAnchorY(ev.getScreenY());
            }
        });
    }
    
    public void ocultar() {
        popup.hide();
    }

    public JFXButton getEditarBoton() {
        return editar;
    }
    
    public JFXButton getEliminarBoton() {
        return eliminar;
    }

    private VBox getContent() {
        editar = new JFXButton("Editar");
        editar.setGraphic(new MaterialDesignIconView(ICON_EDIT));
        editar.setAlignment(Pos.BASELINE_LEFT);
        editar.setContentDisplay(ContentDisplay.LEFT);

        eliminar = new JFXButton("Eliminar");
        eliminar.setGraphic(new MaterialDesignIconView(ICON_DELETE));
        eliminar.setAlignment(Pos.BASELINE_LEFT);
        eliminar.setContentDisplay(ContentDisplay.LEFT);

        refrescar = new JFXButton("Refrescar");
        refrescar.setGraphic(new MaterialDesignIconView(ICON_REFRESH));
        refrescar.setAlignment(Pos.BASELINE_LEFT);
        refrescar.setContentDisplay(ContentDisplay.LEFT);

        VBox contextMenu = new VBox();
        contextMenu.setPrefSize(100, 130);
        contextMenu.getChildren().addAll(editar, eliminar, refrescar);

        return contextMenu;
    }
}
