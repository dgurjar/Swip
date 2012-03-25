package swip.cmu.edu.activity;

import swip.cmu.edu.PermissionManager;
import swip.cmu.edu.PermissionManager.Mode;
import swip.cmu.edu.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
	
		// Initialize the data layer
		PermissionManager.initialize();
				
		// Wait for actions on the buttons.
		Button install = (Button) findViewById(R.id.install);
		install.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PermissionManager.setMode(Mode.INSTALLING);
				MainMenuActivity.this.startActivity(new Intent(MainMenuActivity.this, AppSelectActivity.class));
			}
		});
		
		Button modify = (Button) findViewById(R.id.modify);
		modify.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PermissionManager.setMode(Mode.MODIFYING);
				MainMenuActivity.this.startActivity(new Intent(MainMenuActivity.this, AppSelectActivity.class));
			}
		});
		
		Button defaults = (Button) findViewById(R.id.defaults);
		defaults.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PermissionManager.setMode(Mode.CONFIGURING);
				MainMenuActivity.this.startActivity(new Intent(MainMenuActivity.this, ModifyDefaultsActivity.class));
			}
		});
	}
}