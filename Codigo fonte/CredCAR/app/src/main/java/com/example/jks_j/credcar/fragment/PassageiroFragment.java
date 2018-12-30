package com.example.jks_j.credcar.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jks_j.credcar.R;
import com.example.jks_j.credcar.activity.EditarPassageiroActivity;
import com.example.jks_j.credcar.adapter.PassageiroFragmentAdapter;
import com.example.jks_j.credcar.helper.DBHelper;
import com.example.jks_j.credcar.model.Passageiro;
import com.example.jks_j.credcar.model.Situacao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PassageiroFragment extends Fragment {

    private ListView lista;
    private DBHelper banco;
    PassageiroFragmentAdapter adapter;
    private SharedPreferences sharedPreferencesAgendaPassageiroFragmentActivity;
    private static final String ARQUIVO_PREFERENCIA = "ArqPreferencia";
    int[] imagens = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4,
            R.drawable.avatar5, R.drawable.avatar6, R.drawable.avatar7, R.drawable.avatar8,
            R.drawable.avatar9, R.drawable.avatar10, R.drawable.avatar11, R.drawable.avatar12,
            R.drawable.avatar13, R.drawable.avatar14, R.drawable.avatar15, R.drawable.avatar16};

    public PassageiroFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passageiro, container, false);

        lista = view.findViewById(R.id.lv_agenda_passageiros_fragment);
        banco = new DBHelper(getContext());
        adapter = new PassageiroFragmentAdapter(getContext(), banco.getDBPassageiro());

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Passageiro passageiroSelecionado = (Passageiro) adapterView.getItemAtPosition(i);
                sharedPreferencesAgendaPassageiroFragmentActivity = getActivity().getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                final Double valorPassagem = Double.parseDouble(sharedPreferencesAgendaPassageiroFragmentActivity.getString("valorPassagem", "05.00"));
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_dialog);
                Button bt_dialog_pagou = dialog.findViewById(R.id.bt_dialog_pagou);
                Button bt_dialog_devendo = dialog.findViewById(R.id.bt_dialog_devendo);
                Button bt_dialog_salvar = dialog.findViewById(R.id.bt_dialog_salvar);
                final EditText et_dialog_valor = dialog.findViewById(R.id.et_dialog_valor);
                TextView tv_dialog_nome = dialog.findViewById(R.id.tv_dialog_nome);
                ImageView iv_dialog_avatar = dialog.findViewById(R.id.iv_dialog_avatar);
                tv_dialog_nome.setText(passageiroSelecionado.getNome());
                iv_dialog_avatar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[passageiroSelecionado.getAvatar()]));
                bt_dialog_devendo.setText("-" + String.valueOf(valorPassagem));
                bt_dialog_pagou.setText(String.valueOf(valorPassagem));

                bt_dialog_devendo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String inputValor = et_dialog_valor.getText().toString();
                        if (inputValor.isEmpty())
                            inputValor = "00.00";
                        inputValor = inputValor.replace(",", ".");
                        Double valor = Double.parseDouble(inputValor);
                        et_dialog_valor.setText(String.valueOf(valor - valorPassagem));
                    }
                });

                bt_dialog_pagou.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String inputValor = et_dialog_valor.getText().toString();
                        if (inputValor.isEmpty())
                            inputValor = "00.00";
                        inputValor = inputValor.replace(",", ".");
                        Double valor = Double.parseDouble(inputValor);
                        et_dialog_valor.setText(String.valueOf(valor + valorPassagem));
                    }
                });

                bt_dialog_salvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String inputValor = et_dialog_valor.getText().toString();
                        if (inputValor.isEmpty() || inputValor.equalsIgnoreCase("0.0")) {
                            Toast.makeText(getContext(), "Informe o valor", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                banco.salvarSituacao(new Situacao(passageiroSelecionado.getId(),
                                        passageiroSelecionado.getNome(), Double.parseDouble(inputValor),
                                        "0", dataAtual(), passageiroSelecionado.getAvatar()));
                                Toast.makeText(getContext(), "Valor salvo com sucesso", Toast.LENGTH_SHORT).show();
                                sharedPreferencesAgendaPassageiroFragmentActivity = getActivity().getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                                SharedPreferences.Editor editor = sharedPreferencesAgendaPassageiroFragmentActivity.edit();
                                editor.putBoolean("atualizarMain", true);
                                editor.commit();
                                dialog.dismiss();
                            }catch (Exception e){
                                Toast.makeText(getContext(), "Falha na operação", Toast.LENGTH_SHORT).show();
                            }
                            getActivity().recreate();
                        }
                    }
                });

                dialog.show();

            }
        });

        registerForContextMenu(lista);
        return view;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_flutuante_passageiro, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Passageiro p = (Passageiro) lista.getItemAtPosition(info.position);
        try {
            switch (item.getItemId()) {
                case R.id.menu_flutuante_passageiro_excluir:
                    try {
                        if (banco.deletarPassageiro(p.getId()) == 1) {
                            Toast.makeText(getContext(), "Passageiro excluido com sucesso", Toast.LENGTH_SHORT).show();
                            sharedPreferencesAgendaPassageiroFragmentActivity = getActivity().getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                            SharedPreferences.Editor editor = sharedPreferencesAgendaPassageiroFragmentActivity.edit();
                            editor.putBoolean("atualizarMain", true);
                            editor.commit();
                            getActivity().recreate();
                        } else {
                            Toast.makeText(getContext(), "Falha na exclusão", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Falha ao excluir", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                case R.id.menu_flutuante_passageiro_editar:

                    Intent intent = new Intent(getContext(), EditarPassageiroActivity.class);
                    intent.putExtra("id", p.getId());
                    intent.putExtra("nome", p.getNome());
                    intent.putExtra("avatar", p.getAvatar());

                    startActivity(intent);

            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Falha na operacao", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public String dataAtual() {
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
        String currentDateTimeString = sdf.format(date);
        return currentDateTimeString;

    }

}
