package pt.ipg.gestortreinos;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DBTableDiasSemana implements BaseColumns {
    public static final String ID_DIA = "id";
    public static final String NOME_DIA = "nome_dia";
    public static final String GRUPOS_MUSCULAR = "grupos_muscular";
    public static final String DATABASENAME = "diasSemana";
    private SQLiteDatabase db;

    public DBTableDiasSemana(SQLiteDatabase db){
        this.db = db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE " + DATABASENAME + " (" +
                        ID_DIA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NOME_DIA + " TEXT NOT NULL," +
                        GRUPOS_MUSCULAR + " TEXT NOT NULL" +
                        ")"
                );
    }


}
