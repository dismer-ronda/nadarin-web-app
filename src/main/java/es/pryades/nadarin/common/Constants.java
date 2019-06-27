package es.pryades.nadarin.common;

public class Constants 
{
	public static final String CHART_FONT_SIZE 					= "14px";

	public static final int ONE_YEAR 								= 365 * 24 * 60 * 60;
	
	public static final long ID_PROFILE_DEVELOPER					= 0;
	public static final long ID_PROFILE_ADMIN 						= 1;
	public static final long ID_PROFILE_USER						= 2;

	public static final int AUDIT_TYPES								= 27;
	
	public static final int AUDIT_TYPE_UNDEFINED					= -1;
	
	public static final int AUDIT_TYPE_LOGIN						= 0;
	public static final int AUDIT_TYPE_LOGOUT						= 1;
	public static final int AUDIT_TYPE_UPDATE_PEGASUS				= 2;
	public static final int AUDIT_TYPE_CANCEL_PEGASUS				= 3;
	public static final int AUDIT_TYPE_UPDATE_LIVIQ					= 4;
	public static final int AUDIT_TYPE_CANCEL_LIVIQ					= 5;
	
	public static final int AUDIT_TYPE_NEW_SMART_STATION			= 6;
	public static final int AUDIT_TYPE_MODIFY_SMART_STATION			= 7;
	public static final int AUDIT_TYPE_DELETE_SMART_STATION			= 8;
	
	public static final int AUDIT_TYPE_NEW_MODEM					= 9;
	public static final int AUDIT_TYPE_MODIFY_MODEM					= 10;
	public static final int AUDIT_TYPE_DELETE_MODEM					= 11;

	public static final int AUDIT_TYPE_NEW_FIRMWARE					= 12;
	public static final int AUDIT_TYPE_MODIFY_FIRMWARE				= 13;
	public static final int AUDIT_TYPE_DELETE_FIRMWARE				= 14;

	public static final int AUDIT_TYPE_NEW_PLANT					= 15;
	public static final int AUDIT_TYPE_MODIFY_PLANT					= 16;
	public static final int AUDIT_TYPE_DELETE_PLANT					= 17;

	public static final int AUDIT_TYPE_NEW_PLATFORM					= 18;
	public static final int AUDIT_TYPE_MODIFY_PLATFORM				= 19;
	public static final int AUDIT_TYPE_DELETE_PLATFORM				= 20;
	
	public static final int AUDIT_TYPE_NEW_REPORT					= 21;
	public static final int AUDIT_TYPE_MODIFY_REPORT				= 22;
	public static final int AUDIT_TYPE_DELETE_REPORT				= 23;

	public static final int AUDIT_TYPE_NEW_TASK						= 24;
	public static final int AUDIT_TYPE_MODIFY_TASK					= 25;
	public static final int AUDIT_TYPE_DELETE_TASK					= 26;

	public static final int AUDIT_TYPE_NEW_HOSPITAL					= 27;
	public static final int AUDIT_TYPE_MODIFY_HOSPITAL				= 28;
	public static final int AUDIT_TYPE_DELETE_HOSPITAL				= 29;

	public static final int REPORT_CLAZZES							= 3;

	public static final int REPORT_CLAZZ_ST_LIST					= 0;
	public static final int REPORT_CLAZZ_OPERATIONS					= 1;
	public static final int REPORT_CLAZZ_FLEET						= 2;

	public static final int DATE_TODAY								= 1;
	public static final int DATE_YESTERDAY							= 2;
	public static final int DATE_LAST_WEEK							= 3;
	public static final int DATE_LAST_MONTH							= 4;
	public static final int DATE_LAST_THREMESTER					= 5;
	public static final int DATE_LAST_SEMESTER						= 6;
	public static final int DATE_LAST_YEAR							= 7;

	public static final int AUDIT_DURATION_DEFAULT					= 30;
	
	public static final int TASK_CLAZZES							= 5;
	public static final int TASK_FIRST								= 100;

	public static final int TASK_CLAZZ_DATABASE_UPDATE				= 100;
	public static final int TASK_CLAZZ_DATABASE_QUERY				= 101;
	public static final int TASK_CLAZZ_IMPORT_SYMBOLS				= 102;
	public static final int TASK_CLAZZ_IMPORT_TECHNICALS			= 103;
	public static final int TASK_CLAZZ_IMPORT_END_OF_DAYS			= 104;

	public static final String CONTEXT = "easy.stoks.context";
    public static final String USER_LOGGED_IN = "user.logged.in";
    public static final String LOGIN_ERROR_PAGE = "login/error";

    //Duración de la notificación en milisegundos
	public static final int NOTIFICATION_DURATION = 3000;

}
