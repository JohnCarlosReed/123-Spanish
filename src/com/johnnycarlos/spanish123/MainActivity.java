package com.johnnycarlos.spanish123;

import java.io.IOException;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;


public class MainActivity extends Activity implements 
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener{
    
    private static final int SWIPE_MIN_DISTANCE = 120;
    
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
    private GestureDetectorCompat mDetector; 
    
    private SoundImage soundImage;

    private SoundPool soundPool;
    
    private ImageView imageView;
    
    private SoundImage[] soundImages;
    
    private int index = -1;
              

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDetector = new GestureDetectorCompat(this,this);

        mDetector.setOnDoubleTapListener(this);
        
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        loadSoundImages();
       
        imageView = (ImageView)findViewById(R.id.main_image_id);

    } 
   
    /**
     * This method loads all the SoundImages into a global array.
     */
    private void loadSoundImages(){
        try{
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor;
            
            descriptor = assetManager.openFd("n1.ogg");
            SoundImage n1 = new SoundImage( soundPool.load(descriptor, 1), R.drawable.n1);
            
            descriptor = assetManager.openFd("n2.ogg");
            SoundImage n2 = new SoundImage( soundPool.load(descriptor, 1), R.drawable.n2);

            descriptor = assetManager.openFd("n3.ogg");
            SoundImage n3 = new SoundImage( soundPool.load(descriptor, 1), R.drawable.n3);

            descriptor = assetManager.openFd("n4.ogg");
            SoundImage n4 = new SoundImage( soundPool.load(descriptor, 1), R.drawable.n4);

            descriptor = assetManager.openFd("n5.ogg");
            SoundImage n5 = new SoundImage( soundPool.load(descriptor, 1), R.drawable.n5);

            descriptor = assetManager.openFd("n6.ogg");
            SoundImage n6 = new SoundImage( soundPool.load(descriptor, 1), R.drawable.n6);

            descriptor = assetManager.openFd("n7.ogg");
            SoundImage n7 = new SoundImage( soundPool.load(descriptor, 1), R.drawable.n7);

            descriptor = assetManager.openFd("n8.ogg");
            SoundImage n8 = new SoundImage( soundPool.load(descriptor, 1), R.drawable.n8);

            descriptor = assetManager.openFd("n9.ogg");
            SoundImage n9 = new SoundImage( soundPool.load(descriptor, 1), R.drawable.n9);
            
            descriptor = assetManager.openFd("n10.ogg");
            SoundImage n10 = new SoundImage( soundPool.load(descriptor, 1), R.drawable.n10);

            
            soundImages = new SoundImage[]{ n1, n2, n3, n4, n5, n6, n7, n8, n9, n10 };
        } 
        catch(Exception e) {
           Log.d("loadSoundFiles Exception:", e.toString());
        }
    }

    @Override 
    public boolean onTouchEvent(MotionEvent event){ 
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) { 
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, 
    float velocityX, float velocityY) {
        
        if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
            Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            
            //From Right to Left
            playNextSoundImage();
            
            return true;
        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
            Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            
            //From Left to Right
            playPreviousSoundImage();
            
            return true;
        }
      
        if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
           Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            
            //From Bottom to Top
            playNextSoundImage();
        
            return true;
        
        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
            Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            
            //From Top to Bottom
            playPreviousSoundImage();
            
            return true;
        }
    
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        
        playPreviousSoundImage();
                
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {      
        return true;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        
        playNextSoundImage();
        
        return true;
    }

    public void playPreviousSoundImage(){
        
        index--;
        
        // If we reach beginning of SoundImage array, loop back to the end
        // subtract one to account for zero based array
        if( index < 0){
            index = ( soundImages.length - 1 );
        }
            
        soundImage = soundImages[index];

        // Draw the current letter
        Drawable image = getResources().getDrawable( soundImage.imageNumber );
        imageView.setImageDrawable(image);

        // Play the current sound
            soundPool.play(soundImage.poolNumber, 1, 1, 0, 0, 1);
        }
        
    
    public void playNextSoundImage(){
            
        index++;
            
        // If we reach the last SoundImage, go back to beginning
        if( index == soundImages.length ){
            index = 0;
        }          
            
        soundImage = soundImages[index];
            
        // Draw the current letter
        Drawable image = getResources().getDrawable( soundImage.imageNumber );
        imageView.setImageDrawable(image);

        // Play the current sound
        soundPool.play(soundImage.poolNumber, 1, 1, 0, 0, 1);

    }

} // end class
