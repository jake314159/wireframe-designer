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
            wireboxButton.setIcon(R.getIcon("wirebox"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            wireboxButton.setText("Wirebox");
        }
        wireboxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.addWireframe(new Wirebox("New box",10*DrawPanel.scale,10*DrawPanel.scale,20*DrawPanel.scale,20*DrawPanel.scale,true));
                drawPanel.repaint();
            }
        });
        this.add(wireboxButton);


        JButton labelButton = new JButton("");
        try {
            labelButton.setIcon(R.getIcon("label"));
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
            pointerButton.setIcon(R.getIcon("pointer"));
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
    }
}
