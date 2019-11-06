package weibo;

import input.create_hottrace_table;
import input.input_hot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import model.hot;
import util.Tool;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;


public class hottrace {
	private static final WebClient webClient = new WebClient(
			BrowserVersion.INTERNET_EXPLORER_6);
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		Login("*******", "*****");
		String name = "民生银行";
		//create_hottrace_table.createhottracetable("hot");
		String start_date = "2014-03-25";
		int count;
		while(!start_date.equals("2014-04-01"))
		{
			hot ww = new hot();
			String url = new String("http://weibo.cn/search/mblog?hideSearchFrame=&"
					+ "keyword="+URLEncoder.encode(name, "utf-8")
					+ "&advancedfilter=1&starttime="+start_date+"&endtime="+start_date
					+ "&sort=time&page=1");
			HtmlPage htmlpage=webClient.getPage(url);
			HtmlSpan referSpan = htmlpage.getFirstByXPath("//div[@class='c']/span[@class='cmt']");
			if (referSpan == null)
			{
				System.out.println(start_date+" 返回数为0.结果有误");
				ww.hot = 0;
			}
				else	
			{
				String text = referSpan.asText();
				text = text.substring(text.indexOf("共")+1, text.indexOf("条"));
				count = Integer.valueOf(text).intValue();
				System.out.println(start_date+" 返回结果共有"+count+"条记录.");
				ww.hot = count;
			}
			ww.date = start_date;
			start_date = Tool.add(start_date,1);
			input_hot.input(ww);
			webClient.closeAllWindows();
		}
		
	}
	
	
	
	public static void Login(String username, String password){
		String loginURL = "http://login.weibo.cn/login/";
		try {
			HtmlPage loginPage = webClient.getPage(loginURL);
			mysleep(3000);
			HtmlInput usernameInput = loginPage
					.getFirstByXPath("/html/body/div[2]/form/div/input[1]");
			HtmlInput passwordInput = loginPage
					.getFirstByXPath("/html/body/div[2]/form/div/input[2]");
			HtmlInput loginButton = loginPage
					.getFirstByXPath("/html/body/div[2]/form/div/input[8]");
			usernameInput.setValueAttribute(username);
			passwordInput.setValueAttribute(password);
			loginPage = loginButton.click();
			System.out.println("完成登录");
			
		} catch (FailingHttpStatusCodeException e) {
			
		} catch (MalformedURLException e) {
			
		} catch (IOException e) {
			
		}
	}
	
	private static void mysleep(long n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			
		}
	}
	
}
