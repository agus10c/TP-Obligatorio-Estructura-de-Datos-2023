package Estructuras;

public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object nuevoElem) {
        
        Nodo nuevo = new Nodo(nuevoElem, null);
        
        if (this.frente == null) {
            
            this.frente = nuevo;
            this.fin = nuevo;
            
        } else {
            
            this.fin.setEnlace(nuevo);
            this.fin = nuevo;
            
        }
        
        return true;
        
    }
    

    public boolean sacar() {
        
        boolean exito = true;
        
        if (this.frente == null) {
            exito = false;
        } else {
            this.frente = this.frente.getEnlace();
            
            if (this.frente == null) {
                this.fin = null;
            }
            
        }
        
        return exito;
        
    }
    

    public Object obtenerFrente() {
        
        Object r;
        
        if (frente == null) {
            r = null;
        } else {
            r = frente.getElemento();
        }
        
        return r;
        
    }

    public boolean esVacia() {
        return frente == null;
    }

    public void vaciar() {
        frente = null;
        fin = null;
    }

    public Cola clone() {
        
        Cola clon = new Cola();
        
        if (this.frente != null) {
            
            Nodo aux = this.frente.getEnlace();
            
            clon.frente = new Nodo(this.frente.getElemento(), null);
            clon.fin = clon.frente;
            
            while (aux != null) {
                
                clon.fin.setEnlace(new Nodo(aux.getElemento(), null));
                clon.fin = clon.fin.getEnlace();
                
                aux = aux.getEnlace();
                
            }
            
        }
        
        return clon;
        
    }

    @Override
    public String toString() {
        
        String cadena = "[";
        
        if (this.frente != null) {
            
            Nodo aux = this.frente;
            
            while (aux.getEnlace() != null) {
                
                cadena += aux.getElemento() + ", ";
                
                aux = aux.getEnlace();
                
            }
            
            cadena += aux.getElemento();
            
        }
        
        cadena += "]";
        
        return cadena;
        
    }
    
}
