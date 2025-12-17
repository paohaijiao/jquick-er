package com.github.paohaijiao.model;

import com.github.paohaijiao.layout.Point;
import com.github.paohaijiao.relation.Relationship;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Table {

    private String name;

    private String comment;

    private List<Column> columns = new ArrayList<>();

    private List<Relationship> relationships = new ArrayList<>();

    private com.github.paohaijiao.layout.Point position = new com.github.paohaijiao.layout.Point(0, 0);

    private Dimension size = new Dimension(280, 150);

    private Color color;

    private Rectangle bounds;

    private boolean selected = false;

    private int zIndex = 0;

    public Table() {
    }

    public Table(String name) {
        this.name = name;
    }


    public void addColumn(Column column) {
        if (column != null) {
            this.columns.add(column);
        }
    }


    public void addRelationship(Relationship relationship) {
        if (relationship != null && !relationships.contains(relationship)) {
            this.relationships.add(relationship);
        }
    }

    public Column getColumn(String name) {
        return columns.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }


    public void setPosition(double x, double y) {
        this.position = new com.github.paohaijiao.layout.Point(x, y);
        updateBounds();
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
        updateBounds();
    }

    public void setSize(int width, int height) {
        this.size = new Dimension(width, height);
        updateBounds();
    }


    public Rectangle getBounds() {
        if (bounds == null) {
            updateBounds();
        }
        return bounds;
    }

    private void updateBounds() {
        bounds = new Rectangle(
                (int) position.x,
                (int) position.y,
                size.width,
                size.height
        );
    }

    public boolean contains(com.github.paohaijiao.layout.Point point) {
        return bounds != null && bounds.contains(point.x, point.y);
    }

    public com.github.paohaijiao.layout.Point getCenter() {
        return new com.github.paohaijiao.layout.Point(position.x + size.width / 2.0, position.y + size.height / 2.0);
    }

    public com.github.paohaijiao.layout.Point getConnectionPoint(com.github.paohaijiao.layout.Point target) {
        com.github.paohaijiao.layout.Point center = getCenter();
        double dx = target.x - center.x;
        double dy = target.y - center.y;

        if (Math.abs(dx) > Math.abs(dy)) {
            // 水平方向
            double x = dx > 0 ? position.x + size.width : position.x;
            double y = center.y;
            return new com.github.paohaijiao.layout.Point(x, y);
        } else {
            // 垂直方向
            double x = center.x;
            double y = dy > 0 ? position.y + size.height : position.y;
            return new Point(x, y);
        }
    }
}
