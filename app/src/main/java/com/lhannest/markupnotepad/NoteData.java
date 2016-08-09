package com.lhannest.markupnotepad;

import java.util.Comparator;

public class NoteData implements Comparable<NoteData> {
    private Integer id;
    private String title;
    private Time timeCreated;
    private Time lastEdited;
    private int wordCount;

    public NoteData(Integer id, String title, Time timeCreated, Time lastEdited, int wordCount) {
        this.id = id;
        this.title = title;
        this.timeCreated = timeCreated;
        this.lastEdited = lastEdited;
        this.wordCount = wordCount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Time getTimeCreated() {
        return timeCreated;
    }

    public Time getLastEdited() {
        return lastEdited;
    }

    public int getWordCount() {
        return wordCount;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[id=" + id + ", textView=" + title +
                ", timeCreated=" + timeCreated + ", lastEdited=" + lastEdited + ", wordCount=" +
                wordCount + "]";
    }

    @Override
    public int compareTo(NoteData other) {
        return lastEdited.compareTo(other.lastEdited);
    }
}
