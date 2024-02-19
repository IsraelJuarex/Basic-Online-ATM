import org.apache.commons.text.WordUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarPage extends JFrame implements ActionListener {
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    private static final String NAME_REGEX = "^([a-zA-Z]{1,}[a-zA-Z]{1,}'?-?[a-zA-Z]{2,} ?([a-zA-Z]{1,})?)";
    private static final String EMAIL_REGEX = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);
    private JFrame frame;
    private JPasswordField passwordVerify;
    private JPasswordField password;
    private JTextField txtApellido;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JTextField txtUsuario;
    private JButton aceptarButton;
    private JButton regresarButton;
    private JPanel registarPanel;
    private JLabel labelPasswordVerify;
    private JLabel labelEdad;
    private JLabel labelError;
    private JLabel labelNombre;
    private JLabel labelUsuario;
    private JLabel labelApellido;
    private JLabel labelPassword;
    private JLabel labelCorreo;
    private JTextField txtCorreo;

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public RegistrarPage() {
        frame = new JFrame("Registro");
        labelPassword.setVisible(true);
        labelError.setVisible(false);
        frame.setPreferredSize(new Dimension(350, 450));
        frame.setResizable(false);
        frame.add(registarPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        aceptarButton.addActionListener(new ActionListener() {

            private void entradasValidasParaRegistro(boolean nombre, boolean apellido, boolean edad, boolean usuario, boolean contrasenia , boolean correo) {
                String fullName = String.join(" ", getTextNombre(), getTextApellido());
                if ((nombre && edad && usuario && contrasenia && correo)) {
                    if (Usuario.RegistrarUsuario(fullName, getTextEdad(), getTextUsuario(), getPassword(), getTextCorreo())){
                        new PopupPage("Usuario registrado exitosamente! Porfavor incia sesion", 400, 100, "LoginPage");
                        frame.setVisible(false);
                    }else {
                        frame.setVisible(false);
                        new PopupPage("Hubo un problema registrando el usuario, profavor contact al adminstrador", 400, 100, "LoginPage");
                    }
                }
            }

            private boolean validarCampoEmail() {
                Matcher matcher = EMAIL_PATTERN.matcher(getTextCorreo());
                if (getTextCorreo().equals("")) {
                    labelCorreo.setText("<html><nobr> Correo <font color='#E51C25'>Ingresa tu correo</font><html><nobr>");
                } else if (matcher.matches()) {
                    labelCorreo.setText("Correo");
                    return true;
                } else {
                    labelCorreo.setText("<html><nobr> Correo <font color='#E51C25'> Correo invalido</font><html><nobr>");
                }
                return false;
            }

            private boolean validarPassword() {
                Matcher matcher = PASSWORD_PATTERN.matcher(getPassword());
                if (getPassword().equals("")) {
                    labelPassword.setText("<html><nobr> Password <font color='#E51C25'>Ingresa tu contraseña </font><html><nobr>");
                } else if (!matcher.matches()) {
                    labelPassword.setText(" Contraseña ");
                    if (!getPassword().equals(getPasswordVerify())) {
                        labelPasswordVerify.setText("<html><nobr> Verificar contraseña  <font color='#E51C25'> Contraseñas no coinciden </font><html><nobr>");
                    } else {
                        labelPasswordVerify.setText(" Verificar contraseña ");
                        return true;
                    }
                } else {
                    labelPassword.setText("<html><nobr> Contraseña  <font color='#E51C25'> Contraseña invalida </font><html><nobr>");
                }
                return false;
            }

            private boolean validarCampoString(String campo) {
                if (campo.equals("Nombre")) {
                    txtNombre.setText(WordUtils.capitalizeFully(getTextNombre()));
                    Matcher matcher = NAME_PATTERN.matcher(getTextNombre());
                    if (getTextNombre().equals("")) {
                        labelNombre.setText("<html><nobr> " + campo + " <font color='#E51C25'>Ingresa tu " + campo + "  </font><html><nobr>");
                    } else if (!matcher.matches()) {
                        labelNombre.setText("<html><nobr> " + campo + "  <font color='#E51C25'> " + campo + "  invalido</font><html><nobr>");
                    } else {
                        labelNombre.setText(campo);
                        return true;
                    }
                } else if (campo.equals("Apellido")) {
                    txtApellido.setText(WordUtils.capitalizeFully(getTextApellido()));
                    Matcher matcher = NAME_PATTERN.matcher(getTextApellido());
                    if (getTextApellido().equals("")) {
                        labelApellido.setText("<html><nobr> " + campo + " <font color='#E51C25'>Ingresa tus " + campo + "  </font><html><nobr>");
                    } else if (!matcher.matches()) {
                        labelApellido.setText("<html><nobr> " + campo + "  <font color='#E51C25'> " + campo + "  invalido</font><html><nobr>");
                    } else {
                        labelApellido.setText(campo);
                        return true;
                    }
                }
                return false;
            }

            private boolean validarCampoEdad() {
                if (getTextEdad() < 17 && getTextEdad() != 0) {
                    labelEdad.setText("<html><nobr> Edad <font color='#E51C25'> Tienes que ser mayor de 16 </font><html><nobr>");
                } else if (getTextEdad() == 0) {
                    labelEdad.setText("<html><nobr> Edad <font color='#E51C25'> Porfavor ingrese su edad </font><html><nobr>");
                } else {
                    labelEdad.setText("Edad");
                    return true;
                }
                return false;
            }

            private boolean validarCampoUsuario() {
                Matcher matcher = USERNAME_PATTERN.matcher(getTextUsuario());
                if (getTextUsuario().equals(""))
                    labelUsuario.setText("<html><nobr> Usuario <font color='#E51C25'>Ingresa tu usuario </font><html><nobr>");

                else if (!matcher.matches())
                    labelUsuario.setText("<html><nobr>Usuario <font color='#E51C25'> Porfavor intente otro usuario </font><html><nobr>");

                else {
                    labelUsuario.setText("Usuario ");
                }

                if (ValidInputs.validarExistUser(getTextUsuario())) {
                    labelUsuario.setText("<html><nobr>Usuario <font color='#E51C25'> Usuario ya registrado , Elgia otro usuario</font><html><nobr>");
                } else {
                    return true;
                }
                return false;
            }


            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    validarCampoUsuario();
                    validarCampoString("Nombre");
                    validarCampoString("Apellidos");
                    validarCampoEdad();
                    validarPassword();
                    validarCampoEmail();
                    entradasValidasParaRegistro(validarCampoString("Nombre"), validarCampoString("Apellido"), validarCampoEdad(), validarCampoUsuario(), validarPassword() , validarCampoEmail());
                } catch (NumberFormatException ex) {
                    System.out.println(ex);
                    labelError.setText("<html><nobr> <font color='#E51C25'> Error porfavor reporta este error a tu admin </font><html><nobr>");
                    labelError.setVisible(true);
                }
            }

        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new LoginPage();
            }
        });
    }

    public int getTextEdad() {
        if (txtEdad.getText().equals("")) {
            return 0;
        }
        return Integer.parseInt((txtEdad.getText()));
    }

    public String getTextNombre() {
        return txtNombre.getText();
    }

    public String getTextUsuario() {
        return txtUsuario.getText();
    }

    public String getTextApellido() {
        return txtApellido.getText();
    }

    public String getPassword() {
        return new String(password.getPassword());
    }

    public String getPasswordVerify() {
        return new String(passwordVerify.getPassword());
    }

    public String getTextCorreo() {
        return txtCorreo.getText();
    }
}

