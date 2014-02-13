import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SettingsPanel extends JPanel{
    private Wireframe wireframe = null;
    private DrawPanel drawPanel;

    private JTextField nameTextBox = new JTextField(10);
    private JLabel displayNameLabel = new JLabel("Display name:");
    private JCheckBox displayNameCheckbox = new JCheckBox();
    private JComboBox fillList = new JComboBox(Filler.getFillOptions());
    private JButton selectFillColorButton = new JButton("Select color");

    private JLabel sizeLabel = new JLabel("Size:");
    private JTextField fontSizeBox = new JTextField(10);


    private Color selectedColor = Color.WHITE;

    public SettingsPanel(DrawPanel drawPanel){
        this.drawPanel = drawPanel;
    }

    public void setWireframe(Wireframe wireframe){

        if(wireframe != null){
            this.wireframe = null;
            nameTextBox.setText(wireframe.getName());
            displayNameCheckbox.setSelected(wireframe.drawName());
            for(int i=0; i<Filler.getFillOptions().length; i++){
                System.out.println("Checking if " + wireframe.getFillType() + " equals " + fillList.getItemAt(i));
                if(wireframe.getFillType().equals(fillList.getItemAt(i))){
                    fillList.setSelectedIndex(i);
                    break;
                }
                //error option not avalible :/
                fillList.setSelectedIndex(0);
            }
            if(wireframe.getTypeOfWireframe().equals("Label")){
                sizeLabel.setVisible(true);
                fontSizeBox.setVisible(true);
                fillList.setVisible(false);
                displayNameCheckbox.setVisible(false);
                displayNameLabel.setVisible(false);
                fontSizeBox.setText(""+((Label)wireframe).getSize());
            }else{
                sizeLabel.setVisible(false);
                fontSizeBox.setVisible(false);
                fillList.setVisible(true);
                displayNameLabel.setVisible(true);
                displayNameCheckbox.setVisible(true);
            }

            this.wireframe = wireframe;
        } else {
            this.wireframe = wireframe;
            nameTextBox.setText("");
            displayNameCheckbox.setSelected(false);
        }
        if(drawPanel != null) drawPanel.repaint();
    }

    public void addSelectOverlay(Graphics2D g){
        if(wireframe != null)
            wireframe.drawSelectOverlay(g);
    }


    public void init(){

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel("Name: "));

        this.add(nameTextBox);
        nameTextBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {}
            @Override
            public void keyPressed(KeyEvent keyEvent) {}

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if(wireframe != null){
                    wireframe.setName(nameTextBox.getText().replace(",", ""));
                    if(drawPanel != null) drawPanel.repaint();
                }
            }
        });

        this.add(displayNameLabel);
        this.add(displayNameCheckbox);
        displayNameCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                wireframe.setDrawName(displayNameCheckbox.isSelected());
                if(drawPanel != null) drawPanel.repaint();
            }
        });


        fillList.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(wireframe!=null){
                    String fillType = (String)fillList.getSelectedItem();
                    if(fillType.equals("Fill")){
                        wireframe.setFillType("Fill"+String.format("#%02x%02x%02x", selectedColor.getRed(),
                                selectedColor.getGreen(), selectedColor.getBlue()));
                    }else{
                        wireframe.setFillType(fillType);
                    }
                    drawPanel.repaint();
                }
            }
        });
        this.add(fillList);

        selectFillColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedColor = JColorChooser.showDialog(
                        SettingsPanel.this,
                        "Choose Background Color",
                        selectedColor);
            }
        });
        this.add(selectFillColorButton);

        this.add(sizeLabel);
        this.add(fontSizeBox);
        fontSizeBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {}
            @Override
            public void keyPressed(KeyEvent keyEvent) {}
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if(wireframe.getTypeOfWireframe().equals("Label")){
                    try{
                        int size = Integer.parseInt(fontSizeBox.getText());
                        ((Label)wireframe).setSize(size);
                        drawPanel.repaint();
                    } catch (Exception e){
                        System.out.println("NOT A VALID SIZE ENTRY");
                    }
                }
            }
        });

        JButton toTopButton = new JButton("Bring to top");
        toTopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.bringToTop(wireframe);
            }
        });
        this.add(toTopButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.deleteWireframe(wireframe);
                setWireframe(null);
            }
        });
        this.add(deleteButton);

        this.add(new JLabel("    ")); //filler
    }

}
