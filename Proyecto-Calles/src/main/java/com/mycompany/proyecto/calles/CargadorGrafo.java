/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.calles;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONTokener;
import org.json.JSONTokener;
/**
 *
 * @author dugla
 */
public class CargadorGrafo {
    
    private InterfazPrueba inter;
    private String datito;
    private JSONObject jsonObject;
    
    
    public void enviardatito(String datito){
        this.datito = datito;
    }
    
    public CargadorGrafo(InterfazPrueba inter) {
      this.inter = inter;
    }
    
    public static Grafo cargarGrafoDesdeJson(String datito) {
    Grafo grafo = new Grafo(null); // Inicializa el grafo con pFirst como null

    try {
        JSONObject jsonObject = new JSONObject(new JSONTokener(datito));

        // Obtener el nombre de la red (ej. "Metro de Caracas")
        String nombreRed = jsonObject.keys().next();
        JSONArray lineas = jsonObject.getJSONArray(nombreRed);

        // Iterar sobre las líneas
        for (int i = 0; i < lineas.length(); i++) {
            JSONObject lineaObj = lineas.getJSONObject(i);
            String nombreLinea = lineaObj.keys().next();
            JSONArray paradas = lineaObj.getJSONArray(nombreLinea);

            NodoGrafo nodoAnterior = null;

            for (int j = 0; j < paradas.length(); j++) {
                Object parada = paradas.get(j);
                Estacion estacion;

                if (parada instanceof String) {
                    String nombreParada = (String) parada;
                    estacion = new Estacion(nombreParada);
                } else if (parada instanceof JSONObject) {
                    // Manejar la estación combinada
                    JSONObject conexion = (JSONObject) parada;
                    String origen = conexion.keys().next();
                    String destino = conexion.getString(origen);
                    
                    // Crear un nombre combinado para la estación única
                    String nombreCombinado = origen + ": " + destino;

                    // Verificar si la estación combinada ya existe
                    NodoGrafo nodoCombinado = buscarNodo(grafo, nombreCombinado);
                    if (nodoCombinado == null) {
                        nodoCombinado = new NodoGrafo(new Estacion(nombreCombinado));
                        grafo.agregarNodo(nodoCombinado);
                    }

                    // Establecer adyacencias
                    if (nodoAnterior != null) {
                        // Conectar el nodo anterior con la estación combinada
                        if (!nodoAnterior.getListaAdyacencia().existeAdyacencia(nodoCombinado)) {
                            nodoAnterior.getListaAdyacencia().nuevaAdyacencia(nodoCombinado);
                        }
                        if (!nodoCombinado.getListaAdyacencia().existeAdyacencia(nodoAnterior)) {
                            nodoCombinado.getListaAdyacencia().nuevaAdyacencia(nodoAnterior);
                        }
                    }
                    nodoAnterior = nodoCombinado; // Actualizar el nodo anterior
                    continue; // Continuar al siguiente elemento
                } else {
                    continue; // Si no es ni String ni JSONObject, continuar
                }

                // Verificar si la estación ya existe
                NodoGrafo nodoActual = buscarNodo(grafo, estacion.getNombreEstacion());
                if (nodoActual == null) {
                    nodoActual = new NodoGrafo(estacion);
                    grafo.agregarNodo(nodoActual);
                }

                // Crear la adyacencia
                if (nodoAnterior != null) {
                    if (!nodoAnterior.getListaAdyacencia().existeAdyacencia(nodoActual)) {
                        nodoAnterior.getListaAdyacencia().nuevaAdyacencia(nodoActual);
                    }
                    if (!nodoActual.getListaAdyacencia().existeAdyacencia(nodoAnterior)) {
                        nodoActual.getListaAdyacencia().nuevaAdyacencia(nodoAnterior); // Adyacencia inversa
                    }
                }
                nodoAnterior = nodoActual; // Actualizar el nodo anterior
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return grafo;
}

// Método para buscar un nodo en el grafo por nombre de estación
private static NodoGrafo buscarNodo(Grafo grafo, String nombreEstacion) {
    NodoGrafo nodoActual = grafo.getpFirst();
    while (nodoActual != null) {
        if (nodoActual.getEstacion().getNombreEstacion().equals(nombreEstacion)) {
            return nodoActual; // Retorna el nodo si se encuentra
        }
        nodoActual = nodoActual.getpNext();
    }
    return null; // Retorna null si no se encuentra
}

}
