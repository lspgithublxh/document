package com.bj58.fang.hugopenapi.sdk.OPENTEST;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.bj58.fang.hugopenapi.client.Entity.BrokerESFEntity;
import com.bj58.fang.hugopenapi.client.Entity.BrokerZFEntity;
import com.bj58.fang.hugopenapi.client.Entity.CompanyESFEntity;
import com.bj58.fang.hugopenapi.client.Entity.CompanyZFEntity;
import com.bj58.fang.hugopenapi.client.Entity.PicEntity;
import com.bj58.fang.hugopenapi.client.Entity.RentedDetailsEntity;
import com.bj58.fang.hugopenapi.client.Entity.result.CommonResult;
import com.bj58.fang.hugopenapi.client.enumn.AddOrUpdate;
import com.bj58.fang.hugopenapi.client.enumn.house.Chaoxiang;
import com.bj58.fang.hugopenapi.client.enumn.house.PaymentTerms;
import com.bj58.fang.hugopenapi.client.enumn.house.RentType;
import com.bj58.fang.hugopenapi.client.enumn.ldt.SiteId;
import com.bj58.fang.hugopenapi.client.enumn.rentdetail.RenterAge;
import com.bj58.fang.hugopenapi.client.enumn.rentdetail.RenterType;
import com.bj58.fang.hugopenapi.client.enumn.rentdetail.RenterWork;
import com.bj58.fang.hugopenapi.client.exception.ESFException;
import com.bj58.fang.hugopenapi.client.service.DispLocalService;
import com.bj58.fang.hugopenapi.client.service.InitService;
import com.bj58.fang.hugopenapi.client.service.MainService;
import com.bj58.fang.hugopenapi.client.service.XQService;
import com.bj58.fang.hugopenapi.client.service.pub.BrokerPublicService;
import com.bj58.fang.hugopenapi.client.service.pub.CompanyPublicService;
import com.bj58.fang.hugopenapi.client.service.wlt.LdtService;

public class TestClient {

	public static void main(String[] args) {
		tokenGetFromLocal(false);
		//3.具体的调用：
		//发布二手房！
		publishCompanyESF(AddOrUpdate.Add);
//		publishCompanyESF(AddOrUpdate.Update);
//		publishBrokeESF(AddOrUpdate.Add);
//		publishBrokeESF(AddOrUpdate.Update);
		//租房
//		publishCompanyZF(AddOrUpdate.Add);
//		publishCompanyZF(AddOrUpdate.Update);
//		publishBrokerZF(AddOrUpdate.Add);
//		publishBrokerZF(AddOrUpdate.Update);
		//公共接口测试
//		full_publicInterfaceTest();
//		publicInterfaceTest();
		//TODO 3.22 来电通接口
		ldtService();
		//cmd模式测试
//		runbyCmd();

	}

	private static void ldtService() {
		LdtService ldtS = MainService.getLdtService();
		//添加公司白名单
//		CommonResult rs = ldtS.addCompanyWhiteSheet("20000101", "20190303", LdtType.SECRET_EXACT_MATCH);
		//查询公司白名单
//		CommonResult rs = ldtS.queryCompanyWhiteSheet();
//		//更新公司开通状态
//		CommonResult rs = ldtS.updateCompanyState(LdtState.used);
//		//更新公司开通状态
//		CommonResult rs = ldtS.addBrokerWhiteSheet("1111");
//		//查询经纪人精准匹配白名单配置
//		CommonResult rs = ldtS.queryBrokerExactWhiteSheetConfig(1111);
//		//分页查询公司精准匹配白名单名单
//		CommonResult rs = ldtS.pageQueryCompanyExactWhiteSheet(1, 10);
//		//按照状态分页查询公司精准匹配白名单名单
//		CommonResult rs = ldtS.pageQueryCompanyExactWhiteSheetByState(1, 10, LdtState.used);
//		//判断是否需要开通精准匹配
//		CommonResult rs = ldtS.isVip(1111);
//		//查询经纪人小号
		CommonResult rs = ldtS.queryBrokerSecretPhone(1111, SiteId.anjuke);
		System.out.println(rs);
	}
	
