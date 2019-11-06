package weibo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.zan;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import input.create_zan;
import input.input_zan;
import input.read_weibo;
import input.rename;

public class zan_crawler {

	private static final WebClient webClient = new WebClient(
			BrowserVersion.INTERNET_EXPLORER_6);
	
	public static void main(String[] args) throws SQLException, FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		Login("*******", "****");
		create_zan.createtable("zan");
		String name = "中粮地产";
		ResultSet rs = read_weibo.get(name);
		String url = null;
		int i=0;
		if(i>1)
		rs.absolute(i-1);
		 while(rs.next())
		 {
			 for(int j=1;j<=((rs.getInt("zan")-1)/10+1);j++)
			 {
				 url = "http://weibo.cn/attitude/"+rs.getString("wid")+"?page="+j;
				 HtmlPage htmlpage=webClient.getPage(url);
				 System.out.println("current weibo is "+rs.getString("wid"));
				 if(htmlpage.asText().contains("SMLFY"))   
					 continue;
				 if(htmlpage.asText().contains("如果没有自动跳转"))   
					 continue;
				 ArrayList<zan> zan_buf = new ArrayList<zan>();
				 List<HtmlDivision> zans = (List<HtmlDivision>) htmlpage.getByXPath("//div[@class='c']");
				 //System.out.println("length of zan is "+ zans.size());
				 /*for(int x=0;x<zans.size();x++)
					 System.out.println(zans.get(x).asText());*/
				 for(int k=3;k<zans.size()-1;++k){
					 zan zz = new zan();
					 zz.wid = rs.getString("wid");
					 zz.uid = rs.getString("uid");
					 zz.zan_i = k-2+(j-1)*10;
					 zz.zan = rs.getInt("zan");
					 String temp = zans.get(k).asXml();
					 
					 temp = temp.substring(temp.indexOf("/u/")+3,temp.indexOf("/u/")+33);
					 temp = temp.substring(0,temp.indexOf(">")-1);
					 zz.zan_uid = temp;
					 //System.out.println(temp);
					 //System.out.println("wid="+zz.wid+", uid="+zz.uid+", zan_i="+zz.zan_i+", zan="+zz.zan+", zan_uid="+zz.zan_uid);
					 zan_buf.add(zz);
				 }
				 	input_zan.add(zan_buf);
				 	System.out.println("Successful.");
				
			 }
			 i++;
			 System.out.println("processing to " + i);
			 //System.out.println(rs.getString("wid"));
			 webClient.closeAllWindows();
		 }
		 
		 input.rename.re_name("zan","zan_"+name);
		 
		 
		 
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

