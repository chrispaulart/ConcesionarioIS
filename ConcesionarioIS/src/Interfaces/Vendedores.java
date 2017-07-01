package Interfaces;

import Conexion.conectar;

import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.*;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrador
 */
public class Vendedores extends javax.swing.JInternalFrame {
    DefaultTableModel modelo;
     int m, n,o,p;

    /**
     * Creates new form IngresoCliente
     */
    public Vendedores() {
        initComponents();
        bloquear();
        Generarnumeracion();
        Generarnumeracioneje();
        cargar("");
        lblcor.setVisible(false);
        lblcorreo.setVisible(false);
        lblci.setVisible(false);
    }
    int i = 0;
    int j;

  

    boolean cedula(String ced) {
        int vec1[] = new int[5];
        int ci, cp, r;
        int sumim, sumpa, sum, cedu;
        int vec[] = new int[5];
        sumim = 0;
        sumpa = 0;
        if (ced.length() == 10) {
            for (int k = 0; k <= ced.length() - 1; k++) {
                if (ced.charAt(k) < '0' || ced.charAt(k) > '9') {
                    break;
                }
                int m = 0;
                for (int j = 0; j <= ced.length() - 2; j = j + 2) {
                    ci = Integer.parseInt(String.valueOf(ced.charAt(j)));
                    vec[m] = 2 * ci;
                    if (vec[m] >= 9) {
                        vec1[m] = vec[m] - 9;
                    } else {
                        vec1[m] = vec[m];
                    }
                    sumim = sumim + vec1[m];
                    m = m + 1;
                }
                for (int i = 1; i <= ced.length() - 2; i = i + 2) {
                    cp = Integer.parseInt(String.valueOf(ced.charAt(i)));
                    sumpa = sumpa + cp;
                }
                sum = sumim + sumpa;
                if ((sum % 10) == 0) {
                    cedu = sum - sum;
                } else {
                    r = sum % 10;
                    cedu = 10 - r;
                }
                if (cedu == Integer.parseInt(String.valueOf(ced.charAt(9)))) {
                    //System.out.println("Esta Correcta");
                    return true;
                } else {
                    // System.out.println("incorrecta");
                    lblci.setVisible(true);
                    txtced.setText("");

                    String mai = txtemail.getText();
                    if (!mai.matches(".+@.+\\..+")) {

                        txtemail.setText("");
                        lblcorreo.setVisible(true);
                    } else {
                        lblcor.setVisible(true);

                    }
                    return false;
                }

            }
        } else {
            String mail = txtemail.getText();
            if (!mail.matches(".+@.+\\..+")) {

                txtemail.setText("");
                lblcorreo.setVisible(true);
            } else {
                lblcor.setVisible(true);

            }

            lblci.setVisible(true);
            txtced.setText("");
            return false;

        }
        return false;

    }

    void bloquear() {
        txtid.setEnabled(false);
        txtnom.setEnabled(false);
        txtape.setEnabled(false);
        txtusu.setEnabled(false);
        txtemail.setEnabled(false);
        txttel.setEnabled(false);
        txtcon.setEnabled(false);
        txtced.setEnabled(false);
        cbotipo.setEnabled(false);
        btnguardar.setEnabled(false);
        btnnuevo.setEnabled(true);
        btncancelar.setEnabled(false);
        //btnactualizar.setEnabled(false);

    }

    void limpiar() {
        //txtid.setText("");
        txtnom.setText("");
        txtced.setText("");
        txtemail.setText("");
        txtusu.setText("");
        txtcon.setText("");
        txttel.setText("");
        txtape.setText("");

    }

    void desbloquear() {
        txtid.setEnabled(false);
        txtnom.setEnabled(true);
        txtape.setEnabled(true);
        txtusu.setEnabled(true);
        txtemail.setEnabled(true);
        txttel.setEnabled(true);
        txtcon.setEnabled(true);
        txtced.setEnabled(true);
        cbotipo.setEnabled(true);
        btnguardar.setEnabled(true);
        btnnuevo.setEnabled(false);
        btncancelar.setEnabled(true);
       // btnactualizar.setEnabled(false);
        lblcor.setVisible(false);
        lblcorreo.setVisible(false);
    }

