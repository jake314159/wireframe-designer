import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class DrawPanel extends JPanel{

    private ArrayList<Wireframe> wireFrames;

    private Color backgroundColor = Color.WHITE;

    public static final int NOT_CORNER = 0;
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 2;
    public static final int CORNER_BOTTOM_LEFT = 3;
    public static final int CORNER_BOTTOM_RIGHT = 4;

    private SettingsPanel settingsPanel = null;

    private boolean exportMode = false;
    private MouseEvent exportSelectStart = null;
    private MouseEvent exportSelectEnd = null;
    private String exportFile;

    /**
     * Amount of scale applied to the image.
     * High = Good quality but slow
     * Low  = Low  quality but fast
     */
    public static int scale = 1;

    //How much higher quality the exported image is
    private int exportScale = 6;

    private ExportPopup exportPopup = new ExportPopup();

    public DrawPanel(){
        wireFrames = new ArrayList<Wireframe>();
    }

    public void init(){
        DrawPanelMouseListener listener = new DrawPanelMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    @Override
    public void paintComponent(Graphics gScreen){
        BufferedImage img = new BufferedImage(getWidth()*scale, getHeight()*scale, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(backgroundColor);
        g.fillRect(0,0,getWidth()*scale, getHeight()*scale);
        for(Wireframe f : wireFrames){
            //System.out.println("Draw time for "+f.getName());
            f.draw((Graphics2D) g, false);
        }
        if(settingsPanel!=null)
            settingsPanel.addSelectOverlay((Graphics2D)g);

        if(exportMode && exportSelectStart != null && exportSelectEnd != null){
            g.setColor(Color.RED);
            g.drawRect(exportSelectStart.getX(), exportSelectStart.getY(),
                    exportSelectEnd.getX() - exportSelectStart.getX(),
                    exportSelectEnd.getY() - exportSelectStart.getY()
            );
            //System.out.println("Drag when exporting");
        }

        gScreen.drawImage(img, 0,0, getWidth(), getHeight(), null);
    }

    public void addWireframe(Wireframe wirebox) {
        wireFrames.add(wirebox);
        repaint();
    }

    public void setSettingsPanel(SettingsPanel sp){
        this.settingsPanel = sp;
    }

    public boolean save(String fileLocation){
        try {
            FileUtil.saveFrames(wireFrames,fileLocation);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return true;
    }
    public boolean load(String fileToLoad){
        try {
            wireFrames = FileUtil.loadFrames(fileToLoad);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        repaint();
        return true;
    }

    //Deletes everything (with no undo!)
    public void clear(){
        wireFrames.clear();
        repaint();
    }

    public void export(String exportFile){
        //FileUtil.saveAsImage(wireFrames, 0,0,getWidth(), getHeight(), "output.png");
        exportMode = true;
        this.exportFile = exportFile;
    }

    public void deleteWireframe(Wireframe wireframe) {
        wireFrames.remove(wireframe);
        repaint();
    }

    public void bringToTop(Wireframe wireframe) {
        wireFrames.remove(wireframe);
        wireFrames.add(wireframe);
        repaint();
    }


    class DrawPanelMouseListener implements MouseListener, MouseMotionListener{
        Wireframe interestedFrame;
        MouseEvent downEvent;


        @Override
        public void mousePressed(MouseEvent me) {
            MouseEvent me2 = new MouseEvent((Component)me.getSource(), me.getID(), 0l, me.getModifiers(),
                    me.getX()*scale, me.getY()*scale, me.getClickCount(), me.isPopupTrigger());
            if(exportMode){

                exportSelectStart = me2;
                return;
            }
            //System.out.println("Pressed at "+me.getX()+","+me.getY());
            for(Wireframe f : wireFrames){
                //System.out.println("Is down on boarder "+f.getName()+"?  "+f.onBoarder(me.getX(), me.getY()));
                if(f.onBoarder(me.getX()*scale, me.getY()*scale) || f.isCorner(me.getX()*scale, me.getY()*scale)!=NOT_CORNER){
                    interestedFrame = f;
                    downEvent = me2;
                    break;
                }
            }
        }

        @Override
        public void mouseReleased(final MouseEvent me) {
            if(exportMode){

                exportPopup.export(me.getX() * scale - exportSelectStart.getX(),
                        me.getY() * scale - exportSelectStart.getY(),
                        new Thread(){
                            public void run(){
                                FileUtil.saveAsImage(wireFrames, exportSelectStart.getX(), exportSelectStart.getY(),
                                        me.getX()*scale-exportSelectStart.getX(),
                                        me.getY()*scale-exportSelectStart.getY(),
                                        exportFile,
                                        exportScale
                                );

                            }
                        },
                        new Thread(){
                            public void run(){
                                exportMode = false;
                                exportSelectStart = null;
                                exportSelectEnd = null;
                                exportFile = null;
                                repaint();
                            }
                        });

            }else{
                interestedFrame = null; //all sorted
            }
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            MouseEvent me2 = new MouseEvent((Component)me.getSource(), me.getID(), 0l, me.getModifiers(),
                    me.getX()*scale, me.getY()*scale, me.getClickCount(), me.isPopupTrigger());
            if(!exportMode){
                //System.out.println("Released at "+me.getX()+","+me.getY());
                if(interestedFrame != null){

                    interestedFrame.mouseDrag(downEvent, me2);

                    //interestedFrame = null; //all sorted
                    downEvent = me2;
                }
            }else{
                exportSelectEnd = me2; //we are in export mode
            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {}
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {}
        @Override
        public void mouseExited(MouseEvent mouseEvent) {}
        @Override
        public void mouseClicked(MouseEvent me) {
            if(settingsPanel != null){
                boolean set = false;
                for(Wireframe f : wireFrames){
                    //System.out.println("Is down on boarder "+f.getName()+"?  "+f.onBoarder(me.getX(), me.getY()));
                    if(f.onBoarder(me.getX()*scale, me.getY()*scale)){
                        settingsPanel.setWireframe(f);
                        set = true;
                        break;
                    }
                }
                if(!set)
                    settingsPanel.setWireframe(null); //didn't click on anything
            }
        }
    }

}


