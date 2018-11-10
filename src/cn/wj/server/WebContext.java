package cn.wj.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上下文对象，解析的结果存储到这个类，
 * 并通过这个类获取对应servlet的路径
 * @author 王杰
 *
 */
public class WebContext {
	
	//key->servlet-name  value->servlet-class
	private Map<String,String> entityMap=new HashMap<>();
	//key->url-pattern  value->servlet0-name
	private Map<String,String> mappingMap=new HashMap<>();
	public WebContext(List<Entity> entitys, List<Mapping> mappings) {
		super();
		
		//将entity的List转换成对应的map
		for(Entity entity:entitys){
			entityMap.put(entity.getName(), entity.getClz());
		}
		//将map的List转成对应的map
		for(Mapping mapping:mappings){
			for(String pattern:mapping.getPatterns()){
				mappingMap.put(pattern, mapping.getName());
			}
		}
	}
	/**
	 * 通过URL的路径找到对应的class
	 */
	public String getClz(String pattern){
		String name=mappingMap.get(pattern);
		return entityMap.get(name);
	}
	
}
