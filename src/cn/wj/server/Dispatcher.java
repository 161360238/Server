package cn.wj.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

/**
 * 分发器：
 * 
 * @author 王杰
 *
 */
public class Dispatcher implements Runnable {
	private Socket client;
	private Request request;
	private Response response;

	public Dispatcher(Socket client) {
		this.client = client;
		try {
			// 把请求的信息封装到request里面，然后传递给对应的servlet
			request = new Request(client);
			// 封装的响应信息的类，由程序员根据业务需求手动进行封装，然后返回到浏览器
			response = new Response(client);
		} catch (IOException e) {
			this.release();
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			if (null == request.getUrl() || request.getUrl().equals("")) {
				BufferedReader br = new BufferedReader(new FileReader("src/index.html"));
				String line = null;
				while ((line = br.readLine()) != null) {
					response.print(line);
				}
				br.close();
				return;
			}
			// 通过反射获取对应的servlet
			Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
			if (null != servlet) {
				servlet.service(request, response);
				// 设置状态码
				response.pushToBrowser(200);
			} else {
				// 出现了错误
				BufferedReader br = new BufferedReader(new FileReader("src/error.html"));
				String line = null;
				while ((line = br.readLine()) != null) {
					response.print(line);
				}
				br.close();
				response.pushToBrowser(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.println("服务器端错误");
				response.pushToBrowser(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		release();
	}

	// 释放资源
	private void release() {
		try {
			client.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
