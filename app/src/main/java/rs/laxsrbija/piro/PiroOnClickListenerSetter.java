package rs.laxsrbija.piro;

import android.app.Activity;

/**
 * Created by LAX on 7.4.2016..
 * Project piro-android
 */
public class PiroOnClickListenerSetter {

    public static void setOnClickListener(Activity context, PiroComms piroComms) {

        context.findViewById(R.id.ledMain)
                .setOnClickListener(new PiroOnClickListener(piroComms, PiroConstants.SET_LED_MAIN, 0));
        context.findViewById(R.id.ledRight)
                .setOnClickListener(new PiroOnClickListener(piroComms, PiroConstants.SET_LED_RIGHT, 0));
        context.findViewById(R.id.ledLeft)
                .setOnClickListener(new PiroOnClickListener(piroComms, PiroConstants.SET_LED_LEFT, 0));

        context.findViewById(R.id.pc)
                .setOnClickListener(new PiroOnClickListener(piroComms, PiroConstants.TOGGLE_PC, 0));

        context.findViewById(R.id.thermalPower)
                .setOnClickListener(new PiroOnClickListener(piroComms, PiroConstants.THERMAL_POWER.concat("&m=A"), 1));
        context.findViewById(R.id.thermalDecrement)
                .setOnClickListener(new PiroOnClickListener(piroComms, PiroConstants.THERMAL_DECREMENT.concat("&m=A"), 1));
        context.findViewById(R.id.thermalIncrement)
                .setOnClickListener(new PiroOnClickListener(piroComms, PiroConstants.THERMAL_INCREMENT.concat("&m=A"), 1));

        context.findViewById(R.id.modeAuto)
                .setOnClickListener(new PiroOnClickListener(piroComms, PiroConstants.THERMAL_MODE_SET.concat("0&m=A"), 1));
        context.findViewById(R.id.modeDay)
                .setOnClickListener(new PiroOnClickListener(piroComms, PiroConstants.THERMAL_MODE_SET.concat("2&m=A"), 1));
        context.findViewById(R.id.modeNight)
                .setOnClickListener(new PiroOnClickListener(piroComms, PiroConstants.THERMAL_MODE_SET.concat("3&m=A"), 1));
        context.findViewById(R.id.modeFrost)
                .setOnClickListener(new PiroOnClickListener(piroComms, PiroConstants.THERMAL_MODE_SET.concat("4&m=A"), 1));

    }

}
