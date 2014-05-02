package tanuj.sudokugame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Stats extends Activity{
		
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.stats);
			
			userlist();
			pointslist();
			
		}
		public void userlist() 
		{
			String[] showList = new String[mymain.total_users];
			System.out.println("USER LIST tot users: "+mymain.total_users);
			for (int i=0;i<mymain.total_users;i++){
				showList[i] = mymain.usernames[i];
			}
			ListView listView = (ListView)findViewById(R.id.userListStat);
			ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,R.layout.textview,showList);
			listView.setAdapter(adapter);
			System.out.println("USER LIST");
		}
		public void pointslist() 
		{
			String[] showList = new String[mymain.total_users];
			System.out.println("USER LIST tot users: "+mymain.total_users);
			for (int i=0;i<mymain.total_users;i++){
				showList[i] = Integer.toString(mymain.points[i]);
			}
			ListView listView = (ListView)findViewById(R.id.pointsListStat);
			ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,R.layout.textview,showList);
			listView.setAdapter(adapter);
			System.out.println("POINTS LIST");
		}
}
