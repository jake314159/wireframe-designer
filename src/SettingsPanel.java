import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SettingsPanel extends JPanel{
    private Wireframe wireframe = null;
    private DrawPanel drawPanel;

    private JLabel nameLabel = new JLabel("Name:");
    private JTextField nameTextBox = new JTextField(10);
    private JTextArea multilineTextEntry = new JTextArea(3,10);
    private JLabel displayNameLabel = new JLabel("Display name:");
    private JCheckBox displayNameCheckbox = new JCheckBox();
    private JComboBox fillList = new JComboBox(Filler.getFillOptions());
    private JButton selectFillColorButton = new JButton("Select color");

    private JLabel sizeLabel = new JLabel("Size:");
    private JTextField fontSizeBox = new JTextField(10);



    private JButton toTopButton = new JButton("Bring to top");
    private JButton deleteButton = new JButton("Delete");

    private Color selectedColor = Color.WHITE;

    public SettingsPanel(DrawPanel drawPanel){
        this.drawPanel = drawPanel;
    }

    public void setWireframe(Wireframe wireframe){

        if(wireframe != null){
            this.wireframe = null;
            nameTextBox.setText(wireframe.getName());
            multilineTextEntry.setText(wireframe.getName());
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

            if(wireframe.getTypeOfWireframe().equals("Pointer")){
                multilineTextEntry.setVisible(true);
                nameTextBox.setVisible(false);
            }else{
                multilineTextEntry.setVisible(false);
                nameTextBox.setVisible(true);
            }

            this.wireframe = wireframe;
        } else {
            this.wireframe = wireframe;
            nameTextBox.setText("");
            multilineTextEntry.setText("");
            displayNameCheckbox.setSelected(false);
        }
        if(drawPanel != null) drawPanel.repaint();
    }

    public void addSelectOverlay(Graphics2D g){
        if(wireframe != null)
            wireframe.drawSelectOverlay(g);
    }


    public void init(){

        this.add(nameLabel);

        this.add(nameTextBox);
        nameTextBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {}
            @Override
            public void keyPressed(KeyEvent keyEvent) {}

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if(wireframe != null){
                    wireframe.setName(nameTextBox.getText().replaceAll(",", ""));
                    if(drawPanel != null) drawPanel.repaint();
                }
            }
        });


        multilineTextEntry.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {}
            @Override
            public void keyPressed(KeyEvent keyEvent) {}
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if(wireframe != null){
                    wireframe.setName(multilineTextEntry.getText().replaceAll(",", ""));
                    if(drawPanel != null) drawPanel.repaint();
                }
            }
        });
        multilineTextEntry.setVisible(false);
        this.add(multilineTextEntry);

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


        toTopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.bringToTop(wireframe);
            }
        });
        this.add(toTopButton);


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.deleteWireframe(wireframe);
                setWireframe(null);
            }
        });
        this.add(deleteButton);



        GroupLayout layout = new GroupLayout(this);

        /*private JLabel nameLabel = new JLabel("Name:");
    private JTextField nameTextBox = new JTextField(10);
    private JTextArea multilineTextEntry = new JTextArea(3,10);
    private JLabel displayNameLabel = new JLabel("Display name:");
    private JCheckBox displayNameCheckbox = new JCheckBox();
    private JComboBox fillList = new JComboBox(Filler.getFillOptions());
    private JButton selectFillColorButton = new JButton("Select color");

    private JLabel sizeLabel = new JLabel("Size:");
    private JTextField fontSizeBox = new JTextField(10);



    JButton toTopButton = new JButton("Bring to top");
    JButton deleteButton = new JButton("Delete");
    */

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        //.addComponent(c1)
                       // .addComponent(c2)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(nameLabel)
                                .addComponent(nameTextBox)
                                .addComponent(multilineTextEntry)
                                .addComponent(displayNameLabel)

                                .addComponent(fillList)
                                .addComponent(selectFillColorButton)
                                .addComponent(sizeLabel)
                                .addComponent(fontSizeBox)
                                .addComponent(toTopButton)
                                .addComponent(deleteButton)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(displayNameCheckbox)
                        )
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                       // .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                      //          .addComponent(c1)
                      //          .addComponent(c2)
                      //          .addComponent(c3))
                        .addComponent(nameLabel)
                        .addComponent(nameTextBox)
                        .addComponent(multilineTextEntry)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(displayNameLabel)
                            .addComponent(displayNameCheckbox) )
                        .addComponent(fillList)
                        .addComponent(selectFillColorButton)
                        .addComponent(sizeLabel)
                        .addComponent(fontSizeBox)
                        .addComponent(toTopButton)
                        .addComponent(deleteButton)
        );

        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout(layout);
    }

}
