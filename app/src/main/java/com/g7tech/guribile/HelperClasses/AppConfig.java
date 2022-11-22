package com.g7tech.guribile.HelperClasses;

public class AppConfig {

    public static String BASE_URL = "http://guribile.cert-pro.net/api/";
    public static String URL_WORKER_LIST = BASE_URL+"worker_list.php?id=";
    public static String URL_FIX_APPOINTMENT = BASE_URL+"fixAppointment.php";
    public static String URL_LOGIN = BASE_URL+"login.php";
    public static String URL_ACCEPTED_WORK_LIST = BASE_URL+"client_accepted_work.php?id=";
    public static String URL_COMPLETED_WORK_LIST = BASE_URL+"client_completed_work.php?id=";
    public static String URL_PENDING_WORK_LIST = BASE_URL+"client_pending_work.php?id=";
    public static String URL_DECLINED_WORK_LIST = BASE_URL+"client_declined_work.php?id=";
    public static String URL_REGISTER = BASE_URL+"register_client.php";
    public static String URL_GET_RATING_REQUESTS = BASE_URL+"rate_work_list.php?id=";
    public static String URL_SUBMIT_RATING = BASE_URL+"client_review.php?id=";

}
