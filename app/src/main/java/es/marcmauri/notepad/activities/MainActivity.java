package es.marcmauri.notepad.activities;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    // ListView y Adapter
    private ListView listView;
    private NoteAdapter noteAdapter;

    // Lista de nuestro modelo, nota
    private List<Note> notes;

    // FAB to Add note
    private FloatingActionButton fab_addNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        bindUI();

        // Recogemos datos de prueba
        notes = getMockData();

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

    private List<Note> getMockData() {
        return new ArrayList<Note>() {{
            add(new Note("Titulo 1", "Contenido de la nota 1", new Date().getTime(), new Date().getTime()));
            add(new Note("Titulo 2", "Contenido de la nota 2", new Date().getTime(), new Date().getTime()));
            add(new Note("Titulo 3", "Contenido de la nota 3", new Date().getTime(), new Date().getTime()));
            add(new Note("Titulo 4", "Contenido de la nota 4", new Date().getTime(), new Date().getTime()));
            add(new Note("Titulo 5", "Contenido de la nota 5", new Date().getTime(), new Date().getTime()));
        }};
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

    private void removeNote(int position) {
        // Eliminamos la nota por la posicion en la que se encuentra
        this.notes.remove(position);
        // Notificamos al adaptador que los cambios han cambiado
        this.noteAdapter.notifyDataSetChanged();
    }


    // Events

    //FAB listener
    @Override
    public void onClick(View v) {
        Toast.makeText(this, "New note is coming", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Se hace click en la nota notes[position]
        Toast.makeText(this, "Seleccionada nota en POS " + position, Toast.LENGTH_SHORT).show();
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
                removeNote(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
