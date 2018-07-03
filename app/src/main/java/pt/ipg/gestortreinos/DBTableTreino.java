package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DBTableTreino implements BaseColumns {

    public static final String DATABASENAME_T = "treino";

    public static final String REPETICOES = "Repeticoes";
    public static final String EXERCICIO = "Exercicio";
    public static final String ID_DIA = "id_dia";
    public static final String PESO_USADO = "PesoUsado";
    public static final String SERIES = "Series";
    public static final String TOTAL_REPS = "Total_Reps";

    public static final String [] ALL_COLUMNS = new String[] { _ID,EXERCICIO,PESO_USADO,REPETICOES, SERIES, TOTAL_REPS};
    public static final String TREINO_ID = "Treino ID";

    private SQLiteDatabase db;

    public DBTableTreino(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + DATABASENAME_T + " (" +
                        _ID + " INTEGER PRIMARY KEY , " +
                        REPETICOES + " INTEGER NOT NULL," +
                        EXERCICIO + " TEXT NOT NULL," +
                        PESO_USADO + " INTEGER NOT NULL," +
                        SERIES + " INTEGER NOT NULL," +
                        TOTAL_REPS + " INTEGER NOT NULL," +
                        " FOREIGN KEY ( " + ID_DIA + " ) REFERENCES " +
                        DBTableDiasSemana.DATABASENAME_D+
                        " (" + DBTableDiasSemana._ID + ") " + " )"
        );
    }

     public static ContentValues getContentValues(Treinos treino){
         ContentValues values = new ContentValues();

         values.put(EXERCICIO,treino.getExercicio());
         values.put(REPETICOES,treino.getRepeticoes());
         values.put(PESO_USADO,treino.getPesoUsado());
         values.put(SERIES,treino.getSeries());
         values.put(TOTAL_REPS, treino.getTotal_Reps(treino.getRepeticoes(), treino.getSeries()));
         values.put(ID_DIA, treino.getIdDia());
        return values;
     }

    public static Treinos getCurrentTreinoFromCursor(Cursor cursor) {
        final int posId = cursor.getColumnIndex(_ID);
        final int posExercicio = cursor.getColumnIndex(EXERCICIO);
        final int posRepeticoes = cursor.getColumnIndex(REPETICOES);
        final int posSeries = cursor.getColumnIndex(SERIES);
        final int posId_Dia = cursor.getColumnIndex(ID_DIA);

        Treinos treino = new Treinos();

        treino.setTreinoId(cursor.getInt(posId));
        treino.setExercicio(cursor.getString(posExercicio));
        treino.setRepeticoes(cursor.getInt(posRepeticoes));
        treino.setSeries(cursor.getInt(posSeries));
        treino.getTotal_Reps(cursor.getInt(posRepeticoes), cursor.getInt(posSeries));
        treino.setIdDia(cursor.getInt(posId_Dia));

        return treino;
    }

    public long insert(ContentValues values){ return db.insert(DATABASENAME_T,null,values);

    }

    /**
     * Convenience method for updating rows in the categories table.
     *
     * @param values a map from column names to new column values. null is a
     *            valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *            Passing null will update all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected
     */

    public int update(ContentValues values,String whereClause,String[] whereArgs){
        return db.update(DATABASENAME_T,values, whereClause, whereArgs);
    }

    /**
     * Convenience method for deleting rows in the categories table.
     *
     * @param whereClause the optional WHERE clause to apply when deleting.
     *            Passing null will delete all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. To remove all rows and get a count pass "1" as the
     *         whereClause.
     */

    public int delete(String whereClause,String[] whereArgs){
        return db.delete(DATABASENAME_T,whereClause,whereArgs);
    }

    /**
     * Query the categories table, returning a {@link Cursor} over the result set.
     *
     * @param columns A list of which columns to return. Passing null will
     *            return all columns, which is discouraged to prevent reading
     *            data from storage that isn't going to be used.
     * @param selection A filter declaring which rows to return, formatted as an
     *            SQL WHERE clause (excluding the WHERE itself). Passing null
     *            will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *         replaced by the values from selectionArgs, in order that they
     *         appear in the selection. The values will be bound as Strings.
     * @param groupBy A filter declaring how to group rows, formatted as an SQL
     *            GROUP BY clause (excluding the GROUP BY itself). Passing null
     *            will cause the rows to not be grouped.
     * @param having A filter declare which row groups to include in the cursor,
     *            if row grouping is being used, formatted as an SQL HAVING
     *            clause (excluding the HAVING itself). Passing null will cause
     *            all row groups to be included, and is required when row
     *            grouping is not being used.
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
     *            (excluding the ORDER BY itself). Passing null will use the
     *            default sort order, which may be unordered.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that
     * {@link Cursor}s are not synchronized, see the documentation for more details.
     * @see Cursor
     */

    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(DATABASENAME_T, columns, selection, selectionArgs, groupBy, having, orderBy);
    }


}