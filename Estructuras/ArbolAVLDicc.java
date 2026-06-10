package Estructuras;

public class ArbolAVLDicc {

    private NodoAVLDicc raiz;

    public ArbolAVLDicc() {
        this.raiz = null;
    }

    private int altura(NodoAVLDicc n) {

        int altura = -1;

        if (n != null) {
            altura = n.getAltura();
        }

        return altura;

    }

    private int balance(NodoAVLDicc n) {

        int balance = 0;

        if (n != null) {
            balance = altura(n.getIzquierdo()) - altura(n.getDerecho());
        }

        return balance;

    }

    private NodoAVLDicc balancear(NodoAVLDicc n) {

        int balance = balance(n);

        if (balance > 1) { // Desbalance hacia la izquierda

            if (balance(n.getIzquierdo()) >= 0) {
                // Caso de rotacion simple a la Derecha

                n = rotarDerecha(n);

            } else {
                // Caso de rotacion doble Izquierda-Derecha

                n.setIzquierdo(rotarIzquierda(n.getIzquierdo()));
                n = rotarDerecha(n);

            }

        } else if (balance < -1) { // Desbalance hacia la derecha
            // Caso de rotacion simple a la izquierda

            if (balance(n.getDerecho()) <= 0) {
                // Caso de rotacion doble Derecha-Izquierda
                n = rotarIzquierda(n);

            } else {

                n.setDerecho(rotarDerecha(n.getDerecho()));
                n = rotarIzquierda(n);

            }

        }

        return n;

    }

    private NodoAVLDicc rotarIzquierda(NodoAVLDicc r) {

        NodoAVLDicc h = r.getDerecho();
        NodoAVLDicc temp = h.getIzquierdo();

        h.setIzquierdo(r);
        r.setDerecho(temp);

        r.recalcularAltura();
        h.recalcularAltura();

        return h;

    }

    private NodoAVLDicc rotarDerecha(NodoAVLDicc r) {

        NodoAVLDicc h = r.getIzquierdo();
        NodoAVLDicc temp = h.getDerecho();

        h.setDerecho(r);
        r.setIzquierdo(temp);

        r.recalcularAltura();
        h.recalcularAltura();

        return h;

    }

    public boolean insertar(Comparable clave, Object elemento) {
        /*Recibe un elemento y lo agrega en el árbol de manera ordenada. Si el elemento ya se encuentra
        en el árbol no se realiza la inserción. Devuelve verdadero si el elemento se agrega a la estructura y
        falso en caso contrario.*/
        boolean exito = false;
        Object[] ret = new Object[2];
        ret = insertarAux(this.raiz, clave, elemento);
        exito = (boolean) ret[0];
        if (exito) { //En la posicion 0 retorna el valor booleano
            this.raiz = (NodoAVLDicc) ret[1]; //En la posicion 1 retorna el nuevo nodo
        }
        return exito;

    }

    private Object[] insertarAux(NodoAVLDicc n, Comparable clave, Object elemento) {

        Object[] ret = new Object[2];

        if (n == null) {
            ret[0] = true;
            ret[1] = new NodoAVLDicc(clave, elemento, null, null);

        } else {

            if (clave.compareTo(n.getClave()) == 0) {
                ret[0] = false; //la clave esta repetida
            } else if (clave.compareTo(n.getClave()) < 0) {

                ret = insertarAux(n.getIzquierdo(), clave, elemento);
                if ((boolean) ret[0]) {
                    n.setIzquierdo((NodoAVLDicc) ret[1]);
                }
            } else {
                ret = insertarAux(n.getDerecho(), clave, elemento);
                if ((boolean) ret[0]) {
                    n.setDerecho((NodoAVLDicc) ret[1]);
                }
            }

            if ((boolean) ret[0]) {
                n.recalcularAltura();
                n = balancear(n);
                ret[1] = n;
            }

        }

        return ret;

    }

    public boolean eliminar(Comparable clave) {

        boolean exito = false;
        Object[] ret = new Object[2];
        ret = eliminarAux(this.raiz, clave);
        exito = (boolean) ret[0];
        if (exito) { //En la posicion 0 retorna el valor booleano
            this.raiz = (NodoAVLDicc) ret[1]; //En la posicion 1 retorna el nuevo nodo
        }

        return exito;

    }