	private static void cityListGet() {
		CommonResult rs = MainService.getDispLocalService().getCityList();
		System.out.println(rs);
	}

	private static void full_publicInterfaceTest() {
		ExecutorService service = Executors.newFixedThreadPool(100);
		List<Integer> list = new ArrayList<>();
		List<Future> fl = new ArrayList<>();
		for(int i = 0; i < 100000; i++) {
			int j = i;
			Future<?> f = service.submit(()->{
				CommonResult r = publicInterfaceTest();
				if(r.getCode() == 200) {
					list.add(j);
				}
			});
			fl.add(f);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(Future x : fl) {
			try {
				x.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println(list);
	}

	private static void tokenGetFromLocal(boolean local) {
		if(local) {
			//1.测试环境
			InitService.test(true, "5f29993c5735f99d5e97dad6e536b99c");//94eaf4baa37f262a94a1b33ae71a077e
		}else {
			//1.正式环境  先 初始化clientId, clientSecret
			initClientIdAndClientSecret();
			//2.再 看所在项目是否部署在分布式环境，集群中; 是则入参为true, 且要配置tokenserver所在机器的ip,port
			tokenGetFromCenterService(true);
//			tokenGetFromCenterService(false);
		}
	}

	private static void initClientIdAndClientSecret() {
		String clientId = "b6ab5dc63efb2ea7c7de1317bd9a9d58";
		String clientSecret = "180def1ba07798ba4447790830358be3";
		long saveTime = 7776000000l;
		InitService.init(clientId, clientSecret, saveTime);
	}

	private static void tokenGetFromCenterService(boolean distributed) {
		if(distributed) {
			Map<String, Integer> ipPortMap = new HashMap<String, Integer>();
			ipPortMap.put("localhost", 16778);
			InitService.configDistributeCondition(true, ipPortMap);
//			InitService.configDistributeConditionOf3Service(true, "D:\\config\\mysql.properties", DisLockType.mysql);
//			InitService.configDistributeConditionOf3Service(true, "D:\\config\\redis.properties", DisLockType.redis);
//			InitService.configDistributeConditionOf3Service(true, "D:\\config\\zookeeper.properties", DisLockType.zookeeper);
//			InitService.configDistributeConditionOfOtherService(true, "D:\\config\\oracle.properties", OracleProvider.class);

		}
	}

	private static CommonResult publicInterfaceTest() {
		BrokerPublicService serv = MainService.getBrokerPublicService();
		CompanyPublicService service = MainService.getCompanyPublicService();
		DispLocalService dserv = MainService.getDispLocalService();
		//TODO 公司库 公共接口
		//删除房源
//		CommonResult rs = service.deleteHouse("1122334432", Cateid.ershoufang);
		//公司库房源认领 公司房源库到经纪人房源库, brokerids如果多值，值之间|分割，如122|334|567
//		CommonResult rs = service.postCompanyFangToBrokerFang("1122334432", "11111", Plats.wuba, Cateid.ershoufang);
		//公司库房源关联经纪人房源列表
//		CommonResult rs = service.getSanWangFANGByCompanyFang("1122334432", Cateid.ershoufang);
		//支持状态筛选的公司库房源关联经纪人房源列表查询接口
//		CommonResult rs = service.getBrokerHouseCountByBianhaoByPage("1122334432", Cateid.ershoufang, Type.youxiao, null, null, null, null);
		//公司库房源关联经纪人房源总数查询接口
//		CommonResult rs = service.getBrokerHouseCountByBianhao("1122334432", Cateid.ershoufang, Type.youxiao, null, null);
		//公司房源库是否存在查询接口
//		CommonResult rs = service.existsCompanyFang("1122334432", Cateid.ershoufang);
		//公司库房源状态更新接口
//		CommonResult rs = service.updateCompanyFangState("1122334432", Cateid.ershoufang, State.chengjiao);
		//公司库房源状态查询接口
//		CommonResult rs = service.getCompanyFangState("1122334432", Cateid.ershoufang);
		//经纪人认领房源全量删除接口
//		CommonResult rs = service.deleteBrokerFang("1111", "0");
		
		
		//部门新增
//		DepartmentEntity dte = new DepartmentEntity();
//		dte.setBrokerageDeptId("1");
//		dte.setDeptAddress("海淀区");
//		dte.setDeptLevel(1);
//		dte.setDeptName("房产");
//		dte.setParentDeptId("0");
//		dte.setParentDeptIdType(DeptIdType.jingjigongsibumenid.getValue());
//		dte.setParentDeptLevel(1);
//		dte.setUserId("1111");
//		dte.setUserIdType(UserIdType.jingjigongsiUser.getValue());
//		CommonResult rs = service.departmentAdd(dte);

		//部门查询
//		CommonResult rs = service.departmentQuery("1", DeptIdType.jingjigongsibumenid, 1);
		//部门名称模糊查询
		CommonResult rs = service.departmentFuzzyQuery("京");
		
//		//员工新增更新
//		EmployeeEntity ee = new EmployeeEntity();
//		ee.setAccountId("1");
//		ee.setAccountName("weiheshi");
//		ee.setBrokerageDeptLevel(1);
//		ee.setBrokerId(1111l);
//		ee.setDeptId("1");
//		ee.setDeptIdType(DeptIdType.jingjigongsibumenid.getValue());
//		ee.setEntryTime("" + System.currentTimeMillis() / 1000);
//		ee.setPassword("112");
//		ee.setPhone("13654533222");
//		ee.setPositionId(1);
//		ee.setTrueName("小明");
//		CommonResult rs = service.employeeAddUpdate(ee);
		//员工查询
//		CommonResult rs = service.employeeQuery("1111", UserIdType.jingjigongsiUser);
		//绑定三网账号
//		CommonResult rs = service.bindSanWangAcount("1123", UserIdType.jingjigongsiUser, 1111l);
		//解除三网绑定关系
//		CommonResult rs = service.deleteSiteBinding("1111", UserIdType.jingjigongsiUser);
		//员工离职
//		CommonResult rs = service.employeeDimission("1111", UserIdType.jingjigongsiUser);
		//员工批量冻结
//		CommonResult rs = service.employeeFreezeBatch("1111", UserIdType.jingjigongsiUser, IsFreeze.jiedong);
		
		//新建更新带看记录
//		HouseWorkEntity hwe = new HouseWorkEntity();
//		hwe.setBianhao("");
//		hwe.setBizType(BizType.ershoufang.getValue());
//		hwe.setBrokerageWorkId("1");
//		hwe.setCustomerId("2");
//		hwe.setFollowTime(System.currentTimeMillis());
//		hwe.setUserId("122");
//		hwe.setUserIdType(UserIdType.jingjigongsiUser.getValue());
//		hwe.setWorkContent("");
//		CommonResult rs = service.houseworkAddUpdate(hwe);
		
		//新建成交记录接口
//		DealRecordEntity dre = new DealRecordEntity();
//		dre.setBianhao("1122334432");
//		dre.setCommission(1000l);
//		dre.setContractNo("xxxs");
//		dre.setDealPrice(1000000l);
//		dre.setDealTime(System.currentTimeMillis());
//		dre.setDeptId("ss");
//		dre.setDeptIdType(DeptIdType.jingjigongsibumenid.getValue());
//		dre.setDeptLevel(1);
//		dre.setUserId("");
//		dre.setUserIdType(UserIdType.jingjigongsiUser.getValue());
//		CommonResult rs = service.dealRecordAddUpdate(dre);
		
		//新增和更新角色人
//		RoleEntity re = new RoleEntity();
//		re.setBianhao("1122334432");
//		re.setGuardian("1111");
//		re.setInputPerson("1111");
//		re.setKey("1111");
//		re.setPromotion("1111");
//		re.setSurvey("1111");
//		re.setUserIdType(UserIdType.jingjigongsiUser.getValue());
//		CommonResult rs = service.roleAddUpdate(re);
		
		//配置可视盘
//		CommonResult rs = service.settingsViewRange("1", DeptIdType.jingjigongsibumenid, "320");
		
		//TODO 经纪人库 公共接口
		//获取三网经纪人id
//		CommonResult rs = serv.getSanwangBrokerByPhoneOrUserName(QueryType.by_mobilePhone, "1122222", "lsp", true);
		//根据用户名获取三网经纪人id以及58、赶集和安居客平台的经纪人id接口 
//		CommonResult rs = serv.getSanwangBrokerIdByUserName("lsp");
		//根据身份证号码查询三网经纪人id
//		CommonResult rs = serv.getSanwangBrokerIdByIdentity("12344");	
		//查询经纪人通话详情
//		CommonResult rs = serv.queryBrokerTelephone("1111", "1122222", null, null, null, null);
		//查询经纪人通话详情2， 第二种实现
//		BrokerPhoneEnttiy entity = new BrokerPhoneEnttiy();
//		entity.setBrokerid(1111l);
//		entity.setClientPhone("12211111111");
//		entity.setEndTime("" + System.currentTimeMillis());
//		entity.setPage("1");
//		entity.setSize("10");
//		entity.setStartTime("" + (System.currentTimeMillis() - 1000000));
//		CommonResult rs = serv.queryBrokerTelephone(entity);
		//批量查询经纪人通话详情
//		CommonResult rs = serv.queryBrokerTelephoneBatch("11111", "" + (System.currentTimeMillis() - 10000),
//		"" + System.currentTimeMillis(), 1	, 10);
		//批量查询录音地址
//		CommonResult rs = serv.queryLogsBatch("", "", "");
		//获取当前城市的房源标签
//		CommonResult rs = serv.currCityFangTag(100);
		
		//标准城市id和区域名称查询
//		CommonResult rs = dserv.getCityList();
		//标准磐石小区名称模糊匹配
//		CommonResult xq = MainService.getXQService().matchXiaoqu("远洋", 10);
		//TOOO 3.22剩余的多个接口
		xiaoquGet();
		System.out.println(rs);
		return rs;
	}

	private static void xiaoquGet() {
		XQService xq = MainService.getXQService();
//		XQEntity entity = new XQEntity();
//		entity.setCity_id(1);
//		entity.setCommunity_name("");
//		entity.setAddress("");
//		entity.setLat("");
//		entity.setLng("");
//		entity.setPanshi_property_type(PanshiPropertyType.bangong.getValue());
//		entity.setOwnership_type(OwnerShipType.bangong.getValue());
//		entity.setBase_images("");
//		entity.setCommunity_alias("");
//		entity.setSubmit_id("");
//		entity.setSubmit_name("");
//		entity.setSubmit_phone("");
//		entity.setSubmit_type(1);
//		entity.setAjk_area_block("");
//		entity.setWuba_area_block("");
//		entity.setGanji_area_block("");
//		entity.setShangquan("");
		//小区新增
//		CommonResult rs = xq.addPanshiXiaoqu(entity);
//		XQRecoveryEntity entity = new XQRecoveryEntity();
//		entity.setCity_id(1);
//		entity.setCommunity_name("");
//		entity.setCommunity_id("");
//		entity.setAddress("");
//		entity.setLat("");
//		entity.setLng("");
//		entity.setPanshi_property_type(PanshiPropertyType.bangong.getValue());
//		entity.setOwnership_type(OwnerShipType.bangong.getValue());
//		entity.setBase_images("");
//		entity.setCommunity_alias("");
//		entity.setAjk_area_block("");
//		entity.setWuba_area_block("");
//		entity.setGanji_area_block("");
//		entity.setShangquan("");
//		entity.setSubmit_id("");
//		entity.setSubmit_name("");
//		entity.setSubmit_phone("");
//		entity.setSubmit_type(1);
//		entity.setHouse_build_no("");
//		entity.setHouse_unit_no("");
//		entity.setHouse_room_no("");
//		entity.setHouse_floor(1);
//		entity.setHouse_floor_num(1);
//		entity.setHouse_area(1);
//		entity.setHouse_house_images("");
//		entity.setHouse_house_id(1);
//		entity.setHouse_houses(1);
//		entity.setHouse_price(0.0d);
//		entity.setHouse_price_images("");
//		entity.setHouse_desc("");
//		entity.setPhase(1);
//		entity.setCommunity_abbr("");
//		entity.setSearch_name("");
//		entity.setFactor_name("");
//		entity.setNew_house_show_name("");
//		entity.setNavigation_address("");
//		entity.setGaode_lat("");
//		entity.setGaode_lng("");
//		entity.setDistrict_name("");
//		entity.setCircle_name("");
//		entity.setPostcode("");
//		entity.setStreet_name("");
//		entity.setNumber_type(1);
//		entity.setEstablished_year("");
//		entity.setCommunity_houses(1);
//		entity.setGreen_rate("");
//		entity.setPlot_rate("");
//		entity.setProperty_company_name("");
//		entity.setProperty_fee("");
//		entity.setProperty_features("");
//		entity.setProperty_office_address("");
//		entity.setProperty_telephone("");
//		entity.setProperty_others("");
//		entity.setDeveloper_name("");
//		entity.setInvestor_name("");
//		entity.setMerits("");
//		entity.setDemerits("");
//		entity.setOthers("");
//		entity.setOwnership_types("");
//		//磐石小区纠错
//		CommonResult rs = xq.recoveryPanshiXiaoqu(entity);
		//纠错查询
		CommonResult rs = xq.queryPanshiXiaoqu(11);
		//磐石小区精准查询
//		CommonResult rs = xq.queryExactlyPanshiXiaoqu("111");
		System.out.println(rs);
	}
	
	private static void runbyCmd() {
		String cmd = "";
		Scanner scanner = new Scanner(System.in);
		Class<?> type = TestClient.class;
		TestClient test = new TestClient();
		while(true) {
			cmd = scanner.nextLine();
			String[] methodP = cmd.split("\\s+");
			try {
				Method m = type.getDeclaredMethod(methodP[0], AddOrUpdate.class);
				if("add".equals(methodP[1])) {
					System.out.println();
					m.invoke(null, AddOrUpdate.Add);//methodP[1]
				}else if("update".equals(methodP[1])) {
					m.invoke(null, AddOrUpdate.Update);//methodP[1]
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	private static void publishCompanyZF(AddOrUpdate operate) {
		CompanyZFEntity entity = new CompanyZFEntity();

		entity.setRentType(RentType.hezu.getValue());
		entity.setPaymentTerms(PaymentTerms.bannianfu.getValue());
		entity.setRoomType(1);
		entity.setBuildingArea(300d);
		entity.setBianhao("123");
//		entity.setXiaoqu("1+1城中城");
//		entity.setQuyu("上海周边");
		entity.setXiaoquId(100375975);
		entity.setTitle("好的顶顶顶顶顶顶顶顶顶休息休息 title");
		entity.setShi(1);
		entity.setTing(1);
		entity.setWei(1);
		entity.setZonglouceng(1);
		entity.setSuozailouceng(1);

		entity.setMianji(30d);
		entity.setChaoxiang(1);//Chaoxiang.bei;
		entity.setZhuangxiuqingkuang(1);
		entity.setFangwuleixing(1);
		entity.setJiage(120l);
		List<PicEntity> picList = new ArrayList<PicEntity>();
		for(int i = 0; i  < 9; i++) {
			PicEntity pic = new PicEntity();
			pic.setCategory( i / 3 + 1 + "");
			pic.setIscover("0");
			pic.setUrl(String.format("example.pic/pic_%s.jpg", i));
			picList.add(pic);
		}
		entity.setPicList(picList);
		List<RentedDetailsEntity> renList = new ArrayList<RentedDetailsEntity>();
		for(int i = 0; i  < 9; i++) {
			RentedDetailsEntity ren = new RentedDetailsEntity();
			ren.setRenterAge(RenterAge.age_0_20.getValue());
			ren.setRenterNum("1");
			ren.setRenterType(RenterType.nansheng.getValue());
			ren.setRenterWork(RenterWork.caiwukuaiji.getValue());
			renList.add(ren);
		}
		entity.setRentedDetailsList(renList);
//		entity.setRentedDetails(rentedDetails);
//		entity.setPic("[{\"category\":\"1\",\"url\":\"example.pic/pic_2.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_3.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"3\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"2\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"}]");
//		entity.setRentedDetails("[{\"renterType\":\"101\",\"renterNum\":\"1\",\"renterAge\":\"201\",\"renterWork\":\"16\"},{\"renterType\":\"101\",\"renterNum\":\"2\",\"renterAge\":\"202\",\"renterWork\":\"27\"}]");
		try {
			CommonResult rs = MainService.getZFService().addUpdateCompanyZF(entity, operate);//, ComanyOrBroker.Comany, UrlConst.COM_ZF_ADD
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void publishBrokerZF(AddOrUpdate operate) {
		BrokerZFEntity entity = new BrokerZFEntity();

		entity.setHouseid(1111l);
		entity.setBrokerid(1501178l);
		entity.setRentType(1);
		entity.setPaymentTerms(1);
		entity.setRoomType(1);
		entity.setBuildingArea(300d);
		entity.setBianhao("123");
//		entity.setXiaoqu("新建小区");
//		entity.setQuyu("朝阳");
		entity.setXiaoquId(100375975);
		entity.setTitle("好的顶顶顶顶顶顶顶顶顶休息休息 title");
		entity.setShi(1);
		entity.setTing(1);
		entity.setWei(1);
		entity.setZonglouceng(1);
		entity.setSuozailouceng(1);

		entity.setMianji(30d);
		entity.setChaoxiang(1);
		entity.setZhuangxiuqingkuang(1);
		entity.setFangwuleixing(1);
		entity.setJiage(120l);
		List<PicEntity> picList = new ArrayList<PicEntity>();
		for(int i = 0; i  < 9; i++) {
			PicEntity pic = new PicEntity();
			pic.setCategory( i / 3 + 1 + "");
			pic.setIscover("0");
			pic.setUrl(String.format("example.pic/pic_%s.jpg", i));
			picList.add(pic);
		}
		entity.setPicList(picList);
		List<RentedDetailsEntity> renList = new ArrayList<RentedDetailsEntity>();
		for(int i = 0; i  < 9; i++) {
			RentedDetailsEntity ren = new RentedDetailsEntity();
			ren.setRenterAge(RenterAge.age_0_20.getValue());
			ren.setRenterNum("1");
			ren.setRenterType(RenterType.nansheng.getValue());
			ren.setRenterWork(RenterWork.caiwukuaiji.getValue());
			renList.add(ren);
		}
		entity.setRentedDetailsList(renList);
//		entity.setRentedDetails(rentedDetails);
//		entity.setPic("[{\"category\":\"1\",\"url\":\"example.pic/pic_2.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_3.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"3\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"2\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"}]");
//		entity.setRentedDetails("[{\"renterType\":\"101\",\"renterNum\":\"1\",\"renterAge\":\"201\",\"renterWork\":\"16\"},{\"renterType\":\"101\",\"renterNum\":\"2\",\"renterAge\":\"202\",\"renterWork\":\"27\"}]");
		try {
			CommonResult rs = MainService.getZFService().addUpdateBrokerZF(entity, operate);//, ComanyOrBroker.Comany, UrlConst.COM_ZF_ADD
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void publishBrokerZF() {
		BrokerZFEntity entity = new BrokerZFEntity();

		entity.setBrokerid(1l);
		entity.setRentType(1);
		entity.setPaymentTerms(1);
		entity.setRoomType(1);
		entity.setBuildingArea(300d);
		entity.setBianhao("123");
		entity.setXiaoqu("新建小区");
		entity.setQuyu("朝阳");
//		entity.setXiaoquId(1);
		entity.setTitle("好的顶顶顶顶顶顶顶顶顶休息休息");
		entity.setShi(1);
		entity.setTing(1);
		entity.setWei(1);
		entity.setZonglouceng(1);
		entity.setSuozailouceng(1);

		entity.setMianji(30d);
		entity.setChaoxiang(1);
		entity.setZhuangxiuqingkuang(1);
		entity.setFangwuleixing(1);
		entity.setJiage(120l);
		entity.setPic("[{\"category\":\"1\",\"url\":\"example.pic/pic_2.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_3.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"3\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"2\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"}]");
		try {
			CommonResult rs = MainService.getZFService().addUpdateBrokerZF(entity, AddOrUpdate.Add);//, ComanyOrBroker.Broker, UrlConst.COM_ZF_ADD
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void publishBrokeESF(AddOrUpdate operate) {
		BrokerESFEntity entity = new BrokerESFEntity();
		entity.setBrokerid(1501178l);
		entity.setJianzhuniandai(1992);
		entity.setBianhao("123");
//		entity.setXiaoqu("");
//		entity.setQuyu("");
		entity.setXiaoquId(320);
		entity.setTitle("eeeeeeeeeeeeeeeeeeeeeeeee");
		entity.setShi(1);
		entity.setTing(1);
		entity.setWei(1);
		entity.setZonglouceng(1);
		entity.setSuozailouceng(1);
		
		entity.setMianji(20d);
		entity.setChaoxiang(1);
		entity.setZhuangxiuqingkuang(1);
		entity.setFangwuleixing(1);
		entity.setJiage(1l);
		List<PicEntity> picList = new ArrayList<PicEntity>();
		for(int i = 0; i  < 9; i++) {
			PicEntity pic = new PicEntity();
			pic.setCategory( i / 3 + 1 + "");
			pic.setIscover("0");
			pic.setUrl(String.format("example.pic/pic_%s.jpg", i));
			picList.add(pic);
		}
		entity.setPicList(picList);
//		entity.setPic("[  {      \"category\":\"1\",       \"url\":\"example.pic/pic_0.jpg\",       \"iscover\":\"0\"  }]");
		try {
			CommonResult rs = MainService.getESFService().addUpdateBrokerESF(entity, operate);//, ComanyOrBroker.Comany, UrlConst.COM_ESF_ADD
			System.out.println(rs);
		} catch (ESFException e) {
			e.printStackTrace();
		}
	}

	private static void publishCompanyESF(AddOrUpdate oprate) {
		CompanyESFEntity entity = new CompanyESFEntity();
		entity.setBeianbianhao("1122334432");
		entity.setBianhao("1122334432");
		entity.setXiaoqu("远洋山水北区");
//		entity.setDeptId(0l);//
		entity.setQuyu("石景山");
		entity.setTitle("测试一套二手房,嗯好的不错,eeeeeee呃呃呃呃呃呃");
		entity.setShi(1);
		entity.setTing(1);
		entity.setWei(1);
		entity.setZonglouceng(10);
		entity.setSuozailouceng(1);
		entity.setMianji(80.19d);
		entity.setJianzhuniandai(1992);
		entity.setChaoxiang(Chaoxiang.dong.getValue());
		entity.setZhuangxiuqingkuang(1);
		entity.setFangwuleixing(1);
		entity.setJiage(7000000l);//20000
		List<PicEntity> picList = new ArrayList<PicEntity>();
		for(int i = 0; i  < 9; i++) {
			PicEntity pic = new PicEntity();
			pic.setCategory( i / 3 + 1 + "");
			pic.setIscover("0");
			pic.setUrl(String.format("example.pic/pic_%s.jpg", i));
			picList.add(pic);
		}
		entity.setPicList(picList);
		//补充信息
		try {
			CommonResult rs = MainService.getESFService().addUpdateCompanyESF(entity, oprate);//, AddOrUpdate.Add, ComanyOrBroker.Comany, UrlConst.COM_ESF_ADD
			System.out.println(rs);
		} catch (ESFException e) {
			e.printStackTrace();
		}
	}
}
