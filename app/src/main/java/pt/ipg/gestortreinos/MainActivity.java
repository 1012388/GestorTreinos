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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {//implements OnClickListener{
    public int conta =0;
    CoordinatorLayout layoutMainParent;
    private  Button b1;
    int tag_id = 1;
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
                Snackbar.make(view, "Adicionar treino:"+tag_id, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                createNewPratice();
                //tag_id++;
            }

        });

       /* Button buttonTreino = (Button) findViewById(R.id.buttonTreino);
        buttonTreino.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "O button Treino 1 está a funcionar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showTreinos();
            }
        });*/



    }

    private void createNewPratice() {//Cria um botão que ao ser presionado abre a atividade Treinos
        ConstraintLayout constraintLayout= (ConstraintLayout) findViewById(R.id.constraintLayout);

        String[]button_names = {"Treino 1","Treino 2","Treino 3"};
        b1 = new Button(MainActivity.this);
        tag_id = conta;
        b1.setId(tag_id);
        b1.setText(button_names[tag_id]);

        b1.setTag(tag_id);



        b1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Tag"+tag_id, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showTreinos();//Abrir Treino Activity

                seClicado(b1);
            }
        });
        tag_id++;
        constraintLayout.addView(b1);//,params);
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
            createNewPratice();
            Toast.makeText(getApplicationContext(),"O botão adicionar está a funcionar",Toast.LENGTH_SHORT ).show();
            //tag_id++;
        }
        return super.onOptionsItemSelected(item);
    }



   public void seClicado(View v) {
       String tag = v.getTag().toString();
       for (int i = 0; i < Integer.parseInt(tag); i++) {
           if (tag.equals(i)) {
               Toast.makeText(getApplicationContext(), "Treino" + i, Toast.LENGTH_SHORT).show();
               conta++;
               //abre a TreinoActivity com e passa para a atividade o valor da tag
           }
       }
   }

    private void showTreinos(){
        //Toast.makeText(getApplicationContext(),"Treino 2",Toast.LENGTH_SHORT ).show();


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
