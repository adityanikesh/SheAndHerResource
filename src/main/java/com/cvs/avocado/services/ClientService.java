package com.cvs.avocado.services;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import com.cvs.avocado.models.Client;
import com.cvs.avocado.repositories.ClientRepository;
import com.cvs.avocado.security.HttpServletFacade;

@Service
public class ClientService extends JdbcClientDetailsService {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	ManagementPoolService managementPoolService;

	@Autowired
	HttpServletFacade httpServlet;

	public ClientService(DataSource dataSource) {
		super(dataSource);
	}
	
	public List<Client> getAllClients(){
		return this.clientRepository.getAllClients();
	}

	@Override
	@Cacheable(value="clients", key="#clientId")
	public Client loadClientByClientId(String clientId) throws InvalidClientException {
		Client client = this.clientRepository.findClientByClientId(clientId);
		if(client == null) {
			clientId = httpServlet.getRemoteAddress();
			int managementIPPresence = this.managementPoolService.findManagementIP(clientId);
			if(managementIPPresence > 0) {
				int insertNewClient = this.clientRepository.insertClient(new Client(clientId, "appmanager", null, "client_credentials", null, 3, true, 0, 0));
				if(insertNewClient > 0){
					client = this.clientRepository.findClientByClientId(clientId);
				}
			} else {
				this.clientRepository.insertClient(new Client(clientId, "appmanager", null, "client_credentials", null, 3, false, 0, 0));
			}
		} else if(!client.isEnabled()) {
			client = null;
		}
		return client;
	}

}