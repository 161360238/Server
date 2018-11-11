package cn.wj.server;

/**
 * <servlet> <servlet-name>login</servlet-name>
 * <servlet-class>com.shsxt.user.LoginServlet</servlet-class> </servlet>
 * 
 * @author 王杰
 *
 */
public class Entity {
	private String name;
	private String clz;

	public Entity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClz() {
		return clz;
	}

	public void setClz(String clz) {
		this.clz = clz;
	}

}
