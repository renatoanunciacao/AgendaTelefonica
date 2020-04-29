package br.com.upf.agendatelefonica;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private EditText nome;
    private EditText telefone;
    private ListView listaContatos;
    private List<Contato> contatos;
    private AgendaDbHelper dbHelper;
    private ArrayAdapter<Contato> contatoAdapter;
    private Contato contatoSelecionado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nome = findViewById(R.id.et_nome);
        telefone = findViewById(R.id.et_telefone);
        listaContatos = findViewById(R.id.lv_contatos);



        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTextWatcher =  new MaskTextWatcher(telefone, simpleMaskFormatter);
        telefone.addTextChangedListener(maskTextWatcher);


        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contatoSelecionado = contatos.get(position);
                nome.setText(contatoSelecionado.getNome());
                telefone.setText(contatoSelecionado.getTelefone());
            }
        });

    }



    public void salvarContato(View view) {
        if (!nome.getText().toString().equals("") &&
                !telefone.getText().toString().equals("")) {
            AgendaController ac = new AgendaController(this);
            String resultado = "";
            if (contatoSelecionado == null) {
                resultado = ac.insereData(nome.getText().toString(),
                        telefone.getText().toString());
            } else {
                resultado = ac.alteraRegistro(contatoSelecionado.getId(),
                        nome.getText().toString(),
                        telefone.getText().toString());

            }
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
            Log.i("inseriu",resultado);
//            atualizaListView();
        } else {
            Toast.makeText(this,
                    "Escreva um nome e um numero de contato",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void atualizaListView() {
        contatos = new ArrayList<Contato>();
        contatoAdapter = new ArrayAdapter<Contato>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text2, contatos);
        listaContatos.setAdapter(contatoAdapter);
        AgendaController ac = new AgendaController(this);
        Cursor cursor = ac.carregaDados();
        while(cursor.moveToNext()) {
            Contato linhaContato = new Contato(
                    cursor.getInt(cursor.getColumnIndex(AgendaDbHelper.ID)),
                    cursor.getString(cursor.getColumnIndex(AgendaDbHelper.COLUMN_NOME)),
                    cursor.getString(cursor.getColumnIndex(AgendaDbHelper.COLUMN_TELEFONE))
            );
            contatos.add(linhaContato);
        }
        nome.setText("");
        telefone.setText("");
        contatoSelecionado = null;

    }
    public void removerContato(View view) {
        if (contatoSelecionado != null) {
            AgendaController ac = new AgendaController(this);
            ac.deletaRegistro(contatoSelecionado.getId());
            Toast.makeText(this,"Contato removido",Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this,"Nenhum contato  removido",Toast.LENGTH_SHORT).show();
            atualizaListView();
    }


}
