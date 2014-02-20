package blog.rostova.app;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class End_send extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.end_send);
	    ImageButton  blgr, vk ;
	    
	    blgr = (ImageButton) findViewById(R.id.imageButton1);
	    vk = (ImageButton) findViewById(R.id.imageButton2);
	    
	    Button button = (Button) findViewById(R.id.button1);
	    
	   
	    blgr.setOnClickListener(new View.OnClickListener() {

      				public void onClick(View v) {
      					
      					startActivity(new Intent(Intent.ACTION_VIEW, 
      						    Uri.parse("http://blogrostova.ru/")));
      				}
	    
	    // TODO Auto-generated method stub
	});
	    
	    
	    vk.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					
					startActivity(new Intent(Intent.ACTION_VIEW, 
  						    Uri.parse("http://vk.com/blogrostova")));
				}

// TODO Auto-generated method stub
});
	    
	    button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				SharedPreferences Settings = getSharedPreferences("Set1", Context.MODE_PRIVATE);
				Editor editor = Settings.edit();
				editor.remove("message");
				editor.commit();
				
				Intent intent=new Intent(getApplicationContext(), BlogrostovaActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}

//TODO Auto-generated method stub
});    

}
	public void onBackPressed () {
		super.onBackPressed ();
		
	}
}