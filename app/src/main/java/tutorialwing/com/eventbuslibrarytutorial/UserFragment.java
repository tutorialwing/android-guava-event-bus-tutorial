package tutorialwing.com.eventbuslibrarytutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

public class UserFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		GlobalBus.getBus().register(this);
		return inflater.inflate(R.layout.fragment_user, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setClickListener(view);
	}

	public void setClickListener(final View view) {
		Button btnSubmit = (Button) view.findViewById(R.id.submit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText etMessage = (EditText) view.findViewById(R.id.editText);
				Events.FragmentActivityMessage fragmentActivityMessageEvent =
						new Events.FragmentActivityMessage(String.valueOf(etMessage.getText()));

				GlobalBus.getBus().post(fragmentActivityMessageEvent);
			}
		});
	}

	@Subscribe
	public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {
		TextView messageView = (TextView) getView().findViewById(R.id.message);
		messageView.setText(
				getString(R.string.message_received) +
						" " + activityFragmentMessage.getMessage());

		Toast.makeText(getActivity(),
				getString(R.string.message_fragment) +
						" " + activityFragmentMessage.getMessage(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		GlobalBus.getBus().unregister(this);
	}
}
