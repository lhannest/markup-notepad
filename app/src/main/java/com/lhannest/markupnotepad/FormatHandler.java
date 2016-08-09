package com.lhannest.markupnotepad;


public class FormatHandler {
    public static final char escapeChar = '/';

    private int[] formats = new int[FormatType.values().length];

    public FormatHandler() {
        for (int i = 0; i < formats.length; i++) {
            formats[i] = -1;
        }
    }

    public boolean contains(FormatType type) {
        return formats[type.ordinal()] != -1;
    }

    public void remove(FormatType type) {
        formats[type.ordinal()] = -1;
    }

    public int getLocation(FormatType type) {
        return formats[type.ordinal()];
    }

    public void setLocation(FormatType type, int location) {
        formats[type.ordinal()] = location;
    }

    public void bump(int location, int distance) {
        for (int i = 0; i < formats.length; i++) {
            if (formats[i] != -1 && formats[i] >= location) {
                formats[i] += distance;
            }
        }
    }

    public void clear(int start, int end) {
        for (int i = 0; i < formats.length; i++) {
            if (formats[i] >= start && formats[i] <= end) {
                formats[i] = -1;
            }
        }
    }

    public void dropDown(int location, int distance) {
        for (int i = 0; i < formats.length; i++) {
            if (formats[i] != -1 && formats[i] >= location) {
                formats[i] -= distance;
            }
        }
    }

    public static String getSymbol(FormatType type) {
        if (type.equals(FormatType.BOLD)) {
            return "#";
        } else if (type.equals(FormatType.ITALIC)) {
            return "*";
        } else if (type.equals(FormatType.UNDERLINE)) {
            return "_";
        } else if (type.equals(FormatType.SUPER)) {
            return "+";
        } else if (type.equals(FormatType.SUB)) {
            return "-";
        } else if (type.equals(FormatType.BIG)) {
            return "&";
        } else if (type.equals(FormatType.SMALL)) {
            return "@";
        } else {
            return null;
        }
    }

    public static String openingTag(FormatType type) {
        if (type.equals(FormatType.BOLD)) {
            return "<b>";
        } else if (type.equals(FormatType.ITALIC)) {
            return "<i>";
        } else if (type.equals(FormatType.UNDERLINE)) {
            return "<u>";
        } else if (type.equals(FormatType.SUPER)) {
            return "<small><sup>";
        } else if (type.equals(FormatType.SUB)) {
            return "<small><sub>";
        } else if (type.equals(FormatType.BIG)) {
            return "<font size=\"2\">";
        } else if (type.equals(FormatType.SMALL)) {
            return "<h6>";
        } else {
            return null;
        }
    }

    public static String closingTag(FormatType type) {
        if (type.equals(FormatType.BOLD)) {
            return "</b>";
        } else if (type.equals(FormatType.ITALIC)) {
            return "</i>";
        } else if (type.equals(FormatType.UNDERLINE)) {
            return "</u>";
        } else if (type.equals(FormatType.SUPER)) {
            return "</sup></small>";
        } else if (type.equals(FormatType.SUB)) {
            return "</sub></small>";
        } else if (type.equals(FormatType.BIG)) {
            return "</font>";
        } else if (type.equals(FormatType.SMALL)) {
            return "</h6>";
        } else {
            return null;
        }
    }
}
