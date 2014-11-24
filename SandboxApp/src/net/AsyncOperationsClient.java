package net;

import com.example.sandboxapp.MainActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.Window;

public class AsyncOperationsClient extends
			AsyncTask<String/* Param */, Boolean /* Progress */, String /* Result */> {

	private Manager manager = new Manager();
	private Bitmap bitMap;

	@Override
	protected String doInBackground(String... params) {
		publishProgress(true);
		// Do the usual httpclient thing to get the result
		bitMap = manager.search(bitMap);
		return "";
	}

	@Override
	protected void onProgressUpdate(Boolean... progress) {
		// line below coupled with 
		  //  getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS) ;
		//    before setContentView 
		// will show the wait animation on the top-right corner
		//MainActivity.this.setProgressBarIndeterminateVisibility(progress[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		publishProgress(false);
		// Do something with result in your activity
	}



}
