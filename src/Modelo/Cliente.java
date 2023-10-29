
package Modelo;

public class Cliente extends Persona{
    private String tipoCliente;
    private Double compras;

    public Cliente() {
    }

    public Cliente(String tipoCliente, Double compras, Integer id, String nombre, String cedula, String telefono, String correo, String direccion) {
        super(id, nombre, cedula, telefono, correo, direccion);
        this.tipoCliente = tipoCliente;
        this.compras = compras;
    }

    public Cliente(String tipoCliente, Integer id, String nombre, String cedula, String telefono, String correo, String direccion) {
        super(id, nombre, cedula, telefono, correo, direccion);
        this.tipoCliente = tipoCliente;
    }

    public Cliente(Double compras, Integer id, String nombre) {
        super(id, nombre);
        this.compras = compras;
    }

    public Cliente(Integer id, String nombre) {
        super(id, nombre);
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Double getCompras() {
        return compras;
    }

    public void setCompras(Double compras) {
        this.compras = compras;
    }   
}
