package sg.edu.np.mad.madpractical5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DatabaseHandler databaseHandler = new DatabaseHandler(this,null,null,1);
        TextView tvName = findViewById(R.id.textView2);     //'userTitle'
        TextView tvDescription = findViewById(R.id.textView3);  // 'user.description'
        Button btnFollow = findViewById(R.id.button1);  //toggle 'followed' boolean

        Intent receivingID = getIntent();
        String username = receivingID.getStringExtra("name");
        String userDescription = receivingID.getStringExtra("description");
        int userId = receivingID.getIntExtra("id",1);
        boolean userFollowed = receivingID.getBooleanExtra("followed", true);
        User user = new User(username, userDescription, userId, userFollowed );


        tvName.setText(user.getName());
        tvDescription.setText(user.getDescription());
        btnFollow.setText(user.getFollowed() ? "Unfollow" : "Follow");
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newFollowedStatus = !user.getFollowed();
                user.setFollowed(newFollowedStatus);
                databaseHandler.updateUser(user.getId(), newFollowedStatus);

                btnFollow.setText(newFollowedStatus ? "Unfollow" : "Follow");
                Toast.makeText(getApplicationContext(), newFollowedStatus ? "Followed" : "Unfollowed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}