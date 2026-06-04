package Estructuras;

import Clases.SolicitudDeViaje;

public class MapeoAMuchos {

    /* Decidi utilizar una estructura mapeoAMuchos implementada con tabla Hash donde los dominios seran strings con la combinacion 
    de los codigos postales de la ciudad de origen y la ciudad de destino(Ej : 8300-5000) y el rango es una Lista con las solicitudes correspomdientes.
    Esto porque el acceso al hashmap(O(1)) es eficiente para la búsqueda de todas las solicitudes entre dos ciudades. */
    private NodoHashMapeoM[] tabla;
    private static int TAM = 60;
    private int cant = 0;

    public MapeoAMuchos() {
        this.tabla = new NodoHashMapeoM[TAM];
    }

    public boolean asociar(Object clave, Object valor) {
        boolean exito = false;
        int pos = Math.abs(clave.hashCode()) % TAM;
        NodoHashMapeoM aux = tabla[pos];
        while (aux != null && !aux.getDominio().equals(clave)) {
            aux = aux.getEnlace();
        }
        if (aux != null) {
            // El dominio ya existe
            aux.getRango().insertar(valor, aux.getRango().longitud() + 1);
            exito = true;
        } else {
            // El dominio no existe
            Lista lis = new Lista();
            lis.insertar(valor, 1);
            tabla[pos] = new NodoHashMapeoM(clave, lis, tabla[pos]);
            this.cant++;
            exito = true;
        }
        return exito;
    }

    public boolean desasociar(Object clave, Object valor) {
        boolean exito = false;
        int pos = Math.abs(clave.hashCode()) % TAM;
        NodoHashMapeoM aux = tabla[pos];
        while (aux != null && !aux.getDominio().equals(clave)) {
            aux = aux.getEnlace();
        }
        if (aux != null) {
            int i = aux.getRango().localizar(valor);
            if (aux.getRango().eliminar(i)) {
                exito = true;
                cant--;
            }
        }
        return exito;
    }

    public Lista obtenerValor(Object clave) {
        Lista lis = new Lista();
        NodoHashMapeoM aux = obtenerNodoConDominio(clave);
        if (aux != null) {
            lis = aux.getRango().clone();
        }
        return lis;
    }

    public Lista obtenerConjuntoDominio() {
        Lista lis = new Lista();
        for (int i = 0; i < TAM; i++) {
            NodoHashMapeoM n = tabla[i];
            while (n != null) {
                lis.insertar(n.getDominio(), lis.longitud() + 1);
                n = n.getEnlace();
            }
        }
        return lis;
    }

    private NodoHashMapeoM obtenerNodoConDominio(Object clave) {
        int pos = Math.abs(clave.hashCode()) % TAM;
        NodoHashMapeoM aux = tabla[pos];
        while (aux != null && !aux.getDominio().equals(clave)) {
            aux = aux.getEnlace();
        }
        return aux;
    }

    public SolicitudDeViaje obtenerSolicitud(int co, int cd, int num) {
        Lista solicitudes = obtenerValor(co + "-" + cd);
        SolicitudDeViaje solicitud = null;
        boolean encontrado = false;
        int pos = 1;
        while (!encontrado && pos <= solicitudes.longitud()) {
            solicitud = (SolicitudDeViaje) solicitudes.recuperar(pos);
            if (solicitud.getNumero() == num) {
                encontrado = true;
            } else {
                pos++;
            }
        }
        return solicitud;
    }

    private int buscarSolicitud(Lista lista, int num) {
        int pos = 1;
        boolean encontrado = false;
        if (lista != null) {
            SolicitudDeViaje solicitud = null;
            while (!encontrado && pos <= lista.longitud()) {
                solicitud = (SolicitudDeViaje) lista.recuperar(pos);
                if (solicitud.getNumero() == num) {
                    encontrado = true;
                } else {
                    pos++;
                }
            }
        }
        if (!encontrado) {
            pos = -1;
        }
        return pos;
    }

    public boolean eliminar(int co, int cd, int numSolicitud) {
        boolean exito = false;
        NodoHashMapeoM n = this.obtenerNodoConDominio(co + "-" + cd);
        if (n != null) {
            Lista solicitudes = n.getRango();
            int pos = buscarSolicitud(solicitudes, numSolicitud);
            if (solicitudes.eliminar(pos)) {
                exito = true;
                cant--;
            }
        }
        return exito;
    }

    public void eliminarSolicitudesPorCiudad(int codigoPostal) {
        String cod = String.valueOf(codigoPostal);
        Lista claves = obtenerConjuntoDominio();
        for (int i = 1; i <= claves.longitud(); i++) {
            String clave = (String) claves.recuperar(i);
            String[] partes = clave.split("-");
            String origen = partes[0];
            String destino = partes[1];
            if (origen.equals(cod) || destino.equals(cod)) {
                Lista lis = this.obtenerNodoConDominio(clave).getRango();
                cant = cant - lis.longitud();
                lis.vaciar();
            }
        }
    }

    public Lista listarPosiblesSolicitudesIntermedias(Lista camino, double espacioDisponible) {
        Lista posibles = new Lista();
        int i = 1;
        while (i <= camino.longitud() - 1) {
            int origen = (int) camino.recuperar(i);
            int j = i + 1;
            while (j <= camino.longitud()) {
                int destino = (int) camino.recuperar(j);
                Lista solicitudes = this.obtenerValor(origen + "-" + destino);
                int k = 1;
                while (k <= solicitudes.longitud()) {
                    SolicitudDeViaje s = (SolicitudDeViaje) solicitudes.recuperar(k);
                    if (s.getMetrosCubicos() <= espacioDisponible) {
                        posibles.insertar(s, posibles.longitud() + 1);
                    }
                    k++;
                }
                j++;
            }
            i++;
        }
        return posibles;
    }

    public boolean verificarCaminoPerfecto(Lista camino, double espacioDisponible) {
        boolean perfecto = true;
        int i = 1;
        while (i <= camino.longitud() - 1) {
            int origen = (int) camino.recuperar(i);
            int j = i + 1;
            boolean existeSolicitud = false;
            while (j <= camino.longitud() && !existeSolicitud) {
                int destino = (int) camino.recuperar(j);
                Lista solicitudes = this.obtenerValor(origen + "-" + destino);
                int k = 1;
                while (k <= solicitudes.longitud() && !existeSolicitud) {
                    SolicitudDeViaje s = (SolicitudDeViaje) solicitudes.recuperar(k);
                    if (s.getMetrosCubicos() <= espacioDisponible) {
                        existeSolicitud = true;
                    }
                    k++;
                }
                j++;
            }
            if (!existeSolicitud) {
                perfecto = false;
            }
            i++;
        }
        return perfecto;
    }

    public boolean esVacia() {
        return cant == 0;
    }

    @Override
    public String toString() {
        String cadena = "";
        Lista claves = obtenerConjuntoDominio();
        String clave;
        for (int i = 1; i <= claves.longitud(); i++) {
            clave = (String) claves.recuperar(i);
            cadena = cadena + "Ciudad de origen-destino: " + clave + "\n";
            Lista solicitudes = obtenerValor(clave);
            for (int j = 1; j <= solicitudes.longitud(); j++) {
                cadena = cadena + "  " + solicitudes.recuperar(j) + "\n";
            }
            cadena += "\n";
        }
        return cadena;
    }
}
