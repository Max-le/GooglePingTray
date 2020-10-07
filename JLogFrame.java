import javax.swing.*;
import java.awt.*;

class JLogFrame extends JFrame {

   private JTextArea loggingTextArea = new JTextArea();

    public JLogFrame() {
        super("Log Frame");//Constructor of JFrame class

        loggingTextArea.setFont(new Font("Serif", Font.BOLD, 16));
        loggingTextArea.setLineWrap(true);
        loggingTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(loggingTextArea);

        loggingTextArea.setEditable(false);
        loggingTextArea.append("Hello ! Logs will appear here.");

        getContentPane().add(scrollPane);
        setSize(300, 600);

        getContentPane().add(scrollPane);

        setVisible(false);
    }

    /**
     * This method appends the data to the text area.
     *
     * @param msg the Logging information
     */
    public void appendToLog(String msg) {
        loggingTextArea.append(msg+"\n");
        this.getContentPane().validate();
    }
}
