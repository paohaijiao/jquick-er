package com.github.paohaijiao.relation;

import com.github.paohaijiao.enums.RelationshipType;
import com.github.paohaijiao.layout.Point;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Relationship {

    private String sourceTable;

    private String sourceColumn;

    private String targetTable;

    private String targetColumn;

    private RelationshipType type;

    private String label;

    private Color color;

    private List<Point> pathPoints = new ArrayList<>();

    private Point labelPosition;

    private boolean selected = false;

    public Relationship() {
    }

    public Relationship(String sourceTable, String sourceColumn, String targetTable, String targetColumn, RelationshipType type) {
        this.sourceTable = sourceTable;
        this.sourceColumn = sourceColumn;
        this.targetTable = targetTable;
        this.targetColumn = targetColumn;
        this.type = type;
    }

    public void addPathPoint(Point point) {
        this.pathPoints.add(point);
    }

    public void clearPathPoints() {
        this.pathPoints.clear();
    }

    public boolean connectsTables(String table1, String table2) {
        return (sourceTable.equals(table1) && targetTable.equals(table2)) || (sourceTable.equals(table2) && targetTable.equals(table1));
    }


}
