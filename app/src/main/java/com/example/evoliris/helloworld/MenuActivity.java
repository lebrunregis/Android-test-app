package com.example.evoliris.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private static final int CODE1 = 411;
    private static final int CODE2 = 667;
    private static String USERNAME = "Not set";
    private ArrayList<String> liste;
    private ArrayAdapter<String> menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);


        initAccueil(savedInstanceState);
        initView();
    }

    protected void initAccueil(Bundle savedInstanceState) {
        TextView textView = (TextView) findViewById(R.id.tV_login);
        if (getIntent().hasExtra("login")) {
            USERNAME = getIntent().getStringExtra("login");
        } else {
            USERNAME = "Inconnu";
        }
        textView.setText(USERNAME);
    }

    protected void initView() {
// Construct the data source

        //  ArrayList<User> arrayOfUsers = new ArrayList<User>();
        liste = new ArrayList<String>();
// Create the adapter to convert the array to views

        //  UsersAdapter adapter = new UsersAdapter(this, arrayOfUsers);
        menu = new ArrayAdapter<String>(this, R.layout.menu_button, R.id.button_menu, liste);
// Attach the adapter to a ListView

        //   ListView listView = (ListView) findViewById(R.id.lvItems);
        ListView list_view_menu = (ListView) findViewById(R.id.menu);
        //  listView.setAdapter(adapter);
        list_view_menu.setAdapter(menu);

        liste.add("Activité 1");
        liste.add("Activité 2");
        liste.add("Activité 3");
        liste.add("Activité 4");
        liste.add("Draw");
        liste.add("Phone");
        liste.add("Search");
        liste.add("Back");

        list_view_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = menu.getItem(position);
                switch(position){
                    case 0:startActivity1(); return;
                    case 1:startActivity2(); return;
                    case 2:startActivity3(); return;
                    case 3:startActivity4(); return;
                    case 4:startDrawing(); return;
                    case 5:startCall(); return;
                    case 6:startSearch(); return;
                    default:retour();return;
                }
            }
        });
    }

    protected void retour() {

        finish();
    }

    private void startActivity1() {
        Intent intent = new Intent(this, Activity1.class);
        startActivityForResult(intent, CODE1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CODE1:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, R.string.ok, Toast.LENGTH_LONG).show();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, R.string.retour, Toast.LENGTH_LONG).show();
                }
                break;
            case CODE2:
                if (resultCode == RESULT_OK && data != null) {
                    int coul = data.getIntExtra("bg", 0);
                    setBgColor(coul);
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, R.string.retour, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Toast.makeText(this, "Probleme", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void startActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivityForResult(intent, CODE2);
    }

    private void startActivity3() {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }

    private void startActivity4() {
        Intent intent = new Intent(this, TextEditorActivity.class);
        startActivity(intent);
    }

    private void startDrawing() {
        Intent intent = new Intent(this, DrawActivity.class);
        startActivity(intent);
    }

    private void startCall() {
        Intent intent = new Intent(this, PhoneActivity.class);
        startActivity(intent);
    }

    private void startSearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    protected void setBgColor(int couleur) {
        View activite = (View) findViewById(R.id.menu);
        Toast.makeText(this, "Changement couleur", Toast.LENGTH_LONG).show();
        activite.setBackgroundColor(couleur);
    }
}
