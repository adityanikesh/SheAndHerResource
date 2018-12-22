package com.cvs.avocado.utils;

public class OrchestratorMessageUtil {

	public static String getAppRegMessage(int appRegCode) {
		String appRegMessage = null;
		switch(appRegCode) {
		case 1:
			appRegMessage = OrchestratorConstants.APP_REG_APP_NOT_FOUND_MESSAGE;
			break;
		case 2:
			appRegMessage = OrchestratorConstants.APP_REG_MD5_MALWARE_MATCH_MESSAGE;
			break;
		case 3:
			appRegMessage = OrchestratorConstants.APP_REG_SHA2_MALWARE_MATCH_MESSAGE;
			break;
		case 4:
			appRegMessage = OrchestratorConstants.APP_REG_APP_ALREADY_REGISTERED_MESSAGE;
			break;
		case 5:
			appRegMessage = OrchestratorConstants.APP_REG_DUPLICATE_ALIAS_NAME_MESSAGE;
			break;
		case 6:
			appRegMessage = OrchestratorConstants.APP_REG_CUSTOMER_NOT_FOUND_MESSAGE;
			break;
		case 7:
			appRegMessage = OrchestratorConstants.APP_REG_DEPARTMENT_NOT_FOUND_MESSAGE;
			break;
		case 8:
			appRegMessage = OrchestratorConstants.APP_REG_SUCCESS_REGISTRATION_MESSAGE;
			break;
		}
		return appRegMessage;
	}
	

	public static String getSLAMessage(int slaResult) {
		String slaMessage = null;
		switch(slaResult) {
		case 1:
			slaMessage = OrchestratorConstants.SLA_VALID_PROTECTED_MESSAGE;
			break;
		case 2:
			slaMessage = OrchestratorConstants.SLA_VALID_NOT_PROTECTED_MESSAGE;
			break;
		case 3:
			slaMessage = OrchestratorConstants.SLA_MD5_MISMATCH_MESSAGE;
			break;
		case 4:
			slaMessage = OrchestratorConstants.SLA_MD5_MALWARE_MATCH_MESSAGE;
			break;
		case 5:
			slaMessage = OrchestratorConstants.SLA_SHA2_MISMATCH_MESSAGE;
			break;
		case 6:
			slaMessage = OrchestratorConstants.SLA_SHA2_MALWARE_MATCH_MESSAGE;
			break;
		case 7:
			slaMessage = OrchestratorConstants.SLA_NO_RECORD_IN_DB_MESSAGE;
			break;
		case 8:
			slaMessage = OrchestratorConstants.SLA_DISCOVER_MODE_ON_MESSAGE;
			break;
		case 9:
			slaMessage = OrchestratorConstants.SLA_APP_NOT_SYNC_MESSAGE;
			break;
		}
		return slaMessage;
	}
}
