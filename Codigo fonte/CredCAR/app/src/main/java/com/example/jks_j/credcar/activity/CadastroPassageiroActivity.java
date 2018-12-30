package com.example.jks_j.credcar.activity;


import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.jks_j.credcar.adapter.AbasAdapter;
import com.example.jks_j.credcar.fragment.CadastroFragment;
import com.example.jks_j.credcar.R;

public class CadastroPassageiroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_passageiro);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Cadastrar");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        AbasAdapter adapter = new AbasAdapter( getSupportFragmentManager() );
        adapter.adicionar( new CadastroFragment(), "CADASTRO");

        ViewPager viewPager = (ViewPager) findViewById(R.id.abas_view_pager_cadastro);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
