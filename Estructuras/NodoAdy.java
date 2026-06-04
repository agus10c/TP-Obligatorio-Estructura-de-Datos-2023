
package Estructuras;

public class NodoAdy {
    
    private NodoVert vertice;
    private NodoAdy sigAdy;
    private Double etiqueta;
    
    NodoAdy(NodoVert vert, NodoAdy ady, Double etiqueta) {
        this.vertice = vert;
        this.sigAdy = ady;
        this.etiqueta = etiqueta;
    }
    
    public NodoVert getVertice() {
        return this.vertice;
    }
    
    public void setVertice(NodoVert vert) {
        this.vertice = vert;
    }
    
    public double getEtiqueta() {
        return this.etiqueta;
    }
    
    public NodoAdy getSigAdy() {
        return this.sigAdy;
    }
    
    public void setSigAdy(NodoAdy ady) {
        this.sigAdy = ady;
    }
    
    public void setEtiqueta(Double etiq) {
        this.etiqueta = etiq;
    }
}
