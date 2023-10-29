package Controlador;

import Alertas.ConstructorAlerta;
import Alertas.TipoAlerta;
import Animaciones.Animacion;
import BD.BDayuda;
import BD.BDconexion;
import Constantes.constantes;
import Modelo.Usuario;
import Notificaciones.ConstructorNotificacion;
import Notificaciones.TipoNotificacion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ConfiguracionViewController implements Initializable {

    private String usuar;
    @FXML
    private HBox headerContainer;
    @FXML
    private StackPane stckConfiguracion;
    @FXML
    private AnchorPane rootConfiguracion;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtContraseña;
    @FXML
    private JFXPasswordField txtConfirmContraseña;
    @FXML
    public Text textNombre;
    @FXML
    private Text textTipoUsuario;
    @FXML
    private JFXButton btnGuardar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        animationNodes();
        loadData();
    }

    private void loadData() {
        try {

            Integer id = BDayuda.ultimoIngreso();

            Integer idU = BDayuda.idUsuario(id);

            setearTxt(idU);

        } catch (SQLException ex) {
            Logger.getLogger(InicioViewController.class.getName()).log(Level.SEVERE, null, ex);
            ConstructorAlerta.create(TipoAlerta.ERROR, stckConfiguracion, rootConfiguracion, rootConfiguracion, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
    }

    private void setearTxt(Integer idU) throws SQLException {
        String sql3 = "SELECT nombre_usuario, usuario, contraseña_usuario, tipoUsuario FROM usuarios WHERE id_usuario = ?";
        PreparedStatement preparedStatementThree = BDconexion.getInstance().getConnection().prepareStatement(sql3);
        preparedStatementThree.setInt(1, idU);
        preparedStatementThree.execute();
        ResultSet resultSetThree = preparedStatementThree.executeQuery();
        String nombre = "";
        String usu = "";
        String contra = "";
        String tip = "";
        while (resultSetThree.next()) {
            nombre = resultSetThree.getString("nombre_usuario");
            usu = resultSetThree.getString("usuario");
            contra = resultSetThree.getString("contraseña_usuario");
            tip = resultSetThree.getString("tipoUsuario");
        }
        usuar = usu;
        txtNombre.setText(nombre);
        textNombre.setText(nombre);
        txtUsuario.setText(usu);
        txtContraseña.setText(contra);
        txtConfirmContraseña.setText(contra);
        textTipoUsuario.setText(tip);
    }

    @FXML
    private void actualizarPerfil() throws SQLException {
        String name = txtNombre.getText().trim();
        String user = txtUsuario.getText().trim();
        String password = txtContraseña.getText().trim();
        String confirmPassword = txtConfirmContraseña.getText().trim();

        if (name.isEmpty() && user.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
            Animacion.shake(txtNombre);
            Animacion.shake(txtUsuario);
            Animacion.shake(txtContraseña);
            Animacion.shake(txtConfirmContraseña);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_DATOS_INSUFICIENTES);
            return;
        }

        if (name.isEmpty()) {
            txtNombre.requestFocus();
            Animacion.shake(txtNombre);
            return;
        }

        if (user.isEmpty()) {
            txtUsuario.requestFocus();
            Animacion.shake(txtUsuario);
            return;
        }

        if (user.length() < 4) {
            txtUsuario.requestFocus();
            Animacion.shake(txtUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_INGRESAR_4_CARACTERES);
            return;
        }

        if (password.isEmpty()) {
            txtContraseña.requestFocus();
            Animacion.shake(txtContraseña);
            return;
        }

        if (password.length() < 4) {
            txtContraseña.requestFocus();
            Animacion.shake(txtContraseña);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_INGRESAR_4_CARACTERES);
            return;
        }

        if (confirmPassword.isEmpty()) {
            txtConfirmContraseña.requestFocus();
            Animacion.shake(txtConfirmContraseña);
            return;
        }

        if (!confirmPassword.equals(password)) {
            txtConfirmContraseña.requestFocus();
            Animacion.shake(txtContraseña);
            Animacion.shake(txtConfirmContraseña);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_CONTRA_NO_COINCIDEN);
            return;
        }

        if (!user.equals(usuar) || user.isEmpty()) {
            if (BDayuda.verificarExistencia("usuarios","usuario", user) > 0) {
                txtUsuario.requestFocus();
                Animacion.shake(txtUsuario);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_USUARIO_EXISTE_USUARIO);
                return;
            }
        }
        Integer id = BDayuda.ultimoIngreso();
        Integer idU = BDayuda.idUsuario(id);
        Usuario usuario = new Usuario(user, password, idU, name);
        
        boolean result = BDayuda.actualizarUsuarioConfiguracion(usuario);
        if (result) {
            loadData();
            ConstructorAlerta.create(TipoAlerta.SUCCES, stckConfiguracion, rootConfiguracion, rootConfiguracion, constantes.MENSAJE_AGREGADO);
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
    }

    private void animationNodes() {
        Animacion.fadeInUp(txtNombre);
        Animacion.fadeInUp(txtUsuario);
        Animacion.fadeInUp(txtContraseña);
        Animacion.fadeInUp(btnGuardar);
        Animacion.fadeInUp(txtConfirmContraseña);
        Animacion.fadeInUp(headerContainer);
    }
}
