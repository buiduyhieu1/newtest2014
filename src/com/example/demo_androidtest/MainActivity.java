package com.example.demo_androidtest;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable {

	// khai bao bien toan cuc
	int a = 0, b = 0, c;
	Button btnYes, btnNo;
	Boolean bAnswer = true, bEndGame = false;
	TextView tvQuestion, tvTime, tvPoint;
	int point = 0, time = 100;
	AlertDialog dial;
	Thread thd;

	Handler hnd = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				tvTime.setText(time + "");
			} else if (msg.what == 2) {
				dial.show();
			} else if (msg.what == 3) {
				tvPoint.setText(point + "");
			}
		}
	};

	// khai bao bien random 0 - 9
	Random rd = new Random();

	public String getCaculate() {
		do{
			a = rd.nextInt(10);
			b = rd.nextInt(10);
		}while(a == 0 && b == 0);
		
		if (rd.nextInt(10) >= 5) {
			bAnswer = true;
			c = a + b;
		} else {
			bAnswer = false;
			c = rd.nextInt((a + b) * 2);
			c = c - a - b;
			if (c == a + b)
				c = a + b - 1;
		}
		return String.valueOf(a) + " + " + String.valueOf(b) + " = "
				+ String.valueOf(c) + "?";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		dial = new AlertDialog.Builder(this).create();
		dial.setTitle("Notice");
		dial.setMessage("Game over!");
		dial.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});

		tvQuestion = (TextView) findViewById(R.id.tv_question);
		tvPoint = (TextView) findViewById(R.id.tv_point);
		tvTime = (TextView) findViewById(R.id.tv_time);
		btnYes = (Button) findViewById(R.id.btn_yes);
		btnNo = (Button) findViewById(R.id.btn_no);

		tvQuestion.setText(getCaculate());
		time = 100;
		point = 0;
		
		thd = new Thread(this);
		
		btnYes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bAnswer == true) {
					point++;
					tvPoint.setText(point + "");
					time = 100;
					if (!bEndGame){
						thd.start();
						bEndGame = true;
					}
				} else {
					if (point > 0) {
						point--;
						tvPoint.setText(point + "");
						time = 100;
					} else
						// Toast.makeText(getBaseContext(), "Game over!",
						// Toast.LENGTH_SHORT);
						dial.show();
				}
				tvQuestion.setText(getCaculate());

			}
		});

		btnNo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bAnswer == false) {
					point++;
					tvPoint.setText(point + "");
					time = 100;
					if (!bEndGame){
						thd.start();
						bEndGame = true;
					}
				} else {
					if (point > 0) {
						point--;
						tvPoint.setText(point + "");
						time = 100;
					} else
						// Toast.makeText(getBaseContext(), "Game over!",
						// Toast.LENGTH_SHORT);
						dial.show();
				}
				tvQuestion.setText(getCaculate());
			}
		});
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (point >= 0) {
			if (time > 0) {
				time--;
				Message msg = new Message();
				msg.what = 1;
				hnd.sendMessage(msg);
				try{
					Thread.sleep(5);
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}else{
				if (point>0){
					point--;
					time = 100;
					Message msg = new Message();
					msg.what = 3;
					hnd.sendMessage(msg);
				}else{
					Message msg = new Message();
					msg.what = 2;
					hnd.sendMessage(msg);
				}
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		point = -1;
		super.onDestroy();
	}
}
//chandai123
//dm chan dai

