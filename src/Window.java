import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;


public class Window extends JFrame{

    private AboutWindow aboutWindow = new AboutWindow();

    public Window(){
        super("Wire frame Designer");
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
        final Window thisWindow = this;
        final FileDialog fdSave = new FileDialog(this, "Choose a file", FileDialog.SAVE);
        fdSave.setFile("*.wfd");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fdSave.setVisible(true);
                fdSave.toFront();
                fdSave.repaint();
                String filename;
                try{
                    filename = fdSave.getFiles()[0].getAbsolutePath();
                }catch(Exception e){return;}
                if (filename != null){
                    String ext =  FileUtil.getFileExtension(filename);
                    if(ext!=null && ext.equals("wfd")){
                        drawPanel.save(filename);
                    }else{
                        drawPanel.save(filename+".wfd");
                    }
                }

            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Load", KeyEvent.VK_L); //R.R.getIcon("label"));
        menuItem.getAccessibleContext().setAccessibleDescription("Load wireframe");
        final FileDialog fdLoad = new FileDialog(this, "Choose a file", FileDialog.LOAD);
        fdLoad.setFile("*.wfd");
        fdLoad.setFilenameFilter(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".wfd");
            }
        });
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fdLoad.setVisible(true);
                String filename;
                try{
                    filename = fdLoad.getFiles()[0].getAbsolutePath();
                }catch(Exception e) {return;}
                if (filename != null)
                    drawPanel.load(filename);


            }
        });
        menu.add(menuItem);

//a submenu
        menu.addSeparator();
        submenu = new JMenu("Export");

        final JFileChooser fcImg = new JFileChooser();
        final FileDialog fdExport = new FileDialog(this, "Choose a file", FileDialog.SAVE);
        fdSave.setFile("*.png");

        menuItem = new JMenuItem("PNG");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fdExport.setVisible(true);
                fdExport.toFront();
                fdExport.repaint();
                String filename;
                try{
                    filename = fdExport.getFiles()[0].getAbsolutePath();
                }catch(Exception e){return;}
                if (filename != null){
                    String ext =  FileUtil.getFileExtension(filename);
                    if(ext!=null && ext.equals("png")){
                        drawPanel.export(filename);
                    }else{
                        drawPanel.export(filename + ".png");
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

        menuItem = new JMenuItem("Wirebox", KeyEvent.VK_B); //R.R.getIcon("label"));
        menuItem.getAccessibleContext().setAccessibleDescription("Create wirebox");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.addWireframe(new Wirebox("Wirebox", 20, 20, 20, 20));
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Label", KeyEvent.VK_L); //R.R.getIcon("label"));
        menuItem.getAccessibleContext().setAccessibleDescription("Create label");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.addWireframe(new Label("Label"));
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Pointer", KeyEvent.VK_P); //R.R.getIcon("label"));
        menuItem.getAccessibleContext().setAccessibleDescription("Create pointer");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.addWireframe(new Pointer("Pointer"));
            }
        });
        menu.add(menuItem);

        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menu.getAccessibleContext().setAccessibleDescription("Get help");
        menuBar.add(menu);

        menuItem = new JMenuItem("Help", KeyEvent.VK_H); //R.R.getIcon("label"));
        menuItem.getAccessibleContext().setAccessibleDescription("Help");
        menu.add(menuItem);

        menuItem = new JMenuItem("About", KeyEvent.VK_A); //R.R.getIcon("label"));
        menuItem.getAccessibleContext().setAccessibleDescription("About");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                aboutWindow.popup();
            }
        });
        menu.add(menuItem);

        return menuBar;
    }
}
