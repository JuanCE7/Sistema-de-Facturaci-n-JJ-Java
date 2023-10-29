package Controlador;

import Alertas.ConstructorAlerta;
import Alertas.TipoAlerta;
import Animaciones.Animacion;
import BD.*;
import Constantes.constantes;
import Factura.Factura;
import Herramientas.contextoMenu;
import Modelo.Adquirido;
import Modelo.Producto;
import Notificaciones.ConstructorNotificacion;
import Notificaciones.TipoNotificacion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dialogTools.jfxDialogTools;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class VentasViewController implements Initializable {

    private ArrayList<Adquirido> lista = new ArrayList<>();
    private ObservableList<String> listaClientes;
    private ObservableList<Producto> listaProductos;
    private ObservableList<Adquirido> listaAdquiridos;
    private jfxDialogTools dialogoConfirmarCompra;
    private jfxDialogTools dialogoBorrarProducto;
    private jfxDialogTools dialogoFacturacion;
    private contextoMenu contextoMenu;
    private Integer id_factura;
    @FXML
    private StackPane stckVentas;
    @FXML
    private AnchorPane nodoVentas;
    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableView<Adquirido> tablaAdquiridos;
    @FXML
    private JFXButton btnAgregarProducto;
    @FXML
    private JFXTextField txtCantidad;
    @FXML
    private Text textoAdquirido;
    @FXML
    private Text textoProductos;
    @FXML
    private Text textoPagar;
    @FXML
    private Text textoCambio;
    @FXML
    private Text textoPagoM;
    @FXML
    private Text textoFecha;
    @FXML
    private Text textoVendedor;
    @FXML
    private TextField txtPrecioTotal;
    @FXML
    private JFXTextField txtPagar;
    @FXML
    private Separator separador;
    @FXML
    private JFXButton btnFacturar;
    @FXML
    private TextFlow teFlowClientes;
    @FXML
    private HBox hBoxBuscarCliente;
    @FXML
    private TextField txtBuscarProducto;
    @FXML
    private Text textoSignoDolar;
    @FXML
    private TableColumn<Producto, Integer> colCodigoBarra;
    @FXML
    private TableColumn<Producto, String> colProducto;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableColumn<Adquirido, Integer> colCantidad;
    @FXML
    private TableColumn<Adquirido, String> colProducto1;
    @FXML
    private TableColumn<Adquirido, Double> colPrecio1;
    @FXML
    private TableColumn<Adquirido, Double> colIva;
    @FXML
    private JFXComboBox<String> comboClientes;
    @FXML
    private TableColumn<Producto, String> colMarca;
    @FXML
    private TableColumn<Producto, Integer> colCantidadStock;
    @FXML
    private TextField txtBuscarIdCode;
    @FXML
    private AnchorPane contenedorConfirmarCompra;
    @FXML
    private AnchorPane contenedorEliminarAdquirido;
    @FXML
    private TableColumn<Adquirido, Integer> colCantidad1;
    @FXML
    private TableColumn<Adquirido, String> colProducto11;
    @FXML
    private TableColumn<Adquirido, Double> colPrecio11;
    @FXML
    private TableColumn<Adquirido, Double> colIva1;
    @FXML
    private AnchorPane contenedorFactura;
    @FXML
    private TableView<Adquirido> tablaFactura;
    @FXML
    private Text textoHora;
    @FXML
    private Label textoNumFactura;
    @FXML
    private Text textoCliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtPrecioTotal.setEditable(false);
        cargarDatos();
        cargarClientes();
        nodosAnimados();
        setContextoMenu();
    }

    @FXML
    private void filtroNombreProducto() {
        listaProductos.clear();
        listaProductos.setAll(BDayuda.buscarProducto("nombre_producto", txtBuscarProducto.getText()));
    }

    @FXML
    private void filtroIdCodigo() {
        listaProductos.clear();
        listaProductos.setAll(BDayuda.buscarProducto("codigo_Barra", txtBuscarIdCode.getText()));
    }

    private void nodosAnimados() {
        Animacion.fadeInUp(tablaProductos);
        Animacion.fadeInUp(tablaAdquiridos);
        Animacion.fadeInUp(hBoxBuscarCliente);
        Animacion.fadeInUp(btnAgregarProducto);
        Animacion.fadeInUp(teFlowClientes);
        Animacion.fadeInUp(textoSignoDolar);
        Animacion.fadeInUp(txtPrecioTotal);
        Animacion.fadeInUp(textoPagar);
        Animacion.fadeInUp(textoProductos);
        Animacion.fadeInUp(separador);
        Animacion.fadeInUp(btnFacturar);
        Animacion.fadeInUp(textoAdquirido);
        Animacion.fadeInUp(txtCantidad);
    }

    private void cargarDatos() {
        txtPrecioTotal.setText("0");
        txtCantidad.setText("1");
        cargarTabla();
        colCodigoBarra.setCellValueFactory(new PropertyValueFactory("codigoBarra"));
        colProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
        colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        colCantidadStock.setCellValueFactory(new PropertyValueFactory("cantidad"));

        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colProducto1.setCellValueFactory(new PropertyValueFactory("nombre"));
        colPrecio1.setCellValueFactory(new PropertyValueFactory("precio"));
        colIva.setCellValueFactory(new PropertyValueFactory("precioIva"));

        colCantidad1.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colProducto11.setCellValueFactory(new PropertyValueFactory("nombre"));
        colPrecio11.setCellValueFactory(new PropertyValueFactory("precio"));
        colIva1.setCellValueFactory(new PropertyValueFactory("precioIva"));

        tablaAdquiridos.setItems(listaAdquiridos);
    }

    private void cargarTabla() {
        ArrayList<Producto> list = new ArrayList<>();
        try {
            String sql = "SELECT codigo_Barra, nombre_producto, marca_producto, precio_producto, cantidad_producto FROM productos";
            PreparedStatement preparedStatement = BD.BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer codigoBarra = resultSet.getInt("codigo_Barra");
                String nombre = resultSet.getString("nombre_producto");
                String marca = resultSet.getString("marca_producto");
                Double precio = resultSet.getDouble("precio_producto");
                Integer cantidad = resultSet.getInt("cantidad_producto");
                list.add(new Producto(codigoBarra, nombre, marca, cantidad, precio));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InicioViewController.class.getName()).log(Level.SEVERE, null, ex);
            ConstructorAlerta.create(TipoAlerta.ERROR, stckVentas, nodoVentas, tablaProductos, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
        listaProductos = FXCollections.observableArrayList(list);
        tablaProductos.setItems(listaProductos);
        tablaProductos.setFixedCellSize(25);
    }

    private void cargarClientes() {
        ArrayList<String> listaC = new ArrayList<>();
        try {
            String sql = "SELECT id_cliente ,nombre_cliente FROM clientes";
            PreparedStatement preparedStatement = BD.BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id_cliente");
                String nombre = resultSet.getString("nombre_cliente");
                String palabra = id + "." + nombre;
                listaC.add(palabra);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InicioViewController.class.getName()).log(Level.SEVERE, null, ex);
            ConstructorAlerta.create(TipoAlerta.ERROR, stckVentas, nodoVentas, tablaProductos, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
        listaClientes = FXCollections.observableArrayList(listaC);
        comboClientes.setItems(listaClientes);
    }

    @FXML
    private void agregarProducto() throws SQLException {

        if (tablaProductos.getSelectionModel().getSelectedItems().isEmpty()) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckVentas, nodoVentas, tablaProductos, constantes.MENSAJE_NO_SELECCIONADO);
            return;
        }

        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
        Integer can = producto.getCantidad() - Integer.parseInt(txtCantidad.getText());

        if (can >= 0) {
            String sql = "UPDATE productos SET cantidad_producto = ? WHERE codigo_Barra = ?";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, can);
            preparedStatement.setInt(2, producto.getCodigoBarra());
            preparedStatement.execute();

            Adquirido adquirido = new Adquirido();
            adquirido.setCodigoBarra(producto.getCodigoBarra());
            adquirido.setNombre(producto.getNombre());
            Integer cantidad = Integer.parseInt(txtCantidad.getText());
            adquirido.setPrecio(producto.getPrecio() * cantidad);
            Double precioIva = (((0.12 * producto.getPrecio()) + producto.getPrecio()) * cantidad);
            Double precioIva2 = Math.round(precioIva * 100.0) / 100.0;
            adquirido.setCantidad(cantidad);
            adquirido.setPrecioIva(precioIva2);
            lista.add(adquirido);
            listaAdquiridos = FXCollections.observableArrayList(lista);
            tablaAdquiridos.setItems(listaAdquiridos);
            cargarDatos();
            setPrecio();
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, "Productos Insuficientes");
        }
    }

    @FXML
    private void mostrarDialogoConfirmarCompra() {
        if (tablaAdquiridos.getItems() == null) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckVentas, nodoVentas, tablaAdquiridos, "Agregue productos para facturar");
            return;
        }
        if (comboClientes.getValue() == null) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckVentas, nodoVentas, tablaProductos, "Seleccione un cliente");
            return;
        }

        nodoVentas.setEffect(constantes.EFECTO_BOX_BLUR);
        contenedorConfirmarCompra.setVisible(true);
        tablaProductos.setDisable(true);
        tablaAdquiridos.setDisable(true);

        dialogoConfirmarCompra = new jfxDialogTools(contenedorConfirmarCompra, stckVentas);
        dialogoConfirmarCompra.show();

        dialogoConfirmarCompra.setOnDialogClosed(ev -> {
            tablaProductos.setDisable(false);
            tablaAdquiridos.setDisable(false);
            nodoVentas.setEffect(null);
            contenedorConfirmarCompra.setVisible(false);
        });
    }

    @FXML
    private void pagar(MouseEvent event) {
        if (txtPagar.getText().isEmpty()) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckVentas, nodoVentas, tablaAdquiridos, "PA GA ME");
            return;
        }
        Double pago = Double.parseDouble(txtPagar.getText());
        Double precio = Double.parseDouble(txtPrecioTotal.getText());
        if (pago < precio) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckVentas, nodoVentas, tablaAdquiridos, "No le alcanza para pagar");
            return;
        }
        Double cambio = pago - precio;
        textoCambio.setText((Math.round(cambio * 100.0) / 100.0) + " $");
    }

    @FXML
    private void realizarCompra(MouseEvent event) {
        if (textoCambio.getText().isEmpty()) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckVentas, nodoVentas, tablaAdquiridos, "Para hacer su compra primero pague");
            return;
        }
        esconderDialogoFacturacion(event);
        Double compraTotal = Double.parseDouble(txtPrecioTotal.getText());
        String[] id = comboClientes.getValue().split("[.]");
        Integer idC = Integer.parseInt(id[0]);
        boolean result = BDayuda.actualizarClienteCompras(idC, compraTotal);
        if (result) {
            Factura factura = new Factura();
            factura.setId_factura(Integer.parseInt(textoNumFactura.getText()));
            factura.setId_cliente(idC);
            factura.setCompras(compraTotal);
            factura.setFecha(new java.sql.Date(new Date().getTime()));

            generarFactura(factura.getId_factura(), idC);

            BDayuda.insertarFactura(factura);
            lista.clear();
            listaAdquiridos.clear();
            tablaAdquiridos.setItems(null);
            comboClientes.setValue(null);
            cargarDatos();
            ConstructorAlerta.create(TipoAlerta.SUCCES, stckVentas, nodoVentas, tablaProductos, "Compra Realizada");
        } else {
            ConstructorNotificacion.create(TipoNotificacion.ERROR, constantes.MENSAJE_ERROR_CONEXION_MYSQL);
        }
        textoCambio.setText("");
        txtPagar.clear();
    }

    private void setContextoMenu() {
        contextoMenu = new contextoMenu(tablaAdquiridos);
        contextoMenu.getEditarBoton().setVisible(false);
        contextoMenu.setAccionEliminar(ev -> {
            mostrarDialogoEliminarAdquirido();
            contextoMenu.ocultar();
        });
        contextoMenu.mostrar();
    }

    private void mostrarDialogoEliminarAdquirido() {
        if (tablaAdquiridos.getSelectionModel().getSelectedItems().isEmpty()) {
            ConstructorAlerta.create(TipoAlerta.ERROR, stckVentas, nodoVentas, tablaAdquiridos, constantes.MENSAJE_NO_SELECCIONADO);
            return;
        }
        nodoVentas.setEffect(constantes.EFECTO_BOX_BLUR);
        contenedorEliminarAdquirido.setVisible(true);
        tablaAdquiridos.setDisable(true);

        dialogoBorrarProducto = new jfxDialogTools(contenedorEliminarAdquirido, stckVentas);
        dialogoBorrarProducto.show();

        dialogoBorrarProducto.setOnDialogClosed(ev -> {
            tablaAdquiridos.setDisable(false);
            nodoVentas.setEffect(null);
            contenedorEliminarAdquirido.setVisible(false);
        });
    }

    @FXML
    private void esconderDialogoConfirmarCompra(MouseEvent event) {
        if (dialogoConfirmarCompra != null) {
            dialogoConfirmarCompra.close();
        }
    }

    @FXML
    private void eliminarAdquirido(MouseEvent event) throws SQLException {
        Integer canti = 0;
        lista.remove(tablaAdquiridos.getSelectionModel().getSelectedItem());
        Integer cantidad = tablaAdquiridos.getSelectionModel().getSelectedItem().getCantidad();
        String nombre = tablaAdquiridos.getSelectionModel().getSelectedItem().getNombre();
        tablaAdquiridos.getItems().remove(tablaAdquiridos.getSelectionModel().getSelectedItem());
        String sql = "SELECT cantidad_producto FROM productos WHERE nombre_producto = ?";
        PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, nombre);
        preparedStatement.execute();
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            canti = rs.getInt("cantidad_producto");
        }
        canti = canti + cantidad;
        String sql1 = "UPDATE productos SET cantidad_producto = ? WHERE nombre_producto = ?";
        PreparedStatement preparedStatement1 = BDconexion.getInstance().getConnection().prepareStatement(sql1);
        preparedStatement1.setInt(1, canti);
        preparedStatement1.setString(2, nombre);
        preparedStatement1.execute();
        cargarDatos();
        setPrecio();
        esconderDialogoEliminarAdquirido(event);
    }

    private void setPrecio() {
        Double precioTotal = 0.0;
        for (int i = 0; i < lista.size(); i++) {
            precioTotal = precioTotal + lista.get(i).getPrecioIva();
        }
        if (comboClientes.getValue() != null) {
            String[] ide = comboClientes.getValue().split("[.]");
            Integer idee = Integer.parseInt(ide[0]);
            String tipo = "";
            try {
                String sql = "SELECT tipo_cliente FROM clientes WHERE id_cliente = ?";
                PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, idee);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    tipo = rs.getString("tipo_cliente");
                }
            } catch (SQLException ex) {
                Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (tipo.equals("Afiliado")) {
                precioTotal = precioTotal - (precioTotal * 0.10);
            }
        }
        txtPrecioTotal.setText(String.valueOf(Math.round(precioTotal * 100.0) / 100.0));
    }

    @FXML
    private void esconderDialogoEliminarAdquirido(MouseEvent event) {
        if (dialogoBorrarProducto != null) {
            dialogoBorrarProducto.close();
        }
    }

    private void generarFactura(Integer idF, Integer idC) {
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Factura#" + idF + "_" + idC + textoCliente.getText() + ".pdf"));

            com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/Imagenes/cabecera.png");
            header.scaleToFit(350, 700);
            header.setAlignment(Chunk.ALIGN_CENTER);

            Paragraph parrafo = new Paragraph();
            parrafo.setFont(FontFactory.getFont("arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("\t \t");
            parrafo.add(" \nFactura # " + idF + "\n \n");
            parrafo.add("Tienda #1"
                    + "                               "
                    + "                               "
                    + "               ");
            parrafo.add("Loja - Ecuador\n\n");
            parrafo.add("Fecha: " + textoFecha.getText()
                    + "                                  "
                    + "                            "
                    + "Hora: " + textoHora.getText() + "\n\n");
            parrafo.add("Vendedor: " + textoVendedor.getText()
                    + "                                     "
                    + "             "
                    + "Cliente: " + textoCliente.getText() + "\n\n");
            PdfPTable tabla = new PdfPTable(4);
            tabla.setFooterRows(4);
            tabla.addCell("Cantidad");
            tabla.addCell("Producto");
            tabla.addCell("Precio");
            tabla.addCell("Precio Iva");

            for (int i = 0; i < listaAdquiridos.size(); i++) {
                tabla.addCell(String.valueOf(listaAdquiridos.get(i).getCantidad()));
                tabla.addCell(listaAdquiridos.get(i).getNombre());
                tabla.addCell(String.valueOf(listaAdquiridos.get(i).getPrecio()));
                tabla.addCell(String.valueOf(listaAdquiridos.get(i).getPrecioIva()));
            }
            Paragraph parrafo2 = new Paragraph();
            parrafo2.setFont(FontFactory.getFont("arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo2.setAlignment(Paragraph.ALIGN_RIGHT);
            parrafo2.add("\nTotal: " + textoPagoM.getText() + "                \n");
            parrafo2.add("\nPago: " + txtPagar.getText() + "                    \n");
            parrafo2.add("\nCambio: " + textoCambio.getText() + "             \n");

            Paragraph parrafo3 = new Paragraph();
            parrafo3.setFont(FontFactory.getFont("arial", 13, Font.BOLDITALIC, BaseColor.BLACK));
            parrafo3.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo3.add("\nComprobante de Venta");

            documento.open();
            documento.add(header);
            documento.add(parrafo);
            documento.add(tabla);
            documento.add(parrafo2);
            documento.add(parrafo3);
            documento.close();
        } catch (DocumentException | IOException e) {
            System.err.println("Error al generar PDF " + e);
        }
    }

    @FXML
    private void mostrarDialogoFacturacion(MouseEvent event) throws SQLException {
        esconderDialogoConfirmarCompra(event);
        id_factura = BDayuda.numeroFactura();
        if (id_factura == null) {
            id_factura = 1;
        } else {
            id_factura += 1;
        }
        textoPagoM.setText(txtPrecioTotal.getText() + " $");
        nodoVentas.setEffect(constantes.EFECTO_BOX_BLUR);
        contenedorFactura.setVisible(true);
        tablaAdquiridos.setDisable(true);
        String[] nomb = comboClientes.getValue().split("[.]");

        textoNumFactura.setText("" + id_factura);
        textoCliente.setText(nomb[1]);
        textoFecha.setText("" + new java.sql.Date(new Date().getTime()));
        textoHora.setText("" + new java.sql.Time(new Date().getTime()));
        textoVendedor.setText("" + BDayuda.devolverNombre(BDayuda.ultimoIngreso()));

        tablaFactura.setItems(listaAdquiridos);
        dialogoFacturacion = new jfxDialogTools(contenedorFactura, stckVentas);
        dialogoFacturacion.show();

        dialogoFacturacion.setOnDialogClosed(ev -> {
            tablaAdquiridos.setDisable(false);
            nodoVentas.setEffect(null);
            contenedorFactura.setVisible(false);
        });
    }

    @FXML
    private void esconderDialogoFacturacion(MouseEvent event) {
        if (dialogoFacturacion != null) {
            dialogoFacturacion.close();
        }
    }

    @FXML
    public void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtPagar)) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) && !event.getCharacter().equals(".") || txtPagar.getText().length() > 5) {
                event.consume();
            }
        } else if (evt.equals(txtCantidad)) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) || txtCantidad.getText().length() > 4) {
                event.consume();
            }
        }
    }
}
