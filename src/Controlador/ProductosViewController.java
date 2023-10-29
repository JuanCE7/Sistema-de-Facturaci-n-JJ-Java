package Controlador;

import Alertas.ConstructorAlerta;
import Alertas.TipoAlerta;
import Animaciones.Animacion;
import BD.BDayuda;
import Constantes.constantes;
import Modelo.Producto;
import Notificaciones.ConstructorNotificacion;
import Notificaciones.TipoNotificacion;
import com.jfoenix.controls.JFXButton;
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
import javafx.stage.Stage;

public class ProductosViewController implements Initializable {

    private ObservableList<Producto> listaProductos;

    private jfxDialogTools dialogoAgregarProducto;

    private jfxDialogTools dialogoBorrarProducto;

    private static final Stage stage = new Stage();

    private contextoMenu contextoMenu;
    @FXML
    private StackPane stckProductos;
    @FXML
    private AnchorPane nodoProductos;
    @FXML
    private TextField txtBuscarIdCode;
    @FXML
    private TextField txtBuscarProducto;
    @FXML
    private JFXButton btnNuevoProducto;
    @FXML
    private TableView<Producto> tblProductos;
    @FXML
    private TableColumn columnaIdCodigo;
    @FXML
    private TableColumn columnaProducto;
    @FXML
    private TableColumn columnaMarcaProducto;
    @FXML
    private TableColumn columnaCantidadProducto;
    @FXML
    private TableColumn columnaPrecioProducto;
    @FXML
    private AnchorPane contenedorAgregarProducto;

