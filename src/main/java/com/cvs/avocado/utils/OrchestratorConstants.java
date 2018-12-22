package com.cvs.avocado.utils;

public class OrchestratorConstants {

	public static final int InetIPV4Address = 4;
	public static final int InetIPV6Address = 6;
	
	public static final int ZMQ_REQ_SEND_GLOBAL_SETTINGS = 1;
	public static final int ZMQ_REQ_FETCH_LICENCE = 2;
	public static final int ZMQ_REQ_DELETE_LICENCE = 3;
	public static final int ZMQ_REQ_FETCH_GLOBAL_SETTINGS = 9;


	public static final int SLA_VALID_PROTECTED_CODE = 1;
	public static final int SLA_VALID_NOT_PROTECTED_CODE = 2;
	public static final int SLA_MD5_MISMATCH_CODE = 3;
	public static final int SLA_MD5_MALWARE_MATCH_CODE = 4;
	public static final int SLA_SHA2_MISMATCH_CODE = 5;
	public static final int SLA_SHA2_MALWARE_MATCH_CODE = 6;
	public static final int SLA_NO_RECORD_IN_DB_CODE = 7;
	public static final int SLA_DISCOVER_MODE_ON_CODE = 8;
	public static final int SLA_APP_NOT_SYNC_CODE = 9;
	
	public static final int APP_REG_APP_NOT_FOUND_CODE = 1;
	public static final int APP_REG_MD5_MALWARE_MATCH_CODE = 2;
	public static final int APP_REG_SHA2_MALWARE_MATCH_CODE = 3;
	public static final int APP_REG_APP_ALREADY_REGISTERED_CODE = 4;
	public static final int APP_REG_DUPLICATE_ALIAS_NAME_CODE = 5;
	public static final int APP_REG_CUSTOMER_NOT_FOUND_CODE = 6;
	public static final int APP_REG_DEPARTMENT_NOT_FOUND_CODE = 7;
	public static final int APP_REG_SUCCESS_REGISTRATION_CODE = 8;
	
	public static final String APP_REG_APP_NOT_FOUND_MESSAGE = "Application record not found.";
	public static final String APP_REG_MD5_MALWARE_MATCH_MESSAGE = "Application MD5 matched with malware.";
	public static final String APP_REG_SHA2_MALWARE_MATCH_MESSAGE = "Application SHA2 matched with malware.";
	public static final String APP_REG_APP_ALREADY_REGISTERED_MESSAGE = "Application having same signature is already registered.";
	public static final String APP_REG_DUPLICATE_ALIAS_NAME_MESSAGE = "Duplicate alias name.";
	public static final String APP_REG_CUSTOMER_NOT_FOUND_MESSAGE = "Customer record not found.";
	public static final String APP_REG_DEPARTMENT_NOT_FOUND_MESSAGE = "Department record not found.";
	public static final String APP_REG_SUCCESS_REGISTRATION_MESSAGE = "Application is registered successfully.";
	
	
	public static final String SLA_VALID_PROTECTED_MESSAGE = "Application is registered and protected.";
	public static final String SLA_VALID_NOT_PROTECTED_MESSAGE = "Application is registerd but bypassed.";
	public static final String SLA_MD5_MISMATCH_MESSAGE = "Application MD5 mismatch.";
	public static final String SLA_MD5_MALWARE_MATCH_MESSAGE = "Application MD5 matched with malware.";
	public static final String SLA_SHA2_MISMATCH_MESSAGE = "Application SHA2 mismatch.";
	public static final String SLA_SHA2_MALWARE_MATCH_MESSAGE = "Application SHA2 matched with malware.";
	public static final String SLA_NO_RECORD_IN_DB_MESSAGE = "No record in database for this application.";
	public static final String SLA_DISCOVER_MODE_ON_MESSAGE = "Application is allowed since discover mode is ON.";
	public static final String SLA_APP_NOT_SYNC_MESSAGE = "Application is not synced.";
	
	
	public static final String JEROMQ_CONNECT_URL = "tcp://management_ip_to_connect:8900";
	public static final String JEROMQ_CONNECT_URL_REPLACE_IP = "management_ip_to_connect";
	
}
