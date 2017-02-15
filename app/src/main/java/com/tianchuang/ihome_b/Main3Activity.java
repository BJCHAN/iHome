package com.tianchuang.ihome_b;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.MainFragment;

/**
 * Created by Abyss on 2017/2/9.
 * description:主页
 */
public class Main3Activity extends ToolBarActivity {

	@Override
	protected BaseFragment getFirstFragment() {
		return MainFragment.newInstance();
	}

	@Override
	protected void initToolBar(Toolbar toolbar) {
		toolbar.setTitle("主页");
		toolbar.setTitleTextColor(Color.WHITE);
		// 设置导航按钮
		toolbar.setNavigationIcon(R.mipmap.menu);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(Main3Activity.this, "Navigation", Toast.LENGTH_SHORT).show();
			}
		});


		// 设置菜单及其点击监听
		toolbar.inflateMenu(R.menu.ac_toolbar_menu);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				String result = "";
				switch (item.getItemId()) {
					case R.id.ac_toolbar_copy:
						result = "Copy";
						break;
					case R.id.ac_toolbar_cut:
						result = "Cut";
						break;
					case R.id.ac_toolbar_del:
						result = "Del";
						break;
					case R.id.ac_toolbar_edit:
						result = "Edit";
						break;
					case R.id.ac_toolbar_email:
						result = "Email";
						break;
				}
				Toast.makeText(Main3Activity.this, result, Toast.LENGTH_SHORT).show();
				return true;
			}
		});
	}
}
