/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.calles;

/**
 *
 * @author ffust
 */
public class ListaSimple {
    
    private NodoListaSimple head;
    private int size;

    public ListaSimple() {
        this.head = null;
        this.size = 0;
    }

    /**
     * @return the head
     */
    public Object getHead() {
        return head;
    }

    /**
     * @param head the head to set
     */
    public void setHead(NodoListaSimple head) {
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

    public void agregar(Estacion estacion) {
           NodoListaSimple nuevo = new NodoListaSimple(estacion);
           nuevo.setpNext(head);
           head = nuevo;
    
}
    public boolean contiene(Estacion estacion) {
           NodoListaSimple actual = head;
           while (actual != null) {
               if (actual.estacion.equals(estacion)) {
                   return true; 
               }
               actual = actual.getpNext();
           }
           return false; 
       }
}
  

