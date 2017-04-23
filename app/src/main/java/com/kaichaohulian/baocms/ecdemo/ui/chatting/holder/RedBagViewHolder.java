package com.kaichaohulian.baocms.ecdemo.ui.chatting.holder;


import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;

public class RedBagViewHolder extends BaseHolder {

	public View chattingContent;

	public RelativeLayout relativeLayout;
	public TextView readTv;

	public TextView username,phone;

	public ImageView avater;


	/**
	 * @param type
	 */
	public RedBagViewHolder(int type) {
		super(type);

	}
	
	public BaseHolder initBaseHolder(View baseView , boolean receive) {
		super.initBaseHolder(baseView);

		chattingTime = (TextView) baseView.findViewById(R.id.chatting_time_tv);
		chattingUser = (TextView) baseView.findViewById(R.id.transfers_amount);

		username = (TextView) baseView.findViewById(R.id.chatting_id_username_tv);
		phone = (TextView) baseView.findViewById(R.id.chatting_id_phone_tv);
		avater = (ImageView) baseView.findViewById(R.id.chatting_id_avatar_iv);

		checkBox = (CheckBox) baseView.findViewById(R.id.chatting_checkbox);
		chattingMaskView = baseView.findViewById(R.id.chatting_maskview);
		chattingContent = baseView.findViewById(R.id.chatting_content_area);
		relativeLayout=(RelativeLayout) baseView.findViewById(R.id.re_rl);
//		if(!receive){
			readTv =(TextView) baseView.findViewById(R.id.tv_read_unread);
//		}
		if(receive) {
			type = 10;
			return this;
		}
		
		uploadState = (ImageView) baseView.findViewById(R.id.chatting_state_iv);
		progressBar = (ProgressBar) baseView.findViewById(R.id.uploading_pb);
		type = 11;
		return this;
	}


	/**
	 * 
	 * @return
	 */
	public ImageView getChattingState() {
		if(uploadState == null) {
			uploadState = (ImageView) getBaseView().findViewById(R.id.chatting_state_iv);
		}
		return uploadState;
	}
	
	/**
	 * 
	 * @return
	 */
	public ProgressBar getUploadProgressBar() {
		if(progressBar == null) {
			progressBar = (ProgressBar) getBaseView().findViewById(R.id.uploading_pb);
		}
		return progressBar;
	}

	@Override
	public TextView getReadTv() {
		return readTv;
	}
}
