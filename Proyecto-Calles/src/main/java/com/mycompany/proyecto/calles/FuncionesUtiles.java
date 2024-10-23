/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.calles;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */




/**
 *
 * @author ffust
 */
public class FuncionesUtiles {
    
    public static void enlazarNodos(NodoGrafo nodo1, NodoGrafo nodo2){
        
        nodo1.listaAdyacencia.nuevaAdyacencia(nodo2);
        nodo2.listaAdyacencia.nuevaAdyacencia(nodo1);
        
        
    }
    
    public static void agregarSucursal(NodoGrafo nodo){
        
        nodo.estacion.setSucursal(true);
    }
    
    
    
    public static void marcarCubiertas(Grafo grafo, int t) {
            if (grafo == null || t < 0) {
                throw new IllegalArgumentException("El grafo no puede ser nulo y t debe ser un entero positivo.");
            }

            ListaSimple visitados = new ListaSimple();
            NodoGrafo actual = grafo.getpFirst();

            // Buscar nodos con sucursal = true
            while (actual != null) {
                if (actual.getEstacion().isSucursal()) {
                    // Realizar DFS desde este nodo con la distancia t
                    DFSMarcarCubiertas(grafo, actual, visitados, t, 0); // Pasar el grafo
                }
                actual = actual.getpNext();
            }
        }

    private static void DFSMarcarCubiertas(Grafo grafo, NodoGrafo nodo, ListaSimple visitados, int t, int distancia) {
        if (nodo == null || visitados.contiene(nodo.getEstacion()) || distancia > t) {
            return; // Si el nodo es nulo, ya fue visitado o se ha superado la distancia
        }

        // Marcar el nodo como visitado
        visitados.agregar(nodo.getEstacion());
        
        // Cambiar cubierta a true
        nodo.getEstacion().setCubierta(true);

        // Recorrer las adyacencias
        NodoListaAdyacencia adyacente = nodo.getListaAdyacencia().getHead();
        while (adyacente != null) {
            NodoGrafo siguienteNodo = buscarNodoPorEstacion(grafo, adyacente.estacion); // Ahora tiene acceso a grafo
            DFSMarcarCubiertas(grafo, siguienteNodo, visitados, t, distancia + 1); // Pasar el grafo
            adyacente = adyacente.getpNext(); 
        }
    }

    private static NodoGrafo buscarNodoPorEstacion(Grafo grafo, Estacion estacion) {
        if (grafo == null || estacion == null) {
            return null; // Si el grafo o la estación son nulos, retornamos null
        }

        NodoGrafo actual = grafo.getpFirst();
        while (actual != null) {
            if (actual.getEstacion().equals(estacion)) {
                return actual; // Retornamos el nodo que coincide con la estación
            }
            actual = actual.getpNext(); // Avanzar al siguiente nodo
        }
        return null; // No se encontró el nodo
    }
    
    
}

    

