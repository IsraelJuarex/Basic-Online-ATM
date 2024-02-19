import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

class ValidInputs {
    public static boolean validarExistUser(String user) {
        ResultSet resultSet;
        try (PreparedStatement selectUser = Objects.requireNonNull(DataBaseConnection.getConnectionToDB())
                .prepareStatement("select usuario from usuario",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY)) {
            resultSet = selectUser.executeQuery();
            resultSet.beforeFirst();
            while (resultSet.next()) {
                String usuario = resultSet.getString(1);
                if (usuario.equalsIgnoreCase(user)) {
                   return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean validCorrectPassword(String usuario, String contrasenia) {
        try (PreparedStatement fechUsers = Objects.requireNonNull(DataBaseConnection.getConnectionToDB())
                .prepareStatement("select usuario , contrasenia  from usuario where usuario = ?")) {
            fechUsers.setString(1, usuario);
            ResultSet resultSet = fechUsers.executeQuery();
            while (resultSet.next()) {
                String user = resultSet.getString(1);
                String pass = resultSet.getString(2);

                if (user.equalsIgnoreCase(usuario) && pass.equals(contrasenia)){
                    resultSet.close();
                    return true;
                }

            }
            resultSet.close();

        } catch (SQLException  e) {
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            new PopupPage("Error conectado al servidor , Asegure que tenga una conexion a internet",400 , 150 ,"");
        }
        return false;
    }
}