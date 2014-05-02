package tanuj.sudokugame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newuser);
	}

	public void clicked(View v)
	{
		EditText et=(EditText)findViewById(R.id.username);
		String username = et.getText().toString();

		for(int i = 0; i< mymain.total_users; i++)
		{
			if(mymain.usernames[i].equalsIgnoreCase(username))
			{
				Toast.makeText(getApplicationContext(), "This username is already present. Enter another username.", Toast.LENGTH_LONG).show();
				finish();
				return;
			}
		}

		File root = Environment.getExternalStorageDirectory();
		File file = new File(root,"Sudoku_data.txt");
		if (username.length() > 0)
		{
			try
			{
				if (root.canWrite()) 
				{
					FileWriter filewriter = new FileWriter(file);
					BufferedWriter out = new BufferedWriter(filewriter);
					out.write(username + " " +"0\n");
					out.close();
				}
				mymain.usernames[mymain.total_users] = username;
				mymain.points[mymain.total_users++] = 0;
			} 
			catch (IOException e) 
			{
				Log.e("TAG", "Could not write file " + e.getMessage());
			}
		}
		else{  //empty input
			clicked(v);
			return;
		}


		try 
		{
			if (root.canWrite()) 
			{
				FileWriter filewriter = new FileWriter(file,true);
				BufferedWriter out = new BufferedWriter(filewriter);
				for(int i=0;i<mymain.total_users;i++)
					out.write(mymain.usernames[i] + " " +mymain.points[i]+"\n");
				out.close();
			} 
		}
		
		catch (IOException e) 
		{
			Log.e("TAG", "Could not write file " + e.getMessage());
		}
		
		/*Intent i = new Intent(this, mymain.class);
		startActivity(i);*/
		finish();
	}
}
