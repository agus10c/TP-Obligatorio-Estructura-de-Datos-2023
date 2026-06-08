package main;

import Clases.Ciudad;
import Clases.Cliente;
import Clases.IDCliente;
import Clases.Log;
import Clases.SolicitudDeViaje;
import Estructuras.ArbolAVLDicc;
import Estructuras.GrafoEtiquetado;
import Estructuras.Lista;
import Estructuras.MapeoAMuchos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class MudanzasCompartidas {

    private static Scanner lectura = new Scanner(System.in);
    private static ArbolAVLDicc ciudades = new ArbolAVLDicc();
    private static MapeoAMuchos solicitudes = new MapeoAMuchos();
    private static GrafoEtiquetado rutas = new GrafoEtiquetado();
    private static HashMap<IDCliente, Cliente> clientes = new HashMap<>();

    public static void menu() {
        //Menu princiapal
        System.out.println("");
        System.out.println("1 - Carga Inicial del sistema del archivo carga.txt");
        System.out.println("2 - ABM de ciudades");
        System.out.println("3 - ABM de la red de rutas");
        System.out.println("4 - ABM de clientes");
        System.out.println("5 - ABM de pedidos");
        System.out.println("6 - Consultar Cliente");
        System.out.println("7 - Consultar Ciudad");
        System.out.println("8 - Consultar Ciudad con prefijo");
        System.out.println("9 - Obtener el camino que llegue de A a B que pase por menos ciudades");
        System.out.println("10 - Obtener el camino que llegue de A a B de menor distancia en kilómetros");
        System.out.println("11 - Obtener todos los caminos posibles para llegar de A a B que pasen por una ciudad C dada sin pasar dos veces por la misma ciudad");
        System.out.println("12 - Verificar si es posible llegar de A a B recorriendo como máximo una cantidad X de kilómetros");
        System.out.println("13 - Dada una ciudad A y una ciudad B mostrar todos los pedidos y calcular cuánto espacio total hace falta en el camión");
        System.out.println("14 - Dada una ciudad A y una ciudad B verificar si sobra espacio en el camión y hacer un listado de posibles solicitudes a ciudades intermedias que se podrían aprovechar a cubrir ");
        System.out.println("considerando el camino más corto en kilómetros.");
        System.out.println("15 - Dada una lista de ciudades y una cantidad de metros cúbicos que corresponden a la capacidad del camión, verificar si es un camino perfecto");
        System.out.println("16 - Mostrar sistema");
        System.out.println("17 - Finalizar");
        System.out.println("Ingrese el numero de la opcion que desea realizar: ");
    }

    public static void main(String[] args) {

        Log.inicializar("src/main/log.txt");
        int op;
        boolean continuar = true;
        while (continuar) {  //Mientras no se ingrese un numero del 1 al 6 se sigue solicitando la opcion

            //muestro el menu de inicio
            menu();

            op = lectura.nextInt();

            lectura.nextLine(); //Para leer el ultimo enter ingresado

            if (1 <= op && op <= 17) {
                //Switch de operaciones a realizar segun la opcion numerica ingresada
                switch (op) {
                    case 1:
                        realizarCarga();
                        Log.escribir(estadoDelSistema());
                        break;
                    case 2:
                        ABMCiudades();
                        break;
                    case 3:
                        ABMRedDeRutas();
                        break;
                    case 4:
                        ABMClientes();
                        break;
                    case 5:
                        ABMPedidos();
                        break;
                    case 6:
                        consultarCliente();
                        break;
                    case 7:
                        consultarCiudad();
                        break;
                    case 8:
                        consultarCiudadesConPrefijo();
                        break;
                    case 9:
                        obtenerCaminoConMenosCiudades();
                        break;
                    case 10:
                        obtenerCaminoConMenorDistancia();
                        break;
                    case 11:
                        obtenerCaminosConIntermedio();
                        break;
                    case 12:
                        verificarRecorrido();
                        break;
                    case 13:
                        mostrarEspacioNecesarioYPedidos();
                        break;
                    case 14:
                        verificarSolicitudesACiudadesIntermedias();
                        break;
                    case 15:
                        verificarCaminoPerfecto();
                        break;
                    case 16:
                        System.out.println(estadoDelSistema());
                        ;
                        break;
                    case 17:
                        continuar = false;
                        break;

                }

            } else {
                System.out.println("Opcion no valida, porfavor ingrese un numero del menu de opciones \n");
            }

            if (op != 17) {
                System.out.println("Presione enter para continuar");
                lectura.nextLine();
            }

        }

        Log.escribir(estadoDelSistema()); //Al finalizar escribo el estado del sistema en el archivo log.txt
        Log.cerrar(); // cierro el Log

    }

    //METODOS DE CARGA 
    public static void realizarCarga() {
        //Realiza la carga de datos desde el archivo carga.txt 
        int c, s, r, p;
        c = 0;
        s = 0;
        r = 0;
        p = 0;
        String file = "src/main/carga.txt";

        //FileReader abre el archivo de texto para lectura de caracteres
        try ( BufferedReader entrada = new BufferedReader(new FileReader(file))) {
            //creo un bufer de entrada para leer el archivo de texto dentro de try para garantizar el cierre automático del archivo(como usar un finally)

            String linea;

            // Leer el archivo línea por línea
            while ((linea = entrada.readLine()) != null) {
                String[] pal = linea.split(";"); //Divide el string usando ; como separador almacenando cada palabra en una posicion del arreglo pal

                //Este switch  se fija  la posicion  0 del arreglo y verifica la letra que indica el tipo de carga a realizar
                switch (pal[0]) {
                    case "C":
                        if (cargarCiudad(pal[1], pal[2], pal[3])) {
                            c++;
                        }
                        break;
                    case "S":
                        if (cargarSolicitud(pal[1], pal[2], pal[3], pal[4], pal[5], pal[6], pal[7], pal[8], pal[9], pal[10])) {
                            s++;
                        }
                        break;
                    case "R":
                        if (cargarRuta(pal[1], pal[2], pal[3])) {
                            r++;
                        }
                        break;
                    case "P":
                        if (cargarCliente(pal[1], pal[2], pal[3], pal[4], pal[5], pal[6])) {
                            p++;
                        }
                        break;
                }
            }

            System.out.println("Carga Finalizada. Se cargaron: " + c + " Ciudades, " + s + " Solicitudes, " + r + " Rutas, " + p + " Clientes.");

            Log.escribir("Se realizo carga del archivo carga.txt. Se cargaron: " + c + " Ciudades, " + s + " Solicitudes, " + r + " Rutas, " + p + " Clientes.");

            entrada.close();

        } catch (IOException e) {

            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());

            Log.escribir("Error: Ocurrio un error al leer el archivo carga.txt " + e.getMessage());

        }

    }

    public static boolean cargarCiudad(String codPostal, String nomCiudad, String nomProvincia) {

        //Realiza la carga al sistema de una nueva ciudad
        boolean exito = false;

        int postalInt = Integer.parseInt(codPostal);

        //creo una nueva ciudad
        Ciudad c = new Ciudad(postalInt, nomCiudad, nomProvincia);

        //la inserto en el ArbolAVLDicc Ciudades
        if (ciudades.insertar(postalInt, c)) {
            //inserto la ciudad como un vertice en el grafo etiquetado de rutas
            if (rutas.insertarVertice(postalInt)) {
                exito = true;
            }
        }

        return exito;
    }

    public static boolean cargarSolicitud(String co, String cd, String fe, String td, String nd, String mc, String cb, String dr, String de, String p) {
        //Realiza la carga al sistema de una nueva solicitud

        String clave = co + "-" + cd;
        boolean exito = false;
        int codOrigen = Integer.parseInt(co);
        int codDestino = Integer.parseInt(cd);
        int numDocumento = Integer.parseInt(nd);
        int metrosCubicos = Integer.parseInt(mc);
        int cantBultos = Integer.parseInt(cb);
        String domRetiro = dr.toUpperCase();
        String domEntrega = de.toUpperCase();
        boolean pago;

        if (p.equals("T")) {
            pago = true;
        } else {
            pago = false;
        }

        IDCliente id = new IDCliente(td, numDocumento);

        if (clientes.containsKey(id)) { //verifico que el cliente este cargado al sistema

            SolicitudDeViaje solicitud = new SolicitudDeViaje(codOrigen, codDestino, fe, id, metrosCubicos, cantBultos, domRetiro, domEntrega, pago);

            if (rutas.existeCamino(codOrigen, codDestino)) { //verifico que exista un camino de rutas

                if (solicitudes.asociar(co + "-" + cd, solicitud)) { //Se guarda la solicitud en la estructura de  mapeoAMuchos con clave co-cd
                    exito = true;
                }

            } else {
                System.out.println("Error: Ruta no existente");
            }

        } else {
            System.out.println("Error: No existe cliente");
        }

        return exito;
    }

    public static boolean cargarRuta(String co, String cd, String d) {
        //Realiza la carga al sistema de una nueva solicitud

        boolean exito = false;
        int ciudadOrigen = Integer.parseInt(co);
        int ciudadDestino = Integer.parseInt(cd);
        Double distancia = Double.parseDouble(d);

        if (distancia < 0) {
            System.out.println("ERROR: la distancia no puede ser menor a cero");
        } else {
            if (rutas.insertarArco(ciudadOrigen, ciudadDestino, distancia)) {
                exito = true;
            }

        }

        return exito;

    }

    public static boolean cargarCliente(String td, String nd, String n, String a, String t, String e) {
        //Realiza la carga al sistema de un nuevo cliente

        boolean exito = false;
        int numDocumento = Integer.parseInt(nd);
        long numTelefono = Long.parseLong(t);

        Cliente cliente = new Cliente(td, numDocumento, n, a, numTelefono, e); //Creo un nuevo Cliente

        if (!clientes.containsKey(cliente.getId())) { //Verifica que el cliente no este este ya cargado
            clientes.put(cliente.getId(), cliente);
            exito = true;
        }

        return exito;

    }

    //ABM
    public static void ABMCiudades() {

        int op;
        boolean continuar = true;

        while (continuar) {

            System.out.println("");
            System.out.println("1 - Dar de alta una ciudad");
            System.out.println("2 - Dar de baja una ciudad ADVERTENCIA: Si se da de baja una ciudad se eliminaran todas las rutas y solicitudes que tengan asociada esa ciudad");
            System.out.println("3 - Modificar el nombre de la ciudad");
            System.out.println("4- Modificar el nombre de la provincia ");
            System.out.println("5 - volver");
            System.out.println("Ingrese el numero de la opcion que desea ejecutar: ");
            System.out.println("");
            op = lectura.nextInt();
            lectura.nextLine();

            if (1 <= op && op <= 5) {

                continuar = false;

                switch (op) {
                    case 1:
                        altaCiudad();
                        break;
                    case 2:
                        bajaCiudad();
                        break;
                    case 3:
                        modificarNombreCiudad();
                        break;
                    case 4:
                        modificarNombreProvincia();
                        break;
                    default:
                        break;
                }

            } else {
                System.out.println("Opcion no valida, porfavor ingrese un numero del menu de opciones \n");
            }

        }

    }

    public static void altaCiudad() {
        //Da de alta una Ciudad al sistema. Almacena la nueva ciudad en el ArbolDicc de Ciudades

        int codPostal;
        String nomCiudad, nomProvincia;
        System.out.println("Ingrese el codigo postal de la ciudad (numero entero)");
        codPostal = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese el nombre de la ciudad");
        nomCiudad = lectura.nextLine();

        System.out.println("Ingrese el nombre de la provincia");
        nomProvincia = lectura.nextLine();

        if (cargarCiudad(String.valueOf(codPostal), nomCiudad, nomProvincia)) {

            System.out.println("Se inserto la ciudad " + nomCiudad + " exitosamente");
            Log.escribir("Se inserto la ciudad " + nomCiudad + " exitosamente");

        } else {

            System.out.println("No se inserto la ciudad " + nomCiudad + " exitosamente");
            Log.escribir("No se inserto la ciudad " + nomCiudad + " exitosamente");

        }

    }

    public static void bajaCiudad() {

        if (!ciudades.esVacio()) {

            int cp;

            System.out.println("Ingrese el codigo postal de la ciudad de la ciudad que desea dar de baja");
            cp = lectura.nextInt();
            lectura.nextLine();

            if (ciudades.eliminar(cp)) {

                rutas.eliminarVertice(cp); //Elimino la ciudad del grafo de rutas
                solicitudes.eliminarSolicitudesPorCiudad(cp);//Elimino todas las solicitudes que tengan esa ciudad como origen o destino
                System.out.println("Exito al eliminar la ciudad con codigo postal: " + cp);
                Log.escribir("Exito al eliminar la ciudad con codigo postal: " + cp);

            } else {

                System.out.println("No se pudo eliminar la ciudad con codigo postal: " + cp);
                Log.escribir("No se pudo eliminar la ciudad con codigo postal: " + cp);

            }

        } else {

            System.out.println("No hay ninguna ciudad registrada");

        }

    }

    public static void modificarNombreCiudad() {

        if (!ciudades.esVacio()) {

            int cod;
            System.out.println("Ingrese el codigo postal de la ciudad que desea modificar");
            cod = lectura.nextInt();
            lectura.nextLine();
            Ciudad ciudad = (Ciudad) ciudades.obtenerDato(cod);

            if (ciudad != null) {

                String nuevoNombre;

                System.out.println("Ingrese un nuevo nombre de Ciudad");
                nuevoNombre = lectura.nextLine();

                ciudad.setNomCiudad(nuevoNombre);

                System.out.println("Se modiifico el nombre de la Ciudad correctamente");
                Log.escribir("Se modiifico el nombre de ka Ciudad con codigo postal " + cod + " a " + nuevoNombre);

            } else {

                System.out.println("Ciudad no encoontrada");
                Log.escribir("Error al intentar modificar el nombre de la Ciudad con codigo postal " + cod + ": Ciudad no encontrada");

            }

        } else {
            System.out.println("No hay ninguna ciudad registrada");
        }

    }

    public static void modificarNombreProvincia() {

        if (!ciudades.esVacio()) {

            int cod;
            System.out.println("Ingrese el codigo postal de la ciudad que desea modificar");
            cod = lectura.nextInt();
            lectura.nextLine();

            Ciudad ciudad = (Ciudad) ciudades.obtenerDato(cod);

            if (ciudad != null) {

                String nuevoNombre;

                System.out.println("Ingrese un nuevo nombre de Provincia");
                nuevoNombre = lectura.nextLine();

                ciudad.setNomProvincia(nuevoNombre);

                System.out.println("Se modiifico el nombre de la Provincia correctamente");
                Log.escribir("Se modiifico el nombre de Provincia de la ciudad con codigo postal " + cod + " a " + nuevoNombre);

            } else {

                System.out.println("Ciudad no encontrada");
                Log.escribir("Error al intentar modificar el nombre de la Provincia de la Ciudad con codigo postal " + cod + ": Ciudad no encontrada");

            }

        } else {
            System.out.println("No hay ninguna ciudad registrada");
        }

    }

    public static void ABMRedDeRutas() {
        int op;
        boolean continuar = true;
        while (continuar) {

            System.out.println("");
            System.out.println("1 - Dar de alta ruta");
            System.out.println("2 - Dar de baja ruta");
            System.out.println("3 - modificar la distancia de una ruta");
            System.out.println("4 - volver");
            System.out.println("Ingrese el numero de la opcion que desea realizar: ");
            op = lectura.nextInt();
            lectura.nextLine();

            if (1 <= op && op <= 4) {
                continuar = false;
                switch (op) {
                    case 1:
                        altaRuta();
                        break;
                    case 2:
                        bajaRuta();
                        break;
                    case 3:
                        modificarRuta();
                        break;
                    default:
                        break;
                }

            } else {
                System.out.println("Opcion no valida, porfavor ingrese un numero del menu de opciones \n");
            }

        }

    }

    public static void altaRuta() {

        int ciudadOrigen, ciudadDestino;
        double distancia;

        System.out.println("Ingrese el codigo postal de la ciudad de origen ");
        ciudadOrigen = lectura.nextInt();

        lectura.nextLine();

        System.out.println("Ingrese el codigo postal de la  ciudad destino");
        ciudadDestino = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese la distancia entre ciudades");
        distancia = lectura.nextInt();
        lectura.nextLine();

        if (cargarRuta(String.valueOf(ciudadOrigen), String.valueOf(ciudadDestino), String.valueOf(distancia))) {
            System.out.println("Se inserto ruta de " + ciudadOrigen + " a " + ciudadDestino + " exitosamente");
            Log.escribir("Se inserto ruta de " + ciudadOrigen + " a " + ciudadDestino + " exitosamente");
        } else {
            System.out.println("No se inserto ruta exitosamente");
            Log.escribir("Error al intentar dar de alta nueva ruta de " + ciudadOrigen + " a " + ciudadDestino);
        }
    }

    public static void bajaRuta() {

        if (!rutas.esVacio()) {

            int co, cd;

            System.out.println("Ingrese el codigo postal de la ciudad de origen");
            co = lectura.nextInt();
            lectura.nextLine();

            System.out.println("Ingrese el codigo postal de la ciudad destino");
            cd = lectura.nextInt();
            lectura.nextLine();

            if (rutas.eliminarArco(co, cd)) {

                System.out.println("Se dio de baja la ruta");
                Log.escribir("Se dio de baja la ruta con Ciudad de origen " + co + " y ciudad destino " + cd);

            } else {

                System.out.println("No se pudo dar de baja la ruta");
                Log.escribir("Error al intentar dar de baja la ruta con ciudad de origen " + co + " y ciudad destino " + cd);

            }

        } else {
            System.out.println("No hay ninguna ruta cargada");
        }

    }

    public static void modificarRuta() {

        if (!rutas.esVacio()) {

            int co, cd;
            double distancia;

            System.out.println("Ingrese el codigo postal de la ciudad de origen");
            co = lectura.nextInt();
            lectura.nextLine();

            System.out.println("Ingrese el codigo postal de la ciudad destino");
            cd = lectura.nextInt();
            lectura.nextLine();

            if (rutas.existeArco(co, cd)) {

                System.out.println("Ingrese el nueva distancia");
                distancia = lectura.nextDouble();
                lectura.nextLine();

                if (rutas.eliminarArco(co, cd)) {

                    if (rutas.insertarArco(co, cd, distancia)) {
                        System.out.println("Exito al modificar ruta");
                        Log.escribir("Se modifico la distancia de la ruta con ciudad de origen " + co + " y ciudad destino " + cd + " a " + distancia);
                    } else {
                        System.out.println("No se pudo modificar ruta");
                        Log.escribir("Error al intentar modificar la distancia de la ruta con ciudad de origen " + co + " y ciudad destino " + cd);
                    }

                } else {

                    System.out.println("No se pudo modificar ruta");
                    Log.escribir("Error al intentar modificar la distancia de la ruta con ciudad de origen " + co + " y ciudad destino " + cd);

                }

            } else {

                System.out.println("La ruta ingresada no existe");
                Log.escribir("Error al intentar modificar la distancia de la ruta con ciudad de origen " + co + " y ciudad destino " + cd + ": La ruta ingresada no existe");

            }

        } else {
            System.out.println("No hay ninguna ruta cargada");
        }

    }

    public static void ABMClientes() {
        int op;
        boolean continuar = true;

        while (continuar) {

            System.out.println("");
            System.out.println("1 - Dar de alta cliente");
            System.out.println("2 - Dar de baja cliente");
            System.out.println("3 - modificar Nombre de un cliete");
            System.out.println("4 - modificar numero de telefono de un cliente");
            System.out.println("5 - modificar mail de un cliete");
            System.out.println("6 - volver");
            System.out.println("Ingrese el numero de la opcion que desea realizar: ");
            op = lectura.nextInt();
            lectura.nextLine();

            if (1 <= op && op <= 6) {
                continuar = false;
                switch (op) {
                    case 1:
                        altaCliente();
                        break;
                    case 2:
                        bajaCliente();
                        break;
                    case 3:
                        modificarTelefono();
                        break;
                    case 4:
                        modificarMail();
                        break;
                    default:
                        break;
                }

            } else {
                System.out.println("Opcion no valida, porfavor ingrese un numero del menu de opciones \n");
            }

        }

    }

    public static void altaCliente() {

        String tipoDoc, nom, ap, email;
        int numDoc, tel;

        System.out.println("Ingrese el tipo de documento ");
        tipoDoc = lectura.nextLine();

        System.out.println("Ingrese el numero de documento ");
        numDoc = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese el nombre ");
        nom = lectura.nextLine();

        System.out.println("Ingrese el apellido");
        ap = lectura.nextLine();

        System.out.println("Ingrese el numero de telefono");
        tel = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese el email");
        email = lectura.nextLine();

        if (cargarCliente(tipoDoc, String.valueOf(numDoc), nom, ap, String.valueOf(tel), email)) {

            System.out.println("Se inserto cliente " + nom + " " + ap + " exitosamente");
            Log.escribir("Se inserto cliente " + nom + " " + ap + " exitosamente");

        } else {

            System.out.println("No se inserto cliente exitosamente");
            Log.escribir("Error al intentar insertar al cliente " + nom + " " + ap);

        }

    }

    public static void bajaCliente() {

        if (!clientes.isEmpty()) {

            String tipoDoc;
            int numDoc;

            System.out.println("Ingrese el tipo de documento del cliente que desea dar de baja");
            tipoDoc = lectura.nextLine();

            System.out.println("Ingrese el numero de documento del cliente que desea dar de baja");
            numDoc = lectura.nextInt();
            lectura.nextLine();

            IDCliente id = new IDCliente(tipoDoc, numDoc);

            if (clientes.remove(id) == null) {
                System.out.println("El Cliente no se pudo dar de baja");
                Log.escribir("No se pudo dar de baja al cliente: " + tipoDoc + " " + numDoc);

            } else {

                System.out.println("Cliente dado de baja correctamente");
                Log.escribir("Se dio de baja al cliente: " + tipoDoc + " " + numDoc);

            }

        } else {
            System.out.println("No hay clientes registrados aun");
        }

    }

    public static void modificarMail() {

        if (!clientes.isEmpty()) {

            String tipoDoc;
            int numDoc;
            Cliente cliente;

            System.out.println("Ingrese el tipo de documento de identidad");
            tipoDoc = lectura.nextLine();

            System.out.println("Ingrese el numero de documento de identidad");
            numDoc = lectura.nextInt();
            lectura.nextLine();

            IDCliente id = new IDCliente(tipoDoc, numDoc);
            cliente = (Cliente) clientes.get(id);

            if (cliente != null) {

                String mail;

                System.out.println("Ingrese el nuevo mail");
                mail = lectura.nextLine();

                cliente.setEmail(mail);

                System.out.println("Se modifico el mail correctamente");
                Log.escribir("Se modifico el email del cliente: " + tipoDoc + " " + numDoc + " a " + mail);

            } else {

                System.out.println("Cliente no encontrado");
                Log.escribir("Error al intentar modificar el email del cliente: " + tipoDoc + " " + numDoc + ": Cliente no encontrado");

            }

        } else {
            System.out.println("No hay clientes registrados aun");
        }

    }

    public static void modificarTelefono() {

        if (!clientes.isEmpty()) {

            String tipoDoc;
            int numDoc;
            Cliente cliente;

            System.out.println("Ingrese el tipo de documento de identidad");
            tipoDoc = lectura.nextLine();

            System.out.println("Ingrese el numero de documento de identidad");
            numDoc = lectura.nextInt();
            lectura.nextLine();

            IDCliente id = new IDCliente(tipoDoc, numDoc);

            cliente = (Cliente) clientes.get(id);

            if (cliente != null) {

                int numero;

                System.out.println("Ingrese el nuevo numero de telefono");
                numero = lectura.nextInt();

                cliente.setTelefono(numero);

                System.out.println("Se modifico el telefono correctamente");
                Log.escribir("Se modifico el telefono del cliente: " + tipoDoc + " " + numDoc + " a " + numero);

            } else {

                System.out.println("Cliente no encontrado");
                Log.escribir("Error al intentar modificar el telefono del cliente: " + tipoDoc + " " + numDoc + ": Cliente no encontrado");

            }

        } else {
            System.out.println("No hay clientes registrados aun");
        }

    }

    public static void ABMPedidos() {
        int op;
        boolean continuar = true;

        while (continuar) {

            System.out.println("");
            System.out.println("1 - Dar de alta un pedido");
            System.out.println("2 - Dar de baja un pedido");
            System.out.println("3 - modificar una pedido");
            System.out.println("4 - volver");
            System.out.println("Ingrese el numero de la opcion que desea realizar: ");
            op = lectura.nextInt();
            lectura.nextLine();

            if (1 <= op && op <= 4) {

                continuar = false;
                switch (op) {
                    case 1:
                        altaSolicitud();
                        break;
                    case 2:
                        bajaSolicitud();
                        break;
                    case 3:
                        modificarSolicitud();
                        break;
                    default:
                        break;
                }

            } else {
                System.out.println("Opcion no valida, porfavor ingrese un numero del menu de opciones \n");
            }

        }

    }

    public static void altaSolicitud() {

        String fe, td, dr, de, p;
        int co, cd, nd, cb, mc;

        System.out.println("Ingrese el codigo de la ciudad de origen");
        co = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese el codigo de la ciudad de destino");
        cd = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese la fecha del pedido en formato dd/mm/yyyy");
        fe = lectura.nextLine();

        System.out.println("Ingrese el tipo de documento del cliente");
        td = lectura.nextLine();

        System.out.println("Ingrese el numero de documento del cliente");
        nd = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese la cantidad de metros cubicos del pedido(Tiene que ser un numero entero)");
        mc = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese la cantidad de bultos del pedido");
        cb = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese domicilio de retiro (calle y numero)");
        dr = lectura.nextLine();

        System.out.println("Ingrese domicilio de entrega (calle y numero)");
        de = lectura.nextLine();

        System.out.println("Ingrese T si el pedido esta pago y cualquier otra letra si no lo esta");
        p = lectura.nextLine();

        if (cargarSolicitud(String.valueOf(co), String.valueOf(cd), fe, td, String.valueOf(nd), String.valueOf(mc), String.valueOf(cb), dr, de, p)) {

            System.out.println("Se insertó la solicitud de viaje de Cliente " + td + " " + nd + " desde " + co + " -> " + cd + " exitosamente");
            Log.escribir("Se cargo una nueva Solicitud de viaje de " + co + " a " + cd);

        } else {

            System.out.println("No se pudo insertar la solicitud");
            Log.escribir("Error: No se pudo cargar una nueva Solicitud de viaje de " + co + " a " + cd);

        }

    }

    public static void bajaSolicitud() {

        if (!solicitudes.esVacia()) {

            int co, cd, num;

            System.out.println("Ingrese el codigo postal de la ciudad de origen");
            co = lectura.nextInt();
            lectura.nextLine();

            System.out.println("Ingrese el codigo postal de la ciudad destino");
            cd = lectura.nextInt();
            lectura.nextLine();

            System.out.println("Ingrese el numero de solicitud");
            num = lectura.nextInt();
            lectura.nextLine();

            if (solicitudes.eliminar(co, cd, num)) {

                System.out.println("Se pudo eliminar la solicitud correctamente");
                Log.escribir("Se dio de baja la solicitud de " + co + " a " + cd + " con numero " + num);

            } else {

                System.out.println("No se pudo eliminar la solicitud correctamente");
                Log.escribir("Error al intentar dar de baja la solicitud de " + co + " a " + cd + " con numero " + num);

            }

        } else {
            System.out.println("No hay solicitudes cargadas");
        }

    }

    public static void modificarSolicitud() {

        if (!solicitudes.esVacia()) {

            int co, cd, num;
            System.out.println("Ingrese el codigo postal de la ciudad de origen");
            co = lectura.nextInt();
            lectura.nextLine();

            System.out.println("Ingrese el codigo postal de la ciudad destino");
            cd = lectura.nextInt();
            lectura.nextLine();

            System.out.println("Ingrese el numero de solicitud");
            num = lectura.nextInt();
            lectura.nextLine();

            SolicitudDeViaje solicitud = solicitudes.obtenerSolicitud(co, cd, num);

            if (solicitud != null) {

                int op;
                boolean continuar = true;

                while (continuar) {

                    System.out.println("1 - Modificar fecha");
                    System.out.println("2 - Modificar domicilio de entrega");
                    System.out.println("3 - Modificar domicilio de retiro");
                    System.out.println("4 - Modificar la cantidad de metros cubicos");
                    System.out.println("5 - Modificar la cantidad de bultos");
                    System.out.println("6 - Modificar Cliente");
                    System.out.println("7 - Modificar estado de pago del pedido");
                    System.out.println("8 - volver");
                    System.out.println("Ingrese el numero de la opcion que desea realizar: ");
                    op = lectura.nextInt();
                    lectura.nextLine();

                    if (1 <= op && op <= 8) {

                        continuar = false;
                        switch (op) {
                            case 1:
                                String fechaNueva;
                                System.out.println("Ingrese nueva fecha en formato dd/mm/yyyy: ");
                                fechaNueva = lectura.nextLine();
                                solicitud.setFecha(fechaNueva);
                                Log.escribir("Se modifico la fecha de la solicitud de " + co + " a " + cd + " con numero " + num + ". Nueva fecha: " + fechaNueva);
                                break;
                            case 2:
                                String domicilioEntregaNuevo;
                                System.out.println("Ingrese nuevo domicilio de entrega(calle y numero): ");
                                domicilioEntregaNuevo = lectura.nextLine();
                                solicitud.setDomicilioDeEntrega(domicilioEntregaNuevo);
                                Log.escribir("Se modifico el domicilio de entrega de la solicitud de " + co + " a " + cd + " con numero " + num + ". Nuevo domicilio: " + domicilioEntregaNuevo);
                                break;
                            case 3:
                                String domicilioRetiroNuevo;
                                System.out.println("Ingrese nuevo domicilio de retiro(calle y numero): ");
                                domicilioRetiroNuevo = lectura.nextLine();
                                solicitud.setDomicilioDeRetiro(domicilioRetiroNuevo);
                                Log.escribir("Se modifico el domicilio de retiro de la solicitud de " + co + " a " + cd + " con numero " + num + ". Nuevo domicilio: " + domicilioRetiroNuevo);
                                break;
                            case 4:
                                int mc;
                                System.out.println("Ingrese la nueva cantidad de metros cubicos(Numero Entero): ");
                                mc = lectura.nextInt();
                                lectura.nextLine();
                                solicitud.setMetrosCubicos(mc);
                                Log.escribir("Se modifico la cantidad de metros cubicos de la solicitud de " + co + " a " + cd + " con numero " + num + ". Nueva cantidad: " + mc);
                                break;
                            case 5:
                                int cb;
                                System.out.println("Ingrese la cantidad de bultos: ");
                                cb = lectura.nextInt();
                                lectura.nextLine();
                                solicitud.setCantBultos(cb);
                                Log.escribir("Se modifico la cantidad de bultos de la solicitud de " + co + " a " + cd + " con numero " + num + ". Nueva cantidad: " + cb);
                                break;
                            case 6:
                                String td;
                                int nd;
                                System.out.println("Ingrese el tipo de documento del cliente:");
                                td = lectura.nextLine();
                                System.out.println("Ingrese el numero de documento del cliente:");
                                nd = lectura.nextInt();
                                lectura.nextLine();
                                IDCliente id = new IDCliente(td, nd);
                                if (clientes.containsKey(id)) {
                                    solicitud.setID(id);
                                    Log.escribir("Se modifico el cliente de la solicitud de " + co + " a " + cd + " con numero " + num + ". Nuevo cliente: " + id.toString());
                                } else {
                                    System.out.println("Cliente no encontrado");
                                    Log.escribir("No se pudo modificar el cliente de la solicitud de " + co + " a " + cd + " con numero " + num + ": El cliente no se encontro");
                                }
                                break;
                            case 7:
                                boolean p = false;
                                int pago;
                                System.out.println("Ingrese 1 si la solicitud esta paga, ingrese cualquier otro numero si no lo esta: ");
                                pago = lectura.nextInt();
                                lectura.nextLine();
                                if (pago == 1) {
                                    p = true;
                                }
                                solicitud.setEstaPago(p);
                                Log.escribir("Se modifico el estado de pago de la solicitud de " + co + " a " + cd + " con numero " + num + ": " + p);
                                break;
                            default:
                                break;
                        }
                    } else {
                        System.out.println("Opcion no valida, porfavor ingrese un numero del menu de opciones \n");
                    }

                }

            } else {

                System.out.println("Solicitud no valida");
                Log.escribir("No se encontro la solicitud de " + co + " a " + cd + " con numero " + num + " para poder modificarla");

            }

        } else {
            System.out.println("No hay nunguna solicitud cargada");
        }

    }

    public static void consultarCliente() {

        if (!clientes.isEmpty()) {

            String td;
            int nd;

            System.out.println("Ingrese el tipo de documento del cliente a consultar");
            td = lectura.nextLine();

            System.out.println("Ingrese el numero de documento del cliente a consultar");
            nd = lectura.nextInt();
            lectura.nextLine();

            IDCliente id = new IDCliente(td, nd);
            Cliente c = (Cliente) clientes.get(id);

            if (c == null) {
                System.out.println("Cliente no encontrado");
            } else {
                System.out.println(c.toString());
            }

        } else {
            System.out.println("No hay ningun cliente cargado");
        }

    }

    public static void consultarCiudad() {

        if (!ciudades.esVacio()) {

            int cod;

            System.out.println("Ingrese el Codigo postal de la Ciudad");
            cod = lectura.nextInt();

            lectura.nextLine();
            Ciudad c = (Ciudad) ciudades.obtenerDato(cod);

            if (c != null) {
                System.out.println(c.toString());
            } else {
                System.out.println("Ciudad no encontrada");
            }

        } else {
            System.out.println("No hay ciudades registradas");
        }

    }

    public static void consultarCiudadesConPrefijo() {

        if (!ciudades.esVacio()) {

            Lista lis;
            int prefijo, min, max;

            System.out.println("Ingrese un prefijo (numero entero de 2 digitos)");
            prefijo = lectura.nextInt();
            lectura.nextLine();

            min = prefijo * 100;
            max = min + 99;

            lis = ciudades.listarRango(min, max);

            for (int i = 1; i <= lis.longitud(); i++) {
                System.out.println(lis.recuperar(i).toString());
            }

        } else {
            System.out.println("No hay ciudades registradas");
        }

    }

    public static void obtenerCaminoConMenosCiudades() {

        Lista camino;
        int cOrigen, cDestino;

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de origen");
        cOrigen = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de destino");
        cDestino = lectura.nextInt();
        lectura.nextLine();

        camino = rutas.CaminoMasCorto(cOrigen, cDestino);
        System.out.println("Camino con menos ciudades: ");
        System.out.println(camino.toString());
    }

    public static void obtenerCaminoConMenorDistancia() {

        Lista camino;
        int cOrigen, cDestino;

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de origen");
        cOrigen = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de destino");
        cDestino = lectura.nextInt();
        lectura.nextLine();

        camino = rutas.caminoConMenorDistancia(cOrigen, cDestino);
        System.out.println("Camino con menor distancia: ");
        System.out.println(camino.toString());

    }

    public static void obtenerCaminosConIntermedio() {

        Lista caminos, camino;
        int cOrigen, cIntermedia, cDestino;

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de origen");
        cOrigen = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de Intermedia");
        cIntermedia = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de destino");
        cDestino = lectura.nextInt();
        lectura.nextLine();

        caminos = rutas.obtenerCaminosConIntermedio(cOrigen, cIntermedia, cDestino);

        System.out.println(" CAMINOS ");

        for (int i = 1; i <= caminos.longitud(); i++) {

            camino = (Lista) caminos.recuperar(i);
            System.out.print("Camino: ");
            System.out.print(camino.toString());

        }

    }

    public static void verificarRecorrido() {

        int cOrigen, cDestino;
        double cantKM;

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de origen");
        cOrigen = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de destino");
        cDestino = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese una cantidad de kilometros a recorrer");
        cantKM = lectura.nextDouble();
        lectura.nextLine();

        if (cantKM < rutas.recorridoMin(cOrigen, cDestino)) {
            System.out.println("Si existe un camino en el cual se recorre como maximo " + cantKM + " km");
        } else {
            System.out.println("No existe ningun camino en el cual se recorre como maximo " + cantKM + " km");
        }

    }

    public static void mostrarEspacioNecesarioYPedidos() {

        int cOrigen, cDestino;
        Lista pedidos;

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de origen");
        cOrigen = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de destino");
        cDestino = lectura.nextInt();
        lectura.nextLine();

        pedidos = solicitudes.obtenerValor(cOrigen + "-" + cDestino);
        if (pedidos.esVacia()) {
            System.out.println("No hay ninguna solicitud de esa ciudad de Origen hacia esa ciudad Destino");
        } else {
            SolicitudDeViaje s;
            int cantTotal = 0;

            for (int i = 1; i <= pedidos.longitud(); i++) {
                s = (SolicitudDeViaje) pedidos.recuperar(i);
                System.out.println(s.toString());
                cantTotal = cantTotal + s.getMetrosCubicos();
            }

            System.out.println("Espacio necesario: " + cantTotal + " metros cubicos");
        }

    }

    public static void verificarSolicitudesACiudadesIntermedias() {

        int cOrigen, cDestino;
        int espacioDisponible;

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de origen");
        cOrigen = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese el codigo postal(numero entero) de la ciudad de destino");
        cDestino = lectura.nextInt();
        lectura.nextLine();

        System.out.println("Ingrese una cantidad de metros cubicos disponibles del camion");
        espacioDisponible = lectura.nextInt();
        lectura.nextLine();

        Lista caminoMasCorto = rutas.caminoConMenorDistancia(cOrigen, cDestino);
        Lista sIntermedias = solicitudes.listarPosiblesSolicitudesIntermedias(caminoMasCorto, espacioDisponible);

        System.out.println("Camino Mas Corto: " + caminoMasCorto);

        if (sIntermedias.esVacia()) {
            System.out.println("No Hay posibles solicitudes a ciudades intermedias");
        }

        for (int i = 1; i <= sIntermedias.longitud(); i++) {
            SolicitudDeViaje solicitud = (SolicitudDeViaje) sIntermedias.recuperar(i);
            System.out.println("Posibles solicitudes a ciudades intermedias: ");
            System.out.println(solicitud.toString());

        }

    }

    public static void verificarCaminoPerfecto() {
        Lista camino = new Lista();
        int op = 1;

        while (op == 1) {

            System.out.println("Ingrese el codigo postal");
            int codPostal = lectura.nextInt();
            lectura.nextLine();

            camino.insertar(codPostal, camino.longitud() + 1);

            System.out.println("Agregar otra ciudad? (1=SI, 2=NO)");
            op = lectura.nextInt();
            lectura.nextLine();
        }

        System.out.println("Ingrese la cantidad de metros cubicos disponibles del camion");
        int espacioDisponible = lectura.nextInt();
        lectura.nextLine();
        
        System.out.println("Camino: " + camino);

        Lista listaSolicitudes = solicitudes.verificarCaminoPerfecto(camino, espacioDisponible);
        
        if (rutas.verificarCamino(camino) && !listaSolicitudes.esVacia()) {
            System.out.println("Es un camino perfecto. Solicitudes: ");
            for (int i = 1; i <= listaSolicitudes.longitud(); i++) {
                System.out.println(listaSolicitudes.recuperar(i).toString());
            }
        } else {
            System.out.println("No es un camino perfecto.");
        }

    }

    public static String estadoDelSistema() {

        String cad;

        cad = "\n ===== ESTADO DEL SISTEMA ===== \n"
                + ("\n ---- HASHMAP CLIENTES ---- \n");

        for (IDCliente id : clientes.keySet()) {
            Cliente cliente = clientes.get(id);
            cad += "[ID: " + cliente.getId().toString() + " -> " + cliente.toString() + " ] \n";
        }

        cad += "\n ---- ARBOL AVL CIUDADES ---- \n"
                + ciudades.toString()
                + "\n ---- GRAFO DE RUTAS ---- \n"
                + rutas.toString()
                + "\n---- Solicitudes ---- \n"
                + solicitudes.toString();

        return (cad);

    }

}
