package com.example.jks_j.credcar.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jks_j.credcar.R;
import com.example.jks_j.credcar.adapter.HistoricoFragmentAdapter;
import com.example.jks_j.credcar.helper.DBHelper;
import com.example.jks_j.credcar.model.Historico;

public class HistoricoFragment extends Fragment {

    private ListView listaHistorico;
    private DBHelper banco;
    private HistoricoFragmentAdapter adapter;

    public HistoricoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        listaHistorico = view.findViewById(R.id.lv_agenda_historico_fragment);
        banco = new DBHelper(getContext());
        adapter = new HistoricoFragmentAdapter(getContext(),banco.getDBHistorico());

        listaHistorico.setAdapter(adapter);

        registerForContextMenu(listaHistorico);
        return view;
    }
}
