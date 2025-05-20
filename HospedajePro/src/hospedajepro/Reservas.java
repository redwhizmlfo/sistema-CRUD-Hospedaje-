/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package hospedajepro;
import Clases.vReserva;
import Clases.vComboBoxItem;
import static hospedajepro.Inicio.escritorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import javax.swing.JInternalFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jesus
 */
public class Reservas extends javax.swing.JInternalFrame {
    /**
     * Creates new form Reservas
     */
    public Reservas() {
        initComponents();
        ListarDatos();
        ObtenerDatosHabitacion();
        
    }
    
    private void ListarDatos() {
       	
        ConexionBD conexion = new ConexionBD();
        
        try{
            /*  String consulta = "SELECT RESERVAS.idreserva, CLIENTE.nombre, HABITACION.nombre FROM RESERVAS "
                    + "INNER JOIN CLIENTE ON CLIENTE.idcliente = RESERVAS.idcliente "
                    + "INNER JOIN HABITACION ON HABITACION.idhabitacion = RESERVAS.idhabitacion";*/
            
            String consulta = "SELECT RESERVAS.idreserva,( CLIENTE.nombre || ' ' || CLIENTE.apellido)AS CLIENTE, HABITACION.nombre AS HABITACION,RESERVAS.cant_personas,RESERVAS.fechaentrada,RESERVAS.fechasalida,RESERVAS.estado FROM RESERVAS "
                    + "INNER JOIN CLIENTE ON CLIENTE.idcliente = RESERVAS.idcliente "
                    + "INNER JOIN HABITACION ON HABITACION.idhabitacion = RESERVAS.idhabitacion";
            
            Connection con = conexion.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(consulta);
            
            DefaultTableModel modelo = new DefaultTableModel();
            
            ResultSetMetaData metaData = res.getMetaData();
            int numeroColumnas = metaData.getColumnCount();
            for( int i = 1; i <= numeroColumnas; i++ ) {
                modelo.addColumn(metaData.getColumnName(i));
            }
            
            while(res.next()) {
                Object[] fila = new Object[numeroColumnas];
                for( int i = 1; i <= numeroColumnas; i++ ) {
                        fila[(i - 1)] = res.getObject(i);
                }
                modelo.addRow(fila);
            }

            tablalistado.setModel(modelo);
            
        } catch(Exception e){
            System.out.println(e.toString());
        }
    }
    //Metodo para cargar los combox con datos de habitacion
    private void ObtenerDatosHabitacion(){
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.conectar();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
         
         String consulta = "SELECT idhabitacion, nombre FROM HABITACION";
          try {
            con = conexion.conectar();
            pstmt = con.prepareStatement(consulta);
            rs = pstmt.executeQuery();

            // Limpiar el combo antes de agregar nuevos elementos (si es necesario)
            cbotipo_habitacion.removeAllItems();

            // Recorrer todos los registros de la consulta
            while (rs.next()) {
                cbotipo_habitacion.addItem(new vComboBoxItem("", "Seleccione"));
                cbotipo_habitacion.addItem(new vComboBoxItem(rs.getString("idhabitacion"),rs.getString("nombre")));
            }
            } catch (SQLException e) {
                e.printStackTrace(); // Manejo de la excepción
            } finally {
            // Cerrar los recursos utilizados
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    //Metodo para poder obtener el nombre y apellido del cliente
    private void obtenerDatosCliente(String dni) {
        // Aquí debes hacer una consulta a la base de datos para obtener el nombre y apellido del cliente   
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.conectar();
        String consulta = "SELECT idcliente, nombre, apellido FROM CLIENTE WHERE dni = ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(consulta);
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Si se encuentra el cliente, concatenar nombre y apellido y asignarlos a txtDatos
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                // Concatenar nombre y apellido, y asignarlo a txtDatos
                txtDatos.setText(nombre + " " + apellido);
                LabelIdCliente.setText(rs.getString("idcliente"));
            } else {
                // Si no se encuentra el cliente, limpiar txtDatos
                txtDatos.setText("");             
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void RegistrarDatos() {
       	
        ConexionBD conexion = new ConexionBD();

            try { 
            // Recopilar información del formulario y asignarla al objeto
            vReserva dts = new vReserva();
            Connection con = conexion.conectar();

            // Recopilar información del formulario
            dts.setIdCliente(Integer.parseInt(LabelIdCliente.getText()));
            vComboBoxItem selectedItem = (vComboBoxItem) cbotipo_habitacion.getSelectedItem();
            dts.setIdHabitacion(Integer.parseInt(selectedItem.getId()));
            dts.setCantPersonas(Integer.parseInt(txtCantidad.getText()));
            dts.setFechaEntrada(txtxFechaIngreso.getText());
            dts.setFechaSalida(txtFechaSalidad.getText());
            dts.setEstado(cboestado.getSelectedItem().toString());
            String consulta = "";
            String mensaje = "";

            // Consulta SQL para registrar los datos en la TABLA USUARIOS
            consulta = "INSERT INTO RESERVAS (idcliente, idhabitacion, cant_personas, fechaentrada, fechasalida, estado)" + "VALUES (?,?,?,?,?,?)";
 
            // Preparar la consulta para insertar la nueva reserva
            PreparedStatement pstmt = con.prepareStatement(consulta);
            pstmt.setInt(1, dts.getIdCliente());
            pstmt.setInt(2, dts.getIdHabitacion());
            pstmt.setInt(3, dts.getCantPersonas()); // Usamos setInt para los enteros
            pstmt.setDate(4, Date.valueOf(dts.getFechaEntrada())); // Si es tipo Date, convertir antes
            pstmt.setDate(5, Date.valueOf(dts.getFechaSalida())); // Si es tipo Date, convertir antes
            pstmt.setString(6, dts.getEstado());
           

               // Ejecutar la consulta
            pstmt.executeUpdate();
            
            //REVISAR DESPUES
             // Actualizar el estado de la habitación en función del estado de la reserva
                String updateHabitacion = "";
                if ("Confirmado".equals(dts.getEstado())) {
                    updateHabitacion = "UPDATE HABITACION SET estado = 'Ocupado' WHERE idhabitacion = ?";
                } else if ("Cancelado".equals(dts.getEstado())) {
                    updateHabitacion = "UPDATE HABITACION SET estado = 'Disponible' WHERE idhabitacion = ?";
                }

                if (!updateHabitacion.isEmpty()) {
                    PreparedStatement pstmtUpdateHabitacion = con.prepareStatement(updateHabitacion);
                    pstmtUpdateHabitacion.setInt(1, dts.getIdHabitacion());
                    pstmtUpdateHabitacion.executeUpdate();
                }
            mensaje = "Usuario Registrado!!";
             ListarDatos();

            System.out.println(mensaje);
        } catch(Exception e){
            System.out.println(e.toString());
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDatos = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cboestado = new javax.swing.JComboBox();
        btnbuscahabitacion = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        txtxFechaIngreso = new java.awt.TextField();
        txtFechaSalidad = new java.awt.TextField();
        cbotipo_habitacion = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtCantidad = new java.awt.TextField();
        LabelIdCliente = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablalistado = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        btnbuscar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        btnrealizarpagos = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("RESERVA DE HABITACIÓN ");

        jPanel1.setBackground(new java.awt.Color(255, 204, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Registro de Reservas"));

        jLabel6.setText("Habitación:");

        txtCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteActionPerformed(evt);
            }
        });

        jLabel7.setText("Cliente:");

        txtDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDatosActionPerformed(evt);
            }
        });

        jLabel12.setText("Fecha Ingreso:");

        jLabel13.setText("Fecha Salida:");

        jLabel14.setText("Estado Reserva:");

        cboestado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Confirmada", "Cancelada" }));
        cboestado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboestadoActionPerformed(evt);
            }
        });

        btnbuscahabitacion.setText("...");
        btnbuscahabitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscahabitacionActionPerformed(evt);
            }
        });

        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/guardar.png"))); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        txtxFechaIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtxFechaIngresoActionPerformed(evt);
            }
        });

        cbotipo_habitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbotipo_habitacionActionPerformed(evt);
            }
        });

        jLabel2.setText("Cantidad Persona:");

        LabelIdCliente.setText("LabelIdCliente");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cboestado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtxFechaIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtFechaSalidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbotipo_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addGap(25, 25, 25)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbuscahabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LabelIdCliente))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(LabelIdCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(btnbuscahabitacion)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbotipo_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtxFechaIngreso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaSalidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboestado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(38, 38, 38)
                .addComponent(btnRegistrar)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Reservas"));

        tablalistado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablalistado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablalistadoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablalistado);

        jLabel15.setText("Buscar");

        btnbuscar.setBackground(new java.awt.Color(51, 51, 51));
        btnbuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/buscar.png"))); // NOI18N
        btnbuscar.setText("Buscar");
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });

        btneliminar.setBackground(new java.awt.Color(51, 51, 51));
        btneliminar.setForeground(new java.awt.Color(255, 255, 255));
        btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/eliminar.png"))); // NOI18N
        btneliminar.setText("Eliminar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btnsalir.setBackground(new java.awt.Color(51, 51, 51));
        btnsalir.setForeground(new java.awt.Color(255, 255, 255));
        btnsalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/salir.gif"))); // NOI18N
        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        btnrealizarpagos.setText("Realizar Pagos");
        btnrealizarpagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrealizarpagosActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(51, 51, 51));
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/nuevo.GIF"))); // NOI18N
        btnModificar.setText("Modificar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btneliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnsalir)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar))
                    .addComponent(btnrealizarpagos, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar)
                    .addComponent(btneliminar)
                    .addComponent(btnsalir)
                    .addComponent(btnModificar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnrealizarpagos)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteActionPerformed
        // TODO add your handling code here:
        String dni = txtCliente.getText();

        // Verificar que el DNI tiene 8 caracteres (asumiendo que es un DNI válido de Perú)
        if (dni.length() == 8) {
            // Llamar a la función para obtener los datos del cliente
            obtenerDatosCliente(dni);
        } else {
            // Mostrar un mensaje de advertencia si el DNI no tiene 8 caracteres
        }
    }//GEN-LAST:event_txtClienteActionPerformed

    private void txtDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDatosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDatosActionPerformed

    private void cboestadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboestadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboestadoActionPerformed

    private void btnbuscahabitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscahabitacionActionPerformed
        // TODO add your handling code here:
         for (JInternalFrame frame : escritorio.getAllFrames()) {
        frame.dispose(); // Cierra el JInternalFrame
        }
        Clientes form =new Clientes();
        escritorio.add(form);
        form.toFront();
        form.setVisible(true);

    }//GEN-LAST:event_btnbuscahabitacionActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:
        RegistrarDatos();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void cbotipo_habitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbotipo_habitacionActionPerformed
        // TODO add your handling code here:
       // transferFocus();
    }//GEN-LAST:event_cbotipo_habitacionActionPerformed

    private void tablalistadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablalistadoMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tablalistadoMouseClicked

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnbuscarActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnsalirActionPerformed

    private void btnrealizarpagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrealizarpagosActionPerformed
        // TODO add your handling code here:
        for (JInternalFrame frame : escritorio.getAllFrames()) {
        frame.dispose(); // Cierra el JInternalFrame
        }
        Pagos form =new Pagos();
        escritorio.add(form);
        form.toFront();
        form.setVisible(true);
    }//GEN-LAST:event_btnrealizarpagosActionPerformed

    private void txtxFechaIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtxFechaIngresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtxFechaIngresoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelIdCliente;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnbuscahabitacion;
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnrealizarpagos;
    private javax.swing.JButton btnsalir;
    private javax.swing.JComboBox cboestado;
    private javax.swing.JComboBox cbotipo_habitacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablalistado;
    private java.awt.TextField txtCantidad;
    public static javax.swing.JTextField txtCliente;
    public static javax.swing.JTextField txtDatos;
    private java.awt.TextField txtFechaSalidad;
    private javax.swing.JTextField txtbuscar;
    private java.awt.TextField txtxFechaIngreso;
    // End of variables declaration//GEN-END:variables
}
