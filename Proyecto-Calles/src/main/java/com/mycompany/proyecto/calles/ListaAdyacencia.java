/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.calles;

/**
 *
 * @author ffust
 */
public class ListaAdyacencia {
    private NodoListaAdyacencia head;
    private int size; 
    

    public ListaAdyacencia() {
        this.head = null;
        this.size = 0;
    }

    /**
     * @return the head
     */
    public NodoListaAdyacencia getHead() {
        return head;
    }

    /**
     * @param head the head to set
     */
    public void setHead(NodoListaAdyacencia head) {
        this.head = head;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    public void nuevaAdyacencia(NodoGrafo nuevoNodoGrafo){
        
        NodoListaAdyacencia nuevo = new NodoListaAdyacencia(nuevoNodoGrafo.getEstacion());
        
        nuevo.setpNext(head);
        head = nuevo;
        
    }
    public void printAdyacancias(){
        NodoListaAdyacencia actual = head; // Comenzar desde la cabeza
        while (actual != null) {
            System.out.print(actual.estacion.nombreEstacion + " -> "); // Imprimir el dato del nodo
            actual = actual.getpNext(); // Moverse al siguiente nodo
        }
        System.out.println("null"); // Indicar el final de la lista
    }
        
        
    }
    
