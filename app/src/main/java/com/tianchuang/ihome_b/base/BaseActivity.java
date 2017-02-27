package com.tianchuang.ihome_b.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.TianChuangApplication;
import com.tianchuang.ihome_b.permission.MPermission;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.MaterialDialogsUtil;


/**
 * Created by Abyss on 2017/2/8.
 * description:FragmentActivity的基类
 */

public abstract class BaseActivity extends RxFragmentActivity {
	public MaterialDialogsUtil getMaterialDialogsUtil() {
		return materialDialogsUtil;
	}

	private MaterialDialogsUtil materialDialogsUtil;

	//布局文件ID
	protected abstract int getLayoutId();

	//添加Fragment容器的ID
	protected abstract int getFragmentContainerId();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		materialDialogsUtil = new MaterialDialogsUtil(this);
		TianChuangApplication.application.addActivity(this);

	}

	//添加fragment
	protected Fragment addFragment(BaseFragment fragment) {
		Fragment i = null;
		if (fragment != null) {
			i = FragmentUtils.replaceFragment(getSupportFragmentManager(), getFragmentContainerId(), fragment, true);
		}
		return i;
	}

	//移除fragment
	protected void removeFragment() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
			FragmentUtils.popFragment(getSupportFragmentManager());
		} else {
			finish();
		}
	}

	/**
	 * 申请权限
	 *
	 * @param permissionRequestCode 权限的标识码
	 * @param manifestStrings       权限对应的stirng
	 */
	public void requestPermission(int permissionRequestCode, String[] manifestStrings) {
		MPermission.with(this)
				.addRequestCode(permissionRequestCode)
				.permissions(manifestStrings)
				.request();
	}


	//权限提示框
	@RequiresApi(api = Build.VERSION_CODES.M)
	public void showPermissionInfo(String permissionContent, final boolean isCloese) {
		if (materialDialogsUtil == null)
			materialDialogsUtil = new MaterialDialogsUtil(this);
//		int color = getResources().getColor(R.color.chartreuse);
		int titleColor = getResources().getColor(R.color.TC_1);
		int contentColor = getResources().getColor(R.color.TC_2);
		materialDialogsUtil.getBuilder(getString(R.string.perssion_tip), permissionContent, getString(R.string.perssion_go_setting), getString(R.string.perssion_cancel))
				.negativeColor(titleColor)
				.positiveColor(titleColor)
				.titleColor(titleColor)
				.contentColor(contentColor)
				.cancelable(false).callback(new MaterialDialog.ButtonCallback() {
			@Override
			public void onPositive(MaterialDialog dialog) {
				dialogDismiss(isCloese);
			}

			@Override
			public void onNeutral(MaterialDialog dialog) {
			}

			@Override
			public void onNegative(MaterialDialog dialog) {
				materialDialogsUtil.dismiss();
				if (isCloese) {
					finish();
				}
			}
		});
		materialDialogsUtil.show();
	}

	private void dialogDismiss(final boolean isCloese) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent localIntent = new Intent();
				localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				if (Build.VERSION.SDK_INT >= 9) {
					localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
					localIntent.setData(Uri.fromParts("package", getPackageName(), null));
				} else if (Build.VERSION.SDK_INT <= 8) {
					localIntent.setAction(Intent.ACTION_VIEW);
					localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
					localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
				}
				startActivity(localIntent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				if (isCloese) {
					finish();
				}
			}
		}, 500);
		materialDialogsUtil.dismiss();
	}


	public void startActivityWithAnim(Intent intent) {
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	private MaterialDialog progressDialog;
	public void showProgress() {
		if (progressDialog == null) {
			progressDialog = getMaterialDialogsUtil().getProgressDialog();
		}
		progressDialog.show();
	}
	public void dismissProgress() {
		if (progressDialog!=null)
			progressDialog.dismiss();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		TianChuangApplication.application.removeActivity(this);
	}
}
