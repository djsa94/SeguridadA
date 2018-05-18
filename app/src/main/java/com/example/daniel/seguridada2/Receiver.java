package com.example.daniel.seguridada2;

import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//            Intent intent2 = new Intent(ContactsContract.Intents.Insert.ACTION);
//            intent2.setType(ContactsContract.RawContacts.CONTENT_TYPE);
//            intent2.putExtra(ContactsContract.Intents.Insert.PHONE, intent.getStringExtra("telefono"));
//            intent2.putExtra(ContactsContract.Intents.Insert.NAME, intent.getStringExtra("nombre"));
//            intent2.putExtra(ContactsContract.Intents.Insert.EMAIL, intent.getStringExtra("email"));
//            context.startActivity(intent2);
//            Intent addContactIntent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
//            addContactIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME,"xyz");
//            addContactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL,"abc@gmail.com");
//            addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE,"012345678");
//            context.startActivity(addContactIntent);
//            Toast toast = Toast.makeText(context, "recibido",  Toast.LENGTH_SHORT);
//            toast.show();


        String displayName = intent.getStringExtra("nombre");
        String mobileNumber = intent.getStringExtra("telefono");
        String email = intent.getStringExtra("email");

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

        // Names
        if (displayName != null) {
            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            displayName).build());
        }

        // Mobile Number
        if (mobileNumber != null) {
            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());
        }

        // Email
        if (email != null) {
            ops.add(ContentProviderOperation
                       .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                            ContactsContract.CommonDataKinds.Email.TYPE_WORK).build());
        }

        // Asking the Contact provider to create a new contact
        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}