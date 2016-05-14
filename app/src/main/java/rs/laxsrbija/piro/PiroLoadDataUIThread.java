package rs.laxsrbija.piro;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by LAX on 7.4.2016..
 * Project piro-android
 */
public class PiroLoadDataUIThread {

    public static void runOnUIThreadSwitch(View view, String string) {
        ((Switch) view).setChecked("1".equals(string));
    }

    public static void runOnUIThreadTextView(View view, String string) {
        ((TextView) view).setText(string);
    }

    public static void runOnUIThreadImageView(AppCompatActivity context, View view, String icon) {
        Glide.with(context).load(context.getResources()
                .getIdentifier(icon, "drawable", context.getPackageName())).into((ImageView) view);
    }

}