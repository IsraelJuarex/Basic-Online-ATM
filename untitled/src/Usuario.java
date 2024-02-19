import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

class Usuario {

    static protected boolean RegistrarUsuario(String nombre, int edad, String usuario, String contrasenia, String correo) {

            try {
                PreparedStatement updateUsuario = Objects.requireNonNull(DataBaseConnection.getConnectionToDB()).prepareStatement("INSERT INTO usuario (nombre,edad,usuario,contrasenia,correo)VALUES (?, ?, ?, ?, ?)");
                updateUsuario.setString(1, nombre);
                updateUsuario.setInt(2, edad);
                updateUsuario.setString(3, usuario);
                updateUsuario.setString(4, contrasenia);
                updateUsuario.setString(5, correo);
                updateUsuario.executeUpdate();
                updateUsuario.close();
                return true;
            } catch (SQLException e) {
                System.out.println("Error connecting to database, Please contact the application developer");
                e.printStackTrace();
                return false;
            }
    }

}