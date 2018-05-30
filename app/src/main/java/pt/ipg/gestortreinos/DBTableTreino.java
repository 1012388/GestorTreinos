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
                        "FOREIGN KEY (" + ID_DIA + ") REFERENCES " +
                        "diasSemana" +
                        "(" +
                        "id" +
                        ")" +
                        ")"
        );
    }

    //IR ao github para ver o que o setor tem na classe DBTableCategory

    public long insert(ContentValues values){
       return db.insert(DATABASENAME_T,null,values);
     }

     public ContentValues getContentValues(Treinos treino){
         ContentValues values = new ContentValues();

         values.put(ID_TREINO,treino.getId());
        //O mesmo para o resto dos campos
        return values;
     }

    public void update(){

    }

    public void delete(){

    }

    //IR BUSCAR OS DADOS
}

