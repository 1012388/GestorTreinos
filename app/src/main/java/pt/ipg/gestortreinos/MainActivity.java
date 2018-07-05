
package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  {
    public static final String VEZES_QUE_CLICADO_EM_ADICIONAR_TREINO = "Vezes que clicado em adicionar treino";
    public static final String TREINO_ID = "Treino ID";
    public static final String DIA = "Dia";
    public static final String EXERCICIO = "Exercicio";
    public static final String PESO_USADO = "Peso Usado";
    public static final String SERIES = "Séries";
    public static final String TOTAL = "Total";
    public static final String MES = "mês";
    public static final String REPETICOES = "Repetições";
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
                    Toast.makeText(getApplicationContext(), R.string.Erro, Toast.LENGTH_SHORT).show();
                } else {
                    createNewPraticeButton();
                }
            }
        });


    }

    private void createNewPraticeButton() {//Cria um botão que ao ser presionado abre a atividade Treinos

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
/*
        DBTreinoOpenHelper dbTreinoOpenHelper = new DBTreinoOpenHelper(getApplicationContext());
        SQLiteDatabase db = dbTreinoOpenHelper.getWritableDatabase();

        DBTableTreino dbTableTreino = new DBTableTreino(db);


        DBTableDiasSemana dbTableDiasSemana = new DBTableDiasSemana(db);*/


        b1 = new Button(MainActivity.this);
        tag_id = conta;

        b1.setId(tag_id);
        b1.setText(getString(R.string.Treino) + tag_id);
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

        //Inserção para a base de dado
        // dbTableDiasSemana.insert(DBTableDiasSemana.getContentValues(diasSemana));
        //dbTableTreino.insert(DBTableTreino.getContentValues(treino));
        //dbTableDiasSemana.delete(null,null);
        //dbTableTreino.delete(null,null);

        //dbTableDiasSemana.update(DBTableDiasSemana.getContentValues(diasSemana),DBTableDiasSemana._ID+"="+diasSemana.getIdDia(),null);

        //dbTableTreino.update(DBTableTreino.getContentValues(treino),DBTableTreino._ID+"="+treino.getIdDia(),null);

        //DbTableOrcamento._ID+"= (SELECT MAX(id_orcamento) FROM Orcamento)"

        //db.close();
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
        if (id == R.id.action_Listar) {
            showListar();
            Toast.makeText(getApplicationContext(), R.string.Listar_todos_os_treinos , Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showListar() {
        Intent intent = new Intent(this, ListaActivity.class);

        intent.putExtra(TREINO_ID, treino.getTreinoId());
       // intent.putExtra(DIA, diasSemana.getIdDia());

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
        intent.putExtra(EXERCICIO, treino.getExercicio());
        intent.putExtra(PESO_USADO, treino.getPesoUsado());
        intent.putExtra(REPETICOES, treino.getRepeticoes());
        intent.putExtra(SERIES, treino.getSeries());
        intent.putExtra(TOTAL, treino.getTotal_Reps(treino.getRepeticoes(),treino.getSeries()));

        intent.putExtra(DIA, diasSemana.getIdDia());
        intent.putExtra(MES, diasSemana.getNomeMes());



        startActivity(intent);
    }


}
