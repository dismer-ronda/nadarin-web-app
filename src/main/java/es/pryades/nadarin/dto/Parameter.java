package es.pryades.nadarin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
*
* @author dismer.ronda 
* @since 1.0.0.0
*/

@EqualsAndHashCode(callSuper=true)
@Data
public class Parameter extends BaseDto
{
	private static final long serialVersionUID = 1113547474378950069L;
	
	public static final long PAR_RELEASE 					= 1;
	public static final long PAR_TOO_HOT					= 2;
	public static final long PAR_TOO_COLD					= 3;
	public static final long PAR_FILL_PRESSURE				= 4;
	public static final long PAR_SLEEP_CONSUMPTION			= 5;
	public static final long PAR_DELIVER_CONSUMPTION		= 6;
	public static final long PAR_BATTERY_POWER				= 7;
	public static final long PAR_PRE_RELEASE				= 8;
	public static final long PAR_EMPTY_PRESSURE				= 9;
	public static final long PAR_PLATFORM					= 10;

	public static final long PAR_DEFAULT_PAGE_SIZE			= 11;
	public static final long PAR_DIAMOND_SET_DELAY			= 12;
	public static final long PAR_FIRST_USE_DELAY			= 13;

	public static final long PAR_SMART_STATION_MODEL		= 17;
	public static final long PAR_ADVERTISEMENT_INSIGHTS		= 18;
	public static final long PAR_ADVERTISEMENT_HOSPITAL		= 19;
	public static final long PAR_ADVERTISEMENT_WARNING		= 20;
	public static final long PAR_GASTYPE0					= 21;
	public static final long PAR_GASTYPE1					= 22;
	public static final long PAR_GASTYPE2					= 23;
	public static final long PAR_GASTYPE3					= 24;
	public static final long PAR_GASTYPE4					= 25;
	public static final long PAR_THERAPY_MIN_BACK			= 26;
	public static final long PAR_THERAPY_DELTA_PRESSURE		= 27;
	public static final long PAR_COMPENSATED_DELTA_TIME		= 28;
	public static final long PAR_COMPENSATED_DELTA_TEMP		= 29;
	public static final long PAR_THERAPY_VALID_TIME			= 30;
	public static final long PAR_MIN_COMPENSATED_PRESSURE	= 31;
	public static final long PAR_LOGIN_FAILS_NEW_PASS		= 32;
	public static final long PAR_LOGIN_FAILS_BLOCK			= 33;
	public static final long PAR_PASSWORD_MIN_SIZE			= 34;
	public static final long PAR_PASSWORD_VALID_TIME		= 35;
	public static final long PAR_MAIL_HOST_ADDRESS			= 36;
	public static final long PAR_MAIL_SENDER_EMAIL			= 37;
	public static final long PAR_MAIL_SENDER_USER			= 38;
	public static final long PAR_MAIL_SENDER_PASSWORD		= 39;

	public static final long PAR_DURATION_LOGIN_LOGOUT					= 40;
	public static final long PAR_DURATION_PEGASUS_UPDATE				= 41;
	public static final long PAR_DURATION_LIVIQ_UPDATE					= 42;
	
	public static final long PAR_DURATION_SMART_STATION_CREATION 		= 43;
	public static final long PAR_DURATION_SMART_STATION_MODIFICATION 	= 44;
	public static final long PAR_DURATION_SMART_STATION_DELETION 		= 45;

	public static final long PAR_DURATION_PEGASUS_CREATION 				= 46;
	public static final long PAR_DURATION_PEGASUS_MODIFICATION 			= 47;
	public static final long PAR_DURATION_PEGASUS_DELETION 				= 48;

	public static final long PAR_DURATION_FIRMWARE_CREATION 			= 49;
	public static final long PAR_DURATION_FIRMWARE_MODIFICATION 		= 50;
	public static final long PAR_DURATION_FIRMWARE_DELETION 			= 51;

	public static final long PAR_DURATION_PLANT_CREATION 				= 52;
	public static final long PAR_DURATION_PLANT_MODIFICATION 			= 53;
	public static final long PAR_DURATION_PLANT_DELETION 				= 54;

	public static final long PAR_DURATION_PLATFORM_CREATION 			= 55;
	public static final long PAR_DURATION_PLATFORM_MODIFICATION 		= 56;
	public static final long PAR_DURATION_PLATFORM_DELETION 			= 57;

	public static final long PAR_DURATION_REPORT_CREATION 				= 58;
	public static final long PAR_DURATION_REPORT_MODIFICATION 			= 59;
	public static final long PAR_DURATION_REPORT_DELETION 				= 60;

	public static final long PAR_STABLE_PEGASUS_VERSIONS				= 61;

