import java.awt.*;
import java.awt.event.MouseEvent;

public class Label implements Wireframe{
    private String name;
    private int x = 10;
    private int y = 10;
    private int sensitivity = 10; //How easy to pick up
    private Color textColor = Color.BLACK;
    private Font font = new Font("Arial", Font.PLAIN, 22);

    public Label(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTypeOfWireframe() {
        return "Label";
    }

    @Override
    public Point getTopCorner() {
        return new Point(x,y);
    }

    @Override
    public int getWidth() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getHeight() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onBoarder(int xClick, int yClick) {
        return Math.abs(x-xClick) < sensitivity && Math.abs(y-yClick) < sensitivity;
    }

    @Override
    public boolean contains(int x, int y) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void draw(Graphics2D g, boolean export) {
        g.setColor(textColor);
        g.setFont(font);
        g.drawString(name, x, y+22);
        if(!export){
            g.fillRect(x-5,y-5,10,10);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        draw(g,false);
    }

    @Override
    public void drawSelectOverlay(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(x-5,y-5,10,10);
    }

    @Override
    public void mouseDrag(MouseEvent from, MouseEvent too) {
        x = too.getX();
        y = from.getY();
    }

    @Override
    public int isCorner(int x, int y) {
        return DrawPanel.NOT_CORNER; //There are no corners
    }

    @Override
    public boolean drawName() {
        return true;  //Must always draw name
    }

    @Override
    public void setDrawName(boolean drawName) {
       //Doesn't matter so lets ignore it
    }

    @Override
    public String getSaveString() {
        return getTypeOfWireframe() +","+
                name;
    }
    public static Label makeWireframe(String[] args){
        return new Label(args[1]);
    }

    @Override
    public String getFillType() {
        return "None";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setFillType(String fillType) {
        //Do nothing this makes no sense otherwise
    }
}
