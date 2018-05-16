package es.marcmauri.notepad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import es.marcmauri.notepad.R;
import es.marcmauri.notepad.models.Note;

public class NoteAdapter extends BaseAdapter {

    // Atributos de la clase
    private Context context;
    private int layout;
    private List<Note> notes;

    public NoteAdapter(Context context, int layout, List<Note> notes) {
        this.context = context;
        this.layout = layout;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return this.notes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        // Si es la primera vez...
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            holder = new ViewHolder();
            holder.textView_title = convertView.findViewById(R.id.textView_title);
            holder.textView_lastEdit = convertView.findViewById(R.id.textView_lastEdit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Rellenamos los campos del layout con la info de la nota actual
        final Note currentNote = this.notes.get(position);
        holder.textView_title.setText(currentNote.getTitle());
        holder.textView_lastEdit.setText(String.valueOf(currentNote.getLastEdit()));

        return convertView;
    }

    private static class ViewHolder {
        private TextView textView_title;
        private TextView textView_lastEdit;
    }
}
