import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: jake
 * Date: 12/02/14
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public class NewFramePanel extends JPanel{

    DrawPanel drawPanel = new DrawPanel(); //Where to add the new frames

    public NewFramePanel(DrawPanel drawPanel){
        this.drawPanel = drawPanel;
    }

    public void init(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel("Draw options:"));

        JButton wireboxButton = new JButton("Wirebox");
        wireboxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.addWireframe(new Wirebox("New box",10,10,20,20,true));
                drawPanel.repaint();
            }
        });
        this.add(wireboxButton);

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
                    System.out.println("Saving at "+file.getName());
                    drawPanel.save(file.getAbsolutePath()+".wfd");
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
                    drawPanel.export(file.getAbsolutePath()+".png");
                } else {
                    //log.append("Save command cancelled by user." + newline);
                }
                //log.setCaretPosition(log.getDocument().getLength());
                //drawPanel.export();
            }
        });
        this.add(exportButton);

    }
}
