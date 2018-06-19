package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TreinoActivity extends AppCompatActivity {
    public static final String DIA_INVALIDO = "Dia Inválido";
    public static final String NUMERO_DE_REPETICOES_INVALIDO = "Número de repetições inválido";
    public static final String NUMERO_DE_SERIES_INVALIDO = "Número de séries inválido";
    public static final String NUMERO_INVALIDO_DE_PESO_USADO = "Número inválido de peso usado";

    public static final String NOME_DO_EXERCICIO = "Nome do exercicio";
    public static final String NUMERO_DE_REPETICOES = "Número de repetições";
    public static final String NUMERO_DE_SERIES = "Número de séries";
    public static final String NUMERO_DO_PESO_USADO = "Número do peso usado";
    public static final String TEM_DE_COLOCAR_UM_EXERCÍCIO = "Tem de colocar um exercício";


    public int id =0;
    private EditText EditText1;

    Treinos treinos = new Treinos();
    DiasSemana diasSemana = new DiasSemana();

    final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
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
        int idDia = intent.getIntExtra(MainActivity.DIA, 0);

        DBTreinoOpenHelper dbTreinoOpenHelper = new DBTreinoOpenHelper(this);
        SQLiteDatabase db = dbTreinoOpenHelper.getWritableDatabase();
        SQLiteDatabase readableDatabase = dbTreinoOpenHelper.getReadableDatabase();

        DBTableDiasSemana dbTableDiasSemana = new DBTableDiasSemana(db);
        DBTableTreino dbTableTreino = new DBTableTreino(db);
        Treinos treino = new Treinos();
        DiasSemana diasSemanas = new DiasSemana();



        if (treinoID < 0 || idDia == 0) {
            Toast.makeText(getApplicationContext(), "A fechar porque não há ids", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        idTreino = treinoID;

        String exercicio = "";
        int repeticoes = 0;
        int series = 0;
        int pesoUsado = 0;
        //int total_Reps = 0;


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
            exercicio = editTextExercicio.getText().toString();
            //textView_Total.setText("Exercicio : "+ exercicio);

            if (exercicio.trim().isEmpty()) {
                editTextExercicio.setError(TEM_DE_COLOCAR_UM_EXERCÍCIO);
                editTextExercicio.requestFocus();

            } else {
                treino.setExercicio(exercicio);

                /*if (getExercicioFromTable() != "") {//Se o utilizador quiser alterar o pesoUsado,então tem de clicar outra vez para guardar, e nesse caso tem de ser feito o update para a tabela
                    dbTableTreino.update(DBTableTreino.getContentValues(treinos), DBTableTreino._ID + "=?", null);
                    //return;
                }*/
            }

        } catch (NumberFormatException/*NullPointerException*/ e) {//Apanhar se o campo ficar vazio
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

                /*if (getPesoFromTable() != 0) {//Se o utilizador quiser alterar o pesoUsado,então tem de clicar outra vez para guardar, e nesse caso tem de ser feito o update para a tabela
                    dbTableTreino.update(DBTableTreino.getContentValues(treinos), DBTableTreino._ID + "=?", null);
                }*/
            }
        } catch (NumberFormatException e) {
            editTextPeso.setError(NUMERO_INVALIDO_DE_PESO_USADO);
            editTextPeso.requestFocus();
            return;
        }

        try {
            repeticoes = Integer.parseInt(editTextRep.getText().toString());
            if (repeticoes <= 0) {
                editTextRep.setError(NUMERO_DE_REPETICOES_INVALIDO);
                editTextRep.requestFocus();
            } else {//se for bem introduzido
                treino.setRepeticoes(repeticoes);

                /*if (getRepeticoesFromTable() != 0) {//Se o utilizador quiser alterar o pesoUsado,então tem de clicar outra vez para guardar, e nesse caso tem de ser feito o update para a tabela
                    dbTableTreino.update(DBTableTreino.getContentValues(treinos), DBTableTreino._ID + "=?", null);
                }*/
            }

        } catch (NumberFormatException e) {
            editTextRep.setError(NUMERO_DE_REPETICOES_INVALIDO);
            editTextRep.requestFocus();
            return;
        }

        try {
            series = Integer.parseInt(editTextSerie.getText().toString());
            if(series <= 0){//se o idTreino for mal introduzido
                editTextSerie.setError(NUMERO_DE_SERIES_INVALIDO);
                editTextSerie.requestFocus();

            }else {
                treino.setSeries(series);

                /*if (getSeriesFromTable() != 0) {//Se o utilizador quiser alterar o pesoUsado,então tem de clicar outra vez para guardar, e nesse caso tem de ser feito o update para a tabela
                    dbTableTreino.update(DBTableTreino.getContentValues(treinos), DBTableTreino._ID + "=?", null);
                }*/
            }
        } catch (NumberFormatException e) {
            editTextSerie.setError(NUMERO_DE_SERIES_INVALIDO);
            editTextSerie.requestFocus();
            return;
        }
        //textView_Total.setText("Total de Repetições:" + treino.getRepeticoes() * treino.getSeries());

        // if(novosValores) {
        dbTableDiasSemana.insert(dbTableDiasSemana.getContentValues(diasSemanas));
        dbTableTreino.insert(dbTableTreino.getContentValues(treino));


/*
            editTextExercicio.setText("");
            editTextRep.setText("");
            editTextPeso.setText("");
            editTextSerie.setText("");
            textView_Total.setText("");

            novosValores = !novosValores;*/
        textView_Total.setText("" + dbTableTreino.getContentValues(treino));
        //}


        db.close();
    }
    //todo: As funções getFromTables estão erradas!! IR A VER COMO FIZ NA função createTreino para inserir na BD.



    private String getExercicioFromTable() {
        String ExercicioFromTable = "";

        DBTreinoOpenHelper dbTreinoOpenHelper = new DBTreinoOpenHelper(this);

        SQLiteDatabase db = dbTreinoOpenHelper.getReadableDatabase();
        String query_reps = "SELECT " + DBTableTreino.EXERCICIO + " FROM treino WHERE " + DBTableTreino._ID + " =?";
        db.rawQuery(query_reps, new String[]{String.valueOf(ExercicioFromTable)});
        db.close();

        /*
        SQLiteDatabase db = dbTreinoOpenHelper.getWritableDatabase();

        DBTableDiasSemana dbTableDiasSemana = new DBTableDiasSemana(db);
        DBTableTreino dbTableTreino = new DBTableTreino(db);
        Treinos treino = new Treinos();
        DiasSemana diasSemanas = new DiasSemana();*/

        return ExercicioFromTable;

    }

    private int getPesoFromTable() {
        int PesoFromTable = 0;

        DBTreinoOpenHelper dbTreinoOpenHelper = new DBTreinoOpenHelper(this);

        SQLiteDatabase db = dbTreinoOpenHelper.getReadableDatabase();
        String query_reps = "SELECT " + DBTableTreino.PESO_USADO + " FROM " + DBTableTreino.DATABASENAME_T + " WHERE " + DBTableTreino._ID + " =?";
        db.rawQuery(query_reps, new String[]{String.valueOf(PesoFromTable)});
        db.close();

        return PesoFromTable;
    }

    private int getRepeticoesFromTable() {
        int repeticoesFromTable = 0;

        SQLiteDatabase db = dbTreinoOpenHelper.getReadableDatabase();
        String query_reps = "SELECT " + DBTableTreino.REPETICOES + " FROM " + DBTableTreino.DATABASENAME_T + " WHERE " + DBTableTreino._ID + " =?";
        db.rawQuery(query_reps, new String[]{String.valueOf(repeticoesFromTable)});

        db.close();

        return repeticoesFromTable;
    }

    /*private int getSeriesFromTable() {

        SQLiteDatabase db = dbTreinoOpenHelper.getReadableDatabase();
        db.close();

        return seriesFromTable;
    }*/






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
        if(id == R.id.action_Eliminar){//Eliminar treino
           // deleteTreino();

        }else if(id== R.id.action_AdicionarEx) {//Alterar nome do treino
            //adicionarExercicio();
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void adicionarExercicio() {
        String[]field_names = {"Exercicio","Peso","Repetições","Séries","TotalRepetições"};
        LinearLayout linearLayoutTreino = (LinearLayout) findViewById(R.id.linearLayoutTreino);
        //CoordinatorLayout layoutTreinoParent = (CoordinatorLayout) findViewById(R.id.layoutTreinoParent);
        int tag = 0;
        int total = treinos.getTotal_Reps(treinos.setRepeticoes(3),treinos.setSeries(3));//
        for (int i = 0; i <= 4 ; i++) {
            tag = i;
            EditText1 = new EditText(TreinoActivity.this);
            EditText1.setTreinoId(i);
            EditText1.setText(field_names[i]);
            EditText1.setTag(tag);
            linearLayoutTreino.addView(EditText1);
            if (tag == 4) {
                TextView TextView1 = new TextView(TreinoActivity.this);
                TextView1.setLayoutParams(layoutParams);
                TextView1.setText(field_names[tag]+":"+String.valueOf(total));
            }
        }
        //editTextExercicio
        if(EditText1.getTreinoId()== 0) {
            EditText1.setInputType(InputType.TYPE_CLASS_TEXT);
        } else{//editTextPeso,Reps e Series que são todos numeros
            EditText1.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

    }*/

    private void deleteTreino() {
        //eliminar botão escolhido

        /*Algoritmo para eliminar botão:
            Selecionar botão,ou seja criar uma check box para o botão
                Torná-lo invisível
         */
   }

    /*private int verificaNLinhasTreinoBD(Treinos treinos) {
        int linha = -1;

        if (Integer.parseInt(query) > linha) {
            return linha = cursor.getCount();//foi introduzido x linhas na tabela Treino
        } else {
            return linha = -1;//Então não foi nenhuma linha introduzida na tabela Treino
        }




    }*/


    private int verificarNLinhasDiaBD(DiasSemana diasSemana) {
        int linha = -1;
        DBTreinoOpenHelper dbTreinoOpenHelper = new DBTreinoOpenHelper(getApplicationContext());

        SQLiteDatabase readableDiasDatabase = dbTreinoOpenHelper.getReadableDatabase();

        DBTableDiasSemana dbTableDiasSemana = new DBTableDiasSemana(readableDiasDatabase);

        Cursor cursor = dbTableDiasSemana.query(TREINO_ID_COLUMN, null,
                null, null
                , null, null
        );

        if (cursor.getCount() > linha) {
            return linha = cursor.getCount();//foi introduzido x linhas na tabela Treino
        } else {
            return linha = -1;//Então não foi nenhuma linha introduzida na tabela Treino
        }

    }
}
