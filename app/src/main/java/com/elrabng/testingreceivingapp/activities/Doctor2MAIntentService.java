package com.elrabng.testingreceivingapp.activities;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.elrabng.testingreceivingapp.interfaces.API;
import com.elrabng.testingreceivingapp.model.CallerRequest;
import com.elrabng.testingreceivingapp.model.CallerResponse;
import com.elrabng.testingreceivingapp.model.ContentModel;
import com.elrabng.testingreceivingapp.model.ContentModel2;
import com.elrabng.testingreceivingapp.model.LoginResponse;
import com.elrabng.testingreceivingapp.network.RetrofitService;
import com.elrabng.testingreceivingapp.utilities.AppPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.elrabng.testingreceivingapp.utilities.UIComponentandDebug.isNull;

public class Doctor2MAIntentService  extends IntentService {
    private Context context;
    String doctorId;
    public static final String GET_CALLER= "GET_CALLER";
    public static final String ACTION_TO_PERFORM = "com.elrabng.testingreceivingapp";
    public Doctor2MAIntentService() {
        super("test-intent-service");
    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        context = getApplicationContext(); // If a Context object is needed, call getApplicationContext() here.
    }

    @Override
    protected void onHandleIntent(Intent intent) {
       String phoneNumber = intent.getStringExtra("phonenumber");
        //Log.i("TAG",phoneNumber+" My phone number recived");
        LoginResponse loginResponse = AppPreference.getUserData();
        if (loginResponse!=null){
            doctorId= loginResponse.getData().getClientId();
        }
        String ACTION = intent.getStringExtra(ACTION_TO_PERFORM);

        switch (ACTION) {
            case GET_CALLER:
                getCaller(doctorId,phoneNumber);
                break;

            default:
                break;
        }
    }


    public void getCaller(String doctorId, String phonenumber){
        API api = RetrofitService.initializer();
        DisposableSingleObserver<CallerResponse> a = getCallerInfo();
        api.getCallerInfoBgMethod(new CallerRequest(doctorId,phonenumber))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(a);
    }


