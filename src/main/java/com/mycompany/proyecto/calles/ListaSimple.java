/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.calles;
/*
 
 
@author ffust*/
public class ListaSimple {

    private NodoListaSimple head;
    private int size;

    public ListaSimple() {
        this.head = null;
        this.size = 0;
    }

    
  public Object getHead() {
      return head;}

    
  public void setHead(NodoListaSimple head) {
      this.head = head;}

    
  public int getSize() {
      return size;}

    /**
     
@param size the size to set*/
  public void setSize(int size) {
      this.size = size;}

    public void agregar(Estacion estacion) {
           NodoListaSimple nuevo = new NodoListaSimple(estacion);
           nuevo.setpNext(head);
           head = nuevo;
           size ++;

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
    public boolean isEmpty(){
        return (head == null);
    }
    public Estacion eliminarPrimero() {
        if (head == null) {
            return null; // Si la lista está vacía, retornamos null
        }
        Estacion estacion = head.getEstacion(); // Guardamos la estación del nodo a eliminar
        head = head.getpNext(); // Avanzamos el head al siguiente nodo
        size--; // Decrementamos el tamaño
        return estacion; // Retornamos la estación eliminada
}
    }
