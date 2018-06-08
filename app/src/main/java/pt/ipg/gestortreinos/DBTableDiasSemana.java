package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.security.PublicKey;

public class DBTableDiasSemana implements BaseColumns {
    public static final String NOME_DIA = "nome_dia";
    public static final String GRUPO_MUSCULAR = "grupos_muscular";
    public static final String DATABASENAME_D = "diasSemana";
    private SQLiteDatabase db;

    public DBTableDiasSemana(SQLiteDatabase db){
        this.db = db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE " + DATABASENAME_D + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NOME_DIA + " TEXT NOT NULL," +
                        GRUPO_MUSCULAR + " TEXT NOT NULL" +
                        ")"
                );

    }

    public static ContentValues getContentValues(DiasSemana DiasSemana){
        ContentValues values = new ContentValues();

        values.put(_ID,DiasSemana.getDia());
        values.put(NOME_DIA,DiasSemana.getNome_dia());
        values.put(GRUPO_MUSCULAR,DiasSemana.getGrupo_Muscular());

        return values;
    }


    public long insert(ContentValues values){
        return db.insert(DATABASENAME_D,null,values);
    }

    public int update(ContentValues values,String whereClause,String[] whereArgs){
        return db.update(DATABASENAME_D,values, whereClause, whereArgs);
    }

    public int delete(String table,String whereClause,String[] whereArgs){
        return db.delete(DATABASENAME_D,whereClause,whereArgs);
    }

    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(DATABASENAME_D, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}
