package cn.wj.server;

/**
 * 服务器小脚本接口
 * @author 王杰
 *
 */
public interface Servlet {
	void service(Request request,Response response);
}
