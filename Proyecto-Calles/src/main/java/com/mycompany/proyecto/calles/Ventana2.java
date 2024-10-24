/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyecto.calles;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.swing_viewer.SwingViewer;

/**
 *
 * @author dugla
 */ 
public class Ventana2 extends javax.swing.JFrame {
    
    //declaracion de variables
    private JSONObject jsonObject;
    private String data;
    private InterfazPrueba interfacita;
    
    public void dibujarGrafo(Grafo grafo) {
        // Crear el grafo
        Graph graph = new SingleGraph("Grafo");

        // Agregar nodos con nombres de estación
        NodoGrafo actual = grafo.getpFirst();
        while (actual != null) {
            String nombreEstacion = actual.getEstacion().getNombreEstacion().replace(":", "").replace(" ", "_");
            graph.addNode(nombreEstacion).setAttribute("ui.label", actual.getEstacion().getNombreEstacion()); // Set label
            actual = actual.getpNext();
        }

        // Agregar aristas
        actual = grafo.getpFirst();
        while (actual != null) {
            String nombreEstacion = actual.getEstacion().getNombreEstacion().replace(":", "").replace(" ", "_");
            NodoListaAdyacencia adyacente = actual.getListaAdyacencia().getHead();
            while (adyacente != null) {
                String nombreAdyacente = adyacente.getEstacion().getNombreEstacion().replace(":", "").replace(" ", "_");
                String edgeId = nombreEstacion + "-" + nombreAdyacente;

                // Agregar la arista solo si no existe
                if (graph.getEdge(edgeId) == null) {
                    try {
                        graph.addEdge(edgeId, nombreEstacion, nombreAdyacente, true); // true para aristas dirigidas
                    } catch (EdgeRejectedException e) {
                        System.out.println("Edge rejected: " + edgeId);
                    }
                }

                adyacente = adyacente.getpNext(); // Mover al siguiente nodo
            }
            actual = actual.getpNext();
        }

        // Establecer el estilo del grafo
        graph.setAttribute("ui.stylesheet", "node { fill-color: red; }");
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        // Crear el GraphViewer
        // Crear el GraphViewer
        SwingViewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout(); // Habilitar auto-layout

    // Obtener el panel de visualización
        viewer.addDefaultView(false); // false para no crear un JFrame
        
        JPanel viewerPanel = (JPanel) viewer.getDefaultView();

        // Limpiar y agregar el panel al JPanel existente
        MostrareAcaPanelGrafo.setLayout(new BorderLayout());
        MostrareAcaPanelGrafo.removeAll(); // Limpiar el panel
        MostrareAcaPanelGrafo.add(viewerPanel, BorderLayout.CENTER); // Agregar el panel del viewer
        MostrareAcaPanelGrafo.revalidate(); // Revalidar el panel
        MostrareAcaPanelGrafo.repaint(); // Volver a dibujar el panel

// Mostrar el grafo
        viewer.enableAutoLayout();

    }
    
