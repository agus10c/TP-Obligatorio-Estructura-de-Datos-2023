/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import Estructuras.GrafoEtiquetado;


public class prueba {
    
    public static void main(String[] args) {
        GrafoEtiquetado grafo = new GrafoEtiquetado();
        grafo.insertarVertice("a");
        grafo.insertarVertice("b");
        grafo.insertarVertice("c");
        grafo.insertarVertice("d");
        grafo.insertarArco("a", "b", 1.0);
        grafo.insertarArco("b", "c", 2.0);
        grafo.insertarArco("c", "a", 3.0);
        grafo.insertarArco("a", "d", 3.0);
        grafo.obtenerCaminosConIntermedio("a", "b", "c");
    }
}
