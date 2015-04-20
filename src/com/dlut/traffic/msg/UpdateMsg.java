package com.dlut.traffic.msg;

import com.dlut.traffic.R;
import com.dlut.traffic.TitleActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateMsg extends TitleActivity implements OnClickListener {

	@Override
	protected void onBackward(View backwardView) {
		// TODO Auto-generated method stub
		super.onBackward(backwardView);
	}

	@Override
	protected void onForward(View forwardView) {
		// TODO Auto-generated method stub
		super.onForward(forwardView);
		resultcontent = info_et.getText().toString().trim();
		int length = resultcontent.length();

		if (length > num) {
			Toast.makeText(getApplicationContext(), "字数超过限制字数",
					Toast.LENGTH_LONG);
			return;
		}

		Intent resultIntent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("result", resultcontent);
		resultIntent.putExtras(bundle);
		this.setResult(code, resultIntent);

		this.finish();

	}

	private EditText info_et;
	private String loginid;

	private String mimecontent;
	private TextView editdetail_back;
	private TextView editdetail_finish;
	private int code;
	private TextView edit_title;
	private String titleString;
	private int num;

	private String resultcontent;

	private TextView edit_num;
	private int len;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupViews();
	}

	private void setupViews() {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.update_info);
		setTitle(R.string.edit_user);
		showBackwardView(R.string.button_backward, true);
		showForwardView(R.string.button_submit,true);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		mimecontent = bundle.getString("mimecontent");
		code = bundle.getInt("resultcode");
		titleString = bundle.getString("title");
		num = bundle.getInt("num");

		edit_num = (TextView) findViewById(R.id.edit_num);

		info_et = (EditText) findViewById(R.id.info_et);
		info_et.setText(mimecontent);

		resultcontent = info_et.getText().toString();
		len = resultcontent.length();
		len = num - len;
		edit_num.setText(len + "/" + num);

		info_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				resultcontent = info_et.getText().toString();
				len = resultcontent.length();
				if (len <= num) {
					len = num - len;
					edit_num.setTextColor(getResources().getColor(R.color.gray));
					edit_num.setText(String.valueOf(len) + "/" + num);
				}

				else {
					len = len - num;
					edit_num.setTextColor(Color.RED);
					edit_num.setText("-" + String.valueOf(len) + "/" + num);

				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

}
