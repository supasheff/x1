package blog.rostova.app;


import java.util.Date;

//import com.sheff.game.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.text.format.DateFormat;

import android.view.ContextThemeWrapper;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.TextView;

public class SendMailActivity extends Activity {
	 EditText  emailtext, tema, time,date,addres_here;
	 ImageButton Image;
	 Button send;
	 SharedPreferences Settings;
	 String message, address, subject;
	 String currentDate;
     String currentTime;
     String enter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mail);

			// Наши поля и кнопка
	    	Settings = getSharedPreferences("Set1", Context.MODE_PRIVATE);
			send = (Button) findViewById(R.id.button1);
			address = Settings.getString("address", "");
			subject = Settings.getString("address", "");
			emailtext = (EditText) findViewById(R.id.emailtext);
			Image = (ImageButton) findViewById(R.id.imageButton1);
			tema = (EditText) findViewById(R.id.Tema);
			time = (EditText) findViewById(R.id.editText1);
			date = (EditText) findViewById(R.id.editText2 );
			addres_here = (EditText) findViewById(R.id.adres_here);
			
	           
	           currentDate = (String) DateFormat.format("dd-MM-yyyy" ,new Date());
	           currentTime = (String) DateFormat.format("kk:mm:ss",new Date());
	           enter = getString (R.string.enter);
	           time.setText(currentTime);
	           date.setText(currentDate);
			
			
			tema.setText("Сообщаю о злостном нарушении!");
			message = Settings.getString("message", "");
			
			
			
			
			    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			   
			    // Determine how much to scale down the image
			    int scaleFactor =1;
			 
			    // Decode the image file into a Bitmap sized to fill the View
			    bmOptions.inJustDecodeBounds = false;
			    bmOptions.inSampleSize = Settings.getInt("Scale", scaleFactor);
			    bmOptions.inPurgeable = true;
			    
			   
			    Bitmap bitmap = BitmapFactory.decodeFile(Settings.getString("Image_Adress", ""), bmOptions);
			    Image.setImageBitmap(bitmap);
			    
			    
			
			 final Button button1 = (Button)findViewById(R.id.button1);	
				button1.setOnClickListener(new View.OnClickListener() {
			
			

			
				public void onClick(View v) {
					MesCreate();
					MesSave();
					
					showDialog(1);
 
					
					
					
				}

				
			});
				
				
				Button button2 = (Button)findViewById(R.id.button2);	
				button2.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						MesCreate();
						MesSave();
						finish();
					}
					
				});
		}
	 @Override
	 protected Dialog onCreateDialog(int id) {
		 
		  
	 		
	 		 
		  switch(id) {
		 	case 1:
	
	 	
	 		
         //final EditText x = (EditText)findViewById(R.id.editText1);
            ContextThemeWrapper ctw = new ContextThemeWrapper( this,  R.style.dialogStyle);
            
            LayoutInflater inflater = getLayoutInflater();
            final View layout = inflater.inflate(
                 R.layout.dialog1, (ViewGroup)findViewById(R.id.DiaLay));
            
	 		final AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
	 		
	 		//View layout = View.inflate(this, R.layout.dialog1, null); 
	 		builder.setView(layout);
	 		builder.setMessage("Текст вашего письма");
	 		 
	 		//MesCreate();
	 		//MesSave();
							
	 		TextView t;
	 		t =  (TextView) layout.findViewById(R.id.DialogtextView1);
	 		t.setText(message);
	 		
	 		
	 		builder.setPositiveButton("Отправляем", new DialogInterface.OnClickListener() {
				
	 			 
       		

				public void onClick(DialogInterface dialog, int id) {
					// TODO Auto-generated method stub
					//list.set(z, ((EditText)findViewById(R.id.editText1)).getText().toString());
					send_mail();
					
				 dialog.cancel();
				}
				});
	 		
	 		
				builder.setNegativeButton("Вернуться к форме", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int id) {
						// TODO Auto-generated method stub
						dialog.cancel();
						
					}
					});
				builder.setCancelable(false);
	            return builder.create();
		 	default:
		 		return null; 
		  
		  }
			
		
}
	 private void MesCreate() {
		// TODO Auto-generated method stub
		 message = "                          Заявление. " +enter+
					" Я "+Settings.getString("FIO", "") + enter+
					" проживающий по адресу: " + Settings.getString("address", "") +enter+
					currentDate+"года, в " + currentTime + " по Московскому времени " +enter+
							" Находясь по адресу: " + addres_here.getText().toString() + enter+
							" стал свидетелем вопиющего нарушения правил, а именно: " + emailtext.getText().toString() +enter +
							"прошу Вас незамедлительно принять меры и известить меня об этом по моему почтовому либо электронному адресу :"
							+ Settings.getString("email", "") +enter + "С уважением, " + Settings.getString("FIO", "");
	}
	private void MesSave() {
		// TODO Auto-generated method stub
		Editor editor = Settings.edit();
		editor.putString("message", message);
		editor.commit();
	}
	private void send_mail() {
			// TODO Auto-generated method stub
			//emailIntent.setType("plain/text");\
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			// Кому
			
			emailIntent.setType("application/octet-stream");
			
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { subject });
			// Тема
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					tema.getText().toString());
			// Текст
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					message );
			// Картинка
			
			emailIntent.putExtra(android.content.Intent.EXTRA_STREAM,
					Uri.parse("file:" + Settings.getString("Image_Adress", "")));
			
			emailIntent.setType("application/octet-stream");
		
			// Поехали!
			
				
			SendMailActivity.this.startActivityForResult(Intent.createChooser(emailIntent,
					"Отправка письма..."), 1);
		}	 
	 
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	
		    
			
	    		
	    	
	    	
			
			
	    		
				startActivity(new Intent(getApplicationContext(), End_send.class));
				finish();
			    
			
	 }
	  @Override
		public boolean onCreateOptionsMenu(Menu menu) {
	    	super.onCreateOptionsMenu(menu);
	    	// TODO Auto-generated method stub
	    	MesCreate();
			MesSave();
	    	startActivity(new Intent(getApplicationContext(), preferences.class));
			return false;
		} 
}
	   


