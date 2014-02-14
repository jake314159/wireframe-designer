import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;


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


        setJMenuBar(makeMenu(dp));

        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public JMenuBar makeMenu(final DrawPanel drawPanel){
        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        final JFileChooser fc = new JFileChooser();

//Create the menu bar.
        menuBar = new JMenuBar();

//Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription(
                "File options");
        menuBar.add(menu);

//a group of JMenuItems
        menuItem = new JMenuItem("New", KeyEvent.VK_N); //R.R.getIcon("label"));
        menuItem.getAccessibleContext().setAccessibleDescription("Start new wireframe");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.clear();
            }
        });
        menu.add(menuItem);


        menuItem = new JMenuItem("Save", KeyEvent.VK_S); //R.R.getIcon("label"));
        menuItem.getAccessibleContext().setAccessibleDescription("Save wireframe");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("SAVE");
                int returnVal = fc.showSaveDialog(window.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would save the file.
                    // log.append("Saving: " + file.getName() + "." + newline);
                    //System.out.println("Saving at "+file.getName());
                    String ext =  FileUtil.getFileExtension(file.getAbsolutePath());
                    if(ext!=null && ext.equals("wfd")){
                        drawPanel.save(file.getAbsolutePath());
                    }else{
                        drawPanel.save(file.getAbsolutePath()+".wfd");
                    }

                }
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Load", KeyEvent.VK_L); //R.R.getIcon("label"));
        menuItem.getAccessibleContext().setAccessibleDescription("Load wireframe");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fc.showOpenDialog(window.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    drawPanel.load(file.getAbsolutePath());
                } else {
                }
            }
        });
        menu.add(menuItem);

        /*menuItem = new JMenuItem("Export", KeyEvent.VK_L); //R.R.getIcon("label"));
        menuItem.getAccessibleContext().setAccessibleDescription("Export wireframe");
        menu.add(menuItem);      */

//a submenu
        menu.addSeparator();
        submenu = new JMenu("Export");

        final JFileChooser fcImg = new JFileChooser();

        menuItem = new JMenuItem("PNG");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fcImg.showSaveDialog(window.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fcImg.getSelectedFile();
                    System.out.println("Saving at "+file.getName());
                    String ext =  FileUtil.getFileExtension(file.getAbsolutePath());
                    if(ext!=null && ext.equals("png")){
                        drawPanel.export(file.getAbsolutePath());
                    }else{
                        drawPanel.export(file.getAbsolutePath() + ".png");
                    }
                }
            }
        });
        submenu.add(menuItem);

        /*menuItem = new JMenuItem("Another item");
        submenu.add(menuItem); */
        menu.add(submenu);

//Build second menu in the menu bar.
        menu = new JMenu("Add");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("Add new items to the draw panel");
        menuBar.add(menu);

        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menu.getAccessibleContext().setAccessibleDescription("Get help");
        menuBar.add(menu);

        return menuBar;
    }
}
