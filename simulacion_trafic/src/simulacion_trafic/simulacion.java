/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulacion_trafic;


import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Josselyn
 */
public class simulacion extends javax.swing.JFrame {

    DefaultTableModel modelo1;
    DefaultTableModel modelo2;

    //variables para alamacenar los numero de carros y tiempos en los que los carros llegan
    int tiempo = 0;
    int t1 = 1;
    int t2 = 1;

    //Funcion de hilo 1
    // Funciona como un cornometro interno para el tiempo de corrida del programa
    // Es Infinito 
    Thread cronometro1 = new Thread() {

        @Override
        public void run() {
            int seg = 0;

            for (;;) {

                try {
                    seg++;
                    tiempo++;

                    Thread.sleep(1000);
                    System.out.println(tiempo);

                    txt1.setText(Integer.toString(sumaT1()));
                    txt2.setText(Integer.toString(sumaT2()));
                    // txtpro1.setText(Double.toString(1-Math.pow(2.72,(-10/sumaT1())))+"%");
                    //txtpro2.setText(Double.toString(1-Math.pow(2.72,(-10/sumaT2())))+"%");
                    transitarCarros.start();
                } catch (Exception e) {
                }

            }
        }
    };

    /**
     * Creates new form simulacion
     */
    //Metodo para cargar las columnas en las Tables 
    public simulacion() {
        initComponents();
        modelo1 = new DefaultTableModel();
        modelo2 = new DefaultTableModel();

        modelo1.addColumn("Nro.");
        modelo1.addColumn("Tiempo ");

        modelo2.addColumn("Nro.");
        modelo2.addColumn("Tiempo ");

        this.tabla1.setModel(modelo1);
        this.tabla2.setModel(modelo2);
    }

    //Metodo para calcular el promedio de tiempo y llegada de los carros en 
    // la primera tabla
    public int sumaT1() {
        int cont1 = tabla1.getRowCount();
        int suma1 = 0;

        for (int i = 0; i < cont1; i++) {
            suma1 = suma1 + (tiempo - Integer.parseInt(tabla1.getValueAt(i, 1).toString()));

        }

        return suma1 / cont1;
    }

    //Metodo para calcular el promedio de tiempo y llegada de los carros en 
    // la segunda tabla
    public int sumaT2() {
        int cont1 = tabla2.getRowCount();
        int suma1 = 0;

        for (int i = 0; i < cont1; i++) {
            suma1 = suma1 + (tiempo - Integer.parseInt(tabla2.getValueAt(i, 1).toString()));

        }
        return suma1 / cont1;
    }

    //Funcion Hilo
    //Metodo Principal
    //Consiste en presentar los promedios en las tablas, hacer el conteo de carros para poder comparar
    // los tiempos de espera y ejercutar el semaforo
    Thread transitarCarros = new Thread() {

        @Override
        public void run() {
            for (;;) {
                int prom1, prom2;
                prom1 = Integer.parseInt(txt1.getText());
                prom2 = Integer.parseInt(txt2.getText());

                try {
                    //Como inicia nuestro semaforo
                    proceso1();

                    if (prom1 > prom2 && tabla1.getRowCount() != 0) {
                        for (int i = 0; i <= tabla1.getRowCount() + 1; i++) {
                            Thread.sleep(1000);
                            //semaforo 
                            proceso2();
                            ((DefaultTableModel) tabla1.getModel()).removeRow(0);
                        }
                        txt1.setText(0 + "");

                    } else {
                        for (int i = 0; i <= tabla2.getRowCount() + 1; i++) {
                            Thread.sleep(1000);
                            //semaforo
                            proceso3();
                            ((DefaultTableModel) tabla2.getModel()).removeRow(0);
                            System.out.println("CALLE 2 => " + i);

                        }
                        txt2.setText(0 + "");

                    }
                    generarVariables();
                } catch (Exception e) {
                }
            }
        }
    };

    //metodo para el inicio del Semaforo
    public void proceso1() {
        amarillo1.setBackground(Color.yellow);
        verde1.setBackground(Color.gray);
        rojo2.setBackground(Color.gray);
        amarillo2.setBackground(Color.yellow);
        verde2.setBackground(Color.gray);
        rojo2.setBackground(Color.gray);

    }

    //metodo para semaforo de acuerdo a la condicion uno
    public void proceso2() {
        amarillo1.setBackground(Color.gray);
        amarillo2.setBackground(Color.gray);
        verde2.setBackground(Color.green);
        verde1.setBackground(Color.gray);
        rojo1.setBackground(Color.red);
        rojo2.setBackground(Color.gray);

    }

