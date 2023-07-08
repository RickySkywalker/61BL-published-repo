/** A class that represents a path via pursuit curves. */
public class Path {
    // TODO
    private Point curr, next;

    public Path(double x, double y){
        curr = new Point(x,y);
        next = new Point(x,y);
    }

    public double getCurrX(){
        return curr.getX();
    }
    public double getCurrY(){
        return curr.getY();
    }
    public double getNextX(){
        return next.getX();
    }
    public double getNextY(){
        return next.getY();
    }

    public Point getCurrentPoint(){
        return curr;
    }
    public void setCurrentPoint(Point point){
        curr = point;
    }

    public void iterate(double dx, double dy){
        curr = new Point(next.getX(),next.getY());
        next = new Point(curr.getX()+dx, curr.getY()+dy);
    }



}
