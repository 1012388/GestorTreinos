package pt.ipg.gestortreinos;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {//implements OnClickListener{
    public int conta = 0;
    private  Button b1;
    int tag_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                seClicado(view);
                createNewPraticeButton();
            }

        });
    }

    private void createNewPraticeButton() {//Cria um botão que ao ser presionado abre a atividade Treinos
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        b1 = new Button(MainActivity.this);
        tag_id = conta;
        b1.setId(tag_id);
        b1.setText("Treino" + tag_id);
        b1.setTag(tag_id);
        b1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                showTreinos();//Abrir Treino Activity
             }
        });

        tag_id++;
        linearLayout.addView(b1);
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
        if (id == R.id.action_Adicionar) {//Adicionar treino
            createNewPraticeButton();
            //conta++;
            //Toast.makeText(getApplicationContext(),"Adicionar treino:"+conta,Toast.LENGTH_SHORT ).show();

        }
        return super.onOptionsItemSelected(item);
    }


    public void seClicado(View v) {
        conta++;
        Snackbar.make(v, "Adicionar treino:" + conta, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


        //return conta;
    }

    private void showTreinos(){
    //Passar para o Treino Actitivity o valor de quantas vezes o botão foi clicado
    /*
        intent.putExtra(TREINO_ID,idTreino);
        intent.putExtra(NOME_DO_EXERCICIO,exercicio);
        intent.putExtra(NUMERO_DE_REPETICOES,repeticoes);
        intent.putExtra(NUMERO_DE_SERIES,series);
        intent.putExtra(NUMERO_DO_PESO_USADO,pesoUsado);
    */
        Intent intent = new Intent(this, TreinoActivity.class);
        //intent.putExtra("Vezes",getContaVezes());//contador serve para saber o número do id da tag para criar os botões por ordem crescente
        startActivity(intent);
    }
}
