package Controlador;

import Alertas.ConstructorAlerta;
import Alertas.TipoAlerta;
import Animaciones.Animacion;
import BD.BDayuda;
import Constantes.constantes;
import Expresiones.ControladorExcepciones;
import Modelo.Usuario;
import Notificaciones.ConstructorNotificacion;
import Notificaciones.TipoNotificacion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dialogTools.jfxDialogTools;
import Herramientas.contextoMenu;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class UsuariosViewController implements Initializable {

    @FXML
    private StackPane stckUsuarios;
    @FXML
    private AnchorPane nodoUsuarios;
    @FXML
    private HBox hBoxBuscarUsuarios;
    @FXML
    private TextField txtBuscarId;
    @FXML
    private TextField txtBuscarNombre;
    @FXML
    private JFXButton btnAgregarCliente;
    @FXML
    private TableView<Usuario> tblUsuarios;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCedula;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colTipoUsuario;
    @FXML
    private AnchorPane contenedorAgregarUsuario;
    @FXML
    private JFXTextField txtNombreUsuario;
    @FXML
    private JFXTextField txtCorreoUsuario;
    @FXML
    private JFXTextField txtDireccionUsuario;
    @FXML
    private JFXButton btnActualizarUsuario;
    @FXML
    private JFXTextField txtTelefonoUsuario;
    @FXML
    private JFXTextField txtCedulaUsuario;
    @FXML
    private JFXComboBox<String> CBoxTipoUser;
    @FXML
    private JFXTextField txtUser;
    @FXML
    private JFXTextField txtContraseña;
    @FXML
    private AnchorPane contenedorEliminarUsuario;
    @FXML
    private Text textoAgregarUsuario;
    @FXML
    private JFXButton btnGuardarUsuario;
    @FXML
    private TableColumn colUsuario;
    @FXML
    private TableColumn colContrasena;
    private String correoAux, cedulaAux, usuarioAux;
    private jfxDialogTools dialogoAgregarUsuario;
    private jfxDialogTools dialogoBorrarUsuario;
    private contextoMenu contextoMenu;
    private ObservableList<Usuario> listaUsuarios;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        nodosAnimados();
        setContextoMenu();
        cerrarDialogoConEscape();
        inicializarComboBox();
        cerrarDialogoConLosCamposTexto();
    }

    @FXML
    private void filtroId() {
        listaUsuarios.clear();
        listaUsuarios.setAll(BDayuda.buscarUsuario("id_usuario", txtBuscarId.getText()));
    }

    @FXML
    private void filtroNombre() {
        listaUsuarios.clear();
        listaUsuarios.setAll(BDayuda.buscarUsuario("nombre_usuario", txtBuscarNombre.getText()));
    }

    private void inicializarComboBox() {
        CBoxTipoUser.getItems().addAll("Administrador", "Empleado");
        CBoxTipoUser.focusedProperty().addListener((o, oldV, newV) -> {
            if (!oldV) {
                CBoxTipoUser.show();
            } else {
                CBoxTipoUser.hide();
            }
        });
    }

    private void cerrarDialogoConLosCamposTexto() {
        txtNombreUsuario.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarUsuario();
            }
        });

        txtCedulaUsuario.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarUsuario();
            }
        });

        txtTelefonoUsuario.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarUsuario();
            }
        });

        txtCorreoUsuario.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarUsuario();
            }
        });

        txtDireccionUsuario.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarUsuario();
            }
        });
    }

    private void cerrarDialogoConEscape() {
        nodoUsuarios.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarUsuario();
            }

            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                esconderDialogoEliminarUsuario();
            }

            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                tblUsuarios.setDisable(false);
                nodoUsuarios.setEffect(null);
                ConstructorAlerta.close();
            }

        });
    }

    @FXML
    private void mostrarDialogoAgregarUsuario() {
        restablecerValidacion();
        habilitarEditarControles();
        deshabilitarTabla();
        nodoUsuarios.setEffect(constantes.EFECTO_BOX_BLUR);
        textoAgregarUsuario.setText("Agregar Usuario");
        contenedorAgregarUsuario.setVisible(true);
        btnGuardarUsuario.setDisable(false);
        btnActualizarUsuario.setVisible(true);
        btnGuardarUsuario.toFront();

        dialogoAgregarUsuario = new jfxDialogTools(contenedorAgregarUsuario, stckUsuarios);
        dialogoAgregarUsuario.show();

        dialogoAgregarUsuario.setOnDialogOpened(ev -> {
            txtNombreUsuario.requestFocus();
        });

        dialogoAgregarUsuario.setOnDialogClosed(ev -> {
            tblUsuarios.setDisable(false);
            nodoUsuarios.setEffect(null);
            contenedorAgregarUsuario.setVisible(false);
            limpiarControles();
        });
    }

    @FXML
    private void nuevoUsuario() {
        String nombre = txtNombreUsuario.getText().trim();
        String cedula = txtCedulaUsuario.getText().trim();
        String telefono = txtTelefonoUsuario.getText().trim();
        String correo = txtCorreoUsuario.getText().trim();
        String direccion = txtDireccionUsuario.getText().trim();
        String user = txtUser.getText().trim();
        String password = txtContraseña.getText().trim();
        String tipoUsuario = CBoxTipoUser.getValue();

        if (nombre.isEmpty() && cedula.isEmpty() && telefono.isEmpty() && correo.isEmpty() && direccion.isEmpty() && user.isEmpty() && password.isEmpty()) {
            Animacion.shake(txtNombreUsuario);
            Animacion.shake(txtCedulaUsuario);
            Animacion.shake(txtTelefonoUsuario);
            Animacion.shake(txtCorreoUsuario);
            Animacion.shake(txtDireccionUsuario);
            Animacion.shake(txtUser);
            Animacion.shake(txtContraseña);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_DATOS_INSUFICIENTES);
            return;
        }

        if (tipoUsuario == null) {
            CBoxTipoUser.requestFocus();
            Animacion.shake(CBoxTipoUser);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_TIPUS_VACIO);
            return;
        }

        if (cedula.isEmpty() || ControladorExcepciones.cedula(cedula) == false || ControladorExcepciones.Verificacion(cedula) == false) {
            txtCedulaUsuario.requestFocus();
            Animacion.shake(txtCedulaUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, "Campo cédula vacio o no es válido");
            return;
        }

        if (!cedula.isEmpty()) {
            if (BDayuda.verificarExistencia("usuarios", "cedula_usuario", cedula) > 0) {
                txtCedulaUsuario.requestFocus();
                Animacion.shake(txtCedulaUsuario);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_USUARIO_EXISTE_CEDULA);
                return;
            }
        }

        if (correo.isEmpty() || ControladorExcepciones.correo(correo) == false) {
            txtCorreoUsuario.requestFocus();
            Animacion.shake(txtCorreoUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, "Campo correo vacio o no es válido");
            return;
        }

        if (!correo.isEmpty()) {
            if (BDayuda.verificarExistencia("usuarios", "correo_usuario", correo) > 0) {
                txtCorreoUsuario.requestFocus();
                Animacion.shake(txtCorreoUsuario);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_USUARIO_EXISTE_CORREO);
                return;
            }
        }

        if (nombre.isEmpty()) {
            txtNombreUsuario.requestFocus();
            Animacion.shake(txtNombreUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_NOMBRE_VACIO);
            return;
        }

        if (telefono.isEmpty()) {
            txtTelefonoUsuario.requestFocus();
            Animacion.shake(txtTelefonoUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_TEL_VACIO);
            return;
        }

        if (direccion.isEmpty()) {
            txtDireccionUsuario.requestFocus();
            Animacion.shake(txtDireccionUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_DIR_VACIO);
            return;
        }

        if (user.isEmpty()) {
            txtUser.requestFocus();
            Animacion.shake(txtUser);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_USUARIO_VACIO);
            return;
        }

        if (!user.isEmpty()) {
            if (BDayuda.verificarExistencia("usuarios", "usuario", user) > 0) {
                txtUser.requestFocus();
                Animacion.shake(txtUser);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_USUARIO_EXISTE_USUARIO);
                return;
            }
        }

        if (password.isEmpty()) {
            txtContraseña.requestFocus();
            Animacion.shake(txtContraseña);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CONTRA_VACIO);
            return;
        }

        if (user.length() < 4) {
            txtUser.requestFocus();
            Animacion.shake(txtUser);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_INGRESAR_4_CARACTERES);
            return;
        }

        if (password.length() < 4) {
            txtContraseña.requestFocus();
            Animacion.shake(txtContraseña);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_INGRESAR_4_CARACTERES);
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCedula(cedula);
        usuario.setTelefono(telefono);
        usuario.setCorreo(correo);
        usuario.setDireccion(direccion);
        usuario.setUsuario(user);
        usuario.setContraseña(password);
        usuario.setTipoUsuario(tipoUsuario);

        boolean resultado = BDayuda.insertNuevoUsuario(usuario, listaUsuarios);
        if (resultado) {
            cargarDatos();
            limpiarControles();
            cerrarDialogoAgregarUsuario();
            ConstructorAlerta.create(TipoAlerta.SUCCES, stckUsuarios, nodoUsuarios, tblUsuarios, constantes.MENSAJE_AGREGADO);
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }

    }

    @FXML
    private void actualizarUsuario() {
        String nombre = txtNombreUsuario.getText().trim();
        String cedula = txtCedulaUsuario.getText();
        String telefono = txtTelefonoUsuario.getText().trim();
        String correo = txtCorreoUsuario.getText();
        String direccion = txtDireccionUsuario.getText().trim();
        String user = txtUser.getText().trim();
        String password = txtContraseña.getText().trim();
        String tipoUsuario = CBoxTipoUser.getValue();

        if (nombre.isEmpty() && cedula.isEmpty() && telefono.isEmpty() && correo.isEmpty() && direccion.isEmpty() && user.isEmpty() && password.isEmpty() && tipoUsuario.isEmpty()) {
            Animacion.shake(txtUser);
            Animacion.shake(txtContraseña);
            Animacion.shake(txtNombreUsuario);
            Animacion.shake(txtCedulaUsuario);
            Animacion.shake(txtTelefonoUsuario);
            Animacion.shake(txtCorreoUsuario);
            Animacion.shake(txtDireccionUsuario);
            Animacion.shake(CBoxTipoUser);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_DATOS_INSUFICIENTES);
            return;
        }

        if (user.isEmpty()) {
            txtUser.requestFocus();
            Animacion.shake(txtUser);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_USUARIO_VACIO);
            return;
        }

        if (password.isEmpty()) {
            txtContraseña.requestFocus();
            Animacion.shake(txtContraseña);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CONTRA_VACIO);
            return;
        }

        if (nombre.isEmpty()) {
            txtNombreUsuario.requestFocus();
            Animacion.shake(txtNombreUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_NOMBRE_VACIO);
            return;
        }

        if (telefono.isEmpty()) {
            txtTelefonoUsuario.requestFocus();
            Animacion.shake(txtTelefonoUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_TEL_VACIO);
            return;
        }

        if (direccion.isEmpty()) {
            txtDireccionUsuario.requestFocus();
            Animacion.shake(txtDireccionUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_DIR_VACIO);
            return;
        }

        if (correo.isEmpty()) {
            txtCorreoUsuario.requestFocus();
            Animacion.shake(txtCorreoUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CORREO_VACIO);
            return;
        }

        if (tipoUsuario == null) {
            CBoxTipoUser.requestFocus();
            Animacion.shake(CBoxTipoUser);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_TIPUS_VACIO);
            return;
        }

        if (cedula.isEmpty()) {
            txtCedulaUsuario.requestFocus();
            Animacion.shake(txtCedulaUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CEDULA_VACIO);
            return;
        }

        if (ControladorExcepciones.cedula(cedula) == false || ControladorExcepciones.Verificacion(cedula) == false) {
            txtCedulaUsuario.requestFocus();
            Animacion.shake(txtCedulaUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CEDULA_INVALIDO);
            return;
        }

        if (!cedula.equals(cedulaAux) && !cedula.isEmpty()) {
            if (BDayuda.verificarExistencia("usuarios", "cedula_usuario", cedula) > 0) {
                txtCedulaUsuario.requestFocus();
                Animacion.shake(txtCedulaUsuario);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_USUARIO_EXISTE_CEDULA);
                return;
            }
        }

        if (ControladorExcepciones.correo(correo) == false) {
            txtCorreoUsuario.requestFocus();
            Animacion.shake(txtCorreoUsuario);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CORREO_INVALIDO);
            return;
        }

        if (!correo.equals(correoAux) && !correo.isEmpty()) {
            if (BDayuda.verificarExistencia("usuarios", "correo_usuario", correo) > 0) {
                txtCorreoUsuario.requestFocus();
                Animacion.shake(txtCorreoUsuario);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_USUARIO_EXISTE_CORREO);
                return;
            }
        }

        if (!user.equals(usuarioAux) && !user.isEmpty()) {
            if (BDayuda.verificarExistencia("usuarios", "usuario", user) > 0) {
                txtUser.requestFocus();
                Animacion.shake(txtUser);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_USUARIO_EXISTE_USUARIO);
                return;
            }
        }

        if (user.length() < 4) {
            txtUser.requestFocus();
            Animacion.shake(txtUser);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_INGRESAR_4_CARACTERES);
            return;
        }

        if (password.length() < 4) {
            txtContraseña.requestFocus();
            Animacion.shake(txtContraseña);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_INGRESAR_4_CARACTERES);
            return;
        }

        Usuario usuario = tblUsuarios.getSelectionModel().getSelectedItem();
        usuario.setNombre(nombre);
        usuario.setCedula(cedula);
        usuario.setTelefono(telefono);
        usuario.setCorreo(correo);
        usuario.setDireccion(direccion);
        usuario.setUsuario(user);
        usuario.setContraseña(password);
        usuario.setTipoUsuario(tipoUsuario);

        boolean result = BDayuda.actualizarUsuario(usuario);
        if (result) {
            cerrarDialogoAgregarUsuario();
            cargarDatos();
            limpiarControles();
            ConstructorAlerta.create(TipoAlerta.SUCCES, stckUsuarios, nodoUsuarios, tblUsuarios, constantes.MENSAJE_ACTUALIZADO);
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }

    }

    @FXML
    private void cerrarDialogoAgregarUsuario() {
        if (dialogoAgregarUsuario != null) {
            dialogoAgregarUsuario.close();
        }
    }

    @FXML
    private void eliminarUsuario() {
        boolean result = BDayuda.borrarUsuario(tblUsuarios, listaUsuarios);
        if (result) {
            cargarDatos();
            limpiarControles();
            esconderDialogoEliminarUsuario();
            ConstructorAlerta.create(TipoAlerta.SUCCES, stckUsuarios, nodoUsuarios, tblUsuarios, constantes.MENSAJE_BORRADO);
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
    }

    @FXML
    private void esconderDialogoEliminarUsuario() {
        if (dialogoBorrarUsuario != null) {
            dialogoBorrarUsuario.close();
        }
    }

    private void mostrarDialogoEditarUsuario() {
        if (tblUsuarios.getSelectionModel().getSelectedItems().isEmpty()) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckUsuarios, nodoUsuarios, tblUsuarios, constantes.MENSAJE_NO_SELECCIONADO);
            return;
        }
        mostrarDialogoAgregarUsuario();
        registroSeleccionado();
        btnActualizarUsuario.toFront();
        textoAgregarUsuario.setText("A C T U A L I Z A R");
    }

    private void mostrarDialogoEliminarUsuario() {
        if (tblUsuarios.getSelectionModel().getSelectedItems().isEmpty()) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckUsuarios, nodoUsuarios, tblUsuarios, constantes.MENSAJE_NO_SELECCIONADO);
            return;
        }

        nodoUsuarios.setEffect(constantes.EFECTO_BOX_BLUR);
        contenedorEliminarUsuario.setVisible(true);
        deshabilitarTabla();

        dialogoBorrarUsuario = new jfxDialogTools(contenedorEliminarUsuario, stckUsuarios);
        dialogoBorrarUsuario.show();

        dialogoBorrarUsuario.setOnDialogClosed(ev -> {
            tblUsuarios.setDisable(false);
            nodoUsuarios.setEffect(null);
            contenedorEliminarUsuario.setVisible(false);
            limpiarControles();
        });

    }

    private void registroSeleccionado() {
        Usuario usuario = tblUsuarios.getSelectionModel().getSelectedItem();
        txtNombreUsuario.setText(String.valueOf(usuario.getNombre()));
        txtCedulaUsuario.setText(String.valueOf(usuario.getCedula()));
        txtTelefonoUsuario.setText(String.valueOf(usuario.getTelefono()));
        txtCorreoUsuario.setText(String.valueOf(usuario.getCorreo()));
        txtDireccionUsuario.setText(String.valueOf(usuario.getDireccion()));
        txtUser.setText(String.valueOf(usuario.getUsuario()));
        txtContraseña.setText(String.valueOf(usuario.getContraseña()));
        CBoxTipoUser.setValue(usuario.getTipoUsuario());
        correoAux = txtCorreoUsuario.getText();
        cedulaAux = txtCedulaUsuario.getText();
        usuarioAux = txtUser.getText();
    }

    private void limpiarControles() {
        txtNombreUsuario.clear();
        txtCedulaUsuario.clear();
        txtTelefonoUsuario.clear();
        txtCorreoUsuario.clear();
        txtDireccionUsuario.clear();
        txtUser.clear();
        txtContraseña.clear();
    }

    private void deshabilitarTabla() {
        tblUsuarios.setDisable(true);
    }

    private void habilitarEditarControles() {
        txtNombreUsuario.setEditable(true);
        txtCedulaUsuario.setEditable(true);
        txtTelefonoUsuario.setEditable(true);
        txtCorreoUsuario.setEditable(true);
        txtDireccionUsuario.setEditable(true);
        txtUser.setEditable(true);
        txtContraseña.setEditable(true);
    }

    private void restablecerValidacion() {
        txtNombreUsuario.resetValidation();
        txtCedulaUsuario.resetValidation();
        txtTelefonoUsuario.resetValidation();
        txtCorreoUsuario.resetValidation();
        txtDireccionUsuario.resetValidation();
        txtUser.resetValidation();
        txtContraseña.resetValidation();
    }

    private void nodosAnimados() {
        Animacion.fadeInUp(btnAgregarCliente);
        Animacion.fadeInUp(tblUsuarios);
        Animacion.fadeInUp(hBoxBuscarUsuarios);
    }

    private void cargarDatos() {
        cargarTabla();
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colUsuario.setCellValueFactory(new PropertyValueFactory("usuario"));
        colContrasena.setCellValueFactory(new PropertyValueFactory("contraseña"));
        colCedula.setCellValueFactory(new PropertyValueFactory("cedula"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        colTipoUsuario.setCellValueFactory(new PropertyValueFactory("tipoUsuario"));
    }

    private void cargarTabla() {
        ArrayList<Usuario> lista = new ArrayList<>();
        try {
            String sql = "SELECT id_usuario, nombre_usuario, cedula_usuario, telefono_usuario, correo_usuario, "
                    + "direccion_usuario, usuario, contraseña_usuario, tipoUsuario FROM usuarios";
            PreparedStatement preparedStatement = BD.BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idUsuario = resultSet.getInt("id_usuario");
                String nombreUsuario = resultSet.getString("nombre_usuario");
                String cedulaUsuario = resultSet.getString("cedula_usuario");
                String telefonoUsuario = resultSet.getString("telefono_usuario");
                String correoUsuario = resultSet.getString("correo_usuario");
                String direccionUsuario = resultSet.getString("direccion_usuario");
                String user = resultSet.getString("usuario");
                String password = resultSet.getString("contraseña_usuario");
                String tipoUsuario = resultSet.getString("tipoUsuario");
                lista.add(new Usuario(user, password, tipoUsuario, idUsuario, nombreUsuario, cedulaUsuario, telefonoUsuario, correoUsuario, direccionUsuario));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InicioViewController.class.getName()).log(Level.SEVERE, null, ex);
            ConstructorAlerta.create(TipoAlerta.ERROR, stckUsuarios, nodoUsuarios, tblUsuarios, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
        listaUsuarios = FXCollections.observableArrayList(lista);
        tblUsuarios.setItems(listaUsuarios);
        tblUsuarios.setFixedCellSize(25);
    }

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtDireccionUsuario)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !Character.isDigit(event.getCharacter().charAt(0)) && !event.getCharacter().equals(" ") || txtDireccionUsuario.getText().length() > 49) {
                event.consume();
            }
        } else if (evt.equals(txtNombreUsuario)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !Character.isDigit(event.getCharacter().charAt(0)) && !event.getCharacter().equals(" ") || txtNombreUsuario.getText().length() > 29) {
                event.consume();
            }
        } else if (evt.equals(txtTelefonoUsuario)) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) || txtTelefonoUsuario.getText().length() > 9) {
                event.consume();
            }
        } else if (evt.equals(txtCedulaUsuario)) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) || txtCedulaUsuario.getText().length() > 9) {
                event.consume();
            }
        }
    }

    private void setContextoMenu() {
        contextoMenu = new contextoMenu(tblUsuarios);

        contextoMenu.setAccionEditar(ev -> {
            mostrarDialogoEditarUsuario();
            contextoMenu.ocultar();
        });

        contextoMenu.setAccionEliminar(ev -> {
            mostrarDialogoEliminarUsuario();
            contextoMenu.ocultar();
        });

        contextoMenu.setAccionRefrescar(ev -> {
            cargarDatos();
            contextoMenu.ocultar();
        });
        contextoMenu.mostrar();
    }
}
