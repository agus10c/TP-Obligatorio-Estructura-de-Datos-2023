
package Estructuras;

public class NodoVert {

    private Object elem;
    private NodoVert sigVert;
    private NodoAdy primerAdy;
    
    NodoVert(Object elem, NodoVert vert) {        
        this.elem = elem;
        this.sigVert = vert;
        this.primerAdy = null;
    }
    
    public Object getElem() {
        return this.elem;
    }
    
    public void setElem(Object elem) {
        this.elem = elem;
    }
    
    public NodoVert getSigVert() {
        return this.sigVert;
    }
    
    public void setSigVert(NodoVert vert) {
        this.sigVert = vert;
    }
    
    public NodoAdy getPrimerAdy() {
        return this.primerAdy;
    }
    
    public void setPrimerAdy(NodoAdy ady) {
        this.primerAdy = ady;
    }

    Object getElminarimerAdy() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
