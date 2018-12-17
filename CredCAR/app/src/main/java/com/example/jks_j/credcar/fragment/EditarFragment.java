package com.example.jks_j.credcar.fragment;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jks_j.credcar.R;
import com.example.jks_j.credcar.helper.DBHelper;
import com.example.jks_j.credcar.model.Passageiro;


public class EditarFragment extends Fragment {

    private SharedPreferences sharedPreferencesEditarPassageiroActivity;
    private static final String ARQUIVO_PREFERENCIA = "ArqPreferencia";
    private EditText et_editarPassageiro_nome;
    private Button bt_editarPassageiro_salvar;
    private ImageView iv_avatar_editar;
    private String inputNome;
    private DBHelper banco;
    private Bundle passados;
    private int avatarEscolhido;
    int[] imagens = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4,
            R.drawable.avatar5, R.drawable.avatar6, R.drawable.avatar7, R.drawable.avatar8,
            R.drawable.avatar9, R.drawable.avatar10, R.drawable.avatar11, R.drawable.avatar12,
            R.drawable.avatar13, R.drawable.avatar14, R.drawable.avatar15, R.drawable.avatar16};

    public EditarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar, container, false);

        banco = new DBHelper(getContext());
        iv_avatar_editar = view.findViewById(R.id.iv_avatar_editar_fragment);
        et_editarPassageiro_nome = view.findViewById(R.id.et_editarPassageiro_fragment_nome);
        bt_editarPassageiro_salvar = view.findViewById(R.id.bt_editarPassageiro_fragment_salvar);

        passados = getActivity().getIntent().getExtras();
        avatarEscolhido = passados.getInt("avatar");
        et_editarPassageiro_nome.setText(passados.getString("nome"));

        iv_avatar_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escolherAvatar();
            }
        });

        bt_editarPassageiro_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputNome = et_editarPassageiro_nome.getText().toString();

                if(inputNome.isEmpty()){
                    Toast.makeText( getContext(),"Preencha o nome do passageiro", Toast.LENGTH_LONG).show();

                }else {
                    try {
                        Passageiro p = new Passageiro(passados.getInt("id"),inputNome, avatarEscolhido);
                        if(banco.editarPassageiro(p) == -1){
                            Toast.makeText( getContext(),"Erro ao editar contato! "+p.getId(), Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getContext(), "Contato salvo com sucesso!", Toast.LENGTH_LONG).show();
                            sharedPreferencesEditarPassageiroActivity = getActivity().getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                            SharedPreferences.Editor editor = sharedPreferencesEditarPassageiroActivity.edit();
                            editor.putBoolean("atualizarMain", true);
                            editor.putBoolean("atualizarPassageiros", true);
                            editor.commit();
                            getActivity().finish();
                        }
                    } catch (Exception e){
                        Toast.makeText( getContext(),"Falha ao editar! Id: "+passados.getInt("id")+" "+e, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[avatarEscolhido]));

        return view;
    }

    public void escolherAvatar(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_avatar);
        ImageView iv_avatar_1 = dialog.findViewById(R.id.iv_avatar_1);
        ImageView iv_avatar_2 = dialog.findViewById(R.id.iv_avatar_2);
        ImageView iv_avatar_3 = dialog.findViewById(R.id.iv_avatar_3);
        ImageView iv_avatar_4 = dialog.findViewById(R.id.iv_avatar_4);
        ImageView iv_avatar_5 = dialog.findViewById(R.id.iv_avatar_5);
        ImageView iv_avatar_6 = dialog.findViewById(R.id.iv_avatar_6);
        ImageView iv_avatar_7 = dialog.findViewById(R.id.iv_avatar_7);
        ImageView iv_avatar_8 = dialog.findViewById(R.id.iv_avatar_8);
        ImageView iv_avatar_9 = dialog.findViewById(R.id.iv_avatar_9);
        ImageView iv_avatar_10 = dialog.findViewById(R.id.iv_avatar_10);
        ImageView iv_avatar_11 = dialog.findViewById(R.id.iv_avatar_11);
        ImageView iv_avatar_12 = dialog.findViewById(R.id.iv_avatar_12);
        ImageView iv_avatar_13 = dialog.findViewById(R.id.iv_avatar_13);
        ImageView iv_avatar_14 = dialog.findViewById(R.id.iv_avatar_14);
        ImageView iv_avatar_15 = dialog.findViewById(R.id.iv_avatar_15);
        ImageView iv_avatar_16 = dialog.findViewById(R.id.iv_avatar_16);

        iv_avatar_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[0]));
                avatarEscolhido=0;
                dialog.dismiss();
            }
        });

        iv_avatar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[1]));
                avatarEscolhido=1;
                dialog.dismiss();
            }
        });

        iv_avatar_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[2]));
                avatarEscolhido=2;
                dialog.dismiss();
            }
        });

        iv_avatar_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[3]));
                avatarEscolhido=3;
                dialog.dismiss();
            }
        });

        iv_avatar_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[4]));
                avatarEscolhido=4;
                dialog.dismiss();
            }
        });

        iv_avatar_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[5]));
                avatarEscolhido=5;
                dialog.dismiss();
            }
        });

        iv_avatar_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[6]));
                avatarEscolhido=6;
                dialog.dismiss();
            }
        });

        iv_avatar_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[7]));
                avatarEscolhido=7;
                dialog.dismiss();
            }
        });

        iv_avatar_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[8]));
                avatarEscolhido=8;
                dialog.dismiss();
            }
        });

        iv_avatar_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[9]));
                avatarEscolhido=9;
                dialog.dismiss();
            }
        });

        iv_avatar_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[10]));
                avatarEscolhido=10;
                dialog.dismiss();
            }
        });

        iv_avatar_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[11]));
                avatarEscolhido=11;
                dialog.dismiss();
            }
        });

        iv_avatar_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[12]));
                avatarEscolhido=12;
                dialog.dismiss();
            }
        });

        iv_avatar_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[13]));
                avatarEscolhido=13;
                dialog.dismiss();
            }
        });

        iv_avatar_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[14]));
                avatarEscolhido=14;
                dialog.dismiss();
            }
        });

        iv_avatar_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_avatar_editar.setImageDrawable(ContextCompat.getDrawable(getContext(), imagens[15]));
                avatarEscolhido=15;
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
