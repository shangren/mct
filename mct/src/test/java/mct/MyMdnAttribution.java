package mct;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyMdnAttribution {

	public static void main(String[] args){
		String fileName = "D://region.txt";
		String fileNamePlatform = "D://platform.csv";
		String fileNameCity = "D://city.csv";
		
		Map<String, String> platformMap = new HashMap<String, String>();
		Map<String, String> cityMap = new HashMap<String, String>();
		Set set = new HashSet();
		
		long id=0 ,ids= 0;
		try {
			String lineStr = null;
			String[] attrs = null;
			LineNumberReader reader =  new LineNumberReader(new BufferedReader(new FileReader(new File(fileNamePlatform))));
			while((lineStr = reader.readLine()) != null){
				attrs = lineStr.split(",");
				if(attrs.length < 3){
					System.out.println("错误 ->>" + reader.getLineNumber());
					reader.close();
					return ;
				}
				platformMap.put(attrs[1].replace(" ", "").trim(),attrs[0].replace(" ", ""));
			
				
			}
			reader.close();
			
			System.out.println("platform 文件解析完成");
			reader =  new LineNumberReader(new BufferedReader(new FileReader(new File(fileNameCity))));
			
			while((lineStr = reader.readLine()) != null){
				attrs = lineStr.split(",");
				if(attrs.length < 3){
					System.out.println("错误 ->>" + reader.getLineNumber());
					reader.close();
					return ;
				}
				cityMap.put(attrs[1].replace(" ", "").trim(),attrs[0].replace(" ", ""));
			
			}
			
			reader.close();
			
			Writer writer = new BufferedWriter(new FileWriter(new File("D://city.sql")));
			reader =  new LineNumberReader(new BufferedReader(new FileReader(new File(fileName))));
			while((lineStr = reader.readLine()) != null){
				id++;
				attrs = lineStr.split(",");
				if(attrs.length < 3){
					System.out.println("error, at line " + reader.getLineNumber());
					break;
				}
				
				//号段
				String mdnSection = attrs[0];
				String[] tmp = attrs[1].split(" ");
				//省份
				String province = tmp[0].replace(" ", "");
				
				if(platformMap.get(province) == null){
					if(province.startsWith("内蒙")){
						province = "内蒙";
					}else{
						ids++;
					}
				}
				
				String provinceId = platformMap.get(province) == null ? "****" : platformMap.get(province);
				//地市   省和直辖市
				String city = tmp.length > 1 ? tmp[1].replace(" ", "") : tmp[0].replace(" ", "");
				if(city.equals("襄阳市")){
					city = "襄樊市";
				}
				String cityId = cityMap.get(city) == null ? (cityMap.get(city+"市") == null ? "****" : cityMap.get(city+"市")) : cityMap.get(city);
				if(cityMap.get(city) == null && cityMap.get(city+"市") == null){
				}
				//卡片类型
				String cardType = attrs[2].replace(" ", "");
				if(cityMap.get(city) == null && cityMap.get(city+"市") == null){
					if(city.contains("市")){
						cityId = cityMap.get(city.replace("市", "地区"));
						if(cityId == null){
							cityId = cityMap.get(city.replace("市", ""));
						}
					}else if(city.contains("地区")){
						cityId = cityMap.get(city.replace("地区", "市"));
						if(cityId == null){
							cityId = cityMap.get(city.replace("地区", ""));
							
						}
					}
					if(cityId == null){
						set.add(city);
						System.out.println("-" + mdnSection + "-" + province + "-" + provinceId + "-"  + city + "-" + cityId +"-"  + cardType);
						cityId = "0";
					}
				}
				
				
				writer.write("INSERT INTO im_mdn_attribution (id, mdn_section, province_name, province_id, city_name,city_id,card_type_desc) VALUES("
						+ "'"+ id +"',"
						+ "'"+ mdnSection +"',"
						+  "'"+province +"',"
						+  "'"+provinceId +"',"
						+ "'"+ city +"',"
						+  "'"+cityId +"',"
						+  "'"+cardType +"'"
						+");\r\n");
				
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("异常数据-->>" + set.size());
	}
}
