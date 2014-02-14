import java.awt.*;
import java.awt.event.MouseEvent;

public class Wirebox implements Wireframe {
    private int x;
    private int y;
    private int width;
    private int height;
    private String name;
    private boolean drawName = false;
    private String fillType = "None";

    private static int edgeThreshold = 5;
    private static int cornerThreshold = 10;
    private Color lineColor = Color.BLACK;
    private Font font = new Font("Arial", Font.PLAIN, 12);


    public Wirebox(String name, int x, int y, int width, int height){
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public Wirebox(String name, int x, int y, int width, int height, boolean drawName){
        this(name,x,y,width,height);
        this.drawName = drawName;
    }

    @Override
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name.replace(",", ""); //remove commas
    }

    @Override
    public String getTypeOfWireframe() {
        return "Wirebox";
    }

    @Override
    public Point getTopCorner() {
        return new Point(x,y);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean onBoarder(int xClick, int yClick) {
        return (xClick > x && xClick< (x+width) && Math.abs(yClick-y) < edgeThreshold) || //Top line
                (xClick > x && xClick< (x+width) && Math.abs(yClick-height-y) < edgeThreshold) || //Bottom line
                (yClick > y && yClick< (y+height) && Math.abs(xClick-x) < edgeThreshold) || //Left line
                (yClick > y && yClick< (y+height) && Math.abs(xClick-x-width) < edgeThreshold); //Right line
    }

    @Override
    public boolean contains(int x, int y) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getFillType(){
        return fillType;
    }
    public void setFillType(String fillType){
        this.fillType = fillType;
    }


    @Override
    public void draw(Graphics2D g) {
        draw(g,false);
    }

    @Override
    public void draw(Graphics2D g, boolean export) {
        Filler.fill(fillType,x,y,width,height,g);
        g.setColor(lineColor);
        g.setFont(font);
        g.setStroke(new BasicStroke(1));
        g.drawRect(x,y,width, height);
        if(!export){
            g.fillOval(x + width - 3, y + height - 3, 6, 6);
        }
        if(drawName){
            g.drawString(getName(), x+5, y+15);
        }

    }

    //Draws whatever extra is required to show that this is currently selected
    public void drawSelectOverlay(Graphics2D g){
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(1));
        g.drawRect(x,y,width, height);
    }

    //Returns value based on which corner or DrawPanel.NOT_CORNER if not a corner
    public int isCorner(int xClick, int yClick){
        if(Math.abs(xClick - x)<cornerThreshold){
            if(Math.abs(yClick - y)<cornerThreshold) return DrawPanel.CORNER_TOP_LEFT;
        }else if(Math.abs(xClick - x - width)<cornerThreshold) {
            if(Math.abs(yClick - y - height)<cornerThreshold) return DrawPanel.CORNER_BOTTOM_RIGHT;
        }

        return DrawPanel.NOT_CORNER;
    }

    @Override
    public boolean drawName() {
        return drawName;
    }

    @Override
    public void setDrawName(boolean drawName) {
        this.drawName = drawName;
    }

    @Override
    public String getSaveString() {
        return getTypeOfWireframe() +","+   //MUST start with the type of wire frame!

                getName() +","+
                drawName() +","+
                x +","+
                y +","+
                height +","+
                width +","+
                fillType;
    }


    public static Wirebox makeWireframe(String[] args){
        String name = args[1];
        boolean drawName = true;
        if(args[2].equals("false")) drawName = false;
        int x = Integer.parseInt(args[3]);
        int y = Integer.parseInt(args[4]);
        int height = Integer.parseInt(args[5]);
        int width = Integer.parseInt(args[6]);
        String fillType = args[7];
        Wirebox returnBox = new Wirebox(name, x,y,width,height,drawName);
        returnBox.setFillType(fillType);
        return returnBox;
    }

    @Override
    public void mouseDrag(MouseEvent from, MouseEvent too) {
        switch(isCorner(from.getX(),from.getY())){
            case DrawPanel.CORNER_BOTTOM_RIGHT:
                //System.out.println("Clicked on the bottom right corner");
                width = too.getX() - x;
                height = too.getY() - y;
                break;
            default:
                //System.out.println("Someone has dragged "+getName());
                x = x - from.getX() + too.getX();
                y = y - from.getY() + too.getY();
        }

    }
}
