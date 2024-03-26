package com.ar.vgmsistemas.task;

import android.os.AsyncTask;

import androidx.fragment.app.FragmentActivity;

import com.ar.vgmsistemas.bo.IEntityBo;
import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment;

import java.util.List;

public class LoadEntitiesTask <T> extends AsyncTask<Object, Object, Object> {
	private IEntityBo<T> entityBo;
	private LoadEntityTaskListener<T> listener;
	private List<T> listEntities;
	private String error;
	private FragmentActivity fragmentActivity;
	private ProgressDialogFragment dialogFragment;
	private boolean isOk = false;
	
	public LoadEntitiesTask(FragmentActivity fragmentActivity, IEntityBo<T> entityBo, LoadEntityTaskListener<T> listener) {
		this.entityBo = entityBo;
		this.listener = listener;
		this.fragmentActivity = fragmentActivity;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialogFragment = ProgressDialogFragment.newInstance("Cargando...");
		dialogFragment.show(fragmentActivity.getSupportFragmentManager(), "loadEntitiesDialog");
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		try{
			listEntities=entityBo.recoveryAllEntities();
		} catch (Exception exception){
			isOk = false;
			error = exception.toString();
		}finally{
			isOk= true;
		}
		
		
		return null;
		
	}
	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		if(isOk){
			listener.onDone(listEntities);
			
		} else {
			listener.onError(error);
		}
				
		dialogFragment.dismissAllowingStateLoss();
		
	}
	public interface LoadEntityTaskListener<T>{
		void onDone(List<T> entities);
		void onError(String error);
	}
}
