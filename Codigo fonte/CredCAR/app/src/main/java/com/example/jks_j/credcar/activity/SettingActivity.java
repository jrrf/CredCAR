package com.example.jks_j.credcar.activity;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jks_j.credcar.R;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferencesSettingActivity;
    private static final String ARQUIVO_PREFERENCIA = "ArqPreferencia";
    private EditText et_setting_valor_passagem;
    private Button bt_setting_salvar;
    private Switch bt_setting_diferenca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Configurações");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        et_setting_valor_passagem = findViewById(R.id.et_setting_valor_passagem);
        bt_setting_salvar = findViewById(R.id.bt_setting_salvar);
        bt_setting_diferenca = findViewById(R.id.bt_setting_diferenca);

        sharedPreferencesSettingActivity = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);

        et_setting_valor_passagem.setHint(sharedPreferencesSettingActivity.getString("valorPassagem", "5.0"));

        bt_setting_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputValor = et_setting_valor_passagem.getText().toString();
                if (inputValor.isEmpty() || inputValor.equalsIgnoreCase("0.0") || inputValor.equalsIgnoreCase("0,0")) {
                    inputValor = sharedPreferencesSettingActivity.getString("valorPassagem", "5.0");
                }
                inputValor = inputValor.replace(",", ".");
                Double valor = Double.parseDouble(inputValor);
                inputValor = String.valueOf(valor);
                sharedPreferencesSettingActivity = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                SharedPreferences.Editor editor = sharedPreferencesSettingActivity.edit();
                editor.putString("valorPassagem", inputValor);
                editor.commit();
                finish();

            }
        });

        sharedPreferencesSettingActivity = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        if (sharedPreferencesSettingActivity.contains("valorDiferenca"))
            bt_setting_diferenca.setChecked(sharedPreferencesSettingActivity.getBoolean("valorDiferenca", false));

        bt_setting_diferenca.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferencesSettingActivity = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                SharedPreferences.Editor editor = sharedPreferencesSettingActivity.edit();
                if (isChecked)
                    editor.putBoolean("valorDiferenca", true);
                else
                    editor.putBoolean("valorDiferenca", false);
                editor.putBoolean("atualizarMain", true);
                editor.commit();
            }
        });
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
