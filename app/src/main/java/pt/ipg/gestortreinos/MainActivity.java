package pt.ipg.gestortreinos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    LinearLayout parent;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // createNewButton();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                createNewButton();





            }
        });
    }

    private void createNewButton() {
        //Create a new button. The button will intent to new activity.
        //That ativity will be to create a new training report
        String[]button_names = {"Treino 1","Treino 2","Treino 3"};
        LinearLayout parent = (LinearLayout) findViewById(R.id.l1_parent);
        int id = 0;
        int tag = id;
            b1 = new Button (MainActivity.this);
            b1.setId(id);
            b1.setText(button_names[id+1]);
            b1.setTag(tag);
            parent.addView(b1);
            b1.setOnClickListener(MainActivity.this);
            id++;
            //Toast.makeText(getApplicationContext(),""+id,Toast.LENGTH_SHORT ).show();
            //Erro com o height do linearlayout:Não aparece os primeiros butões

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
        if (id == R.id.action_settings) {
            return true;
        }//Adicionar aqui opções de eliminar treinos,alterar nome do treino,

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String tag = v.getTag().toString();

        if(tag.equals("0")){
            Toast.makeText(getApplicationContext(),"Treino 1",Toast.LENGTH_SHORT ).show();

        }else if(tag.equals("1")){
            Toast.makeText(getApplicationContext(),"Treino 2",Toast.LENGTH_SHORT ).show();
        }else if(tag.equals("2")){
            Toast.makeText(getApplicationContext(),"Treino 3",Toast.LENGTH_SHORT ).show();
        }

    }
}