    /**
     * Creates new form Ventana2
     */
    public Ventana2(InterfazPrueba interfacita) {
      this.interfacita = interfacita;
      initComponents();
      setLocationRelativeTo(null);
      jComboBoxLines.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBoxLinesActionPerformed(evt);
            }
        });
    }
  
    //recibe de la ventana 1 el dato que pasamos para trabajar en la ventana 2
    public void SetData(String data){
        this.data = data;
        loadJsonFile();
    }
    
    
    
    
    //metodo para agregar estaciones a la cajita de opciones anidadas para las lineas y estaciones, se trabaja con librerias json (ayuda extra)
    private void populateLineComboBox(JSONObject jsonObject) {
        jComboBoxLines.removeAllItems();
        JSONArray networksArray = jsonObject.names();
        for (int i = 0; i < networksArray.length(); i++) {
            String networkName = networksArray.getString(i);
            JSONArray linesArray = jsonObject.getJSONArray(networkName);
            for (int j = 0; j < linesArray.length(); j++) {
                JSONObject lineObject = linesArray.getJSONObject(j);
                String lineName = lineObject.keys().next();
                jComboBoxLines.addItem(lineName);
            }
        }
    }
    //metodo para cargan los archivos json
    private void loadJsonFile() {
        try {
            JSONObject jsonObject = new JSONObject(new JSONTokener(data));
            this.jsonObject = jsonObject; // Guardar el jsonObject para uso futuro
            populateLineComboBox(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //metodo para cargar las paradas por linea (se uso la implementacion de archivos JSON)
    private void loadStopsForLine(String line) {
        jComboBoxStops.removeAllItems();
        JSONArray networksArray = jsonObject.names();
        for (int i = 0; i < networksArray.length(); i++) {
            String networkName = networksArray.getString(i);
            JSONArray linesArray = jsonObject.getJSONArray(networkName);
            for (int j = 0; j < linesArray.length(); j++) {
                JSONObject lineObject = linesArray.getJSONObject(j);
                if (lineObject.has(line)) {
                    JSONArray stopsArray = lineObject.getJSONArray(line);
                    for (int k = 0; k < stopsArray.length(); k++) {
                        Object stop = stopsArray.get(k);
                        if (stop instanceof String) {
                            jComboBoxStops.addItem(stop.toString());
                        } else if (stop instanceof JSONObject) {
                            JSONObject stopObject = (JSONObject) stop;
                            String from = stopObject.keys().next();
                            String to = stopObject.getString(from);
                            jComboBoxStops.addItem(from + " -> " + to);
                        }
                    }
                }
            }
        }
    }
        
    //metodo para mostrar la cajita de lineas
    private void jComboBoxLinesActionPerformed(ActionEvent evt) {
        String selectedLine = (String) jComboBoxLines.getSelectedItem();
        if (selectedLine != null) {
            loadStopsForLine(selectedLine);
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxStops = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        pelo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        grafito = new javax.swing.JButton();
        RegresarAtras = new javax.swing.JButton();
        jComboBoxLines = new javax.swing.JComboBox<>();
        MostrareAcaPanelGrafo = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 204, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(255, 204, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxStops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxStopsActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBoxStops, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 570, 230, -1));

        jLabel1.setText("Selecciona la Parada:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 570, 120, 20));

        pelo.setText("Seleccionar la Linea:");
        getContentPane().add(pelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 540, 180, 20));

        jButton1.setBackground(new java.awt.Color(102, 255, 0));
        jButton1.setText("agregar sucursal");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 590, -1, -1));

        grafito.setBackground(new java.awt.Color(0, 204, 255));
        grafito.setText("Mostrar Grafo");
        grafito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grafitoActionPerformed(evt);
            }
        });
        getContentPane().add(grafito, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 120, 40));

        RegresarAtras.setBackground(new java.awt.Color(255, 153, 153));
        RegresarAtras.setText("Regresar");
        RegresarAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarAtrasActionPerformed(evt);
            }
        });
        getContentPane().add(RegresarAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        getContentPane().add(jComboBoxLines, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 540, 230, -1));

        MostrareAcaPanelGrafo.setBackground(new java.awt.Color(0, 0, 0));
        getContentPane().add(MostrareAcaPanelGrafo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 620, 500));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 570, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Nodo Rojo:  No hay sucursal en la estacion");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Nodo Amarillo: Hay sucursal cercana a la estacion");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 100, 360, 30));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Nodo Verde: La estacion tiene una sucursal");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 140, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 255, 51));
        jLabel5.setText("•");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 130, 50, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 51));
        jLabel6.setText("•");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, 50, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setText("•");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 60, 50, 30));

        jLabel8.setText("Selecciona numero de t paradas a cubrir");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 600, -1, 20));
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 640, -1, -1));
        getContentPane().add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 600, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegresarAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarAtrasActionPerformed
        // TODO add your handling code here:
        interfacita.setVisible(true);//regresa a la interfaz anterior
        this.setVisible(false);// cierra la interfaz actual
            
        
        
    }//GEN-LAST:event_RegresarAtrasActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void grafitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grafitoActionPerformed
        // TODO add your handling code here:
        Grafo grafo = CargadorGrafo.cargarGrafoDesdeJson(data);

    // Verificar si el grafo se cargó correctamente
    if (grafo != null) {
        // Mostrar el grafo en consola
        grafo.printGrafo();

        // Dibujar el grafo en el hilo de eventos
        SwingUtilities.invokeLater(() -> dibujarGrafo(grafo));
    } else {
        JOptionPane.showMessageDialog(this, "Error al cargar el grafo. Verifica los datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_grafitoActionPerformed

    private void jComboBoxStopsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxStopsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxStopsActionPerformed

    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MostrareAcaPanelGrafo;
    private javax.swing.JButton RegresarAtras;
    private javax.swing.JButton grafito;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBoxLines;
    private javax.swing.JComboBox<String> jComboBoxStops;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel pelo;
    // End of variables declaration//GEN-END:variables
}
