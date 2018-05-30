package pt.ipg.gestortreinos;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DBTableDiasSemana implements BaseColumns {
    private SQLiteDatabase db;

    public DBTableDiasSemana(SQLiteDatabase db){
        this.db = db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE diasSemana (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "dias INTEGER NOT NULL," +
                        "grupos_muscular TEXT NOT NULL" +
                        ")"
                );

    }


}
