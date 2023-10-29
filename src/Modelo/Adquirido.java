package Modelo;

public class Adquirido {
    private Integer codigoBarra;
    private Integer cantidad;
    private String nombre;
    private Double precio;
    private Double precioIva;

    public Adquirido() {
    }

    public Adquirido(Integer codigoBarra, Integer cantidad, String nombre, Double precio, Double precioIva) {
        this.codigoBarra = codigoBarra;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.precio = precio;
        this.precioIva = precioIva;
    }

    public Integer getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(Integer codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPrecioIva() {
        return precioIva;
    }

    public void setPrecioIva(Double precioIva) {
        this.precioIva = precioIva;
    }
    
    
}