    //metodo para semaforo de acuerdo a la condicion dos
    public void proceso3() {
        amarillo1.setBackground(Color.gray);
        amarillo2.setBackground(Color.gray);
        verde1.setBackground(Color.green);
        verde2.setBackground(Color.gray);
        rojo2.setBackground(Color.red);
        rojo1.setBackground(Color.gray);

    }

    //Metodo generador  de Variables para el total de numeros de carros que llegan a dicaha calle
    // y de acuerdo a eso se realiza los diferentes metodos
    public void generarVariables() {

        int[] arreglo_carros = {3, 5, 4, 6, 4, 3, 2, 4, 1, 4, 2, 3, 4, 2, 3, 5, 2, 4, 6, 5, 2, 4, 1, 4, 3, 6, 5, 4, 4, 1, 4, 3, 6, 5, 4, 4, 1, 4, 3, 6, 5, 4, 4, 3, 5, 4, 6, 4, 3, 2, 4, 1, 4, 2, 3, 4, 2, 3, 5, 2, 4, 6, 5, 2, 4, 1, 4, 3, 6, 5, 4, 4, 1, 4, 3, 6, 5, 4, 4, 1, 4, 3, 6, 5, 4, 4};
        int[] arreglo_calle = {2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 1, 1, 2, 2, 2, 1, 1, 1, 2, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 1, 1, 2, 2, 2, 1, 1, 1, 2, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 2};

        for (int i = 0; i < arreglo_carros[i]; i++) {

            System.out.println(arreglo_carros[i]);

            if (arreglo_calle[i] == 1) {

                String[] info = new String[2];

                info[0] = Integer.toString(t1);
                info[1] = Integer.toString(tiempo);
                modelo1.addRow(info);
                t1++;

            }
            if (arreglo_calle[i] == 2) {
                String[] info = new String[2];

                info[0] = Integer.toString(t2);
                info[1] = Integer.toString(tiempo);
                modelo2.addRow(info);
                t2++;

            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                //Logger.getLogger(inicio.class.getName()).log(Level.SEVERE, null, ex);
            }

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

        amarillo2 = new javax.swing.JButton();
        rojo2 = new javax.swing.JButton();
        verde2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txt2 = new javax.swing.JTextField();
        inicio = new javax.swing.JButton();
        verde1 = new javax.swing.JButton();
        amarillo1 = new javax.swing.JButton();
        rojo1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txt1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(amarillo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 30, 20));
        getContentPane().add(rojo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, 30, 20));

        verde2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verde2ActionPerformed(evt);
            }
        });
        getContentPane().add(verde2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 30, 20));

        tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro.", "Tiempo"
            }
        ));
        jScrollPane2.setViewportView(tabla2);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 280, 220, 140));

        jLabel3.setText("Promedio:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 430, -1, -1));

        txt2.setBackground(new java.awt.Color(153, 255, 204));
        getContentPane().add(txt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 430, 50, -1));

        inicio.setText("Inicio");
        inicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inicioActionPerformed(evt);
            }
        });
        getContentPane().add(inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 70, -1));

        verde1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verde1ActionPerformed(evt);
            }
        });
        getContentPane().add(verde1, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 270, 30, 20));
        getContentPane().add(amarillo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 30, 20));
        getContentPane().add(rojo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 30, 20));

        jLabel2.setText("Promedio:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        txt1.setBackground(new java.awt.Color(153, 255, 204));
        txt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt1ActionPerformed(evt);
            }
        });
        getContentPane().add(txt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 50, 20));

        tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro. ", "Tiempo"
            }
        ));
        jScrollPane1.setViewportView(tabla1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 210, 130));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/simulacion_trafic/Calle.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt1ActionPerformed

    private void verde1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verde1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verde1ActionPerformed

    private void inicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inicioActionPerformed
        //llamado de los metodos para el inicio del programa
        cronometro1.start();
        generarVariables();

        // transitarCarro();
    }//GEN-LAST:event_inicioActionPerformed

    private void verde2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verde2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verde2ActionPerformed

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
            java.util.logging.Logger.getLogger(simulacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(simulacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(simulacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(simulacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new simulacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton amarillo1;
    private javax.swing.JButton amarillo2;
    private javax.swing.JButton inicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton rojo1;
    private javax.swing.JButton rojo2;
    private javax.swing.JTable tabla1;
    private javax.swing.JTable tabla2;
    private javax.swing.JTextField txt1;
    private javax.swing.JTextField txt2;
    private javax.swing.JButton verde1;
    private javax.swing.JButton verde2;
    // End of variables declaration//GEN-END:variables
}
