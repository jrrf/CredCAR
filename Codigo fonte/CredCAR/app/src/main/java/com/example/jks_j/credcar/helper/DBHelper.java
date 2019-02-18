package com.example.jks_j.credcar.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jks_j.credcar.model.Arquivado;
import com.example.jks_j.credcar.model.Historico;
import com.example.jks_j.credcar.model.Passageiro;
import com.example.jks_j.credcar.model.Situacao;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "credcar.db";
    private static final int VERSAO_BANCO = 2;
    private Context context;
    private SQLiteDatabase dbInstancia = null;

    public DBHelper(Context context){
        super(context, NOME_BANCO, null, VERSAO_BANCO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE passageiros ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL," +
                "avatar INTEGER)");

        db.execSQL("CREATE TABLE situacao ( id_passageiro INTEGER PRIMARY KEY, " +
                "nome_passageiro TEXT NOT NULL," +
                "valor REAL NOT NULL," +
                "arquivado TEXT NOT NULL," +
                "data TEXT NOT NULL)");

        db.execSQL("CREATE TABLE historico ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_passageiro INTEGER NOT NULL, " +
                "nome_passageiro TEXT NOT NULL," +
                "valor REAL NOT NULL," +
                "data TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS passageiros");
        db.execSQL("DROP TABLE IF EXISTS situacao");
        db.execSQL("DROP TABLE IF EXISTS arquivado");
        db.execSQL("DROP TABLE IF EXISTS historico");
        onCreate(db);
    }

    public long salvarPassageiro(Passageiro passageiro) throws SQLException {
        abrirBD();
        long resultado = dbInstancia.insert("passageiros", null, passageiro.getContentValues());
        fecharBD();
        return resultado;
    }

    public ArrayList<Passageiro> getDBPassageiro() throws SQLException {
        ArrayList<Passageiro> meusPassageiro = new ArrayList<>();
        SQLiteDatabase banco = getReadableDatabase();
        Cursor minhaConsulta = banco.rawQuery("SELECT id, nome, avatar FROM passageiros ORDER BY nome", null);
        minhaConsulta.moveToFirst();
        while (!minhaConsulta.isAfterLast())
        {
            meusPassageiro.add(new Passageiro(minhaConsulta.getInt(0), minhaConsulta.getString(1), minhaConsulta.getInt(2)));
            minhaConsulta.moveToNext();
        }
        banco.close();
        minhaConsulta.close();
        return meusPassageiro;
    }

    public int editarPassageiro(Passageiro passageiro){
        int resultado;
        abrirBD();
        resultado = dbInstancia.update("passageiros",passageiro.getContentValuesEditar(),"id = "+passageiro.getId(),null);
        fecharBD();
        return resultado;
    }

    public int deletarPassageiro(int id) throws SQLException {
        int resultado;
        abrirBD();
        resultado = dbInstancia.delete("passageiros", "id = "+id, null);
        dbInstancia.delete("situacao", "id_passageiro = " + id, null);
        dbInstancia.delete("historico", "id_passageiro = " + id, null);
        fecharBD();

        return resultado;
    }

    public long salvarSituacao(Situacao situacao) throws SQLException {
        abrirBD();
        Situacao s;
        long resultado;

        Cursor minhaConsulta = dbInstancia.rawQuery("SELECT id_passageiro, nome_passageiro, valor, arquivado, data FROM situacao WHERE id_passageiro = "+situacao.getId_passageiro(), null);
        minhaConsulta.moveToFirst();

        if(minhaConsulta.getCount() > 0){
            s = new Situacao(minhaConsulta.getInt(0),minhaConsulta.getString(1), minhaConsulta.getDouble(2), minhaConsulta.getString(3), minhaConsulta.getString(4), 1);
            s.setValor(s.getValor() + situacao.getValor());
            s.setData(situacao.getData());
            s.setArquivado(situacao.getArquivado());
            resultado = dbInstancia.update("situacao",s.getContentValues(),"id_passageiro = "+s.getId_passageiro(),null);
            if(resultado == -1)
                return resultado;
            resultado = dbInstancia.insert("historico", null, situacao.getContentValuesHistorico());
        } else {
            resultado = dbInstancia.insert("situacao", null, situacao.getContentValues());
            if(resultado == -1)
                return resultado;
            resultado = dbInstancia.insert("historico", null, situacao.getContentValuesHistorico());
        }

        fecharBD();
        return resultado;
    }

    public ArrayList<Situacao> getDBSituacao() throws SQLException{
        ArrayList<Situacao> minhasSituacao = new ArrayList<>();
        SQLiteDatabase banco = getReadableDatabase();
        Cursor minhaConsulta = banco.rawQuery("SELECT situacao.id_passageiro, passageiros.nome, situacao.valor, situacao.arquivado, situacao.data, passageiros.avatar FROM situacao, passageiros WHERE situacao.arquivado = 0 AND passageiros.id = situacao.id_passageiro ORDER BY data DESC", null);
        minhaConsulta.moveToFirst();
        while (!minhaConsulta.isAfterLast())
        {

            minhasSituacao.add(new Situacao(minhaConsulta.getInt(0),minhaConsulta.getString(1), minhaConsulta.getDouble(2), minhaConsulta.getString(3), minhaConsulta.getString(4), minhaConsulta.getInt(5)));
            minhaConsulta.moveToNext();
        }
        banco.close();
        minhaConsulta.close();
        return minhasSituacao;
    }

    public int deletarSituacao(int id) throws SQLException {
        abrirBD();
        int resultado = dbInstancia.delete("situacao", "id_passageiro = "+id, null);
        fecharBD();
        return resultado;
    }

    public ArrayList<Arquivado> getDBArquivado() throws SQLException {
        ArrayList<Arquivado> minhasArquivado = new ArrayList<>();
        SQLiteDatabase banco = getReadableDatabase();
        Cursor minhaConsulta = banco.rawQuery("SELECT situacao.id_passageiro, passageiros.nome, situacao.valor, situacao.arquivado, situacao.data, passageiros.avatar FROM situacao, passageiros WHERE situacao.arquivado = 1 AND passageiros.id = situacao.id_passageiro ORDER BY data DESC", null);
        minhaConsulta.moveToFirst();
        while (!minhaConsulta.isAfterLast())
        {
            minhasArquivado.add(new Arquivado(minhaConsulta.getInt(0),minhaConsulta.getString(1), minhaConsulta.getDouble(2), minhaConsulta.getString(3), minhaConsulta.getString(4), minhaConsulta.getInt(5)));
            minhaConsulta.moveToNext();
        }
        banco.close();
        minhaConsulta.close();
        return minhasArquivado;
    }

    public int arquivar(Situacao situacao) throws SQLException {
        abrirBD();
        int resultado = dbInstancia.update("situacao",situacao.getContentValues(),"id_passageiro = "+situacao.getId_passageiro(),null);
        fecharBD();
        return resultado;

    }

    public int desarquivar(Arquivado arquivado) throws SQLException {
        abrirBD();
        int resultado = dbInstancia.update("situacao",arquivado.getContentValues(),"id_passageiro = "+arquivado.getId_passageiro(),null);
        fecharBD();
        return resultado;

    }

    public ArrayList<Historico> getDBHistorico() throws SQLException{
        ArrayList<Historico> minhasHistorico = new ArrayList<>();
        SQLiteDatabase banco = getReadableDatabase();
        Cursor minhaConsulta = banco.rawQuery("SELECT historico.id, historico.id_passageiro, passageiros.nome, historico.valor, historico.data, passageiros.avatar FROM passageiros, historico WHERE historico.id_passageiro = passageiros.id ORDER BY data DESC", null);
        minhaConsulta.moveToFirst();
        while (!minhaConsulta.isAfterLast())
        {
            minhasHistorico.add(new Historico(minhaConsulta.getInt(0),minhaConsulta.getInt(1), minhaConsulta.getString(2), minhaConsulta.getDouble(3), minhaConsulta.getString(4), minhaConsulta.getInt(5)));
            minhaConsulta.moveToNext();
        }
        banco.close();
        minhaConsulta.close();
        return minhasHistorico;
    }

    public int limparHistorico(){
        abrirBD();
        int resultado = dbInstancia.delete("historico", "", null);
        fecharBD();
        return resultado;
    }

    public void abrirBD() throws SQLException {
        if(dbInstancia == null) {
            dbInstancia = this.getWritableDatabase();
        }
    }

    public void fecharBD() throws SQLException {
        if(dbInstancia != null) {
            if (dbInstancia.isOpen()) {
                dbInstancia.close();
            }
        }
    }

    public ArrayList<Situacao> getDBTotal() throws SQLException{
        ArrayList<Situacao> minhasSituacao = new ArrayList<>();
        SQLiteDatabase banco = getReadableDatabase();
        Cursor minhaConsulta = banco.rawQuery("SELECT id_passageiro, nome_passageiro, valor, arquivado, data  FROM situacao ORDER BY data DESC", null);
        minhaConsulta.moveToFirst();
        while (!minhaConsulta.isAfterLast())
        {
            minhasSituacao.add(new Situacao(minhaConsulta.getInt(0),minhaConsulta.getString(1), minhaConsulta.getDouble(2), minhaConsulta.getString(3), minhaConsulta.getString(4),1));
            minhaConsulta.moveToNext();
        }
        banco.close();
        minhaConsulta.close();
        return minhasSituacao;
    }

    public double totalDevendo () throws SQLException{
        double total=0;
        ArrayList<Situacao> listaCompleta = getDBTotal();
        for (Situacao s : listaCompleta){
            if(s.getValor() < 0)
                total += s.getValor();
        }
        if(total==0.0)
            return total;
        return total*(-1);
    }

    public double totalDiferenca () throws SQLException{
        double total=0;
        ArrayList<Situacao> listaCompleta = getDBTotal();
        for (Situacao s : listaCompleta){
            total += s.getValor();
        }
        return total;
    }

}
