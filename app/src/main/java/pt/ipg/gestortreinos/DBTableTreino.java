package pt.ipg.gestortreinos;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DBTableTreino implements BaseColumns {

    private SQLiteDatabase db;

    public DBTableTreino(SQLiteDatabase db) {

    }

    public void create() {
        db.execSQL(
                "CREATE TABLE treinos (" +
                        "id_treino INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "repeticoes INTEGER NOT NULL," +
                        "exercicio TEXT NOT NULL," +
                        "peso INTEGER NOT NULL," +
                        "id_dia INTEGER ," +
                        "FOREIGN KEY (id_dia) REFERENCES " +
                        "diasSemana" +
                        "(" +
                        "id" +
                        ")" +
                        ")"
        );
    }
}

