package tbc.uncagedmist.bhulekhkheshra.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {

    public static final String PRIVACY_URL =
            "https://docs.google.com/document/d/1-vjZGhK4EuVfs25YRSpjxnZxvCPoOff-N5psvab9nvQ/edit?usp=sharing";

    public static String CurrentStateName;
    public static String CurrentStateUrl;

    public static boolean isConnectedToInternet(Context context)    {

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null)    {

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if (info != null)   {

                for (int i = 0; i <info.length;i++)   {

                    if (info[i].getState() == NetworkInfo.State.CONNECTED)  {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
