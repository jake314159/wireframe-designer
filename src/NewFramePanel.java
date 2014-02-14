import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class NewFramePanel extends JPanel{

    DrawPanel drawPanel = new DrawPanel(); //Where to add the new frames

    public NewFramePanel(DrawPanel drawPanel){
        this.drawPanel = drawPanel;
    }

    public void init(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel(" "));

        JButton wireboxButton = new JButton("");
        //wireboxButton.setSize(50,50);
        wireboxButton.setMargin(new Insets(2, 2, 2, 2));
        try {
            //System.out.println("ICON "+R.R.getIcon("wirebox"));
            wireboxButton.setIcon(R.R.getIcon("wirebox"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            wireboxButton.setText("Wirebox");
        }
        wireboxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.addWireframe(new Wirebox("New box",10,10,20,20,true));
                drawPanel.repaint();
            }
        });
        this.add(wireboxButton);


        JButton labelButton = new JButton("");
        try {
            //System.out.println("ICON "+R.R.getIcon("wirebox"));
            labelButton.setIcon(R.R.getIcon("label"));
            labelButton.setMargin(new Insets(2, 2, 2, 2));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            labelButton.setText("Label");
        }
        labelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.addWireframe(new Label("Label 1"));
            }
        });
        this.add(labelButton);

        JButton pointerButton = new JButton("");
        try {
            //System.out.println("ICON "+R.R.getIcon("wirebox"));
            pointerButton.setIcon(R.R.getIcon("pointer"));
            pointerButton.setMargin(new Insets(2, 2, 2, 2));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            pointerButton.setText("Pointer");
        }
        pointerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.addWireframe(new Pointer("Pointer 1"));
            }
        });
        this.add(pointerButton);


        /* MOVED TO THE MENU
        this.add(new JLabel("Save options:"));

        JButton saveButton = new JButton("Save");
        final JFileChooser fc = new JFileChooser();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fc.showSaveDialog(NewFramePanel.this);
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

                } else {
                    //log.append("Save command cancelled by user." + newline);
                }
                //log.setCaretPosition(log.getDocument().getLength());
                //drawPanel.save();
            }
        });
        this.add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fc.showOpenDialog(NewFramePanel.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    //log.append("Opening: " + file.getName() + "." + newline);
                    drawPanel.load(file.getAbsolutePath());
                } else {
                    //log.append("Open command cancelled by user." + newline);
                }
            }
        });
        this.add(loadButton);

        final JFileChooser fcImg = new JFileChooser();
        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fcImg.showSaveDialog(NewFramePanel.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fcImg.getSelectedFile();
                    System.out.println("Saving at "+file.getName());
                    //drawPanel.export(file.getAbsolutePath() + ".png");
                    String ext =  FileUtil.getFileExtension(file.getAbsolutePath());
                    if(ext!=null && ext.equals("png")){
                        drawPanel.export(file.getAbsolutePath());
                    }else{
                        drawPanel.export(file.getAbsolutePath() + ".png");
                    }
                } else {
                    //log.append("Save command cancelled by user." + newline);
                }
                //log.setCaretPosition(log.getDocument().getLength());
                //drawPanel.export();
            }
        });
        this.add(exportButton);

        this.add(new JLabel("WARNING"));
        this.add(new JLabel("THE NEXT"));
        this.add(new JLabel("BUTTON"));
        this.add(new JLabel("WILL DELETE"));
        this.add(new JLabel("EVERYTHING:"));

        JButton newButton = new JButton("New");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.clear();
            }
        });
        this.add(newButton);
                */
    }
}
