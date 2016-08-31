package com.soz.sozTest.plugin.proxyHook;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.soz.hook.BaseHookPluginActivity;
import com.soz.hook.HookHelper;
import com.soz.log.Logger;
import com.soz.sozTest.R;

/**
 * hook service
 * Created by zhushaolong on 8/23/16.
 */
public class BinderHookActivity extends BaseHookPluginActivity {
    Logger mLogger = new Logger("BinderHookActivity");
    EditText mEditText;
    TextView mPasteText;
    ClipboardManager mClipBoardManager;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
        try {
            HookHelper.HookClipBoardService();
        } catch (Exception e) {
            mLogger.i("hook clipboard failed");
            e.printStackTrace();
        }
        this.setContentView(R.layout.act_binder_hook);
        this.initView();
    }

    private void initView() {
        this.mEditText = (EditText) this.findViewById(R.id.binder_hook_edit_text);
        this.mPasteText = (TextView) this.findViewById(R.id.binder_hook_paste_text);
    }

    public void copyFromEdit(View view) {
        if (null == this.mClipBoardManager) {
            // get clipboard service.
            this.mClipBoardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        }
        ClipData clip = ClipData.newPlainText("text", mEditText.getText());
        this.mClipBoardManager.setPrimaryClip(clip);
    }

    public void pasteToResult(View view) {
        if (null == this.mClipBoardManager) {
            // get clipboard service.
            this.mClipBoardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        }

        if (!this.mClipBoardManager.hasPrimaryClip()) {
            Toast.makeText(this, "clipboard is empty", Toast.LENGTH_LONG).show();
        } else {
            String pasteResult = "";
            ClipData clipData = this.mClipBoardManager.getPrimaryClip();
            int count = clipData.getItemCount();
            for (int i = 0; i < count; ++i) {
                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item.coerceToText(this);
                pasteResult += str;
                mLogger.i("item : " + i + ": " + str);
            }
            this.mPasteText.setText(pasteResult);
        }
    }
}
