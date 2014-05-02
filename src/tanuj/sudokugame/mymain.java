package tanuj.sudokugame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class mymain extends Activity implements OnClickListener{

	public static boolean isPlay; //true = play and false = learn
	private static boolean noUserFlag = false;
	public static String[] usernames = new String[1000];
	public static String currentUser;
	public static int[] points = new int[1000];
	public static int total_users, userPoints;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		dataLoader();
		System.out.println("my main called");

		setContentView(R.layout.mymain);
		
		TextView tv = (TextView)findViewById(R.id.usernameView);
		tv.setText(mymain.currentUser);

		if (noUserFlag == true)
		{
			noUserFlag = false;
			Toast.makeText(this, "Create a new user", Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, NewUser.class);
			startActivity(i);
		}
		

		// Set up click listeners for all the buttons
		View changeButton = findViewById(R.id.buttonchange);
		changeButton.setOnClickListener(this);
		View playButton = findViewById(R.id.buttonplay);
		playButton.setOnClickListener(this);
		View learnButton = findViewById(R.id.buttonpractice);
		learnButton.setOnClickListener(this);
		View statsButton = findViewById(R.id.buttonstats);
		statsButton.setOnClickListener(this);
		View rulesButton = findViewById(R.id.buttonrules);
		rulesButton.setOnClickListener(this);
		View exitButton = findViewById(R.id.buttonabout);
		exitButton.setOnClickListener(this);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		TextView tv = (TextView)findViewById(R.id.usernameView);
		tv.setText(mymain.currentUser);
	}

	@Override
	public void onBackPressed() {
		System.out.println("Finishing app!");
		android.os.Process.killProcess(android.os.Process.myPid());
		finish();
	}

	public static void dataLoader()
	{
		//Read text from file
		File root = Environment.getExternalStorageDirectory();
		File file = new File(root,"Sudoku_data.txt");
		try 
		{
			if( file.length()  < 1 || file.exists()==false)
			{
				noUserFlag = true;
				return;
			}

			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			System.out.println("file len: "+file.length());
			
			line = br.readLine();
			
			System.out.println("Line length = "+line.split(" ").length);
			
			if(line.split(" ").length==2)
			{
				currentUser = line.split(" ")[0];
				userPoints = Integer.parseInt(line.split(" ")[1]);
			}
			else
			{
				currentUser = line.split(" ")[0] +" "+ line.split(" ")[1];
				userPoints = Integer.parseInt(line.split(" ")[line.split(" ").length-1]);
			}
			
			total_users = 0;
			while ((line = br.readLine()) != null) 
			{
				if(line.split(" ").length==2)
				{
					usernames[total_users] = line.split(" ")[0];
					points[total_users++] = Integer.parseInt(line.split(" ")[1]);
				}
				else
				{
					usernames[total_users] = line.split(" ")[0]+" "+line.split(" ")[1];
					points[total_users++] = Integer.parseInt(line.split(" ")[line.split(" ").length-1]);
				}
				System.out.println(usernames[total_users-1]+" "+points[total_users-1]);
			}
			br.close();
		}
		catch (Exception e) 
		{
			Log.e("TAG", "Could not read file " + e.getMessage());
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId()) 
		{
		case R.id.buttonchange:
			Intent n = new Intent(this, ChangeUser.class);
			startActivity(n);
			break;

		case R.id.buttonplay:
			isPlay = true;
			openNewGameDialog();
			break;

		case R.id.buttonpractice:
			isPlay = false;
			openNewGameDialog();
			break;     

		case R.id.buttonstats:
			Intent s = new Intent(this, Stats.class);
			startActivity(s);
			break;

		case R.id.buttonrules:
			Intent r = new Intent(this, Rules.class);
			startActivity(r);
			break;

		case R.id.buttonabout:
			Intent i = new Intent(this, About.class);
			startActivity(i);
			break;
		}
	}

	/** Ask the user what difficulty level they want */
	private void openNewGameDialog() 
	{
		Intent i = new Intent(this, Myplay.class);
		startActivity(i);
	}

}
