
package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.UriMatcher;
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


public class MainActivity extends AppCompatActivity  {
    public static final String VEZES_QUE_CLICADO_EM_ADICIONAR_TREINO = "Vezes que clicado em adicionar treino";
    public static final String TREINO_ID = "Treino ID";
    public static final String DIA = "Dia";
    public int conta = 0;
    private  Button b1;
    int tag_id = 0;
    Treinos treino = new Treinos();
    DiasSemana diasSemana = new DiasSemana();

    //private TreinoCursorAdapter treinoCursorAdapter;

    ContentValues values = new ContentValues();


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
                    createNewPraticeButton();
                }
            }
        });


    }

    private void createNewPraticeButton() {//Cria um botão que ao ser presionado abre a atividade Treinos

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
        //dbTableDiasSemana.delete(null,null);
        //dbTableTreino.delete(null,null);

        //dbTableDiasSemana.update(DBTableDiasSemana.getContentValues(diasSemana),DBTableDiasSemana._ID+"="+diasSemana.getIdDia(),null);

        //dbTableTreino.update(DBTableTreino.getContentValues(treino),DBTableTreino._ID+"="+treino.getIdDia(),null);

        //DbTableOrcamento._ID+"= (SELECT MAX(id_orcamento) FROM Orcamento)"

        db.close();
    }

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
        } else if (id == R.id.action_Listar) {
            showListar();
            Toast.makeText(getApplicationContext(), "Lista de todos os treinos" , Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showListar() {
        Intent intent = new Intent(this, ListaActivity.class);

        intent.putExtra(TREINO_ID, treino.getTreinoId());
        intent.putExtra(DIA, diasSemana.getIdDia());

        startActivity(intent);
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


}
