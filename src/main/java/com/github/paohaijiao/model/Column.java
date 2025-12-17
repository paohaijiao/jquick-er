package com.github.paohaijiao.model;

import java.awt.*;

public class Column {

    private String name;

    private String dataType;

    private Integer length;

    private Integer precision;

    private boolean nullable = true;

    private String comment;

    private boolean primaryKey = false;

    private boolean foreignKey = false;

    private Color color;

    public Column() {
    }

    public Column(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (primaryKey) sb.append("🔑 ");
        if (foreignKey) sb.append("🔗 ");
        sb.append(name).append(" : ").append(dataType);
        if (length != null) {
            sb.append("(").append(length);
            if (precision != null) {
                sb.append(",").append(precision);
            }
            sb.append(")");
        }
        if (!nullable) {
            sb.append(" NOT NULL");
        }
        return sb.toString();
    }

    public String getDisplayString() {
        return toString();
    }
}
