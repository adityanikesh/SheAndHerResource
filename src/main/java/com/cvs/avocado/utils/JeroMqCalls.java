package com.cvs.avocado.utils;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class JeroMqCalls {

	private ZContext zContext;
	private ZMQ.Socket socket;
	
	private static final Logger log = LogManager.getLogger();
	
	public JeroMqCalls() {
		zContext = new ZContext();
		socket = zContext.createSocket(ZMQ.REQ);
	}
	
	public void sendMessage(String managementIP, Map<String, Object> request) {
		String jeroMqResponse = null;
		try {
			String url = OrchestratorConstants.JEROMQ_CONNECT_URL.replace(
					OrchestratorConstants.JEROMQ_CONNECT_URL_REPLACE_IP, managementIP);

			socket.setIPv6(true);
			socket.connect(url);
			boolean result = socket.send(request.toString());
			if(result) {
			log.info("Message sent to appmanger running on: " + url + " .Message: " + request.toString());

			socket.setReceiveTimeOut(100);
			jeroMqResponse = socket.recvStr();
			log.info("Response from " + url + ":" + jeroMqResponse);
			} else {
				log.error("Failed to send message to appmanager running on: " + managementIP);
			}

		} catch (Exception e) {
			log.error("Exception occured while sending message to appmanager running on: " + managementIP + ". Reason: "+ e.getMessage());

		} finally {
			if (socket != null && zContext != null) {
				zContext.destroySocket(socket);
				zContext.destroy();
			}
		}
	}
}