    void cargar(String valor) {
        
        int con2=0;
        String[] titulos = {"N.-", "Usuario", "Cedula", "Nombres", "Apellidos", "Telefono", "Email"};
        modelo = new DefaultTableModel(null, titulos);
        String datos[] = new String[7];
        String sql = "select * from usuarios,vendedores WHERE usuarios.ID_USU=vendedores.ID_USU_VEN && CONCAT (ID_USU,'',CED_VEN,'',NOM_VEN,'',APE_VEN) LIKE '%" + valor + "%' ORDER BY ID_USU";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                con2=con2+1;
                //datos[0] = rs.getString("ID_USU");
                datos[0] = String.valueOf(con2);
                datos[1] = rs.getString("USUARIO");
                datos[2] = rs.getString("CED_VEN");
                datos[3] = rs.getString("NOM_VEN");
                datos[4] = rs.getString("APE_VEN");
                datos[5] = rs.getString("TEL_VEN");
                datos[6] = rs.getString("EMAIL_VEN");
                modelo.addRow(datos);

            }
            tbvendedores.setModel(modelo);
//             tbproductos.setModel(model);
            tbvendedores.getColumnModel().getColumn(0).setPreferredWidth(10);
            tbvendedores.getColumnModel().getColumn(1).setPreferredWidth(80);
            tbvendedores.getColumnModel().getColumn(2).setPreferredWidth(80);
            tbvendedores.getColumnModel().getColumn(3).setPreferredWidth(80);
            tbvendedores.getColumnModel().getColumn(4).setPreferredWidth(80);
            tbvendedores.getColumnModel().getColumn(5).setPreferredWidth(80);
            tbvendedores.getColumnModel().getColumn(6).setPreferredWidth(80);

        } catch (SQLException ex) {
            Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     void cargareje(String valor) {
         int con=0;
        String[] titulos = {"N.-", "Usuario", "Cedula", "Nombres", "Apellidos", "Telefono", "Email"};
        modelo = new DefaultTableModel(null, titulos);
        String datos[] = new String[7];
        String sql = "select * from usuarios,ejecutivos_compras WHERE usuarios.ID_USU=ejecutivos_compras.ID_USU_EJEC && CONCAT (ID_USU,'',CED_EJEC,'',NOM_EJEC,'',APE_EJEC) LIKE '%" + valor + "%' ORDER BY ID_USU";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                con=con+1;
               // datos[0] = rs.getString("ID_USU");
                datos[0] =String.valueOf(con);
                datos[1] = rs.getString("USUARIO");
                datos[2] = rs.getString("CED_EJEC");
                datos[3] = rs.getString("NOM_EJEC");
                datos[4] = rs.getString("APE_EJEC");
                datos[5] = rs.getString("TEL_EJEC");
                datos[6] = rs.getString("EMAIL_EJEC");
                modelo.addRow(datos);

            }
            tbvendedores.setModel(modelo);
//             tbproductos.setModel(model);
            tbvendedores.getColumnModel().getColumn(0).setPreferredWidth(10);
            tbvendedores.getColumnModel().getColumn(1).setPreferredWidth(80);
            tbvendedores.getColumnModel().getColumn(2).setPreferredWidth(80);
            tbvendedores.getColumnModel().getColumn(3).setPreferredWidth(80);
            tbvendedores.getColumnModel().getColumn(4).setPreferredWidth(80);
            tbvendedores.getColumnModel().getColumn(5).setPreferredWidth(80);
            tbvendedores.getColumnModel().getColumn(6).setPreferredWidth(80);

        } catch (SQLException ex) {
            Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void Generarnumeracion() {
        String SQL = "select max(ID_USU) from usuarios";
        int c = 0;
        int b = 0;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                c = rs.getInt(1);
            }

            if (c == 0) {
                txtid.setText("1");
            } else {

                txtid.setText("" + (c + 1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    void Guardar(){
        if(cbotipo.getSelectedItem()=="Vendedor"){
    String ced, nom, ape, tel, email, id;

    ced = txtced.getText();
    nom = txtnom.getText();
    ape = txtape.getText();
    tel = txttel.getText();
    email = txtemail.getText();
    id = txtid.getText();

    if (cedula(ced) == true) {
              if (!email.matches(".+@.+\\..+")) {

            txtemail.setText("");
            lblcorreo.setVisible(true);
        } else {
            lblcor.setVisible(true);

            String sql = "INSERT INTO vendedores (CED_VEN,ID_USU_VEN,NOM_VEN,APE_VEN,TEL_VEN,EMAIL_VEN) VALUES (?,?,?,?,?,?)";
            try {

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, ced);
                pst.setString(2, id);
                pst.setString(3, nom);
                pst.setString(4, ape);
                pst.setString(5, tel);
                pst.setString(6, email);

                m = pst.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
            }


            String ins = "INSERT INTO usuarios (ID_USU,USUARIO,PASS_USU) VALUES(?,?,?)";
            try {
                PreparedStatement pst = cn.prepareStatement(ins);
               pst.setString(1, txtid.getText());
                pst.setString(2, txtusu.getText());
                pst.setString(3, new String(txtcon.getPassword()));

                n = pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (m > 0 && n > 0) {

                JOptionPane.showMessageDialog(this, "Se guardaron los datos");
                bloquear();

            } else {
                JOptionPane.showMessageDialog(this, "Error");
            }
        }
    }
    cargar(""); 
    }if(cbotipo.getSelectedItem()=="Comprador"){
        String ced2, nom2, ape2, tel2, email2, id2;

    ced2 = txtced.getText();
    nom2 = txtnom.getText();
    ape2 = txtape.getText();
    tel2 = txttel.getText();
    email2 = txtemail.getText();
    id2 = txtid.getText();

    if (cedula(ced2) == true) {
        //System.out.println("Esta Correcta");
        // lblci.setVisible(false);

        if (!email2.matches(".+@.+\\..+")) {

            txtemail.setText("");
            lblcorreo.setVisible(true);
        } else {
            lblcor.setVisible(true);

            String sql = "INSERT INTO ejecutivos_compras (CED_EJEC,ID_USU_EJEC,NOM_EJEC,APE_EJEC,TEL_EJEC,EMAIL_EJEC) VALUES (?,?,?,?,?,?)";
            try {

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, ced2);
                pst.setString(2, id2);
                pst.setString(3, nom2);
                pst.setString(4, ape2);
                pst.setString(5, tel2);
                pst.setString(6, email2);

                o = pst.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
            }

            String ins = "INSERT INTO usuarios (ID_USU,USUARIO,PASS_USU) VALUES(?,?,?)";
            try {
                PreparedStatement pst = cn.prepareStatement(ins);
                pst.setString(1, txtid.getText());
                pst.setString(2, txtusu.getText());
                pst.setString(3, new String(txtcon.getPassword()));

                p = pst.executeUpdate();


            } catch (SQLException ex) {
                 Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (o > 0 && p > 0) {

                JOptionPane.showMessageDialog(this, "Se guardaron los datos");
                bloquear();

            } else {
                JOptionPane.showMessageDialog(this, "Error");
            }
        }
    }

    cargareje("");
}
   
    }
    void Generarnumeracioneje() {
        String SQL = "select max(ID_USU) from usuarios";
        int c = 0;
        int b = 0;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                c = rs.getInt(1);
            }

            if (c == 0) {
                txtid.setText("1");
            } else {

                txtid.setText("" + (c + 1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        mneliminar = new javax.swing.JMenuItem();
        jpDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtape = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtced = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbotipo = new javax.swing.JComboBox();
        txttel = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtusu = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtcon = new javax.swing.JPasswordField();
        lblcorreo = new javax.swing.JLabel();
        lblcor = new javax.swing.JLabel();
        lblci = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbvendedores = new javax.swing.JTable();
        txtbuscar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        mneliminar.setText("Eliminar");
        mneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mneliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mneliminar);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("REGISTRO DE VENDEDORES");

        jpDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Cliente"));
        jpDatos.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jpDatosMouseMoved(evt);
            }
        });

        jLabel1.setText("ID");

        jLabel2.setText("Nombres:");

        txtnom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnomKeyTyped(evt);
            }
        });

        jLabel3.setText("Apellidos:");

        txtape.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtapeKeyTyped(evt);
            }
        });

        jLabel4.setText("CI:");

        txtced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcedActionPerformed(evt);
            }
        });
        txtced.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcedKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcedKeyTyped(evt);
            }
        });

        jLabel5.setText("Tipo Usuario");

        cbotipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vendedor", "Comprador" }));
        cbotipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbotipoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbotipoMousePressed(evt);
            }
        });
        cbotipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbotipoKeyPressed(evt);
            }
        });

        txttel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttelKeyTyped(evt);
            }
        });

        jLabel6.setText("Telefono:");

        txtemail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtemailKeyPressed(evt);
            }
        });

        jLabel7.setText("Email:");

        jLabel8.setText("Usuario");

        jLabel9.setText("Contraseña");

        lblcorreo.setForeground(new java.awt.Color(204, 0, 0));
        lblcorreo.setText("Correo no válido");

        lblci.setForeground(new java.awt.Color(204, 0, 0));
        lblci.setText("CI no válida");

        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\HermesBot\\Documents\\NetBeansProjects\\ProyectoIS\\src\\Imagenes\\ojo.png")); // NOI18N
        jLabel11.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel11MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel11MouseMoved(evt);
            }
        });
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel11MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel11MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jpDatosLayout = new javax.swing.GroupLayout(jpDatos);
        jpDatos.setLayout(jpDatosLayout);
        jpDatosLayout.setHorizontalGroup(
            jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addGap(10, 10, 10)
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosLayout.createSequentialGroup()
                        .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblcor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblcorreo)
                        .addContainerGap())
                    .addGroup(jpDatosLayout.createSequentialGroup()
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpDatosLayout.createSequentialGroup()
                                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jpDatosLayout.createSequentialGroup()
                                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jpDatosLayout.createSequentialGroup()
                                                .addComponent(txtced, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblci)))
                                        .addGap(21, 21, 21)
                                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel6))))
                                .addGap(18, 18, 18))
                            .addGroup(jpDatosLayout.createSequentialGroup()
                                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbotipo, javax.swing.GroupLayout.Alignment.LEADING, 0, 97, Short.MAX_VALUE)
                                    .addComponent(txtusu, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDatosLayout.createSequentialGroup()
                                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtape, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jpDatosLayout.createSequentialGroup()
                                .addComponent(txtcon, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))))))
        );
        jpDatosLayout.setVerticalGroup(
            jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpDatosLayout.createSequentialGroup()
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtced, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(lblci))
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpDatosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)))
                            .addGroup(jpDatosLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(lblcor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(lblcorreo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtusu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbotipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnnuevo.setText("Nuevo");
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        btnguardar.setText("Guardar");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btncancelar.setText("Cancelar");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnguardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnnuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnnuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnguardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btncancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnsalir)
                .addGap(40, 40, 40))
        );

        tbvendedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbvendedores.setComponentPopupMenu(jPopupMenu1);
        jScrollPane2.setViewportView(tbvendedores);

        txtbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarActionPerformed(evt);
            }
        });
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        jLabel10.setText("BUSCAR:");

        jButton1.setText("Vendedores");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Compradores");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
