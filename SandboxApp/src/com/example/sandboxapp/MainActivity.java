package com.example.sandboxapp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.Manager;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Bitmap bitMap;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	public void browseGallery(View view){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(intent, 1);

	}

	public void takeAPicture(View view){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 100);

	}

	public void search(View view){
		ImageView imageView = ((ImageView)findViewById(R.id.imageView1));

		//imageView.setImageBitmap(null);
		Manager manager = new Manager();
		bitMap = manager.search(bitMap);

		imageView.setImageBitmap(bitMap);

		Log.i(this.getClass().getName() , "Finished search method");
	}


	public void addToDb(View view){
		add(view);

		findViewById(R.id.autoCompleteTextView1).setVisibility(View.VISIBLE);
		findViewById(R.id.autoCompleteTextView2).setVisibility(View.VISIBLE);
		findViewById(R.id.button5).setVisibility(View.VISIBLE);
		findViewById(R.id.autoCompleteTextView1).setEnabled(true);
		findViewById(R.id.autoCompleteTextView2).setEnabled(true);
		findViewById(R.id.button5).setEnabled(true);
	}

	public void addToDbExecute(View view){
		String name = ((AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1)).getText().toString();
		String description = ((AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2)).getText().toString();

		Log.i(this.getClass().getName(), "Password: " + password);
		if(password.equals("12345")){
			Manager manager = new Manager();
			manager.AddToDB(bitMap, name, description);
		}
		else
			Toast.makeText(this, "Wrong password", Toast.LENGTH_LONG).show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//	super.onActivityResult(requestCode, resultCode, data);

		InputStream is = null;
		if(requestCode == 1 && resultCode == Activity.RESULT_OK){
			ImageView imageView = ((ImageView)findViewById(R.id.imageView1));
			try {
				is = getContentResolver().openInputStream(data.getData());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			bitMap = BitmapFactory.decodeStream(is);
			imageView.setImageBitmap(bitMap);
		}

		if(requestCode == 100 && resultCode == Activity.RESULT_OK){
			try{
				Toast.makeText(this, "saved to: \n" + data.getData(), Toast.LENGTH_LONG).show();
				ImageView imageView = ((ImageView)findViewById(R.id.imageView1));
				try {
					is = getContentResolver().openInputStream(data.getData());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				bitMap = BitmapFactory.decodeStream(is);
				imageView.setImageBitmap(bitMap);
			} catch(Exception e){

			}
		}

		((Button)findViewById(R.id.button3)).setEnabled(true);
		((Button)findViewById(R.id.button4)).setEnabled(true);
	}


	public void add(View view){

		final EditText input = new EditText(this);

		new AlertDialog.Builder(this)
		.setTitle("Enter password")
		.setMessage("Enter Password")
		.setView(input)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Editable value = input.getText();
				password = value.toString();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Do nothing.
			}
		}).show();


	}





}
