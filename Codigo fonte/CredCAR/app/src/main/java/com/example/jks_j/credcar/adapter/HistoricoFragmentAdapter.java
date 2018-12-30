package com.example.jks_j.credcar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jks_j.credcar.R;
import com.example.jks_j.credcar.model.Historico;

import java.util.ArrayList;

public class HistoricoFragmentAdapter extends ArrayAdapter<Historico> {
    private final Context context;
    private final ArrayList<Historico> listaHistorico;
    int[] imagens = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4,
            R.drawable.avatar5, R.drawable.avatar6, R.drawable.avatar7, R.drawable.avatar8,
            R.drawable.avatar9, R.drawable.avatar10, R.drawable.avatar11, R.drawable.avatar12,
            R.drawable.avatar13, R.drawable.avatar14, R.drawable.avatar15, R.drawable.avatar16};

    public HistoricoFragmentAdapter(Context context, ArrayList<Historico> listaHistorico) {
        super(context, R.layout.linha_historico, listaHistorico);
        this.context = context;
        this.listaHistorico = listaHistorico;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View linha_historico = inflater.inflate(R.layout.linha_historico, parent, false);

        TextView nome = linha_historico.findViewById(R.id.tv_linha_historico_nome);
        TextView valor = linha_historico.findViewById(R.id.tv_linha_historico_valor);
        TextView data = linha_historico.findViewById(R.id.tv_linha_historico_data);
        TextView hora = linha_historico.findViewById(R.id.tv_linha_historico_hora);
        ImageView avatar = linha_historico.findViewById(R.id.iv_linha_historico_avatar);

        if (listaHistorico.get(position).getValor() < 0) {
            valor.setTextColor(Color.parseColor("#FF0000"));
            valor.setText("R$ " + String.valueOf(listaHistorico.get(position).getValor() * (-1)));

        } else {
            valor.setTextColor(Color.parseColor("#00AA00"));
            valor.setText("R$ " + String.valueOf(listaHistorico.get(position).getValor()));

        }
        nome.setText(listaHistorico.get(position).getNome_passageiro());
        data.setText(inverterData(listaHistorico.get(position).getData()));
        hora.setText(formatarHora(listaHistorico.get(position).getData()));

        avatar.setImageDrawable(ContextCompat.getDrawable(context, imagens[listaHistorico.get(position).getAvatar()]));

        return linha_historico;
    }

    public String inverterData(String data) {
        String ano = "";
        String mes = "";
        String dia = "";

        for (int i = 0; i < 4; i++) {
            ano += data.charAt(i);
            if (i < 2) {
                mes += data.charAt(i + 5);
                dia += data.charAt(i + 8);
            }
        }
        return "" + dia + "/" + mes + "/" + ano;

    }

    public String formatarHora(String data) {
        String hora = "";
        for (int i = 11; i < 19; i++) {
            hora += data.charAt(i);
        }

        return hora;
    }
}
