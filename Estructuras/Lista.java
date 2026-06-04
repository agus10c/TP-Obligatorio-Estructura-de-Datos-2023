package Estructuras;

public class Lista {

    private Nodo cabecera;
    private int longitud;

    public Lista() {
        this.cabecera = null;
        this.longitud = 0;
    }

    public boolean insertar(Object nuevoElem, int pos) {
        boolean exito = true;
        if (pos < 1 || pos > this.longitud + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = new Nodo(nuevoElem, this.cabecera);
                this.longitud++;
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(nuevoElem, aux.getEnlace());
                aux.setEnlace(nuevo);
                this.longitud++;
            }
        }
        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito = true;
        if (pos < 1 || pos > this.longitud) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
                this.longitud--;
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
                this.longitud--;
            }
        }
        return exito;
    }

    public Object recuperar(int pos) {
        Object elem;
        if (pos < 1 || pos > this.longitud) {
            elem = null;
        } else {
            Nodo aux = this.cabecera;
            int i = 1;
            while (i != pos) {
                aux = aux.getEnlace();
                i++;
            }
            elem = aux.getElemento();
        }
        return elem;
    }

    public int localizar(Object elem) {
        int pos = 1;
        Nodo aux = this.cabecera;
        while (aux != null) {
            if (aux.getElemento().equals(elem)) {
                aux = null;
            } else {
                aux = aux.getEnlace();
                pos++;
            }
        }
        if (pos > this.longitud) {
            pos = -1;
        }
        return pos;
    }

    public void vaciar() {
        this.cabecera = null;
        this.longitud = 0;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public int longitud() {
        return this.longitud;
    }

    public Lista clone() {
        Lista clon = new Lista();
        if (this.cabecera != null) {
            clon.cabecera = cloneAux(this.cabecera);
        }
        clon.longitud = this.longitud;
        return clon;
    }

    private Nodo cloneAux(Nodo nodo) {
        Nodo nuevo;
        nuevo = new Nodo(nodo.getElemento(), null);
        if (nodo.getEnlace() != null) {
            nuevo.setEnlace(cloneAux(nodo.getEnlace()));
        }
        return nuevo;
    }

//    public String toString() {
//        String cadena = "[";
//        Nodo aux = this.cabecera;
//        while (aux != null) {
//            cadena = cadena + aux.getElemento();
//            aux = aux.getEnlace();
//            if (aux != null) {
//                cadena += ", ";
//            }
//        }
//        cadena += "]";
//        return cadena;
//    }
    public void invertir() {
        if (this.cabecera != null) {
            if (this.cabecera.getEnlace() != null) {
                this.cabecera = invertirAux(this.cabecera);
            }
        }
    }

    private Nodo invertirAux(Nodo n) {
        Nodo invertido;
        if (n.getEnlace().getEnlace() == null) {
            n.getEnlace().setEnlace(n);
            invertido = n.getEnlace();
            this.cabecera.setEnlace(null);
        } else {
            invertido = invertirAux(n.getEnlace());
            n.getEnlace().setEnlace(n);
        }
        return invertido;
    }

    public void eliminarRepetidos() {
        Nodo aux = this.cabecera;
        while (aux != null) {
            eliminarRepetidosAux(aux, aux.getElemento());
            aux = aux.getEnlace();
        }
    }

    public void eliminarRepetidosAux(Nodo n, Object elem) {
        Nodo aux = n.getEnlace();
        if (n.getEnlace() != null) {
            if (aux.getElemento().equals(elem)) {
                n.setEnlace(aux.getEnlace());
                this.longitud--;
                if(n.getEnlace() != null) {
                    eliminarRepetidosAux(n.getEnlace(), elem);
                } 
            } else {
                eliminarRepetidosAux(n.getEnlace(), elem);
            }          
        }
    }

    public boolean verificarRepetidos() {
        boolean exito = true;
        Object elem;
        Nodo aux, n = this.cabecera;
        while (n != null && exito) {
            elem = n.getElemento();
            aux = n.getEnlace();
            while (aux != null && exito) {
                if (aux.getElemento().equals(elem)) {
                    exito = false;
                }
                aux = aux.getEnlace();
            }
            n = n.getEnlace();
        }
        return exito;
    }

    public Lista obtenerMultiplos(int num) {
        //genera una lista con los elementos que estan en las posiciones multiplo de num
        Lista lis = new Lista();
        lis.cabecera = obtenerMultiplosAux(this.cabecera, num, 1, lis);
        return lis;
    }

    private Nodo obtenerMultiplosAux(Nodo n, int num, int pos, Lista lis) {
        Nodo nuevo;
        if (n != null) {
            if (pos % num == 0) {
                nuevo = new Nodo(n.getElemento(), obtenerMultiplosAux(n.getEnlace(), num, pos + 1, lis));
                lis.longitud = lis.longitud + 1;
            } else {
                nuevo = obtenerMultiplosAux(n.getEnlace(), num, pos + 1, lis);
            }
        } else {
            nuevo = null;
        }
        return nuevo;
    }

    public void eliminarApariciones(Object elem) {
        //elimina todas las apariciones de el elmento ingresado por parametro de la lista
        while (this.cabecera != null && this.cabecera.getElemento().equals(elem)) {
            this.cabecera = this.cabecera.getEnlace();
            this.longitud--;
        }
        if (this.cabecera != null) {
            Nodo aux = this.cabecera;
            Nodo aux2 = aux.getEnlace();
            while (aux2 != null) {
                if (aux2.getElemento().equals(elem)) {
                    aux.setEnlace(aux2.getEnlace());
                    this.longitud--;
                } else {
                    aux = aux.getEnlace();
                }
                aux2 = aux.getEnlace();
            }
        }
    }

    public String toString() {
        String cad = "";
        Nodo n = this.cabecera;
        while (n != null) {
            cad = cad + " " + n.getElemento();
            n = n.getEnlace();
        }
        return cad;
    }

}
