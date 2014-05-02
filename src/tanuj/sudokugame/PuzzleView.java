package tanuj.sudokugame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
//import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

public class PuzzleView extends View
{
	private boolean isFirstDraw = true;  //to initialize textColorGrid
	public boolean allowHints = false, helperPad = false;  //setting variables for practice mode.
	private int textColorGrid[][] = new int[9][9]; //has black nums as 1 and red ones as 0
	private int gameEndSum, points;
	private long start_time = System.currentTimeMillis(),total_time;
	private boolean[] checkedItems = {false,false};
	
	private static final String TAG = "Sudoku PuzzleView";
	private float width;    // width of one tile
	private float height;   // height of one tile
	private int selX;       // X index of selection
	private int selY;       // Y index of selection
	private final Rect selRect = new Rect();
	private final Game game;
	Paint background = new Paint(), dark = new Paint(), hilite = new Paint(), 
			light = new Paint(), hint = new Paint(), selected = new Paint(), 
			foreground = new Paint(Paint.ANTI_ALIAS_FLAG), foreground_input = new Paint(Paint.ANTI_ALIAS_FLAG);
	Rect r = new Rect();

	public PuzzleView(Context context)
	{
		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		if (mymain.isPlay==false)  //for practice mode ask the settings
			pracSettings();
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		width = w / 9f;
		height = h / 9f;
		getRect(selX, selY, selRect);
		Log.d(TAG, "onSizeChanged: width " + width + ", height "+ height);
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	private void pracSettings()
	{
		new AlertDialog.Builder(this.getContext()).setTitle("Practice Settings").setMultiChoiceItems
		(
			R.array.practiceSettings, 
			checkedItems, 
			new DialogInterface.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					helperPad = checkedItems[0];
					allowHints = checkedItems[1];
					System.out.println("h,a: "+helperPad+" "+allowHints);
					invalidate();
				}
			}
		).setNegativeButton("Okay", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		})
		.show();
	}

	protected void onDraw(Canvas canvas) 
	{
		// Draw the background...
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
		//Log.d(TAG,"Invetigating: "+getWidth()+" "+getHeight());

		// Draw the board...
		// Define colors for the grid lines
		dark.setColor(getResources().getColor(R.color.puzzle_dark));
		hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
		light.setColor(getResources().getColor(R.color.puzzle_light));

		// Draw the minor grid lines
		for (int i = 0; i < 9; i++) 
		{
			canvas.drawLine(0, i * height, getWidth(), i * height, light);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
			canvas.drawLine(i * width, 0, i * width, getHeight(), light);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
		}

		// Draw the major grid lines
		for (int i = 0; i < 9; i++) 
		{
			if (i % 3 != 0)
				continue;
			canvas.drawLine(0, i * height, getWidth(), i * height, dark);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
			canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
		}

		// Draw the numbers...

		// Define color and style for numbers
		foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height * 0.75f);
		foreground.setTextScaleX(width / height);
		foreground.setTextAlign(Paint.Align.CENTER);
		

		// Draw the number in the center of the tile
		FontMetrics fm = foreground.getFontMetrics();
		//System.out.println("Fm:"+fm+" "+fm.ascent+" "+fm.descent);

		// Centering in X: use alignment (and X at midpoint)
		float x = width / 2;

		// Centering in Y: measure ascent/descent first
		float y = height / 2 - (fm.ascent + fm.descent) / 2;
		
		gameEndSum = 0; /**to check if the game has ended*/
		
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if(isFirstDraw==true)
				{
					foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
					canvas.drawText(this.game.getTileString(i, j), i* width + x, j * height + y, foreground);
					textColorGrid[i][j] = ( game.getTileString(i, j) ).length();
				}
				else
				{
					if(textColorGrid[i][j]==0)  //of 0 initial length
					{
						foreground.setColor(getResources().getColor(R.color.puzzle_inputColor));
						canvas.drawText(this.game.getTileString(i, j), i* width + x, j * height + y, foreground);
					}
					else
					{
						foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
						canvas.drawText(this.game.getTileString(i, j), i* width + x, j * height + y, foreground);
					}
				}
				gameEndSum += (game.getTileString(i, j)).length();
				if (gameEndSum==81)
					gameEnd();
			}
		}

		if(mymain.isPlay == false && allowHints == true) /**Only displayed during practice*/
		{
			// Draw the hints...
	
			// Pick a hint color based on #moves left
			int c[] = { getResources().getColor(R.color.puzzle_hint_0), 
						getResources().getColor(R.color.puzzle_hint_1),
						getResources().getColor(R.color.puzzle_hint_2), };
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					int movesleft = 9 - game.getUsedTiles(i, j).length;
					if (movesleft < c.length) {
						getRect(i, j, r);
						hint.setColor(c[movesleft]);
						canvas.drawRect(r, hint);
					}
				}
			}
		}

		// Draw the selection...

		/**Log.d(TAG, "selRect=" + selRect);*/
		selected.setColor(getResources().getColor(R.color.puzzle_selected));
		canvas.drawRect(selRect, selected);
	}



	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);
		
		if( textColorGrid[(int) (event.getX() / width)][(int) (event.getY() / height)] == 1) /**blocks change in preset tiles*/
		   return true;
		
		select( (int) (event.getX() / width), (int) (event.getY() / height));
		game.showKeypadOrError(selX, selY);
		isFirstDraw = false;
		/**Log.d(TAG, "onTouchEvent: x " + selX + ", y " + selY);*/
		return true;
	}

	public void setSelectedTile(int tile) {
		if (game.setTileIfValid(selX, selY, tile)) 
			invalidate();// may change hints
		else 
		{
			// Number is not valid for this tile
			/**Log.d(TAG, "setSelectedTile: invalid: " + tile);*/
			startAnimation(AnimationUtils.loadAnimation(game,R.anim.shake));
		}
	}

	private void select(int x, int y) {
		invalidate(selRect);
		selX = Math.min(Math.max(x, 0), 8);
		selY = Math.min(Math.max(y, 0), 8);
		getRect(selX, selY, selRect);
		invalidate(selRect);
	}
	
	private void gameEnd()
	{
		System.out.println("Congratulations! You Win!");
		total_time = (System.currentTimeMillis()-start_time)/1000;
		
		if(total_time>3600)total_time = 3600;
		
		switch (Game.diff) {
	      case Game.DIFFICULTY_HARD:
	    	  points =(int) ( 40 * (1-(total_time/3600.0)) + 10);
	         break;
	      case Game.DIFFICULTY_MEDIUM:
	    	  points =(int) ( 20 * (1-(total_time/3600.0)) + 5);
	         break;
	      case Game.DIFFICULTY_EASY:
	      default:
	    	 points =(int) ( 8 * (1-(total_time/3600.0)) + 2);
	         break;
	      }
		
		if(mymain.isPlay==true)
		{
			int ptr;
			for(ptr=0;ptr < mymain.total_users; ptr++)
			{
				if(mymain.usernames[ptr]==mymain.currentUser)
					break;
			}
			mymain.points[ptr] += points;
			mymain.userPoints += points;
			mymain.dataLoader();
			
			new AlertDialog.Builder(this.getContext()).setTitle("You Win")
			.setMessage(
			"You took "+total_time/3600+" Hours "+(total_time%3600)/60+" Minutes "+(total_time%3600)%60+" Seconds to solve this problem.\n"+
			"You earned "+points+" points.\n"+
			"Congratulations!"
			)
			.setNegativeButton("Okay", new DialogInterface.OnClickListener() 
	  		{
	            public void onClick(DialogInterface dialoginterface, int i)
	            {
	               game.restart();
	            }
	         }
			).show();
		}
		else
		{
			new AlertDialog.Builder(this.getContext()).setTitle("You Win")
			.setMessage(
			"Congratulations!\n"+
			"You took "+total_time/3600+" Hours "+(total_time%3600)/60+" Minutes "+(total_time%3600)%60+" Seconds to solve this problem.\n"+
			"You can play challenge rounds to add points to your profile."
			)
			.setNegativeButton("Okay", new DialogInterface.OnClickListener() 
	  		{
	            public void onClick(DialogInterface dialoginterface, int i)
	            {
	               game.restart();
	            }
	         }
			).show();
		}
	}
	
	private void getRect(int x, int y, Rect rect) {
		rect.set((int) (x * width), (int) (y * height), (int) (x* width + width), (int) (y * height + height));
	}
}
