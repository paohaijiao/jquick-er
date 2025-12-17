package com.github.paohaijiao.config;

import com.github.paohaijiao.enums.RelationshipType;

import java.awt.*;

public class ThemeConfig {
    // 背景
    public Color background = new Color(255, 255, 255);

    // 表格
    public Color tableBackground = Color.WHITE;
    public Color tableSelectedBackground = new Color(240, 248, 255);
    public Color tableBorder = new Color(200, 200, 200);
    public Color tableSelectedBorder = new Color(66, 133, 244);
    public Color tableHeaderBackground = new Color(245, 245, 245);

    // 文本颜色 - 简化颜色配置
    public Color tableNameColor = new Color(33, 33, 33);
    public Color columnNameColor = new Color(66, 66, 66); // 所有字段使用这个颜色
    public Color commentColor = new Color(150, 150, 150);
    public Color titleColor = new Color(33, 33, 33);
    public Color watermarkColor = new Color(180, 180, 180, 100);
    public Color dateColor = new Color(150, 150, 150);

    // 特殊标记颜色（仅用于图标，文本颜色统一使用columnNameColor）
    public Color primaryKeyColor = new Color(219, 68, 55);
    public Color foreignKeyColor = new Color(15, 157, 88);
    public Color notNullColor = new Color(244, 180, 0); // 保留但文本中不再使用

    // 关系线颜色 - 保留但不再使用，统一使用黑色虚线
    public Color relationshipOneToOne = new Color(66, 133, 244);
    public Color relationshipOneToMany = new Color(219, 68, 55);
    public Color relationshipManyToMany = new Color(249, 168, 37);
    public Color relationshipIdentifying = new Color(171, 71, 188);

    // 字体
    public Font titleFont = new Font("Microsoft YaHei", Font.BOLD, 22);
    public Font tableNameFont = new Font("Microsoft YaHei", Font.BOLD, 14);
    public Font columnFont = new Font("Microsoft YaHei", Font.PLAIN, 12);
    public Font commentFont = new Font("Microsoft YaHei", Font.ITALIC, 11);
    public Font relationshipFont = new Font("Microsoft YaHei", Font.PLAIN, 11);
    public Font watermarkFont = new Font("Microsoft YaHei", Font.ITALIC, 12);
    public Font dateFont = new Font("Microsoft YaHei", Font.PLAIN, 11);

    public Color getRelationshipColor(RelationshipType type) {
        return Color.BLACK;
    }
}
