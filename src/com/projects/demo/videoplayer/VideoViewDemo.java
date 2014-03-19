/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.projects.demo.videoplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.projects.demo.R;

public class VideoViewDemo extends Activity implements OnBufferingUpdateListener {

    /**
     * TODO: Set the path variable to a streaming video URL or a local media
     * file path.
     */
    private String path = "http://192.168.1.201/Resources/c.mp4";
    private VideoView mVideoView;
    private Button play;
    private Button pause;
    
    MediaController mediaController;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.videoview);
        initView();
        mVideoView = (VideoView) findViewById(R.id.surface_view);

        if (path == "") {
            // Tell the user to provide a media file URL/path.
            Toast.makeText(
                    VideoViewDemo.this,
                    "Please edit VideoViewDemo Activity, and set path"
                            + " variable to your media file URL/path",
                    Toast.LENGTH_LONG).show();

        } else {

            /*
             * Alternatively,for streaming media you can use
             * mVideoView.setVideoURI(Uri.parse(URLstring));
             */
            //mVideoView.setVideoPath(path);
        	mVideoView.setVideoURI(Uri.parse(path));
            mediaController = new MediaController(this);
            //mediaController.setVisibility(View.INVISIBLE); 
            

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            mVideoView.setLayoutParams(lp);

            mVideoView.setMediaController(mediaController);
            mVideoView.requestFocus();
            //mVideoView.start();
        }
    }

	private void initView() {
		play = (Button)findViewById(R.id.play);
		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mVideoView.start();
			}
		});
		
		pause = (Button)findViewById(R.id.pause);
		pause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mVideoView.pause();
			}
		});
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		Log.d("buffer", percent + "%");
	}
}
