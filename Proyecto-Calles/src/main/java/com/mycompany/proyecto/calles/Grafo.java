    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.calles;
//<<<<<<< HEAD
//=======

    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//>>>>>>> 6c173ccaf66165473c04738e6dd8d6d3a7853570
/**
 *
 * @author dugla
 */
public class Grafo {
    
  private NodoGrafo pFirst;
  private String grafito;

    public Grafo(NodoGrafo pFirst) {
        this.pFirst = pFirst;
    }

    /**
     * @return the pFirst
     */
    public NodoGrafo getpFirst() {
        return pFirst;
    }

    /**
     * @param pFirst the pFirst to set
     */
    public void setpFirst(NodoGrafo pFirst) {
        this.pFirst = pFirst;
    }
    
    public void agregarNodo(NodoGrafo nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El nodo no puede ser nulo");
    }

    
        if (existeNodo(nuevo)) {
            System.out.println("El nodo ya existe en el grafo.");
            return; 
    }

        nuevo.setpNext(pFirst); 
        pFirst = nuevo; 
}
    private boolean existeNodo(NodoGrafo nodo) {
        NodoGrafo actual = pFirst; // Asumiendo que pFirst es el primer nodo de la lista
        while (actual != null) {
            if (actual.equals(nodo)) {
                                                  
                return true; // El nodo ya existe
        }
        actual = actual.getpNext(); // Avanzar al siguiente nodo
    }
        return false; // El nodo no existe
}
    
    
    
    public void printGrafo() {
        NodoGrafo actual = pFirst;
        while (actual != null) {
            System.out.print(actual.estacion.nombreEstacion + " Adyacencias: ");
            actual.listaAdyacencia.imprimirAdyacencias();
            actual = actual.getpNext();
        }
        System.out.println(); 
    }

}
