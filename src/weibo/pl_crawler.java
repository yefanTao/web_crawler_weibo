package weibo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.pl;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import input.create_pl;
import input.input_pl;
import input.read_weibo;

public class pl_crawler {
	private static final WebClient webClient = new WebClient(
			BrowserVersion.FIREFOX_3);
	
	public static void main(String[] args) throws SQLException, FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		Login("*******", "*****");
		//create_pl.createtable("pl");
		String name = "长江证券";
		ResultSet rs = read_weibo.get(name);
		String url = null;
		int i=194;
		if(i>1) 
		rs.absolute(i-1);
		while(rs.next())
		 {
			 for(int j=1;j<=((rs.getInt("pl")-1)/10+1);j++)
			 { 
				 url = "http://weibo.cn/comment/"+rs.getString("wid")+"?page="+j;
				 //System.out.println(rs.getString("wid"));
				 HtmlPage htmlpage=webClient.getPage(url); 
				 System. out.println("current weibo is "+rs.getString("wid"));
				 if(htmlpage.asText().contains("DM_2014"))   
					 continue;
				 /*if(htmlpage.asText().contains("如果没有自动跳转"))   
					 continue;*/
				 if(htmlpage.asText().contains("还没有人针对这条微博发表评论!"))  
					 continue;
				 ArrayList<pl> pl_buf = new ArrayList<pl>();
				 List<HtmlDivision> pls = (List<HtmlDivision>) htmlpage.getByXPath("//div[@class='c']");
				 for(int k=3;k<pls.size()-1;++k){ 
					 pl zz = new pl();
					 String temp = pls.get(k).asXml();
					 String text = pls.get(k).asText();
					 if(text.contains("查看更多热门"))
						 continue;
					 //System.out.println(temp);
					 temp = temp.substring(temp.indexOf("href"),temp.indexOf("href")+50);
					 if(temp.contains("/u/"))
						 zz.pl_uid = temp.substring(temp.indexOf("/u/")+3, temp.indexOf(">")-1);
					 else
						 zz.pl_uid = temp.substring(temp.indexOf("/")+1,temp.indexOf(">")-1);
					 //System.out.println(text);
					 text = text.substring(text.indexOf(":")+1,text.indexOf("举报 ")-2);
					 zz.pl_context = text;
					 zz.wid = rs.getString("wid");
					 zz.uid = rs.getString("uid");
					 zz.pl_i = k-2+(j-1)*10;
					 zz.pl = rs.getInt("pl");
					 pl_buf.add(zz);
				 }
				 	input_pl.add(pl_buf);
				 	System.out.println("Successful.");
				
			 }
			 i++;
			 System.out.println("processing to " + i);
			 //System.out.println(rs.getString("wid"));
			 webClient.closeAllWindows();
			 //break;
		 }
		 input.rename.re_name("pl","pl_"+name);
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