    @FXML
    private JFXTextField txtCodigoBarras;
    @FXML
    private JFXTextField txtMarcaProducto;
    @FXML
    private JFXTextField txtCantidadProducto;
    @FXML
    private JFXTextField txtPrecioProducto;
    @FXML
    private JFXTextField txtNombreProducto;
    @FXML
    private AnchorPane contenedorEliminarProducto;
    @FXML
    private JFXButton btnActualizarProducto;
    @FXML
    private JFXButton btnGuardarProducto;
    @FXML
    private JFXButton btnCancelarAgregarProducto;
    @FXML
    private Text txtAgregarProducto;
    @FXML
    private HBox hBoxBuscarProducto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        nodosAnimados();
        validacionTipoUsuario();
        cerrarDialogoConEscape();
        cerrarDialogoConLosCamposTexto();
    }

    private void nodosAnimados() {
        Animacion.fadeInUp(btnNuevoProducto);
        Animacion.fadeInUp(tblProductos);
        Animacion.fadeInUp(hBoxBuscarProducto);
    }

    private Stage getStage() {
        return (Stage) btnCancelarAgregarProducto.getScene().getWindow();
    }

    private void restablecerValidacion() {
        txtCodigoBarras.resetValidation();
        txtMarcaProducto.resetValidation();
        txtCantidadProducto.resetValidation();
        txtNombreProducto.resetValidation();
        txtPrecioProducto.resetValidation();
    }

    private void habilitarEditarControles() {
        txtCodigoBarras.setEditable(true);
        txtMarcaProducto.setEditable(true);
        txtCantidadProducto.setEditable(true);
        txtNombreProducto.setEditable(true);
        txtPrecioProducto.setEditable(true);
    }

    private void deshabilitarEditarControles() {
        txtCodigoBarras.setEditable(false);
        txtMarcaProducto.setEditable(false);
        txtCantidadProducto.setEditable(false);
        txtNombreProducto.setEditable(false);
        txtPrecioProducto.setEditable(false);
    }

    private void deshabilitarTabla() {
        tblProductos.setDisable(true);
    }

    public static void closeStage() {
        if (stage != null) {
            stage.hide();
        }
    }

    private void limpiarControles() {
        txtNombreProducto.clear();
        txtCodigoBarras.clear();
        txtCantidadProducto.clear();
        txtMarcaProducto.clear();
        txtPrecioProducto.clear();
    }

    private void cerrarDialogoConEscape() {
        nodoProductos.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarProducto();
            }

            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                esconderDialogoEliminarProducto();
            }

            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                tblProductos.setDisable(false);
                nodoProductos.setEffect(null);
                ConstructorAlerta.close();
            }

        });
    }

    private void cerrarDialogoConLosCamposTexto() {
        txtCodigoBarras.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarProducto();
            }
        });

        txtNombreProducto.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarProducto();
            }
        });

        txtMarcaProducto.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarProducto();
            }
        });

        txtPrecioProducto.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarProducto();
            }
        });

        txtCantidadProducto.setOnKeyReleased(ev -> {
            if (ev.getCode().equals(KeyCode.ESCAPE)) {
                cerrarDialogoAgregarProducto();
            }
        });
    }

    private void setContextoMenu() {
        contextoMenu = new contextoMenu(tblProductos);

        contextoMenu.setAccionEditar(ev -> {
            mostrarDialogoEditarProducto();
            contextoMenu.ocultar();
        });

        contextoMenu.setAccionEliminar(ev -> {
            mostrarDialogoEliminarProducto();
            contextoMenu.ocultar();
        });

        contextoMenu.setAccionRefrescar(ev -> {
            cargarDatos();
            contextoMenu.ocultar();
        });

        contextoMenu.mostrar();
    }

    private void activarElementoMenu() {
        contextoMenu.getEditarBoton().setDisable(false);
        contextoMenu.getEliminarBoton().setDisable(false);
    }

    private void validacionTipoUsuario() {
        setContextoMenu();
        eliminarUsuarioYClave();
        columnaCantidadProducto.setVisible(true);
        btnNuevoProducto.setDisable(false);
        txtNombreProducto.setVisible(true);
        txtMarcaProducto.setVisible(true);
        txtCantidadProducto.setVisible(true);
        activarElementoMenu();
    }

    @FXML
    private void filtroIdCodigo() {
        listaProductos.clear();
        listaProductos.setAll(BDayuda.buscarProducto("codigo_Barra", txtBuscarIdCode.getText()));
    }

    @FXML
    private void filtroNombreProducto() {
        listaProductos.clear();
        listaProductos.setAll(BDayuda.buscarProducto("nombre_producto", txtBuscarProducto.getText()));
    }

    @FXML
    private void mostrarDialogoAgregarProducto() {
        restablecerValidacion();
        habilitarEditarControles();
        deshabilitarTabla();
        nodoProductos.setEffect(constantes.EFECTO_BOX_BLUR);
        txtCodigoBarras.setDisable(false);
        txtAgregarProducto.setText("Agregar Producto");
        contenedorAgregarProducto.setVisible(true);
        btnGuardarProducto.setDisable(false);
        btnActualizarProducto.setVisible(true);
        btnGuardarProducto.toFront();

        dialogoAgregarProducto = new jfxDialogTools(contenedorAgregarProducto, stckProductos);
        dialogoAgregarProducto.show();

        dialogoAgregarProducto.setOnDialogOpened(ev -> {
            txtCodigoBarras.requestFocus();
        });

        dialogoAgregarProducto.setOnDialogClosed(ev -> {
            closeStage();
            tblProductos.setDisable(false);
            nodoProductos.setEffect(null);
            contenedorAgregarProducto.setVisible(false);
            limpiarControles();
        });
    }

    @FXML
    private void cerrarDialogoAgregarProducto() {
        if (dialogoAgregarProducto != null) {
            dialogoAgregarProducto.close();
        }
    }

    private void mostrarDialogoEliminarProducto() {
        if (tblProductos.getSelectionModel().getSelectedItems().isEmpty()) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckProductos, nodoProductos, tblProductos, constantes.MENSAJE_NO_SELECCIONADO);
            return;
        }

        nodoProductos.setEffect(constantes.EFECTO_BOX_BLUR);
        contenedorEliminarProducto.setVisible(true);
        deshabilitarTabla();

        dialogoBorrarProducto = new jfxDialogTools(contenedorEliminarProducto, stckProductos);
        dialogoBorrarProducto.show();

        dialogoBorrarProducto.setOnDialogClosed(ev -> {
            tblProductos.setDisable(false);
            nodoProductos.setEffect(null);
            contenedorEliminarProducto.setVisible(false);
            limpiarControles();
        });

    }

    @FXML
    private void esconderDialogoEliminarProducto() {
        if (dialogoBorrarProducto != null) {
            dialogoBorrarProducto.close();
        }
    }

    @FXML
    private void eliminarProductos() {
        boolean result = BDayuda.borrarProducto(tblProductos, listaProductos);
        if (result) {
            cargarDatos();
            limpiarControles();
            esconderDialogoEliminarProducto();
            ConstructorAlerta.create(TipoAlerta.SUCCES, stckProductos, nodoProductos, tblProductos, constantes.MENSAJE_BORRADO);
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }

    }

    private void eliminarUsuarioYClave() {
        nodoProductos.setOnKeyPressed(ev -> {
            if (ev.getCode().equals(KeyCode.DELETE)) {
                if (tblProductos.isDisable()) {
                    return;
                }

                if (tblProductos.getSelectionModel().getSelectedItems().isEmpty()) {
                    ConstructorAlerta.create(TipoAlerta.ERROR, stckProductos, nodoProductos, tblProductos, constantes.MENSAJE_NO_SELECCIONADO);
                    return;
                }

                eliminarProductos();
            }
        });
    }

    private void mostrarDialogoEditarProducto() {
        if (tblProductos.getSelectionModel().getSelectedItems().isEmpty()) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckProductos, nodoProductos, tblProductos, constantes.MENSAJE_NO_SELECCIONADO);
            return;
        }
        mostrarDialogoAgregarProducto();
        txtCodigoBarras.setDisable(true);
        btnActualizarProducto.toFront();
        txtAgregarProducto.setText("A C T U A L I Z A R");
        registroSeleccionado();
    }

    private void cargarDatos() {
        cargarTabla();
        columnaIdCodigo.setCellValueFactory(new PropertyValueFactory("codigoBarra"));
        columnaProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
        columnaMarcaProducto.setCellValueFactory(new PropertyValueFactory("marca"));
        columnaCantidadProducto.setCellValueFactory(new PropertyValueFactory("cantidad"));
        columnaPrecioProducto.setCellValueFactory(new PropertyValueFactory("precio"));
    }

    private void cargarTabla() {
        ArrayList<Producto> lista = new ArrayList<>();
        try {
            String sql = "SELECT codigo_Barra, nombre_producto, marca_producto, cantidad_producto, precio_producto FROM productos";
            PreparedStatement preparedStatement = BD.BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int codigoBarras = resultSet.getInt("codigo_Barra");
                String nombreProducto = resultSet.getString("nombre_producto");
                String marcaProducto = resultSet.getString("marca_producto");
                int cantidadProducto = resultSet.getInt("cantidad_producto");
                Double precioProducto = resultSet.getDouble("precio_producto");
                lista.add(new Producto(codigoBarras, nombreProducto, marcaProducto, cantidadProducto, precioProducto));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InicioViewController.class.getName()).log(Level.SEVERE, null, ex);
            ConstructorAlerta.create(TipoAlerta.ERROR, stckProductos, nodoProductos, tblProductos, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
        listaProductos = FXCollections.observableArrayList(lista);
        tblProductos.setItems(listaProductos);
        tblProductos.setFixedCellSize(25);
    }

    @FXML
    private void actualizarProducto() {
        String nombreProducto = txtNombreProducto.getText().trim();
        String cantidadProducto = txtCantidadProducto.getText().trim();
        String marcaProducto = txtMarcaProducto.getText().trim();
        String precioProducto = txtPrecioProducto.getText().trim();

        if (nombreProducto.isEmpty() && cantidadProducto.isEmpty() && marcaProducto.isEmpty() && precioProducto.isEmpty()) {
            Animacion.shake(txtNombreProducto);
            Animacion.shake(txtCantidadProducto);
            Animacion.shake(txtMarcaProducto);
            Animacion.shake(txtPrecioProducto);
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_DATOS_INSUFICIENTES);
            return;
        }

        if (nombreProducto.isEmpty()) {
            txtNombreProducto.requestFocus();
            Animacion.shake(txtNombreProducto);
            return;
        }

        if (cantidadProducto.isEmpty()) {
            txtCantidadProducto.requestFocus();
            Animacion.shake(txtCantidadProducto);
            return;
        }

        if (marcaProducto.isEmpty()) {
            txtMarcaProducto.requestFocus();
            Animacion.shake(txtMarcaProducto);
            return;
        }

        if (precioProducto.isEmpty()) {
            txtPrecioProducto.requestFocus();
            Animacion.shake(txtPrecioProducto);
            return;
        }

        Producto producto = tblProductos.getSelectionModel().getSelectedItem();
        producto.setNombre(nombreProducto);
        producto.setMarca(marcaProducto);
        producto.setCantidad(Integer.parseInt(cantidadProducto));
        producto.setPrecio(Double.parseDouble(precioProducto));

        boolean result = BDayuda.actualizarProducto(producto);
        if (result) {
            cerrarDialogoAgregarProducto();
            cargarDatos();
            limpiarControles();
            ConstructorAlerta.create(TipoAlerta.SUCCES, stckProductos, nodoProductos, tblProductos, constantes.MENSAJE_ACTUALIZADO);
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
    }

    @FXML
    private void nuevoProducto() {
        String codigoBarras = txtCodigoBarras.getText().trim();
        String nombreProducto = txtNombreProducto.getText().trim();
        String cantidadProducto = txtCantidadProducto.getText().trim();
        String marcaProducto = txtMarcaProducto.getText().trim();
        String precioProducto = txtPrecioProducto.getText().trim();

        if (codigoBarras.isEmpty()) {
            txtCodigoBarras.requestFocus();
            Animacion.shake(txtCodigoBarras);
            return;
        }

        if (!codigoBarras.isEmpty()) {
            if (BDayuda.comprobarSiElProductoExiste(codigoBarras) != 0) {
                txtCodigoBarras.requestFocus();
                ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.PRODUCTO_EXISTE);
                return;
            }
        }

        if (nombreProducto.isEmpty()) {
            txtNombreProducto.requestFocus();
            Animacion.shake(txtNombreProducto);
            return;
        }

        if (cantidadProducto.isEmpty()) {
            txtCantidadProducto.requestFocus();
            Animacion.shake(txtCantidadProducto);
            return;
        }

        if (marcaProducto.isEmpty()) {
            txtMarcaProducto.requestFocus();
            Animacion.shake(txtMarcaProducto);
            return;
        }

        if (precioProducto.isEmpty()) {
            txtPrecioProducto.requestFocus();
            Animacion.shake(txtPrecioProducto);
            return;
        }

        Producto producto = new Producto();
        producto.setCodigoBarra(Integer.parseInt(codigoBarras));
        producto.setNombre(nombreProducto);
        producto.setMarca(marcaProducto);
        producto.setCantidad(Integer.parseInt(cantidadProducto));
        producto.setPrecio(Double.parseDouble(precioProducto));

        boolean resultado = BDayuda.insertNuevoProducto(producto, listaProductos);
        if (resultado) {
            cargarDatos();
            limpiarControles();
            cerrarDialogoAgregarProducto();
            ConstructorAlerta.create(TipoAlerta.SUCCES, stckProductos, nodoProductos, tblProductos, constantes.MENSAJE_AGREGADO);
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }

    }

    private void registroSeleccionado() {
        Producto producto = tblProductos.getSelectionModel().getSelectedItem();
        txtCodigoBarras.setText(String.valueOf(producto.getCodigoBarra()));
        txtNombreProducto.setText(producto.getNombre());
        txtCantidadProducto.setText(String.valueOf(producto.getCantidad()));
        txtMarcaProducto.setText(String.valueOf(producto.getMarca()));
        txtPrecioProducto.setText(String.valueOf(producto.getPrecio()));
    }

    @FXML
    public void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtCodigoBarras)) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) || txtCodigoBarras.getText().length() > 5) {
                event.consume();
            }
        } else if (evt.equals(txtNombreProducto)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !Character.isDigit(event.getCharacter().charAt(0)) && !event.getCharacter().equals(" ") || txtNombreProducto.getText().length() > 25) {
                event.consume();
            }
        } else if (evt.equals(txtCantidadProducto)) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) || txtCantidadProducto.getText().length() > 5) {
                event.consume();
            }
        } else if (evt.equals(txtMarcaProducto)) {
            if (!Character.isLetter(event.getCharacter().charAt(0)) && !Character.isDigit(event.getCharacter().charAt(0)) && !event.getCharacter().equals(" ") || txtMarcaProducto.getText().length() > 25) {
                event.consume();
            }
        } else if (evt.equals(txtPrecioProducto)) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) && !event.getCharacter().equals(".") || txtPrecioProducto.getText().length() > 5) {
                event.consume();
            }
        }
    }

}
