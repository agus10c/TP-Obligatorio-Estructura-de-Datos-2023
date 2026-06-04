
package Estructuras;


public class NodoHashMapeoM {
    private Object dominio;
    private Lista rango;
    private NodoHashMapeoM enlace;
            
    public NodoHashMapeoM(Object dominio, Lista rango, NodoHashMapeoM enlace) {
        this.dominio = dominio;
        this.rango = rango;
        this.enlace = enlace;
    }
    
    public Object getDominio() {
        return this.dominio;
    }
    
    public Lista getRango() {
        return this.rango;
    }
    
    public NodoHashMapeoM getEnlace() {
        return this.enlace;
    }    
    
    public void setDominio(Object dominio) {
       this.dominio = dominio;
    }
    
    public void setRango(Lista rango) {
        this.rango = rango;
    }
    
    public void setEnlace(NodoHashMapeoM en) {
        this.enlace = en;
    }     
    
    
}
