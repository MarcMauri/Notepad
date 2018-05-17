package es.marcmauri.notepad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.marcmauri.notepad.R;
import es.marcmauri.notepad.adapters.NoteAdapter;
import es.marcmauri.notepad.models.Note;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    // ListView y Adapter
    private ListView listView;
    private NoteAdapter noteAdapter;

    // Lista de nuestro modelo, nota
    private Realm realm;
    private RealmResults<Note> notes;

    // FAB to Add note
    private FloatingActionButton fab_addNote;

    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        bindUI();

        // Databases
        realm = Realm.getDefaultInstance();
        notes = getAllNotes();
        count = notes.size();

        // Adjuntando el metodo click para el FAB
        this.fab_addNote.setOnClickListener(this);

        // Adjuntando el metodo click para los elementos del listView
        this.listView.setOnItemClickListener(this);

        // Inicializando el adaptador y lo adjuntamos al listView
        this.noteAdapter = new NoteAdapter(this, R.layout.note_list_item, this.notes);
        this.listView.setAdapter(noteAdapter);

        // Registramos el context menu para las vistas
        registerForContextMenu(this.listView);
    }


    // Customizations

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

    private void bindUI() {
        this.listView = findViewById(R.id.listView);
        this.fab_addNote = findViewById(R.id.fab_addNote);
    }


    // Events

    //FAB listener
    @Override
    public void onClick(View v) {
        createNewNote(++count);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Se hace click en la nota notes[position]
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("title", this.notes.get(position).getTitle());
        intent.putExtra("content", this.notes.get(position).getContent());
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Para sacar el item de la lista, primero debemos saber a cual nos referimos
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        // , y seteamos el titulo de la cabecera del menu:
        menu.setHeaderTitle(this.notes.get(info.position).getTitle());

        // e inflamos el menu con el layout creado + el menu arriba modificado;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Para sacar el item de la lista, primero debemos saber a cual nos referimos
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.menu_delNote:
                // Aqui debemos borrar la nota
                removeNote(this.notes.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }


    // CRUD
    private RealmResults<Note> getAllNotes() {
        return realm.where(Note.class).findAll();
    }

    private void createNewNote (int newPos) {
        try {
            realm.beginTransaction();
            Note _note = new Note("Nueva nota #" + newPos, "Contenido de la nota #" + newPos, new Date().getTime(), new Date().getTime());
            realm.copyToRealm(_note);
            realm.commitTransaction();
            this.noteAdapter.notifyDataSetChanged();
            this.listView.smoothScrollToPosition(this.notes.size()-1);
        } catch (Exception e) {
            Toast.makeText(this, "Error al crear ciudad:\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void removeNote(Note note) {
        try {
            realm.beginTransaction();
            note.deleteFromRealm();
            realm.commitTransaction();
            Toast.makeText(this, "It has been deleted successfully!", Toast.LENGTH_SHORT).show();
            this.noteAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(this, "An error has been occurred. \n" + e, Toast.LENGTH_SHORT).show();
        }
    }

}
