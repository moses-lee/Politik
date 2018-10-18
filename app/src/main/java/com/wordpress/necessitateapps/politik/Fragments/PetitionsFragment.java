package com.wordpress.necessitateapps.politik.Fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.necessitateapps.politik.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PetitionsFragment extends Fragment {

    private EditText editSubject, editBody;
    private MultiAutoCompleteTextView editEmails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_petitions, container, false);

        editSubject = view.findViewById(R.id.edit_subject);
        editBody = view.findViewById(R.id.edit_body);
        editEmails = view.findViewById(R.id.edit_emails);



        getEmails();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getEmails());
        editEmails.setAdapter(adapter);
        editEmails.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        Button buttonGo = view.findViewById(R.id.button_go);
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "", body = "", emails = "";

                if (checkifEmpty()) {
                    subject = editSubject.getText().toString().trim();
                    body = editBody.getText().toString().trim();
                    emails = editEmails.getText().toString().trim();
                    popUp(subject, body, emails);
                }


            }
        });


        return view;
    }

    private boolean checkifEmpty() {
        if (editSubject.getText().toString().isEmpty()){
            editSubject.requestFocus();
            editSubject.setError("Subject Cannot Be Empty!");
            return false;
        }
        if (editEmails.getText().toString().isEmpty()){
            editEmails.requestFocus();
            editEmails.setError("Emails Cannot Be Empty!");
            return false;
        }
        if (editBody.getText().toString().isEmpty()){
            editBody.requestFocus();
            editBody.setError("Description Cannot Be Empty!");
            return false;
        }
        return true;
    }

    public String[] getEmails() {
        ArrayList<String> emlRecs = new ArrayList<String>();
        HashSet<String> emlRecsHS = new HashSet<String>();
        Context context = getActivity();
        ContentResolver cr = context.getContentResolver();
        String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
        String order = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE";
        String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
        if (cur.moveToFirst()) {
            do {
                // names comes in hand sometimes
                String name = cur.getString(1);
                String emlAddr = cur.getString(3);

                // keep unique only
                if (emlRecsHS.add(emlAddr.toLowerCase())) {
                    emlRecs.add(emlAddr);
                }
            } while (cur.moveToNext());
        }

        cur.close();

        String[] array = emlRecs.toArray(new String[0]);
        return array;
    }

    private void popUp(final String subject, final String body, final String emails) {
        //initialize dialogue
        //opens popup
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup, null);

        mBuilder.setView(view);
        final AlertDialog dialog = mBuilder.create();


        TextView cancelText = view.findViewById(R.id.text_cancel);
        Button buttonPetition = view.findViewById(R.id.button_petition);

        dialog.show();

        buttonPetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(subject, body, emails);
            }
        });

        //dismisses dialogue
        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void sendEmail(String subject, String body, String emails){
        List<String> email_array = new ArrayList<String>(Arrays.asList(emails.split(" ")));

        //sendto?
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");

        //sends email to multiple people
        for(int j = 0; j < email_array.size(); j++){
            i.putExtra(android.content.Intent.EXTRA_EMAIL,
                    email_array.toArray(new String[email_array.size()]));
        }

        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT   , "I am inviting you to sign a petition: "+
                "\n" + body);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

        getActivity().finish();
    }


}
