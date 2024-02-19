import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginPage extends JFrame implements ActionListener {

    private JTextField usuario;
    private JPasswordField contrasenia;
    private JButton loginButton;
    private JPanel mainPanel;
    private JButton crearUnaCuentaButton;
    private JLabel errorLabel;
    private final JFrame frame;

    public LoginPage(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame = new JFrame("Banco Israel INC ");
        frame.setPreferredSize(new Dimension(250,250));
        frame.setResizable(false);
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        errorLabel.setVisible(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        if (ValidInputs.validCorrectPassword(getUsuario(), getContrasenia())){
                            errorLabel.setVisible(false);
                            frame.setVisible(false);
                            try {

                                new AccountDetailsPage(getUsuario());

                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }


                        }
                        else{
                            errorLabel.setVisible(true);
                            errorLabel.setText("<html><nobr><font color='#E51C25'> Usuario/Contraseña incorrectos</font><html><nobr>");
                        }
            }
        });

        crearUnaCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               frame.setVisible(false);
               new RegistrarPage();
            }
        });


    }
    public String getUsuario(){
        return usuario.getText();
    }
    public String getContrasenia(){
        return new String(contrasenia.getPassword());
    }
    public void actionPerformed(ActionEvent e) {

    }
}


