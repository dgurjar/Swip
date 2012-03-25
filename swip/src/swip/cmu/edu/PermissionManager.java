package swip.cmu.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import swip.cmu.edu.Application.Request;

public class PermissionManager
{
	public enum Mode
	{
		INSTALLING, MODIFYING, CONFIGURING
	};

	private static boolean initialized = false;
	public static HashMap<String, Permission> permissionsById = new HashMap<String, Permission>();
	public static List<Application> installedApps = new ArrayList<Application>();
	public static List<Application> uninstalledApps = new ArrayList<Application>();
	public static List<Category> categories = new ArrayList<Category>();

	private static Application selectedApp = null;
	private static Permission selectedPermission = null;
	private static Mode mode = Mode.INSTALLING;
	
	public static boolean installSelected()
	{
		int index = uninstalledApps.indexOf(selectedApp);
		if (index < 0)
			return false;
		uninstalledApps.remove(index);
		installedApps.add(selectedApp);
		return true;
	}

	public static void initialize()
	{
		// Don't initialize more than once. (just in case)
		if (initialized)
			return;
		initialized = true;

		// Create all the permissions.
		loadPermissions();
		loadCategories();
		loadApps();
	}

	private static void loadPermissions()
	{
		// Create permisions
		/*
		 * Permission p = new Permission("Risky but accepted by default",
		 * "Very relevant permission.", true); p.setAcceptByDefault(true);
		 * permissionsById.put("TEST", p);
		 * 
		 * permissionsById.put("RISKY", new Permission("very risky",
		 * "Very relevant permission.", true)); permissionsById.put("NOT_RISKY",
		 * new Permission("not risky", "Very relevant permission.", false));
		 */

		permissionsById.put("ACCESS_COARSE_LOCATION", new Permission("Access Approximate Location",
				"Allows an application to access approximate location", false));
		permissionsById.put("ACCESS_FINE_LOCATION", new Permission("Access Fine Location",
				"Allows an application to access fine (e.g. GPS) location", true));
		permissionsById.put("ACCESS_LOCATION_EXTRA_COMMANDS", new Permission("Access Location Extra Commands",
				"Allows an application to access extra location provider commands", false));
		permissionsById.put("ACCESS_MOCK_LOCATION", new Permission("Create Fake Location",
				"Allows an application to create fake locations", false));
		permissionsById.put("ACCESS_NETWORK_STATE", new Permission("Access Network State",
				"Allows applications to access information about networks", false));
		permissionsById.put("ACCESS_WIFI_STATE", new Permission("Access Wifi State",
				"Allows applications to access information about Wi-Fi networks", false));
		permissionsById.put("ADD_VOICEMAIL", new Permission("Add Voicemail",
				"Allows an application to add voicemails into the system", false));
		permissionsById.put("BATTERY_STATS", new Permission("Battery Stats",
				"Allows an application to collect battery statistics", false));
		permissionsById.put("BIND_TEXT_SERVICE", new Permission("Bind Text Service",
				"Must be required by a TextService", false));
		permissionsById.put("BLUETOOTH", new Permission("Bluetooth",
				"Allows applications to connect to paired bluetooth devices", false));
		permissionsById.put("BLUETOOTH_ADMIN", new Permission("Manage Bluetooth",
				"Allows applications to discover and pair bluetooth devices", false));
		permissionsById.put("BRICK", new Permission("Brick",
				"Required to be able to disable the device (very dangerous!)", true));
		permissionsById.put("BROADCAST_SMS", new Permission("Broadcast Text",
				"Allows an application to broadcast an SMS receipt notification", true));
		permissionsById.put("CALL_PHONE", new Permission("Call Phone",
				"Allows an application to initiate a phone call without going through the phone's standard keypad",
				true));
		permissionsById
				.put("CALL_PRIVILEGED",
						new Permission(
								"Call Any Number",
								"Allows an application to call any phone number including emergency numbers without going through the phone's standard keypad",
								true));
		permissionsById.put("CAMERA", new Permission("Camera", "Required to be able to access the camera device.",
				false));
		permissionsById
				.put("CHANGE_COMPONENT_ENABLED_STATE",
						new Permission(
								"Change Component Enabled State",
								"Allows an application to change whether an application component (other than its own) is enabled or not.",
								false));
		permissionsById.put("CHANGE_NETWORK_STATE", new Permission("Change Network State",
				"Allows applications to change network connectivity state", false));
		permissionsById.put("CHANGE_WIFI_MULTICAST_STATE", new Permission("Change Wifi Multicast State",
				"Allows applications to enter Wi-Fi Multicast mode", false));
		permissionsById.put("CHANGE_WIFI_STATE", new Permission("Change Wifi State",
				"Allows applications to change Wi-Fi connectivity state", false));
		permissionsById.put("CLEAR_APP_CACHE", new Permission("Clear App Cache",
				"Allows an application to clear the caches of all installed applications on the device.", true));
		permissionsById.put("CLEAR_APP_USER_DATA", new Permission("Clear App User Data",
				"Allows an application to clear user data", true));
		permissionsById.put("CONTROL_LOCATION_UPDATES", new Permission("Control Location Updates",
				"Allows enabling/disabling location update notifications from the radio.", true));
		permissionsById.put("DELETE_CACHE_FILES", new Permission("Delete Cache Files",
				"Allows an application to delete cache files.", false));
		permissionsById.put("DEVICE_POWER", new Permission("Device Power",
				"Allows low-level access to power management", false));
		permissionsById.put("DISABLE_KEYGUARD", new Permission("Disable Keyguard",
				"Allows applications to disable the keyguard", false));
		permissionsById.put("FACTORY_TEST", new Permission("Factory Test",
				"Run as a manufacturer test application running as the root user.", true));
		permissionsById.put("FLASHLIGHT", new Permission("Flashlight", "Allows access to the flashlight", false));
		permissionsById.put("FORCE_BACK", new Permission("Force Back",
				"Allows an application to force a BACK operation on whatever is the top activity.", false));
		permissionsById.put("GET_ACCOUNTS", new Permission("Get Accounts",
				"Allows access to the list of accounts in the Accounts Service", true));
		permissionsById.put("HARDWARE_TEST", new Permission("Hardware Test", "Allows access to hardware peripherals.",
				false));
		permissionsById
				.put("INJECT_EVENTS",
						new Permission(
								"Inject Events",
								"Allows an application to inject user events (keys touch trackball) into the event stream and deliver them to ANY window.",
								false));
		permissionsById.put("INSTALL_LOCATION_PROVIDER", new Permission("Install Location Provider",
				"Allows an application to install a location provider into the Location Manager", false));
		permissionsById
				.put("INTERNAL_SYSTEM_WINDOW",
						new Permission(
								"Internal System Window",
								"Allows an application to open windows that are for use by parts of the system user interface.",
								false));
		permissionsById.put("KILL_BACKGROUND_PROCESSES", new Permission("Kill Background Processes",
				"Allows an application to call killBackgroundProcesses", false));
		permissionsById.put("MANAGE_ACCOUNTS", new Permission("Manage Accounts",
				"Allows an application to manage the list of accounts in the AccountManager", false));
		permissionsById.put("MANAGE_APP_TOKENS", new Permission("Manage App Tokens",
				"Allows an application to manage (create destroy Z-order) application tokens in the window manager.",
				false));
		permissionsById.put("MODIFY_AUDIO_SETTINGS", new Permission("Modify Audio Settings",
				"Allows an application to modify global audio settings", false));
		permissionsById.put("MODIFY_PHONE_STATE", new Permission("Modify Phone State",
				"Allows modification of the telephony state - power on mmi etc.", false));
		permissionsById.put("MOUNT_FORMAT_FILESYSTEMS", new Permission("Mount Format File Systems",
				"Allows formatting file systems for removable storage.", false));
		permissionsById.put("MOUNT_UNMOUNT_FILESYSTEMS", new Permission("Mount Unmount File Systems",
				"Allows mounting and unmounting file systems for removable storage.", false));
		permissionsById.put("NFC",
				new Permission("Nfc", "Allows applications to perform I/O operations over NFC", true));
		permissionsById.put("PROCESS_OUTGOING_CALLS", new Permission("Process Outgoing Calls",
				"Allows an application to monitor modify or abort outgoing calls.", true));
		permissionsById.put("READ_CALENDAR", new Permission("Read Calendar",
				"Allows an application to read the user's calendar data.", true));
		permissionsById.put("READ_CONTACTS", new Permission("Read Contacts",
				"This permission allows the application to access your phone's contacts.", true));
		permissionsById.put("READ_FRAME_BUFFER", new Permission("Read Frame Buffer",
				"Allows an application to take screen shots and more generally get access to the frame buffer data",
				false));
		permissionsById.put("READ_HISTORY_BOOKMARKS", new Permission("Read History Bookmarks",
				"Allows an application to read (but not write) the user's browsing history and bookmarks.", true));
		permissionsById.put("READ_INPUT_STATE", new Permission("Read Input State",
				"Allows an application to retrieve the current state of keys and switches.", false));
		permissionsById.put("READ_LOGS", new Permission("Read Logs",
				"Allows an application to read the low-level system log files.", true));
		permissionsById.put("READ_PHONE_STATE", new Permission("Read Phone State",
				"Allows read only access to phone state.", false));
		permissionsById.put("READ_PROFILE", new Permission("Read Profile",
				"Allows an application to read the user's personal profile data.", true));
		permissionsById.put("REBOOT", new Permission("Reboot", "Required to be able to reboot the device.", false));
		permissionsById
				.put("RECEIVE_MMS",
						new Permission(
								"Receive Mms",
								"Allows an application to monitor incoming MMS messages to record or perform processing on them.",
								true));
		permissionsById
				.put("RECEIVE_SMS",
						new Permission(
								"Receive Sms",
								"Allows an application to monitor incoming SMS messages to record or perform processing on them.",
								true));
		permissionsById.put("RECEIVE_WAP_PUSH", new Permission("Receive Wap Push",
				"Allows an application to monitor incoming WAP push messages.", true));
		permissionsById.put("RECORD_AUDIO", new Permission("Record Audio", "Allows an application to record audio",
				false));
		permissionsById.put("REORDER_TASKS", new Permission("Reorder Tasks",
				"Allows an application to change the Z-order of tasks", false));
		permissionsById
				.put("SEND_SMS", new Permission("Send Sms", "Allows an application to send SMS messages.", true));
		permissionsById.put("SET_ALARM", new Permission("Set Alarm",
				"Allows an application to broadcast an Intent to set an alarm for the user.", false));
		permissionsById
				.put("SET_ALWAYS_FINISH",
						new Permission(
								"Set Always Finish",
								"Allows an application to control whether activities are immediately finished when put in the background.",
								false));
		permissionsById.put("SET_ORIENTATION", new Permission("Set Orientation",
				"Allows low-level access to setting the orientation (actually rotation) of the screen.", false));
		permissionsById.put("SET_POINTER_SPEED", new Permission("Set Pointer Speed",
				"Allows low-level access to setting the pointer speed.", false));
		permissionsById.put("SET_PROCESS_LIMIT",new Permission("Set Process Limit","Allows an application to set the maximum number of (not needed) application processes that can be running.", false));
		permissionsById.put("SET_TIME", new Permission("Set Time", "Allows applications to set the system time", false));
		permissionsById.put("SET_TIME_ZONE", new Permission("Set Time Zone",
				"Allows applications to set the system time zone", false));
		permissionsById.put("SET_WALLPAPER", new Permission("Set Wallpaper",
				"Allows applications to set the wallpaper", false));
		permissionsById.put("SET_WALLPAPER_HINTS", new Permission("Set Wallpaper Hints",
				"Allows applications to set the wallpaper hints", false));
		permissionsById.put("STATUS_BAR", new Permission("Status Bar",
				"Allows an application to open close or disable the status bar and its icons.", true));
		permissionsById.put("SUBSCRIBED_FEEDS_READ", new Permission("Subscribed Feeds Read",
				"Allows an application to allow access the subscribed feeds ContentProvider.", false));
		permissionsById.put("UPDATE_DEVICE_STATS", new Permission("Update Device Stats",
				"Allows an application to update device statistics.", false));
		permissionsById.put("VIBRATE", new Permission("Vibrate", "Allows access to the vibrator", false));
		permissionsById.put("WAKE_LOCK", new Permission("Wake Lock",
				"This permission is used to ensure your phone does not shut off while the application is active.", false));
		permissionsById.put("WRITE_CALENDAR", new Permission("Write Calendar",
				"Allows an application to add to (but not read) the user's calendar data.", true));
		permissionsById.put("WRITE_CONTACTS", new Permission("Write Contacts",
				"Allows an application to add to (but not read) the user's contacts data.", true));
		permissionsById.put("WRITE_EXTERNAL_STORAGE", new Permission("Write External Storage",
				"Allows an application to write to external storage", false));
		permissionsById.put("WRITE_GSERVICES", new Permission("Write Gservices",
				"Allows an application to modify the Google service map.", false));
		permissionsById.put("WRITE_HISTORY_BOOKMARKS", new Permission("Write History Bookmarks",
				"Allows an application to add to (but not read) the user's browsing history and bookmarks.", true));
		permissionsById.put("WRITE_PROFILE", new Permission("Write Profile",
				"Allows an application to add to (but not read) the user's personal profile data.", true));
		permissionsById.put("WRITE_SECURE_SETTINGS", new Permission("Write Secure Settings",
				"Allows an application to read or write the secure system settings.", false));
		permissionsById.put("WRITE_SETTINGS", new Permission("Write Settings",
				"Allows an application to read or write the system settings.", false));
		permissionsById.put("WRITE_SMS", new Permission("Write Sms", "Allows an application to write SMS messages.",
				true));
		permissionsById.put("WRITE_SYNC_SETTINGS", new Permission("Write Sync Settings",
				"Allows applications to write the sync settings", false));
		permissionsById.put("INTERNET", new Permission("Full Internet Access",
				"Allows applications to connect with the Internet.", false));
	}

