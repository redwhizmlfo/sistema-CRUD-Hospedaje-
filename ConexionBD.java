package hospedajepro;

/*

/**
*
* @author Jesus
*/
import java.sql.Connection; // Clase para manejar la conexión.
import java.sql.DriverManager; // Clase para obtener la conexión con la base de datos.
import java.sql.SQLException; // Manejo de excepciones relacionadas con SQL.
import javax.swing.JOptionPane; // Para mostrar mensajes emergentes.

public class ConexionBD {
    
    // Variables constantes para la configuración de la conexión.
    private final String DRIVER = "oracle.jdbc.driver.OracleDriver"; // Controlador JDBC de Oracle.
    private final String URL = "jdbc:oracle:thin:@//localhost:1521/XE"; // URL de conexión a la base de datos.
    private final String USER = "SYSTEM";
    private final String PASWORD = "123456";

    public Connection cadena;

    public ConexionBD() {
         this.cadena = null;
    }
     /**
     * Constructor de la clase que inicializa la conexión como null.
     */
    public Connection conectar() {
        try {
            Class.forName(DRIVER);
            this.cadena = DriverManager.getConnection(URL, USER, PASWORD);

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        return this.cadena;

    }
     /**
     * Método para cerrar la conexión con la base de datos.
     */
    public void desconectar() {
        try {
            this.cadena.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
