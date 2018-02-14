package ko.alex.enigmachat2;

        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.format.DateFormat;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.firebase.ui.auth.AuthUI;
        import com.firebase.ui.database.FirebaseListAdapter;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.ArrayList;
        import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // https://code.tutsplus.com/tutorials/how-to-create-an-android-chat-app-using-firebase--cms-27397
    private FirebaseListAdapter<ChatMessage> adapter;
    int SIGN_IN_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true); // https://firebase.google.com/docs/database/android/offline-capabilities

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();

            // Load chat room contents
            displayChatMessages();
        }


    }


    public void fabClicked(View view){
        EditText input = findViewById(R.id.input);
        Encrypt encryptedInput = new Encrypt(); // instantiate Encrypt object, then can call methods
        String asdf = encryptedInput.Encrypt(input.getText().toString());

        // Read the input field and push a new instance
        // of ChatMessage to the Firebase database
        FirebaseDatabase.getInstance()
                .getReference()
                .push()
                .setValue(new ChatMessage(asdf,
                        FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getDisplayName())
                );

        // Clear the input
        input.setText("");
    }



    private void displayChatMessages() {
        ListView listOfMessages = findViewById(R.id.list_of_messages);

//        // start experiment
//        listOfMessages.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) { // void rather than boolean, https://stackoverflow.com/questions/14340579/android-removing-item-from-listview-on-long-click
//                Toast.makeText(getApplicationContext(),"Hello ",adapter.get(position),Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),"Hello ",Toast.LENGTH_LONG).show(); // https://stackoverflow.com/questions/41313200/how-to-delete-selected-child-on-firebase-database-android
//                return false;
//            }
//        });

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference()) {   // FirebaseDatabase.getInstance().getReference() is this an ArrayList?
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);







        // https://stackoverflow.com/questions/43165751/retrieving-single-value-from-firebase-child-in-android
        // http://takeip.com/best-way-to-retrieveformat-data-using-firebase-java-api.html
        // https://groups.google.com/forum/#!msg/firebase-talk/z6ICgQL6Vcw/A8mNWDUxAwAJ
        // https://stackoverflow.com/questions/30933328/how-to-convert-firebase-data-to-java-object
        // start experiment https://www.101apps.co.za/index.php/item/182-firebase-realtime-database-tutorial.html
        listOfMessages.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//            DatabaseReference ref = mDatabase.child("enigmachat2");


            // https://www.firebase.com/docs/android/guide/retrieving-data.html
            // using a datasnapshot
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot snapshot) {
//                    System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
//                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                        BlogPost post = postSnapshot.getValue(BlogPost.class);
//                        System.out.println(post.getAuthor() + " - " + post.getTitle());
//                    }
//                }
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//                    System.out.println("The read failed: " + firebaseError.getMessage());
//                }
//            });



            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) { // void rather than boolean, https://stackoverflow.com/questions/14340579/android-removing-item-from-listview-on-long-click
//                ChatMessage chatMessage = new ChatMessage();

//                Toast.makeText(getApplicationContext(),"GET CLASS " + FirebaseDatabase.getInstance().getReference().getClass(),Toast.LENGTH_SHORT).show(); // myFriends.get(position) myFriends is an ArrayList
//                Toast.makeText(getApplicationContext(),"GET KEY " + FirebaseDatabase.getInstance().getReference().getKey(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),"GET ROOT " + FirebaseDatabase.getInstance().getReference().getRoot(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),"GET PARENT " + FirebaseDatabase.getInstance().getReference().getParent(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),"GET REF " + FirebaseDatabase.getInstance().getReference().getRef(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),"GET DATABASE " + FirebaseDatabase.getInstance().getReference().getDatabase(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),"GET MESSAGE TEXT " + chatMessage.getMessageText(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),"POSITION " +  position,Toast.LENGTH_SHORT).show(); // https://stackoverflow.com/questions/41313200/how-to-delete-selected-child-on-firebase-database-android
                // https://stackoverflow.com/questions/43293935/how-to-get-child-of-child-value-from-firebase-in-android
                // https://stackoverflow.com/questions/37094631/get-the-pushed-id-for-specific-value-in-firebase-android/39492322
                // https://stackoverflow.com/questions/46510476/on-select-item-in-recyclerview-get-unique-key-from-firebase
//                HashMap<String, Object> allData = (HashMap<String, Object>) dataSnapshot.getValue();
//                String[] allKey = allData.keySet().toArray(new String[0]);
//                ChatMessage chatItemSelected; // instantiate chatmessage object
                ChatMessage chatItemSelected = adapter.getItem(position); // get the position of https://stackoverflow.com/questions/42073899/how-to-display-the-keys-from-a-firebase-database-with-android
//                String itemSelected;
                String itemSelected = chatItemSelected.getMessageText();
//                Toast.makeText(getApplicationContext(),"MESSAGE TEXT " +  itemSelected,Toast.LENGTH_SHORT).show();
                Decrypt decryptedOutput = new Decrypt(); // DECRYPTED OUTPUT IS THE INSTANTIATED CLASS.  USING THE INSTANTIATED CLASS, CALL DECRYPT METHOD ON TEXT SELECTED
                Toast.makeText(getApplicationContext(),"DECRYPTED OUTPUT " +  decryptedOutput.Decrypt(itemSelected),Toast.LENGTH_SHORT).show();
                // https://firebase.google.com/docs/reference/js/firebase.database.DataSnapshot
                //Toast.makeText(getApplicationContext(),"GET EXP " + FirebaseDatabase.getInstance().getReference().child("enigmachat2").push().getKey().get(position).child("messageText"),Toast.LENGTH_SHORT).show();
                return false;
            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();

                // Close the app
                finish();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            finish();
                        }
                    });
        }
        return true;
    }

}
