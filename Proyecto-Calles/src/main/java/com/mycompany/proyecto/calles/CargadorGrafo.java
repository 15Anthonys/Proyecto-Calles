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
                    NodoGrafo nodoCombinado = buscarEstacionCombinada(grafo, nombreCombinado);
                    if (nodoCombinado == null) {
                        nodoCombinado = new NodoGrafo(new Estacion(nombreCombinado));
                        grafo.agregarNodo(nodoCombinado);
                    }

                    // Establecer adyacencias con estaciones previas y siguientes
                    if (nodoAnterior != null) {
                        agregarAdyacencia(nodoAnterior, nodoCombinado);
                    }

                    // Conectar la estación combinada con otras estaciones de la línea
                    for (int k = j + 1; k < paradas.length(); k++) {
                        Object siguienteParada = paradas.get(k);
                        NodoGrafo nodoSiguiente;

                        if (siguienteParada instanceof String) {
                            nodoSiguiente = buscarNodo(grafo, (String) siguienteParada);
                        } else if (siguienteParada instanceof JSONObject) {
                            JSONObject siguienteConexion = (JSONObject) siguienteParada;
                            String siguienteOrigen = siguienteConexion.keys().next();
                            String siguienteDestino = siguienteConexion.getString(siguienteOrigen);
                            String siguienteNombreCombinado = siguienteOrigen + ": " + siguienteDestino;
                            nodoSiguiente = buscarEstacionCombinada(grafo, siguienteNombreCombinado);
                        } else {
                            continue; // Si no es ni String ni JSONObject, continuar
                        }

                        if (nodoSiguiente != null) {
                            agregarAdyacencia(nodoCombinado, nodoSiguiente);
                        }
                    }

                    nodoAnterior = nodoCombinado; // Actualizar el nodo anterior
                    continue; // Continuar al siguiente elemento
                } else {
                    continue; // Si no es ni String ni JSONObject, continuar
                }

                // Verificar si la estación ya existe
                NodoGrafo nodoActual = buscarEstacionCombinada(grafo, estacion.getNombreEstacion());
                if (nodoActual == null) {
                    nodoActual = new NodoGrafo(estacion);
                    grafo.agregarNodo(nodoActual);
                }

                // Crear la adyacencia
                if (nodoAnterior != null) {
                    agregarAdyacencia(nodoAnterior, nodoActual);
                }
                nodoAnterior = nodoActual; // Actualizar el nodo anterior
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return grafo;
}
    
    private static NodoGrafo buscarEstacionCombinada(Grafo grafo, String nombreEstacion) {
    // Buscar la estación en su forma original
    NodoGrafo nodo = buscarNodo(grafo, nombreEstacion);
    if (nodo != null) {
        return nodo; // Si se encuentra, retornar el nodo
    }

    // Buscar la estación en su forma invertida
    String[] partes = nombreEstacion.split(":");
    if (partes.length == 2) {
        String nombreInvertido = partes[1].trim() + ": " + partes[0].trim();
        return buscarNodo(grafo, nombreInvertido); // Retornar si se encuentra el invertido
    }

    return null; // Si no se encuentra ninguna de las dos
}


    private static void agregarAdyacencia(NodoGrafo nodo1, NodoGrafo nodo2) {
    if (!nodo1.getListaAdyacencia().existeAdyacencia(nodo2)) {
        nodo1.getListaAdyacencia().nuevaAdyacencia(nodo2);
    }
    if (!nodo2.getListaAdyacencia().existeAdyacencia(nodo1)) {
        nodo2.getListaAdyacencia().nuevaAdyacencia(nodo1); // Adyacencia inversa
    }
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