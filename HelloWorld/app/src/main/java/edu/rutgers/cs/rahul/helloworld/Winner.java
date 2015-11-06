package edu.rutgers.cs.rahul.helloworld;


        import android.app.Activity;
        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.Button;
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
        Button statButton = (Button) findViewById(R.id.statButton);
        StudentRepo repo = new StudentRepo(this);

        student=repo.compareTime(sqLiteDatabase);
        String winner1=student.name;
        winner.setText(winner1);

        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StatActivity.class);
                startActivity(intent);
            }
        });


    }

}
