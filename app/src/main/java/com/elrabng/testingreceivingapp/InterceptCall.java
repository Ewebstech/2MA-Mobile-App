package com.elrabng.testingreceivingapp;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.elrabng.testingreceivingapp.activities.CallerStatus;
import com.elrabng.testingreceivingapp.activities.Doctor2MAIntentService;
import com.elrabng.testingreceivingapp.activities.MainActivity;
import com.elrabng.testingreceivingapp.utilities.AppPreference;
import com.elrabng.testingreceivingapp.utilities.NetworkChecker;

import java.util.List;

public class InterceptCall extends BroadcastReceiver {
    static long start_time, end_time,off_hook_time;
    //String incomingNumbermine;
    @Override
    public void onReceive(final Context context, final Intent intent) {
//        if(isRunning(context)){
//            Intent i = new Intent(context,CallerStatus.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.putExtra("phonenumber", "08023119233");
//            context.startActivity(i);
//            Log.i("TAG","SYstem is running");
//        }else {
//            Intent i = new Intent(context,CallerStatus.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.putExtra("phonenumber", "08023119233");
//            Toast.makeText(context,"It is not running",Toast.LENGTH_SHORT).show();
//            context.startActivity(i);
//            Log.i("TAG","SYstem is not running");
//        }

        try{
            TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            telephony.listen(new PhoneStateListener(){
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    super.onCallStateChanged(state, incomingNumber);
//                    if (incomingNumber!=null){
//                        Log.i("TAG","incoming Number is not null"+incomingNumber);
//                        incomingNumbermine = incomingNumber;
//                    }else if (incomingNumber.equals("")) {
//                        Log.i("TAG","incoming Number is null"+incomingNumber);
//                    }

                    final String incomingNumberMine = incomingNumber;
                    //start_time = System.currentTimeMillis();
                    //Log.i("TAG","start time "+start_time);
                    String stateOfPhone = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    if (stateOfPhone.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){
                        if(!NetworkChecker.isNetworkAvailable(context)){
                            Toast.makeText(context,"No Internet connection to check 2MA clients",Toast.LENGTH_LONG).show();
                            return;
                        }
                        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                        boolean isPhoneLocked = keyguardManager.inKeyguardRestrictedInputMode();
                        if(isPhoneLocked){
                           Log.i("ME",isPhoneLocked+" my phone lock mode");
                           // Toast.makeText(context,"Phone is locked",Toast.LENGTH_SHORT).show();
                            Log.i("TAG","incomingNumber : "+incomingNumber);
                            if(incomingNumber!=null){
                                AppPreference.setClientNumber(incomingNumberMine);
                                Intent i = new Intent(context, CallerStatus.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("phonenumber", incomingNumber);
                                context.startActivity(i);
                            }
                        }else{
                            Log.i("ME",isPhoneLocked+" my phone lock mode");
                           // Toast.makeText(context,"Phone is not locked",Toast.LENGTH_SHORT).show();
                            Log.i("TAG","incomingNumber : "+incomingNumber);
                            if(incomingNumber!=null){
                                AppPreference.setClientNumber(incomingNumberMine);
                                Intent i = new Intent(context, CallerStatus.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                              //  i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                i.putExtra("phonenumber", incomingNumber);
                                context.startActivity(i);
                            }

                        }
                        Toast.makeText(context,"Searching for caller on 2MA Database",Toast.LENGTH_SHORT).show();
//                        Log.i("TAG","incomingNumber : "+incomingNumber);
//                        if(incomingNumber!=null){
//                            Intent i = new Intent(context, CallerStatus.class);
//                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            i.putExtra("phonenumber", incomingNumber);
//                            context.startActivity(i);
//                        }
                    }
                    else if (stateOfPhone.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                        Log.i("TAG","the saved number "+AppPreference.getClientNumber());
                        AppPreference.setClientNumber(incomingNumberMine);


                      //  Toast.makeText(context,"Call Ended",Toast.LENGTH_SHORT).show();
                      //  Log.i("TAG","start time "+off_hook_time);
                     //   off_hook_time = System.currentTimeMillis();
                    }

                    else if (stateOfPhone.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)){
                        Log.i("TAG","Incoming Number idele state "+incomingNumberMine);
                        AppPreference.setClientNumber(incomingNumberMine);
                        Log.i("TAG","the saved number idle state "+AppPreference.getClientNumber());
                        AppPreference.setClientNumber(incomingNumberMine);
                      //  Log.i("TAG","start time "+end_time);
                      //  end_time = System.currentTimeMillis();
                            //Toast.makeText(context,"idle",Toast.LENGTH_SHORT).show();

//                        if(!NetworkChecker.isNetworkAvailable(context)){
//                            Toast.makeText(context,"No Internet connection to check 2MA clients",
//                                    Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        Toast.makeText(context,"Incoming call!",Toast.LENGTH_SHORT).show();
//                        Log.i("TAG","incomingNumber : "+incomingNumber);
//                        if(incomingNumber!=null){
//                            Intent i = new Intent(context, CallerStatus.class);
//                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            i.putExtra("phonenumber", incomingNumber);
//                            context.startActivity(i);
//                        }
                    }
                    //System.out.println("incomingNumber : "+incomingNumber);
                }
            },PhoneStateListener.LISTEN_CALL_STATE);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

//    public boolean isRunning (Context cxt){
//        ActivityManager activityManager = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
//        for (ActivityManager.RunningTaskInfo task : tasks){
//          if  (cxt.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
//                    return true;
//        }
//        return false;
//    }
    private void showDialog() {
//       AlertDialog.Builder builder = new AlertDialog.Builder()
//        builder.setTitle("Confirmation").setMessage("Check your mail for your password")
//                .setPositiveButton("Email", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        return;
//                    }
//                });
//        builder.show();
    }
}
