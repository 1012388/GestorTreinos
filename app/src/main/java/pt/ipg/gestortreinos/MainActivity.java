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
    CoordinatorLayout layoutMainParent;
    private  Button b1;
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
        String[]button_names = {"Treino 1","Treino 2","Treino 3"};
        //LinearLayout parent = (LinearLayout) findViewById(R.id.l1_parent);
        layoutMainParent = (CoordinatorLayout) findViewById(R.id.layoutMainParent);
        int id = 0;
        int tag = id;
        b1 = new Button (MainActivity.this);
        b1.setId(id);
        b1.setText(button_names[id]);
        b1.setTag(tag);
        layoutMainParent.addView(b1);
        b1.setOnClickListener(MainActivity.this);
        //id++;
        //Toast.makeText(getApplicationContext(),""+id,Toast.LENGTH_SHORT ).show();
        //Erro com o height do linearlayout:Não aparece os primeiros butões

    }

    @Override
    public void onClick(View v) {
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
    Intent intent = new Intent(this, TreinoActivity.class);

        EditText editTextData = (EditText) findViewById(R.id.editTextData);

        EditText editTextExercicio = (EditText) findViewById(R.id.editTextExercicio);

        EditText editTextRep = (EditText) findViewById(R.id.editTextRep);

        EditText editTextSerie = (EditText) findViewById(R.id.editTextSerie);

        TextView textView_Total = (TextView) findViewById(R.id.textView_Total);



        startActivity(intent);


    }
}
