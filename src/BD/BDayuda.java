package BD;

import Factura.Factura;
import Modelo.Cliente;
import Modelo.Producto;
import Modelo.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class BDayuda {

    public static boolean insertNuevoCliente(Cliente cliente, ObservableList<Cliente> listaClientes) {
        try {
            String sql = "INSERT INTO clientes (nombre_cliente, cedula_cliente, telefono_cliente, correo_cliente,"
                    + " direccion_cliente, tipo_cliente, compras_cliente) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getCedula());
            preparedStatement.setString(3, cliente.getTelefono());
            preparedStatement.setString(4, cliente.getCorreo());
            preparedStatement.setString(5, cliente.getDireccion());
            preparedStatement.setString(6, cliente.getTipoCliente());
            preparedStatement.setDouble(7, cliente.getCompras());
            preparedStatement.execute();
            listaClientes.add(cliente);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean borrarCliente(TableView<Cliente> tabla, ObservableList<Cliente> listaClientes) {
        try {
            String sql = "DELETE FROM clientes WHERE id_cliente = ?";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, tabla.getSelectionModel().getSelectedItem().getId());
            preparedStatement.execute();
            listaClientes.remove(tabla.getSelectionModel().getSelectedIndex());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean actualizarCliente(Cliente cliente) {
        try {
            String sql = "UPDATE clientes SET nombre_cliente = ?, cedula_cliente = ?, telefono_cliente = ?, correo_cliente = ?,"
                    + " direccion_cliente = ?, tipo_cliente = ? WHERE id_cliente = ?";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getCedula());
            preparedStatement.setString(3, cliente.getTelefono());
            preparedStatement.setString(4, cliente.getCorreo());
            preparedStatement.setString(5, cliente.getDireccion());
            preparedStatement.setString(6, cliente.getTipoCliente());
            preparedStatement.setInt(7, cliente.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean actualizarClienteCompras(Integer id, Double compras) {
        try {
            String sql = "UPDATE clientes SET compras_cliente = ? WHERE id_cliente = ?";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setDouble(1, compras);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static ObservableList<Cliente> buscarCliente(String nombreBusqueda) {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        try {
            String sql = "SELECT id_cliente, nombre_cliente,compras_cliente"
                    + " FROM clientes WHERE nombre_cliente LIKE '%" + nombreBusqueda + "%'";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre_cliente");
                Double compras = rs.getDouble("compras_cliente");
                clientes.add(new Cliente(compras, id, nombre));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clientes;
    }

    public static ObservableList<Cliente> buscarCliente(String consulta, String nombreBusqueda) {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        try {
            String sql = "SELECT id_cliente, nombre_cliente, cedula_cliente, telefono_cliente, correo_cliente, "
                    + "direccion_cliente, tipo_cliente FROM clientes WHERE " + consulta + " LIKE '%" + nombreBusqueda + "%'";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre_cliente");
                String cedula = rs.getString("cedula_cliente");
                String telefono = rs.getString("telefono_cliente");
                String correo = rs.getString("correo_cliente");
                String direccion = rs.getString("direccion_cliente");
                String tipo = rs.getString("tipo_cliente");
                clientes.add(new Cliente(tipo, id, nombre, cedula, telefono, correo, direccion));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clientes;
    }

    public static ObservableList<Usuario> buscarUsuario(String consulta, String nombreC) {
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
        try {
            String sql = "SELECT id_usuario, nombre_usuario, cedula_usuario, telefono_usuario, correo_usuario, "
                    + "direccion_usuario, tipoUsuario, usuario, contraseña_usuario FROM usuarios WHERE " + consulta + " LIKE '%" + nombreC + "%'";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre_usuario");
                String cedula = rs.getString("cedula_usuario");
                String telefono = rs.getString("telefono_usuario");
                String correo = rs.getString("correo_usuario");
                String direccion = rs.getString("direccion_usuario");
                String tipo = rs.getString("tipoUsuario");
                String usuario = rs.getString("usuario");
                String contraseña = rs.getString("contraseña_usuario");
                usuarios.add(new Usuario(usuario, contraseña, tipo, id, nombre, cedula, telefono, correo, direccion));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    public static ObservableList<Producto> buscarProducto(String consulta, String codigo_barra) {
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        try {
            String sql = "SELECT codigo_Barra,nombre_producto,marca_producto, cantidad_producto,precio_producto FROM productos WHERE " + consulta + " LIKE '%" + codigo_barra + "%'";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer codigoBarra = rs.getInt("codigo_Barra");
                String nombre = rs.getString("nombre_producto");
                String marca = rs.getString("marca_producto");
                Integer cantidad = rs.getInt("cantidad_producto");
                Double precio = rs.getDouble("precio_producto");
                productos.add(new Producto(codigoBarra, nombre, marca, cantidad, precio));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productos;
    }

    public static boolean insertNuevoProducto(Producto producto, ObservableList<Producto> listaProductos) {
        try {
            String sql = "INSERT INTO productos (codigo_Barra, nombre_producto, marca_producto, cantidad_producto, precio_producto) "
                    + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, producto.getCodigoBarra());
            preparedStatement.setString(2, producto.getNombre());
            preparedStatement.setString(3, producto.getMarca());
            preparedStatement.setInt(4, producto.getCantidad());
            preparedStatement.setDouble(5, producto.getPrecio());
            preparedStatement.execute();
            listaProductos.add(producto);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean borrarProducto(TableView<Producto> tablaProductos, ObservableList<Producto> listaProductos) {
        try {
            String sql = "DELETE FROM productos WHERE codigo_Barra = ?";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, tablaProductos.getSelectionModel().getSelectedItem().getCodigoBarra());
            preparedStatement.execute();
            listaProductos.remove(tablaProductos.getSelectionModel().getSelectedIndex());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean actualizarProducto(Producto producto) {
        try {
            String sql = "UPDATE productos SET nombre_producto = ?, marca_producto = ?, "
                    + "cantidad_producto = ?, precio_producto = ? WHERE codigo_Barra = ?";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getMarca());
            preparedStatement.setInt(3, producto.getCantidad());
            preparedStatement.setDouble(4, producto.getPrecio());
            preparedStatement.setInt(5, producto.getCodigoBarra());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean actualizarProductoEsNull(Producto producto) {
        try {
            String sql = "UPDATE productos SET nombre_producto = ?, marca_producto = ?, "
                    + "cantidad_producto = ?, precio_producto = ? WHERE codigo_Barra = ?";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getMarca());
            preparedStatement.setInt(3, producto.getCantidad());
            preparedStatement.setDouble(4, producto.getPrecio());
            preparedStatement.setInt(5, producto.getCodigoBarra());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static int comprobarSiElProductoExiste(String codigo_Barra) {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM productos WHERE codigo_Barra = ?";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, codigo_Barra);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public static boolean insertNuevoUsuario(Usuario usuario, ObservableList<Usuario> listaUsuarios) {
        try {
            String sql = "INSERT INTO usuarios (nombre_usuario, cedula_usuario, telefono_usuario, correo_usuario, "
                    + "direccion_usuario, usuario, contraseña_usuario, tipoUsuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getCedula());
            preparedStatement.setString(3, usuario.getTelefono());
            preparedStatement.setString(4, usuario.getCorreo());
            preparedStatement.setString(5, usuario.getDireccion());
            preparedStatement.setString(6, usuario.getUsuario());
            preparedStatement.setString(7, usuario.getContraseña());
            preparedStatement.setString(8, usuario.getTipoUsuario());
            preparedStatement.execute();
            listaUsuarios.add(usuario);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean insertNuevoUsuario(Usuario usuario) {
        try {
            String sql = "INSERT INTO usuarios (nombre_usuario, cedula_usuario, telefono_usuario, correo_usuario, "
                    + "direccion_usuario, usuario, contraseña_usuario, tipoUsuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getCedula());
            preparedStatement.setString(3, usuario.getTelefono());
            preparedStatement.setString(4, usuario.getCorreo());
            preparedStatement.setString(5, usuario.getDireccion());
            preparedStatement.setString(6, usuario.getUsuario());
            preparedStatement.setString(7, usuario.getContraseña());
            preparedStatement.setString(8, usuario.getTipoUsuario());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean borrarUsuario(TableView<Usuario> tablaUsuarios, ObservableList<Usuario> listaUsuarios) {
        try {
            String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, tablaUsuarios.getSelectionModel().getSelectedItem().getId());
            preparedStatement.execute();
            listaUsuarios.remove(tablaUsuarios.getSelectionModel().getSelectedIndex());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean actualizarUsuario(Usuario usuario) {
        try {
            String sql = "UPDATE usuarios SET  nombre_usuario = ?, cedula_usuario = ?, telefono_usuario = ?, correo_usuario = ?, "
                    + "direccion_usuario = ?, usuario = ?, contraseña_usuario = ?, tipoUsuario = ? WHERE id_usuario = ?";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getCedula());
            preparedStatement.setString(3, usuario.getTelefono());
            preparedStatement.setString(4, usuario.getCorreo());
            preparedStatement.setString(5, usuario.getDireccion());
            preparedStatement.setString(6, usuario.getUsuario());
            preparedStatement.setString(7, usuario.getContraseña());
            preparedStatement.setString(8, usuario.getTipoUsuario());
            preparedStatement.setInt(9, usuario.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean actualizarUsuarioConfiguracion(Usuario usuario) {
        try {
            String sql = "UPDATE usuarios SET nombre_usuario = ?, usuario = ?, contraseña_usuario = ? WHERE id_usuario = ?";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getUsuario());
            preparedStatement.setString(3, usuario.getContraseña());
            preparedStatement.setInt(4, usuario.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static int verificarExistencia(String tabla, String consulta, String cambio) {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE " + consulta + " = ? ";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, cambio);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public static int siExistenUsuarios() {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM usuarios";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public static String traerTipoDeUsuario() {
        String userType = null;
        try {
            String sql = "SELECT usuarios.tipoUsuario FROM usuarios WHERE usuarios.usuario = usuario";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userType = resultSet.getString("tipoUsuario");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
            userType = "Empleado";
        }
        return userType;
    }

    public static String devolverNombre(Integer id) {
        String nombre = "";
        try {
            String sql2 = "SELECT nombre_ingreso FROM ingresos WHERE id_Ningreso = ?";
            PreparedStatement preparedStatementTwo = BDconexion.getInstance().getConnection().prepareStatement(sql2);
            preparedStatementTwo.setInt(1, id);
            preparedStatementTwo.execute();
            ResultSet resultSetTwo = preparedStatementTwo.executeQuery();
            while (resultSetTwo.next()) {
                nombre = resultSetTwo.getString("nombre_ingreso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
            nombre = "vendedor ·#·";
        }
        return nombre;
    }

    public static Integer ultimoIngreso() {
        Integer id = 0;
        try {
            String sql = "SELECT MAX(id_Ningreso) AS id FROM ingresos";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public static Integer idUsuario(Integer id) {
        Integer idU = 0;
        try {
            String sql2 = "SELECT id_usuarioIngreso FROM ingresos WHERE id_Ningreso = ?";
            PreparedStatement preparedStatementTwo = BDconexion.getInstance().getConnection().prepareStatement(sql2);
            preparedStatementTwo.setInt(1, id);
            preparedStatementTwo.execute();
            ResultSet resultSetTwo = preparedStatementTwo.executeQuery();
            while (resultSetTwo.next()) {
                idU = resultSetTwo.getInt("id_usuarioIngreso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idU;
    }

    public static void insertarIngreso(Usuario us) {
        try {
            String sql2 = "INSERT INTO ingresos (id_usuarioIngreso, nombre_ingreso, fecha_ingreso) VALUES (?, ?, ?)";
            PreparedStatement preparedStatementTwo = BDconexion.getInstance().getConnection().prepareStatement(sql2);
            preparedStatementTwo.setInt(1, us.getId());
            preparedStatementTwo.setString(2, us.getNombre());
            preparedStatementTwo.setDate(3, new java.sql.Date(new Date().getTime()));
            preparedStatementTwo.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Integer numeroFactura() {
        Integer id = 0;
        try {
            String sql = "SELECT MAX(id_factura) AS id FROM facturas";
            PreparedStatement preparedStatement = BDconexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public static void insertarFactura(Factura fac) {
        try {
            String sql2 = "INSERT INTO facturas (id_factura, id_clienteFactura, compras_factura, fecha_factura) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatementTwo = BDconexion.getInstance().getConnection().prepareStatement(sql2);
            preparedStatementTwo.setInt(1, fac.getId_factura());
            preparedStatementTwo.setInt(2, fac.getId_cliente());
            preparedStatementTwo.setDouble(3, fac.getCompras());
            preparedStatementTwo.setDate(4, new java.sql.Date(new Date().getTime()));
            preparedStatementTwo.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BDayuda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
