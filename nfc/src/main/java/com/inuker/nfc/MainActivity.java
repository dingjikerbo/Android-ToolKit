package com.inuker.nfc;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    final String TAG = getClass().getSimpleName();

    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter != null) {
            if (!mNfcAdapter.isEnabled()) {
                Log.v(TAG, String.format("NFC not enabled"));
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            processIntent(intent);
        }
    }

    private void processIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tag);
//        ndef.writeNdefMessage();
    }
}
