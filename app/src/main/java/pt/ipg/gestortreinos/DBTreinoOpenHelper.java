package pt.ipg.gestortreinos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTreinoOpenHelper extends SQLiteOpenHelper {
    public static final boolean PRODUCTION = false;

    public static final String DATABASE_NAME = "treinos.db";
    public static final int DATABASE_VERSION = 1;



    public DBTreinoOpenHelper(Context context) {//CONSTRUTOR
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBTableTreino dbTableTreino = new DBTableTreino(db);
        DBTableDiasSemana dbTableDiasSemana = new DBTableDiasSemana(db);

        dbTableDiasSemana.create();
        dbTableTreino.create();

        if(!PRODUCTION){
            seed(db);
        }
    }



    public void seed(SQLiteDatabase db){
        DBTableTreino dbTableTreino = new DBTableTreino(db);
        DBTableDiasSemana dbTableDiasSemana = new DBTableDiasSemana(db);

        Treinos treinos = new Treinos();

        treinos.setExercicio("Elevações");
        treinos.setPesoUsado(65);
        treinos.setRepeticoes(10);
        treinos.setSeries(3);
        treinos.setIdDia(18);

        int idTreino18 = (int) dbTableTreino.insert(DBTableTreino.getContentValues(treinos));

        DiasSemana diasSemana = new DiasSemana();
        diasSemana.setNomeMes("06");
        diasSemana.setIdDia(18);

        int idDia18 = (int) dbTableDiasSemana.insert(DBTableDiasSemana.getContentValues(diasSemana));

        treinos = new Treinos();

        treinos.setExercicio("Supino");
        treinos.setPesoUsado(80);
        treinos.setRepeticoes(4);
        treinos.setSeries(3);
        treinos.setIdDia(19);

        int idTreino19 = (int) dbTableTreino.insert(DBTableTreino.getContentValues(treinos));

        diasSemana = new DiasSemana();
        diasSemana.setNomeMes("06");
        diasSemana.setIdDia(19);

        int idDia19 = (int) dbTableDiasSemana.insert(DBTableDiasSemana.getContentValues(diasSemana));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Elimina a tabela se existir
        db.execSQL("DROP TABLE IF EXISTS "+DBTableTreino.DATABASENAME_T);
        db.execSQL("DROP TABLE IF EXISTS "+DBTableDiasSemana.DATABASENAME_D);

        onCreate(db);
    }
}
