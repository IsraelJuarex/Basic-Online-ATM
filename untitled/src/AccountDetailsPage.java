import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountDetailsPage extends JFrame implements ActionListener {
    // private static final String NUMBER_REGEX = "@\"-?\\d+(?:\\.\\d+)?\"\n";
    private static final String NUMBER_REGEX = "^[+]?\\d+([.]\\d+)?$";
    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
    private JLabel labelNombre;
    private JButton depositarButton;
    private JButton retirarButton;
    private JButton cerrarSesionButton;
    private JPanel panelAccounDetals;
    private JLabel labelSaldoActual;
    private JPanel panelAccountButtonsDetails;
    private JPanel panelRetirarDinero;
    private JTextField txtRetirarDinero;
    private JButton botonConfirmarRetirar;
    private JButton botonRegresarRetirar;
    private JLabel errorRetirarLabel;
    private JButton botonDepositarConfirmar;
    private JTextField txtDepositarDinero;
    private JPanel panelDepositarDinero;
    private JButton botonDepostarRegresar;
    private JLabel errorDepostiarLabel;
    static ResultSet resultSet;



    public double getTxtRetirarDinero() {

        try{
        Matcher matcher = NUMBER_PATTERN.matcher(txtRetirarDinero.getText());
        if (matcher.matches()){
           errorRetirarLabel.setVisible(false);
           return Double.parseDouble(txtRetirarDinero.getText());
        }
        else {
            errorRetirarLabel.setVisible(true);
            errorRetirarLabel.setText("<html><nobr><font color='red'> Porfavor ingrese una cantidad valida </font><html><nobr>");
            return 0;
        }

        }
        catch(NumberFormatException e){
            errorRetirarLabel.setVisible(true);
            errorRetirarLabel.setText("<html><nobr><font color='red'> Porfavor ingrese una cantidad valida </font><html><nobr>");
            return 0;
        }
    }

    public double geTxtDepositarDinero() {

        try{
            Matcher matcher = NUMBER_PATTERN.matcher(txtDepositarDinero.getText());
            if (matcher.matches()){
                errorDepostiarLabel.setVisible(false);
                return Double.parseDouble(txtDepositarDinero.getText());
            }
            else {
                errorDepostiarLabel.setVisible(true);
                errorDepostiarLabel.setText("<html><nobr><font color='red'> Porfavor ingrese una cantidad valida </font><html><nobr>");
                return 0;
            }

        }
        catch(NumberFormatException e){
            errorDepostiarLabel.setVisible(true);
            errorDepostiarLabel.setText("<html><nobr><font color='red'> Porfavor ingrese una cantidad valida </font><html><nobr>");
            return 0;
        }
    }

    public AccountDetailsPage(String usuario) throws SQLException {
        JFrame frame = new JFrame("Banco Israel INC");
        frame.setPreferredSize(new Dimension(400, 200));
        frame.setResizable(false);
        frame.add(panelAccounDetals);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panelRetirarDinero.setVisible(false);
        errorRetirarLabel.setVisible(false);
        errorDepostiarLabel.setVisible(false);
        panelDepositarDinero.setVisible(false);



        try (PreparedStatement selectDatos = Objects.requireNonNull(DataBaseConnection.getConnectionToDB()).prepareStatement("SELECT * FROM usuario WHERE usuario = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); PreparedStatement updateDinero = Objects.requireNonNull(DataBaseConnection.getConnectionToDB()).prepareStatement("UPDATE usuario SET dinero = ? WHERE usuario = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, resultSet.CONCUR_UPDATABLE)) {
            selectDatos.setString(1, usuario);

            resultSet = selectDatos.executeQuery();

            if (resultSet.next()) {
                labelNombre.setText("<html><nobr>Bienvenid@ <font color='blue'> "+ resultSet.getString("nombre") + " </font><html><nobr>");
                labelSaldoActual.setText("<html><nobr>Balance actual: $<font color='blue'> "+ resultSet.getDouble("dinero") + " </font><html><nobr>");
            }

            resultSet.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        botonDepositarConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (geTxtDepositarDinero() != 0){
                    try (PreparedStatement selectDinero = Objects.requireNonNull(DataBaseConnection.getConnectionToDB()).prepareStatement("SELECT dinero FROM usuario WHERE usuario = ?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                         PreparedStatement updateDinero = Objects.requireNonNull(DataBaseConnection.getConnectionToDB()).prepareStatement("UPDATE usuario SET dinero = ? WHERE usuario = ?")) {

                        double dineroDisponible = 0;
                        double nuevoTotal;

                        selectDinero.setString(1, usuario);
                        resultSet = selectDinero.executeQuery();

                        if (resultSet.next()) {
                            dineroDisponible = resultSet.getDouble(1);
                            System.out.println(dineroDisponible);

                                nuevoTotal = dineroDisponible + geTxtDepositarDinero();
                                updateDinero.setDouble(1, nuevoTotal);
                                updateDinero.setString(2, usuario);
                                updateDinero.executeUpdate();
                                new PopupPage("Cantidad depositada con exito!",50,50,"");
                                labelSaldoActual.setText("<html><nobr>Balance actual: $<font color='blue'> "+ nuevoTotal + " </font><html><nobr>");
                                txtDepositarDinero.setText("");

                            }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }  });

        botonDepostarRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelAccountButtonsDetails.setVisible(true);
                panelDepositarDinero.setVisible(false);
            }
        });

        botonRegresarRetirar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelAccountButtonsDetails.setVisible(true);
                panelRetirarDinero.setVisible(false);
            }
        });



        botonConfirmarRetirar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (getTxtRetirarDinero() != 0){
                try (PreparedStatement selectDinero = Objects.requireNonNull(DataBaseConnection.getConnectionToDB()).prepareStatement("SELECT dinero FROM usuario WHERE usuario = ?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                     PreparedStatement updateDinero = Objects.requireNonNull(DataBaseConnection.getConnectionToDB()).prepareStatement("UPDATE usuario SET dinero = ? WHERE usuario = ?")) {

                    double dineroDisponible = 0;
                    double nuevoTotal;

                    selectDinero.setString(1, usuario);
                    resultSet = selectDinero.executeQuery();

                    if (resultSet.next()) {
                        dineroDisponible = resultSet.getDouble(1);
                        System.out.println(dineroDisponible);
                        if (dineroDisponible > getTxtRetirarDinero()) {
                            nuevoTotal = dineroDisponible - getTxtRetirarDinero();
                            updateDinero.setDouble(1, nuevoTotal);
                            updateDinero.setString(2, usuario);
                            updateDinero.executeUpdate();
                            new PopupPage("Cantidad retirada con exito!",100,100,"");
                            labelSaldoActual.setText("<html><nobr>Balance actual: $<font color='blue'> "+ nuevoTotal + " </font><html><nobr>");
                            txtRetirarDinero.setText("");

                        }
                        else{

                            errorRetirarLabel.setVisible(true);
                            errorRetirarLabel.setText("<html><nobr><font color='red'> No dispone de esa cantidad a retirar </font><html><nobr>");
                        }

                    }
                    resultSet.close();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        }  });


        retirarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                panelAccountButtonsDetails.setVisible(false);
                panelRetirarDinero.setVisible(true);
            }
        });

        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelAccountButtonsDetails.setVisible(false);
                panelDepositarDinero.setVisible(true);
            }
        });

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new LoginPage();
            }
        });



    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}