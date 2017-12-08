/**
 * 
 */
package com.ibm.wam.model.security;


import java.io.IOException;


import com.ibm.bpm.bluepages.BPUser;
import com.ibm.wam.model.beans.BeanUsuarioBPLogado;
import com.ibm.wam.model.util.WebException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BluePage {

	public BluePage() {
		super();
	}

/*	
   public static void main(String[] args) throws WebinException, IOException, JSONException {
	   BluePage bp = new BluePage();
    	bp.doAuthenticationViaW3("paulogjr@br.ibm.com");
	}
*/
	public BeanUsuarioBPLogado doAuthenticationViaW3(String userMail)
		throws WebException, IOException, JSONException {

	    BeanUsuarioBPLogado beanUsuarioBPLogado = new BeanUsuarioBPLogado();
		String ManagerSerialNumber = "";
		String ManagerCountry      = "";
		String IdGerente           = "";
		String urlEmail = "https://w3.api.ibm.com/common/run/bluepages/email/"+userMail+"/";
		//String urlEmail = "https://eapim.w3ibm.mybluemix.net/common/run/bluepages/email/"+userMail+"/";
		
		
		// OkHttpClient from http://square.github.io/okhttp/
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url(urlEmail)
		  .get()
		  .addHeader("x-ibm-client-id", "9f8426a8-8a4f-47c6-90e2-3245b00ec263")
		  .addHeader("accept", "application/json")
		  .build();
	
		
			try (Response response = client.newCall(request).execute()) {
			    String jsonData = response.body().string();
			    JSONObject jsonObj = new JSONObject(jsonData);
			    //System.out.println("Resultado:" + jsonObj);
			    String retCode = jsonObj.getJSONObject("search").getJSONObject("return").getString("code");
				beanUsuarioBPLogado.setEmail(userMail);

			    if (retCode.equals("0")) {
			    	JSONArray jsonEntry = jsonObj.getJSONObject("search").getJSONArray("entry");
			    	int lengthPri = jsonEntry.length();
			    	for(int i=0; i<lengthPri; i++) {
			    		JSONObject jsonSearch = jsonEntry.getJSONObject(i);
			    		JSONArray jsonAtt = jsonSearch.getJSONArray("attribute");
			    		int lengthAtt = jsonAtt.length();
			    		for(int j=0; j<lengthAtt; j++) {
			    			JSONObject jsonNames = jsonAtt.getJSONObject(j);
			    			String nameAtt = jsonAtt.getJSONObject(j).getString("name");
			    			JSONArray jsonValue = jsonNames.getJSONArray("value");
			    			String jsonValor = jsonValue.getString(0);
			    			switch (nameAtt) {
			    			case "givenname":
								beanUsuarioBPLogado.setFirstName(jsonValor);
			    				break;
			    			case "sn":
								beanUsuarioBPLogado.setLastName(jsonValor);
			    				break;
			    			case "notesemail":
								beanUsuarioBPLogado.setNotesMail(jsonValor);
								beanUsuarioBPLogado.setFullName(formataNome(jsonValor));
			    				break;
			    			case "div":
								beanUsuarioBPLogado.setDivision(jsonValor);
			    				break;
			    			case "dept":
								beanUsuarioBPLogado.setDepartment(jsonValor);
			    				break;
			    			case "ibmserialnumber":
								beanUsuarioBPLogado.setSerialNumber(jsonValor);
			    				break;
			    			case "managerserialnumber":
			    				ManagerSerialNumber = jsonValor;
			    			case "managercountrycode":	
			    				ManagerCountry      = jsonValor;
								break;
			    			case "hrorganizationcode":
								beanUsuarioBPLogado.setHrOrganizationCode(jsonValor);
			    				break;
			    			case "employeetype":
			    				if (jsonValor.equals("P")) {
									beanUsuarioBPLogado.setVendor(false);
			    				} else {
									beanUsuarioBPLogado.setVendor(true);
			    				}
			    				break;
			    			case "primaryuserid":
								beanUsuarioBPLogado.setIntranetID(jsonValor);
			    				break;
			    			case "ismanager":
			    				if (jsonValor.equals("N")) {
			    					beanUsuarioBPLogado.setManager(false);
			    				} else {
				    				beanUsuarioBPLogado.setManager(true);
			    				}
			    				break;
			    			}
			    		}
			    	}
			    }
			    else {
			    	System.out.println("Erro de Acesso aos dados do usuário " + userMail + "RETCODE = " + retCode );
			    }
			    IdGerente = ManagerSerialNumber + ManagerCountry;
				beanUsuarioBPLogado.setManagerSerialNumber(IdGerente);
				BeanUsuarioBPLogado gerenteBp;
				gerenteBp = buscarGerente(IdGerente);
				beanUsuarioBPLogado.setManagerNotesMail(gerenteBp.getManagerNotesMail());
				beanUsuarioBPLogado.setManagerEmail(gerenteBp.getManagerEmail());
			}
			catch (IOException e) {
				beanUsuarioBPLogado.setFirstName("RedeFora");
		    	System.out.println("Conexão Fora da Rede IBM - W3ID Funcionário");
			}
			//System.out.println(beanUsuarioBPLogado.getFirstName());
			//System.out.println(beanUsuarioBPLogado.getManagerEmail());
			return beanUsuarioBPLogado;
	}
	
	// Buscar Dados referente ao gerente do Funcionario
	public BeanUsuarioBPLogado buscarGerente(String IdGerente) throws WebException, IOException, JSONException {

		String urlID = "https://w3.api.ibm.com/common/run/bluepages/cnum/"+IdGerente+"/";
		BeanUsuarioBPLogado beanGerenteBP = new BeanUsuarioBPLogado();
		
		// OkHttpClient from http://square.github.io/okhttp/
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url(urlID)
		  .get()
		  .addHeader("x-ibm-client-id", "9f8426a8-8a4f-47c6-90e2-3245b00ec263")
		  .addHeader("accept", "application/json")
		  .build();

		try (Response response = client.newCall(request).execute()) {
		    String jsonData = response.body().string();
		    JSONObject jsonObj = new JSONObject(jsonData);
		    String retCode = jsonObj.getJSONObject("search").getJSONObject("return").getString("code");

		    if (retCode.equals("0")) {
		    	JSONArray jsonEntry = jsonObj.getJSONObject("search").getJSONArray("entry");
		    	int lengthPri = jsonEntry.length();
		    	for(int i=0; i<lengthPri; i++) {
		    		JSONObject jsonSearch = jsonEntry.getJSONObject(i);
		    		JSONArray jsonAtt = jsonSearch.getJSONArray("attribute");
		    		int lengthAtt = jsonAtt.length();
		    		for(int j=0; j<lengthAtt; j++) {
		    			JSONObject jsonNames = jsonAtt.getJSONObject(j);
		    			String nameAtt = jsonAtt.getJSONObject(j).getString("name");
		    			JSONArray jsonValue = jsonNames.getJSONArray("value");
		    			String jsonValor = jsonValue.getString(0);
		    			switch (nameAtt) {
		    			case "notesemail":	
		    				beanGerenteBP.setManagerNotesMail(jsonValor);
							break;
		    			case "mail":
		    				beanGerenteBP.setManagerEmail(jsonValor);
							break;
		    			}
		    		}
		    	}
		    }
		    else {
		    	System.out.println("Erro de Acesso aos dados de Gerente " + IdGerente + "RETCODE = " + retCode );
		    }
		}		
		catch (IOException e) {
	    	System.out.println("Conexão Fora da Rede IBM - W3ID Dados Gerente");
		}
		return (beanGerenteBP);
	}

	
	
	// formata o nome do usuario logado
		public String formataNome(String valor) {
			String tmp;
			String nomeCompleto = "";
			for (int i = 3; i < valor.length(); i++) {
				tmp = valor.substring(i, i + 1);
				if (!"/".equals(tmp)) {
					nomeCompleto = nomeCompleto + tmp;
				} else {
					break;
				}
			}
			return nomeCompleto;
		}
	
	// get image by internet Id
	public String getImageOfPersonsByInternet(String intranetId, int size) {
		 String urlImage = "";
		 urlImage = "http://images.tap.ibm.com:10002/image/" + intranetId + "?s=" + size;
		 return urlImage;
	}
	
	public BPUser isGerente(String userMail) throws WebException, IOException, JSONException {
		    BPUser bpUser = new BPUser();
			String urlEmail = "https://w3.api.ibm.com/common/run/bluepages/email/"+userMail+"/";
			
			// OkHttpClient from http://square.github.io/okhttp/
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
			  .url(urlEmail)
			  .get()
			  .addHeader("x-ibm-client-id", "9f8426a8-8a4f-47c6-90e2-3245b00ec263")
			  .addHeader("accept", "application/json")
			  .build();
			
				try (Response response = client.newCall(request).execute()) {
				    String jsonData = response.body().string();
				    JSONObject jsonObj = new JSONObject(jsonData);
				    String retCode = jsonObj.getJSONObject("search").getJSONObject("return").getString("code");

				    if (retCode.equals("0")) {
				    	JSONArray jsonEntry = jsonObj.getJSONObject("search").getJSONArray("entry");
				    	int lengthPri = jsonEntry.length();
				    	for(int i=0; i<lengthPri; i++) {
				    		JSONObject jsonSearch = jsonEntry.getJSONObject(i);
				    		JSONArray jsonAtt = jsonSearch.getJSONArray("attribute");
				    		int lengthAtt = jsonAtt.length();
				    		for(int j=0; j<lengthAtt; j++) {
				    			JSONObject jsonNames = jsonAtt.getJSONObject(j);
				    			String nameAtt = jsonAtt.getJSONObject(j).getString("name");
				    			JSONArray jsonValue = jsonNames.getJSONArray("value");
				    			String jsonValor = jsonValue.getString(0);
				    			switch (nameAtt) {
				    			case "notesemail":
				    				bpUser.setNotesMail(jsonValor);
				    				bpUser.setFirstName(formataNome(jsonValor));
				    				break;
				    			case "div":
				    				bpUser.setDivision(jsonValor);
				    				break;
				    			case "dept":
				    				bpUser.setDepartment(jsonValor);
				    				break;
				    			case "ibmserialnumber":
				    				bpUser.setSerialNumber(jsonValor);
				    				break;
				    			case "hrorganizationcode":
				    				bpUser.setHrOrganizationCode(jsonValor);
				    				break;
				    			case "ismanager":
				    				if (jsonValor.equals("N")) {
				    					bpUser.setManager(false);
				    				} else {
				    					bpUser.setManager(true);
				    				}
				    				break;
				    			}
				    		}
				    	}
				    }
				    else {
				    	System.out.println("Erro de Acesso aos dados do Gerente " + userMail + "RETCODE = " + retCode );
				    }
				}
				catch (IOException e) {
			    	System.out.println("Conexão Fora da Rede IBM - W3ID Validar Gerente");
				}
			return bpUser;
	} 
} 
