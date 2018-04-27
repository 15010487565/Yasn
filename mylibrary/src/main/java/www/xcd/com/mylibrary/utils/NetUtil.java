package www.xcd.com.mylibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class NetUtil {
	/**
	 * 判断网络状态
	 * 
	 * @param context
	 * @date 2015年11月11日15:23:53
	 * @author qinqin
	 * @return boolean
	 */
	public static boolean getNetWorking(Context context) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			//新版本调用方法获取网络状态
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				Network[] networks = connectivityManager.getAllNetworks();
				NetworkInfo networkInfo;
				for (Network mNetwork : networks) {
					networkInfo = connectivityManager.getNetworkInfo(mNetwork);
					if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
						return true;
					}
				}
			}else {
				//否则调用旧版本方法
				if (connectivityManager != null) {
					NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
					if (info != null) {
						for (NetworkInfo anInfo : info) {
							if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
								Log.e("Network",
										"NETWORKNAME: " + anInfo.getTypeName());
								return true;
							}
						}
					}
				}
			}
			return false;
		}
		return false;
	}

}
