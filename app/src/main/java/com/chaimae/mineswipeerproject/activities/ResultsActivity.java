package com.chaimae.mineswipeerproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaimae.mineswipeerproject.R;
import com.chaimae.mineswipeerproject.controllers.BbddController;
import com.chaimae.mineswipeerproject.serializable.Log;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setDateAndTime();

        TextView textLog = (TextView) findViewById(R.id.valorsLog);
        Log log = getLog();
        assert textLog != null;
        textLog.setText(log.getLog());
        if (savedInstanceState == null) saveLog(log);
    }

    private void saveLog(final Log log) {
        final Activity activity = this;
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return new BbddController(activity).saveLog(log);
            }

            @Override
            protected void onPostExecute(Boolean ok) {
                if (ok) showToast("Log guardat correctament");
                else showToast("Error al guardar el log");
            }
        }.execute();
    }

    public void setDateAndTime() {

        TextView textView = (TextView) findViewById(R.id.diaHora);
        assert textView != null;
        textView.setText(Log.getInstance().getData());
    }

    public Log getLog() {
        Intent resultsIntent = getIntent();
        return (Log) resultsIntent.getSerializableExtra("log");
    }

    public void enviarCorreuLog(View view) {
        Intent sendEmailLog = new Intent(Intent.ACTION_SEND);
        String email = ((EditText) findViewById(R.id.correuLog)).getText().toString();
        if (!isValidEmail(email)) {
            showToast("Posa un email vàlid");
            return;
        }
        Log log = getLog();
        sendEmailLog.setData(Uri.parse("mailto:"));
        sendEmailLog.setType("text/plain");
        sendEmailLog.putExtra(Intent.EXTRA_EMAIL, email);
        sendEmailLog.putExtra(Intent.EXTRA_SUBJECT, "Log " + log.getAlies() + " - " + log.getData());
        sendEmailLog.putExtra(Intent.EXTRA_TEXT, log.getLog());
        try {
            startActivity(Intent.createChooser(sendEmailLog, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            showToast(R.string.noclients);
        }
    }

    public void sortirT(View view) {
        finish();
    }

    public void novaPartida(View view) {
        Log.reset();
        startActivity(new Intent(this, GameActivity.class));
        finish();
    }

    public boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Això afegeix els items creats a la barra de menu
        getMenuInflater().inflate(R.menu.results_setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.registres:
                startActivity(new Intent(this, AccesoBDActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void showToast(int id) {
        Toast.makeText(ResultsActivity.this, id, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String text) {
        Toast.makeText(ResultsActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
