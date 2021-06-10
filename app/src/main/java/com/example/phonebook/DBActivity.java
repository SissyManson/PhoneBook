package com.example.phonebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DBActivity extends AppCompatActivity {
    protected  void SelectSQL(String SelectQ, String[] args, OnSelectSuccess success) throws Exception{
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath()+"/contacts.db",null);
        db.beginTransaction();
        Cursor cursor=db.rawQuery(SelectQ, args);

        while(cursor.moveToNext()){
            String ID = cursor.getString(cursor.getColumnIndex("ID"));
            String Name = cursor.getString(cursor.getColumnIndex("Name"));
            String Tel = cursor.getString(cursor.getColumnIndex("Tel"));
            String Email = cursor.getString(cursor.getColumnIndex("Email"));
            String Description = cursor.getString(cursor.getColumnIndex("Description"));

            success.OnElementSelected(ID, Name, Tel, Email, Description);
        }
        db.endTransaction();
        db.close();
    }

    protected  void initDB(){
        ExecSQL(
                "CREATE TABLE if not exists CONTACTS(" +
                        "ID integer PRIMARY KEY AUTOINCREMENT, " +
                        "Name text not null, " +
                        "Tel text not null, " +
                        "Email text not null, " +
                        "Description text not null, " +
                        "unique(Tel)"+
                        ");",
                null,
                ()->{
                    Toast.makeText(this,"DB was created successfully!",Toast.LENGTH_LONG).show();
                }
                ,
                (error)-> {
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                }
        );
    }

    protected void ExecSQL(String SQL, Object[] args, OnQuerySuccess success, OnError err){
        SQLiteDatabase db = null;

        try{
            db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath()+"/contacts.db",null);

            if(args==null){
                db.execSQL(SQL);
            }else{
                db.execSQL(SQL,args);
            }
            success.OnSuccess();

        }catch (Exception e){
            err.OnQueryError(e.getMessage().toString());

        }finally {
            if(db!=null){
                db.close();
            }
        }
    }

    protected  interface OnQuerySuccess{
        public  void OnSuccess();
    }

    protected  interface  OnError{
        public  void OnQueryError(String error);
    }

    protected  interface  OnSelectSuccess{
        public  void OnElementSelected(String ID, String Name, String Tel, String Email, String Description);
    }
}
