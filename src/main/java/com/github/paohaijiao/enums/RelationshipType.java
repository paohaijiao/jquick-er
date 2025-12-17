package com.github.paohaijiao.enums;

import lombok.Getter;

import java.awt.*;

@Getter
public enum RelationshipType {

    ONE_TO_ONE("--", "1:1", new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)),
    ONE_TO_MANY("->", "1:N", new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)),
    MANY_TO_MANY("<->", "M:N", new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{5, 5}, 0)),
    IDENTIFYING("==", "ID", new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    private final String symbol;

    private final String label;

    private final Stroke stroke;

    RelationshipType(String symbol, String label, Stroke stroke) {
        this.symbol = symbol;
        this.label = label;
        this.stroke = stroke;
    }

    public static RelationshipType fromSymbol(String symbol) {
        for (RelationshipType type : values()) {
            if (type.symbol.equals(symbol)) {
                return type;
            }
        }
        return ONE_TO_MANY;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getLabel() {
        return label;
    }

    public Stroke getStroke() {
        return stroke;
    }
}
