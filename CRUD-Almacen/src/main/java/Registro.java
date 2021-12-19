
import com.google.api.core.ApiFuture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class Registro extends JFrame {
    static Firestore bd;

    public Registro() throws HeadlessException {
        this.setTitle("Registro");
        this.setSize(500, 620);
        this.setLocationRelativeTo(null);
        //this.setUndecorated(true);
        //this.setBackground(new Color(0, 0, 0, 230));
        try {
            conectar();
        } catch (Exception e) {
            e.getStackTrace();
        }
        inicializarComponentes();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        //panel.setOpaque(false);
        panel.setLayout(null);

        //Letra para formulario
        Font letraGeneral = new Font("Century Gothic", Font.PLAIN, 28);
        //Etiqueta Nombre Formulario
        JLabel etiquetaSuperior = new JLabel("Registro");
        etiquetaSuperior.setFont(letraGeneral);
        etiquetaSuperior.setHorizontalAlignment(JLabel.CENTER);
        etiquetaSuperior.setForeground(new Color(38, 38, 38));
        etiquetaSuperior.setBounds(170, 20, 145, 32);
        panel.add(etiquetaSuperior);
        //Imagen superior
        BufferedImage img3 = null;
        try {
            img3 = ImageIO.read(new File("C:\\Users\\jesus\\Downloads\\RecursosBD\\carrito.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert img3 != null;
        Image dimensionarIMG3 = img3.getScaledInstance(170, 160, Image.SCALE_SMOOTH);
        ImageIcon imageIcon3 = new ImageIcon(dimensionarIMG3);

        JLabel imagenSup = new JLabel(imageIcon3);
        imagenSup.setBounds(158, 60, 170, 160);
        panel.add(imagenSup);

        //Etiqueta Nombre
        JLabel Nombre, Correo, Usuario, Contrasena;
        Nombre = new JLabel("Nombre:");
        Font letra = new Font("Century Gothic", Font.PLAIN, 23);
        Font letra2 = new Font("Century Gothic", Font.PLAIN, 15);

        Nombre.setBounds(100, 250, 150, 30);
        Nombre.setForeground(new Color(38, 38, 38));
        Nombre.setOpaque(false);
        Nombre.setFont(letra);
        panel.add(Nombre);
        //Cuadro de texto

        //panel.add(round1);
        JTextField round = new JTextField("");
        round.setBounds(100, 281, 300, 38);
        round.setFont(letra2);
        round.setOpaque(false);
        round.setHorizontalAlignment(JTextField.CENTER);
        round.setForeground(new Color(38, 38, 38));
        round.setBackground(new Color(0, 0, 0, 10));
        round.setBorder(new RoundedBorder(10));
        panel.add(round);

        /*//Etiqueta Correo
        Correo = new JLabel("Correo:");
        Correo.setBounds(100, 320, 150, 30);
        Correo.setForeground(new Color(38, 38, 38));
        Correo.setOpaque(false);
        Correo.setFont(letra);
        panel.add(Correo);
        //panel.add(round);
        JTextField round2 = new JTextField("");
        round2.setBounds(100, 350, 300, 38);
        round2.setFont(letra2);
        round2.setOpaque(false);
        round2.setForeground(new Color(38, 38, 38));
        round2.setBackground(new Color(0, 0, 0, 10));
        round2.setBorder(new RoundedBorder(10));
        panel.add(round2);
        */

        //Etiqueta Usuario
        Usuario = new JLabel("Usuario:");
        Usuario.setBounds(100, 320, 150, 30);
        Usuario.setForeground(new Color(38, 38, 38));
        Usuario.setOpaque(false);
        Usuario.setFont(letra);
        panel.add(Usuario);
        //panel.add(round);
        JTextField round3 = new JTextField("");
        round3.setBounds(100, 350, 300, 38);
        round3.setFont(letra2);
        round3.setHorizontalAlignment(JTextField.CENTER);
        round3.setOpaque(false);
        round3.setForeground(new Color(38, 38, 38));
        round3.setBackground(new Color(0, 0, 0, 10));
        round3.setBorder(new RoundedBorder(10));
        panel.add(round3);
        //Etiqueta Contrasena
        Contrasena = new JLabel("Contraseña:");
        Contrasena.setBounds(100, 390, 150, 30);
        Contrasena.setForeground(new Color(38, 38, 38));
        Contrasena.setOpaque(false);
        Contrasena.setFont(letra);
        panel.add(Contrasena);
        //panel.add(round);
        Font know = new Font("Webdings", Font.PLAIN, 15);
        JTextField round4 = new JTextField("");
        round4.setBounds(100, 420, 300, 38);
        round4.setFont(letra2);
        round4.setOpaque(false);
        round4.setForeground(new Color(38, 38, 38));
        round4.setBackground(new Color(0, 0, 0, 10));
        round4.setBorder(new RoundedBorder(10));
        panel.add(round4);
        System.out.println(round4.getText());
        //Division Admin
        JCheckBox checkAdmin = new JCheckBox("Administrador");
        checkAdmin.setBounds(100, 470, 300, 30);
        checkAdmin.setFont(letra2);
        checkAdmin.setOpaque(false);
        checkAdmin.setForeground(new Color(38, 38, 38));
        final boolean[] statAdmin = {false};
        //listener checkAdmin
        checkAdmin.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(e.getStateChange());
                if (e.getStateChange() == 1) {
                    System.out.println("check");
                    statAdmin[0] = true;
                } else {
                    System.out.println("Uncheck");
                    statAdmin[0] = false;
                }
                System.out.println(e.getStateChange() + " final");
                System.out.println(statAdmin[0]);
            }
        });
        panel.add(checkAdmin);

        //Imagen Hide
        BufferedImage hideimg = null;
        try {
            hideimg = ImageIO.read(new File("C:\\Users\\jesus\\Documents\\Projects-NetBeans\\UniversidadParcial2\\src\\Imagenes\\hide.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert hideimg != null;
        Image dimensionarHide = hideimg.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon imageIconHide = new ImageIcon(dimensionarHide);
        //Imagen Show
        BufferedImage showimg = null;
        try {
            showimg = ImageIO.read(new File("C:\\Users\\jesus\\Documents\\Projects-NetBeans\\UniversidadParcial2\\src\\Imagenes\\show.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert showimg != null;
        Image dimensionarShow = showimg.getScaledInstance(38, 38, Image.SCALE_SMOOTH);
        ImageIcon imageIconShow = new ImageIcon(dimensionarShow);

        //Boton Password
        JButton hideShow = new JButton();
        hideShow.setIcon(imageIconHide);
        hideShow.setBounds(400, 490, 38, 38);
        hideShow.setBorder(null);
        hideShow.setOpaque(false);
        hideShow.setBackground(new Color(0, 0, 1, 100));
        panel.add(hideShow);
        //Boton registro
        JButton registro = new JButton("Registrar");
        registro.setBounds(180, 540, 150, 25);
        registro.setForeground(new Color(38, 38, 38));
        registro.setFont(letra2);
        registro.setOpaque(false);
        registro.setFocusable(false);
        registro.setBorder(new RoundedBorder(20));
        registro.setBackground(new Color(0, 5, 8, 12));

        registro.addActionListener(e -> {
            String Nombre1 = round.getText();
            String Usuario1 = round3.getText();
            String Pass = round4.getText();
            String adminDude = String.valueOf(statAdmin[0]);
            Map<String, Object> datosRegistro = new HashMap<>();
            datosRegistro.put("Nombre", Nombre1);
            //datosRegistro.put("Correo", Correo1);
            datosRegistro.put("Usuario", Usuario1);
            datosRegistro.put("Password", Pass);
            datosRegistro.put("Admin",adminDude);
            String uuid = java.util.UUID.randomUUID().toString();
            insertarDatos("Registro", uuid, datosRegistro);

            round.setText(null);
            //round2.setText(null);
            round3.setText(null);
            round4.setText(null);


        });

        panel.add(registro);
        panel.getRootPane().setDefaultButton(registro);

    }

    private void conectar() throws IOException {


        FileInputStream serviceAccount =
                new FileInputStream("CRUD-FIREBASE.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://crud-firebase-96db5-default-rtdb.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);



        /*FileInputStream serviceAccount
                = new FileInputStream("ordinarioBD.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://ordinariobd-default-rtdb.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

         */

        bd = FirestoreClient.getFirestore();

        System.out.println("Se realizo la conexion con exito");

    }

    public static boolean insertarDatos(
            String coleccion,
            String documento,
            Map<String, Object> data) {
        try {
            DocumentReference docRef = bd.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Hora de actualizacion: " + result.get().getUpdateTime());

            return true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return false;
    }
}