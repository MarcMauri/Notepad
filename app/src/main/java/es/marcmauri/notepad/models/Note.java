package es.marcmauri.notepad.models;

import java.util.Date;

public class Note {

    private String title;
    private String content;
    private long createdAt;
    private long lastEdit;

    public Note() {
    }

    public Note(String title, String content, long createdAt, long lastEdit) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.lastEdit = lastEdit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(long lastEdit) {
        this.lastEdit = lastEdit;
    }
}
