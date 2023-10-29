
package Modelo;

public class Usuario extends Persona{

    private String usuario;
    private String contraseña;
    private String tipoUsuario;

    public Usuario() {
    }

    public Usuario(String usuario, String contraseña, String tipoUsuario, Integer id, String nombre, String cedula, String telefono, String correo, String direccion) {
        super(id, nombre, cedula, telefono, correo, direccion);
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(String usuario, String contraseña, Integer id, String nombre) {
        super(id, nombre);
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public Usuario(String tipoUsuario, Integer id, String nombre) {
        super(id, nombre);
        this.tipoUsuario = tipoUsuario;
    }
    
    public Usuario(String usuario, String contraseña, String tipoUsuario, String nombre, String cedula, String telefono, String correo, String direccion) {
        super(nombre, cedula, telefono, correo, direccion);
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    
}
