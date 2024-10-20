/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.calles;

/**
 *
 * @author ffust
 */
public class NodoGrafo {
    
    private ListaAdyacencia listaAdyacencia;
    private Estacion estacion;

    public NodoGrafo( Estacion estacion) {
        this.listaAdyacencia = new ListaAdyacencia();
        this.estacion = estacion;
    }

    /**
     * @return the listaAdyacencia
     */
    public ListaAdyacencia getListaAdyacencia() {
        return listaAdyacencia;
    }

    /**
     * @param listaAdyacencia the listaAdyacencia to set
     */
    public void setListaAdyacencia(ListaAdyacencia listaAdyacencia) {
        this.listaAdyacencia = listaAdyacencia;
    }

    /**
     * @return the estacion
     */
    public Estacion getEstacion() {
        return estacion;
    }

    /**
     * @param estacion the estacion to set
     */
    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }
    
}
