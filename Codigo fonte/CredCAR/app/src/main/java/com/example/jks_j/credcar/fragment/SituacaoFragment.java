package com.example.jks_j.credcar.fragment;


import android.app.Dialog;
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
import com.example.jks_j.credcar.adapter.SituacaoFragmentAdapter;
import com.example.jks_j.credcar.helper.DBHelper;
import com.example.jks_j.credcar.model.Situacao;

import java.text.SimpleDateFormat;

public class SituacaoFragment extends Fragment {

    private ListView listaSituacao;
    private SituacaoFragmentAdapter adapter;
    private DBHelper banco;
    private SharedPreferences sharedPreferencesSituacaoFragmentActivity;
    private static final String ARQUIVO_PREFERENCIA = "ArqPreferencia";
    int[] imagens = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4,
            R.drawable.avatar5, R.drawable.avatar6, R.drawable.avatar7, R.drawable.avatar8,
            R.drawable.avatar9, R.drawable.avatar10, R.drawable.avatar11, R.drawable.avatar12,
            R.drawable.avatar13, R.drawable.avatar14, R.drawable.avatar15, R.drawable.avatar16};

    public SituacaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_situacao, container, false);

        listaSituacao = view.findViewById(R.id.lv_agenda_situacao_fragment);
        banco = new DBHelper(getContext());
        adapter = new SituacaoFragmentAdapter(getContext(),banco.getDBSituacao());

        listaSituacao.setAdapter(adapter);

        listaSituacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Situacao situacaoSelecionado = (Situacao) adapterView.getItemAtPosition(i);
                sharedPreferencesSituacaoFragmentActivity = getActivity().getSharedPreferences(ARQUIVO_PREFERENCIA, 0);

                final Double valorPassagem = Double.parseDouble(sharedPreferencesSituacaoFragmentActivity.getString("valorPassagem", "05.00"));
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_dialog);
                Button bt_dialog_pagou = dialog.findViewById(R.id.bt_dialog_pagou);
                Button bt_dialog_devendo = dialog.findViewById(R.id.bt_dialog_devendo);
                Button bt_dialog_salvar = dialog.findViewById(R.id.bt_dialog_salvar);
                final EditText et_dialog_valor = dialog.findViewById(R.id.et_dialog_valor);
                TextView tv_dialog_nome = dialog.findViewById(R.id.tv_dialog_nome);
                ImageView iv_dialog_avatar = dialog.findViewById(R.id.iv_dialog_avatar);
                tv_dialog_nome.setText(situacaoSelecionado.getNome_passageiro());
                iv_dialog_avatar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[situacaoSelecionado.getAvatar()]));
                bt_dialog_devendo.setText("-"+String.valueOf(valorPassagem));
                bt_dialog_pagou.setText(String.valueOf(valorPassagem));

                bt_dialog_devendo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String inputValor = et_dialog_valor.getText().toString();
                        if(inputValor.isEmpty())
                            inputValor = "00.00";
                        inputValor = inputValor.replace(",",".");
                        Double valor = Double.parseDouble(inputValor);
                        et_dialog_valor.setText(String.valueOf(valor-valorPassagem));
                    }
                });

                bt_dialog_pagou.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String inputValor = et_dialog_valor.getText().toString();
                        if(inputValor.isEmpty())
                            inputValor = "00.00";
                        inputValor = inputValor.replace(",",".");
                        Double valor = Double.parseDouble(inputValor);
                        et_dialog_valor.setText(String.valueOf(valor+valorPassagem));
                    }
                });

                bt_dialog_salvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String inputValor = et_dialog_valor.getText().toString();
                        if (inputValor.isEmpty() || inputValor.equalsIgnoreCase("0.0")){
                            Toast.makeText(getContext(), "Informe o valor", Toast.LENGTH_SHORT).show();
                        }else{
                            situacaoSelecionado.setValor(Double.parseDouble(inputValor));
                            situacaoSelecionado.setData(dataAtual());
                            try {
                                banco.salvarSituacao(situacaoSelecionado);
                                Toast.makeText(getContext(), "Valor salvo com sucesso", Toast.LENGTH_SHORT).show();
                                getActivity().recreate();
                            }catch (Exception e){
                                Toast.makeText(getContext(), "Erro ao salvar", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });

        registerForContextMenu(listaSituacao);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_flutuante_situacao, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Situacao s = (Situacao) listaSituacao.getItemAtPosition(info.position);
        try {
            switch (item.getItemId()) {
                case R.id.menu_flutuante_situacao_arquivar:
                    s.setArquivado("1");
                    if(banco.arquivar(s)==1) {
                        Toast.makeText(getContext(), "Situação arquivada", Toast.LENGTH_SHORT).show();
                        getActivity().recreate();
                    }
                    return true;
                case R.id.menu_flutuante_situacao_excluir:
                    if(banco.deletarSituacao(s.getId_passageiro())==1) {
                        Toast.makeText(getContext(), "Situação excluida", Toast.LENGTH_SHORT).show();
                        getActivity().recreate();
                    }
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Falha na operacao", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public String dataAtual(){
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
        String currentDateTimeString = sdf.format(date);
        return currentDateTimeString;

    }
}
