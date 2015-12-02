package edu.rutgers.cs.rahul.helloworld;

/**
 * Created by Careena on 11/28/15.
 */
public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://10.0.2.2:8888/addEmp.php";
    //public static final String URL_ADD="http://10.0.2.2:8888/insertID.php";
    //public static final String URL_GET_ALL = "http://10.0.2.2:8888/getAllEmp.php";
    public static final String URL_GET_EMP = "http://10.0.2.2:8888/getEmp.php?sender_id=%27";
    public static final String URL_UPDATE_EMP = "http://10.0.2.2:8888/updateEmp.php";
    public static final String URL_DELETE_EMP = "http://10.0.2.2:8888/deleteEmp.php?sender_id=%27";
    public static final String URL_GET_RUN_INFO = "http://10.0.2.2:8888/getRunInfo.php?user_id=%27";
    //added
    public static final String URL_GET_ALL = "http://10.0.2.2:8888/getCurrentUserAll.php?sender_id=%27";
    //checkuser
    public static final String URL_GET_USER = "http://10.0.2.2:8888/selectAll.php?sender_id=%27";



    //Keys that will be used to send the request to php scripts
    public static final String KEY_EMP_ID = "sender_id";
    public static final String KEY_EMP_NAME = "receiver_id";
    public static final String KEY_EMP_DESG = "datetime";
    public static final String KEY_EMP_SAL = "sender_id";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "sender_id";
    public static final String TAG_NAME = "receiver_id";
    public static final String TAG_DESG = "datetime";
    public static final String TAG_SAL = "sender_id";

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

    //dist and dur to pass with intent
    public static final int distanceSender = 0;
    public static final int durationSender = 0;
    public static final int distanceReceiver = 0;
    public static final int durationReceiver = 0;
}
