package com.ibm.wam.call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.ibm.wam.model.beans.BeanUsuarioBPLogado;
import com.ibm.wam.model.security.BluePage;
import com.ibm.wam.model.util.WebException;

public class BlueRequester {

	public static void main(String[] args) throws WebException, IOException, JSONException {
		// TODO Auto-generated method stub
		if (args.length == 1) {
			List<String> user = new ArrayList<String>();
			BluePage bp = new BluePage();
			BeanUsuarioBPLogado loggedIn = bp.doAuthenticationViaW3(args[0]);

			user.add(bp.getImageOfPersonsByInternet(loggedIn.getEmail(), 100));
			user.add(loggedIn.getFullName());
			user.add(loggedIn.getEmail());
			user.add(loggedIn.getSerialNumber());
			if (loggedIn.isManager()) {
				user.add("Gerente");
			} else {
				user.add(loggedIn.getManagerEmail());
			}

			for (String u : user) {
				System.out.println(u + System.lineSeparator());
			}
		}else {
			System.err.println("Args Issues");
		}
		System.exit(0);
	}

}
