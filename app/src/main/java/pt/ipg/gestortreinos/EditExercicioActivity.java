package pt.ipg.gestortreinos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.content.CursorLoader;

public class EditExercicioActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int TREINO_CURSOR_LOADER_ID = 0;


    private EditText editTextExercicio;
    private EditText editTextSerie;
    private EditText editTextPeso;
    private EditText editTextReps;

    private Spinner spinnerTreino;
    private Treinos treino;
    private String exercicio;
    private int pesoUsado;
    private int repeticoes;
    private int series;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        int treinoId = intent.getIntExtra(MainActivity.TREINO_ID, -1);

        if (treinoId == -1) {
            Toast.makeText(getApplicationContext(), "Erro ao iniciar esta atividade", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Cursor cursorTreino = getContentResolver().query(
                Uri.withAppendedPath(TreinoContentProvider.TREINO_URI, Integer.toString(treinoId)),
                DBTableTreino.ALL_COLUMNS
                , null,
                null
                , null
        );

        if (!cursorTreino.moveToNext()) {
            Toast.makeText(this, "Não foi encontrado nenhum Treino", Toast.LENGTH_LONG).show();
            finish();
            return;
        }


        editTextExercicio = (EditText) findViewById(R.id.editTextExercicio);
        editTextPeso = (EditText) findViewById(R.id.editTextPeso);
        editTextReps = (EditText) findViewById(R.id.editTextRep);
        editTextSerie = (EditText) findViewById(R.id.editTextSerie);
        spinnerTreino = (Spinner) findViewById(R.id.spinnerTreino);

        treino = DBTableTreino.getCurrentTreinoFromCursor(cursorTreino);

        editTextExercicio.setText(treino.getTreinoId());
        editTextPeso.setText(treino.getPesoUsado());
        editTextReps.setText(treino.getRepeticoes());
        editTextSerie.setText(treino.getSeries());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(TREINO_CURSOR_LOADER_ID, null, this);
        //getSupportLoaderManager().initLoader(,null,);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportLoaderManager().restartLoader(TREINO_CURSOR_LOADER_ID, null, this);
    }

    public void cancelar(View view) {
        finish();
        return;
    }

    public void guardar(View view) {
        try {
            exercicio = editTextExercicio.getText().toString();
            if (exercicio.trim().isEmpty()) {
                editTextExercicio.setError(TreinoActivity.TEM_DE_COLOCAR_UM_EXERCÍCIO);
                editTextExercicio.requestFocus();
            }

        } catch (NumberFormatException e) {//Apanhar se o campo ficar vazio
            editTextExercicio.setError(TreinoActivity.TEM_DE_COLOCAR_UM_EXERCÍCIO);
            editTextExercicio.requestFocus();
            return;
        }

        try {
            pesoUsado = Integer.parseInt(editTextPeso.getText().toString());
            if (pesoUsado <= 0) {
                editTextPeso.setError(TreinoActivity.NUMERO_INVALIDO_DE_PESO_USADO);
                editTextPeso.requestFocus();
            }

        } catch (NumberFormatException e) {
            editTextPeso.setError(TreinoActivity.NUMERO_INVALIDO_DE_PESO_USADO);
            editTextPeso.requestFocus();
            return;
        }

        try {
            repeticoes = Integer.parseInt(editTextReps.getText().toString());
            if (repeticoes <= 0) {
                editTextReps.setError(TreinoActivity.NUMERO_DE_REPETICOES_INVALIDO);
                editTextReps.requestFocus();
            }
        } catch (NumberFormatException e) {
            editTextReps.setError(TreinoActivity.NUMERO_DE_REPETICOES_INVALIDO);
            editTextReps.requestFocus();
            return;
        }

        try {
            series = Integer.parseInt(editTextSerie.getText().toString());
            if (series <= 0) {//se o idTreino for mal introduzido
                editTextSerie.setError(TreinoActivity.NUMERO_DE_SERIES_INVALIDO);
                editTextSerie.requestFocus();
            }
        } catch (NumberFormatException e) {
            editTextSerie.setError(TreinoActivity.NUMERO_DE_SERIES_INVALIDO);
            editTextSerie.requestFocus();
            return;
        }

        treino.setExercicio(editTextExercicio.getText().toString());
        treino.setPesoUsado(Integer.parseInt(editTextExercicio.getText().toString()));
        treino.setRepeticoes(Integer.parseInt(editTextReps.getText().toString()));
        treino.setSeries(Integer.parseInt(editTextSerie.getText().toString()));

        int linhasAfetadas = getContentResolver().update(
                Uri.withAppendedPath(TreinoContentProvider.TREINO_URI, Integer.toString(treino.getTreinoId())),
                DBTableTreino.getContentValues(treino),
                null,
                null
        );

        if (linhasAfetadas > 0) {
            Toast.makeText(getApplicationContext(), "Update foi feito com sucesso", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Toast.makeText(getApplicationContext(), "Update foi feito com insucesso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == TREINO_CURSOR_LOADER_ID) {
            return new CursorLoader(this, TreinoContentProvider.TREINO_URI
                    , DBTableDiasSemana.ALL_COLUMNS, null, null, null);
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
     * the {@link CursorAdapter#CursorAdapter(Context, Cursor, int)} constructor <em>without</em> passing
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
        SimpleCursorAdapter cursorAdapterTreino = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                data,
                new String[]{DBTableTreino.TREINO_ID},
                new int[]{android.R.id.text1}
        );

        spinnerTreino.setAdapter(cursorAdapterTreino);

        int idTreino = treino.getTreinoId();

        for (int i = 0; i < spinnerTreino.getCount(); i++) {
            Cursor cursor = (Cursor) spinnerTreino.getItemAtPosition(i);
            final int posID = cursor.getColumnIndex(DBTableTreino._ID);

            if (idTreino == cursor.getInt(posID)) {
                spinnerTreino.setSelection(i);
                break;
            }
        }
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

    }
}
