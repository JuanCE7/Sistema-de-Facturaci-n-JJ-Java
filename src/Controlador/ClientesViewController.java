package Controlador;

import Alertas.ConstructorAlerta;
import Alertas.TipoAlerta;
import Animaciones.Animacion;
import BD.BDayuda;
import Constantes.constantes;
import static Controlador.ProductosViewController.closeStage;
import Expresiones.ControladorExcepciones;
import Herramientas.contextoMenu;
import Modelo.Cliente;
import Notificaciones.ConstructorNotificacion;
import Notificaciones.TipoNotificacion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dialogTools.jfxDialogTools;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientesViewController implements Initializable {

    private ObservableList<Cliente> listaClientes;

    private jfxDialogTools dialogoAgregarCliente;

    private jfxDialogTools dialogoBorrarCliente;

    private String cedulaAux, correoAux;
    private static final Stage stage = new Stage();

    private contextoMenu contextoMenu;

    @FXML
    private StackPane stckClientes;

    @FXML
    private AnchorPane nodoClientes;

    @FXML
    private HBox hBoxBuscarCliente;

    @FXML
    private TextField txtBuscarId;

    @FXML
    private TextField txtBuscarNombre;

    @FXML
    private JFXButton btnAgregarCliente;

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TableColumn<Cliente, Integer> colId;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colCedula;

    @FXML
    private TableColumn<Cliente, String> colTelefono;

    @FXML
    private TableColumn<Cliente, String> colCorreo;

    @FXML
    private TableColumn<Cliente, String> colDireccion;

    @FXML
    private TableColumn<Cliente, String> colTipoCliente;

    @FXML
    private AnchorPane contenedorAgregarCliente;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtCorreo;

    @FXML
    private JFXTextField txtDireccion;

    @FXML
    private JFXButton btnGuardarCliente;

    @FXML
    private JFXButton btnActualizarCliente;

    @FXML
    private JFXTextField txtTelefono;

    @FXML
    private JFXButton btnCancelarAgregarCliente;

    @FXML
    private Text textoAgregarCliente;

    @FXML
    private JFXTextField txtCedula;

    @FXML
    private JFXComboBox<String> comboTipo;

    @FXML
    private AnchorPane contenedorEliminarCliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        nodosAnimados();
        validacionTipoUsuario();
        cerrarDialogoConEscape();
        cerrarDialogoConLosCamposTexto();
        inicializarComboBox();
    }

    private void inicializarComboBox() {
        comboTipo.getItems().addAll("Normal", "Afiliado");
        comboTipo.focusedProperty().addListener((o, oldV, newV) -> {
            if (!oldV) {
                comboTipo.show();
            } else {
                comboTipo.hide();
            }
        });
    }

    @FXML
    private void filtroId() {
        listaClientes.clear();
        listaClientes.setAll(BDayuda.buscarCliente("id_cliente", txtBuscarId.getText()));
    }

    @FXML
    private void filtroNombre() {
        listaClientes.clear();
        listaClientes.setAll(BDayuda.buscarCliente("nombre_cliente", txtBuscarNombre.getText()));
    }

    private void cerrarDialogoConLosCamposTexto() {
        txtNombre.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarCliente();
            }
        });

        txtCedula.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarCliente();
            }
        });

        txtTelefono.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarCliente();
            }
        });

        txtCorreo.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarCliente();
            }
        });

        txtDireccion.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarCliente();
            }
        });
    }

    private void cerrarDialogoConEscape() {
        nodoClientes.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarCliente();
            }

            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                esconderDialogoEliminarCliente();
            }

            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                tblClientes.setDisable(false);
                nodoClientes.setEffect(null);
                ConstructorAlerta.close();
            }

        });
    }

    @FXML
    private void cerrarDialogoAgregarCliente() {
        if (dialogoAgregarCliente != null) {
            dialogoAgregarCliente.close();
        }
    }

    private void validacionTipoUsuario() {
        setContextoMenu();
        eliminarUsuarioYClave();
        btnAgregarCliente.setDisable(false);
        txtNombre.setVisible(true);
        txtCedula.setVisible(true);
        txtTelefono.setVisible(true);
        txtCorreo.setVisible(true);
        txtDireccion.setVisible(true);
        comboTipo.setVisible(true);
        activarElementoMenu();
    }

    private void activarElementoMenu() {
        contextoMenu.getEditarBoton().setDisable(false);
        contextoMenu.getEliminarBoton().setDisable(false);
    }

    private void eliminarUsuarioYClave() {
        nodoClientes.setOnKeyPressed(ev -> {
            if (ev.getCode().equals(KeyCode.DELETE)) {
                if (tblClientes.isDisable()) {
                    return;
                }

                if (tblClientes.getSelectionModel().getSelectedItems().isEmpty()) {
                    ConstructorAlerta.create(TipoAlerta.ERROR, stckClientes, nodoClientes, tblClientes, constantes.MENSAJE_NO_SELECCIONADO);
                    return;
                }

                eliminarCliente();
            }
        });
    }

    @FXML
    private void eliminarCliente() {
        boolean result = BDayuda.borrarCliente(tblClientes, listaClientes);
        if (result) {
            cargarDatos();
            limpiarControles();
            esconderDialogoEliminarCliente();
            ConstructorAlerta.create(TipoAlerta.SUCCES, stckClientes, nodoClientes, tblClientes, constantes.MENSAJE_BORRADO);
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
    }

    @FXML
    private void esconderDialogoEliminarCliente() {
        if (dialogoBorrarCliente != null) {
            dialogoBorrarCliente.close();
        }
    }

    private void setContextoMenu() {
        contextoMenu = new contextoMenu(tblClientes);

        contextoMenu.setAccionEditar(ev -> {
            mostrarDialogoEditarCliente();
            contextoMenu.ocultar();
        });

        contextoMenu.setAccionEliminar(ev -> {
            mostrarDialogoEliminarCliente();
            contextoMenu.ocultar();
        });

        contextoMenu.setAccionRefrescar(ev -> {
            cargarDatos();
            contextoMenu.ocultar();
        });

        contextoMenu.mostrar();
    }

    private void mostrarDialogoEliminarCliente() {
        if (tblClientes.getSelectionModel().getSelectedItems().isEmpty()) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckClientes, nodoClientes, tblClientes, constantes.MENSAJE_NO_SELECCIONADO);
            return;
        }

        nodoClientes.setEffect(constantes.EFECTO_BOX_BLUR);
        contenedorEliminarCliente.setVisible(true);
        deshabilitarTabla();

        dialogoBorrarCliente = new jfxDialogTools(contenedorEliminarCliente, stckClientes);
        dialogoBorrarCliente.show();

        dialogoBorrarCliente.setOnDialogClosed(ev -> {
            tblClientes.setDisable(false);
            nodoClientes.setEffect(null);
            contenedorEliminarCliente.setVisible(false);
            limpiarControles();
        });

    }

    private void mostrarDialogoEditarCliente() {
        if (tblClientes.getSelectionModel().getSelectedItems().isEmpty()) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckClientes, nodoClientes, tblClientes, constantes.MENSAJE_NO_SELECCIONADO);
            return;
        }
        mostrarDialogoAgregarCliente();
        btnActualizarCliente.toFront();
        textoAgregarCliente.setText("A C T U A L I Z A R");
        registroSeleccionado();
    }

    private void registroSeleccionado() {
        Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();
        txtNombre.setText(String.valueOf(cliente.getNombre()));
        txtCedula.setText(String.valueOf(cliente.getCedula()));
        txtTelefono.setText(String.valueOf(cliente.getTelefono()));
        txtCorreo.setText(String.valueOf(cliente.getCorreo()));
        txtDireccion.setText(String.valueOf(cliente.getDireccion()));
        comboTipo.setValue(cliente.getTipoCliente());
        cedulaAux = txtCedula.getText();
        correoAux = txtCorreo.getText();
    }

    @FXML
    private void mostrarDialogoAgregarCliente() {
        restablecerValidacion();
        habilitarEditarControles();
        deshabilitarTabla();
        comboTipo.setValue("");
        nodoClientes.setEffect(constantes.EFECTO_BOX_BLUR);
        textoAgregarCliente.setText("Agregar Cliente");
        contenedorAgregarCliente.setVisible(true);
        btnGuardarCliente.setDisable(false);
        btnActualizarCliente.setVisible(true);
        btnGuardarCliente.toFront();

        dialogoAgregarCliente = new jfxDialogTools(contenedorAgregarCliente, stckClientes);
        dialogoAgregarCliente.show();

        dialogoAgregarCliente.setOnDialogOpened(ev -> {
            txtNombre.requestFocus();
        });

        dialogoAgregarCliente.setOnDialogClosed(ev -> {
            closeStage();
            tblClientes.setDisable(false);
            nodoClientes.setEffect(null);
            contenedorAgregarCliente.setVisible(false);
            limpiarControles();
        });
    }

    private void limpiarControles() {
        txtNombre.clear();
        txtCedula.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtDireccion.clear();
    }

    private void deshabilitarTabla() {
        tblClientes.setDisable(true);
    }

    private void habilitarEditarControles() {
        txtNombre.setEditable(true);
        txtCedula.setEditable(true);
        txtTelefono.setEditable(true);
        txtCorreo.setEditable(true);
        txtDireccion.setEditable(true);
    }

    private void restablecerValidacion() {
        txtNombre.resetValidation();
        txtCedula.resetValidation();
        txtTelefono.resetValidation();
        txtCorreo.resetValidation();
        txtDireccion.resetValidation();
    }

    private void nodosAnimados() {
        Animacion.fadeInUp(btnAgregarCliente);
        Animacion.fadeInUp(tblClientes);
        Animacion.fadeInUp(hBoxBuscarCliente);
    }

    private void cargarDatos() {
        cargarTabla();
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCedula.setCellValueFactory(new PropertyValueFactory("cedula"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        colTipoCliente.setCellValueFactory(new PropertyValueFactory("tipoCliente"));
    }

    private void cargarTabla() {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            String sql = "SELECT id_cliente, nombre_cliente, cedula_cliente, telefono_cliente, correo_cliente, "
                    + "direccion_cliente, tipo_cliente FROM clientes";
            PreparedStatement preparedStatement = BD.BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_cliente");
                String nombre = resultSet.getString("nombre_cliente");
                String cedula = resultSet.getString("cedula_cliente");
                String telefono = resultSet.getString("telefono_cliente");
                String correo = resultSet.getString("correo_cliente");
                String direccion = resultSet.getString("direccion_cliente");
                String tipo = resultSet.getString("tipo_cliente");
                lista.add(new Cliente(tipo, id, nombre, cedula, telefono, correo, direccion));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InicioViewController.class.getName()).log(Level.SEVERE, null, ex);
            ConstructorAlerta.create(TipoAlerta.ERROR, stckClientes, nodoClientes, tblClientes, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
        listaClientes = FXCollections.observableArrayList(lista);
        tblClientes.setItems(listaClientes);
        tblClientes.setFixedCellSize(25);
    }

    @FXML
    private void actualizarCliente() {
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String tipo = comboTipo.getValue();

        if (nombre.isEmpty() && cedula.isEmpty() && telefono.isEmpty() && correo.isEmpty() && direccion.isEmpty() && tipo.isEmpty()) {
            Animacion.shake(txtNombre);
            Animacion.shake(txtCedula);
            Animacion.shake(txtTelefono);
            Animacion.shake(txtCorreo);
            Animacion.shake(txtDireccion);
            Animacion.shake(comboTipo);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_DATOS_INSUFICIENTES);
            return;
        }

        if (nombre.isEmpty()) {
            txtNombre.requestFocus();
            Animacion.shake(txtNombre);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_NOMBRE_VACIO);
            return;
        }

        if (cedula.isEmpty()) {
            txtCedula.requestFocus();
            Animacion.shake(txtCedula);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CEDULA_VACIO);
            return;
        }

        if (ControladorExcepciones.cedula(cedula) == false || ControladorExcepciones.Verificacion(cedula) == false) {
            txtCedula.requestFocus();
            Animacion.shake(txtCedula);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CEDULA_INVALIDO);
            return;
        }

        if (!cedula.equals(cedulaAux) && !cedula.isEmpty()) {
            if (BDayuda.verificarExistencia("clientes", "cedula_cliente", cedula) > 0) {
                txtCedula.requestFocus();
                Animacion.shake(txtCedula);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_CLIENTE_EXISTE_CEDULA);
                return;
            }
        }

        if (telefono.isEmpty()) {
            txtTelefono.requestFocus();
            Animacion.shake(txtTelefono);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_TEL_VACIO);
            return;
        }

        if (correo.isEmpty()) {
            txtCorreo.requestFocus();
            Animacion.shake(txtCorreo);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CORREO_VACIO);
            return;
        }

        if (ControladorExcepciones.correo(correo) == false) {
            txtCorreo.requestFocus();
            Animacion.shake(txtCorreo);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CORREO_INVALIDO);
            return;
        }

        if (!correo.equals(correoAux) && !correo.isEmpty()) {
            if (BDayuda.verificarExistencia("clientes", "correo_cliente", correo) > 0) {
                txtCorreo.requestFocus();
                Animacion.shake(txtCorreo);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_CLIENTE_EXISTE_CORREO);
                return;
            }
        }

        if (direccion.isEmpty()) {
            txtDireccion.requestFocus();
            Animacion.shake(txtDireccion);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_DIR_VACIO);
            return;
        }

        if (tipo.isEmpty()) {
            comboTipo.requestFocus();
            Animacion.shake(comboTipo);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_TIPCLIEN_VACIO);
            return;
        }

        Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();
        cliente.setNombre(nombre);
        cliente.setCedula(cedula);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
        cliente.setDireccion(direccion);
        cliente.setTipoCliente(tipo);

        boolean result = BDayuda.actualizarCliente(cliente);
        if (result) {
            cerrarDialogoAgregarCliente();
            cargarDatos();
            limpiarControles();
            ConstructorAlerta.create(TipoAlerta.SUCCES, stckClientes, nodoClientes, tblClientes, constantes.MENSAJE_ACTUALIZADO);
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
    }

    @FXML
    public void nuevoCliente(MouseEvent event) {
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String tipo = comboTipo.getValue();

        if (nombre.isEmpty() && cedula.isEmpty() && telefono.isEmpty() && correo.isEmpty() && direccion.isEmpty() && tipo.isEmpty()) {
            Animacion.shake(txtNombre);
            Animacion.shake(txtCedula);
            Animacion.shake(txtTelefono);
            Animacion.shake(txtCorreo);
            Animacion.shake(txtDireccion);
            Animacion.shake(comboTipo);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_DATOS_INSUFICIENTES);
            return;
        }

        if (nombre.isEmpty()) {
            txtNombre.requestFocus();
            Animacion.shake(txtNombre);
            return;
        }

        if (cedula.isEmpty()) {
            txtCedula.requestFocus();
            Animacion.shake(txtCedula);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CEDULA_VACIO);
            return;
        }

        if (telefono.isEmpty()) {
            txtTelefono.requestFocus();
            Animacion.shake(txtTelefono);
            return;
        }

        if (direccion.isEmpty()) {
            txtDireccion.requestFocus();
            Animacion.shake(txtDireccion);
            return;
        }

        if (correo.isEmpty()) {
            txtCorreo.requestFocus();
            Animacion.shake(txtCorreo);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CORREO_VACIO);
            return;
        }

        if (tipo.isEmpty()) {
            comboTipo.requestFocus();
            Animacion.shake(comboTipo);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_TIPCLIEN_VACIO);
            return;
        }

        if (ControladorExcepciones.cedula(cedula) == false || ControladorExcepciones.Verificacion(cedula) == false) {
            txtCedula.requestFocus();
            Animacion.shake(txtCedula);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CEDULA_INVALIDO);
            return;
        }

        if (!cedula.isEmpty()) {
            if (BDayuda.verificarExistencia("clientes", "cedula_cliente", cedula) > 0) {
                txtCedula.requestFocus();
                Animacion.shake(txtCedula);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_CLIENTE_EXISTE_CEDULA);
                return;
            }
        }

        if (ControladorExcepciones.correo(correo) == false) {
            txtCorreo.requestFocus();
            Animacion.shake(txtCorreo);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.C_CORREO_INVALIDO);
            return;
        }

        if (!correo.isEmpty()) {
            if (BDayuda.verificarExistencia("clientes", "correo_cliente", correo) > 0) {
                txtCorreo.requestFocus();
                Animacion.shake(txtCorreo);
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_CLIENTE_EXISTE_CORREO);
                return;
            }
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setCedula(cedula);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
        cliente.setDireccion(direccion);
        cliente.setTipoCliente(tipo);
        cliente.setCompras(0.0);

        boolean resultado = BDayuda.insertNuevoCliente(cliente, listaClientes);
        if (resultado) {
            cargarDatos();
            limpiarControles();
            cerrarDialogoAgregarCliente();
            ConstructorAlerta.create(TipoAlerta.SUCCES, stckClientes, nodoClientes, tblClientes, constantes.MENSAJE_AGREGADO);
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
    }

    @FXML
    public void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtDireccion)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !Character.isDigit(event.getCharacter().charAt(0)) && !event.getCharacter().equals(" ") || txtDireccion.getText().length() > 50) {
                event.consume();
            }
        } else if (evt.equals(txtNombre)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !Character.isDigit(event.getCharacter().charAt(0)) && !event.getCharacter().equals(" ") || txtNombre.getText().length() > 30) {
                event.consume();
            }
        } else if (evt.equals(txtTelefono)) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) || txtTelefono.getText().length() > 10) {
                event.consume();
            }
        } else if (evt.equals(txtCedula)) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) || txtCedula.getText().length() > 10) {
                event.consume();
            }
        }
    }
}