// TODO add your handling code here:
    this.dispose();
}//GEN-LAST:event_btnsalirActionPerformed

private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
// TODO add your handling code here:
    desbloquear();
    limpiar();
    Generarnumeracion();
Generarnumeracioneje();
    txtid.requestFocus();

}//GEN-LAST:event_btnnuevoActionPerformed

private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
// TODO add your handling code here:
    bloquear();
}//GEN-LAST:event_btncancelarActionPerformed

private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
Guardar();

}//GEN-LAST:event_btnguardarActionPerformed

private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed
// TODO add your handling code here:

}//GEN-LAST:event_txtbuscarActionPerformed

private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
// TODO add your handling code here:
    cargar(txtbuscar.getText());
    cargareje(txtbuscar.getText());
}//GEN-LAST:event_txtbuscarKeyReleased

private void txtcedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcedKeyPressed
// TODO add your handling code here:
    lblci.setVisible(false);
}//GEN-LAST:event_txtcedKeyPressed

private void txtcedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcedKeyTyped
// TODO add your handling code here:
    char car = evt.getKeyChar();
    if (txtced.getText().length() >= 10) {
        evt.consume();
    }
    if ((car < '0' || car > '9')) {
        evt.consume();
    }

    // cedula(txtced.getText());
    j = 1;
}//GEN-LAST:event_txtcedKeyTyped