	private static void loadCategories()
	{

		{
			Category c = new Category("Your personal information");
			//c.addPermission(permissionsById.get("CLEAR_APP_USER_DATA"));
			//c.addPermission(permissionsById.get("GET_ACCOUNTS"));
			//c.addPermission(permissionsById.get("MANAGE_ACCOUNTS"));
			c.addPermission(permissionsById.get("READ_CALENDAR"));
			c.addPermission(permissionsById.get("READ_CONTACTS"));
			//c.addPermission(permissionsById.get("READ_HISTORY_BOOKMARKS"));
			//c.addPermission(permissionsById.get("READ_PROFILE"));
			c.addPermission(permissionsById.get("WRITE_CALENDAR"));
			c.addPermission(permissionsById.get("WRITE_CONTACTS"));
			//c.addPermission(permissionsById.get("WRITE_GSERVICES"));
			//c.addPermission(permissionsById.get("WRITE_HISTORY_BOOKMARKS"));
			//c.addPermission(permissionsById.get("WRITE_PROFILE"));
			//c.addPermission(permissionsById.get("BIND_TEXT_SERVICE"));
			//c.addPermission(permissionsById.get("BROADCAST_SMS"));
			//c.addPermission(permissionsById.get("RECEIVE_MMS"));
			c.addPermission(permissionsById.get("RECEIVE_SMS"));
			//c.addPermission(permissionsById.get("RECEIVE_WAP_PUSH"));
			c.addPermission(permissionsById.get("SEND_SMS"));
			c.addPermission(permissionsById.get("WRITE_SMS"));
			categories.add(c);
		}
		{
			//Category c = new Category("Services that cost you money");
			//c.addPermission(permissionsById.get("INTERNET"));
			// TODO What happened with this ones?
		}
		{
			Category c = new Category("Your location");
			c.addPermission(permissionsById.get("ACCESS_COARSE_LOCATION"));
			c.addPermission(permissionsById.get("ACCESS_FINE_LOCATION"));
			//c.addPermission(permissionsById.get("ACCESS_LOCATION_EXTRA_COMMANDS"));
			//c.addPermission(permissionsById.get("ACCESS_MOCK_LOCATION"));
			//c.addPermission(permissionsById.get("CONTROL_LOCATION_UPDATES"));
			//c.addPermission(permissionsById.get("INSTALL_LOCATION_PROVIDER"));
			categories.add(c);
		}
		{
			//Category c = new Category("Your messages");
			// TODO What happened with this ones?
		}
		{
			Category c = new Category("Network communication");
			c.addPermission(permissionsById.get("ACCESS_NETWORK_STATE"));
			c.addPermission(permissionsById.get("ACCESS_WIFI_STATE"));
			c.addPermission(permissionsById.get("BLUETOOTH"));
			//c.addPermission(permissionsById.get("BLUETOOTH_ADMIN"));
			c.addPermission(permissionsById.get("CHANGE_NETWORK_STATE"));
			//c.addPermission(permissionsById.get("CHANGE_WIFI_MULTICAST_STATE"));
			c.addPermission(permissionsById.get("CHANGE_WIFI_STATE"));
			categories.add(c);
		}
		{
			//Category c = new Category("Your accounts");
			// TODO What happened with this ones?
		}
		{
			//Category c = new Category("Storage");
			//c.addPermission(permissionsById.get("MOUNT_FORMAT_FILESYSTEMS"));
			//c.addPermission(permissionsById.get("MOUNT_UNMOUNT_FILESYSTEMS"));
			//c.addPermission(permissionsById.get("WRITE_EXTERNAL_STORAGE"));
			//categories.add(c);
		}
		{
			Category c = new Category("Phone calls");
			c.addPermission(permissionsById.get("ADD_VOICEMAIL"));
			c.addPermission(permissionsById.get("CALL_PHONE"));
			c.addPermission(permissionsById.get("CALL_PRIVILEGED"));
			c.addPermission(permissionsById.get("PROCESS_OUTGOING_CALLS"));
			categories.add(c);
		}
		{
			Category c = new Category("Hardware controls");
			//c.addPermission(permissionsById.get("BRICK"));
			c.addPermission(permissionsById.get("CAMERA"));
			c.addPermission(permissionsById.get("DEVICE_POWER"));
			//c.addPermission(permissionsById.get("DISABLE_KEYGUARD"));
			c.addPermission(permissionsById.get("FLASHLIGHT"));
			//c.addPermission(permissionsById.get("HARDWARE_TEST"));
			c.addPermission(permissionsById.get("MODIFY_AUDIO_SETTINGS"));
			//c.addPermission(permissionsById.get("MODIFY_PHONE_STATE"));
			//c.addPermission(permissionsById.get("NFC"));
			//c.addPermission(permissionsById.get("READ_FRAME_BUFFER"));
			c.addPermission(permissionsById.get("REBOOT"));
			//c.addPermission(permissionsById.get("RECORD_AUDIO"));
			c.addPermission(permissionsById.get("SET_ALARM"));
			c.addPermission(permissionsById.get("VIBRATE"));
			c.addPermission(permissionsById.get("WAKE_LOCK"));
			categories.add(c);
		}
		{
			//Category c = new Category("System tools");
			// TODO What happened with this ones?
		}
		/*
		{
			Category c = new Category("Smartphone data");
			c.addPermission(permissionsById.get("BATTERY_STATS"));
			//c.addPermission(permissionsById.get("DELETE_CACHE_FILES"));
			c.addPermission(permissionsById.get("READ_INPUT_STATE"));
			//c.addPermission(permissionsById.get("READ_LOGS"));
			c.addPermission(permissionsById.get("READ_PHONE_STATE"));
			//c.addPermission(permissionsById.get("REORDER_TASKS"));
			c.addPermission(permissionsById.get("SET_TIME"));
			c.addPermission(permissionsById.get("SET_TIME_ZONE"));
			c.addPermission(permissionsById.get("SET_WALLPAPER"));
			//c.addPermission(permissionsById.get("SET_WALLPAPER_HINTS"));
			c.addPermission(permissionsById.get("UPDATE_DEVICE_STATS"));
			//c.addPermission(permissionsById.get("WRITE_SECURE_SETTINGS"));
			c.addPermission(permissionsById.get("WRITE_SETTINGS"));
			categories.add(c);
		}
		{
			Category c = new Category("Software Controls");
			c.addPermission(permissionsById.get("CHANGE_COMPONENT_ENABLED_STATE"));
			//c.addPermission(permissionsById.get("CLEAR_APP_CACHE"));
			//c.addPermission(permissionsById.get("FACTORY_TEST"));
			c.addPermission(permissionsById.get("FORCE_BACK"));
			c.addPermission(permissionsById.get("INJECT_EVENTS"));
			c.addPermission(permissionsById.get("INTERNAL_SYSTEM_WINDOW"));
			c.addPermission(permissionsById.get("KILL_BACKGROUND_PROCESSES"));
			//c.addPermission(permissionsById.get("MANAGE_APP_TOKENS"));
			c.addPermission(permissionsById.get("SET_ALWAYS_FINISH"));
			c.addPermission(permissionsById.get("SET_ORIENTATION"));
			//c.addPermission(permissionsById.get("SET_POINTER_SPEED"));
			//c.addPermission(permissionsById.get("SET_PROCESS_LIMIT"));
			c.addPermission(permissionsById.get("STATUS_BAR"));
			//c.addPermission(permissionsById.get("SUBSCRIBED_FEEDS_READ"));
			c.addPermission(permissionsById.get("WRITE_SYNC_SETTINGS"));
			categories.add(c);
		}
		*/
	}

