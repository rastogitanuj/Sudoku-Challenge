package tanuj.sudokugame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Myplay extends Activity implements OnClickListener{
	
	private static final String TAG = "My Play";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myplay);
		View ButtonEasy = findViewById(R.id.Button1);
		ButtonEasy.setOnClickListener(this);
		View ButtonMedium = findViewById(R.id.Button2);
		ButtonMedium.setOnClickListener(this);
		View ButtonHard = findViewById(R.id.Button3);
		ButtonHard.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId()) 
		{

		//0,1,2 corresponds to difficulties
		case R.id.Button1:
			startGame(0);
			break;

		case R.id.Button2:
			startGame(1);
			break;

		case R.id.Button3:
			startGame(2);
			break;
		}
	}
	
	private void startGame(int i) 
	{
		Log.d(TAG, "clicked on " + i); //Logs the button clicked on.
		Intent intent = new Intent(Myplay.this, Game.class);
		intent.putExtra(Game.KEY_DIFFICULTY, i);
		finish();
		startActivity(intent);
	}
}
