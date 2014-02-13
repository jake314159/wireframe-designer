import java.awt.*;
import java.awt.event.MouseEvent;

public class Label implements Wireframe{
    private String name;
    private int x = 10;
    private int y = 10;
    private int sensitivity = 10; //How easy to pick up
    private Color textColor = Color.BLACK;
    private Font font;

    public Label(String name){
        this.name = name;
        setSize(22); //sets the default font size
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(int x, int y){
        this.x = x;
        this.y=y;
    }

    public void setSize(int size){
        font = new Font("Arial", Font.PLAIN, size);
    }
    public int getSize(){
        return font.getSize();
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
        g.drawString(name, x, y+font.getSize());
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
                x +","+
                y +","+
                getSize() +","+
                name;
    }
    public static Label makeWireframe(String[] args){
        Label returnLabel = new Label(args[4]);
        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int size = Integer.parseInt(args[3]);
        returnLabel.setLocation(x,y);
        returnLabel.setSize(size);
        return returnLabel;
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
