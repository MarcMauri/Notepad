package es.marcmauri.notepad.models;

import es.marcmauri.notepad.applications.RealmApplication;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Note extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String title;
    @Required
    private String content;
    private long createdAt;
    private long lastEdit;

    public Note() {
    }

    public Note(String title, String content, long createdAt, long lastEdit) {
        this.id = RealmApplication.NoteID.incrementAndGet();
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.lastEdit = lastEdit;
    }

    public int getId() {
        return id;
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
