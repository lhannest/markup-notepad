package com.lhannest.markupnotepad;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private NoteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new NoteDatabase(getApplicationContext());
        database.open();

        List<NoteData> notes = database.getAll();

        ListView listView = (ListView) findViewById(R.id.main_list);
        ListAdapter adapter = new ListAdapter(this, notes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteData noteData = database.getByPosition(position);
                Toast.makeText(getApplicationContext(), noteData.getTitle(), Toast.LENGTH_SHORT).show();
                startWriterActivity(noteData.getId());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void finish() {
        database.close();
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.new_note) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startWriterActivity(int noteId) {
        Intent intent = new Intent(MainActivity.this, WriterActivity.class);
        String fileName = "" + noteId;
        intent.putExtra("file_name", fileName);
        startActivity(intent);
    }
}
