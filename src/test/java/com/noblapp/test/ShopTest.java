package com.noblapp.test;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noblapp.support.AbstractTestCase;

public class ShopTest extends AbstractTestCase {

	// status = 1이면 승인처리, 2이면 거절처리(유저가...)
	public void usePointTest(int status) {
		// TODO 포인트를 사용해서 구매하는 테스트 케이스.
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;

		// 사용 요청하기..
		Map<String, Object> cmd = new HashMap<String, Object>();
		cmd.put("uid", 74);
		cmd.put("sid", 1234);
		cmd.put("point", 3000);
		cmd.put("issued_time", System.currentTimeMillis());
		String result = super.getJsonData("/shop/spend/req", cmd);
		
		Assert.notNull(result, "/shop/spend/req response is empty");

		try {
			rootNode = mapper.readTree(result);
			Assert.isTrue(rootNode.get("success").asBoolean(), "result is not true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.isTrue(false, "result is wrong");
		}

		// 상점 주인이 포인트 사용 요청 리스트를 가져오기.
		cmd.clear();
//		cmd.put("uid", 74);		// 이거는 사용 요청을 한 유저의 uid
		cmd.put("uid", 75);		// 이거는 상점 주인의 uid
		cmd.put("sid", 1234);
		result = super.getJsonData("/shop/spend/req/list", cmd);

		Assert.notNull(result, "/shop/spend/req/list response is empty");
		
		int us_id = -1;
		int uid = -1;
		
		try {
			rootNode = mapper.readTree(result);
			Assert.isTrue(rootNode.get("success").asBoolean(), "result is not true");
			
			String responseStr = rootNode.get("response").asText();
			System.out.println("responseStr : " + responseStr);
			JsonNode responseNode = mapper.readTree(responseStr);
			
			Assert.isTrue(responseNode.has("list"), "response don't have list?");
			JsonNode list = responseNode.get("list");

			Assert.notNull(list, "list is NULL??!?!?");

			JsonNode transactionNode = list.get(0);

			Assert.notNull(transactionNode, "transactionNode is null!!!");

			us_id = transactionNode.get("us_id").asInt();
			System.out.println("us_id : " + us_id);

			if (status == 1) {
				// 승인 처리. uid는 승인 처리 요청을 하는 상점 주인이 되겠다..
				uid = 75;
			} else {
				uid = 74;
			}
			System.out.println("uid : " + uid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.isTrue(false, "result is wrong");
		}
		
		Assert.isTrue(us_id > 0, "incorrect us_id");
		Assert.isTrue(uid > 0, "incorrect uid");
			
		// 사용 요청 처리하기.
		cmd.clear();
		cmd.put("us_id", us_id);
		cmd.put("uid", uid);
		cmd.put("status", status);
		cmd.put("commit_time", System.currentTimeMillis());
		
		Assert.isTrue(super.jsonData("/shop/spend/req/confirm", cmd), "spend didn't complete!");
	}
	
	@Test
	public void confirmUsePointTest() {
		usePointTest(1);
	}

//	@Test
	public void denyUsePointTest() {
		usePointTest(2);
	}
	
	public boolean usePointByUser(int status) {
		// TODO 포인트를 사용해서 구매하는 테스트 케이스.
				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode;

				// 사용 요청하기..
				Map<String, Object> cmd = new HashMap<String, Object>();
				cmd.put("uid", 74);
				cmd.put("sid", 1234);
				cmd.put("point", 3000);
				cmd.put("issued_time", System.currentTimeMillis());
				String result = super.getJsonData("/shop/spend/req", cmd);
				
				Assert.notNull(result, "/shop/spend/req response is empty");

				try {
					rootNode = mapper.readTree(result);
					Assert.isTrue(rootNode.get("success").asBoolean(), "result is not true");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Assert.isTrue(false, "result is wrong");
				}

				// 유저가 포인트 사용 요청 리스트를 가져오기.
				cmd.clear();
				cmd.put("uid", 74);		// 이거는 사용 요청을 한 유저의 uid
				result = super.getJsonData("/shop/spend/req/list", cmd);

				Assert.notNull(result, "/shop/spend/req/list response is empty");
				
				int us_id = -1;
//				int uid = -1;
				
				try {
					rootNode = mapper.readTree(result);
					Assert.isTrue(rootNode.get("success").asBoolean(), "result is not true");
					
					String responseStr = rootNode.get("response").asText();
					System.out.println("responseStr : " + responseStr);
					JsonNode responseNode = mapper.readTree(responseStr);
					
					Assert.isTrue(responseNode.has("list"), "response don't have list?");
					JsonNode list = responseNode.get("list");

					Assert.notNull(list, "list is NULL??!?!?");

					JsonNode transactionNode = list.get(0);

					Assert.notNull(transactionNode, "transactionNode is null!!!");

					us_id = transactionNode.get("us_id").asInt();
					System.out.println("us_id : " + us_id);

//					uid = transactionNode.get("uid").asInt();
//					System.out.println("uid : " + uid);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Assert.isTrue(false, "result is wrong");
				}
				
				Assert.isTrue(us_id > 0, "incorrect us_id");
//				Assert.isTrue(uid > 0, "incorrect uid");
					
				// 사용 요청 처리하기.
				cmd.clear();
				cmd.put("us_id", us_id);
				cmd.put("uid", 74);
				cmd.put("status", status);
				cmd.put("commit_time", System.currentTimeMillis());
				
				return super.jsonData("/shop/spend/req/confirm", cmd);
	}
	
//	@Test
	public void confirmUsePointByUserTest() {
		Assert.isTrue(!usePointByUser(1), "성공하면 안되는 케이스!");
	}
	
//	@Test
	public void denyUsePointByUserTest() {
		Assert.isTrue(usePointByUser(2), "실패하면 안되는 케이스!");
	}
	
//	@Test
	public void getStoreListTest() {
		Map<String, Object> cmd = new HashMap<String, Object>();
		cmd.put("lat_x", 37.12345);
		cmd.put("long_y", 127.12345);
		Assert.isTrue(super.jsonData("/shop/list", cmd), "성공이어야 하는데...");
	}
	
//	@Test
	public void getStorePointTest() {
		Map<String, Object> cmd = new HashMap<String, Object>();
		cmd.put("uid", 75);
		cmd.put("sid", 1234);
		Assert.isTrue(super.jsonData("/shop/point", cmd), "성공이어야 하는데...");
	}
	
	@Test
	public void getStoreIncomeTest() {
		Map<String, Object> cmd = new HashMap<String, Object>();
		cmd.put("uid", 75);
		cmd.put("sid", 1234);
		Assert.isTrue(super.jsonData("/shop/income", cmd), "성공이어야 하는데...");
	}
	
}
