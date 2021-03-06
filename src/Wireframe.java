import java.awt.*;
import java.awt.event.MouseEvent;

public interface Wireframe {

    public String getName();
    public void setName(String name);
    public String getTypeOfWireframe();
    public int getWidth();
    public int getHeight();
    public boolean onBoarder(int x, int y);
    public boolean contains(int x, int y);
    public void draw(Graphics2D g, boolean export);
    public void drawSelectOverlay(Graphics2D g);
    public void mouseDrag(MouseEvent from, MouseEvent too);
    public int isCorner(int x, int y);

    public String getSaveString();

    /*
     * Returns a wireframe which has been upscaled by the given amount.
     * Note some drawing values may still be scaled with DrawPanel.scale
     */
    public Wireframe upscale(int scale);
}
