package com.example.jks_j.credcar.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jks_j.credcar.adapter.AbasAdapter;
import com.example.jks_j.credcar.fragment.HistoricoFragment;
import com.example.jks_j.credcar.fragment.SituacaoFragment;
import com.example.jks_j.credcar.helper.DBHelper;
import com.example.jks_j.credcar.R;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferencesMainActivity;
    private static final String ARQUIVO_PREFERENCIA = "ArqPreferencia";

    private TextView tv_main_valor_total;
    private DBHelper banco;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("CredCAR");
        ab.setElevation(0);

        banco = new DBHelper(getApplicationContext());

        AbasAdapter adapter = new AbasAdapter(getSupportFragmentManager());
        adapter.adicionar(new SituacaoFragment(), "SITUACAO");
        adapter.adicionar(new HistoricoFragment(), "HISTORICO");

        ViewPager viewPager = (ViewPager) findViewById(R.id.abas_view_pager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.abas);
        tabLayout.setupWithViewPager(viewPager);

        tv_main_valor_total = findViewById(R.id.tv_main_valor_total);

        sharedPreferencesMainActivity = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        Boolean valorDiferenca = sharedPreferencesMainActivity.getBoolean("valorDiferenca", false);
        if (valorDiferenca) {
            tv_main_valor_total.setText("R$ " + banco.totalDiferenca());
        }else {
            tv_main_valor_total.setText("R$ " + banco.totalDevendo());
        }

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AgendaPassageirosActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferencesMainActivity = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        if (sharedPreferencesMainActivity.contains("atualizarMain")) {
            Boolean atualizarMain = sharedPreferencesMainActivity.getBoolean("atualizarMain", false);
            if (atualizarMain) {
                SharedPreferences.Editor editor = sharedPreferencesMainActivity.edit();
                editor.putBoolean("atualizarMain", false);
                editor.commit();
                recreate();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        MenuItem itemArquivado = menu.findItem(R.id.situacao_arquivado);
        if (banco.getDBArquivado().size() > 0)
            itemArquivado.setVisible(true);
        else itemArquivado.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sobre:
                mensagemSobre();
                return true;

            case R.id.sair:
                sair();
                return true;

            case R.id.configuracoes:
                configuracao();
                return true;

            case R.id.compartilhar:
                compartilharApp();
                return true;

            case R.id.home:
                sair();
                return true;

            case R.id.situacao_arquivado:
                startActivity(new Intent(getApplicationContext(), AgendaArquivadoActivity.class));
                return true;

            case R.id.limpar_historico:
                limparHistorico();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        sair();
    }

    public void sair() {
        new AlertDialog.Builder(this).setTitle("Deseja realmente sair?")
                .setMessage("Fechar o aplicativo")
                .setCancelable(true)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void compartilharApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.texto_compartilhar));
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Teste subject");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getString(R.string.menu_compartilhar)));
    }

    public void mensagemSobre() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage(getString(R.string.descricao_sobre));
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.texto_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
//        alertDialog.setIcon();
        alertDialog.show();
    }

    public void configuracao() {
        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
    }

    public void limparHistorico() {
        new AlertDialog.Builder(this).setTitle("Deseja realmente limpar o historico?")
                .setMessage("Isso apagará todo o histórico do registro")
                .setCancelable(true)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            if (banco.limparHistorico() > 0) {
                                Toast.makeText(getApplicationContext(), "Historico limpado com sucesso", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Falha na limpeza", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Falha ao limpar!", Toast.LENGTH_LONG).show();
                        }
                        recreate();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
