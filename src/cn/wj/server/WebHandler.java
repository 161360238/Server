package cn.wj.server;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 解析xml配置文件解析器的 处理器
 * @author 王杰
 *
 */
public class WebHandler extends DefaultHandler{
	private List<Entity> entitys=new ArrayList<>(); //存储解析出来的Entity
	private List<Mapping> mappings=new ArrayList<>();//存储解析出来的mapping
	private Entity entity;
	private Mapping mapping;
	private String tag;
	private boolean isMapping=false;  //用于区分标签类型
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(null!=qName){
			tag=qName;  //存储标签名
			if(tag.equals("servlet")){
				entity=new Entity();
				isMapping=false;
			}else if(tag.equals("servlet-mapping")){
				mapping=new Mapping();
				isMapping=true;
			}
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String contents=new String(ch, start, length).trim();   //获取标签内容
		if(null!=tag){   //处理空
			if(isMapping){  //解析servlet-mapping标签
				if(tag.equals("servlet-name")){
					mapping.setName(contents);
				}else if(tag.equals("url-pattern")){
					mapping.addPattern(contents);
				}
			}else {  //操作servlet
				if(tag.equals("servlet-name")){
					entity.setName(contents);
				}else if(tag.equals("servlet-class")){
					entity.setClz(contents);
				}
			}
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(null!=qName){
			if(qName.equals("servlet")){
				entitys.add(entity);
			}else if(qName.equals("servlet-mapping")){
				mappings.add(mapping);
			}
		}
		tag=null;
	}

	public List<Entity> getEntitys() {
		return entitys;
	}


	public List<Mapping> getMappings() {
		return mappings;
	}

}
