package com.github.paohaijiao.theme;

import com.github.paohaijiao.enums.RelationshipType;

import java.awt.*;
import java.awt.geom.Point2D;

public class ColorTheme {
    // 主色调 - Material Design 调色板
    public static final Color PRIMARY_COLOR = new Color(30, 136, 229); // 蓝色
    public static final Color SECONDARY_COLOR = new Color(0, 150, 136); // 青色
    public static final Color ACCENT_COLOR = new Color(255, 193, 7); // 黄色

    // 表颜色方案
    public static final Color[] TABLE_COLORS = {
            new Color(66, 133, 244),   // Google Blue
            new Color(52, 168, 83),    // Google Green
            new Color(251, 188, 5),    // Google Yellow
            new Color(234, 67, 53),    // Google Red
            new Color(171, 71, 188),   // Google Purple
            new Color(255, 109, 0),    // Google Orange
            new Color(0, 172, 193),    // Cyan
            new Color(124, 179, 66)    // Light Green
    };

    // 表头渐变
    public static final Color TABLE_HEADER_START = new Color(30, 136, 229);
    public static final Color TABLE_HEADER_END = new Color(21, 101, 192);

    // 背景
    public static final Color BACKGROUND_START = new Color(250, 250, 250);
    public static final Color BACKGROUND_END = new Color(245, 245, 245);
    public static final Color GRID_COLOR = new Color(240, 240, 240);

    // 文本
    public static final Color TEXT_PRIMARY = new Color(33, 33, 33);
    public static final Color TEXT_SECONDARY = new Color(97, 97, 97);
    public static final Color TEXT_COMMENT = new Color(158, 158, 158);
    public static final Color TEXT_ON_DARK = Color.WHITE;

    // 关系线
    public static final Color RELATIONSHIP_ONE_TO_ONE = new Color(30, 136, 229);
    public static final Color RELATIONSHIP_ONE_TO_MANY = new Color(0, 150, 136);
    public static final Color RELATIONSHIP_MANY_TO_MANY = new Color(255, 109, 0);
    public static final Color RELATIONSHIP_IDENTIFYING = new Color(171, 71, 188);

    // 边框和阴影
    public static final Color TABLE_BORDER = new Color(224, 224, 224);
    public static final Color TABLE_SHADOW = new Color(0, 0, 0, 30);
    public static final Color SELECTION_BORDER = new Color(66, 133, 244);
    public static final Color SELECTION_FILL = new Color(66, 133, 244, 20);

    // 特殊标记
    public static final Color PRIMARY_KEY = new Color(251, 188, 5);
    public static final Color FOREIGN_KEY = new Color(52, 168, 83);
    public static final Color NOT_NULL = new Color(234, 67, 53);

    // 获取表颜色
    public static Color getTableColor(int index) {
        return TABLE_COLORS[Math.abs(index) % TABLE_COLORS.length];
    }

    // 获取关系颜色
    public static Color getRelationshipColor(RelationshipType type) {
        switch (type) {
            case ONE_TO_ONE:
                return RELATIONSHIP_ONE_TO_ONE;
            case ONE_TO_MANY:
                return RELATIONSHIP_ONE_TO_MANY;
            case MANY_TO_MANY:
                return RELATIONSHIP_MANY_TO_MANY;
            case IDENTIFYING:
                return RELATIONSHIP_IDENTIFYING;
            default:
                return RELATIONSHIP_ONE_TO_MANY;
        }
    }

    // 创建表头渐变
    public static LinearGradientPaint createTableHeaderGradient(float x, float y, float width, float height) {
        Point2D start = new Point2D.Float(x, y);
        Point2D end = new Point2D.Float(x, y + height);
        float[] fractions = {0.0f, 1.0f};
        Color[] colors = {TABLE_HEADER_START, TABLE_HEADER_END};
        return new LinearGradientPaint(start, end, fractions, colors);
    }

    // 创建背景渐变
    public static LinearGradientPaint createBackgroundGradient(float width, float height) {
        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(width, height);
        float[] fractions = {0.0f, 1.0f};
        Color[] colors = {BACKGROUND_START, BACKGROUND_END};
        return new LinearGradientPaint(start, end, fractions, colors);
    }

    // 获取字体颜色（基于背景亮度）
    public static Color getContrastColor(Color backgroundColor) {
        double luminance = (0.299 * backgroundColor.getRed() +
                0.587 * backgroundColor.getGreen() +
                0.114 * backgroundColor.getBlue()) / 255;
        return luminance > 0.5 ? TEXT_PRIMARY : TEXT_ON_DARK;
    }
}
