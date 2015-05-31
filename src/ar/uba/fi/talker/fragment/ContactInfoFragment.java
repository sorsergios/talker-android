package ar.uba.fi.talker.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.dao.ContactDAO;
import ar.uba.fi.talker.dao.ContactTalkerDataSource;

public class ContactInfoFragment extends DialogFragment {

	View view;
	private ContactTalkerDataSource contactTalkerDataSource = null;
	TextView text = new TextView(getActivity());
	TextView vers = new TextView(getActivity());
	public ContactInfoFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.contact_info, container, false);
		int imageId = getArguments().getInt("imageId");
		if (contactTalkerDataSource == null){
			contactTalkerDataSource = new ContactTalkerDataSource(getActivity());
		}
		contactTalkerDataSource.open();
		ContactDAO contactDAO = contactTalkerDataSource.getContactByImageID(imageId);
		this.change(contactDAO.getAddress(), contactDAO.getPhone());
		contactTalkerDataSource.close();
	    text= (TextView) view.findViewById(R.id.textView1);
	    vers= (TextView)view.findViewById(R.id.textView2);
	 
	 
	    return view;
	
	}

	public void change(String txt, String txt1){
	        text.setText(txt);
	        vers.setText(txt1);
	 
	}

}
