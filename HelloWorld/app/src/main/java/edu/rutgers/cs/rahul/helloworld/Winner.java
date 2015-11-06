package edu.rutgers.cs.rahul.helloworld;


        import android.app.Activity;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.TextView;
        import android.app.Activity;

public class Winner extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView winner = (TextView) findViewById(R.id.winner);
        TextView result=(TextView)findViewById(R.id.winnerresult);
        Student student=new Student();
        StudentRepo repo = new StudentRepo(this);

        student=repo.compareTime(sqLiteDatabase);
        String winner1=student.name;
        winner.setText(winner1);




    }

}
