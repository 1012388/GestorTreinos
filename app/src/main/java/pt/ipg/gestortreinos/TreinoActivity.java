package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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
    public int id =0;
    private EditText EditText1;
    Treinos treinos = new Treinos();
    final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    public int idTreino=0;
    public String exercicio ="";
    public int repeticoes=0;
    public int series=0;
    public int pesoUsado=0;
    public int total_Reps = 0;
    private DBTableTreino dbTableTreino;
    ContentValues values = new ContentValues();

    private static String[] REPETICOES_COLUMN = new String[] {"repetições"};
    private static String[] SERIES_COLUMN = new String[] {"séries"};


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
                Snackbar.make(view, "A guardar...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                   createTreino();
            }
        });

    }

    private void createTreino() {
        Intent intent = getIntent();

        int idTreino = 0;
        String exercicio = "";
        int repeticoes = 0;
        int series = 0;
        int pesoUsado = 0;
        int total_Reps = 0;

        EditText editTextDia = (EditText) findViewById(R.id.editTextDia);
        EditText editTextExercicio = (EditText) findViewById(R.id.editTextExercicio);
        EditText editTextRep = (EditText) findViewById(R.id.editTextRep);
        EditText editTextSerie = (EditText) findViewById(R.id.editTextSerie);
        EditText editTextPeso = (EditText) findViewById(R.id.editTextPeso);
        TextView textView_Total = (TextView) findViewById(R.id.textView_Total);

        // TODO: 10/06/2018 VERIFICAR A ENTRADA NA BASE DE DADOS,PARA COMPARAR SE EXISTEM OS DADOS OU NÃO.


        try {
            idTreino = Integer.parseInt(editTextDia.getText().toString());
        } catch (NumberFormatException e) {
            editTextDia.setError(DIA_INVALIDO);
            editTextDia.requestFocus();
            return;
        }

        try {
            pesoUsado = Integer.parseInt(editTextPeso.getText().toString());
            if(pesoUsado <= 0){//se o idTreino for mal introduzido
                editTextPeso.setError(NUMERO_INVALIDO_DE_PESO_USADO);
                editTextPeso.requestFocus();
                //return true;
            } else {
                if (treinos.getPesoUsado() != 0){//se o peso dado pela BD for diferente de 0 ENTÃO É PORQUE É PARA ACTUALIZAR
                    values.put("Peso Usado", pesoUsado);
                    dbTableTreino.update(values,DBTableTreino._ID+"=?",new String[] {String.valueOf(treinos.getId())});
                }
            }
        } catch (NumberFormatException e) {
            editTextPeso.setError(NUMERO_INVALIDO_DE_PESO_USADO);
            editTextPeso.requestFocus();
            return;
        }

        try {
            repeticoes = Integer.parseInt(editTextRep.getText().toString());
            if(repeticoes <= 0){//se o idTreino for mal introduzido
                editTextRep.setError(NUMERO_DE_REPETICOES_INVALIDO);
                editTextRep.requestFocus();
                //return true;
            }else{//se for bem introduzido
                if(treinos.getRepeticoes() != 0){//se na base de dados já estiver um valor
                    values.put("Repetições",repeticoes);
                    dbTableTreino.update(values,DBTableTreino._ID+"=?",new String[] {String.valueOf(treinos.getId())});
                }
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
                if (treinos.getSeries() != 0) {
                    values.put("Séries", series);
                    dbTableTreino.update(values, DBTableTreino._ID + "=?", new String[]{String.valueOf(treinos.getId())});
                }
            }
        } catch (NumberFormatException e) {
            editTextSerie.setError(NUMERO_DE_SERIES_INVALIDO);
            editTextSerie.requestFocus();
            return;
        }

/*
        DBTableTreino.getContentValues(treinos);

        Cursor query_reps = dbTableTreino.query(REPETICOES_COLUMN,DBTableTreino._ID+"=?"
                ,new String[] {String.valueOf(treinos.getId())},null,null,null);

        Cursor query_series = dbTableTreino.query(SERIES_COLUMN,DBTableTreino._ID+"=?"
               ,new String[] {String.valueOf(treinos.getId())},null,null,null);



        if(treinos.getTotal_Reps(query_reps.getInt(3),query_series.getInt(4)) !=0){
                values.put("Total de Repetições",total_Reps);
                dbTableTreino.update(values,DBTableTreino._ID+"=?"
                                    ,new String[] {String.valueOf(treinos.getId())});
        }
*/
        //TODO em textView_Total em vez de estar treinos.getTotal_Reps,ir a buscar o total à base de dados.
    textView_Total.setText("Total de Repetições:"+treinos.getTotal_Reps(repeticoes,series));
    }

    /*private boolean actualizarExercicio(int repeticoes, int series, int pesoUsado,int total_Reps
            ,EditText editTextDia,EditText editTextRep,EditText editTextSerie,EditText editTextPeso) {

        DBTableTreino.getContentValues(treinos);

        if(repeticoes <= 0){//se o idTreino for mal introduzido
            editTextRep.setError(DIA_INVALIDO);
            editTextRep.requestFocus();
            return true;
        }else{//se for bem introduzido
            if(treinos.getRepeticoes() != 0){//se na base de dados já estiver um valor
                values.put("Repetições",repeticoes);
                dbTableTreino.update(values,DBTableTreino._ID+"=?",new String[] {String.valueOf(treinos.getId())});
            }
        }

        if(series <= 0){//se o idTreino for mal introduzido
            editTextDia.setError(DIA_INVALIDO);
            editTextDia.requestFocus();
            return true;
        }else {
            if (treinos.getSeries() != 0) {
                values.put("Séries", series);
                dbTableTreino.update(values, DBTableTreino._ID + "=?", new String[]{String.valueOf(treinos.getId())});
            }
        }

        if(pesoUsado <= 0){//se o idTreino for mal introduzido
            editTextPeso.setError(DIA_INVALIDO);
            editTextPeso.requestFocus();
            return true;
        } else {
            if (treinos.getPesoUsado() != 0){//se o peso dado pela BD for diferente de 0 ENTÃO É PORQUE É PARA ACTUALIZAR
                values.put("Peso Usado", pesoUsado);
                dbTableTreino.update(values,DBTableTreino._ID+"=?",new String[] {String.valueOf(treinos.getId())});
            }
        }

        if(total_Reps <= 0){//se o idTreino for mal introduzido
            editTextPeso.setError(DIA_INVALIDO);
            editTextPeso.requestFocus();
            return true;
        }else {
            if(treinos.getRepeticoes() !=0){
                values.put("Total de Repetições",total_Reps);
                dbTableTreino.update(values,DBTableTreino._ID+"=?",new String[] {String.valueOf(treinos.getId())});
            }
        }

        return true;
    }

    private boolean inserirExercicio(int idTreino, int repeticoes, int series, int pesoUsado
            , EditText editTextDia, EditText editTextRep, EditText editTextSerie, EditText editTextPeso) {

        if(idTreino <= 0){//se o idTreino for mal introduzido
            editTextDia.setError(DIA_INVALIDO);
            editTextDia.requestFocus();
            return true;
        }else {//se for bem introduzido e não existir a BD
            //Inserir na BD
            values.put("Treino Id",idTreino);
            dbTableTreino.insert(values);
        }

        if(pesoUsado <= 0 || pesoUsado > 999){//se o pesoUsado for mal introduzido
            editTextPeso.setError(NUMERO_INVALIDO_DE_PESO_USADO);
            editTextPeso.requestFocus();
            return true;
        }else{//se for bem introduzido
            values.put("Peso Usado",pesoUsado);
            dbTableTreino.insert(values);
        }

        if(repeticoes <= 0 || repeticoes > 999){
            editTextRep.setError(NUMERO_DE_REPETICOES_INVALIDO);
            editTextRep.requestFocus();
            return true;
        }else{
            values.put("Número de repetições",repeticoes);
            dbTableTreino.insert(values);
        }

        if(series <= 0 || series > 99){
            editTextSerie.setError(NUMERO_DE_SERIES_INVALIDO);
            editTextSerie.requestFocus();
            return true;
        }else{
            values.put("Número de séries",series);
            dbTableTreino.insert(values);
        }
        return false;
    }
*/

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

   /* private void adicionarExercicio() {
        String[]field_names = {"Exercicio","Peso","Repetições","Séries","TotalRepetições"};
        LinearLayout linearLayoutTreino = (LinearLayout) findViewById(R.id.linearLayoutTreino);
        //CoordinatorLayout layoutTreinoParent = (CoordinatorLayout) findViewById(R.id.layoutTreinoParent);
        int tag = 0;
        int total = treinos.getTotal_Reps(treinos.setRepeticoes(3),treinos.setSeries(3));//
        for (int i = 0; i < 4 ; i++) {
            tag = i;
            EditText1 = new EditText(TreinoActivity.this);
            EditText1.setId(i);
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
        if(EditText1.getId()== 0) {
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

    private void  renamePractice(){
        //alterar o nome do botão
    }



}
