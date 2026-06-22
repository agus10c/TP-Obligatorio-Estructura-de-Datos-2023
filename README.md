Sistema de Mudanzas Compartidas

Trabajo Final de la materia Estructuras de Datos — FAI, Universidad Nacional del Comahue.

Sistema de gestión de mudanzas compartidas entre localidades de Argentina, desarrollado en Java. Permite administrar ciudades, rutas, clientes y solicitudes de envío, y consultar caminos eficientes entre ciudades utilizando distintas estructuras de datos implementadas desde cero.


Estructuras de datos implementadas

EstructuraUso en el sistemaÁrbol AVLDiccionario de ciudades (clave: código postal)Grafo etiquetadoMapa de rutas entre ciudades (etiquetas: distancia en km)HashMap / Hash abiertoAlmacenamiento de clientes (clave: tipo y número de documento)Mapeo a MuchosSolicitudes de viaje por par de ciudades (origen → destino → lista de solicitudes), con métodos específicos para recuperar todas las solicitudes entre dos ciudades de forma eficiente

Todas las estructuras fueron implementadas de forma genérica para elementos de tipo Object o Comparable, según el TDA correspondiente.


Funcionalidades

ABM (Altas, Bajas y Modificaciones)


Ciudades
Red de rutas
Clientes
Pedidos / Solicitudes de viaje


Consultas sobre ciudades


Información completa dado un código postal
Listado de ciudades por prefijo de código postal


Consultas sobre clientes


Información completa dado el tipo y número de documento


Consultas sobre viajes (dado origen A y destino B)


Camino que pasa por menor cantidad de ciudades (BFS)
Camino de menor distancia en kilómetros (Dijkstra)
Todos los caminos posibles pasando por una ciudad intermedia C
Verificación de si es posible llegar de A a B en un máximo de X kilómetros


Verificación de viajes


Listado de todos los pedidos entre A y B con cálculo del espacio total requerido
Verificación de espacio disponible y sugerencia de solicitudes intermedias aprovechables
Validación de "camino perfecto": camino existente en el grafo con al menos una solicitud válida entre cada par de ciudades consecutivas, respetando la capacidad del camión

Visualización del sistema

Muestra el contenido completo de todas las estructuras tal como están cargadas (árbol AVL con altura y nodos hijo, grafo, mapeos)


Carga inicial

El sistema se inicializa desde un archivo de texto con el siguiente formato:

# Ciudades: C;codigoPostal;nombre;provincia
C;8300;Neuquén;Neuquén
C;8324;Cipolletti;Río Negro

# Rutas: R;codigoPostalOrigen;codigoPostalDestino;distanciaKm
R;8324;8300;11.0

# Clientes: P;tipoDoc;nroDoc;apellido;nombre;telefono
P;DNI;35678965;FERNANDEZ;JUAN CARLOS;299-4495117

# Solicitudes: S;origen;destino;fecha;tipoDoc;nroDoc;m3;bultos;domRetiro;domEntrega;pago(T/F)
S;8324;8300;15/06/2023;DNI;35678965;13;5;Sarmiento 3400;Roca 2100;T

La carga inicial incluye al menos:

30 ciudades
40 rutas
20 clientes
20 solicitudes de viaje

Los datos se ingresan en orden desordenado para garantizar que se produzcan todas las rotaciones posibles al insertar en el AVL.

Log del sistema

El sistema genera un archivo de log con:

Estado completo de todas las estructuras al finalizar la carga inicial
Registro de cada operación ABM realizada durante la ejecución
Estado final del sistema al cerrar

Tecnologías
Java
Manejo de archivos: FileReader, BufferedReader, FileWriter
