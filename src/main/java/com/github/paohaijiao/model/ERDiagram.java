package com.github.paohaijiao.model;

import com.github.paohaijiao.layout.Point;
import com.github.paohaijiao.relation.Relationship;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ERDiagram {

    private final Map<String, Table> tables = new LinkedHashMap<>();

    private final List<Relationship> relationships = new ArrayList<>();

    private String title = "ER Diagram";

    private Dimension size = new Dimension(1600, 1200);

    private String author;

    private Date createdDate = new Date();

    private String description;

    public ERDiagram() {
    }

    public void addTable(Table table) {
        if (table != null && table.getName() != null) {
            tables.put(table.getName(), table);
        }
    }

    public Table getTable(String name) {
        return tables.get(name);
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void addRelationship(Relationship relationship) {
        if (relationship != null && !relationships.contains(relationship)) {
            relationships.add(relationship);
            // 添加到相关表中
            Table sourceTable = getTable(relationship.getSourceTable());
            Table targetTable = getTable(relationship.getTargetTable());
            if (sourceTable != null) {
                sourceTable.addRelationship(relationship);
            }
            if (targetTable != null) {
                targetTable.addRelationship(relationship);
            }
        }
    }

    public void removeTable(String tableName) {
        Table table = tables.remove(tableName);
        if (table != null) {
            // 移除相关的关系
            relationships.removeIf(rel -> rel.getSourceTable().equals(tableName) || rel.getTargetTable().equals(tableName));
        }
    }

    public void removeRelationship(Relationship relationship) {
        relationships.remove(relationship);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public void setSize(int width, int height) {
        this.size = new Dimension(width, height);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Table findTableContaining(com.github.paohaijiao.layout.Point point) {
        for (Table table : tables.values()) {
            if (table.contains(point)) {
                return table;
            }
        }
        return null;
    }

    public Relationship findRelationshipNear(com.github.paohaijiao.layout.Point point, double tolerance) {
        for (Relationship rel : relationships) {
            if (!rel.getPathPoints().isEmpty()) {
                for (int i = 0; i < rel.getPathPoints().size() - 1; i++) {
                    com.github.paohaijiao.layout.Point p1 = rel.getPathPoints().get(i);
                    com.github.paohaijiao.layout.Point p2 = rel.getPathPoints().get(i + 1);

                    // 计算点到线段的距离
                    double distance = distanceToSegment(point, p1, p2);
                    if (distance < tolerance) {
                        return rel;
                    }
                }
            }
        }
        return null;
    }

    private double distanceToSegment(com.github.paohaijiao.layout.Point p, com.github.paohaijiao.layout.Point a, com.github.paohaijiao.layout.Point b) {
        double length2 = (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y);
        if (length2 == 0) return p.distance(a);
        double t = Math.max(0, Math.min(1, ((p.x - a.x) * (b.x - a.x) + (p.y - a.y) * (b.y - a.y)) / length2));
        com.github.paohaijiao.layout.Point projection = new Point(a.x + t * (b.x - a.x), a.y + t * (b.y - a.y));
        return p.distance(projection);
    }
}