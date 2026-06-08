package Clases;

public class SolicitudDeViaje {

    private int numeroDeSolicitud = 0;
    private static int contador = 1;
    private int ciudadOrigen;
    private int ciudadDestino;
    private String fecha;
    private IDCliente id;
    private int metrosCubicos;
    private int cantBultos;
    private String domicilioRetiro;
    private String domicilioEntrega;
    private boolean estaPago;

    public SolicitudDeViaje(int cs, int cd, String fe, IDCliente id, int mc, int cb, String dr, String de, boolean pago) {
        this.numeroDeSolicitud = contador;
        this.contador++;
        this.ciudadOrigen = cs;
        this.ciudadDestino = cd;
        this.fecha = fe;
        this.id = id;
        this.metrosCubicos = mc;
        this.cantBultos = cb;
        this.domicilioRetiro = dr;
        this.domicilioEntrega = de;
        this.estaPago = pago;
    }

    public int getNumero() {
        return numeroDeSolicitud;
    }

    public int getCiudadOrigen() {
        return ciudadOrigen;
    }

    public int getCiudadDestino() {
        return ciudadDestino;
    }

    public String getFecha() {
        return fecha;
    }

    public IDCliente getID() {
        return id;
    }

    public int getMetrosCubicos() {
        return metrosCubicos;
    }

    public int getCantBultos() {
        return cantBultos;
    }

    public String getDomicilioDeRetiro() {
        return domicilioRetiro;
    }

    public String getDomicilioDeEntrega() {
        return domicilioEntrega;
    }

    public boolean getEstaPago() {
        return estaPago;
    }

    public void setFecha(String fe) {
        this.fecha = fe;
    }

    public void setID(IDCliente id) {
        this.id = id;
    }

    public void setMetrosCubicos(int mtsCub) {
        this.metrosCubicos = mtsCub;
    }

    public void setCantBultos(int cb) {
        this.cantBultos = cb;
    }

    public void setDomicilioDeRetiro(String dr) {
        this.domicilioRetiro = dr;
    }

    public void setDomicilioDeEntrega(String de) {
        this.domicilioEntrega = de;
    }

    public void setEstaPago(boolean p) {
        this.estaPago = p;
    }
        
           
    @Override
    public String toString() {
        return "Solicitud Numero " + this.numeroDeSolicitud + ": "
                + "CO: " + this.ciudadOrigen + " |retiro: " + this.domicilioRetiro + " -> " + "CD: " + this.ciudadDestino + " |entrega: " + this.domicilioEntrega
                + " | Cliente: " + this.id.toString()
                + " | Fecha: " + this.fecha
                + " | m3: " + this.metrosCubicos
                + " | Bultos: " + this.cantBultos
                + " | Pago: " + this.estaPago;
    }
}
