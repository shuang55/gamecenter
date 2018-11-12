package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fall2018.csc2017.R;

/**
 * The Log In Activity for game centre.
 */
public class LogInActivity extends AppCompatActivity {

    /**
     * SAVE FILE FOR USERMANAGER.
     */
    public static final String USERS = UserManager.USERS;

    /**
     * Usermanager for signing in and signing up.
     */
    private UserManager manager;

    /**
     * The username input.
     */
    private String user;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The game centre that handles the loading and saving of user and game data.
     */
    private GameCentre gameCentre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        addLogInButtonListener();
        addPasswordTextListener();
        addUserTextListener();
        addSignUpButtonListener();
        Context LogIn = LogInActivity.this;
        gameCentre = new GameCentre(LogIn);
        manager = gameCentre.userManager;
    }

    /**
     * Show text for incorrect password.
     */
    private void makeToastInvalidLogIn() {
        Toast.makeText(this, "Incorrect User/Password", Toast.LENGTH_SHORT).show();
    }

    /**
     * Show text for successful sign up.
     */
    private void makeToastSignUp() {
        Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
    }

    /**
     * Show text for unsuccessful sign up.
     */
    private void makeToastFailedSignUp() {
        Toast.makeText(this, "Sign Up failed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activates the user name input.
     */
    private void addUserTextListener() {
        EditText userInput = findViewById(R.id.UserName);
        user = userInput.getText().toString();

    }

    /**
     * Activates the password input.
     */
    private void addPasswordTextListener() {
        EditText passwordInput = findViewById(R.id.Password);
        password = passwordInput.getText().toString();
    }

    /**
     * Activates the sign up button.
     */
    private void addSignUpButtonListener() {
        Button signUp = findViewById(R.id.SignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPasswordTextListener();
                addUserTextListener();
                if (manager.signUp(user, password)) {
                    makeToastSignUp();
                } else {
                    Log.v("test", user + password);
                    makeToastFailedSignUp();
                }
            }
        });

    }

    /**
     * Activate the Log in Button.
     */
    private void addLogInButtonListener() {
        Button logIn = findViewById(R.id.LogInButton);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserTextListener();
                addPasswordTextListener();
                if (manager.authenticate(user, password)) {
                    User currentUser = manager.findUser(user);
                    manager.setCurrentUser(currentUser);
                    switchToGameCentre();
                } else {
                    makeToastInvalidLogIn();
                }
            }
        });
    }

    /**
     * Switch to Game Centre to choose games.
     */
    private void switchToGameCentre() {
        gameCentre.saveManager(USERS, manager);
        Intent swap = new Intent(this, GameCentreActivity.class);
        startActivity(swap);

    }

    /**
     * Save usermanager on pause.
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameCentre.saveManager(USERS, manager);
    }

}
