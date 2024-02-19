import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopupPage {
    private JPanel panelPopup;
    private JButton OKButton;
    private JLabel labelMensaje;
    private final JFrame frame;


    public PopupPage(String Mensaje , int width , int height, String windowToOpen) {
        frame = new JFrame("Banco Israel INC");
        frame.setPreferredSize(new Dimension(500,150));
        labelMensaje.setText(Mensaje);
        frame.setResizable(false);
        frame.add(panelPopup);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.setVisible(false);
                if (windowToOpen.equals("LoginPage"))
                    new LoginPage();
            }
        });
    }
}
