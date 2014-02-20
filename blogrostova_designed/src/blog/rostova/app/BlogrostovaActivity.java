package blog.rostova.app;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.TextView;



public class BlogrostovaActivity extends Activity {
	
	SharedPreferences Settings;
	final int CAMERA_CAPTURE = 1;
	final int GALLERY_REQUEST = 2;
	final int PIC_CROP = 2;
	private Uri picUri;
	
	EditText Message;
	TextView Text1,Text2,Text3,Text4;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        Message = (EditText) findViewById(R.id.editText1);
        Settings = getSharedPreferences("Set1", Context.MODE_PRIVATE);
        if (Settings.contains("FIO")) {
        	
        }
        else  {
        	startActivity(new Intent(getApplicationContext(), preferences.class));
        }
      
		// проверка
			 //if (schet.contains(String.valueOf(2))) {
     
        ImageButton imageButton1 = (ImageButton)findViewById(R.id.imageButton1);	
        imageButton1.setOnClickListener(new View.OnClickListener() {

      				public void onClick(View v) {
      					// TODO Auto-generated method stub
      					try {
      						// Намерение для запуска камеры
      						Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      						//Генерим адрес файла
      						picUri = generateFileUri();
      						//заливаем адрес в Shared Preferences
      						Editor editor = Settings.edit();
      						editor.putString("Image_Adress", picUri.getEncodedPath());
      						editor.commit();
      						
      						captureIntent.putExtra("output", picUri);
      						//ImageButton picView = (ImageButton)findViewById(R.id.imageButton1);
      		    			//picView.setImageURI(picUri);
      						startActivityForResult(captureIntent, CAMERA_CAPTURE);
      						
      						
      						
      					} catch (ActivityNotFoundException e) {
      						// Выводим сообщение об ошибке
      						//String errorMessage = "Ваше устройство не поддерживает съемку";
      						//Toast toast = Toast.makeText(View, errorMessage, Toast.LENGTH_SHORT);      
      						//toast.show();
      					}	
      					
      				}
      				
      			
      				
      				// }
          });
        
