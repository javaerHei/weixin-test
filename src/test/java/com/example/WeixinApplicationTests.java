package com.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dao.ExpressCompanyDao;
import com.example.entity.ExpressCompany;
import com.example.weixin.WeiXinApiService;
import com.example.weixin.menu.Menu;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringbootApplication.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class WeixinApplicationTests {

	@Autowired
	private WeiXinApiService weiXinApiService;

	@Autowired
	private ExpressCompanyDao expressCompanyDao;

	@Test
	public void menu_test1() {
		String type = "click";
		String name = "今日歌曲";
		String key = "V1001_TODAY_MUSIC";
		Menu menu = new Menu(type, name, key, null);
		weiXinApiService.createButton(menu);
	}

	@Test
	public void menu_test2() {

		List<Menu> subMenu = new ArrayList<>();
		// 搜索
		Menu subMenu0 = new Menu("click", "笑话", "V1001_JOKE", null);
		Menu subMenu1 = new Menu("view", "搜索", null, "http://www.soso.com/");
		Menu subMenu2 = new Menu("view", "视频", null, "http://v.qq.com/");
		Menu subMenu3 = new Menu("location_select", "发送位置", "rselfmenu_2_0", null);
		Menu subMenu4 = new Menu("click", "赞一下我们", "V1001_GOOD", null);
		subMenu.add(subMenu0);
		subMenu.add(subMenu1);
		subMenu.add(subMenu2);
		subMenu.add(subMenu3);
		subMenu.add(subMenu4);

		Menu menu1 = new Menu("view", "菜单", null, null);
		menu1.setSub_button(subMenu);

		JSONArray menuJsonArray = new JSONArray();
		//
		Menu menu2 = new Menu("click", "今日歌曲", "V1001_TODAY_MUSIC", null);

		Menu menu3 = new Menu("view", "小工具", null, null);
		Menu subMenu31 = new Menu("view", "快递查询", null, "https://m.kuaidi100.com/");
		List<Menu> sub_button = new ArrayList<>();
		sub_button.add(subMenu31);
		menu3.setSub_button(sub_button);

		menuJsonArray.add(menu1);
		menuJsonArray.add(menu2);
		menuJsonArray.add(menu3);
		weiXinApiService.createAllButton(menuJsonArray);

	}

	@Test
	public void test_jdbc() {
		List<ExpressCompany> findActiveExpressCompanyList = expressCompanyDao.findActiveExpressCompanyList();
		String jsonString = JSONObject.toJSONString(findActiveExpressCompanyList);
		System.out.println(jsonString);
	}

	@Test
	public void test_jdbc2() {
		for (int i = 0; i < 10000000; i++) {
			expressCompanyDao.addExpressCompany("笑话", "http://www.baidu.com", "yd", "yd", "yd");
		}
	}

	@Test
	public void test_jdbc3() {
		long startTime = System.currentTimeMillis();
		System.out.println("start time:" + startTime);
		List<JdbcThread> list = new ArrayList<JdbcThread>();
		for (int i = 0; i < 50; i++) {
			JdbcThread thread = new JdbcThread();
			list.add(thread);
			thread.start();
		}
		// 
		try {
			for (JdbcThread my : list) {
				my.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end...:" + (System.currentTimeMillis() - startTime));
	}

	@Test
	public void test_jdbc4() {
		
	}
	
	class JdbcThread extends Thread {

		@Override
		public void run() {
			List<ExpressCompany> list = new ArrayList<>();
			for (int i = 0; i < 100000; i++) {
				ExpressCompany company = new ExpressCompany();
				company.setCompanyName("cessss" + i);
				company.setCompanyUrl("http://www.baidu.com");
				company.setFineexCode("xx");
				company.setKdybCode("kdyb");
				company.setHaoserviceCode("hao");
				list.add(company);
			}
			System.out.println(Thread.currentThread().getName() + " running start..");
			expressCompanyDao.batchAdd(list);
			System.out.println(Thread.currentThread().getName() + " running end ..");
		}

	}
}
