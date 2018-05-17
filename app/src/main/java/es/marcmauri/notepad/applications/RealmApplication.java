package es.marcmauri.notepad.applications;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import es.marcmauri.notepad.models.Note;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmApplication extends Application {

    public static AtomicInteger NoteID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        setUpRealmConfig();
        Realm realm = Realm.getDefaultInstance();
        try {
            NoteID = getIdByTable(realm, Note.class);
        } finally {
            realm.close();
        }
    }

    private void setUpRealmConfig() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("notes.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass) {
        RealmResults<T> res = realm.where(anyClass).findAll();
        return (res.size() > 0) ? new AtomicInteger(res.max("id").intValue()) : new AtomicInteger();
    }
}
