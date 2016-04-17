package rs.laxsrbija.piro;

import android.app.Activity;
import android.view.View;

/**
 * Created by LAX on 7.4.2016..
 * Project piro-android
 */
public class PiroOnClickListeners {

    public static void setOnClickListener(Activity context, final PiroComms piroComms) {

        context.findViewById(R.id.ledMain).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                piroComms.getRequestQueue()
                        .add(PiroLoadData.reqestWithoutResponse(PiroConstants.SET_LED_MAIN));
            }
        });

        context.findViewById(R.id.ledRight).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                piroComms.getRequestQueue()
                        .add(PiroLoadData.reqestWithoutResponse(PiroConstants.SET_LED_RIGHT));
            }
        });

        context.findViewById(R.id.ledLeft).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                piroComms.getRequestQueue()
                        .add(PiroLoadData.reqestWithoutResponse(PiroConstants.SET_LED_LEFT));
            }
        });

    }

}
