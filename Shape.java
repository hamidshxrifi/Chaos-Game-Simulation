import java.awt.*;
import java.util.ArrayList;

public class Shape {
    // array containing the coordinates of the shape vertices
    ArrayList<Point> vertices = new ArrayList<>();
    // array containing start point, and calculated points
    ArrayList<Point> chaosPoints = new ArrayList<>();

    public void addVertex(Point point) {
        vertices.add(point);
    }

    public void addPoint(Point point) {
        chaosPoints.add(point);
    }

    public void removeVerticesAndPoints() {
        vertices.clear();
        chaosPoints.clear();
    }

    public ArrayList<Point> getVertices() {
        return vertices;
    }

    public ArrayList<Point> getPoints() {
        return chaosPoints;
    }

    public int getNumberOfVertices() {
        return vertices.size();
    }

    public Point getVertex(int v) {
        return vertices.get(v);
    }

    public Point getLastPoint() {
        return chaosPoints.get(chaosPoints.size()-1);
    }






}
