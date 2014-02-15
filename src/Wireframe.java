import java.awt.*;
import java.awt.event.MouseEvent;

public interface Wireframe {

    public String getName();
    public void setName(String name);
    public String getTypeOfWireframe();
    public Point getTopCorner();
    public int getWidth();
    public int getHeight();
    public boolean onBoarder(int x, int y);
    public boolean contains(int x, int y);
    public void draw(Graphics2D g, boolean export);
    public void draw(Graphics2D g);
    public void drawSelectOverlay(Graphics2D g);
    public void mouseDrag(MouseEvent from, MouseEvent too);
    public int isCorner(int x, int y);
    public boolean drawName();     //is the name being drawn?
    public void setDrawName(boolean drawName);

    public String getSaveString();
    public String getFillType();
    public void setFillType(String fillType);

    /*
     * Returns a wireframe which has been upscaled by the given amount.
     * Note some drawing values may still be scaled with DrawPanel.scale
     */
    public Wireframe upscale(int scale);
}
