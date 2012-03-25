package swip.cmu.edu.activity;

import swip.cmu.edu.Application.Request;
import swip.cmu.edu.Permission;
import swip.cmu.edu.PermissionManager;
import swip.cmu.edu.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class PermissionDetailsActivity extends Activity
{
	Request req;
	Permission permission;

	/**
	 * Listens to the grant button, applies the action and closes.
	 */
	View.OnClickListener grantListener = new View.OnClickListener()
	{
		public void onClick(View v)
		{
			if (req != null)
				req.setGranted(true);
			else
				permission.setAcceptByDefault(true);
			finish();
		}
	};

	/**
	 * Listens to the deny button, applies the action and closes.
	 */
	View.OnClickListener denyListener = new View.OnClickListener()
	{
		public void onClick(View v)
		{
			if (req != null)
				req.setGranted(false);
			else
				permission.setAcceptByDefault(false);
			finish();
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.permission_details);
	}

	
	@Override
	public void onResume()
	{
		super.onResume();
		
		switch (PermissionManager.getMode())
		{
			case INSTALLING:
			case MODIFYING:
			{
				// Get the application and the relevant request.
				int requestId = this.getIntent().getIntExtra("requestId", 0);
				req = PermissionManager.getSelectedApp().getPermissionRequests().get(requestId);
				permission = req.getPermission();

				// Set developer explanation to be visible.
				((TableRow) findViewById(R.id.featureLoss)).setVisibility(View.VISIBLE);
				TextView explanation = (TextView) findViewById(R.id.explanation);
				if (req.getReason() == null || req.getReason().length() == 0)
					explanation.setText("Nothing.");
				else
					explanation.setText(req.getReason());

				break;
			}
			case CONFIGURING:
			{
				// Get the application and the relevant request.
				permission = PermissionManager.getSelectedPermission();

				// Set developer explanation to be visible.
				((TableRow) findViewById(R.id.featureLoss)).setVisibility(View.GONE);
				break;
			}
		}

		// Set common features like name, description, etc.
		((TextView) findViewById(R.id.permission)).setText(permission.getName());
		((TextView) findViewById(R.id.risk)).setText(permission.getDescription());

		ImageView riskImg = (ImageView) findViewById(R.id.privacyImg);
		if (permission.isRisky())
			riskImg.setVisibility(View.VISIBLE);
		else
			riskImg.setVisibility(View.INVISIBLE);

		// Wait for actions on the buttons.
		Button modify = (Button) findViewById(R.id.grant);
		modify.setOnClickListener(grantListener);
		Button accept = (Button) findViewById(R.id.deny);
		accept.setOnClickListener(denyListener);
	}
}