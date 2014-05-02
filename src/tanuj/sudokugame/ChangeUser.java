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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ChangeUser extends Activity{
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.changeuser);
		TextView tv = (TextView)findViewById(R.id.current_username);
		tv.setText(mymain.currentUser);
		list();
	}
	
	public void update()
	{
		File root = Environment.getExternalStorageDirectory();
		File file = new File(root,"Sudoku_data.txt");
		try 
		{
			if (root.canWrite()) 
			{
				
				FileWriter filewriter = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(filewriter);
				out.write(mymain.currentUser + " " +mymain.userPoints+"\n");
				for(int i=0;i<mymain.total_users;i++)
					out.write(mymain.usernames[i] + " " +mymain.points[i]+"\n");
				out.close();
			} 
		}
		catch (IOException e) 
		{
			Log.e("TAG", "Could not write file " + e.getMessage());
		}
		mymain.dataLoader();
	}
	
	public void deleteUser(View v)
	{
		int ptr;
		for(ptr=0;ptr < mymain.total_users; ptr++)
		{
			if(mymain.usernames[ptr]==mymain.currentUser)
				break;
		}
		
		if(mymain.total_users > 1)
		{
			mymain.usernames[ptr] = mymain.usernames[mymain.total_users-1];
			mymain.points[ptr] = mymain.points[mymain.total_users-1];
			mymain.total_users--;
			mymain.currentUser = mymain.usernames[0];
			mymain.userPoints = mymain.points[0];
			update();
		}
		else
		{
			File root = Environment.getExternalStorageDirectory();
			File file = new File(root,"Sudoku_data.txt");
			boolean deleted = file.delete();
			System.out.println("File sudoku_data deleted: "+deleted);
		}
		list();
		TextView tv = (TextView)findViewById(R.id.current_username);
		tv.setText(mymain.currentUser);
		mymain.dataLoader();
	}
	
	public void createNewUser(View v)
	{
		Intent i = new Intent(this, NewUser.class);
		finish();
		startActivity(i);
	}
	
	public void list() {
		String[] showList = new String[mymain.total_users];
		for (int i=0;i<mymain.total_users;i++){
			System.out.println("user tot: "+mymain.usernames[i]+" "+mymain.total_users);
			showList[i] = mymain.usernames[i];
		}
		ListView listView = (ListView)findViewById(R.id.userList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,R.layout.textview,showList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View id, int position, long logpress){
				mymain.currentUser = mymain.usernames[position];
				mymain.userPoints = mymain.points[position];
				System.out.println("pos:"+position);
				TextView tv = (TextView)findViewById(R.id.current_username);
				tv.setText(mymain.currentUser);
			}
		});
	}
}
