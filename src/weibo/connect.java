package weibo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.DomNode;
public class connect {
	
	private static final WebClient webClient = new WebClient(
			BrowserVersion.INTERNET_EXPLORER_6);
	
	public static void main(String[] args){
		
		Login("********","*****");
	try {
		HtmlPage htmlpage=webClient.getPage("http://weibo.cn/search/mblog"
				+ "?hideSearchFrame=&keyword="
				+  URLEncoder.encode("希尔伯特", "utf-8")
				+ "&advancedfilter=1&starttime=20131001&endtime=20140331"
				+ "&sort=time&page=5&vt=4");
		List<HtmlDivision> test = (List<HtmlDivision>) htmlpage.getByXPath("//div[@class='c']");//  //div[@class='c']/span[@class='cmt']
		String text = null;
		//text = referSpan.asText();
		//text = text.substring(text.indexOf("共")+1, text.indexOf("条"));
		//int i = Integer.valueOf(text).intValue();
		text = test.get(3).asText();
		int count;
		if(text == null)
			System.out.println("error.");
		else
		{
			System.out.println(text);
		}
		
		/*if (referSpan == null)
			System.out.println("返回数为0.");
		else	
		{
			text = referSpan.asText();
			text = text.substring(text.indexOf("共")+1, text.indexOf("条"));
			int i = Integer.valueOf(text).intValue();
			System.out.println("返回数为"+i);
		}*/
			

		
		
		
		
	} catch (FailingHttpStatusCodeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	public static void Login(String username, String password){
		String loginURL = "http://login.weibo.cn/login/";
		try {
			HtmlPage loginPage = webClient.getPage(loginURL);
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
	
}
