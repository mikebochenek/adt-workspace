package com.example.test;

import java.util.Arrays;
import java.util.Collections;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private static final String TAG = "MyPuzzleActivity";
	
	private PuzzleTile currentSelection;
	private int selectionCount;
	private PuzzleTile[] tiles = new PuzzleTile[8];
	private ImageView[] images = new ImageView[8];
	private final int[] imageIds = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7, R.id.imageView8};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	generateTiles();
    	
    	for (int i = 0; i < imageIds.length; i++) {
        	ImageView imgView = (ImageView) findViewById(imageIds[i]);
        	images[i] = imgView;
        	
    		imgView.setOnTouchListener(new OnTouchListener() {
    			public boolean onTouch(View v, MotionEvent event) {
    				Log.i(TAG, " " + event.getDownTime() + " ms");
    				
    				if (v instanceof ImageView) {
    					
    					PuzzleTile tile = tiles[findIndex((ImageView)v)];
    					if (tile.isFaceup() && !tile.isMatched()) { 
    						((ImageView)v).setImageResource(R.drawable.question);
    						tile.setFaceup(false);
    						currentSelection = null;
    						selectionCount--;
    					} else if (selectionCount < 2) {
            		    	((ImageView)v).setImageResource(tile.getImageId());
            		    	tile.setFaceup(true);
            		    	tile.setLastTouchTS(event.getDownTime());
            		    	
            		    	if (currentSelection != null && tile.getImageId() == currentSelection.getImageId()) {
            		    		tile.setMatched(true);
            		    		currentSelection.setMatched(true);
            		    		currentSelection = null;
        						selectionCount = 0;
            		    	} else {
                		    	currentSelection = tile;
                		    	selectionCount++;
            		    	}
    					} else {
    						Log.i(TAG, "you idiot, you already selected two");
    					}
    					
    				}
    				return false;
    			}
    		});
    	}
    	

        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	private void generateTiles() {
    	for (int i = 0; i < imageIds.length; i++) {
    		tiles[i] = new PuzzleTile();
    	}
    	tiles[0].setImageId(R.drawable.luke);
    	tiles[1].setImageId(R.drawable.luke);
    	tiles[2].setImageId(R.drawable.babcia);
    	tiles[3].setImageId(R.drawable.babcia);
    	tiles[4].setImageId(R.drawable.dziadek);
    	tiles[5].setImageId(R.drawable.dziadek);
    	tiles[6].setImageId(R.drawable.daddy);
    	tiles[7].setImageId(R.drawable.daddy);
    	
    	Collections.shuffle(Arrays.asList(tiles));
    	
	}
    
    private int findIndex(ImageView v) {
    	for (int i = 0; i < images.length; i++) {
    		if (v.equals(images[i])) {
    			return i;
    		}
    	}
    	return -1;
    }
    
}
