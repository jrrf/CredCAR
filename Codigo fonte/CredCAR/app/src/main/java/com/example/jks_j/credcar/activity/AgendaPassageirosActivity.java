package com.example.jks_j.credcar.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.jks_j.credcar.adapter.AbasAdapter;
import com.example.jks_j.credcar.fragment.PassageiroFragment;
import com.example.jks_j.credcar.model.Passageiro;
import com.example.jks_j.credcar.R;


public class AgendaPassageirosActivity extends AppCompatActivity {

    private static final String ARQUIVO_PREFERENCIA = "ArqPreferencia";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_passageiros);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Passageiros");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);


        AbasAdapter adapter = new AbasAdapter( getSupportFragmentManager() );
        adapter.adicionar( new PassageiroFragment(), "PASSAGEIRO");

        ViewPager viewPager = (ViewPager) findViewById(R.id.abas_view_pager_passageiro);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferencesAgendaPassageirosActivity = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        if (sharedPreferencesAgendaPassageirosActivity.contains("atualizarPassageiros")) {
            Boolean atualizarPassageiros= sharedPreferencesAgendaPassageirosActivity.getBoolean("atualizarPassageiros", false);
            if(atualizarPassageiros){
                SharedPreferences.Editor editor = sharedPreferencesAgendaPassageirosActivity.edit();
                editor.putBoolean("atualizarPassageiros", false);
                editor.commit();
                recreate();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_passageiro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novo_passageiro:
                startActivity(new Intent(getApplicationContext(), CadastroPassageiroActivity.class));
                return true;
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
