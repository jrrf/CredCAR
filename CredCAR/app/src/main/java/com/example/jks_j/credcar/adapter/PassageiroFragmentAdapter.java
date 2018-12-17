package com.example.jks_j.credcar.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jks_j.credcar.R;
import com.example.jks_j.credcar.model.Passageiro;

import java.util.ArrayList;

public class PassageiroFragmentAdapter extends ArrayAdapter<Passageiro> {

    private final Context context;
    private final ArrayList<Passageiro> listaPassageiro;
    int[] imagens = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4,
            R.drawable.avatar5, R.drawable.avatar6, R.drawable.avatar7, R.drawable.avatar8,
            R.drawable.avatar9, R.drawable.avatar10, R.drawable.avatar11, R.drawable.avatar12,
            R.drawable.avatar13, R.drawable.avatar14, R.drawable.avatar15, R.drawable.avatar16};

    public PassageiroFragmentAdapter(Context context, ArrayList<Passageiro> listaPassageiro) {
        super(context, R.layout.linha_passageiro,listaPassageiro);
        this.context = context;
        this.listaPassageiro = listaPassageiro;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View linha = inflater.inflate(R.layout.linha_passageiro,parent,false);

        TextView nome = linha.findViewById(R.id.tv_linha_passageiro_nome);
        ImageView avatar = linha.findViewById(R.id.iv_linha_passageiro_avatar);

        nome.setText(listaPassageiro.get(position).getNome());
        avatar.setImageDrawable(ContextCompat.getDrawable(context, imagens[listaPassageiro.get(position).getAvatar()]));


        return linha;
    }

}
