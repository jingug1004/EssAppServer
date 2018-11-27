package com.noblapp.support;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractTestCase {

//	private static final String DEV_URL = "http://172.30.1.15:8080/noblappServer";
	private static final String DEV_URL = "http://localhost:8080/noblapp";
	
	protected boolean jsonData(String context, Map<String, Object> cmd){
		HttpUtil httpUtil = new HttpUtil();
//		Aes128Crypt crypt = new Aes128Crypt();		
		
		boolean isResult = false;
		
		Map<String, String> parameters = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		if(cmd == null) cmd = new HashMap<String, Object>();
		try{
			parameters.put("module", context);
			parameters.put("parameter", mapper.writeValueAsString(cmd));
//			parameters.put("cmd", crypt.encrypt(mapper.writeValueAsString(cmd), CMD_KEY));
//			if(cheader != null) parameters.put("cheader", cheader);

			String result = httpUtil.postJson(DEV_URL + context, mapper.writeValueAsString(parameters));

//			String result = httpUtil.postData(DEV_URL + context, parameters);
//			System.out.println("result >>>> " + result);
			JsonNode rootNode = mapper.readTree(result);
//			this.cheader = rootNode.get("header").asText();
//			System.out.println(this.cheader);
			
			String decryptResponse = null;
//			if(rootNode.get("isResEncrypted").asBoolean()){
//				decryptResponse = crypt.decrypt(rootNode.get("response").asText(), RESPONSE_KEY);
//				System.out.println(decryptResponse);
//				isResult = true;
//			}else {
				decryptResponse = rootNode.get("response").toString();
				System.out.println(decryptResponse);
				isResult = rootNode.get("success").asBoolean();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return isResult;
	}
	
	protected String getJsonData(String context, Map<String, Object> cmd){
		HttpUtil httpUtil = new HttpUtil();
//		Aes128Crypt crypt = new Aes128Crypt();		
		
		Map<String, String> parameters = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		if(cmd == null) cmd = new HashMap<String, Object>();
		try{
			parameters.put("module", context);
			parameters.put("parameter", mapper.writeValueAsString(cmd));

			String result = httpUtil.postJson(DEV_URL + context, mapper.writeValueAsString(parameters));
			System.out.println(result);
			return result;

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
