import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import org.markdown4j.Markdown4jProcessor;

public class AboutWindow extends JFrame{

    public AboutWindow(){
        //init

        JPanel panel = (JPanel) this.getContentPane();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /*JEditorPane text = new JEditorPane();

        text.setText("Wireframe <b>des</b>igner!\n\nYay");
        text.setEditable(false);    */
      //  text.setLineWrap(true);
       // text.setWrapStyleWord(true);

        //panel.setLayout(new BorderLayout());
        //panel.add(text, BorderLayout.CENTER);

        panel.add(new JLabel(" "));

        try {
            panel.add(new JLabel("<html>"+new Markdown4jProcessor().process(R.getText("about"))+"</html>"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        this.setSize(450,350);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    /**
     * Display the popup window
     */
    public void popup(){
        this.setVisible(true);
    }

}
