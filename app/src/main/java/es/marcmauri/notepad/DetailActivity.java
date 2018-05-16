package es.marcmauri.notepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    // Note attributes
    private String title;
    private String content;

    // UI attributes
    private TextView textView_title;
    private TextView textView_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setToolbar();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("title") != null && !bundle.getString("title").isEmpty()) {
                this.title = bundle.getString("title");
            }
            if (bundle.getString("content") != null && !bundle.getString("content").isEmpty())
                this.content = bundle.getString("content");
        } else {
            Toast.makeText(this, "No se mandaron datos al llamar este Activity", Toast.LENGTH_LONG).show();
        }


        bindUI();
        paintNoteAttributes();

    }

    private void bindUI() {
        this.textView_title = findViewById(R.id.textView_title);
        this.textView_content = findViewById(R.id.textView_content);
    }

    private void paintNoteAttributes() {
        this.textView_title.setText(this.title);
        this.textView_content.setText(this.content);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }
}
