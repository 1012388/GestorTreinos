
package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.CursorAdapter;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String VEZES_QUE_CLICADO_EM_ADICIONAR_TREINO = "Vezes que clicado em adicionar treino";
    public static final String TREINO_ID = "Treino ID";
    public static final String DIA = "Dia";
    public int conta = 0;
    private  Button b1;
    int tag_id = 0;
    Treinos treino = new Treinos();
    DiasSemana diasSemana = new DiasSemana();

    private TreinoCursorAdapter treinoCursorAdapter;

    ContentValues values = new ContentValues();

    public static final int TREINO_CURSOR_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(" " + diasSemana.getIdDia() + "/" + diasSemana.getNomeMes() + "/" + diasSemana.getAndroidSystemYear());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (diasSemana.getAndroidSystemDay() != diasSemana.getAndroidSystemDay() || seClicado() > 2) {//se for no mesmo dia e se clicar 2 vezes
                    Toast.makeText(getApplicationContext(), "Erro!", Toast.LENGTH_SHORT).show();
                } else {
                    //createNewPraticeButton();
                }
            }
        });

        RecyclerView recyclerViewTreino = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerViewTreino.setLayoutManager(new LinearLayoutManager(this));
        treinoCursorAdapter = new TreinoCursorAdapter(this);

        recyclerViewTreino.setAdapter(treinoCursorAdapter);

        treinoCursorAdapter.setViewHolderClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                showTreinos();
            }
        });

        getSupportLoaderManager().initLoader(TREINO_CURSOR_LOADER_ID, null, this);
    }

   /* private void createNewPraticeButton() {//Cria um botão que ao ser presionado abre a atividade Treinos

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        DBTreinoOpenHelper dbTreinoOpenHelper = new DBTreinoOpenHelper(getApplicationContext());
        SQLiteDatabase db = dbTreinoOpenHelper.getWritableDatabase();

        DBTableTreino dbTableTreino = new DBTableTreino(db);
        DBTableDiasSemana dbTableDiasSemana = new DBTableDiasSemana(db);


        b1 = new Button(MainActivity.this);
        tag_id = conta;


        b1.setId(tag_id);
        b1.setText("Treino" + tag_id);
        b1.setTag(tag_id);
        tag_id++;


        b1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                showTreinos();//Abrir Treino Activity
            }
        });

        linearLayout.addView(b1);
        diasSemana.setIdDia(diasSemana.getIdDia());
        diasSemana.setNomeMes(diasSemana.getAndroidSystemMonth());
        treino.setTreinoId(seClicado());
        treino.setExercicio("");
        treino.setRepeticoes(0);
        treino.setSeries(0);
        treino.setPesoUsado(0);

        //Inserção para a base de dados
        dbTableDiasSemana.insert(DBTableDiasSemana.getContentValues(diasSemana));
        dbTableTreino.insert(DBTableTreino.getContentValues(treino));
        db.close();
    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_Eliminar) {//Adicionar treino
            //deleteTreinoButton();
            //conta++;
            Toast.makeText(getApplicationContext(), "A eliminar o treino:" + tag_id, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    public int seClicado() {
        conta++;
        return conta;
    }

    private void showTreinos(){
    //Passar para o Treino Actitivity o valor de quantas vezes o botão foi clicado
        Intent intent = new Intent(this, TreinoActivity.class);

        intent.putExtra(TREINO_ID, treino.getTreinoId());
        intent.putExtra(DIA, diasSemana.getIdDia());


        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(TREINO_CURSOR_LOADER_ID, null, this);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     * <p>
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == TREINO_CURSOR_LOADER_ID) {
            return new android.support.v4.content.CursorLoader(this,
                    TreinoContentProvider.TREINO_URI,
                    DBTableTreino.ALL_COLUMNS, null, null, null);

        }
        return null;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     * <p>
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     * <p>
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context, * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     * <p>
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        treinoCursorAdapter.refreshData(data);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     * <p>
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        treinoCursorAdapter.refreshData(null);
    }

}
