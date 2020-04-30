package br.com.upf.agendatelefonica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AgendaController {
    private SQLiteDatabase db;
    private AgendaDbHelper banco;

    public AgendaController(Context context){
        banco = new AgendaDbHelper(context);
    }

    public String insereDados(String nome, String telefone){
        db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AgendaDbHelper.COLUMN_NOME, nome);
        values.put(AgendaDbHelper.COLUMN_TELEFONE, telefone);
        long newRowId = db.insert(AgendaDbHelper.TABLE_NAME, null, values);
        db.close();
        if(newRowId == -1){
            return "Erro ao inserir contato";
        }else{
            return "Contato adicionado com sucesso!";
        }
    }

    public Cursor carregaDados() {
        Cursor cursor;
        String[] campos = {AgendaDbHelper.ID,AgendaDbHelper.COLUMN_NOME, AgendaDbHelper.COLUMN_TELEFONE};
        db = banco.getReadableDatabase();
        cursor = db.query(AgendaDbHelper.TABLE_NAME, campos, null,null, null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return  cursor;
    }

    public String alteraRegistro(int id, String nome, String telefone) {
        ContentValues valores;
        String where;
        db = banco.getWritableDatabase();
        where = AgendaDbHelper.ID + "=" + id;
        valores = new ContentValues();
        valores.put(AgendaDbHelper.COLUMN_NOME, nome);
        valores.put(AgendaDbHelper.COLUMN_TELEFONE, telefone);
        int result =
                db.update(AgendaDbHelper.TABLE_NAME,valores,where,null);
        db.close();
        if (result > 0)
            return "Usuário alterado";
        else
            return "Nada foi alterado";
    }

    public void deletaRegistro (int id) {
        String where = AgendaDbHelper.ID +"="+ id;
        db = banco.getReadableDatabase();
        db.delete(AgendaDbHelper.TABLE_NAME,where,null);
        db.close();
    }
}
