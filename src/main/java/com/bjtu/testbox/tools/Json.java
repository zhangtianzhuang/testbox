//package com.bjtu.testbox.tools;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletResponse;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.PropertyFilter;
//
//public class Json {
//
//	public static void pritnTo(HttpServletResponse response, String jsonortext,
//			String json) {
//		PrintWriter writer = null;
//		response.setContentType("text/" + jsonortext + ";charset=utf-8");
//		response.setCharacterEncoding("utf-8");
//		try {
//			writer = response.getWriter();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		writer.print(json);
//		if (writer == null) {
//			writer.flush();
//			writer.close();
//		}
//	}
//
//	public static void pritnTo(HttpServletResponse response, String jsonortext,
//			JSONObject json) {
//		PrintWriter writer = null;
//		response.setContentType("text/" + jsonortext + ";charset=utf-8");
//		response.setCharacterEncoding("utf-8");
//		try {
//			writer = response.getWriter();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		writer.print(json);
//
//		if (writer == null) {
//			writer.flush();
//			writer.close();
//		}
//
//	}
//
//	public static String toJson(List<?> lists, final String... strings) {
//		// 转下 json
//		PropertyFilter profilter = new PropertyFilter() {
//			//过滤
//			@Override
//			public boolean apply(Object object, String name, Object value) {
//				for (String str : strings) {
//					if (name.equalsIgnoreCase(str))
//						return false;
//				}
//				return true;
//			}
//		};
//		String jsons = JSON.toJSONString(lists, profilter);
//		return jsons;
//	}
//
//	public static String toJson(Map<String, Object> map,
//			final String... strings) {
//		// 转下 json
//		PropertyFilter profilter = new PropertyFilter() {
//			// 过滤
//			@Override
//			public boolean apply(Object object, String name, Object value) {
//				for (String str : strings) {
//					if (name.equalsIgnoreCase(str))
//						return false;
//				}
//				return true;
//			}
//		};
//		String jsons = JSON.toJSONString(map, profilter);
//		return jsons;
//	}
//
//	public static void main(String[] args) {
//
//	}
//
//}
