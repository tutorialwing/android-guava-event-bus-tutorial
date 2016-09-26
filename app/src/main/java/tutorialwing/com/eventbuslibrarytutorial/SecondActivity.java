package tutorialwing.com.eventbuslibrarytutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SecondActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
	}

	@Override
	protected void onStart() {
		super.onStart();
		GlobalBus.getBus().register(this);
	}

	@Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
	public void getMessage(Events.ActivityActivityMessage activityActivityMessage) {
		TextView messageView = (TextView) findViewById(R.id.messageReceived);
		messageView.setText(getString(R.string.message_received) + " " + activityActivityMessage.getMessage());

		Toast.makeText(getApplicationContext(),
				getString(R.string.message_second_activity) + " " + activityActivityMessage.getMessage(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStop() {
		super.onStop();
		GlobalBus.getBus().unregister(this);
	}
}
