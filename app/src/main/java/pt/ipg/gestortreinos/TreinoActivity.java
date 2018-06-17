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
    public int idTreino=0;
    public String exercicio ="";
    public int repeticoes=0;
    public int series=0;
    public int pesoUsado=0;
    public int total_Reps = 0;
    private DBTableTreino dbTableTreino;
    private DBTreinoOpenHelper dbTreinoOpenHelper;

    ContentValues values = new ContentValues();

    private static String[] REPETICOES_COLUMN = new String[] {"repetições"};
    private static String[] SERIES_COLUMN = new String[] {"séries"};
    private static String[] TREINO_ID_COLUMN = new String[]{"_ID"};
    private static String[] DIA_ID_COLUMN = new String[]{"_ID"};

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

        if (treinoID < 0 || idDia == 0) {
            Toast.makeText(getApplicationContext(), "A fechar porque não há ids", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        idTreino = treinoID;
        /*int
        String exercicio = "";
        int repeticoes = 0;
        int series = 0;
        int pesoUsado = 0;
        int total_Reps = 0;*/


        TextView textViewDia = (TextView) findViewById(R.id.textViewDia);
        EditText editTextExercicio = (EditText) findViewById(R.id.editTextExercicio);
        EditText editTextRep = (EditText) findViewById(R.id.editTextRep);
        EditText editTextSerie = (EditText) findViewById(R.id.editTextSerie);
        EditText editTextPeso = (EditText) findViewById(R.id.editTextPeso);
        TextView textView_Total = (TextView) findViewById(R.id.textView_Total);

        textViewDia.setText("Treino:" + treinoID + " " + diasSemana.getIdDia() + "/" + diasSemana.getNomeMes() + "/" + diasSemana.getAndroidSystemYear());

        treinos.setRepeticoes(pesoUsado);
        treinos.setSeries(repeticoes);
        treinos.setSeries(series);
        treinos.setExercicio(exercicio);

        try {
            exercicio = editTextExercicio.getText().toString();
            if (exercicio == "") {
                editTextExercicio.setError(TEM_DE_COLOCAR_UM_EXERCÍCIO);
                editTextExercicio.requestFocus();
            }
        } catch (NumberFormatException e) {
            editTextExercicio.setError(TEM_DE_COLOCAR_UM_EXERCÍCIO);
            editTextExercicio.requestFocus();
            return;
        }

        try {
            pesoUsado = Integer.parseInt(editTextPeso.getText().toString());
            if(pesoUsado <= 0){//se o idTreino for mal introduzido
                editTextPeso.setError(NUMERO_INVALIDO_DE_PESO_USADO);
                editTextPeso.requestFocus();
            } else {
                treinos.setPesoUsado(pesoUsado);//Introduzir sempre o pesoUsado
                dbTableTreino.insert(DBTableTreino.getContentValues(treinos));
                if (getPesoFromTable() != 0) {//Se o utilizador quiser alterar o pesoUsado,então tem de clicar outra vez para guardar, e nesse caso tem de ser feito o update para a tabela
                    dbTableTreino.update(DBTableTreino.getContentValues(treinos), DBTableTreino._ID + "=?", null);
                }
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
                //return true;
            } else {//se for bem introduzido
                treinos.setRepeticoes(repeticoes);
                dbTableTreino.insert(DBTableTreino.getContentValues(treinos));
                /*if (treinos.getRepeticoes() > 0) {
                    dbTableTreino.insert(DBTableTreino.getContentValues(treinos));
                }else if(treinos.getRepeticoes() != 0){//se na base de dados já estiver um valor
                    //values.put("Repetições",repeticoes);
                    //dbTableTreino.update(values, DBTableTreino._ID + "=?", new String[]{String.valueOf(treinos.getTreinoId())});
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
                //return true;
            }else {
                treinos.setSeries(series);
                dbTableTreino.insert(DBTableTreino.getContentValues(treinos));
            }
                /*if (treinos.getSeries() > 0) {
                    dbTableTreino.insert(DBTableTreino.getContentValues(treinos));
                }
            }else if (treinos.getSeries() != 0) {
                    values.put("Séries", series);
                    //dbTableTreino.update(values, DBTableTreino._ID + "=?", new String[]{String.valueOf(treinos.getTreinoId())});
                }
            }*/
        } catch (NumberFormatException e) {
            editTextSerie.setError(NUMERO_DE_SERIES_INVALIDO);
            editTextSerie.requestFocus();
            return;
        }
        //TODO em textView_Total em vez de estar treinos.getTotal_Reps,ir a buscar o total à tabela Treinos


        //textView_Total.setText("Total de Repetições:" + getRepeticoesFromTable() * getSeriesFromTable());
        //textView_Total.setText("Total de Repetições:",dbTableTreino.query())
    }

    private int getExercicioFromTable() {
        int ExercicioFromTable = 0;

        SQLiteDatabase db = dbTreinoOpenHelper.getReadableDatabase();
        String query_reps = "SELECT " + DBTableTreino.EXERCICIO + " FROM " + DBTableTreino.DATABASENAME_T + " WHERE " + DBTableTreino._ID + " =?";
        db.rawQuery(query_reps, new String[]{String.valueOf(ExercicioFromTable)});
        db.close();

        return ExercicioFromTable;
    }

    private int getPesoFromTable() {
        int PesoFromTable = 0;

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

    private int getSeriesFromTable() {
        int seriesFromTable = 0;

        SQLiteDatabase db = dbTreinoOpenHelper.getReadableDatabase();
        String query_reps = "SELECT " + DBTableTreino.SERIES + " FROM " + DBTableTreino.DATABASENAME_T + " WHERE " + DBTableTreino._ID + " =?";
        db.rawQuery(query_reps, new String[]{String.valueOf(seriesFromTable)});
        db.close();

        return seriesFromTable;
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
