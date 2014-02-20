package blog.rostova.app;





import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class preferences extends Activity {

	SharedPreferences Settings;
	EditText FIO, adress, phone, email;
	 
	//SharedPreferences Adress;
	//SharedPreferences phone;
	//SharedPreferences email;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.preferences);
	    
	    String enter = getString (R.string.enter);
	    Toast toast = Toast.makeText(getApplicationContext(), 
	    		   "Обратите внимание!"+ enter + "1) Жалобы в ГИБДД принимаются только при наличии личных данных отправителя, которые вводятся в этом разделе нашей программы. Вы можете в любой момент отредактировать эти данные нажав на кнопку 'Настройки' на своём устройстве" +
	    enter + "2) Письмо в ГИБДД может быть отправлено только с Вашего личного почтового ящика, для его отправки на Вашем устройстве должна быть установленна почтовая программа", Toast.LENGTH_LONG); 
	    		toast.show(); 
	    EditText FIO = (EditText)findViewById(R.id.editText1);
		 EditText adress = (EditText)findViewById(R.id.editText2);
		 EditText phone = (EditText)findViewById(R.id.editText3);
		 EditText email = (EditText)findViewById(R.id.editText4);  
		 
	    Settings = getSharedPreferences("Set1", Context.MODE_PRIVATE);
	    
	    
	    FIO.setText(Settings.getString("FIO",""));
	    
	   
	    adress.setText(Settings.getString("adress", ""));
	    
	    
	    phone.setText(Settings.getString("phone",""));
	    
	   
	    email.setText(Settings.getString("email", ""));
	    
	    // TODO Auto-generated method stub
	    final Button button1 = (Button)findViewById(R.id.button1);	
		button1.setOnClickListener(new View.OnClickListener() {
				
			 public void onClick(View v) {
				 
				 
				 SaveAll ();
					finish();
			 }
		});
	}



public void SaveAll () {
	Editor editor = Settings.edit();
	 EditText FIO = (EditText)findViewById(R.id.editText1);
	 EditText adress = (EditText)findViewById(R.id.editText2);
	 EditText phone = (EditText)findViewById(R.id.editText3);
	 EditText email = (EditText)findViewById(R.id.editText4); 
	 
	editor.putString("FIO", FIO.getText().toString());
	editor.putString("adress", adress.getText().toString());
	editor.putString("phone", phone.getText().toString());
	editor.putString("email", email.getText().toString());
	
	editor.commit();
}


protected void onPause () {
	super.onPause ();
	SaveAll ();
}
protected void onResume () {
	super.onResume ();
	
}
protected void onStop () {
	super.onStop ();
	SaveAll ();
}
protected void onDestroy () {
	super.onDestroy ();
	SaveAll ();
}
public void onBackPressed () {
	super.onBackPressed ();
	SaveAll ();
}
}
