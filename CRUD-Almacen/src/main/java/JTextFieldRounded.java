import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;


public class JTextFieldRounded extends JTextField {
    private final Dimension d = new Dimension(250, 28);
    private final BorderLineRound border = new BorderLineRound((Color.lightGray), true);


    public JTextFieldRounded() {
        setOpaque(true);
        setBorder(border);
        setSize(d);
        setPreferredSize(d);
        setHorizontalAlignment(CENTER);
        setFont(new Font("Century Gothic", 0, 12));
        addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                txtFocusGained(e);
            }

            public void focusLost(FocusEvent e) {
                txtFocusLost(e);
            }

        });
    }


    private void txtFocusGained(FocusEvent evt) {

        setBorder(new BorderLineRound(Color.lightGray, true));
    }

    private void txtFocusLost(FocusEvent evt) {

        setBorder(border);
    }


}