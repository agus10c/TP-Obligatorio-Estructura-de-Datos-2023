
package Estructuras;

public class NodoAVLDicc {
    private Comparable clave; 
    private Object elemento;
    private int altura;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;

    public NodoAVLDicc(Comparable clave, Object elemento, NodoAVLDicc izq, NodoAVLDicc der){
        this.clave = clave;
        this.elemento = elemento;
        this.izquierdo = izq;
        this.derecho = der;
        this.altura = 0;
    }

    public Comparable getClave(){
        return clave;
    }
    
    public void setClave(Comparable cl){
        this.clave = cl;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object dato) {
        this.elemento = dato;
    }

    public int getAltura() {
        return altura;
    }

    public void recalcularAltura(){
        //recalcula la altura del nodo
        if (this.izquierdo != null && this.derecho != null) {
            
            if (this.izquierdo.getAltura() >= this.derecho.getAltura()){
                this.altura = this.izquierdo.getAltura()+1;
                
            } else {
                
                this.altura = this.derecho.getAltura()+1;
                
            }
            
        } else {
            
            if (this.izquierdo != null) {
                
                this.altura = this.izquierdo.getAltura()+1;
            } else {
                
                if (this.derecho != null){
                    this.altura = this.derecho.getAltura()+1;
                } else {
                    this.altura = 0;
                }
                
            }
            
        }
        
    }

    public NodoAVLDicc getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAVLDicc izq) {
        this.izquierdo = izq;
    }

    public NodoAVLDicc getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVLDicc der) {
        this.derecho = der;
    }
    
    public boolean equalsEnlaceIzq(NodoAVLDicc enlaceIzq){
        return this.izquierdo == enlaceIzq;
    }

    public boolean equalsEnlaceDer(NodoAVLDicc enlaceDer){
        return this.derecho == enlaceDer;
    }
    
}
