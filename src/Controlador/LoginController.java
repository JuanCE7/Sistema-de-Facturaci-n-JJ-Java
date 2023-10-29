package Controlador;

import Animaciones.Animacion;
import BD.BDayuda;
import BD.BDconexion;
import Constantes.constantes;
import Expresiones.ControladorExcepciones;
import Modelo.Usuario;
import Notificaciones.ConstructorNotificacion;
import Notificaciones.TipoNotificacion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

    @FXML
    private ImageView log1;

    @FXML
    private JFXTextField txtUsuario;

    @FXML
    private JFXTextField txtContraseña;

    @FXML
    private JFXPasswordField pfContraseña;

    @FXML
    private JFXButton btnIngresar;

    @FXML
    private JFXButton close;

    @FXML
    private Pane icono;

    @FXML
    private FontAwesomeIconView icon;

    @FXML
    private AnchorPane register;

    @FXML
    private ImageView log2;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtCedula;

    @FXML
    private JFXTextField txtCorreo;

    @FXML
    private JFXButton btnRegistrar;

    @FXML
    private JFXTextField txtUsuario1;

    @FXML
    private JFXTextField txtContraseña1;

    @FXML
    private JFXPasswordField pfContraseña1;

    @FXML
    private ImageView more;

    @FXML
    private JFXTextField txtTelefono;

    @FXML
    private JFXTextField txtDireccion;

    @FXML
    private Pane icono1;

    @FXML
    private FontAwesomeIconView icon1;

    @FXML
    private Label lbCedula, lbCorreo;

    private TranslateTransition preShow;

    private TranslateTransition preHide;

    private double x, y;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        verContraseña();
        verContraseña1();
        log1.setImage(new Image("/Imagenes/GaFon.jpg"));
        log2.setImage(new Image("/Imagenes/GaFon.jpg"));
    }

    @FXML
    public void preShow() {
        preShow = new TranslateTransition();
        preShow.setDuration(javafx.util.Duration.seconds(0.4));
        preShow.setNode(register);
        preShow.setToX(-570);
        preShow.play();
        register.setTranslateX(0);

        FadeTransition fade = new FadeTransition();
        fade.setDuration(javafx.util.Duration.seconds(0.5));
        fade.setToValue(0);
        fade.setAutoReverse(true);
        fade.setNode(more);
        fade.play();
    }

    @FXML
    public void postHide() {
        preHide = new TranslateTransition();
        preHide.setDuration(javafx.util.Duration.seconds(0.4));
        preHide.setNode(register);
        preHide.setToX(0);
        preHide.play();
        register.setTranslateX(-30);

        FadeTransition fade = new FadeTransition();
        fade.setDuration(javafx.util.Duration.seconds(0.4));
        fade.setFromValue(0.1);
        fade.setToValue(10);
        fade.setNode(more);
        fade.play();
    }

    @FXML
    public void close() {
        try {
            Process p = Runtime.getRuntime().exec("C:/xampp/xampp_stop.exe");
            System.out.println("BASE APAGADA");
        } catch (IOException e) {
            System.out.println("NO SE APAGÓ LA BASE");
        }
        System.exit(0);
    }

    @FXML
    public void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    public void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    private void login(Event event) throws IOException, ClassNotFoundException {
        String usuario = txtUsuario.getText();
        String contraseña = pfContraseña.getText();

        if (usuario.isEmpty() && contraseña.isEmpty()) {
            Animacion.shake(txtUsuario);
            Animacion.shake(pfContraseña);
            Animacion.shake(icono);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_DATOS_INSUFICIENTES);
            return;
        }

        if (usuario.isEmpty()) {
            txtUsuario.requestFocus();
            Animacion.shake(txtUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_USUARIO_VACIO);
            return;
        }

        if (contraseña.isEmpty()) {
            pfContraseña.requestFocus();
            Animacion.shake(pfContraseña);
            Animacion.shake(icono);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CONTRA_VACIO);
            return;
        }

        try {
            String sql = "SELECT id_usuario, nombre_usuario, tipoUsuario FROM usuarios WHERE usuario = '" + usuario + "' AND contraseña_usuario = '" + contraseña + "'";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Integer id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre_usuario");
                String tipoUsuario = rs.getString("tipoUsuario");
                Usuario us = new Usuario(tipoUsuario, id, nombre);
                BDayuda.insertarIngreso(us);
                loadMain(us);
                ConstructorNotificacion.create(TipoNotificacion.INFORMATION, "BIENVENIDO AL SISTEMA " + nombre + "!");
            } else {
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.CREDENCIAL_INVALIDO);
                Animacion.shake(txtUsuario);
                Animacion.shake(pfContraseña);
                Animacion.shake(txtContraseña);
                Animacion.shake(icono);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
    }

    private void loadMain(Usuario us) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(constantes.VIEW_MENU));
            Parent root = loader.load();
            MenuViewController menu = loader.getController();
            if (us.getTipoUsuario().equals("Administrador")) {
                menu.getBtnAgregarUsuario().setVisible(true);
            } else {
                menu.getBtnAgregarUsuario().setVisible(false);
            }
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image(constantes.ICONO_PROGRAMA));
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setMinHeight(constantes.ALTURA_MIN);
            stage.setMinWidth(constantes.ANCHO_MIN);
            stage.setTitle(constantes.TITULO);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            closeStage();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void closeStage() {
        ((Stage) txtUsuario.getScene().getWindow()).close();
    }

    @FXML
    public void register() {
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        String correo = txtCorreo.getText().trim();
        String usuario = txtUsuario1.getText().trim();
        String contraseña = pfContraseña1.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nombre.isEmpty() && cedula.isEmpty() && correo.isEmpty() && usuario.isEmpty() && contraseña.isEmpty() && telefono.isEmpty() && direccion.isEmpty()) {
            Animacion.shake(txtNombre);
            Animacion.shake(txtCedula);
            Animacion.shake(txtCorreo);
            Animacion.shake(txtUsuario1);
            Animacion.shake(pfContraseña1);
            Animacion.shake(icono1);
            Animacion.shake(txtTelefono);
            Animacion.shake(txtDireccion);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_DATOS_INSUFICIENTES);
            return;
        }

        if (nombre.isEmpty()) {
            txtNombre.requestFocus();
            Animacion.shake(txtNombre);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_NOMBRE_VACIO);
            return;
        }

        if (usuario.isEmpty()) {
            txtUsuario1.requestFocus();
            Animacion.shake(txtUsuario1);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_USUARIO_VACIO);
            return;
        }
        
        if (contraseña.isEmpty()) {
            pfContraseña1.requestFocus();
            Animacion.shake(pfContraseña1);
            Animacion.shake(icono1);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CONTRA_VACIO);
            return;
        }

        if (telefono.isEmpty()) {
            txtTelefono.requestFocus();
            Animacion.shake(txtTelefono);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_TEL_VACIO);
            return;
        }

        if (direccion.isEmpty()) {
            txtDireccion.requestFocus();
            Animacion.shake(txtDireccion);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_DIR_VACIO);
            return;
        }
        
        if (!correo.isEmpty()) {
            if (BDayuda.verificarExistencia("usuarios", "correo_usuario", correo) > 0) {
                txtCorreo.requestFocus();
                Animacion.shake(txtCorreo);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_USUARIO_EXISTE_CORREO);
                return;
            }
        }

        if (!cedula.isEmpty()) {
            if (BDayuda.verificarExistencia("usuarios", "cedula_usuario", cedula) > 0) {
                txtCedula.requestFocus();
                Animacion.shake(txtCedula);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_USUARIO_EXISTE_CEDULA);
                return;
            }
        }

        if (ControladorExcepciones.cedula(cedula) == false || ControladorExcepciones.Verificacion(cedula) == false) {
            txtCedula.requestFocus();
            Animacion.shake(txtCedula);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CEDULA_INVALIDO);
            return;
        }

        if (ControladorExcepciones.correo(correo) == false) {
            txtCorreo.requestFocus();
            Animacion.shake(txtCorreo);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CORREO_INVALIDO);
            return;
        }

        if (usuario.length() < 4) {
            txtUsuario1.requestFocus();
            Animacion.shake(txtUsuario1);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_INGRESAR_4_CARACTERES);
            return;
        }

        if (!usuario.isEmpty()) {
            if (BDayuda.verificarExistencia("usuarios", "usuario", usuario) > 0) {
                txtUsuario1.requestFocus();
                Animacion.shake(txtUsuario1);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_USUARIO_EXISTE_USUARIO);
                return;
            }
        }

        if (contraseña.length() < 4) {
            pfContraseña1.requestFocus();
            Animacion.shake(pfContraseña1);
            Animacion.shake(icono1);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_INGRESAR_4_CARACTERES);
            return;
        }

        try {
            Usuario usu = new Usuario(txtUsuario1.getText(), pfContraseña1.getText(), "Empleado", txtNombre.getText(),
                    txtCedula.getText(), txtTelefono.getText(), txtCorreo.getText(), txtDireccion.getText());
            if (BDayuda.insertNuevoUsuario(usu)) {
                limpiarTexto();
                ConstructorNotificacion.create(TipoNotificacion.SUCCESS, constantes.MENSAJE_AGREGADO);
            } else {
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void limpiarTexto() {
        txtNombre.clear();
        txtCedula.clear();
        txtCorreo.clear();
        txtUsuario1.clear();
        pfContraseña1.clear();
        txtTelefono.clear();
        txtDireccion.clear();
    }

    @FXML
    private void verContraseña() {
        txtContraseña.managedProperty().bind(icon.pressedProperty());
        txtContraseña.visibleProperty().bind(icon.pressedProperty());
        txtContraseña.textProperty().bindBidirectional(pfContraseña.textProperty());

        pfContraseña.managedProperty().bind(icon.pressedProperty().not());
        pfContraseña.visibleProperty().bind(icon.pressedProperty().not());

        icon.pressedProperty().addListener((o, oldVal, newVal) -> {
            if (newVal) {
                icon.setIcon(FontAwesomeIcon.EYE);
            } else {
                icon.setIcon(FontAwesomeIcon.EYE_SLASH);
            }
        });
    }

    @FXML
    private void verContraseña1() {
        txtContraseña1.managedProperty().bind(icon1.pressedProperty());
        txtContraseña1.visibleProperty().bind(icon1.pressedProperty());
        txtContraseña1.textProperty().bindBidirectional(pfContraseña1.textProperty());

        pfContraseña1.managedProperty().bind(icon1.pressedProperty().not());
        pfContraseña1.visibleProperty().bind(icon1.pressedProperty().not());

        icon1.pressedProperty().addListener((o, oldVal, newVal) -> {
            if (newVal) {
                icon1.setIcon(FontAwesomeIcon.EYE);
            } else {
                icon1.setIcon(FontAwesomeIcon.EYE_SLASH);
            }
        });
    }

    @FXML
    public void eventKeyTwo(KeyEvent event) {
        Object evt = event.getSource();
        if (evt.equals(txtCedula)) {
            if (ControladorExcepciones.cedula(txtCedula.getText()) == false || ControladorExcepciones.Verificacion(txtCedula.getText()) == false) {
                txtCedula.requestFocus();
                lbCedula.setText("Cédula Invalida");
            } else {
                lbCedula.setText("");
            }
            if (txtCedula.getText().isEmpty()) {
                lbCedula.setText("");
            }
        } else if (evt.equals(txtCorreo)) {
            if (ControladorExcepciones.correo(txtCorreo.getText()) == false) {
                txtCorreo.requestFocus();
                lbCorreo.setText("Correo Invalido");
            } else {
                lbCorreo.setText("");
            }
            if (txtCorreo.getText().isEmpty()) {
                lbCorreo.setText("");
            }
        }
    }

    @FXML
    public void eventKey(KeyEvent event) {
        Object evt = event.getSource();
        if (evt.equals(txtUsuario)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !Character.isDigit(event.getCharacter().charAt(0)) || txtUsuario.getText().length() > 19) {
                event.consume();
            }
        } else if (evt.equals(pfContraseña)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !Character.isDigit(event.getCharacter().charAt(0)) || pfContraseña.getText().length() > 19) {
                event.consume();
            }
        } else if (evt.equals(txtNombre)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !event.getCharacter().equals(" ") || txtNombre.getText().length() > 29) {
                event.consume();
            }
        } else if (evt.equals(txtCedula)) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) || txtCedula.getText().length() > 14) {
                event.consume();
            }
        } else if (evt.equals(txtUsuario1)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !Character.isDigit(event.getCharacter().charAt(0)) || txtUsuario1.getText().length() > 19) {
                event.consume();
            }
        } else if (evt.equals(pfContraseña1)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !Character.isDigit(event.getCharacter().charAt(0)) || pfContraseña1.getText().length() > 19) {
                event.consume();
            }
        } else if (evt.equals(txtTelefono)) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) || txtTelefono.getText().length() > 9) {
                event.consume();
            }
        } else if (evt.equals(txtDireccion)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !event.getCharacter().equals(" ") || txtDireccion.getText().length() > 19) {
                event.consume();
            }
        }
    }
}
