package tanuj.sudokugame;

import tanuj.sudokugame.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class SudokuGame1 extends Activity implements OnClickListener {
	   
	   
	   private static final String TAG = "Sudoku Game1";
	   /** Called when the activity is first created. */
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.main);

	      // Set up click listeners for all the buttons
	      View continueButton = findViewById(R.id.continue_button);
	      continueButton.setOnClickListener(this);
	      View newButton = findViewById(R.id.new_button);
	      newButton.setOnClickListener(this);
	      View aboutButton = findViewById(R.id.about_button);
	      aboutButton.setOnClickListener(this);
	      View exitButton = findViewById(R.id.exit_button);
	      exitButton.setOnClickListener(this);
	   }
 
	   // ...
	   public void onClick(View v) {
	      switch (v.getId()) {
	      case R.id.about_button:
	         Intent i = new Intent(this, About.class);
	         startActivity(i);
	         break;
	      // More buttons go here (if any) ...
	      
	      
	      case R.id.new_button:
	         openNewGameDialog();
	         break;
	      
	      
	      case R.id.exit_button:
	         finish();
	         break;
	      
	      
	      }
	   }
	   
	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      super.onCreateOptionsMenu(menu);
	      MenuInflater inflater = getMenuInflater();
	      inflater.inflate(R.menu.menu, menu);
	      return true;
	   }
	   
	   @Override
	   public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	      case R.id.settings:
	         startActivity(new Intent(this, Prefs.class));
	         return true;
	      // More items go here (if any) ...
	      }
	      return false;
	   }
	   
	   
	   /** Ask the user what difficulty level they want */
	   private void openNewGameDialog() 
	   {
	      new AlertDialog.Builder(this).setTitle(R.string.new_game_title).setItems
	      (R.array.difficulty, new DialogInterface.OnClickListener() 
	      		{
	               public void onClick(DialogInterface dialoginterface, int i)
	               {
	            	  //System.out.println("Selected difficulty level :"+i);
	                  startGame(i);
	               }
	            }
	      ).show(); //<- will show the AlertDialog
	   }
	   
	   /** Start a new game with the given difficulty level */
	   private void startGame(int i) {
		      Log.d(TAG, "clicked on " + i);
		      Intent intent = new Intent(SudokuGame1.this, Game.class);
		      intent.putExtra(Game.KEY_DIFFICULTY, i);
		      //System.out.println("Invetigating :"+intent.getIntExtra(Game.KEY_DIFFICULTY, Game.DIFFICULTY_EASY));
		      startActivity(intent);
		   }
	}