	private static void loadApps()
	{
//		Application app = new Application("Angry Birds", R.drawable.angrybirds2, "Some birds really, really angry.");
//		app.addRequest(new Request(permissionsById.get("TEST"), "We need this permission to screw you..."));
//		app.addRequest(new Request(permissionsById.get("RISKY"), "We need this permission to screw you..."));
//		app.addRequest(new Request(permissionsById.get("NOT_RISKY"), "We need this permission to screw you..."));
//		
//		uninstalledApps.add(app);
//		
//		app = new Application("Bla", R.drawable.info80, "Yeah, very interesting...");
//		app.addRequest(new Request(permissionsById.get("RISKY"), "We need this permission to screw you..."));
//		uninstalledApps.add(app);
//		
//		for(int i=1; i<=10; i++)
//		{
//			app = new Application("App" + i, R.drawable.warn80, "Nop, nothing to say about this one, but HEY! its FREE!");
//			app.addRequest(new Request(permissionsById.get("TEST"), "We need this permission to screw you..."));
//			app.addRequest(new Request(permissionsById.get("NOT_RISKY"), "We need this permission to screw you..."));
//			uninstalledApps.add(app);
//		}
//		
		
		try
		{
		Application app = new Application("Doodle Jump", R.drawable.doodlejump, "Yeah, very interesting...");
		app.addRequest(new Request(permissionsById.get("INTERNET"), "This permission is used to update high scores and compare them against the Doodle Jump community. It also enables sharing your scores with friends through the network."));	
		app.addRequest(new Request(permissionsById.get("READ_PHONE_STATE"), "This permission is used to access the phone features of the device and determine the phone number and serial number of this phone, whether a call is active, the number that call is connected to."));	
		app.addRequest(new Request(permissionsById.get("WAKE_LOCK"), "This permission is used to ensure that your phone does not sleep or does not timeout in the middle of game play."));	
		uninstalledApps.add(app);
		
		app = new Application("Restaurant Cook", R.drawable.restaurantcook, "Yeah, very interesting..."); 
		app.addRequest(new Request(permissionsById.get("INTERNET"), "This permission is used to update high scores and compare them against the Gigbeat community."));	
		app.addRequest(new Request(permissionsById.get("READ_PHONE_STATE"), "This permission is used to access the phone features of the device and determine the phone number and serial number of this phone, whether a call is active, the number that call is connected to."));	
		app.addRequest(new Request(permissionsById.get("WAKE_LOCK"), "This permission is used to ensure that your phone does not sleep or does not timeout in the middle of game play."));	
		uninstalledApps.add(app);
		
		app = new Application("Facebook", R.drawable.facebook, "Yeah, very interesting...");
		app.addRequest(new Request(permissionsById.get("MANAGE_ACCOUNTS"), "This permission is used to add and delete user accounts on the phone."));	
		app.addRequest(new Request(permissionsById.get("SEND_SMS"), "This permission is used if you want to send SMS messages to friends who have their phone already linked to their Facebook account."));	
		app.addRequest(new Request(permissionsById.get("ACCESS_FINE_LOCATION"), "This permission is used for the Facebok checkin service which allow users to check in to their favorite local spots."));	
		app.addRequest(new Request(permissionsById.get("WRITE_SMS"), "This permission is used to edit SMS messages already on your phone if you chose to do so."));	
		app.addRequest(new Request(permissionsById.get("RECEIVE_SMS"), "This permission is used by Facebook to enable social applications such as Friend history, timeline, and messaging."));	
		app.addRequest(new Request(permissionsById.get("INTERNET"), "This permission is at the core of the product and is used to stay connected to Facebook notifications, messages, and posts."));	
		app.addRequest(new Request(permissionsById.get("READ_CONTACTS"), "This permission is used to sync and connect your phone's contacts with your Facebook friends."));	
		app.addRequest(new Request(permissionsById.get("WRITE_CONTACTS"), "This permission is used to update your phone's contacts with your Facebook friends."));	
		app.addRequest(new Request(permissionsById.get("READ_PHONE_STATE"), "This permission is used to access the phone features of the device and determine the phone number and serial number of this phone, whether a call is active, the number that call is connected to."));	
		app.addRequest(new Request(permissionsById.get("WRITE_EXTERNAL_STORAGE"), "This permission is used to access any photos or media stored on your phone's SD card."));	
		app.addRequest(new Request(permissionsById.get("WRITE_SYNC_SETTINGS"), "This permission is used to enable syncing from your Facebook contatcts."));	
		app.addRequest(new Request(permissionsById.get("WAKE_LOCK"), "This permission is used to ensure your phone does not go to sleep or your screen does not timeout while browsing Facebook on your mobile device."));	
		uninstalledApps.add(app);
		
		app = new Application("OnTheFly", R.drawable.onthefly, "Yeah, very interesting...");
		app.addRequest(new Request(permissionsById.get("ACCESS_COARSE_LOCATION"), "This permission is used to approximate what airport you are currently at."));	
		app.addRequest(new Request(permissionsById.get("INTERNET"), "This permission is used to access the internet and retrieve the most up to date information on flights and their status."));	
		uninstalledApps.add(app);
		
		app = new Application("X Construction", R.drawable.xconstruction, "Yeah, very interesting...");
		app.addRequest(new Request(permissionsById.get("INTERNET"), "This permission is used to update high scores."));	
		app.addRequest(new Request(permissionsById.get("WRITE_EXTERNAL_STORAGE"), "This permission is used to save current levels, high scores, and screenshots of your gameplay."));	
		uninstalledApps.add(app);
		
		app = new Application("Gmail", R.drawable.gmail, "Yeah, very interesting...");
		app.addRequest(new Request(permissionsById.get("MANAGE_ACCOUNTS"), "This permission is used to modify and delete accounts on your phone."));	
		app.addRequest(new Request(permissionsById.get("INTERNET"), "This permission is used to retrieve emails, update the status of your emails, and send emails. It is also used to search, retrieve contacts, and retrieve attachments. This permission is at the core of the application."));	
		app.addRequest(new Request(permissionsById.get("READ_CONTACTS"), "This permission is used to suggest contacts from your phone in email messages."));	
		app.addRequest(new Request(permissionsById.get("WRITE_CONTACTS"), "This permission is used to update your phone's contacts based on people you frequently email."));	
		app.addRequest(new Request(permissionsById.get("WRITE_EXTERNAL_STORAGE"), "This permission is used to move attachments to your phone's storage so you can access it later."));	
		app.addRequest(new Request(permissionsById.get("SUBSCRIBED_FEEDS_READ"), "This permission is used to read and change your synced feeds on the phone."));	
		app.addRequest(new Request(permissionsById.get("WAKE_LOCK"), "This permission is used to make sure your phone does not go into sleep or your screen does not timeout while accessing the Gmail application."));	
		app.addRequest(new Request(permissionsById.get("WRITE_SYNC_SETTINGS"), "This permission is used to have Gmail sync at automatically or manually depending on your preference."));	
		app.addRequest(new Request(permissionsById.get("VIBRATE"), "This permission is used to have your phone vibrate when a message is received. Note: You can turn this off within the app."));	
		app.addRequest(new Request(permissionsById.get("ACCESS_NETWORK_STATE"), "This permission is used to notify you how long it's been since the last sync and whether the phone can sync again."));	
		uninstalledApps.add(app);
		
		app = new Application("Pandora internet radio", R.drawable.pandora, "Yeah, very interesting...");
		app.addRequest(new Request(permissionsById.get("BLUETOOTH"), "This permission is used to stream songs to bluetooth enabled speakers or headphones."));	
		app.addRequest(new Request(permissionsById.get("INTERNET"), "This permission is used to stream songs from Pandora."));	
		app.addRequest(new Request(permissionsById.get("READ_CONTACTS"), "This permission is used to see if any of your contacts are on Pandora."));	
		app.addRequest(new Request(permissionsById.get("WRITE_CALENDAR"), "This permission is used to add songs to play at a specific time to your phone's calendar."));	
		app.addRequest(new Request(permissionsById.get("READ_PHONE_STATE"), "This permission is used to access the phone features of the device and determine the phone number and serial number of this phone, whether a call is active, the number that call is connected to."));	
		app.addRequest(new Request(permissionsById.get("WAKE_LOCK"), "This permission is used to make sure your phone does not go into sleep or your screen does not timeout while on Pandora."));	
		app.addRequest(new Request(permissionsById.get("CHANGE_WIFI_STATE"), "This permission is used to make sure your connected to the best network while songs."));	
		app.addRequest(new Request(permissionsById.get("CHANGE_NETWORK_STATE"), "This permission is used to let you know and also enable you to change your connection while streaming."));	
		installedApps.add(app);
		
		app = new Application("Read It Later", R.drawable.readitlater, "Yeah, very interesting...");
		app.addRequest(new Request(permissionsById.get("INTERNET"), "This permission is used to to sync your saved articles and sites to any device."));	
		app.addRequest(new Request(permissionsById.get("WRITE_EXTERNAL_STORAGE"), "This permission is used to save your articles locally so you can access them without a network connection."));	
		app.addRequest(new Request(permissionsById.get("WAKE_LOCK"), "This permission is used to make sure your phone does not go into sleep or your screen does not timeout while on Read It Later."));	
		uninstalledApps.add(app);
		
		app = new Application("Mileage Tracker", R.drawable.mileagetracker, "Yeah, very interesting...");
		app.addRequest(new Request(permissionsById.get("INTERNET"), "This permission is used to save your mileage history online and make it accessible even from a browser."));	
		app.addRequest(new Request(permissionsById.get("WRITE_EXTERNAL_STORAGE"), "This permission is used to save your mileage history locally so you can access it without being connected to a network."));	
		uninstalledApps.add(app);
		
		app = new Application("New York Post ", R.drawable.newyorkpost, "Yeah, very interesting...");app.addRequest(new Request(permissionsById.get("INTERNET"), "This permission is used to retrieve articles as soon as they are published. This is at the core of the app and is required to view any content."));	
		app.addRequest(new Request(permissionsById.get("READ_PHONE_STATE"), "This permission is used to access the phone features of the device and determine the phone number and serial number of this phone, whether a call is active, the number that is connected to."));	
		app.addRequest(new Request(permissionsById.get("WAKE_LOCK"), "This permission is used to make sure your phone does not go into sleep or your screen does not timeout while on the New York Post."));	
		app.addRequest(new Request(permissionsById.get("VIBRATE"), "This permission is used to notify you using your phone's vibrator when breaking news comes out. Note: You can modify this setting within the app."));	
		app.addRequest(new Request(permissionsById.get("ACCESS_NETWORK_STATE"), "This permission is used to let you know how long it has been since the last update and whether it's possible to update now."));	
		uninstalledApps.add(app);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return the selectedApp
	 */
	public static Application getSelectedApp()
	{
		return selectedApp;
	}

	/**
	 * @param selectedApp
	 *            the selectedApp to set
	 */
	public static void setSelectedApp(int index)
	{
		switch (mode)
		{
			case INSTALLING:
				selectedApp = uninstalledApps.get(index);
				break;
			case MODIFYING:
				selectedApp = installedApps.get(index);
				break;
		}
	}

	/**
	 * @return the selectedPermission
	 */
	public static Permission getSelectedPermission()
	{
		return selectedPermission;
	}

	/**
	 * @param selectedPermission
	 *            the selectedPermission to set
	 */
	public static void setSelectedPermission(Permission selectedPermission)
	{
		PermissionManager.selectedPermission = selectedPermission;
	}

	/**
	 * @return the mode
	 */
	public static Mode getMode()
	{
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public static void setMode(Mode mode)
	{
		PermissionManager.mode = mode;
	}
}
