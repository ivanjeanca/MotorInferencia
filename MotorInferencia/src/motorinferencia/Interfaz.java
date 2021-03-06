package motorinferencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Interfaz extends javax.swing.JFrame {

    RandomAccessFile Maestro, IndicePremisas;
    Long longitud, longitud2;
    String regla = "", premisa = "", cc = "", hechos = "";
    String[] reglas, resolucion, columnNames = {"No.", "Regla"}, columnNames2 = {"Evento", "Descripción"};
    DefaultListModel dlm, dlm2;
    Object[][] seleccion;//aquí irá premisas
    Object[][] lista;//aquí irá premisas
    Object[][] data;
    TableColumn tc;
    int z = 0, j = 0;
    DefaultTableModel model;
    Maestro mas = new Maestro();
    indice ind = new indice();

    public Interfaz() throws FileNotFoundException, IOException {
        initComponents();

        Maestro = new RandomAccessFile("Maestro", "r");
        IndicePremisas = new RandomAccessFile("indicePremisas", "r");

        longitud = Maestro.length();
        longitud2 = IndicePremisas.length();

        Maestro.close();
        IndicePremisas.close();

        data = new Object[longitud.intValue()][2];
        seleccion = new Object[longitud2.intValue() / 20][2];

        model = (DefaultTableModel) tblJustificacion.getModel();
        model.setRowCount(0);

        actualizarContenido();
    }

    public void actualizarContenido() throws FileNotFoundException, IOException {
        RandomAccessFile Ma = new RandomAccessFile("Maestro", "r");
        data = new Object[longitud.intValue()][2];
        int[] pos;
        DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
        dtm.setRowCount(0);

        for (int i = 0; i < longitud / 28; i++) {
            data[i][0] = Ma.readInt();
            for (int j = 0; j < 6; j++) {
                premisa += Ma.readChar();
                premisa += Ma.readChar();
                premisa += " ";
                if (!premisa.equals("Pv ")) {
                    regla += premisa;
                }
                premisa = "";
            }
            data[i][1] = regla;
            regla = "";
            Object[] fila = new Object[2];
            fila[0] = data[i][0];
            fila[1] = data[i][1];
            pos = ind.buscarIndice((int) fila[0]);
            if(pos[0] != -1)
                dtm.addRow(fila);
        }
        Ma.close();

        tableReglas.setModel(dtm);

        RandomAccessFile IP = new RandomAccessFile("indicePremisas", "r");

        longitud2 = IP.length();

        lista = new Object[longitud2.intValue() / 20][2];
        System.out.println("Tamaño de la lista = " + lista.length + "\n-------------------------");

        for (int i = 0; i < longitud2 / 20; i++) {
            lista[i][0] = IP.readChar() + "" + IP.readChar();
            lista[i][1] = IP.readInt() + "," + IP.readInt() + "," + IP.readInt() + "," + IP.readInt();
        }

        dlm = new DefaultListModel();
        dlm2 = new DefaultListModel();

        for (int i = 0; i < lista.length; i++) {
            dlm.addElement(lista[i][0]);
        }

        listHPo.setModel(dlm);
        listHPr.setModel(dlm2);

        IP.close();

        hechos = "";

        model.setRowCount(0);
        tblJustificacion.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReglas = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        listHPo = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listHPr = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        btnDel = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblJustificacion = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnResolver = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnActualizarContenido = new javax.swing.JButton();
        btnBorrarRegla = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Sistema Experto Determinista");

        jLabel2.setText("Reglas");

        tableReglas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(1), "p(x),q(x)->r(x)"},
                { new Integer(2), "r(x)->s(x)"},
                { new Integer(3), "s(x)->t(x)"}
            },
            new String [] {
                "No", "Regla"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableReglas);

        listHPo.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "p(x)", "q(x)", "r(x)", "s(x)" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listHPo);

        jLabel3.setText("Hechos Posibles");

        btnAdd.setText(">");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(listHPr);

        jLabel4.setText("Hechos Presentados");

        btnDel.setText("<");
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        tblJustificacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Evento", "Decripción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblJustificacion);

        jLabel5.setText("Justificación");

        btnActualizar.setText("Actualizar Conocimiento");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnResolver.setText("Resolver");
        btnResolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResolverActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar Conocimiento");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnActualizarContenido.setText("Actualizar Contenido");
        btnActualizarContenido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarContenidoActionPerformed(evt);
            }
        });

        btnBorrarRegla.setText("Borrar Regla");
        btnBorrarRegla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarReglaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnActualizarContenido)))
                .addGap(42, 42, 42)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDel)
                .addContainerGap(149, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBorrarRegla)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 233, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAdd)
                                    .addComponent(btnResolver))
                                .addContainerGap(111, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(148, 148, 148)
                                .addComponent(jLabel5)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(173, 173, 173))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4))
                                .addGap(36, 36, 36))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane2)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jScrollPane3)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(btnAdd)
                                .addGap(36, 36, 36)
                                .addComponent(btnDel))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(btnActualizarContenido)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregar)
                            .addComponent(btnBorrarRegla))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnResolver)
                                .addGap(28, 28, 28))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        dlm2.addElement(listHPo.getSelectedValue());
        dlm.remove(listHPo.getSelectedIndex());
        listHPr.setModel(dlm2);
        listHPo.setModel(dlm);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        dlm.addElement(listHPr.getSelectedValue());
        dlm2.remove(listHPr.getSelectedIndex());
        listHPo.setModel(dlm);
        listHPr.setModel(dlm2);
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnResolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResolverActionPerformed
        model.setRowCount(0);
        hechos = "";

        for (int i = 0; i < dlm2.size(); i++) {
            seleccion[i][0] = dlm2.get(i);
        }

        for (int i = 0; i < lista.length; i++) {
            for (int j = 0; j < seleccion.length; j++) {
                if (lista[i][0].equals(seleccion[j][0])) {
                    seleccion[j][1] = lista[i][1];
                    hechos += lista[i][0] + ",";
                }
            }
        }

        hechos = hechos.substring(0, hechos.length() - 1);
        model.addRow(new Object[]{"Hechos", "[" + hechos + "]"});
        tblJustificacion.setModel(model);
        encadenamiento(seleccion);
    }//GEN-LAST:event_btnResolverActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        Maestro m = new Maestro();
        try {
            m.escribirMaestroInterfaz();
            actualizarContenido();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnActualizarContenidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarContenidoActionPerformed
        try {
            actualizarContenido();
            actualizarContenido();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnActualizarContenidoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try {
            if (tableReglas.getSelectedRow() >= 0) {
                RandomAccessFile Ma = new RandomAccessFile("Maestro", "rw");
                String aux[] = new String[6], nuevo[] = new String[6];
                Long desplazamiento = tableReglas.getSelectedRow() * mas.desplazamiento(), apuntador;
                boolean res = false, pv = true;
                String prm = "", nueva = "", modif = "";
                int llave;

                Ma.seek(desplazamiento);

                Ma.readInt();
                for (int j = 0; j < 6; j++) {
                    premisa += Ma.readChar();
                    premisa += Ma.readChar();
                    aux[j] = premisa;
                    nuevo[j] = premisa;
                    premisa = "";
                }

                apuntador = Ma.getFilePointer();
                Ma.seek(apuntador - mas.desplazamiento());
                llave = Ma.readInt();

                for (int i = 0; i < aux.length; i++) {
                    if (!aux[i].equals("Pv")) {
                        prm = prm + aux[i] + " ";
                    }
                }

                OUTER:
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < nuevo.length; j++) {
                        if (!nuevo[j].equals("Pv")) {
                            nueva = nueva + nuevo[j] + " ";
                        }
                    }

                    if (!aux[i].equals("->") && !res) {
                        if (!aux[i].equals("Pv") && !aux[i].equals("->")) {
                            int dialogResult = JOptionPane.showConfirmDialog(null, "Regla " + llave + ":\nActual: " + prm + "\nNueva: " + nueva + "\n\n¿Deseas modificar la premisa { " + aux[i] + " }?", "Modificar premisa", JOptionPane.YES_NO_CANCEL_OPTION);
                            switch (dialogResult) {
                                case JOptionPane.YES_OPTION:
                                    modif = JOptionPane.showInputDialog(null, "Modificar premisa " + aux[i] + ": (Maximo 2 caracteres)", "Modificar Premisa", JOptionPane.INFORMATION_MESSAGE);
                                    if (!modif.equals("")) {
                                        nuevo[i] = modif;
                                    } else {
                                        nuevo[i] = "Pv";
                                    }
                                    break;
                                case JOptionPane.NO_OPTION:
                                    nuevo[i] = aux[i];
                                    break;
                                case JOptionPane.CANCEL_OPTION:
                                    break OUTER;
                                default:
                                    break;
                            }
                        } else if (aux[i].equals("Pv") && !aux[i].equals("->") && pv) {
                            int dialogResult = JOptionPane.showConfirmDialog(null, "Regla " + llave + ":\nActual: " + prm + "\nNueva: " + nueva + "\n\n¿Deseas agregar otra premisa? (Se pueden tener hasta 4)", "Agregar premisa", JOptionPane.YES_NO_CANCEL_OPTION);
                            switch (dialogResult) {
                                case JOptionPane.YES_OPTION:
                                    nuevo[i] = JOptionPane.showInputDialog(null, "Agregar premisa (Maximo 2 caracteres)", "Agregar Premisa", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                case JOptionPane.NO_OPTION:
                                    pv = false;
                                    break;
                                case JOptionPane.CANCEL_OPTION:
                                    break OUTER;
                                default:
                                    break;
                            }
                        }
                    } else if (aux[i].equals("->") && !res) {
                        res = true;
                    } else if (res) {
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Regla " + llave + ":\nActual: " + prm + "\nNueva: " + nueva + "\n\n¿Deseas modificar el consecuente? { " + aux[i] + " }", "Modificar Consecuente", JOptionPane.YES_NO_CANCEL_OPTION);
                        switch (dialogResult) {
                            case JOptionPane.YES_OPTION:
                                do {
                                    nuevo[i] = JOptionPane.showInputDialog(null, "Modificar consecuente " + aux[i] + ": (Maximo 2 caracteres)", "Modificar Premisa", JOptionPane.INFORMATION_MESSAGE);
                                } while (nuevo[i].equals(""));
                                break;
                            case JOptionPane.NO_OPTION:
                                break;
                            case JOptionPane.CANCEL_OPTION:
                                break OUTER;
                            default:
                                break;
                        }
                    }
                    nueva = "";
                }

                for (int i = 0; i < nuevo.length; i++) {
                    if (!nuevo[i].equals(aux[i])) {
                        Ma.writeChar(nuevo[i].charAt(0));
                        Ma.writeChar(nuevo[i].charAt(1));
                        System.out.println("Busqueda para IndicePremisa donde \nLlave: " + llave + "\nHecho/Premisa: " + nuevo[i] + "\n");
                    } else {
                        Ma.readChar();
                        Ma.readChar();
                    }
                }
                Ma.close();
                actualizarContenido();
            } else {
                System.out.println("Ninguna regla seleccionada");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Interfaz.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class
                    .getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBorrarReglaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarReglaActionPerformed
        try {
            indice i = new indice();
            Maestro m = new Maestro();
            Long desplazamiento;
            RandomAccessFile Ma = new RandomAccessFile("Maestro", "r");
            int llave;
            if (tableReglas.getSelectedRow() >= 0) {
                desplazamiento = tableReglas.getSelectedRow() * m.desplazamiento();
                Ma.seek(desplazamiento);
                llave = Ma.readInt();

                int dialogResult = JOptionPane.showConfirmDialog(null, "Borrar Regla", "¿Estas seguro que deseas borrar la regla " + llave + "?", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    i.borrarIndice(llave);
                    actualizarContenido();
                }
            } else {
                System.out.println("Ninguna regla seleccionada");
            }
            Ma.close();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBorrarReglaActionPerformed

    void encadenamiento(Object[][] hI) {
        String cc = generaCC(hI);
        String[] resolucion;
        model.addRow(new Object[]{"Conjunto Conflicto", "{" + cc + "}"});
        tblJustificacion.setModel(model);
        if (cc.equals("")) {
            model.addRow(new Object[]{"Termina proceso", "Fin"});
            tblJustificacion.setModel(model);
        } else {
            String[] reglas = cc.split(",");
            resolucion = new String[hI.length * 4];

            for (int i = 0; i < reglas.length; i++) {
                if (!reglas[i].equals("-1")) {
                    resolucion[j] = reglas[i];
                    j++;
                }
            }

            for (int i = 0; i < resolucion.length; i++) {
                if (resolucion[i] != null) {
                    regla = data[Integer.parseInt(resolucion[i]) - 1][1].toString();
                    String antecedentes = regla.substring(0, regla.length() - 7);
                    String[] ant = antecedentes.split(" ");
                    String[] hT = hechos.split(",");
                    int coincidencia = 0;
                    for (int n = 0; n < hT.length; n++) {
                        for (int k = 0; k < ant.length; k++) {
                            if (ant[k].equals(hT[n])) {
                                coincidencia++;
                            } else {

                            }
                        }
                    }
                    String respuesta;
                    boolean continua = false;
                    if (coincidencia == ant.length) {
                        respuesta = "En regla: " + regla + " sí coinciden antecedentes";
                        continua = true;
                        i = resolucion.length;
                    } else {
                        respuesta = "En regla: " + regla + " no coinciden antecedentes";
                    }
                    model.addRow(new Object[]{"Aplicar regla", respuesta});
                    tblJustificacion.setModel(model);
                    if (continua) {
                        String nH = regla.substring(regla.length() - 3, regla.length() - 1);
                        model.addRow(new Object[]{"Nuevos Hechos", nH});
                        tblJustificacion.setModel(model);
                        if (!existe(nH, hechos)) {
                            hechos += "," + nH;
                        }

                        Object[][] nhs = new Object[3][2];//aquí irá premisas
                        nhs[0][0] = nH;
                        for (int j = 0; j < lista.length; j++) {
                            if (lista[j][0].equals(nhs[0][0])) {
                                nhs[0][1] = lista[j][1];
                            }
                        }
                        encadenamiento(nhs);
                    }
                }
            }
        }
    }

    boolean existe(String este, String aqui) {
        int pos = aqui.indexOf(este);
        if (pos != -1) {
            return true;
        }
        return false;
    }

    String generaCC(Object[][] hI) {
        String cc = "", regla = "";
        String[] reglas;
        int j = 0;

        for (int i = 0; i < hI.length; i++) {
            if (hI[i][1] != null) {
                cc += hI[i][1] + ",";
            }
        }

        reglas = cc.split(",");
        cc = "";

        for (int i = 0; i < reglas.length; i++) {
            if (!reglas[i].equals("-1")) {
                cc += reglas[i] + ",";
                j++;
            }
        }

        cc = cc.substring(0, cc.length() - 1);
        return cc;
    }

    String remove(String cc) {
        char fC = cc.charAt(0);
        int cont = 0;
        String newS = "";

        for (int j = 0; j < cc.length(); j++) {
            for (int i = 1; i < cc.length(); i++) {
                if (fC == cc.charAt(j)) {
                    cont++;
                }
                if (cont == 1) {
                    newS += cc;
                }
                fC = cc.charAt(i);
            }
            cont = 0;
        }

        return "";
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Interfaz().setVisible(true);

                } catch (IOException ex) {
                    Logger.getLogger(Interfaz.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnActualizarContenido;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBorrarRegla;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnResolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList<String> listHPo;
    private javax.swing.JList<String> listHPr;
    private javax.swing.JTable tableReglas;
    private javax.swing.JTable tblJustificacion;
    // End of variables declaration//GEN-END:variables
}
