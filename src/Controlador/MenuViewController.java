package Controlador;

import Animaciones.Animacion;
import Constantes.constantes;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuViewController implements Initializable {

    @FXML
    private AnchorPane rootSideMenu;

    @FXML
    private StackPane stckMenu;

    @FXML
    private AnchorPane rootContenedor;

    @FXML
    private AnchorPane tooltipInicio;

    @FXML
    private AnchorPane tooltipClientes;

    @FXML
    private AnchorPane tooltipVenta;

    @FXML
    private AnchorPane tooltipProductos;

    @FXML
    private AnchorPane tooltipAgregarUsuario;

    @FXML
    private AnchorPane tooltipTarjeta;

    @FXML
    private AnchorPane tooltipAcerca;

    @FXML
    private AnchorPane tooltipConfiguracion;

    @FXML
    private AnchorPane tooltipSalir;

    @FXML
    private JFXButton btnInicio;

    @FXML
    private JFXButton btnClientes;

    @FXML
    private JFXButton btnVentas;

    @FXML
    private JFXButton btnProductos;

    @FXML
    private JFXButton btnAgregarUsuario;

    @FXML
    private JFXButton btnTarjeta;

    @FXML
    private JFXButton btnAcerca;

    @FXML
    private JFXButton btnConfiguracion;

    @FXML
    private JFXButton btnSalir;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioWindowsInitialize();
        tooltips();
    }

    private void setDisableButtons(MouseEvent event) {
        setDisableButtons(event, btnInicio);
        setDisableButtons(event, btnClientes);
        setDisableButtons(event, btnVentas);
        setDisableButtons(event, btnProductos);
        setDisableButtons(event, btnAgregarUsuario);
        setDisableButtons(event, btnTarjeta);
        setDisableButtons(event, btnAcerca);
        setDisableButtons(event, btnConfiguracion);
        setDisableButtons(event, btnSalir);
    }

    private void inicioWindowsInitialize() {
        btnInicio.setDisable(true);
        showFXMLWindows("InicioView");
    }

    @FXML
    private void inicioWindows(MouseEvent event) {
        btnInicio.setDisable(true);
        showFXMLWindows("InicioView");
        setDisableButtons(event);
    }

    @FXML
    private void clientesWindows(MouseEvent event) {
        showFXMLWindows("ClientesView");
        setDisableButtons(event);
    }

    @FXML
    private void ventasWindows(MouseEvent event) {
        showFXMLWindows("VentasView");
        setDisableButtons(event);
    }

    @FXML
    private void configuracionWindows(MouseEvent event) {
        showFXMLWindows("ConfiguracionView");
        setDisableButtons(event);
    }

    @FXML
    private void tarjetaWindows(MouseEvent event) {
        showFXMLWindows("tarjetaView");
        setDisableButtons(event);
    }

    @FXML
    private void acercaWindows(MouseEvent event) {
        showFXMLWindows("AcercaView");
        setDisableButtons(event);
    }

    @FXML
    private void productosWindows(MouseEvent event) {
        showFXMLWindows("ProductosView");
        setDisableButtons(event);
    }

    @FXML
    private void agregarUsuarioWindows(MouseEvent event) {
        showFXMLWindows("UsuariosView");
        setDisableButtons(event);
    }

    @FXML
    private void loginWindow() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(constantes.VIEW_LOGIN));
        Stage stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.getIcons().add(new Image(constantes.ICONO_PROGRAMA));
        stage.show();
        closeStage();
    }

    private void closeStage() {
        ((Stage) btnInicio.getScene().getWindow()).close();
    }

    private void setDisableButtons(MouseEvent event, JFXButton button) {
        if (event.getSource().equals(button)) {
            button.setDisable(true);
        } else {
            button.setDisable(false);
        }
    }

    private void tooltips() {
        Animacion.tooltip(btnInicio, tooltipInicio);
        Animacion.tooltip(btnInicio, tooltipInicio);
        Animacion.tooltip(btnClientes, tooltipClientes);
        Animacion.tooltip(btnVentas, tooltipVenta);
        Animacion.tooltip(btnConfiguracion, tooltipConfiguracion);
        Animacion.tooltip(btnSalir, tooltipSalir);
        Animacion.tooltip(btnProductos, tooltipProductos);
        Animacion.tooltip(btnTarjeta, tooltipTarjeta);
        Animacion.tooltip(btnAcerca, tooltipAcerca);
        Animacion.tooltip(btnAgregarUsuario, tooltipAgregarUsuario);
    }

    private void showFXMLWindows(String FXMLName) {
        rootContenedor.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(constantes.PACKAGE_VISTA + FXMLName + ".fxml"));
            Parent root = loader.load();
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            rootContenedor.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JFXButton getBtnTarjeta() {
        return btnTarjeta;
    }

    public JFXButton getBtnAgregarUsuario() {
        return btnAgregarUsuario;
    }

    public JFXButton getBtnAcerca() {
        return btnAcerca;
    }
}
