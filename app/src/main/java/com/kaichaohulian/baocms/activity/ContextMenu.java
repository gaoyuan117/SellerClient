/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

public class ContextMenu extends BaseActivity {

	private int position;
	
	@Override
	public void setContent() {
		String type = getIntent().getStringExtra("type");
		if (type.equals("TXT")) {
			setContentView(R.layout.context_menu_for_text);
		} else if (type.equals("LOCATION")) {
			setContentView(R.layout.context_menu_for_location);
		} else if (type.equals("IMAGE")) {
			setContentView(R.layout.context_menu_for_image);
		} else if (type.equals("VOICE")) {
			setContentView(R.layout.context_menu_for_voice);
		} else if (type.equals("VIDEO")) {
			setContentView(R.layout.context_menu_for_video);
		}

		/*
		switch (getIntent().getIntExtra("type", -1)) {
		case txtValue:
			setContentView(R.layout.context_menu_for_text);
			break;
		case EMMessage.Type.LOCATION.ordinal():
			setContentView(R.layout.context_menu_for_location);
			break;
		case EMMessage.Type.IMAGE.ordinal():
			setContentView(R.layout.context_menu_for_image);
			break;
		case EMMessage.Type.VOICE.ordinal():
			setContentView(R.layout.context_menu_for_voice);
			break;
			//need to support netdisk and send netsdk?
		case Message.TYPE_NETDISK:
		    setContentView(R.layout.context_menu_for_netdisk);
		    break;
		case Message.TYPE_SENT_NETDISK:
		    setContentView(R.layout.context_menu_for_sent_netdisk);
		    break;
		default:
			break;
		}
		*/
		position = getIntent().getIntExtra("position", -1);
	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {

	}

	@Override
	public void initEvent() {

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void copy(View view){
		setResult(ChatActivity.RESULT_CODE_COPY, new Intent().putExtra("position", position));
		finish();
	}
	public void delete(View view){
		setResult(ChatActivity.RESULT_CODE_DELETE, new Intent().putExtra("position", position));
		finish();
	}
	public void forward(View view){
		setResult(ChatActivity.RESULT_CODE_FORWARD, new Intent().putExtra("position", position));
		finish();
	}
	
	public void open(View v){
	    setResult(ChatActivity.RESULT_CODE_OPEN, new Intent().putExtra("position", position));
        finish();
	}
	public void download(View v){
	    setResult(ChatActivity.RESULT_CODE_DWONLOAD, new Intent().putExtra("position", position));
        finish();
	}
	public void toCloud(View v){
	    setResult(ChatActivity.RESULT_CODE_TO_CLOUD, new Intent().putExtra("position", position));
        finish();
	}
	
}
