package com.ar.vgmsistemas.task;

import android.os.AsyncTask;

import androidx.fragment.app.FragmentActivity;

import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment;

//import static com.google.android.gms.internal.zzip.runOnUiThread;



public class LoadResumenCobranzaTask extends AsyncTask<Void, Void, Void> {
	private final CargarResumenCobranzaListener mListener;
	private final FragmentActivity mFragmentActivity;
	private ProgressDialogFragment dialogFragment;

	public LoadResumenCobranzaTask(CargarResumenCobranzaListener listener, FragmentActivity fragmentActivity){
		mListener = listener;
		mFragmentActivity = fragmentActivity;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialogFragment = ProgressDialogFragment.newInstance("Cargando...");
		dialogFragment.show(mFragmentActivity.getSupportFragmentManager(), "loadEntitiesDialog");
	}
	protected void onProgressUpdate(Integer... progress) {
	}

	@Override
	protected void onPostExecute(Void unused) {
		/*if (mListener != null){
			dialogFragment.dismiss();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mListener.onDone();
				}

			});
		}*/
		super.onPostExecute(unused);
		if(mListener != null){
			dialogFragment.dismiss();
			mListener.onDone();
		}
	}

	@Override
	protected Void doInBackground(Void... voids) {
		mListener.loadData();
		return null;
	}

	public interface CargarResumenCobranzaListener{
		void onDone();
		void onError(String error);
		void loadData();
	}


	
}
