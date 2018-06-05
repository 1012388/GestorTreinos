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

public class MainActivity extends AppCompatActivity implements OnClickListener{
    private int conta=0;
    CoordinatorLayout layoutMainParent;
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
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                Button buttonTreino = (Button) findViewById(R.id.buttonTreino);
                createNewPractice();


            }
        });
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
        if (id == R.id.action_Adicionar) {//Adicionar treino
            createNewPractice();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNewPractice() {
        //Create a new button. The button will intent to new activity.
        //That ativity will be to create a new training report

        LinearLayout parent = (LinearLayout) findViewById(R.id.l1_parent);

        String[]button_names = {"Treino 1","Treino 2","Treino 3"};
        b1 = new Button (MainActivity.this);
        conta=tag_id;
        b1.setId(tag_id);
        b1.setText(button_names[tag_id]);

        b1.setTag(tag_id);
        parent.addView(b1);
        b1.setOnClickListener(MainActivity.this);
        //tag_id++;
    }

    @Override
    public void onClick(View v) {

        conta++;
        String tag = v.getTag().toString();

        if(tag.equals("0")){
            Toast.makeText(getApplicationContext(),"Treino 1",Toast.LENGTH_SHORT ).show();
            showTreinos();//abrir a TreinoActivity
        }else if(tag.equals("1")){
            showTreinos();
            Toast.makeText(getApplicationContext(),"Treino 2",Toast.LENGTH_SHORT ).show();
        }else if(tag.equals("2")){
            showTreinos();
            Toast.makeText(getApplicationContext(),"Treino 3",Toast.LENGTH_SHORT ).show();
        }
    }

    private void showTreinos(){
        Toast.makeText(getApplicationContext(),"Treino 2",Toast.LENGTH_SHORT ).show();
        Intent intent = new Intent(this, TreinoActivity.class);

    //Passar para o Treino Actitivity o valor de quantas vezes o botão foi clicado

        intent.putExtra("Vezes",conta);//contador serve para saber o número do id da tag para criar os botões por ordem crescente

    /*

        intent.putExtra(TREINO_ID,idTreino);
        intent.putExtra(NOME_DO_EXERCICIO,exercicio);
        intent.putExtra(NUMERO_DE_REPETICOES,repeticoes);
        intent.putExtra(NUMERO_DE_SERIES,series);
        intent.putExtra(NUMERO_DO_PESO_USADO,pesoUsado);


    */

        //startActivity(intent);


    }
}
