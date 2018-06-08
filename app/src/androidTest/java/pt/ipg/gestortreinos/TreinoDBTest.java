package pt.ipg.gestortreinos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TreinoDBTest {
    /*@Before

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

        treinos.setExercicio("Elevações");

        //Teste ao criar/inserir
        long id = insertTreino(dbTableTreino,treinos);


    }

    private long insertTreino(DBTableTreino dbTableTreino, Treinos treinos) {
        long id = dbTableTreino.insert(DBTableTreino.getContentValues(treinos));

        assertNotEquals("Failed to insert",-1,id);
        return id;
    }

    private int update(DBTableTreino dbTableTreino,Treinos treinos,String whereClause,String[] whereArgs){
        int id = dbTableTreino.update(DBTableTreino.getContentValues(treinos),whereClause,whereArgs);

        assertNotEquals("",,id);
        return id;
    }

    /*private Treinos ReadFirstTreino(DBTableTreino tableTreino,String expectedName,long expectedId){

        Cursor cursor = tableTreino.query(DBTableTreino.ALL_COLUMNS,null,null,null);
        assertEquals("Failed to read Treinos",1,cursor.getCount());

        assertTrue("Failed t read the first Treino",cursor.moveToNext());

        Treinos treinos = DBTableTreino.getCurrentTreinoFromCursor(cursor);

        assertEquals("Incorrect exercice name", expectedName, treinos.getExercicio());
        assertEquals("Incorrect treino id", expectedId, treinos.getId());

        return treinos;
    }*/

   /* private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

*/

}