	public static final long PAR_HTTP_PROXY_HOST						= 62;
	public static final long PAR_HTTP_PROXY_PORT						= 63;
	
	public static final long PAR_SOCKS5_PROXY_HOST						= 64;
	public static final long PAR_SOCKS5_PROXY_PORT						= 65;

	public static final long PAR_GLITCH_DELTA_PRESSURE					= 66;

	public static final long PAR_DURATION_HOSPITAL_CREATION 			= 67;
	public static final long PAR_DURATION_HOSPITAL_MODIFICATION 		= 68;
	public static final long PAR_DURATION_HOSPITAL_DELETION 			= 69;

	public static final long PAR_STRENGTH_SIZE 							= 70;
	public static final long PAR_STRENGTH_CAPITAL						= 71;
	public static final long PAR_STRENGTH_DIGIT							= 72;
	public static final long PAR_STRENGTH_SYMBOL						= 73;
	public static final long PAR_STRENGTH_LOGIN							= 74;
	public static final long PAR_STRENGTH_REUSE							= 75;

	public static final long PAR_LOG_DEFAULT							= 76;
	public static final long PAR_LOG_REGIONS							= 77;
	public static final long PAR_LOG_REGIONS_VOLUMES					= 78;
	public static final long PAR_LOG_CYLINDERS							= 79;
	public static final long PAR_LOG_CYLINDERS_DEVICES					= 80;
	public static final long PAR_LOG_CYLINDERS_PLANTS					= 81;
	public static final long PAR_LOG_CYLINDERS_SERIALS					= 82;
	public static final long PAR_LOG_EVENTS								= 83;
	public static final long PAR_LOG_DEVICES							= 84;
	public static final long PAR_LOG_FILES								= 85;
	public static final long PAR_LOG_FILLINGS							= 86;
	public static final long PAR_LOG_FILLINGS_ALARMS					= 87;
	public static final long PAR_LOG_FILLINGS_FLOWS						= 88;
	public static final long PAR_LOG_FILLINGS_STATISTICS				= 89;
	public static final long PAR_LOG_FILLINGS_THERAPIES					= 90;
	public static final long PAR_LOG_FIRMWARES							= 91;
	public static final long PAR_LOG_MODEMS 							= 92;
	public static final long PAR_LOG_MODEMS_ERRORS						= 93;
	public static final long PAR_LOG_PLANTS								= 95;
	public static final long PAR_LOG_PLANTS_VOLUMES						= 96;
	public static final long PAR_LOG_PLATFORMS							= 97;
	public static final long PAR_LOG_PROFILES_RIGHTS					= 98;
	public static final long PAR_LOG_REPORTS							= 99;
	public static final long PAR_LOG_REPORTS_FILES						= 100;
	public static final long PAR_LOG_REPORTS_USERS						= 101;
	public static final long PAR_LOG_SMART_STATONS						= 102;
	public static final long PAR_LOG_SMART_STATIONS_MODEMS				= 103;
	public static final long PAR_LOG_SMART_STATIONS_PLANTS				= 104;
	public static final long PAR_LOG_TASKS								= 105;
	public static final long PAR_LOG_USERS								= 106;
	public static final long PAR_LOG_USERS_DEFAULTS						= 107;
	public static final long PAR_LOG_DEVICES_IMPORT						= 108;
	public static final long PAR_LOG_SMART_STATIONS_MIGRATION			= 109;
	public static final long PAR_LOG_HOSPITAL_PLATFORM_REQUESTS			= 110;
	public static final long PAR_IQ_BATCH_REVISION						= 111;
	public static final long PAR_IQ_BATCH_MODEL							= 112;

	public static final long PAR_CHARTS_PLATFORM						= 113;
	public static final long PAR_CHARTS_WIDTH 							= 114;
	public static final long PAR_CHARTS_HEIGHT							= 115;

	public static final long PAR_MAX_ROWS_EXPORTED						= 116;

	public static final long PAR_THERAPY_MAX_BACK						= 117;
	public static final long PAR_THERAPY_JT_COOLING						= 118;
	public static final long PAR_THERAPY_MIN_PRESSURE					= 119;

	public static final long PAR_THERAPY_DELTA_TEMPERATURE				= 120;
	public static final long PAR_THERAPY_TEMPERATURE_FACTOR				= 121;
	public static final long PAR_THERAPY_RAISE_ACCEPTED					= 122;
	public static final long PAR_THERAPY_RAISE_DURATION					= 123;

	public static final long PAR_MIRROR_PLATFORM						= 124;
	
	public static final long PAR_MAIL_HOST_PORT							= 125;
	public static final long PAR_MAIL_AUTH								= 126;

	public static final long PAR_SERVICES_URL							= 127;

	private String description;
	private String value;
	private Integer display_order;
}
