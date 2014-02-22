import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * Not the prettiest of classes i've ever written but it just about gets the job done.
 * Ideally this should be looked at at a later date, in particular the export(..) method
 * needs a lot of parameters
 */


public class ExportPopup extends JFrame{

    private Thread tidyThread = null;

    private JLabel exportResLabel = new JLabel();
    private JComboBox<String> scaleDropdown = new JComboBox<String>();
    private JButton confirmButton = new JButton("Export");
    /*
            -1 == Waiting for input
            0  == Exit
            1+ == Scale selected
     */
    int chosenScale = -1;

    private int height = 0;
    private int width = 0;

    public ExportPopup() {
        super("Export wireframe");
        init();
    }

    public void init() {
        JPanel panel = (JPanel) this.getContentPane();
        panel.setLayout(new FlowLayout());

        panel.add(exportResLabel);

        scaleDropdown.addItem("1 - Low");
        for(int i=2; i<8; i++){
            scaleDropdown.addItem(""+i);
        }
        scaleDropdown.addItem("9 - High");
        scaleDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                updateLabel();
                validate();
                repaint();
            }
        });


        panel.add(exportResLabel);
        panel.add(scaleDropdown);


        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chosenScale = 0; //chosen exit
                if(tidyThread != null){
                    tidyThread.start();
                }
                setVisible(false);
            }
        });

        this.add(confirmButton);
        this.add(exitButton);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }


    /**
     * Shows the popup window and sorts out the users scale choices before exporting
     *
     * NOTE: Scale is how much larger than the screen resolution it should be exported as so a exportScale of
     * 2 will make a box which is 100x100 px on the screen 200x200px in the exported image
     *
     *
     * @param dp The draw panel which is making the call (used to set the exportScale)
     * @param width Width of the export window
     * @param height Height of the export window
     * @param exportThread A thread object which if ran will do the export calculation
     * @param tidyThread A thread object which will tidy up after the user has made their choice
     *                   Note that even if the user exits this will be run
     */
    public void export(final DrawPanel dp, int width, int height, final Thread exportThread, final Thread tidyThread){
        this.height = height;
        this.width = width;
        this.tidyThread = tidyThread;

        //Remove all action listeners of old
        for( ActionListener al : confirmButton.getActionListeners() ) {
            confirmButton.removeActionListener( al );
        }

        //Add an action listener which does the export when the user clicks the "export" button
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setChosenScale();

                //If chose to export then run the export thread object
                if(chosenScale > 0){
                    new Thread(){
                        public void run(){
                            dp.setExportScale(chosenScale);
                            exportThread.run();
                            tidyThread.run();
                            setVisible(false);
                        }
                    }.start();

                }else{
                    tidyThread.run();
                    setVisible(false);
                }
            }
        });

        updateLabel();

        this.setSize(300,400);
        this.setVisible(true);

    }

    private void updateLabel(){
        int scale = scaleDropdown.getSelectedIndex()+1;
        exportResLabel.setText("Export resolution: "+(width*scale)+"x"+(height*scale));
    }

    private void setChosenScale(){
        chosenScale = scaleDropdown.getSelectedIndex()+1;

    }
}
