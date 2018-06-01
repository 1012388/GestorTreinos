package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DBTableTreino implements BaseColumns {

    public static final String ID_TREINO = "id_treino";
    public static final String REPETICOES = "repeticoes";
    public static final String EXERCICIO = "exercicio";
    public static final String PESO = "peso";
    public static final String ID_DIA = "id_dia";
    public static final String DATABASENAME_T = "treinos";
    public static final String PESO_USADO = "pesoUsado";
    public static final String SERIES = "series";
    public static final String TOTAL_REPS = "total_Reps";
    private SQLiteDatabase db;

    public DBTableTreino(SQLiteDatabase db) {

    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + DATABASENAME_T + " (" +
                        ID_TREINO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        REPETICOES + " INTEGER NOT NULL," +
                        EXERCICIO + " TEXT NOT NULL," +
                        PESO + " INTEGER NOT NULL," +
                        ID_DIA + " INTEGER ," +
                        PESO_USADO + " INTEGER NOT NULL," +
                        SERIES + " INTEGER NOT NULL," +
                        TOTAL_REPS + " INTEGER NOT NULL," +
                        "FOREIGN KEY (" + ID_DIA + ") REFERENCES " +
                        DBTableDiasSemana.DATABASENAME+
                        "("+DBTableDiasSemana.ID_DIA+")"+
                        ")"
        );
    }

    //IR ao github para ver o que o setor tem na classe DBTableCategory
    //link para o github:https://github.com/noellopes/Books/blob/master/app/src/main/java/pt/ipg/books/DbTableCategories.java



     public ContentValues getContentValues(Treinos treino){
         ContentValues values = new ContentValues();

         values.put(ID_TREINO,treino.getId());
         values.put(EXERCICIO,treino.getExercicio());
         values.put(REPETICOES,treino.getRepeticoes());
         values.put(PESO_USADO,treino.getPesoUsado());
         values.put(SERIES,treino.getSeries());
         values.put(TOTAL_REPS,treino.getRepeticoes());
        return values;
     }

    public long insert(ContentValues values){
        return db.insert(DATABASENAME_T,null,values);
    }
   /*
    public long update(){

    }

    public long delete(){

    }*/

    //IR BUSCAR OS DADOS
}