    private Object[] eliminarAux(NodoAVLDicc n, Comparable clave) { //devolver un arreglo con boolean

        Object[] ret = new Object[2];

        if (n != null) {

            if (clave.compareTo(n.getClave()) < 0) {

                ret = eliminarAux(n.getIzquierdo(), clave);
                if ((boolean) ret[0]) {
                    n.setIzquierdo((NodoAVLDicc) ret[1]);

                    n.recalcularAltura();
                    n = balancear(n);
                    ret[1] = n;

                }

            } else if (clave.compareTo(n.getClave()) > 0) {

                ret = eliminarAux(n.getDerecho(), clave);
                if ((boolean) ret[0]) {
                    n.setDerecho((NodoAVLDicc) ret[1]);

                    n.recalcularAltura();
                    n = balancear(n);
                    ret[1] = n;

                }

            } else { //Se encuentra el nodo buscado para eliminarlo

                ret[0] = true;

                if (n.getIzquierdo() == null && n.getDerecho() == null) { // CASO 1: es hoja

                    ret[1] = null;

                } else if (n.getIzquierdo() == null) { // CASO 2: un hijo derecho

                    ret[1] = n.getDerecho();

                } else if (n.getDerecho() == null) { // CASO 2: un hijo izquierdo

                    ret[1] = n.getIzquierdo();

                } else { // CASO 3: tiene dos hijos

                    NodoAVLDicc candidato = n.getIzquierdo();

                    while (candidato.getDerecho() != null) {
                        candidato = candidato.getDerecho();
                    }

                    n.setClave(candidato.getClave());
                    n.setElemento(candidato.getElemento());

                    ret = eliminarAux(n.getIzquierdo(), candidato.getClave());

                    n.setIzquierdo((NodoAVLDicc) ret[1]);

                    n.recalcularAltura();
                    n = balancear(n);
                    ret[1] = n;

                }

            }

        } else {
            ret[0] = false;
        }

        return ret;

    }

    public boolean existeClave(Comparable clave) {
        return existeClaveAux(this.raiz, clave);
    }

    private boolean existeClaveAux(NodoAVLDicc n, Comparable clave) {

        boolean per = false;

        if (n != null) {

            if (clave.compareTo(n.getClave()) == 0) {

                per = true;

            } else {

                if (clave.compareTo(n.getClave()) < 0) {
                    per = existeClaveAux(n.getIzquierdo(), clave);
                } else {
                    per = existeClaveAux(n.getDerecho(), clave);
                }

            }

        }

        return per;

    }

    public Object obtenerDato(Comparable clave) {
        return obtenerDatoAux(this.raiz, clave);
    }

    private Object obtenerDatoAux(NodoAVLDicc n, Comparable clave) {

        Object dato = null;

        if (n != null) {

            if (clave.compareTo(n.getClave()) == 0) {

                dato = n.getElemento();

            } else if (clave.compareTo(n.getClave()) < 0) {

                dato = obtenerDatoAux(n.getIzquierdo(), clave);

            } else {

                dato = obtenerDatoAux(n.getDerecho(), clave);

            }

        }

        return dato;

    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public Lista listarElementos() {

        Lista lis = new Lista();
        listarAux(this.raiz, lis);

        return lis;

    }

    private void listarAux(NodoAVLDicc nodo, Lista lis) {

        if (nodo != null) {
            listarAux(nodo.getIzquierdo(), lis);
            lis.insertar(nodo.getElemento(), lis.longitud() + 1);
            listarAux(nodo.getDerecho(), lis);
        }

    }

    public Lista listarClaves() {

        Lista lis = new Lista();
        listarClavesAux(this.raiz, lis);

        return lis;

    }

    private void listarClavesAux(NodoAVLDicc nodo, Lista lis) {

        if (nodo != null) {
            listarClavesAux(nodo.getIzquierdo(), lis);
            lis.insertar(nodo.getClave(), lis.longitud() + 1);
            listarClavesAux(nodo.getDerecho(), lis);
        }

    }

    public Lista listarRango(Comparable claveMin, Comparable claveMax) {

        Lista lis = new Lista();
        listarRangoAux(this.raiz, lis, claveMin, claveMax);

        return lis;

    }

    private void listarRangoAux(NodoAVLDicc nodo, Lista lis, Comparable min, Comparable max) {

        if (nodo != null) {

            if (min.compareTo(nodo.getClave()) < 0) {
                listarRangoAux(nodo.getIzquierdo(), lis, min, max);
            }

            if (min.compareTo(nodo.getClave()) <= 0 && max.compareTo(nodo.getClave()) >= 0) {
                lis.insertar(nodo.getElemento(), lis.longitud() + 1);
            }

            if (max.compareTo(nodo.getClave()) > 0) {
                listarRangoAux(nodo.getDerecho(), lis, min, max);
            }

        }

    }

    @Override
    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoAVLDicc n) {

        String cadena = "";

        if (n != null) {

            cadena += "Nodo: " + n.getClave() + " (Altura: " + n.getAltura() + ")\n";

            if (n.getIzquierdo() != null) {

                cadena += "  HI: " + n.getIzquierdo().getClave() + "\n";

            } else {

                cadena += "  HI: -\n";

            }

            if (n.getDerecho() != null) {

                cadena += "  HD: " + n.getDerecho().getClave() + "\n";

            } else {

                cadena += "  HD: -\n";

            }

            cadena += "\n";
            cadena += toStringAux(n.getIzquierdo());
            cadena += toStringAux(n.getDerecho());
        }

        return cadena;

    }

}
