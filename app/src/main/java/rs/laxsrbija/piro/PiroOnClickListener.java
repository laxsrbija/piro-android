package rs.laxsrbija.piro;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ToggleButton;

/**
 * Created by LAX on 12.5.2016..
 * Project piro-android
 *
 * Type:
 * 0 - Relay ToggleSwitch (changes color)
 * 1 - Thermal click (refreshes the thermal section)
 */
public class PiroOnClickListener implements View.OnClickListener {

    private String address;
    private PiroComms piroComms;
    private int type;

    PiroOnClickListener(PiroComms piroComms, String s, int type) {
        address = s;
        this.piroComms = piroComms;
        this.type = type;
    }

    @Override
    public void onClick(View v) {
        if (type == 0) {
            piroComms.getRequestQueue()
                    .add(PiroLoadData.reqestWithoutResponse(address));

            ToggleButton t = (ToggleButton) v;
            if (t.getCurrentTextColor() == ContextCompat
                    .getColor(piroComms.getContext(), R.color.white))
                t.setTextColor(ContextCompat.getColor(piroComms.getContext(), R.color.colorAccent));
            else
                t.setTextColor(ContextCompat.getColor(piroComms.getContext(), R.color.white));
        } else if (type == 1) {
            new PiroThermalLoadDataTask(address, piroComms.getContext()).execute();
        }
    }
}
