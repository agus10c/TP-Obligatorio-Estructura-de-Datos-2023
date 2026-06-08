package Estructuras;

public class GrafoEtiquetado {

    private NodoVert inicio;

    public GrafoEtiquetado() {
        this.inicio = null;
    }

    private NodoVert ubicarVertice(Object buscado) {
        //Busca hasta encontrar el vertice buscado en la lista de vertice. 
        //Devuelve null si no lo encuentra.

        NodoVert aux = this.inicio;

        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVert();
        }

        return aux;

    }

    public boolean insertarVertice(Object nuevoVertice) {
        /* Dado un elemento de TipoVertice se lo agrega a la estructura controlando que no se inserten
        vértices repetidos. Si puede realizar la inserción devuelve verdadero, en caso contrario devuelve falso.*/

        boolean exito;

        NodoVert aux = this.ubicarVertice(nuevoVertice);

        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        } else {
            exito = false;
        }

        return exito;

    }

    public boolean eliminarVertice(Object vertice) {
        return eliminarVerticeAux(this.inicio, null, vertice);
    }

    private boolean eliminarVerticeAux(NodoVert nVertice, NodoVert nVerticeAnterior, Object vertice) {
        //Elimina el nodo vertice con el objeto correspondiente y elimina todos los arcos que contengan ese vertice. Retorna true si se completa la eliminacion exitosamente 
        boolean exito = false;

        if (nVertice != null) {

            if (nVertice.getElem().equals(vertice)) {

                eliminarArcos(nVertice);

                if (nVerticeAnterior == null) {

                    this.inicio = nVertice.getSigVert();

                } else {

                    nVerticeAnterior.setSigVert(nVertice.getSigVert());

                }

                exito = true;

            } else {

                exito = eliminarVerticeAux(nVertice.getSigVert(), nVertice, vertice);

            }

        }

        return exito;

    }

    public boolean insertarArco(Object origen, Object destino, Double etiqueta) {
        /* Dados dos elementos de TipoVertice (origen y destino) agrega el arco en la estructura, sólo si
        ambos vértices ya existen en el grafo. Si puede realizar la inserción devuelve verdadero, en caso
        contrario devuelve falso.*/

        boolean exito = false;
        NodoVert aux = this.inicio;
        NodoVert nOrigen = null;
        NodoVert nDestino = null;

        while (((nOrigen == null) || (nDestino == null)) && (aux != null)) { //Se buscan los nodos que tengan el elemento de origen y destino entre los vertices

            if (aux.getElem().equals(origen)) {
                nOrigen = aux;
            }

            if (aux.getElem().equals(destino)) {
                nDestino = aux;
            }

            aux = aux.getSigVert();

        }

        if (nOrigen != null && nDestino != null) { // verifica si se encontraron ambos nodos(origen y destino)

            if (verificarArco(nOrigen, nDestino)) { //Verifica que el arco no exista
                insertarAdyacente(nOrigen, nDestino, etiqueta);
                insertarAdyacente(nDestino, nOrigen, etiqueta);
                exito = true;
            }

        }

        return exito;

    }

    private boolean verificarArco(NodoVert origen, NodoVert destino) {
        //verifica que el arco no exista para la la insercion de un nuevo

        boolean exito = true;
        NodoAdy ady = origen.getPrimerAdy();

        while (ady != null) {

            if (ady.getVertice() == destino) {
                exito = false;
            }

            ady = ady.getSigAdy();

        }

        return exito;
    }

    private void insertarAdyacente(NodoVert n, NodoVert nEnlace, Double etiq) {
        //Inserta el nodo nVertice en la lista de adyacentes del nodo n

        if (n != null) {

            if (n.getPrimerAdy() == null) {
                n.setPrimerAdy(new NodoAdy(nEnlace, null, etiq));
            } else {
                insertarAdyacenteAux(n.getPrimerAdy(), nEnlace, etiq);
            }

        }

    }

    private void insertarAdyacenteAux(NodoAdy nAdyacente, NodoVert nEnlace, Double etiq) {
        //Modulo recursivo para insertar el nodo nVertice en la lista de adyacentes del nodo n

        if (nAdyacente != null) {

            if (nAdyacente.getSigAdy() == null) {
                nAdyacente.setSigAdy(new NodoAdy(nEnlace, null, etiq));
            } else {
                insertarAdyacenteAux(nAdyacente.getSigAdy(), nEnlace, etiq);
            }

        }

    }

    private void eliminarArcos(NodoVert n) {
        //Modulo para eliminar los arcos del nodo "n"

        if (n != null) {

            while (n.getPrimerAdy() != null) {
                NodoAdy nAdyacente = n.getPrimerAdy();
                eliminarAdyacente(nAdyacente.getVertice(), n);
                n.setPrimerAdy(nAdyacente.getSigAdy());
            }

        }

    }

    public boolean eliminarArco(Object origen, Object destino) {
        /* Dados dos elementos de TipoVertice (origen y destino) se quita de la estructura el arco que une
        ambos vértices. Si el arco existe y se puede realizar la eliminación con éxito devuelve verdadero, en
        caso contrario devuelve falso.*/

        boolean exito = false;
        NodoVert aux = this.inicio;
        NodoVert nOrigen = null;
        NodoVert nDestino = null;

        while (((nOrigen == null) || (nDestino == null)) && (aux != null)) { //Se buscan los nodos que tengan el elemento de origen y destino entre los vertices

            if (aux.getElem().equals(origen)) {
                nOrigen = aux;
            }

            if (aux.getElem().equals(destino)) {
                nDestino = aux;
            }

            aux = aux.getSigVert();

        }

        if (nOrigen != null && nDestino != null) { // verifica si se encontraron ambos nodos(origen y destino)

            exito = eliminarAdyacente(nOrigen, nDestino);

            if (exito) {
                exito = eliminarAdyacente(nDestino, nOrigen);
            }

        }

        return exito;

    }

    private boolean eliminarAdyacente(NodoVert origen, NodoVert destino) {
        //Confirma la eliminacion del nodo adyacente desitno de la lista de adyacentes del nodo origen

        boolean exito = false;

        if (origen != null) {

            NodoAdy nAdy = origen.getPrimerAdy();

            if (nAdy != null) {

                if (nAdy.getVertice() == destino) {

                    origen.setPrimerAdy(nAdy.getSigAdy());
                    exito = true;

                } else {

                    NodoAdy aux = nAdy.getSigAdy();

                    while (aux != null && !exito) {

                        if (aux.getVertice() == destino) {

                            nAdy.setSigAdy(aux.getSigAdy());
                            exito = true;

                        } else {

                            nAdy = aux;
                            aux = aux.getSigAdy();

                        }

                    }

                }

            }

        }

        return exito;

    }

    public boolean existeVertice(Object buscado) {
        //verifica si existe un vertice con el elemento ingresado por parametro

        boolean exito = false;
        NodoVert aux = this.inicio;

        while (aux != null && !exito) {

            if (aux.getElem().equals(buscado)) {
                exito = true;
            } else {
                aux = aux.getSigVert();
            }

        }

        return exito;

    }

    public boolean existeArco(Object origen, Object destino) {
        //Verfica que exista el arco que va desde el nodo que contiene el elemento origen al nodo que contiene el elemento destino
        boolean exito = false;

        NodoVert nVert = ubicarVertice(origen);

        if (nVert != null) {

            NodoAdy nAdy = nVert.getPrimerAdy();

            while (nAdy != null && !exito) {

                if (nAdy.getVertice().getElem().equals(destino)) {
                    exito = true;
                } else {
                    nAdy = nAdy.getSigAdy();
                }

            }

        }

        return exito;

    }

    public boolean esVacio() {
        return this.inicio == null;
    }

    public Lista listarEnProfundidad() {
        //Devuelve una lista con los elementos del grafo recorriendolo en profundidad

        Lista visitados = new Lista();
        NodoVert aux = this.inicio;

        while (aux != null) {

            if (visitados.localizar(aux.getElem()) < 0) {
                listarEnProfundidadAux(aux, visitados);
            }

            aux = aux.getSigVert();

        }

        return visitados;

    }

    private void listarEnProfundidadAux(NodoVert n, Lista visitados) {

        if (n != null) {

            visitados.insertar(n.getElem(), visitados.longitud() + 1);
            NodoAdy ady = n.getPrimerAdy();

            while (ady != null) {

                if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidadAux(ady.getVertice(), visitados);
                }

                ady = ady.getSigAdy();

            }

        }

    }

    public Lista listarEnAnchura() {
        //Devuelve una lista con los elementos del grafo recorriendolo en anchura

        Lista visitados = new Lista();
        NodoVert aux = this.inicio;

        while (aux != null) {

            if (visitados.localizar(aux.getElem()) < 0) {
                listarEnAnchuraAux(aux, visitados);
            }

            aux = aux.getSigVert();

        }

        return visitados;

    }

    private void listarEnAnchuraAux(NodoVert n, Lista visitados) {

        if (n != null) {

            Cola q = new Cola();
            visitados.insertar(n.getElem(), visitados.longitud() + 1);
            q.poner(n);

            while (!q.esVacia()) {

                NodoVert aux = (NodoVert) q.obtenerFrente();
                q.sacar();
                NodoAdy ady = aux.getPrimerAdy();

                while (ady != null) {

                    NodoVert v = ady.getVertice();

                    if (visitados.localizar(v.getElem()) < 0) {
                        visitados.insertar(v.getElem(), visitados.longitud() + 1);
                        q.poner(v);
                    }

                    ady = ady.getSigAdy();

                }

            }

        }

    }

    public boolean existeCamino(Object origen, Object destino) {
        //Devuelve true si exite un camino desde el verticce origen al vertice destino

        boolean existe = false;

        if (!origen.equals(destino)) {

            NodoVert nOrigen, nDestino, aux;
            nOrigen = null;
            nDestino = null;
            aux = this.inicio;

            while ((nOrigen == null || nDestino == null) && aux != null) { //Se buscan los nodos que tengan el elemento de origen y destino entre los vertices

                if (aux.getElem().equals(origen)) {
                    nOrigen = aux;
                } else if (aux.getElem().equals(destino)) {
                    nDestino = aux;
                }

                aux = aux.getSigVert();

            }

            if (nOrigen != null && nDestino != null) { // verifica si se encontraron ambos nodos(origen y destino)
                existe = existeCaminoAux(nOrigen, destino, new Lista());
            }

        }

        return existe;

    }

    private boolean existeCaminoAux(NodoVert n, Object destino, Lista visitados) {

        boolean existe = false;

        if (n != null) {

            if (n.getElem().equals(destino)) {

                existe = true;

            } else {
                int pos = visitados.longitud() + 1;
                visitados.insertar(n.getElem(), pos);
                NodoAdy ady = n.getPrimerAdy();

                while (!existe && ady != null) {

                    NodoVert v = ady.getVertice();

                    if (visitados.localizar(v.getElem()) < 0) {

                        existe = existeCaminoAux(v, destino, visitados);

                    }

                    ady = ady.getSigAdy();

                }

                visitados.eliminar(pos);
            }

        }

        return existe;

    }

    public Lista CaminoMasCorto(Object origen, Object destino) {
        //Devuelve el camino que va desde el vertice origen al vertice destino que pase por menos vertices
        Lista caminoMasCorto = new Lista();

        if (!origen.equals(destino)) {

            NodoVert nOrigen, nDestino, aux;
            nOrigen = null;
            nDestino = null;
            aux = this.inicio;

            while ((nOrigen == null || nDestino == null) && aux != null) {

                if (aux.getElem().equals(origen)) {
                    nOrigen = aux;
                } else if (aux.getElem().equals(destino)) {
                    nDestino = aux;
                }

                aux = aux.getSigVert();

            }

            if (nOrigen != null && nDestino != null) {
                caminoMasCortoAux(nOrigen, nDestino, new Lista(), new Lista(), caminoMasCorto);
            }

        }

        return caminoMasCorto;
    }

    private void caminoMasCortoAux(NodoVert n, NodoVert destino, Lista visitados, Lista caminoActual, Lista caminoMasCorto) {

        if (n != null) {
            caminoActual.insertar(n.getElem(), caminoActual.longitud() + 1);

            if (!(caminoMasCorto.longitud() > 0 && caminoActual.longitud() >= caminoMasCorto.longitud())) {
                // si n no es nulo y el camino mas corto tiene una longitud mayor a 0 y el camino actual todavia no es mayor
                if (n == destino) {

                    if (caminoMasCorto.esVacia() || caminoActual.longitud() < caminoMasCorto.longitud()) {
                        copiarLista(caminoActual, caminoMasCorto);
                    }

                } else {
                    int pos = visitados.longitud() + 1;
                    visitados.insertar(n.getElem(), pos);

                    NodoAdy ady = n.getPrimerAdy();

                    while (ady != null) {

                        NodoVert v = ady.getVertice();

                        if (visitados.localizar(v.getElem()) < 0) {

                            caminoMasCortoAux(v, destino, visitados, caminoActual, caminoMasCorto);
                        }

                        ady = ady.getSigAdy();
                    }

                    visitados.eliminar(pos);
                }
            }

            caminoActual.eliminar(caminoActual.longitud());
        }
    }

    private void copiarLista(Lista origen, Lista destino) {

        destino.vaciar();

        for (int i = 1; i <= origen.longitud(); i++) {

            destino.insertar(origen.recuperar(i), destino.longitud() + 1);

        }

    }

    public Double recorridoMin(Object origen, Object destino) {

        double r = -1;

        if (!origen.equals(destino)) {

            NodoVert nOrigen, nDestino, aux;
            nOrigen = null;
            nDestino = null;
            aux = this.inicio;

            while ((nOrigen == null || nDestino == null) && aux != null) {

                if (aux.getElem().equals(origen)) {
                    nOrigen = aux;
                } else if (aux.getElem().equals(destino)) {
                    nDestino = aux;
                }

                aux = aux.getSigVert();

            }

            if (nOrigen != null && nDestino != null) {
                r = recorridoMinAux(nOrigen, destino, new Lista(), 0);
            }

        }

        return r;

    }

    private double recorridoMinAux(NodoVert n, Object destino, Lista visitados, double distancia) {

        double distanciaMin = -1;

        if (n != null) {

            if (n.getElem().equals(destino)) {

                distanciaMin = distancia;

            } else {

                int pos = visitados.longitud() + 1;
                visitados.insertar(n.getElem(), pos);
                NodoAdy ady = n.getPrimerAdy();

                while (ady != null) {

                    NodoVert v = ady.getVertice();

                    if (visitados.localizar(v.getElem()) < 0) {

                        double dist = recorridoMinAux(v, destino, visitados, distancia + ady.getEtiqueta());

                        if (dist >= 0) {

                            if (distanciaMin < 0 || dist < distanciaMin) {
                                distanciaMin = dist;
                            }

                        }

                    }

                    ady = ady.getSigAdy();

                }

                visitados.eliminar(pos);

            }

        }

        return distanciaMin;

    }

    public Lista obtenerCaminosConIntermedio(Object origen, Object intermedio, Object destino) {
        /*Devuelve una lista con todos los caminos que van desde el vertice con
        el elemeto origen hasta el vertice con el elemento destino y que pasen por el vertice con el elemento intermedio,
        sin pasar por el mismo vertice mas de una vez  */

        Lista caminos = new Lista();

        if (!origen.equals(destino)) {

            NodoVert nOrigen, nDestino, nIntermedio, aux;
            nOrigen = null;
            nDestino = null;
            nIntermedio = null;
            aux = this.inicio;

            while ((nOrigen == null || nDestino == null || nIntermedio == null) && aux != null) {

                if (aux.getElem().equals(origen)) {

                    nOrigen = aux;

                } else if (aux.getElem().equals(destino)) {

                    nDestino = aux;

                } else if (aux.getElem().equals(intermedio)) {
                    nIntermedio = aux;
                }

                aux = aux.getSigVert();

            }

            if (nOrigen != null && nDestino != null && nIntermedio != null) {

                obtenerCaminosConIntermedioAux(nOrigen, nIntermedio, nDestino, caminos, new Lista(), new Lista(), false);

            }

        }

        return caminos;

    }

    private void obtenerCaminosConIntermedioAux(NodoVert n, NodoVert intermedio, NodoVert destino, Lista caminos, Lista camino, Lista visitados, boolean paso) {
        //camino: es el camino actual que estoy recorriendo de manera recursiva
        //caminos son todos los caminos que van desde el origen al destino pasando por el vertice intermedio sin repetir vertices
        //paso es un booleano que tiene valor true si se paso por el nodo intermedio
        if (n != null) {

            int posCamino = camino.longitud() + 1;
            int posVisitados = visitados.longitud() + 1;

            camino.insertar(n.getElem(), posCamino);
            visitados.insertar(n.getElem(), posVisitados);

            if (n == intermedio) {
                paso = true;
            }

            if (n == destino) {
                if (paso) {
                    caminos.insertar(camino.clone(), caminos.longitud() + 1);
                }
            } else {

                NodoAdy ady = n.getPrimerAdy();

                while (ady != null) {

                    NodoVert v = ady.getVertice();

                    if (visitados.localizar(v.getElem()) < 0) {
                        obtenerCaminosConIntermedioAux(v, intermedio, destino, caminos, camino, visitados, paso);
                    }

                    ady = ady.getSigAdy();

                }

            }

            camino.eliminar(posCamino);
            visitados.eliminar(posVisitados);

        }

    }

    public boolean verificarCamino(Lista lis) {

        boolean exito = true;

        if (lis.esVacia()) {
            exito = false;
        } else {

            NodoVert n = this.inicio;
            Object elem = lis.recuperar(1);

            while (n != null && !n.getElem().equals(elem)) {
                n = n.getSigVert();
            }

            if (n == null) {
                exito = false;
            }

            int i = 2;

            while (exito && i <= lis.longitud()) {

                elem = lis.recuperar(i);

                NodoAdy ady = n.getPrimerAdy();
                boolean encontrado = false;

                while (ady != null && !encontrado) {
                    
                    NodoVert v = ady.getVertice();
                    if (v.getElem().equals(elem)) {

                        encontrado = true;
                        n = v;

                    } else {

                        ady = ady.getSigAdy();

                    }

                }

                if (encontrado) {
                    i++;
                } else {
                    exito = false;
                }

            }

        }

        return exito;
    }

    public Lista caminoConMenorDistancia(Object origen, Object destino) {
        //Devuelve el camino cuya suma de etiquetas sea menor al resto de caminos de origen a destino
        Lista caminoMasCorto = new Lista();

        if (!origen.equals(destino)) {

            NodoVert nOrigen, nDestino, aux;
            nOrigen = null;
            nDestino = null;
            aux = this.inicio;

            while ((nOrigen == null || nDestino == null) && aux != null) {

                if (aux.getElem().equals(origen)) {
                    nOrigen = aux;
                } else if (aux.getElem().equals(destino)) {
                    nDestino = aux;
                }

                aux = aux.getSigVert();

            }

            if (nOrigen != null && nDestino != null) {
                caminoConMenorDistanciaAux(nOrigen, destino, new Lista(), 0, -1, new Lista(), caminoMasCorto);
            }

        }

        return caminoMasCorto;

    }

    private double caminoConMenorDistanciaAux(NodoVert n, Object destino, Lista visitados, double distancia, double distanciaMin, Lista caminoActual, Lista caminoMasCorto) {

        if (!(distanciaMin >= 0 && distancia >= distanciaMin)) {

            if (n != null) {

                caminoActual.insertar(n.getElem(), caminoActual.longitud() + 1);

                if (n.getElem().equals(destino)) {

                    if (distanciaMin < 0 || distancia < distanciaMin) {
                        distanciaMin = distancia;
                        copiarLista(caminoActual, caminoMasCorto);
                    }

                } else {
                    int pos = visitados.longitud() + 1;
                    visitados.insertar(n.getElem(), pos);
                    NodoAdy ady = n.getPrimerAdy();

                    while (ady != null) {

                        NodoVert v = ady.getVertice();
                        if (visitados.localizar(v.getElem()) < 0) {

                            double dist = caminoConMenorDistanciaAux(v, destino, visitados, distancia + ady.getEtiqueta(), distanciaMin, caminoActual, caminoMasCorto);

                            if (dist >= 0) {

                                if (distanciaMin < 0 || dist < distanciaMin) {
                                    distanciaMin = dist;
                                }

                            }

                        }

                        ady = ady.getSigAdy();

                    }

                    visitados.eliminar(pos);

                }

                caminoActual.eliminar(caminoActual.longitud());

            }
        }
        return distanciaMin;

    }

    @Override
    public String toString() {

        String cadena = "";
        NodoVert vert = this.inicio;

        while (vert != null) {

            cadena += "Vertice: " + vert.getElem() + " -> ";
            NodoAdy ady = vert.getPrimerAdy();

            while (ady != null) {
                cadena += "[" + ady.getVertice().getElem() + " (Etiqueta: " + ady.getEtiqueta() + ")] ";
                ady = ady.getSigAdy();
            }

            cadena += "\n";
            vert = vert.getSigVert();

        }

        return cadena;

    }

}
