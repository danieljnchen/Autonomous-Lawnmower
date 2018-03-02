package algorithm;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Robot extends UIObject {
    private final double speed = 3;

    public double length = 15;
    public double width = 10;
    private Point2D pos = Point2D.ZERO; // relative to center of robot

    private ArrayList<Point2D> pathNodes = new ArrayList<>();
    private int curNodeDest = 1;

    public void queueNodes(ArrayList<Point2D> nodes) {
        pathNodes.addAll(nodes);
    }

    public void reset() {
        pathNodes.clear();
        pathNodes.add(Point2D.ZERO);
        pos = pathNodes.get(0);
        curNodeDest = 1;
    }

    private void approachNextNode() {
        if (!(pathNodes.size() > 1)) return;

        Point2D node = pathNodes.get(curNodeDest);
        if (pos.distance(node) < speed) {
            if (curNodeDest < pathNodes.size() - 1) {
                curNodeDest++;
            }
        } else {
            Point2D delta = node.subtract(pos);
            double angle = Math.atan2(delta.getY(), delta.getX());

            pos = pos.add(speed *Math.cos(angle), speed *Math.sin(angle));

        }
    }

    public void draw(GraphicsContext gc) {
        // algorithm.Robot shape
        gc.setFill(Color.ORANGE);
        gc.fillRect(pos.getX() - width / 2, pos.getY() - length / 2, width, length);

        // Planned path
        gc.setLineWidth(5);
        gc.setStroke(Color.RED);
        for (int i = 0; i < pathNodes.size() - 1; i++) {
            gc.strokeLine(pathNodes.get(i).getX(), pathNodes.get(i).getY(), pathNodes.get(i + 1).getX(), pathNodes.get(i + 1).getY());
        }

        if (pathNodes.size() != 0) {
            if (pos == Point2D.ZERO) {
                pos = pathNodes.get(0);
            }
            gc.fillOval(pathNodes.get(pathNodes.size() - 1).getX(), pathNodes.get(pathNodes.size() - 1).getY(), 5, 5);
            approachNextNode();
        }
    }
}