package edu.rutgers.cs.rahul.helloworld;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Careena on 11/28/15.
 */
public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://10.0.2.2:8888/addEmp.php";
    public static final String URL_ADD_STATUS="http://10.0.2.2:8888/addStatus.php";
    //public static final String URL_ADD="http://10.0.2.2:8888/insertID.php";
    //public static final String URL_GET_ALL = "http://10.0.2.2:8888/getAllEmp.php";
   // public static final String URL_GET_EMP = "http://10.0.2.2:8888/getEmp.php?sender_id=%27";
    //public static final String URL_GET_EMP = "http://10.0.2.2:8888/getEmpTwoParams.php?sender_id=%27";
    public static final String URL_GET_EMP = "http://10.0.2.2:8888/getEmpThreeParams.php?sender_id=%27";

    //get received challenge based on datetime and receiver_id
    public static final String URL_GET_CHALLENGE_RECEIVER = "http://10.0.2.2:8888/getEmpDatetimeTwoParams.php?receiver_id=%27";

    public static final String URL_UPDATE_EMP = "http://10.0.2.2:8888/updateEmp.php";
    public static final String URL_DELETE_EMP = "http://10.0.2.2:8888/deleteEmp.php?sender_id=%27";
   // public static final String URL_GET_RUN_INFO = "http://10.0.2.2:8888/getRunInfo.php?user_id=%27";
    //public static final String URL_GET_RUN_INFO = "http://10.0.2.2:8888/getEmpDatetime.php?datetime=%27";
    public static final String URL_GET_RUN_INFO = "http://10.0.2.2:8888/getRunInfoTwoParams.php?sender_id=%27";
    //added
    public static final String URL_GET_ALL = "http://10.0.2.2:8888/getCurrentUserAll.php?sender_id=%27";

    public static final String URL_GET_ALL_SENT = "http://10.0.2.2:8888/getAllSentChallenges.php?sender_id=%27";
    //checkuser
    public static final String URL_GET_USER = "http://10.0.2.2:8888/selectAll.php?sender_id=%27";

    public static final String URL_GET_USER_NAME = "http://10.0.2.2:8888/getNameFromId.php?user_id=%27";


    public static final String URL_GET_TOTAL_CHALLENGES = "http://10.0.2.2:8888/countChallenge.php?sender_id=%27";




    //Keys that will be used to send the request to php scripts
    public static final String KEY_EMP_ID = "sender_id";
    public static final String KEY_EMP_NAME = "receiver_id";
    public static final String KEY_EMP_DESG = "datetime";
    public static final String KEY_EMP_SAL = "sender_id";

    //status key
    public static final String KEY_EMP_STATUS = "status";
    public static final String KEY_EMP_DATETIME = "datetime";

  //  String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "sender_id";
    public static final String TAG_NAME = "receiver_id";
    public static final String TAG_DESG = "datetime";

    public static final String TAG_SAL = "sender_id";

    public static final String TAG_USER_ID = "user_id";
    public static final String TAG_USER_NAME = "name";

    //Keys that will be used to send the request to php scripts for run
    public static final String KEY_USER_ID = "user_id";



    //JSON Tags for User Table
    public static final String TAG_JSON_ARRAY3="result";
    //    public static final String TAG_ID = "sender_id";
    public static final String TAG_NAME3 = "id";
//    public static final String TAG_DESG = "datetime";
//    public static final String TAG_SAL = "sender_id";



    //JSON Tags for RUN
    public static final String TAG_JSON_ARRAY_RUN="result";
    public static final String TAG_DISTANCE = "distance";
    public static final String TAG_DURATION = "duration";

    //JSON Tags for sender RUN
    public static final String TAG_JSON_ARRAY_RUN2="result";
    public static final String TAG_DISTANCE2 = "distance";
    public static final String TAG_DURATION2 = "duration";

    // id to pass with intent
    public static final String EMP_ID = "emp_id";
    public static final String REC_ID = "rec_id";
    public static final String SENDER_ID = "send_id";
    public static final String DATETIME_ID = "datetime";
    public static final String RECEIVER_NAME = "receiver_name";



    //dist and dur to pass with intent
//    public static final int distanceSender = 0;
//    public static final int durationSender = 0;
//    public static final int distanceReceiver = 0;
//    public static final int durationReceiver = 0;
}