        imageButton1.setOnLongClickListener(new View.OnLongClickListener() {
        	
        	public boolean onLongClick(View v) {
      			
  				try {
  				Intent pickPhoto = new Intent(Intent.ACTION_PICK);
  			//Генерим адрес файла
					picUri = generateFileUri();
					//заливаем адрес в Shared Preferences
					Editor editor = Settings.edit();
					editor.putString("Image_Adress", picUri.getEncodedPath());
					editor.commit();
					
  				pickPhoto.setType("image/*");
  				startActivityForResult(pickPhoto, GALLERY_REQUEST);
  				
  				} catch (ActivityNotFoundException e) {
  					
  				}
  				
  			return true;
  			
  			}
        });
      		
       
      		
     

	
  
			
    }
 
  
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	// TODO Auto-generated method stub
    	startActivity(new Intent(getApplicationContext(), preferences.class));
		return false;
	}
	
    
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    
		
		//ImageButton picView = (ImageButton)findViewById(R.id.imageButton1);
    	
		switch (requestCode) {
    	
    	case CAMERA_CAPTURE:
    		
    	
    	
		
		if (resultCode == RESULT_OK) {
    		
    			
    			    // показываем в кнопке
			//picView.destroyDrawingCache();
			PictureSave(picUri,picUri);
    			   
    			   // BB.recycle();
    			    System.gc();
    			    startActivity(new Intent(getApplicationContext(), SendMailActivity.class));   
    			    }
    	break;	    
    		case GALLERY_REQUEST:
    			
    			if (resultCode == RESULT_OK) {
    			//Uri photoUri = Uri.parse("file://" + data.getData().getEncodedPath() + ".jpg");
    				Uri photoUri =Uri.parse(convertMediaUriToPath(data.getData()));
    				//picView.destroyDrawingCache();
    				PictureSave(photoUri,picUri);
    			 
    			System.gc();
    			//BB.recycle();
    			startActivity(new Intent(getApplicationContext(), SendMailActivity.class));  
    			}
    	break;
		}    
    	
    	}
    

   
	

    private Uri generateFileUri() {

    	  if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
    	    return null;

    	  File path = new File (Environment.getExternalStorageDirectory(), "blog");
    	  if (! path.exists()){
    	    if (! path.mkdirs()){
    	      return null;
    	    }
    	  }
    	            
    	  String timeStamp = String.valueOf(System.currentTimeMillis());
    	  File newFile = new File(path.getPath() + File.separator + timeStamp + ".jpg");
    	  return Uri.fromFile(newFile);
    	}

    protected void onResume () {
    	super.onResume ();
    	
    	
    }
    protected void onPause () {
    	super.onPause ();
    	
    }
    
    private void PictureSave  (Uri uri, Uri uri1)  {
    
    	
    	Bitmap  bitmap1 = null, bitmap2 = null, decodedBitmap = null;
    	//Bitmap bitmap, bitmap1, decodedBitmap;
    	
    	int photoW = 0;
	    int photoH = 0;
	    int photoW1 = 0;
	    int photoH1 = 0;
	    
    	//вычисляем размер дисплея    
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics metricsB = new DisplayMetrics();
		display.getMetrics(metricsB);

		    // Get the dimensions of the View
		    int targetW = metricsB.widthPixels;
		    int targetH = metricsB.heightPixels;
		
		    
		   
		    //выясняем ориентацию 
		    int orientation = getOrientation(this,uri);
		    
		   // photoW = bitmap1.getWidth();
    		//photoH = bitmap1.getHeight();
		    
		    BitmapFactory.Options bmO = new BitmapFactory.Options();
		    
		    // Decode the image file into a Bitmap sized to fill the View
		   bmO.inJustDecodeBounds = false;
		   bmO.inPreferredConfig = Config.RGB_565;
		   bmO.inPurgeable = true;	
		       
		   try {
				InputStream in = new FileInputStream(uri.getEncodedPath());
				bitmap1 = BitmapFactory.decodeStream(in, null, bmO);
				in.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		   
		   
		    //поворачиваем если надо
		    if (orientation > 0) {
		    	Matrix matrix = new Matrix();
		        matrix.postRotate(orientation);
		        	 

		       
		        //Bitmap decodedBitmap = bitmap1;
		     decodedBitmap = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(),
		    		 bitmap1.getHeight(), matrix, true);
		    // bitmap1 = decodedBitmap;
		        //рецайклим оригинальный битмап за ненадобностью
		       //if (decodedBitmap != null && !decodedBitmap.equals(bitmap1)) {
		    	 
		       // }
		    //bitmap1.recycle();
		   }
		    else
		    {
		    	 
		    	decodedBitmap = bitmap1;	
		    	//bitmap1.recycle();
		    }
		    
		    
		    
		    //if (orientation == 90 || orientation == 270) {
		   //	photoW = decodedBitmap.getHeight();
			 //  photoH = decodedBitmap.getWidth();
		    //	} else {
		    		photoW = decodedBitmap.getWidth();
		    		photoH = decodedBitmap.getHeight();
		  // 	}
		    
		    		if (photoW>photoH) {
				    	photoW1 = 1200;
						photoH1 =(int) ( 1200*(((float)photoH)/((float)photoW)));
				    }
				    else {
				    	photoH1 = 1200;
				    	photoW1 =(int) ( 1200*(((float)photoW)/((float)photoH)));
				    }
		    		
		    		//компрессим
		    		bitmap2 = Bitmap.createScaledBitmap(decodedBitmap, photoW1, photoH1, true);
					//decodedBitmap.recycle();
					decodedBitmap.recycle();
					bitmap1.recycle();
					decodedBitmap = null;
					bitmap1 =null;
		    
		    
		    //сохраняем с компрессией
		    File file = new File(uri1.getEncodedPath());
		    FileOutputStream fOut = null;
		    //int size = (int) file.length();
		    
    
		   
			try {
				fOut = new FileOutputStream(file);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // сохранять картинку в jpeg-формате с compress сжатия.
			
	       try {
				fOut.flush();
				 fOut.close();
			} catch (IOException e) {
		//		// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       bitmap2.recycle();
	       bitmap2=null;
	      
	      
		    // Determine how much to scale down the image
	      int scaleFactor =1;
		 // if ((photoW1<(targetW/2))&&(photoH1<(targetH/2)))  {
			  
		 // }
		 // else {
	     scaleFactor =(int) (2 + Math.max((1.5*photoW1/(targetW)),1.5*photoH1/(targetH)));
		 // }
		 //сохраняем scalefactor для sendmailactivity
	     Editor editor = Settings.edit();
			editor.putInt("Scale", scaleFactor);
			editor.commit();
			
		   
    }
    private static int getOrientation(Context context, Uri uri) {
        if ("content".equals(uri.getScheme())) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);
            
            if (cursor == null || cursor.getCount() != 1) {
                return -1;
            }
    
            cursor.moveToFirst();
            int orientation = cursor.getInt(0);
            cursor.close();
            return orientation;
        }
        else {
            try {
                ExifInterface exif = new ExifInterface(uri.getPath());
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        return 270;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        return 180;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        return 90;
                    case ExifInterface.ORIENTATION_NORMAL:
                        return 0;
                    default:
                        return -1;
                }
            } catch (IOException e) {
                return -1;
            }
        }
    }
    protected String convertMediaUriToPath(Uri uri) { // Thanx to Jonathon Horsman from stack overflow 4 this
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj,  null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index); 
        cursor.close();
        return path;
    }
}


