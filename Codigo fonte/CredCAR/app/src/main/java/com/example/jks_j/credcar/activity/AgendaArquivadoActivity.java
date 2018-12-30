package com.example.jks_j.credcar.activity;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jks_j.credcar.helper.DBHelper;
import com.example.jks_j.credcar.R;
import com.example.jks_j.credcar.adapter.ArquivadoAdapter;
import com.example.jks_j.credcar.model.Arquivado;
import com.example.jks_j.credcar.model.Situacao;

public class AgendaArquivadoActivity extends AppCompatActivity {

    private ListView lista;
    private DBHelper banco;
    private SharedPreferences sharedPreferencesAgendaArquivadoActivity;
    private static final String ARQUIVO_PREFERENCIA = "ArqPreferencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_arquivado);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Arquivado");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        banco = new DBHelper(getApplicationContext());

        lista = findViewById(R.id.lv_agenda_arquivado);
        ArquivadoAdapter adapter = new ArquivadoAdapter(getApplicationContext(), banco.getDBArquivado());

        lista.setAdapter(adapter);

        registerForContextMenu(lista);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_flutuante_arquivado, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Arquivado a = (Arquivado) lista.getItemAtPosition(info.position);
        try {
            switch (item.getItemId()) {
                case R.id.menu_flutuante_arquivado_restaurar:
                    a.setArquivado("0");
                    if(banco.desarquivar(a)==1) {
                        Toast.makeText(getApplicationContext(), "Situação restaurada", Toast.LENGTH_SHORT).show();
                        sharedPreferencesAgendaArquivadoActivity = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                        SharedPreferences.Editor editor = sharedPreferencesAgendaArquivadoActivity.edit();
                        editor.putBoolean("atualizarMain", true);
                        editor.commit();
                        recreate();
                    }
                    return true;
                case R.id.menu_flutuante_arquivado_excluir:
                    if(banco.deletarSituacao(a.getId_passageiro())==1) {
                        Toast.makeText(getApplicationContext(), "Situação excluida", Toast.LENGTH_SHORT).show();
                        recreate();
                    }
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Falha na operacao", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}