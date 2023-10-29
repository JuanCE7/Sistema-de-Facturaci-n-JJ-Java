package Controlador;

import Alertas.ConstructorAlerta;
import Alertas.TipoAlerta;
import Animaciones.Animacion;
import BD.BDayuda;
import Constantes.constantes;
import Modelo.Cliente;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import java.io.FileOutputStream;
import java.io.IOException;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class TarjetaViewController implements Initializable {

    private ObservableList<Cliente> listaClientes;

    @FXML
    private HBox hBoxBuscarCliente;

    @FXML
    private TextField txtBuscarId;

    @FXML
    private TextField txtBuscarNombre;

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
    private Text textoVistaPrevia;
    @FXML
    private ImageView imgPrevia;
    @FXML
    private JFXButton btnGenerar;
    @FXML
    private Text textoNombreCliente;
    @FXML
    private StackPane stckTarjeta;
    @FXML
    private AnchorPane nodoTarjeta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        nodosAnimados();
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

    private void nodosAnimados() {
        Animacion.fadeInUp(tblClientes);
        Animacion.fadeInUp(hBoxBuscarCliente);
        Animacion.fadeInUp(textoVistaPrevia);
        Animacion.fadeInUp(btnGenerar);
        Animacion.fadeInUp(imgPrevia);
        Animacion.fadeInUp(textoNombreCliente);
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
                if(tipo.equals("Afiliado")){
                    lista.add(new Cliente(tipo, id, nombre, cedula, telefono, correo, direccion));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InicioViewController.class.getName()).log(Level.SEVERE, null, ex);
            ConstructorAlerta.create(TipoAlerta.ERROR, stckTarjeta, nodoTarjeta, tblClientes, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
        listaClientes = FXCollections.observableArrayList(lista);
        tblClientes.setItems(listaClientes);
        tblClientes.setFixedCellSize(25);
    }

    @FXML
    private void generarTarjeta() {
        if (tblClientes.getSelectionModel().getSelectedItems().isEmpty()) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckTarjeta, nodoTarjeta, tblClientes, "Seleccione un cliente en la tabla");
            return;
        }
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Tarjeta"
                    +tblClientes.getSelectionModel().getSelectedItem().getId()
                    +tblClientes.getSelectionModel().getSelectedItem().getNombre()+".pdf"));

            com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/Imagenes/Tarjeta.png");
            header.scaleToFit(500, 700);
            Paragraph parrafo = new Paragraph();
            parrafo.setFont(FontFactory.getFont("Century", 30, Font.BOLDITALIC, BaseColor.WHITE));
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            header.setAlignment(com.itextpdf.text.Image.UNDERLYING);
            parrafo.add("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n                         " 
                    + tblClientes.getSelectionModel().getSelectedItem().getNombre());
            documento.open();
            documento.add(header);
            documento.add(parrafo);
            documento.close();
        } catch (DocumentException | IOException e) {
            System.err.println("Error al generar PDF " + e);
        }
        ConstructorAlerta.create(TipoAlerta.SUCCES, stckTarjeta, nodoTarjeta, tblClientes, "Tarjeta Generada");
    }
}
