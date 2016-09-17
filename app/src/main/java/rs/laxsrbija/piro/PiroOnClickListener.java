package rs.laxsrbija.piro;

import android.app.Activity;
import android.net.Uri;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by LAX on 12.5.2016..
 * Project piro-android
 */
public class PiroOnClickListener implements View.OnClickListener {

    private Uri.Builder builder;
    private Activity context;
    private String function;
    private String argument;

    public PiroOnClickListener(Activity context, String function) {
        this.context = context;
        this.function = function;
        this.builder = PiroContract.buildPreliminaryURI(context);
        this.argument = null;
    }

    public PiroOnClickListener(Activity context, String function, String argument) {
        this.context = context;
        this.function = function;
        this.argument = argument;
        this.builder = PiroContract.buildPreliminaryURI(context);
    }

    @Override
    public void onClick(View v) {
        if (argument != null) {
            builder.appendQueryParameter(PiroContract.FUNCTION, function)
                    .appendQueryParameter(PiroContract.ARGUMENT, argument);
        } else {
            builder.appendQueryParameter(PiroContract.FUNCTION, function);
        }

        builder.build();
        Volley.newRequestQueue(context).add(new StringRequest(builder.toString(),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new DataLoadTask(context, DataLoadTask.MODE_REGULAR).execute();
            }
        }, null));

   }

    public static void setOnClickListener(Activity context) {

        context.findViewById(R.id.ledMain)
                .setOnClickListener(new PiroOnClickListener(context,
                        PiroContract.RELAY_TOGGLE_FUNCTION,
                        PiroContract.ARG_LED_CENTER));

        context.findViewById(R.id.ledRight)
                .setOnClickListener(new PiroOnClickListener(context,
                        PiroContract.RELAY_TOGGLE_FUNCTION,
                        PiroContract.ARG_LED_RIGHT));

        context.findViewById(R.id.ledLeft)
                .setOnClickListener(new PiroOnClickListener(context,
                        PiroContract.RELAY_TOGGLE_FUNCTION,
                        PiroContract.ARG_LED_LEFT));

        context.findViewById(R.id.pc)
                .setOnClickListener(new PiroOnClickListener(context,
                        PiroContract.PC_TOGGLE_FUNCTION));

        context.findViewById(R.id.thermalPower)
                .setOnClickListener(new PiroOnClickListener(context,
                        PiroContract.HEATING_TOGGLE_FUNCTION));

        context.findViewById(R.id.thermalDecrement)
                .setOnClickListener(new PiroOnClickListener(context,
                        PiroContract.HEATING_DECREMENT));

        context.findViewById(R.id.thermalIncrement)
                .setOnClickListener(new PiroOnClickListener(context,
                        PiroContract.HEATING_INCREMENT));

        context.findViewById(R.id.modeAuto)
                .setOnClickListener(new PiroOnClickListener(context,
                        PiroContract.HEATING_MODE, PiroContract.ARG_MODE_AUTO));

        context.findViewById(R.id.modeDay)
                .setOnClickListener(new PiroOnClickListener(context,
                        PiroContract.HEATING_MODE, PiroContract.ARG_MODE_DAY));

        context.findViewById(R.id.modeNight)
                .setOnClickListener(new PiroOnClickListener(context,
                        PiroContract.HEATING_MODE, PiroContract.ARG_MODE_NIGHT));

        context.findViewById(R.id.modeFrost)
                .setOnClickListener(new PiroOnClickListener(context,
                        PiroContract.HEATING_MODE, PiroContract.ARG_MODE_FROST));

    }

}