    private DisposableSingleObserver<CallerResponse> getCallerInfo() {
        return new DisposableSingleObserver<CallerResponse>() {
            @Override
            public void onSuccess(CallerResponse value) {
                Log.i("TAG", "WithdrawHistoryResponse Successful");
                //AppPreference.setUserLocation(value);
                if(value.isStatus()){
                    Log.i("TAG","It is succesful block");
                        Log.i("TAG","boolean success");
                        CallerResponse.Data detailsReceived = value.getData();
                        if(detailsReceived!=null){
                          String clientName = detailsReceived.getClientName();
                           String  subStatus = detailsReceived.getSubStatus();
                            String phoneNumber = detailsReceived.getClientPhoneNumber();
                           String  caseID = detailsReceived.getCaseId();
                           String content = detailsReceived.getContentModel2();
                           String imageUrl = detailsReceived.getAvatar();
                            Gson gson = new GsonBuilder().create();
                            ContentModel2 userDataResponse = gson.fromJson(content,ContentModel2.class);

                            String medicalNeedSp = isNull(userDataResponse.getMedicalNeedSP())? "No Data":userDataResponse.getMedicalNeedSP();
                            String severeSp = isNull(userDataResponse.getSevereSP())? "No Data":userDataResponse.getSevereSP();
                            String allergyFamilySp = isNull(userDataResponse.getAllergySP())? "No Data":userDataResponse.getAllergySP();
                            String takenTreament = isNull(userDataResponse.getTakenTreatment())? "No Data":userDataResponse.getTakenTreatment();
                            String allergies = isNull(userDataResponse.getAllergies())? "No Data":userDataResponse.getAllergies();
                            String otherInfo = isNull(userDataResponse.getOtherInfo())? "No Data":userDataResponse.getOtherInfo();

//                            String medicalNeedSp = "No Data";
//                            String severeSp ="No Data";
//                            String allergyFamilySp = "No Data";
//                            String takenTreament = "No Data";
//                            String allergies ="No Data";
//                            String otherInfo = "No Data";


                            String diffrential = "1";
                           // TODO Apprefrence to be used to save caller caseId;
                            Bundle bundle = new Bundle();
                            bundle.putString("clientName",clientName);
                            bundle.putString("subStatus",subStatus);
                            bundle.putString("phoneNumber",phoneNumber);
                            bundle.putString("imageUrl",imageUrl);
                            bundle.putString("diffrential",diffrential);

                            bundle.putString("medicalNeed",medicalNeedSp);
                            bundle.putString("severe",severeSp);
                            bundle.putString("allergyFamily",allergyFamilySp);
                            bundle.putString("takenTreatment",takenTreament);
                            bundle.putString("allergies",allergies);
                            bundle.putString("otherInfo",otherInfo);

                            AppPreference.setCaseId(caseID);
                            bundle.putString("caseID",caseID);
                            Log.i("TAG",clientName);
                            Log.i("TAG",subStatus);
                            Log.i("TAG",phoneNumber);
                            Log.i("TAG",caseID);

                            Intent i = new Intent(context, CallerIdentified.class).putExtras(bundle);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                          //  Toast.makeText(context,"2MA Caller",Toast.LENGTH_LONG).show();
                            stopSelf();
//                            if (subStatus.equals("Active")){
//                                clientNameTv.setText(clientName);
//                                subStatusTv.setText(subStatus);
//                                clientNumberTv.setText(phoneNumber);
//                                callerInfoPage.setVisibility(View.VISIBLE);
//                                // TODO after this action is done a button can be enabled to send the
//                                // TODO information about a call
//                            }else if(subStatus.equals("InActive")){
//                                clientNameInactiveTv.setText(clientName);
//                                clientNumberInactiveTV.setText(phoneNumber);
//                                subStatusInactiveTv.setText(subStatus);
//                                callerInfoPageInactive.setVisibility(View.VISIBLE);
//                                // TODO The call will need to be terminated here.
//                            }
                        }
                        else {
                            Log.i("TAG","data is null for receiver");
                        }
                }else {
                    Log.i("TAG","error block 400");
                    if(value.getCode() == 400){
                        String message = value.getMessage();
                        Log.i("TAG",message);
                        String diffrential = "2";
                        Bundle bundle = new Bundle();
                        bundle.putString("messageError",message);
                        bundle.putString("diffrential",diffrential);

                        Intent i = new Intent(context, CallerIdentified.class).putExtras(bundle);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);

                        // not selcted a package or something
//                        Log.i("TAG","error 400");
//                        Gson gson = new GsonBuilder().create();
//                        try{
//                            CallerResponse error = gson.fromJson(response.errorBody().string(),CallerResponse.class);
//                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        }catch (Exception ex){
//                            ex.printStackTrace();
//                        }
//                        Toast.makeText(getApplicationContext(), "No consultancy case found for" +
//                                        " this client. Exit Process now!",
//                                Toast.LENGTH_SHORT).show();
                        //finishAffinity();
                    }
                    else if (value.getCode()== 422){
                        // empty field error
                        Log.i("TAG","error 422");
                        Toast.makeText(getApplicationContext(), "A field is missing", Toast.LENGTH_SHORT).show();
//                        Gson gson = new GsonBuilder().create();
//                        try{
//                            CallerErrorResponse error = gson.fromJson(value.getMessage(),CallerErrorResponse.class);
//                            List<String> errorObtained = error.getMessage();
//                            for(String errorMessage:errorObtained){
//                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
//
//                            }
//                        }catch (Exception ex){
//                            ex.printStackTrace();
//                        }
                        // finishAffinity();
                    }
                    else if (value.getCode()== 404){
                        // empty field error
                        Log.i("TAG","error 404");
                        Toast.makeText(getApplicationContext(),"This is not a 2MA client",
                                Toast.LENGTH_LONG).show();
                        //  finishAffinity();
                    }
                    else{
                        Log.i("TAG","error unknown");
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
               // ToastUtils.showErrorMessageToast(getApplication(),"Zone Fail to load");
//                Toast.makeText(getApplication(), "No consultancy case found for this client." +
//                        " Exit Process now!", Toast.LENGTH_SHORT).show();
              //  Log.i("TAG", e.getMessage());

                Log.i("TAG","on failure");
              //  stopSelf();
                String error = e.getMessage();
               if(error.equals("HTTP 400 Bad Request")){
                   String message = "Could not retrieve user details.";
                   Log.i("TAG",message);
                   String diffrential = "2";
                   Bundle bundle = new Bundle();
                   bundle.putString("messageError",message);
                   bundle.putString("diffrential",diffrential);

                   Intent i = new Intent(context, CallerIdentified.class).putExtras(bundle);
                   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(i);

                   Log.i("TAG", e.getMessage());}

                   else if(error.equals("HTTP 404 Not Found")){
                   String message = "Caller is not a 2MA Client!";
                   Log.i("TAG",message);
                   String diffrential = "2";
                   Bundle bundle = new Bundle();
                   bundle.putString("messageError",message);
                   bundle.putString("diffrential",diffrential);

                   Intent i = new Intent(context, CallerIdentified.class).putExtras(bundle);
                   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(i);
                   stopSelf();

                   Log.i("TAG", e.getMessage());
                }

                else if (error.equals("HTTP 403 Forbidden")){
                   String message = "This Client is new and has not subscribed. Please Terminate Call.";
                   Log.i("TAG",message);
                   String diffrential = "2";
                   Bundle bundle = new Bundle();
                   bundle.putString("messageError",message);
                   bundle.putString("diffrential",diffrential);

                   Intent i = new Intent(context, CallerIdentified.class).putExtras(bundle);
                   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(i);
                   stopSelf();

                   Log.i("TAG", e.getMessage());

               }
               else if (error.equals("HTTP 401 Unauthorized")){
                   String message = "Case not Created. Call cannot proceed. Please Terminate.";
                   Log.i("TAG",message);
                   String diffrential = "2";
                   Bundle bundle = new Bundle();
                   bundle.putString("messageError",message);
                   bundle.putString("diffrential",diffrential);

                   Intent i = new Intent(context, CallerIdentified.class).putExtras(bundle);
                   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(i);
                   stopSelf();

                   Log.i("TAG", e.getMessage());
               }

               else if (error.equals("HTTP 422 Unprocessable Entity")){
                   String message = "Client Subscription In-active. Please Terminate Call.";
                   Log.i("TAG",message);
                   String diffrential = "2";
                   Bundle bundle = new Bundle();
                   bundle.putString("messageError",message);
                   bundle.putString("diffrential",diffrential);

                   Intent i = new Intent(context, CallerIdentified.class).putExtras(bundle);
                   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(i);
                   stopSelf();

                   Log.i("TAG", e.getMessage());
               }

               else {
                   Log.i("TAG", e.getMessage());
               }


//                    stopSelf();
//                    Log.i("TAG", "it has stopped itself");
//                }else if (e.equals("HTTP 422 Unprocessable Entity")){
//                    Log.i("TAG", e.getMessage());
//                }
            }
        };
    }

}
