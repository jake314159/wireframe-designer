import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DrawPanel extends JPanel{

    ArrayList<Wireframe> wireFrames;

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

    public DrawPanel(){
        wireFrames = new ArrayList<Wireframe>();
    }

    public void init(){
        DrawPanelMouseListener listener = new DrawPanelMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(backgroundColor);
        g.fillRect(0,0,getWidth(), getHeight());
        for(Wireframe f : wireFrames){
            //System.out.println("Draw time for "+f.getName());
            f.draw((Graphics2D) g);
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
            if(exportMode){
                exportSelectStart = me;
                return;
            }
            //System.out.println("Pressed at "+me.getX()+","+me.getY());
            for(Wireframe f : wireFrames){
                //System.out.println("Is down on boarder "+f.getName()+"?  "+f.onBoarder(me.getX(), me.getY()));
                if(f.onBoarder(me.getX(), me.getY()) || f.isCorner(me.getX(), me.getY())!=NOT_CORNER){
                    interestedFrame = f;
                    downEvent = me;
                    break;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            if(exportMode){
                FileUtil.saveAsImage(wireFrames, exportSelectStart.getX(), exportSelectStart.getY(),
                        me.getX()-exportSelectStart.getX(),
                        me.getY()-exportSelectStart.getY(),
                        exportFile
                        );
                exportMode = false;
                exportSelectStart = null;
                exportSelectEnd = null;
                exportFile = null;
                repaint();
            }else{
                interestedFrame = null; //all sorted
            }
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            if(!exportMode){
                //System.out.println("Released at "+me.getX()+","+me.getY());
                if(interestedFrame != null){
                    interestedFrame.mouseDrag(downEvent, me);

                    //interestedFrame = null; //all sorted
                    downEvent = me;
                }
            }else{
                exportSelectEnd = me; //we are in export mode
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
                    if(f.onBoarder(me.getX(), me.getY())){
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


