package pt.ipg.gestortreinos;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DBTableTreino implements BaseColumns {

    private SQLiteDatabase db;

    public DBTableTreino(SQLiteDatabase db){

    }

    public void create(){
        db.execSQL(
                "CREATE TABLE treinos (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "REPETICOES INTEGER NOT NULL," +
                        "EXERCICIO TEXT NOT NULL," +
                        "PESO INTEGER NOT NULL"+
                        ")"
        );
    }


    //Tabel id:Número do treino

}

