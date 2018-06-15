
package pt.ipg.gestortreinos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public static final String VEZES_QUE_CLICADO_EM_ADICIONAR_TREINO = "Vezes que clicado em adicionar treino";
    public static final String TREINO_ID = "Treino ID";
    public int conta = 0;
    private  Button b1;
    int tag_id = 0;
    Treinos treino = new Treinos();
    private DBTableTreino dbTreino;
    ContentValues values = new ContentValues();
    SQLiteOpenHelper dbTreinoOpenHelper;

    DiasSemana diasSemana = new DiasSemana();

    //ListView listView;
    //ArrayList<Button> arrayList = new ArrayList<Button>();

    //ArrayAdapter <Button> adapter = new ArrayAdapter<Button>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //listView.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //seClicado(view);
                seClicado();

                createNewPraticeButton();
            }

        });
    }

    private void createNewPraticeButton() {//Cria um botão que ao ser presionado abre a atividade Treinos

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        //ScrollView scrollView = (ScrollView) findViewById(R.id.scroolView);

        //ListView listView = (ListView) findViewById(R.id.listView);

        b1 = new Button(MainActivity.this);
        tag_id = conta;
        //treino.setTreinoId(tag_id);
        //tag_id = seClicado();

        b1.setId(tag_id);
        b1.setText("Treino" + tag_id);
        b1.setTag(tag_id);
        tag_id++;

        treino.setTreinoId(tag_id);

        DBTreinoOpenHelper dbTreinoOpenHelper = new DBTreinoOpenHelper(getApplicationContext());
        SQLiteDatabase db = dbTreinoOpenHelper.getWritableDatabase();
        DBTableTreino dbTableTreino = new DBTableTreino(db);

        dbTreino.insert(DBTableTreino.getContentValues(treino));

        b1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                showTreinos();//Abrir Treino Activity

            }
        });

        //scrollView.addView(b1);

        linearLayout.addView(b1);

        //arrayList.add(b1);//Adicionar cada butão criado ao arrayList
        //adapter.notifyDataSetChanged();
        //listView.setAdapter(adapter);//to associate an adapter with the list
        //db.close();
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

        if (id == R.id.action_Eliminar) {//Adicionar treino
            //deleteTreinoButton();
            //conta++;
            Toast.makeText(getApplicationContext(), "A eliminar o treino:" + tag_id, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    public int /*void*/ seClicado(/*View v*/) {
        conta++;
        //Snackbar.make(v, "Adicionar treino:" + treino.getTreinoId(), Snackbar.LENGTH_LONG)
        // .setAction("Action", null).show();
        Toast.makeText(getApplicationContext(), "Adicionar treino:" + treino.getTreinoId(), Toast.LENGTH_SHORT).show();

        return conta;
    }

    private void showTreinos(){
    //Passar para o Treino Actitivity o valor de quantas vezes o botão foi clicado
        Intent intent = new Intent(this, TreinoActivity.class);

        intent.putExtra(TREINO_ID, treino.getTreinoId());



        startActivity(intent);
    }


}
