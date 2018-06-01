package pt.ipg.gestortreinos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TreinoActivity extends AppCompatActivity {
    public int id =0;
    private EditText EditText1;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();

        TextView textViewTotal= (TextView) findViewById(R.id.textView_Total);

        //Receber da classe o getRepetições
        //textViewTotal.setText(""+total);


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
            deleteTreino();
        }else if(id== R.id.action_AdicionarEx) {//Alterar nome do treino
            adicionarExercicio();
        }
        return super.onOptionsItemSelected(item);
    }

    private void adicionarExercicio() {
        String[]field_names = {"Exercicio","Peso","Repetições","Séries","Total Repetiçoes"};
        //LinearLayout parent = (LinearLayout) findViewById(R.id.l1_parent);
        CoordinatorLayout layoutTreinoParent = (CoordinatorLayout) findViewById(R.id.layoutTreinoParent);

        for (int i = 0; i <4 ; i++) {
            int tag = id;
            EditText1 = new EditText(TreinoActivity.this);
            EditText1.setId(id);
            EditText1.setText("");
            EditText1.setTag(tag);
            layoutTreinoParent.addView(EditText1);

        }

    }

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
