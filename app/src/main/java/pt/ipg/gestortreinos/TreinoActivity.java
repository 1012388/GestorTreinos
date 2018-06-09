package pt.ipg.gestortreinos;

import android.content.Intent;
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

public class TreinoActivity extends AppCompatActivity {
    public static final String DIA_INVALIDO = "Dia Inválido";
    public static final String NUMERO_DE_REPETICOES_INVALIDO = "Número de repetições inválido";
    public static final String NUMERO_DE_SERIES_INVALIDO = "Número de séries inválido";
    public static final String NUMERO_INVALIDO_DE_PESO_USADO = "Número inválido de peso usado";
    public static final String TREINO_ID = "Treino id";
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
                /*Algoritmo para guardar dados:
                * Se existirem dados na coluna referente ao treino do momento então:
                *   Actualizar os dados;
                * Se não, então:
                *   Inserir os dados;
                *   */
                createTreino();
            }
        });

    }

    private void createTreino() {
        Intent intent = getIntent();

        int idTreino=0;
        String exercicio ="";
        int repeticoes=0;
        int series=0;
        int pesoUsado=0;
        int total_Reps = 0;

        EditText editTextDia = (EditText) findViewById(R.id.editTextDia);
        EditText editTextExercicio = (EditText) findViewById(R.id.editTextExercicio);
        EditText editTextRep = (EditText) findViewById(R.id.editTextRep);
        EditText editTextSerie = (EditText) findViewById(R.id.editTextSerie);
        EditText editTextPeso = (EditText) findViewById(R.id.editTextPeso);
        TextView textView_Total = (TextView) findViewById(R.id.textView_Total);

        try {
            idTreino = Integer.parseInt(editTextDia.getText().toString());
        } catch (NumberFormatException e) {
            editTextDia.setError(DIA_INVALIDO);
            editTextDia.requestFocus();
            return;
        }

        try {
            repeticoes = Integer.parseInt(editTextRep.getText().toString());
        } catch (NumberFormatException e) {
            editTextRep.setError(NUMERO_DE_REPETICOES_INVALIDO);
            editTextRep.requestFocus();
            return;
        }

        try {
            series = Integer.parseInt(editTextSerie.getText().toString());
        } catch (NumberFormatException e) {
            editTextSerie.setError(NUMERO_DE_SERIES_INVALIDO);
            editTextSerie.requestFocus();
            return;
        }

        try {
            pesoUsado = Integer.parseInt(editTextPeso.getText().toString());
        } catch (NumberFormatException e) {
            editTextPeso.setError(NUMERO_INVALIDO_DE_PESO_USADO);
            editTextPeso.requestFocus();
            return;
        }

        if(idTreino <= 0){
            editTextDia.setError(DIA_INVALIDO);
            editTextDia.requestFocus();
            return;
        }
        
        if(pesoUsado <= 0 || pesoUsado > 999){
            editTextPeso.setError(NUMERO_INVALIDO_DE_PESO_USADO);
            editTextPeso.requestFocus();
            return;
        }
        if(repeticoes <= 0 || repeticoes > 999){
            editTextRep.setError(NUMERO_DE_REPETICOES_INVALIDO);
            editTextRep.requestFocus();
            return;
        }

        if(series <= 0 || series > 99){
            editTextSerie.setError(NUMERO_DE_SERIES_INVALIDO);
            editTextSerie.requestFocus();
            return;
        }



        /*if (idTreino == 0 || repeticoes == 0 || series == 0 ||pesoUsado == 0) {
            finish();
            return;
        }*/

        Treinos treinos = new Treinos();//(idTreino, exercicio, repeticoes, series);

        textView_Total.setText(String.format("%d",treinos.getTotal_Reps(repeticoes,series)));
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
