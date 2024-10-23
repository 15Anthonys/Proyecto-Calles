
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.calles;



/**
 *
 * @author ffust
 */
public class FuncionesUtiles {
    
    public static void enlazarNodos(NodoGrafo nodo1, NodoGrafo nodo2){
        
        nodo1.listaAdyacencia.nuevaAdyacencia(nodo2);
        nodo2.listaAdyacencia.nuevaAdyacencia(nodo1);
        
        
    }
    
}
