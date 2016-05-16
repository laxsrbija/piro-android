package rs.laxsrbija.piro;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

/**
 * Created by LAX on 7.4.2016..
 * Project piro-android
 */
public class PiroLoadDataUIThread {

    public static void runOnUIThreadSwitch(AppCompatActivity context, View view, String string) {
        ((ToggleButton) view).setChecked("1".equals(string));

        if ("1".equals(string))
            ((ToggleButton) view).setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        else
            ((ToggleButton) view).setTextColor(ContextCompat.getColor(context, R.color.white));
    }

    public static void runOnUIThreadTextView(View view, String string) {
        ((TextView) view).setText(string);
    }

    public static void runOnUIThreadImageView(AppCompatActivity context, View view, String icon) {
        Glide.with(context).load(context.getResources()
                .getIdentifier(icon, "drawable", context.getPackageName())).into((ImageView) view);
    }

}