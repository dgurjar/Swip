package swip.cmu.edu.activity;

import swip.cmu.edu.Application;
import swip.cmu.edu.Application.Request;
import swip.cmu.edu.PermissionManager;
import swip.cmu.edu.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Dev Gurjar
 * TODO "Cancel" button functionality is not really working for modification of permissions. 
 */
public class CheckoutActivity extends Activity
{
	PermissionRow beingReviewed = null;

	class PermissionRow extends TableRow
	{
		ImageView risk;
		ImageView loss;
		ImageView blank;
		TextView text;
		Request request;

		public PermissionRow(final Context context, Request reqest, final int requestIndex)
		{
			super(context);
			this.request = reqest;

			// Set the images
			risk = new ImageView(context);
			loss = new ImageView(context);
			blank = new ImageView(context);
			risk.setImageResource(R.drawable.warn20);
			loss.setImageResource(R.drawable.info20);
			blank.setImageResource(R.drawable.blank20);

			text = new TextView(context);
			text.setText(request.getPermission().getName());
			text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, 20));

			this.addView(text);
			this.addView(loss);
			this.addView(risk);
			this.addView(blank);
			update();
		}

		void update()
		{
			risk.setVisibility(request.isRiskingPrivacy() ? VISIBLE : GONE);
			blank.setVisibility(request.isRiskingPrivacy() ? GONE : VISIBLE);			
			loss.setVisibility(request.isGranted() ? GONE : VISIBLE);
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);
		
		Application beingInstalled = PermissionManager.getSelectedApp();

		// Set the app icon and name
		ImageView imageView = (ImageView) findViewById(R.id.appImage);
		imageView.setImageResource(beingInstalled.getIcon());

		TextView textView = (TextView) findViewById(R.id.appName);
		textView.setText(beingInstalled.getName());

		TableLayout table = (TableLayout) findViewById(R.id.permissionTable);

		TableRow allowdeny_row = new TableRow(this);
		TextView allow_text_view = new TextView(this);
		allow_text_view.setText("Allowed Permissions");
		//allow_text_view.setHeight(50);
		allow_text_view.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		allow_text_view.setTextSize(18);
		allowdeny_row.addView(allow_text_view);
		table.addView(allowdeny_row);
		
		// Fill this screen with the information from the permissions, line by line.
		int i = 0;
		for (Request r : beingInstalled.getPermissionRequests())
		{
			// Add risk if present and granted.
			if(r.isGranted())
				table.addView(new PermissionRow(this, r, i++));
		}
		
		allowdeny_row = new TableRow(this);
		allow_text_view = new TextView(this);
		allow_text_view.setText("\n");
		allow_text_view.setHeight(50);
		allowdeny_row.addView(allow_text_view);
		table.addView(allowdeny_row);
		
		allowdeny_row = new TableRow(this);
		allow_text_view = new TextView(this);
		allow_text_view.setText("Denied Permissions");
		//allow_text_view.setHeight(50);
		allow_text_view.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		allow_text_view.setTextSize(18);
		allowdeny_row.addView(allow_text_view);
		table.addView(allowdeny_row);
		
		// Fill this screen with the information from the permissions, line by line.
		for (Request r : beingInstalled.getPermissionRequests())
		{
			// Add risk if present and granted.
			if(!r.isGranted())
				table.addView(new PermissionRow(this, r, i++));
		}
		
		

		// Wait for actions on the buttons.
		Button modify = (Button) findViewById(R.id.modify);
		modify.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent = new Intent(getBaseContext(), ModifyPermissionsActivity.class);
				startActivityForResult(intent, 0);
				finish();
			}
		});
		Button accept = (Button) findViewById(R.id.accept);
		accept.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// TODO Plug Dev's screen
				// startActivity(new Intent(ShowRiskActivity.this,
				// ModifyPermissionsActivity.class));
				PermissionManager.installSelected();
				Intent intent = new Intent(getBaseContext(), MainMenuActivity.class);
				startActivityForResult(intent, 0);
				
				/* Show a toast to provide additional feedback */
				Context context = getApplicationContext();
				CharSequence text = "The Application Has Been Successfuly Installed";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				
				
				finish();
			}
		});
		
		switch (PermissionManager.getMode())
		{
			case INSTALLING:
				modify.setText("Modify");
				accept.setText("Accept and Install");
				break;
			case MODIFYING:
				modify.setText("Cancel");
				accept.setText("Save and Exit");
				break;
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	/**
	 * Update any changes in the request being observed.
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (beingReviewed != null)
		{
			beingReviewed.update();
			beingReviewed = null;
		}
	}

}