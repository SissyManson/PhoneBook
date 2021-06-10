package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDelete extends DBActivity {
    protected EditText editName, editNumber, editEmail,editDescription;
    protected Button btnUpdate, btnDelete;
    protected String ID;

    private  void BackToMain(){
        finishActivity(200);
        Intent i = new Intent(UpdateDelete.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        editName=findViewById(R.id.editName);
        editNumber=findViewById(R.id.editNumber);
        editEmail=findViewById(R.id.editEmail);
        editDescription=findViewById(R.id.editDescription);

        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);

        Bundle b = getIntent().getExtras();

        if(b!= null){
            ID = b.getString("ID");
            editName.setText(b.getString("Name"));
            editNumber.setText(b.getString("Tel"));
            editEmail.setText(b.getString("Email"));
            editDescription.setText(b.getString("Description"));
        }

        btnDelete.setOnClickListener(view->{
            ExecSQL("DELETE FROM CONTACTS WHERE ID = ?",
                    new Object[]{ID},
                    new OnQuerySuccess() {
                        @Override
                        public void OnSuccess() {
                            Toast.makeText(UpdateDelete.this,
                                    "Your delete was successful!",
                                    Toast.LENGTH_LONG).show();
                            BackToMain();
                        }
                    },
                    new OnError() {
                        @Override
                        public void OnQueryError(String error) {

                            Toast.makeText(UpdateDelete.this,
                                    error,Toast.LENGTH_LONG).show();
                        }
                    }
            );
        });

        btnUpdate.setOnClickListener(view->{
            ExecSQL("UPDATE CONTACTS SET " +
                            "Name = ?, " +
                            "Tel = ?, " +
                            "Email = ?, " +
                            "Description = ? " +
                            "WHERE ID = ? ",
                    new Object[]{
                            editName.getText().toString(),
                            editNumber.getText().toString(),
                            editEmail.getText().toString(),
                            editDescription.getText().toString(),
                            ID
                    },
                    new OnQuerySuccess() {
                        @Override
                        public void OnSuccess() {
                            Toast.makeText(UpdateDelete.this,
                                    "Your update was successful!",
                                    Toast.LENGTH_LONG).show();
                            BackToMain();
                        }
                    },
                    new OnError() {
                        @Override
                        public void OnQueryError(String error) {
                            Toast.makeText(UpdateDelete.this,
                                    error,Toast.LENGTH_LONG).show();
                        }
                    }
            );
        });
    }
}