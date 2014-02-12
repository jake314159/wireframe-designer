import javax.swing.*;
import java.awt.*;


public class window extends JFrame{

    public window(){
        super("Wire frame designer");
    }

    public void init(){
        JPanel mainPanel = (JPanel) getContentPane();
        mainPanel.setLayout(new BorderLayout());
        DrawPanel dp = new DrawPanel();
        dp.init();
        mainPanel.add(dp);

        NewFramePanel nfp = new NewFramePanel(dp);
        nfp.init();
        mainPanel.add(nfp, BorderLayout.WEST);

        SettingsPanel sp = new SettingsPanel(dp);
        sp.init();
        JPanel spInnerPanel = new JPanel();
        spInnerPanel.add(sp);
        mainPanel.add(spInnerPanel, BorderLayout.EAST);

        dp.setSettingsPanel(sp);

        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }
}
