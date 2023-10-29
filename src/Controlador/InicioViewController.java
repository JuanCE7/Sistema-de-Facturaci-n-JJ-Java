package Controlador;

import Alertas.ConstructorAlerta;
import Alertas.TipoAlerta;
import Animaciones.Animacion;
import BD.BDayuda;
import BD.BDconexion;
import Constantes.constantes;
import Factura.Factura;
import Modelo.Cliente;
import dialogTools.jfxDialogTools;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InicioViewController implements Initializable {

    private jfxDialogTools dialogoFacturas;
    @FXML
    private TableColumn columnaIdCliente;
    @FXML
    private TableColumn columUsuarioCliente;
    @FXML
    private TableColumn columnaCompraCliente;
    @FXML
    private TextField txtBuscarClienteReciente;
    @FXML
    private Label lblTotalClientes;
    @FXML
    private Label lblTotalCaja;
    @FXML
    private Label txtBienvenido;
    @FXML
    private Label txtMensajeBienvenida;

    private ObservableList<Cliente> listaClientes;
    private ObservableList<Factura> listaFacturas;
    @FXML
    private TableView tblClientes;
    @FXML
    private StackPane stckCasa;
    @FXML
    private AnchorPane nodoHome;
    @FXML
    private AnchorPane nodoBienvenida;
    @FXML
    private AnchorPane busquedaRaizPrincipal;
    @FXML
    private VBox hBoxTotalCaja;
    @FXML
    private VBox HboxTotalClientes;
    @FXML
    private AnchorPane contenedorFacturas;
    @FXML
    private TableView<Factura> tablaFacturas;
    @FXML
    private TableColumn<Factura, Integer> colIdFactura;
    @FXML
    private TableColumn<Factura, Integer> colIdCliente;
    @FXML
    private TableColumn<Factura, Double> colCompra;
    @FXML
    private TableColumn<Factura, Date> colFecha;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTextoBienvenida();
        nodosAnimacion();
        cargarColumnas();
        contadorClientes();
        contadorCaja();
    }

    private void contadorClientes() {
        try {
            String sql = "SELECT COUNT(*) FROM clientes";
            PreparedStatement preparedStatetent = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatetent.executeQuery();

            int total = 0;
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
            lblTotalClientes.setText(String.valueOf(total));
        } catch (SQLException ex) {
            Logger.getLogger(InicioViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void contadorCaja() {
        try {
            String sql = "SELECT SUM(compras_cliente) FROM clientes";
            PreparedStatement preparedStatetent = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatetent.executeQuery();

            double total = 0.0;
            while (resultSet.next()) {
                total = resultSet.getDouble(1);
            }
            lblTotalCaja.setText(String.valueOf(total));
        } catch (SQLException ex) {
            Logger.getLogger(InicioViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarColumnas() {
        cargarTabla();
        columnaIdCliente.setCellValueFactory(new PropertyValueFactory("id"));
        columUsuarioCliente.setCellValueFactory(new PropertyValueFactory("nombre"));
        columnaCompraCliente.setCellValueFactory(new PropertyValueFactory("compras"));
        cargarTabla2();
        colIdFactura.setCellValueFactory(new PropertyValueFactory("id_factura"));
        colIdCliente.setCellValueFactory(new PropertyValueFactory("id_cliente"));
        colCompra.setCellValueFactory(new PropertyValueFactory("compras"));
        colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
    }

    private void setTextoBienvenida() {
        Integer id = BDayuda.ultimoIngreso();
        String nombre = BDayuda.devolverNombre(id);
        txtBienvenido.setText("¡Bienvenido de vuelta, " + nombre + "!");
        txtMensajeBienvenida.setText("Que te parece si agregamos mas clientes?");
    }

    private void cargarTabla2() {
        ArrayList<Factura> listaNueva = new ArrayList<>();
        try {
            String sql = "SELECT * FROM facturas";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer idfactura = resultSet.getInt("id_factura");
                Integer idCliente = resultSet.getInt("id_clienteFactura");
                Double comprasClientes = resultSet.getDouble("compras_factura");
                Date fecha = resultSet.getDate("fecha_factura");
                listaNueva.add(new Factura(idfactura, idCliente,comprasClientes,fecha));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InicioViewController.class.getName()).log(Level.SEVERE, null, ex);
            ConstructorAlerta.create(TipoAlerta.ERROR, stckCasa, nodoHome, nodoHome, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
        listaFacturas = FXCollections.observableArrayList(listaNueva);
        tablaFacturas.setItems(listaFacturas);
    }
    
    private void cargarTabla() {
        ArrayList<Cliente> listaNueva = new ArrayList<>();
        try {
            int total = 0;
            String sql = "SELECT * FROM clientes";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idClientes = resultSet.getInt("id_cliente");
                String nombreClientes = resultSet.getString("nombre_cliente");
                Double comprasClientes = resultSet.getDouble("compras_cliente");
                listaNueva.add(new Cliente(comprasClientes, idClientes, nombreClientes));
                total++;
            }
            lblTotalClientes.setText(String.valueOf(total));
        } catch (SQLException ex) {
            Logger.getLogger(InicioViewController.class.getName()).log(Level.SEVERE, null, ex);
            ConstructorAlerta.create(TipoAlerta.ERROR, stckCasa, nodoHome, nodoHome, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
        listaClientes = FXCollections.observableArrayList(listaNueva);
        tblClientes.setItems(listaClientes);
    }

    private void nodosAnimacion() {
        Animacion.fadeInUp(tblClientes);
        Animacion.fadeInUp(busquedaRaizPrincipal);
        Animacion.fadeInUp(nodoBienvenida);
        Animacion.fadeInUp(HboxTotalClientes);
        Animacion.fadeInUp(hBoxTotalCaja);
    }

    @FXML
    public void eventBuscar(KeyEvent event) {
        listaClientes.clear();
        listaClientes.setAll(BDayuda.buscarCliente(txtBuscarClienteReciente.getText()));
    }

    @FXML
    private void abrirFacturas(KeyEvent evt) {
        if (evt.isControlDown() && evt.getCode() == KeyCode.F) {
            nodoHome.setEffect(constantes.EFECTO_BOX_BLUR);
            contenedorFacturas.setVisible(true);
            tblClientes.setDisable(true);
            
            dialogoFacturas = new jfxDialogTools(contenedorFacturas, stckCasa);
            dialogoFacturas.show();

            dialogoFacturas.setOnDialogClosed(ev -> {
                tblClientes.setDisable(false);
                nodoHome.setEffect(null);
                contenedorFacturas.setVisible(false);
            });
        }
    }
    
    @FXML
    private void esconderDialogoFacturas(MouseEvent event) {
        if (dialogoFacturas != null) {
            dialogoFacturas.close();
        }
    }
}
