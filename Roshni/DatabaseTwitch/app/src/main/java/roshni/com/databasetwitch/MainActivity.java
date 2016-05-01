package roshni.com.databasetwitch;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SQLController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbController=new SQLController(this);
        dbController.open();
        dbController.insert("roshni","bh-138");

    }

    public void fetchDetails() {
        TextView dt1=(TextView)findViewById(R.id.textView);
        TextView dt2=(TextView)findViewById(R.id.textView2);
        Cursor cursor = dbController.fetch();

    }




}
