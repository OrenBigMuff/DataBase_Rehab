package oren_bigmuff.com.database_rehab;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    private final static int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final static String TAG_MESSAGE = "0";
    private final static String TAG_YESNO = "1";
    private final static String TAG_TEXT = "2";
    private final static String TAG_IMAGE = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Layoutの生成
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.WHITE);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Buttonの生成
        layout.addView(makeButton("メッセージダイアログの表示", TAG_MESSAGE));
        layout.addView(makeButton("Yes/Noダイアログの表示", TAG_YESNO));
        layout.addView(makeButton("テキスト入力ダイアログの表示", TAG_TEXT));
        layout.addView(makeButton(res2bmp(this, R.drawable.sample), TAG_IMAGE));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public Bitmap res2bmp(Context context, int resID) {
        return BitmapFactory.decodeResource(
                context.getResources(), resID);
    }

    //ボタンの生成
    private Button makeButton(String text, String tag) {
        Button button = new Button(this);
        button.setText(text);
        button.setTag(tag);
        button.setOnClickListener(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        return button;
    }

    //イメージボタンの生成
    private ImageButton makeButton(Bitmap bmp, String tag) {
        ImageButton button = new ImageButton(this);
        button.setTag(tag);
        button.setOnClickListener(this);
        button.setImageBitmap(bmp);
        button.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        return button;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();

        if (TAG_MESSAGE.equals(tag)) {
            MessageDialog.show(this, "メッセージダイアログ", "ボタンを押した");
        } else if (TAG_YESNO.equals((tag))) {

        } else if (TAG_TEXT.equals(tag)) {

        } else if (TAG_IMAGE.equals(tag)) {

        }
    }

    //メッセージダイアログの定義
    public static class MessageDialog extends DialogFragment {
        //ダイアログの表示
        public static void show(
                Activity activity, String title, String text) {
            MessageDialog f = new MessageDialog();
            Bundle args = new Bundle();
            args.putString("title", title);
            args.putString("text", text);
            f.setArguments(args);
            f.show(activity.getFragmentManager(), "messageDialog");
        }

        //ダイアログの生成
        @Override
        public Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
            ad.setTitle(getArguments().getString("title"));
            ad.setMessage(getArguments().getString("text"));
            ad.setPositiveButton("OK", null);
            return ad.create();
        }
    }

    //Yes/Noダイアログの定義
    public static class YesNoDialog extends DialogFragment {
        //ダイアログの表示
        public static void show(
            Activity activity, String title, String text) {
            YesNoDialog f = new YesNoDialog();
            Bundle args = new Bundle();
            args.putString("title", title);
            args.putString("text", text);
            f.setArguments(args);
            f.show(activity.getFragmentManager(), "yesNoDialog");
        }
        //Yes/Noダイアログの生成
        @Override
        public Dialog onCreateDialog(Bundle bundle) {
            //リスナーの生成
            DialogInterface.OnClickListener listener =
                    new DialogInterface.OnClickListener() {
                        //ダイアログボタン押下時に呼ばれる
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                MessageDialog.show(getActivity(), "", "YES");
                            } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                                MessageDialog.show(getActivity(), "", "NO");
                            }
                        }
            };
            //Yes/Noダイアログの生成
            AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
            ad.setTitle(getArguments().getString("title"));
            ad.setMessage(getArguments().getString("text"));
            ad.setPositiveButton("Yes", listener);
            ad.setNegativeButton("No", listener);
            return ad.create();
        }
    }

    //テキスト入力ダイアログの定義
    public static class TextDialog extends DialogFragment {
        private EditText editText;

        //ダイアログの表示
        public static void show(
                Activity activity, String title, String text) {
            TextDialog f = new TextDialog();
            Bundle args = new Bundle();
            args.putString("title", title);
            args.putString("text", text);
            f.setArguments(args);
            f.show(activity.getFragmentManager(), "textDialog");
        }

        //テキスト入力ダイアログの生成
        @Override
        public Dialog onCreateDialog(Bundle bundle) {
            //リスナーの生成
            DialogInterface.OnClickListener listener =
                    new DialogInterface.OnClickListener() {
                        //ダイアログボタン押下時に呼ばれる
                        public void onClick(DialogInterface dialog, int which) {
                            MessageDialog.show(getActivity(), "", editText.getText().toString());
                        }
                    };

            //エディットテキストの生成
            editText = new EditText(getActivity());

            //テキスト入力ダイアログの生成
            AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
            ad.setTitle(getArguments().getString("title"));
            ad.setMessage(getArguments().getString("text"));
            ad.setView(editText);
            ad.setPositiveButton("OK", listener);

            //フラグメントの状態復帰
            if (bundle != null) editText.setText(bundle.getString("editText", ""));

            return ad.create();
        }

        //フラグメントの状態保存
        @Override
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putString("editText", editText.getText().toString());
        }
    }

}
