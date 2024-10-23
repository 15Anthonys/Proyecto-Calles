/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyecto.calles;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author dugla
 */ 
public class Ventana2 extends javax.swing.JFrame {
    
    //declaracion de variables
    private JSONObject jsonObject;
    private String data;
    private InterfazPrueba interfacita;
    
    

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
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        grafito = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        RegresarAtras = new javax.swing.JButton();
        jComboBoxLines = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        getContentPane().add(jComboBoxStops, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 230, -1));

        jLabel1.setText("Selecciona la Parada:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 120, 20));

        pelo.setText("Seleccionar la Linea:");
        getContentPane().add(pelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 180, 20));

        jButton1.setBackground(new java.awt.Color(102, 255, 0));
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, -1, -1));

        jLabel3.setText("Ingrese el radio t paradas:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 150, -1));
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 70, -1));

        jButton2.setBackground(new java.awt.Color(255, 255, 102));
        jButton2.setText("Agregar Sucursal");
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 120, -1));

        grafito.setBackground(new java.awt.Color(0, 204, 255));
        grafito.setText("Mostrar Grafo");
        grafito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grafitoActionPerformed(evt);
            }
        });
        getContentPane().add(grafito, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 120, 30));

        jButton4.setBackground(new java.awt.Color(255, 153, 255));
        jButton4.setText("Indicador de Cobertura");
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, 160, 30));

        RegresarAtras.setBackground(new java.awt.Color(255, 153, 153));
        RegresarAtras.setText("Regresar");
        RegresarAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarAtrasActionPerformed(evt);
            }
        });
        getContentPane().add(RegresarAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        getContentPane().add(jComboBoxLines, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 110, 230, -1));

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
        
        // Mostrar el grafo
        grafo.printGrafo();
        
    }//GEN-LAST:event_grafitoActionPerformed

    
    
    
    
    
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
    private javax.swing.JButton RegresarAtras;
    private javax.swing.JButton grafito;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBoxLines;
    private javax.swing.JComboBox<String> jComboBoxStops;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel pelo;
    // End of variables declaration//GEN-END:variables
}
