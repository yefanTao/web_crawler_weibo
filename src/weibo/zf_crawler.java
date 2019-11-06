package weibo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.zf;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import input.create_zf;
import input.input_zf;
import input.read_weibo;

public class zf_crawler {
	private static final WebClient webClient = new WebClient(
			BrowserVersion.FIREFOX_3);
	
	public static void main(String[] args) throws SQLException, FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		Login("********", "******");
		create_zf.createtable("zf");
		String name= "紫金矿业";
		ResultSet rs = read_weibo.get(name);
		String url = null;
		int i=0;
		if(i>1) 
		rs.absolute(i-1);
		while(rs.next())
		 {
			 for(int j=1;j<=((rs.getInt("zf")-1)/10+1);j++)
			 {  
				 url = "http://weibo.cn/repost/"+rs.getString("wid")+"?page="+j;
				// System.out.println(rs.getString("wid"));
				 HtmlPage htmlpage=webClient.getPage(url); 
				 System. out.println("current weibo is "+rs.getString("wid"));
				 if(htmlpage.asText().contains("DM1523"))   
					 continue;
				 if(htmlpage.asText().contains("如果没有自动跳转"))   
					 continue;
				/* if(htmlpage.asText().contains("还没有人针对这条微博发表评论!"))  
					 continue;*/
				 ArrayList<zf> zf_buf = new ArrayList<zf>();
				 List<HtmlDivision> zfs = (List<HtmlDivision>) htmlpage.getByXPath("//div[@class='c']");
				 for(int k=3;k<zfs.size()-1;++k){ 
					 zf zz = new zf();
					 String temp = zfs.get(k).asXml();
					 String text = zfs.get(k).asText();
					if(text.contains("查看更多热门"))
						 continue;
					 //System.out.println(temp);
					 temp = temp.substring(temp.indexOf("href"),temp.indexOf("href")+50);
					 if(temp.contains("/u/"))
						 zz.zf_uid = temp.substring(temp.indexOf("/u/")+3, temp.indexOf(">")-1);
					 else
						 zz.zf_uid = temp.substring(temp.indexOf("/")+1,temp.indexOf(">")-1);
					 //System.out.println(text);
					 text = text.substring(text.indexOf(":")+1,text.indexOf("赞["));
					 zz.zf_context = text;
					 zz.wid = rs.getString("wid");
					 zz.uid = rs.getString("uid");
					 zz.zf_i = k-2+(j-1)*10;
					 zz.zf = rs.getInt("zf");
					 zf_buf.add(zz);
				 }
				 	input_zf.add(zf_buf);
				 	System.out.println("Successful.");
				
			 }
			 i++;
			 System.out.println("processing to " + i);
			 //System.out.println(rs.getString("wid"));
			 webClient.closeAllWindows();
			 //break;
			/* if(i==2131)
				 break;*/
		 }
		 input.rename.re_name("zf","zf_"+name);
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
			//System.out.println(loginPage.asText());
			
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
