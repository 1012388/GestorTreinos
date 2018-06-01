package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.security.PublicKey;

public class DBTableDiasSemana implements BaseColumns {
    public static final String ID_DIA = "id";
    public static final String NOME_DIA = "nome_dia";
    public static final String GRUPO_MUSCULAR = "grupos_muscular";
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
                        GRUPO_MUSCULAR + " TEXT NOT NULL" +
                        ")"
                );

    }

    public static ContentValues getContentValues(DiasSemana DiasSemana){
        ContentValues values = new ContentValues();

        values.put(ID_DIA,DiasSemana.getDia());
        values.put(NOME_DIA,DiasSemana.getNome_dia());
        values.put(GRUPO_MUSCULAR,DiasSemana.getGrupo_Muscular());

        return values;
    }


    public long insert(ContentValues values){
        return db.insert(DATABASENAME,null,values);
    }

   /* public int update(ContentValues values){
        return db.update();
    }

    public long delete(){
        //return db.delete();
    }*/
}
