package com.nouckelson.textfilesvideo2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button add_person,save_person;
    EditText name_id,surname_id;
    TextView showfile;
    ArrayList<Person> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name_id=findViewById(R.id.name_id);
        surname_id=findViewById(R.id.surname_id);
        showfile=findViewById(R.id.showfile);
        add_person=findViewById(R.id.add_person);
        save_person=findViewById(R.id.save_person);
        data=new ArrayList<Person>();
        add_person.setOnClickListener(this);
        save_person.setOnClickListener(this);
        readFile()
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.add_person:
                save();
                break;
            case  R.id.save_person:
                saveToFile();
                break;
        }
    }

    public void save(){
        if (!name_id.getText().toString().isEmpty() || !surname_id.getText().toString().isEmpty() ) {
            Person p=new Person(name_id.getText().toString(),surname_id.getText().toString());
            data.add(p);
            name_id.setText("");
            name_id.requestFocus();
            surname_id.setText("");
            Toast.makeText(this,"Add new person ",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this, "Field name and surname required ", Toast.LENGTH_LONG).show();
        }

    }

    private void saveToFile() {
            if(data.size()>=1){
                File file = new File(MainActivity.this.getFilesDir(), "folder");
                if (!file.exists()) {
                    file.mkdir();
                }
                try {
                    File gpxfile = new File(file, "text");
                    FileWriter writer = new FileWriter(gpxfile);
                    for (int i=0; i<data.size();i++){
                        writer.append(data.get(i).getName()+" "+data.get(i).getSurname());
                        writer.append('\n');
                    }
                    writer.flush();
                    writer.close();
                    Toast.makeText(MainActivity.this, "Data has been saved", Toast.LENGTH_LONG).show();
                    readFile();
                } catch (Exception e) { }
            }else{
                Toast.makeText(MainActivity.this, "Data required ", Toast.LENGTH_LONG).show();
            }

    }


    private String readFile() {
        File fileEvents = new File(MainActivity.this.getFilesDir()+"/folder/text");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine())!= null) {
                text.append(line);
                text.append('\n');
                showfile.setText(text.toString());
                data.add(new Person(text.toString(),""));
            }
            br.close();
        } catch (IOException e) { }
        String result = text.toString();
        return result;
    }
}
