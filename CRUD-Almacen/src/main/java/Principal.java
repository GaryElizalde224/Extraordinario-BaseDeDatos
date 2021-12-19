import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Principal extends JFrame {
    static Firestore bd;
    static String[] datosAcceso;

    public Principal() throws HeadlessException {
        this.setTitle("Inicio de Sesion");
        this.setSize(510, 470);
        this.setLocationRelativeTo(null);
        try {
            conectar();
            BDatos BDregistros = new BDatos();
            try {
                BDregistros.buscarUsuario();
            } catch (InterruptedException | ExecutionException interruptedException) {
                interruptedException.printStackTrace();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión");
        }
        inicializarComponentes();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {
        //Panel de Login
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        //Letra para formulario
        Font letraGeneral = new Font("Century Gothic", Font.PLAIN, 28);
        //Etiqueta Nombre Formulario
        JLabel etiquetaSuperior = new JLabel("Bienvenido");
        etiquetaSuperior.setFont(letraGeneral);
        etiquetaSuperior.setHorizontalAlignment(JLabel.CENTER);
        etiquetaSuperior.setForeground(new Color(38, 38, 38));
        etiquetaSuperior.setBounds(150, 20, 200, 32);
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
        imagenSup.setBounds(158, 70, 170, 160);
        panel.add(imagenSup);


        JLabel Usuario, Contrasena;
        Font letra = new Font("Century Gothic", Font.PLAIN, 23);
        Font letra2 = new Font("Century Gothic", Font.PLAIN, 15);
        //Etiqueta Usuario
        Usuario = new JLabel("Usuario:");
        Usuario.setBounds(100, 250, 150, 30);
        Usuario.setForeground(new Color(38, 38, 38));
        Usuario.setOpaque(false);
        Usuario.setFont(letra);
        panel.add(Usuario);
        //panel.add(round);
        JTextField round3 = new JTextField("");
        round3.setBounds(100, 281, 300, 38);
        round3.setFont(letra2);
        round3.setOpaque(false);
        round3.setForeground(new Color(38, 38, 38));
        round3.setBackground(new Color(0, 0, 0));
        round3.setBorder(new RoundedBorder(10));
        panel.add(round3);
        //Etiqueta Contrasena
        Contrasena = new JLabel("Contraseña:");
        Contrasena.setBounds(100, 320, 150, 30);
        Contrasena.setForeground(new Color(38, 38, 38));
        Contrasena.setOpaque(false);
        Contrasena.setFont(letra);
        panel.add(Contrasena);
        //panel.add(round);
        Font know = new Font("Webdings", Font.PLAIN, 15);
        JTextField round4 = new JTextField("");
        round4.setBounds(100, 350, 300, 38);
        round4.setFont(know);
        round4.setOpaque(false);
        round4.setForeground(new Color(38, 38, 38));
        round4.setBackground(new Color(0, 0, 0));
        round4.setBorder(new RoundedBorder(10));
        panel.add(round4);
        System.out.println(round4.getText());

        //Boton registro
        JButton entrar = new JButton("Entrar");
        entrar.setBounds(180, 400, 150, 25);
        entrar.setForeground(new Color(38, 38, 38));
        entrar.setFont(letra2);
        entrar.setOpaque(false);
        entrar.setFocusable(false);
        entrar.setBorder(new RoundedBorder(20));
        entrar.setBackground(new Color(0, 5, 8));
        entrar.addActionListener(e -> {
            String usuario = round3.getText();
            String pass = round4.getText();
            String total = usuario + "-" + pass;
            boolean existe = false;
            for (String s : datosAcceso) {
                if (s.equals(total)) {
                    existe = true;
                    break;
                } else
                    existe = false;
            }

            if (existe) {
                JOptionPane.showMessageDialog(null, "Inicio de sesion exitoso");
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(null, "Error en credenciales o registrese");
                round3.setText(null);
                round4.setText(null);
            }

        });

        //Panel Secundario
        JPanel panel2 = new JPanel();
        panel.setLayout(null);


        panel.getRootPane().setDefaultButton(entrar);
        panel.add(entrar);

    }

    private void conectar() throws IOException {

        FileInputStream serviceAccount =
                new FileInputStream("CRUD-FIREBASE.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://crud-firebase-96db5-default-rtdb.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        bd = FirestoreClient.getFirestore();

        System.out.println("Se realizo la conexion con exito");

    }

    static class Datos {
        private final String Name;
        private final String Usuario;
        private final String Password;
        private final String Admin;

        public Datos(String name, String usuario, String password, String admin) {
            Name = name;
            Usuario = usuario;
            Password = password;
            Admin = admin;
        }

        public String getName() {
            return Name;
        }

        public String getAdmin() {
            return Admin;
        }

        public String getUsuario() {
            return Usuario;
        }

        public String getPassword() {
            return Password;
        }
    }

    static class BDatos {
        List<Datos> listaRegistros = new ArrayList<>();

        public void buscarUsuario() throws InterruptedException, ExecutionException {
            CollectionReference Registros = bd.collection("Registro");
            ApiFuture<QuerySnapshot> querySnapshot = Registros.get();

            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                Datos registros = new Datos(document.getString("Nombre"), document.getString("Usuario"),
                        document.getString("Password"), document.getString("Admin"));

                listaRegistros.add(registros);
            }
            mostrarRegistros();
        }

        public void mostrarRegistros() {
            Datos[] dat = new Datos[listaRegistros.size()];
            String[] datPrueba = new String[listaRegistros.size()];
            for (int i = 0; i < listaRegistros.size(); i++) {
                dat[i] = listaRegistros.get(i);
                //datPrueba[i] = dat[i].getUsuario() + "-" + dat[i].getPassword();
                datPrueba[i] = dat[i].getName() + " - " +dat[i].getUsuario() + " - " + dat[i].getPassword()+ " - " +dat[i].getAdmin();
            }

            for (String s : datPrueba) {
                System.out.println(s);
            }
            datosAcceso = datPrueba;
        }
    }

}
