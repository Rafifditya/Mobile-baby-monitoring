package rafif.com.baymap.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rafif.com.baymap.Controller.SessionManager;
import rafif.com.baymap.R;

public class MainActivity extends AppCompatActivity {

    SessionManager session;
    TextView nama;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
        logout = (Button) findViewById(R.id.logoutbutton);

        nama = (TextView) findViewById(R.id.nama);
        nama.setText(session.getKeyUsername());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
            }
        });
    }
}
