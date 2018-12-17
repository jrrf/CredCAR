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
import com.example.jks_j.credcar.model.Situacao;

import java.util.ArrayList;

public class SituacaoFragmentAdapter extends ArrayAdapter<Situacao> {

    private final Context context;
    private final ArrayList<Situacao> listaSituacao;
    int[] imagens = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4,
            R.drawable.avatar5, R.drawable.avatar6, R.drawable.avatar7, R.drawable.avatar8,
            R.drawable.avatar9, R.drawable.avatar10, R.drawable.avatar11, R.drawable.avatar12,
            R.drawable.avatar13, R.drawable.avatar14, R.drawable.avatar15, R.drawable.avatar16};

    public SituacaoFragmentAdapter(Context context, ArrayList<Situacao> listaSituacao) {
        super(context, R.layout.linha_situacao,listaSituacao);
        this.context = context;
        this.listaSituacao = listaSituacao;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View linha = inflater.inflate(R.layout.linha_situacao,parent,false);

        TextView nome = linha.findViewById(R.id.tv_linha_situacao_nome);
        TextView valor = linha.findViewById(R.id.tv_linha_situacao_valor);
        TextView data = linha.findViewById(R.id.tv_linha_situacao_data);
        ImageView avatar = linha.findViewById(R.id.iv_linha_situacao_avatar);

        if(listaSituacao.get(position).getValor()<0) {
            valor.setTextColor(Color.parseColor("#FF0000"));
            valor.setText("R$ "+String.valueOf(listaSituacao.get(position).getValor()*(-1)));

        }
        else if(listaSituacao.get(position).getValor()>0){
            valor.setTextColor(Color.parseColor("#00AA00"));
            valor.setText("R$ "+String.valueOf(listaSituacao.get(position).getValor()));

        }else{
            valor.setTextColor(Color.parseColor("#000000"));
            valor.setText("R$ "+String.valueOf(listaSituacao.get(position).getValor()));
        }
        nome.setText(listaSituacao.get(position).getNome_passageiro());
        data.setText(inverterData(listaSituacao.get(position).getData()));

        avatar.setImageDrawable(ContextCompat.getDrawable(context, imagens[listaSituacao.get(position).getAvatar()]));

        return linha;
    }

    public String inverterData(String data){
        String ano="";
        String mes="";
        String dia="";

        if('/'==data.charAt(4)){
            for(int i=0;i<4;i++)
                ano+=data.charAt(i);
            for(int i=5;i<7;i++)
                mes+=data.charAt(i);
            for(int i=8;i<10;i++)
                dia+=data.charAt(i);
            return ""+dia+"/"+mes+"/"+ano;
        }
        for(int i=0;i<2;i++)
            dia+=data.charAt(i);
        for(int i=3;i<5;i++)
            mes+=data.charAt(i);
        for(int i=6;i<10;i++)
            ano+=data.charAt(i);
        return ""+ano+"/"+mes+"/"+dia;
    }
}
