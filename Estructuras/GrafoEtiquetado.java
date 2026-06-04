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
        while (((nOrigen == null) || (nDestino == null)) && (aux != null)) {
            if (aux.getElem().equals(origen)) {
                nOrigen = aux;
            }
            if (aux.getElem().equals(destino)) {
                nDestino = aux;
            }
            aux = aux.getSigVert();
        }
        if (nOrigen != null && nDestino != null) {
            if (verificarArco(nOrigen, nDestino)) {
                insertarAdyacente(nOrigen, nDestino, etiqueta);
                insertarAdyacente(nDestino, nOrigen, etiqueta);
                exito = true;
            }
        }
        return exito;
    }

    private boolean verificarArco(NodoVert origen, NodoVert destino) {
        //verifica que el arco no exista para la la insercion
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
        while (((nOrigen == null) || (nDestino == null)) && (aux != null)) {
            if (aux.getElem().equals(origen)) {
                nOrigen = aux;
            }
            if (aux.getElem().equals(destino)) {
                nDestino = aux;
            }
            aux = aux.getSigVert();
        }
        if (nOrigen != null && nDestino != null) {
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

    public boolean existeCamino(Object origen, Object destino) {
        boolean existe = false;
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
                visitados.insertar(n.getElem(), visitados.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!existe && ady != null) {
                    NodoVert v = ady.getVertice();
                    if (visitados.localizar(v.getElem()) < 0) {
                        existe = existeCaminoAux(v, destino, visitados);
                    }
                    ady = ady.getSigAdy();
                }
            }
        }
        return existe;
    }

    public Lista obtenerCaminosSinRepetidos(Object origen, Object destino) {
        Lista caminos = new Lista();
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
                obtenerCaminosSinRepetidosAux(nOrigen, destino, caminos, new Lista(), new Lista());
            }
        }
        return caminos;
    }

    private void obtenerCaminosSinRepetidosAux(NodoVert n, Object destino, Lista caminos, Lista camino, Lista visitados) {
        if (n != null) {
            camino.insertar(n.getElem(), camino.longitud() + 1);
            visitados.insertar(n.getElem(), visitados.longitud() + 1);
            if (n.getElem().equals(destino)) {
                caminos.insertar(camino.clone(), caminos.longitud() + 1);
            } else {
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    NodoVert v = ady.getVertice();
                    if (visitados.localizar(v.getElem()) < 0) {
                        obtenerCaminosSinRepetidosAux(v, destino, caminos, camino, visitados);
                    }
                    ady = ady.getSigAdy();
                }
            }
            camino.eliminar(camino.longitud());
            visitados.eliminar(visitados.localizar(n.getElem()));
        }
    }

    public Lista listarEnProfundidad() {
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
                visitados.insertar(n.getElem(), visitados.longitud() + 1);
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
                visitados.eliminar(visitados.localizar(n.getElem()));
            }
        }
        return distanciaMin;
    }

    public boolean verificarCamino(Lista lis) {
        //Dada una lista verifica si es un camino existente.
        boolean exito = true;
        NodoVert n;
        Object elem;
        elem = lis.recuperar(1);
        n = this.inicio;
        while (elem != null && n != null && !n.getElem().equals(elem)) {
            n = n.getSigVert();
        }
        if (n != null) {
            int i = 2;
            NodoAdy ady;
            elem = lis.recuperar(i);
            while (elem != null && exito && n != null) {
                ady = n.getPrimerAdy();
                boolean encontrado = false;
                while (ady != null && !encontrado) {
                    NodoVert v = ady.getVertice();
                    if (v.getElem().equals(elem)) {
                        encontrado = true;
                        n = v;
                        i++;
                        elem = lis.recuperar(i);
                    } else {
                        ady = ady.getSigAdy();
                    }
                }
                if (!encontrado) {
                    exito = false;
                }
            }
        } else {
            exito = false;
        }
        return exito;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
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
                caminoMasCortoAux(nOrigen, destino, new Lista(), 0, -1, new Lista(), caminoMasCorto);
            }
        }
        return caminoMasCorto;
    }

    private double caminoMasCortoAux(NodoVert n, Object destino, Lista visitados, double distancia, double distanciaMin, Lista caminoActual, Lista caminoMasCorto) {
        if (distanciaMin >= 0 && distancia >= distanciaMin) {
            return distanciaMin;
        }
        if (n != null) {
            caminoActual.insertar(n.getElem(), caminoActual.longitud() + 1);
            if (n.getElem().equals(destino)) {
                if (distanciaMin < 0 || distancia < distanciaMin) {
                    distanciaMin = distancia;
                    copiarLista(caminoActual, caminoMasCorto);
                }
            } else {
                visitados.insertar(n.getElem(), visitados.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    NodoVert v = ady.getVertice();
                    if (visitados.localizar(v.getElem()) < 0) {
                        double dist = caminoMasCortoAux(v, destino, visitados, distancia + ady.getEtiqueta(), distanciaMin, caminoActual, caminoMasCorto);
                        if (dist >= 0) {
                            if (distanciaMin < 0 || dist < distanciaMin) {
                                distanciaMin = dist;
                            }
                        }
                    }

                    ady = ady.getSigAdy();
                }
                visitados.eliminar(visitados.localizar(n.getElem()));
            }
            caminoActual.eliminar(caminoActual.longitud());
        }
        return distanciaMin;
    }

    private void copiarLista(Lista origen, Lista destino) {
        destino.vaciar();
        for (int i = 1; i <= origen.longitud(); i++) {
            destino.insertar(origen.recuperar(i), destino.longitud() + 1);
        }
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
