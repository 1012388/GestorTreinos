package pt.ipg.gestortreinos;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TreinoDBTest {
    @Before

    public void setUp(){
        getContext().deleteDatabase(DBTreinoOpenHelper.DATABASE_NAME);
    }

    @Test
    public void openDBTreinoTest() {
        // Context of the app under test.
        Context appContext = getContext();

        //assertEquals("pt.ipg.gestortreinos", appContext.getPackageName());
         DBTreinoOpenHelper dbTreinoOpenHelper = new DBTreinoOpenHelper(appContext);
         SQLiteDatabase db = dbTreinoOpenHelper.getReadableDatabase();//Abrir a bd para saber se existe

        assertTrue("Couldnt open/create db",db.isOpen());
        db.close();
    }

    public void TreinoCRUDTest(){
        DBTreinoOpenHelper dbTreinoOpenHelper = new DBTreinoOpenHelper(getContext());

        SQLiteDatabase db = dbTreinoOpenHelper.getWritableDatabase();

        DBTableTreino dbTableTreino = new DBTableTreino(db);

        Treinos treinos = new Treinos();

        treinos.setId(1);
        treinos.setExercicio("Elevações");
        treinos.setPesoUsado(20);
        treinos.setSeries(3);
        treinos.setRepeticoes(10);
        treinos.getTotal_Reps(10,3);

        //Teste ao criar/inserir
        long id = insertTreino(dbTableTreino,treinos);

        //query/read C(R)UD
        treinos = ReadFirstTreino(dbTableTreino,1
                                ,"Elevações"
                                ,20,3
                                ,10,30);

        //Teste ao actualizar
        treinos.setPesoUsado(10);
        int rowsAffected = dbTableTreino.update(DBTableTreino.getContentValues(treinos)
                ,DBTableTreino._ID+"=?"
                ,new String[]{Long.toString(id)});

        assertEquals("Failed to update",1,rowsAffected);

        //query/read C(R)UD
        treinos = ReadFirstTreino(dbTableTreino,1
                                ,"Elevações"
                                ,10,3
                                ,10,30);

        //delete CRU(D)
        rowsAffected = dbTableTreino.delete(DBTableTreino._ID+"=?"
                ,new String[]{Long.toString(id)});

        assertEquals("Failed to delete treino",1,rowsAffected);

        Cursor cursor = dbTableTreino.query(DBTableTreino.ALL_COLUMNS,
                                            null,null,
                                            null,null,null
                                            );

        assertEquals("Treinos encontrados depois de apagar ???",0,cursor.getCount());
    }

    @NonNull

    private Treinos ReadFirstTreino(DBTableTreino tableTreino,long expectedTreinoId,String expectedExercicio,int expectedPeso,int expectedSeries,int expectedRepeticoes,int expectedTotalReps){

        Cursor cursor = tableTreino.query(DBTableTreino.ALL_COLUMNS
                                            ,null,null
                                            ,null,null,null
                                         );

        assertEquals("Falha ao ler Treinos",1,cursor.getCount());
        assertTrue("Falha ao ler o primeiro treino",cursor.moveToNext());

        Treinos treinos = DBTableTreino.getCurrentTreinoFromCursor(cursor);

        assertEquals("ID incorreto do treino ", expectedTreinoId, treinos.getId());
        assertEquals("Nome incorreto do exercício", expectedExercicio, treinos.getExercicio());
        assertEquals("Número incorreto de peso usado", expectedPeso, treinos.getPesoUsado());
        assertEquals("Número incorreto de séries", expectedSeries, treinos.getSeries());
        assertEquals("Número incorreto de repetições", expectedRepeticoes, treinos.getRepeticoes());
        assertEquals("Número incorreto de total repetições ", expectedTotalReps, treinos.getTotal_Reps(expectedRepeticoes,expectedSeries));

        return treinos;
    }

    private long insertTreino(DBTableTreino dbTableTreino, Treinos treinos) {
        long id = dbTableTreino.insert(DBTableTreino.getContentValues(treinos));

        assertNotEquals("Failed to insert",-1,id);
        return id;
    }

    @NonNull
    private DiasSemana ReadFirstDia(DBTableDiasSemana tableDiasSemana,long expectedID,String expectedNomeDia,String expectedGrupoMuscular){

      Cursor cursor = tableDiasSemana.query(DBTableDiasSemana.ALL_COLUMNS,null
                            ,null,null
                            ,null,null
                            );

      assertEquals("Falha ao ler o Dia",1,cursor.getCount());
      assertTrue("Falha ao ler o primeiro dia",cursor.moveToNext());

      DiasSemana dia = DBTableDiasSemana.getCurrentTreinoFromCursor(cursor);

      assertEquals("ID incorreto do dia ", expectedID,dia.getIdDia());
      assertEquals("Nome incorreto do dia",expectedNomeDia, dia.getNome_dia());
      assertEquals("Nome incorreto do grupo muscular", expectedGrupoMuscular, dia.getGrupo_Muscular());

      return dia;
    }
    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}
