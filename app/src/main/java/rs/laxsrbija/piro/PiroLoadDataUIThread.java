package rs.laxsrbija.piro;

import android.util.Log;
import android.view.View;
import android.widget.Switch;

/**
 * Created by LAX on 7.4.2016..
 * Project piro-android
 */
public class PiroLoadDataUIThread {

    public static void runOnUIThreadSwitch(View view, String string) {
        ((Switch) view).setChecked("1".equals(string));
        Log.v(PiroConstants.APP_NAME, "runOnUIThreadSwitch: " + string);
    }

}
