package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TreinoActivity extends AppCompatActivity {

    public static final String NUMERO_DE_REPETICOES_INVALIDO = "Número de repetições inválido";
    public static final String NUMERO_DE_SERIES_INVALIDO = "Número de séries inválido";
    public static final String NUMERO_INVALIDO_DE_PESO_USADO = "Número inválido de peso usado";

    public static final String NOME_DO_EXERCICIO = "Nome do exercicio";
    public static final String NUMERO_DE_REPETICOES = "Número de repetições";
    public static final String NUMERO_DE_SERIES = "Número de séries";
    public static final String NUMERO_DO_PESO_USADO = "Número do peso usado";
    public static final String TEM_DE_COLOCAR_UM_EXERCÍCIO = "Tem de colocar um exercício";
    public static final String A_FECHAR_PORQUE_NÃO_HÁ_IDS = "A fechar porque não há ids";


    public int id =0;
    private EditText EditText1;

    Treinos treinos = new Treinos();
    DiasSemana diasSemana = new DiasSemana();

    private int idTreino = 0;
    private String exercicio = "";
    private int repeticoes = 0;
    private int series = 0;
    private int pesoUsado = 0;
    private int total_Reps = 0;
    private DBTableTreino dbTableTreino;
    private DBTableDiasSemana dbTableDiasSemana;
    private DBTreinoOpenHelper dbTreinoOpenHelper;

    ContentValues values = new ContentValues();

    private static String[] REPETICOES_COLUMN = new String[]{"Repeticoes"};
    private static String[] SERIES_COLUMN = new String[]{"Series"};
    private static String[] TREINO_ID_COLUMN = new String[]{"_ID"};
    private static String[] DIA_ID_COLUMN = new String[]{"_ID"};
    private boolean novosValores = false;

    Treinos treino = new Treinos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treino);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTreino();//Cria o treino e só depois dá a mensagem para guardar
                Snackbar.make(view, "A guardar...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void createTreino() {
        Intent intent = getIntent();
        int treinoID = intent.getIntExtra(MainActivity.VEZES_QUE_CLICADO_EM_ADICIONAR_TREINO, 0);
        String exercicio = intent.getStringExtra(MainActivity.EXERCICIO);
        int peso = intent.getIntExtra(MainActivity.PESO_USADO, 0);
        int repeticoes = intent.getIntExtra(MainActivity.REPETICOES, 0);
        int series = intent.getIntExtra(MainActivity.SERIES, 0);
        int total = intent.getIntExtra(MainActivity.TOTAL, 0);


        int idDia = intent.getIntExtra(MainActivity.DIA, 0);
        int mes = intent.getIntExtra(MainActivity.MES, 0);

        DBTreinoOpenHelper dbTreinoOpenHelper = new DBTreinoOpenHelper(this);
        SQLiteDatabase db = dbTreinoOpenHelper.getWritableDatabase();


        DBTableDiasSemana dbTableDiasSemana = new DBTableDiasSemana(db);
        DBTableTreino dbTableTreino = new DBTableTreino(db);
        Treinos treino = new Treinos();
        DiasSemana diasSemanas = new DiasSemana();

        if (treinoID < 0 || idDia == 0) {
            Toast.makeText(getApplicationContext(), A_FECHAR_PORQUE_NÃO_HÁ_IDS, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        idTreino = treinoID;


        TextView textViewDia = (TextView) findViewById(R.id.textViewDia);
        EditText editTextExercicio = (EditText) findViewById(R.id.editTextExercicio);
        EditText editTextRep = (EditText) findViewById(R.id.editTextRep);
        EditText editTextSerie = (EditText) findViewById(R.id.editTextSerie);
        EditText editTextPeso = (EditText) findViewById(R.id.editTextPeso);
        TextView textView_Total = (TextView) findViewById(R.id.textView_Total);

        textViewDia.setText("Treino:" + treinoID + " " + diasSemana.getIdDia() + "/" + diasSemana.getNomeMes() + "/" + diasSemana.getAndroidSystemYear());
        treino.setIdDia(idDia);
        diasSemanas.setNomeMes(diasSemanas.getNomeMes());
        diasSemanas.setIdDia(idDia);
        treino.setTreinoId(idTreino);

        try {
            this.exercicio = editTextExercicio.getText().toString();

            if (this.exercicio.trim().isEmpty()) {
                editTextExercicio.setError(TEM_DE_COLOCAR_UM_EXERCÍCIO);
                editTextExercicio.requestFocus();

            } else {
                treino.setExercicio(this.exercicio);
            }

        } catch (NumberFormatException e) {//Apanhar se o campo ficar vazio
            editTextExercicio.setError(TEM_DE_COLOCAR_UM_EXERCÍCIO);
            editTextExercicio.requestFocus();
            return;
        }

        try {
            pesoUsado = Integer.parseInt(editTextPeso.getText().toString());
            if(pesoUsado <= 0){//se o idTreino for mal introduzido
                editTextPeso.setError(NUMERO_INVALIDO_DE_PESO_USADO);
                editTextPeso.requestFocus();
            } else if (pesoUsado > 0) {
                treino.setPesoUsado(pesoUsado);//Introduzir sempre o pesoUsado
            }
        } catch (NumberFormatException e) {
            editTextPeso.setError(NUMERO_INVALIDO_DE_PESO_USADO);
            editTextPeso.requestFocus();
            return;
        }

        try {
            this.repeticoes = Integer.parseInt(editTextRep.getText().toString());
            if (this.repeticoes <= 0) {
                editTextRep.setError(NUMERO_DE_REPETICOES_INVALIDO);
                editTextRep.requestFocus();
            } else {//se for bem introduzido
                treino.setRepeticoes(this.repeticoes);
            }

        } catch (NumberFormatException e) {
            editTextRep.setError(NUMERO_DE_REPETICOES_INVALIDO);
            editTextRep.requestFocus();
            return;
        }

        try {
            this.series = Integer.parseInt(editTextSerie.getText().toString());
            if(this.series <= 0){//se o idTreino for mal introduzido
                editTextSerie.setError(NUMERO_DE_SERIES_INVALIDO);
                editTextSerie.requestFocus();

            }else {
                treino.setSeries(this.series);
            }
        } catch (NumberFormatException e) {
            editTextSerie.setError(NUMERO_DE_SERIES_INVALIDO);
            editTextSerie.requestFocus();
            return;
        }



        textView_Total.setText("Total de Repetições:" + treino.getRepeticoes() * treino.getSeries());

        //DEBUG
        //textView_Total.setText("" + dbTableTreino.getContentValues(treino));
        //textView_Total.setText("" + dbTableDiasSemana.getContentValues(diasSemanas));


        //dbTableDiasSemana.insert(dbTableDiasSemana.getContentValues(diasSemanas));
        //dbTableTreino.insert(dbTableTreino.getContentValues(treino));

        editTextRep.setText("");
        editTextPeso.setText("");
        editTextSerie.setText("");
        textView_Total.setText("");

        db.close();

    }
    @Override
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
        if(id == R.id.action_Listar){//Eliminar treino
            showListar();
            Toast.makeText(getApplicationContext(), "Lista de todos os treinos" , Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

    private void showListar() {
        Intent intent = new Intent(this, ListaActivity.class);

        /*intent.putExtra("Treino ID",treino.getTreinoId());
        intent.putExtra("Dia ID",diasSemana.getIdDia());*/

        startActivity(intent);


    }

}
