package com.github.paohaijiao.layout;

import com.github.paohaijiao.model.ERDiagram;
import com.github.paohaijiao.model.Table;
import com.github.paohaijiao.relation.Relationship;
import lombok.Data;

import java.util.*;

@Data
public class ForceDirectedLayout {
    private static final double REPULSION_FORCE = 8000;
    private static final double ATTRACTION_FORCE = 0.04;
    private static final double IDEAL_DISTANCE = 300;
    private static final double DAMPING = 0.85;
    private static final int MAX_ITERATIONS = 500;
    private static final double MIN_TEMPERATURE = 0.1;

    public static void layout(ERDiagram diagram) {
        Map<String, Table> tables = diagram.getTables();
        List<Relationship> relationships = diagram.getRelationships();
        if (tables.isEmpty()) return;
        initializePositions(tables.values());// 初始化位置
        // 力导向布局
        double temperature = 100.0;
        for (int iteration = 0; iteration < MAX_ITERATIONS && temperature > MIN_TEMPERATURE; iteration++) {
            temperature *= DAMPING;
            Map<String, Point> forces = new HashMap<>();
            // 计算排斥力
            for (Table table1 : tables.values()) {
                Point force = new Point(0, 0);
                for (Table table2 : tables.values()) {
                    if (table1 == table2) continue;
                    Point delta = table2.getPosition().subtract(table1.getPosition());
                    double distance = delta.length();
                    if (distance == 0) {
                        delta = new Point(Math.random() - 0.5, Math.random() - 0.5);
                        distance = 0.1;
                    }
                    // 库仑斥力
                    double repulsion = REPULSION_FORCE / (distance * distance);
                    Point repulsionForce = delta.normalize().multiply(-repulsion);
                    force = force.add(repulsionForce);
                }

                forces.put(table1.getName(), force);
            }
            // 计算吸引力（基于关系）
            for (Relationship rel : relationships) {
                Table source = tables.get(rel.getSourceTable());
                Table target = tables.get(rel.getTargetTable());
                if (source == null || target == null) continue;
                Point delta = target.getPosition().subtract(source.getPosition());
                double distance = delta.length();
                if (distance == 0) continue;
                // 胡克定律吸引力
                double attraction = ATTRACTION_FORCE * (distance - IDEAL_DISTANCE);
                Point attractionForce = delta.normalize().multiply(attraction);
                // 更新力
                Point sourceForce = forces.get(source.getName());
                Point targetForce = forces.get(target.getName());
                forces.put(source.getName(), sourceForce.add(attractionForce));
                forces.put(target.getName(), targetForce.subtract(attractionForce));
            }
            // 应用力（考虑温度）
            for (Table table : tables.values()) {
                Point force = forces.get(table.getName());
                if (force.length() > temperature) {
                    force = force.normalize().multiply(temperature);
                }
                Point newPos = table.getPosition().add(force);
                table.setPosition(newPos);
            }
            // 防止重叠
            avoidOverlap(tables.values());
        }
        // 调整到画布中心
        centerDiagram(tables.values(), diagram.getSize());
    }

    private static void initializePositions(Collection<Table> tables) {
        int count = tables.size();
        double radius = Math.min(400, count * 50);
        int i = 0;
        for (Table table : tables) {
            double angle = 2 * Math.PI * i / count;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            table.setPosition(x, y);
            i++;
        }
    }

    private static void avoidOverlap(Collection<Table> tables) {
        List<Table> tableList = new ArrayList<>(tables);
        for (int i = 0; i < tableList.size(); i++) {
            Table t1 = tableList.get(i);
            for (int j = i + 1; j < tableList.size(); j++) {
                Table t2 = tableList.get(j);
                double dx = t2.getPosition().x - t1.getPosition().x;
                double dy = t2.getPosition().y - t1.getPosition().y;
                double distance = Math.sqrt(dx * dx + dy * dy);
                double minDistance = Math.max(t1.getSize().width, t1.getSize().height) / 2 + Math.max(t2.getSize().width, t2.getSize().height) / 2 + 20;
                if (distance < minDistance) {
                    double overlap = minDistance - distance;
                    if (overlap > 0) {
                        Point direction = new Point(dx, dy).normalize();
                        Point adjustment = direction.multiply(overlap / 2);
                        t1.setPosition(t1.getPosition().subtract(adjustment));
                        t2.setPosition(t2.getPosition().add(adjustment));
                    }
                }
            }
        }
    }

    private static void centerDiagram(Collection<Table> tables, java.awt.Dimension canvasSize) {
        if (tables.isEmpty()) return;
        // 计算边界
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        for (Table table : tables) {
            minX = Math.min(minX, table.getPosition().x);
            maxX = Math.max(maxX, table.getPosition().x + table.getSize().width);
            minY = Math.min(minY, table.getPosition().y);
            maxY = Math.max(maxY, table.getPosition().y + table.getSize().height);
        }

        double width = maxX - minX;
        double height = maxY - minY;
        // 计算偏移量
        double offsetX = (canvasSize.width - width) / 2 - minX;
        double offsetY = (canvasSize.height - height) / 2 - minY;
        // 应用偏移
        for (Table table : tables) {
            table.setPosition(table.getPosition().x + offsetX, table.getPosition().y + offsetY);
        }
    }

    public static void calculateRelationshipPaths(ERDiagram diagram) {
        for (Relationship rel : diagram.getRelationships()) {
            rel.clearPathPoints();
            Table source = diagram.getTable(rel.getSourceTable());
            Table target = diagram.getTable(rel.getTargetTable());
            if (source == null || target == null) continue;
            // 获取连接点
            Point sourcePoint = source.getConnectionPoint(target.getCenter());
            Point targetPoint = target.getConnectionPoint(source.getCenter());
            // 创建贝塞尔曲线控制点
            double dx = targetPoint.x - sourcePoint.x;
            double dy = targetPoint.y - sourcePoint.y;
            Point control1, control2;
            if (Math.abs(dx) > Math.abs(dy)) {
                // 水平为主
                control1 = new Point(sourcePoint.x + dx * 0.3, sourcePoint.y);
                control2 = new Point(targetPoint.x - dx * 0.3, targetPoint.y);
            } else {
                // 垂直为主
                control1 = new Point(sourcePoint.x, sourcePoint.y + dy * 0.3);
                control2 = new Point(targetPoint.x, targetPoint.y - dy * 0.3);
            }
            // 添加路径点
            rel.addPathPoint(sourcePoint);
            rel.addPathPoint(control1);
            rel.addPathPoint(control2);
            rel.addPathPoint(targetPoint);
            // 计算标签位置
            Point labelPos = calculateBezierPoint(0.5, sourcePoint, control1, control2, targetPoint);
            rel.setLabelPosition(labelPos);
        }
    }

    private static Point calculateBezierPoint(double t, Point p0, Point p1, Point p2, Point p3) {
        double u = 1 - t;
        double tt = t * t;
        double uu = u * u;
        double uuu = uu * u;
        double ttt = tt * t;
        double x = uuu * p0.x + 3 * uu * t * p1.x + 3 * u * tt * p2.x + ttt * p3.x;
        double y = uuu * p0.y + 3 * uu * t * p1.y + 3 * u * tt * p2.y + ttt * p3.y;

        return new Point(x, y);
    }
}