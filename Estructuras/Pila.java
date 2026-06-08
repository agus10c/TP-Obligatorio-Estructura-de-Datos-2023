package Estructuras;

import Estructuras.Nodo;

public class Pila {

    private Nodo tope;

    public Pila() {
        this.tope = null;
    }

    public boolean apilar(Object nuevoElem) {
        
        Nodo nuevo = new Nodo(nuevoElem, this.tope);
        this.tope = nuevo;
        
        return true;
        
    }
    
    

    public boolean desapilar() {
        
        boolean exito;
        
        if (this.tope == null) {
            
            exito = false;
            
        } else {
            
            this.tope = tope.getEnlace();
            exito = true;
            
        }
        
        return exito;
        
    }
    

    public Object obtenerTope() {
        
        Object r;
        
        if (tope == null) {
            r = null;
        } else {
            r = tope.getElemento();
        }
        
        return r;
        
    }

    public boolean esVacia() {
        return tope == null;
    }

    public void vaciar() {
        tope = null;
    }

    public Pila clone() {
        Pila clon;
        clon = new Pila();
        Nodo aux1, aux2;
        aux1 = this.tope;
        
        clon.tope = new Nodo(aux1.getElemento(), null);
        
        aux1 = aux1.getEnlace();
        aux2 = clon.tope;
        
        while (aux1 != null) {
            
            aux2.setEnlace(new Nodo(aux1.getElemento(), null));
            
            aux1 = aux1.getEnlace();
            aux2 = aux2.getEnlace();
            
        }
        
        return clon;
        
    }

    public Pila cloneRecursivo() {
        
        Pila clon = new Pila();
        clon.tope = cloneAux(this.tope);
        
        return clon;
        
    }
    

    private Nodo cloneAux(Nodo n) {
        
        Nodo nuevo;
        
        if (n == null) {
            nuevo = null;
        } else {
            nuevo = new Nodo(n.getElemento(), cloneAux(n.getEnlace()));
        }
        
        return nuevo;
        
    }

    public String toString() {
        
        String cadena = "[";
        
        Nodo aux = this.tope;
        
        if (this.tope == null) {
            
            cadena += "]";
            
        } else {
            
            while (aux != null) {
                
                cadena = cadena + aux.getElemento();
                
                aux = aux.getEnlace();
                
                if (aux != null) {
                    cadena += ", ";
                }
                
            }
            
            cadena += "]";
            
        }
        
        return cadena;
        
    }
    
}
