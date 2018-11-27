package com.noblapp.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * @author jhkim0801
 *
 */
@Component
public class HttpUtil {
	
	public String postData(String uri, HttpEntity entity){
		String result = null;
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try{
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setConfig(requestConfig);
			
			if(entity != null) httpPost.setEntity(entity);
			

			ResponseHandler<String> responseHandler = new ResponseHandler<String>(){
				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
				}
			};
			
			result = httpClient.execute(httpPost, responseHandler);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {httpClient.close();} catch (IOException e) {e.printStackTrace();}
		}		
		
		return result;
			
	}
	
	public String postData(String uri, Map<String, String> parameters){
		String result = null;
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try{
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setConfig(requestConfig);
			
			if(parameters != null){
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				Iterator<String> keyIterator = parameters.keySet().iterator();
				while(keyIterator.hasNext()){
					String key = keyIterator.next();
					postParameters.add(new BasicNameValuePair(key, StringUtils.defaultString(parameters.get(key))));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(postParameters, Consts.UTF_8));
			}
			
			ResponseHandler<String> responseHandler = new ResponseHandler<String>(){
				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
				}
			};
			
			result = httpClient.execute(httpPost, responseHandler);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {httpClient.close();} catch (IOException e) {e.printStackTrace();}
		}		
		
		return result;
	}
	
	/**
	 * 파일 업로드
	 * @param uri
	 * @param parameters
	 * @param file
	 * @param fileName
	 * @return
	 */
	public String postData(String uri, Map<String, String> parameters, File file, String fileName){
		String result = null;
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try{
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setConfig(requestConfig);
			
			MultipartEntityBuilder meb = MultipartEntityBuilder.create();
			
			if(parameters != null){
				Iterator<String> keyIterator = parameters.keySet().iterator();
				while(keyIterator.hasNext()){
					String key = keyIterator.next();
					meb.addPart(key, new StringBody(StringUtils.defaultString(parameters.get(key)), ContentType.TEXT_PLAIN));
				}						
			}

			if(file != null && fileName != null){
				FileBody fileBody = new FileBody(file);
				meb.addPart(fileName, fileBody);
				
				httpPost.setEntity(meb.build());		
			}
			
			ResponseHandler<String> responseHandler = new ResponseHandler<String>(){
				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
				}
			};
			
			result = httpClient.execute(httpPost, responseHandler);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {httpClient.close();} catch (IOException e) {e.printStackTrace();}
		}
		return result;
	}
	
	public String getData(String uri){
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try{
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setConfig(requestConfig);
			
			ResponseHandler<String> responseHandler = new ResponseHandler<String>(){
				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
				}
			};
			
			result = httpClient.execute(httpGet, responseHandler);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {httpClient.close();} catch (IOException e) {e.printStackTrace();}
		}
		return result;
	}
	
	public String postJson(String uri, String pJson){
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		try{
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
			System.out.println("uri? " + uri);
			System.out.println("pJson? " + pJson);
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setConfig(requestConfig);
			
			StringEntity entity = new StringEntity(pJson, Consts.UTF_8);
//			entity.setContentType("application/json-rpc");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			
			ResponseHandler<String> responseHandler = new ResponseHandler<String>(){

				@Override
				public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
				}
				
			};
			result = httpClient.execute(httpPost, responseHandler);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {httpClient.close();} catch (IOException e) {e.printStackTrace();}
		}
		return result;
	}
}

