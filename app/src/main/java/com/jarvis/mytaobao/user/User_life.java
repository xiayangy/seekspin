package com.jarvis.mytaobao.user;

import android.app.Activity;
import android.os.Bundle;

import com.jarvis.MyView.ScratchTextView;
import com.seek.spin.R;

import java.util.Random;
public class User_life extends Activity {

	private ScratchTextView tv_Scratch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.user_life);
		tv_Scratch=(ScratchTextView) findViewById(R.id.tv_Scratch);
		tv_Scratch.initScratchCard(0xFFCECED1, 3, 1f);
		tv_Scratch.setText(str_reward[getRandom()]);
	}
	
	
	private String[] str_reward={"лл�ݹ�","��ϲ������5ë","��������","���㽱","���ý�","Srroy�������ɣ�","��ϲ��һ�Ƚ�","�ܱ�Ǹ","û�н�","����һƿ������"};
	
	
	private int getRandom(){
		Random random=new Random();
		int number=random.nextInt(10);
		
		return number;
	}

}
