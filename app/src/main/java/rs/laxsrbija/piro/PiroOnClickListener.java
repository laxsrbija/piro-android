package rs.laxsrbija.piro;

import android.view.View;

/**
 * Created by LAX on 12.5.2016..
 * Project piro-android
 *
 * Type:
 * 0 - Regular click
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
        } else if (type == 1) {
            new PiroThermalLoadDataTask(address, piroComms.getContext()).execute();
        }
    }
}
