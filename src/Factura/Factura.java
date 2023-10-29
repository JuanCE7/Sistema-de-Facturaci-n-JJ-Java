
package Factura;

import java.sql.Date;

public class Factura {
    private Integer id_factura;
    private Integer id_cliente;
    private Double compras;
    private Date fecha;

    public Factura(Integer id_factura, Integer id_cliente, Double compras, Date fecha) {
        this.id_factura = id_factura;
        this.id_cliente = id_cliente;
        this.compras = compras;
        this.fecha = fecha;
    }

    public Factura() {
    }

    public Integer getId_factura() {
        return id_factura;
    }

    public void setId_factura(Integer id_factura) {
        this.id_factura = id_factura;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Double getCompras() {
        return compras;
    }

    public void setCompras(Double compras) {
        this.compras = compras;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
    
}
