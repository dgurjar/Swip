package swip.cmu.edu.activity;

import swip.cmu.edu.Application;
import swip.cmu.edu.PermissionManager;
import swip.cmu.edu.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowRiskActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_risks);
		this.setTitle("Application Installation");
	}
	
	@Override
	public void onResume() 
	{
		super.onResume();
		
		// Check the selected app and revert changes.
		Application beingInstalled = PermissionManager.getSelectedApp();
		beingInstalled.revertToDefaults();
		
		// Set the app icon and name
		ImageView imageView = (ImageView) findViewById(R.id.appImage);
		imageView.setImageResource(beingInstalled.getIcon());
		TextView textView = (TextView) findViewById(R.id.appName);
		textView.setText(beingInstalled.getName());
		
		// Set the warnings
		TableRow fl = (TableRow) findViewById(R.id.featureLoss);
		fl.setVisibility(beingInstalled.isLoosingFeatures() ? View.VISIBLE : View.GONE);
		TableRow pr = (TableRow) findViewById(R.id.privacyRisk);
		pr.setVisibility(beingInstalled.isRiskingPrivacy() ? View.VISIBLE : View.GONE);
		
		/* Neither item is visible, so we tell the user*/
		TableRow ok = (TableRow) findViewById(R.id.NoPRFL);
		if(!beingInstalled.isLoosingFeatures() && !beingInstalled.isRiskingPrivacy())
			ok.setVisibility(View.VISIBLE);
		else	
			ok.setVisibility(View.GONE);
		
		// Wait for actions on the buttons.
		Button modify = (Button) findViewById(R.id.modify);
		modify.setOnClickListener(new View.OnClickListener() 
		{
	        public void onClick(View v) 
	        {
	    		startActivity(new Intent(ShowRiskActivity.this, ModifyPermissionsActivity.class));
	    		finish();
	        }
	    });
		Button accept = (Button) findViewById(R.id.accept);
		accept.setOnClickListener(new View.OnClickListener() 
		{
	        public void onClick(View v) 
	        {
	        	// Update the app status and go back to the first screen.
	        	//PermissionManager.installSelected();
	    		startActivity(new Intent(getBaseContext(), CheckoutActivity.class));
	        	finish();
	        }
	    });
	}
}