private void txttelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelKeyTyped
// TODO add your handling code here:
    char car = evt.getKeyChar();
    if (txttel.getText().length() >= 10) {
        evt.consume();
    }
    if ((car < '0' || car > '9')) {
        evt.consume();
    }
}//GEN-LAST:event_txttelKeyTyped

private void txtnomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomKeyTyped
// TODO add your handling code here:
    char car = evt.getKeyChar();
    if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z') && (car != (char) KeyEvent.VK_SPACE)) {
        evt.consume();
    }
}//GEN-LAST:event_txtnomKeyTyped

private void txtapeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapeKeyTyped
// TODO add your handling code here:
    char car = evt.getKeyChar();
    if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z') && (car != (char) KeyEvent.VK_SPACE)) {
        evt.consume();
    }
}//GEN-LAST:event_txtapeKeyTyped
    void IngresoCed() {
        if (txtced.getText().length() < 10) {

            JOptionPane.showMessageDialog(null, "Cedula Incompleta");
        }
    }
private void txtcedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcedActionPerformed
// TODO add your handling code here:
    if (i == 0) {
        if (j > 0) {
            IngresoCed();
        }
    }
}//GEN-LAST:event_txtcedActionPerformed

private void mneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mneliminarActionPerformed
// TODO add your handling code here:
    int fila = tbvendedores.getSelectedRow();
    int fila2 = tbvendedores.getSelectedRow();
    
    
    //System.out.println(fila);
    String cod = "";
    cod = tbvendedores.getValueAt(fila, 0).toString();
    if (fila >= 0){
        try {
//            
            PreparedStatement pst = cn.prepareStatement("DELETE vendedores,usuarios FROM usuarios INNER JOIN vendedores WHERE usuarios.ID_USU=vendedores.ID_USU_VEN LIKE '%" + cod + "%'");
            pst.executeUpdate();
             cargar("");
        } catch (SQLException ex) {
            Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        cargar(cod);
    }  else{
        JOptionPane.showMessageDialog(this, "No ha selecionada ninguna fila");
    }
//    String cod2 = "";
//    cod2 = tbvendedores.getValueAt(fila2, 0).toString();
//    if (fila >= 0){
//        try {
////            
//            PreparedStatement pst2 = cn.prepareStatement("DELETE ejecutivos_compras,usuarios FROM usuarios INNER JOIN ejecutivos_compras WHERE usuarios.ID_USU=ejecutivos_compras.ID_USU_EJEC LIKE '%" + cod2 + "%'");
//            pst2.executeUpdate();
//             cargareje("");
//        } catch (SQLException ex) {
//            Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
//            
//        }
//        cargareje(cod2);
//    }  else{
//        //System.out.println("hola");
//        JOptionPane.showMessageDialog(this, "No ha selecionada ninguna fila");
//    }

}//GEN-LAST:event_mneliminarActionPerformed

    private void txtemailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtemailKeyPressed
        // TODO add your handling code here:
        lblcorreo.setVisible(false);

    }//GEN-LAST:event_txtemailKeyPressed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handl
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MousePressed
        // TODO add your handling code here:
        // txtcon.setEchoChar((char) 0);  
    }//GEN-LAST:event_jLabel11MousePressed

    private void jLabel11MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseReleased
        // TODO add your handling code here:
        // txtcon.setEchoChar('*');  
    }//GEN-LAST:event_jLabel11MouseReleased

    private void jLabel11MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseDragged
        // TODO add your handling code here:
         
    }//GEN-LAST:event_jLabel11MouseDragged

    private void jLabel11MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseMoved
        // TODO add your handling code here:
         txtcon.setEchoChar((char) 0); 
         
    }//GEN-LAST:event_jLabel11MouseMoved

    private void jpDatosMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpDatosMouseMoved
        // TODO add your handling code here:
        txtcon.setEchoChar('*'); 
    }//GEN-LAST:event_jpDatosMouseMoved

    private void cbotipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbotipoKeyPressed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_cbotipoKeyPressed

    private void cbotipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbotipoMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_cbotipoMouseClicked

    private void cbotipoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbotipoMousePressed
        // TODO add your handling code here:
          if(cbotipo.getSelectedIndex()==0){
            cargar("");
        }else if(cbotipo.getSelectedIndex()==1){
        cargareje("");
        }
    }//GEN-LAST:event_cbotipoMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add 'your handling code here:
        cargar("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        cargareje("");
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnsalir;
    private javax.swing.JComboBox cbotipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpDatos;
    private javax.swing.JLabel lblci;
    private javax.swing.JLabel lblcor;
    private javax.swing.JLabel lblcorreo;
    private javax.swing.JMenuItem mneliminar;
    private javax.swing.JTable tbvendedores;
    private javax.swing.JTextField txtape;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtced;
    private javax.swing.JPasswordField txtcon;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnom;
    private javax.swing.JTextField txttel;
    private javax.swing.JTextField txtusu;
    // End of variables declaration//GEN-END:variables
conectar cc = new conectar();
    Connection cn = cc.conexion();
}
