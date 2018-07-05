package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DBTableDiasSemana implements BaseColumns {

    public static final String NOME_MES = "nome_mes";
    public static final String DATABASENAME_D = "diasSemana";
    public static final String[] ALL_COLUMNS = new String[]{_ID, NOME_MES};

    private SQLiteDatabase db;

    public DBTableDiasSemana(SQLiteDatabase db){
        this.db = db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE " + DATABASENAME_D + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        NOME_MES + " TEXT NOT NULL);");

    }


    public void dropTable(){
    db.execSQL(
                "DROP TABLE IF EXISTS "+DBTableDiasSemana.DATABASENAME_D
        );
    }
    public static ContentValues getContentValues(DiasSemana DiasSemana){
        ContentValues values = new ContentValues();

        values.put(_ID,DiasSemana.getIdDia());
        values.put(NOME_MES, DiasSemana.getNomeMes());

        return values;
    }


    public static DiasSemana getCurrentDiaFromCursor(Cursor cursor) {
        final int posId = cursor.getColumnIndex(_ID);
        final int posNomeMes = cursor.getColumnIndex(NOME_MES);


        DiasSemana diasSemana = new DiasSemana();

        diasSemana.setIdDia(cursor.getInt(posId));
        diasSemana.setNomeMes(cursor.getString(posNomeMes));


        return diasSemana;
    }


    public long insert(ContentValues values){
        return db.insert(DATABASENAME_D,null,values);
    }

    public int update(ContentValues values,String whereClause,String[] whereArgs){
        return db.update(DATABASENAME_D,values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(DATABASENAME_D,whereClause,whereArgs);
    }


    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(DATABASENAME_D, columns, selection, selectionArgs, groupBy, having, orderBy);
    }


}
