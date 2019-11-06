package weibo;

import input.create_table;
import input.input_stock;
import input.input_weibo;
import input.rename;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import model.stock;
import model.weibo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class testtest {
	
	private static final WebClient webClient = new WebClient(
			BrowserVersion.FIREFOX_3);
	public static String last_date = null;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException  {
		// TODO Auto-generated method stub
		//webClient.getCookieManager().setCookiesEnabled(true);//enable cookies
		//Cookie cookie = new Cookie(".login.sina.com.cn","ALC","ac%3D27%26bt%3D1423203816%26cv%3D5.0%26et%3D1454739816%26uid%3D1944907447%26vf%3D0%26vt%3D0%26es%3D46344cb88962081c4275bd4b3efb55c5");;
		//webClient.getCookieManager().addCookie(cookie);
		Login("*****", "****");
		String name = "中国石油";
		//create_table.createtable("weibo");
		int page;
		//String url = "http://weibo.cn/search/mblog?hideSearchFrame=&keyword=%E6%B0%91%E7%94%9F%E9%93%B6%E8%A1%8C&advancedfilter=1&starttime=20140327&endtime=20140327-19&sort=time&page=100";
		//HtmlPage htmlpage = webClient.getPage(url);
		//System.out.println(htmlpage.asText()); 
		for(page=1;page<=1;page++)
		{
			String start_date = "20131001";
			String end_date = "20140331";
			String url = null;
				url = new String("http://weibo.cn/search/mblog?hideSearchFrame=&" 
					+ "keyword="+URLEncoder.encode(name, "utf-8")
					+ "&advancedfilter=1&starttime="+start_date+"&endtime="+end_date 
					+ "&sort=time&page="+page);
			HtmlPage htmlpage=webClient.getPage(url);
			
			ArrayList<weibo> weibo_buf = new ArrayList<weibo>();
			//mysleep(1000);
			
			List<HtmlDivision> weibos = (List<HtmlDivision>) htmlpage.getByXPath("//div[@class='c']");
			for(int j=3;j<weibos.size()-2;++j){
			
				weibo ww = new weibo();  
				
				String temp_xml = null;
				String temp_text = null;
				String temp_seg = null;
				temp_xml = (String)weibos.get(j).asXml();
				temp_text = (String)weibos.get(j).asText();
				System.out.println(temp_text);
				System.out.println(temp_xml);
				//有删除微博时，跳过整页
				
				if(temp_text.contains("删除"))
					continue;
				ww.wid = temp_xml.substring(21,30);
				temp_seg = temp_xml.substring(temp_xml.indexOf("http"),temp_xml.indexOf("http")+50);
				if(temp_seg.contains("/u/"))
					
				{
					if(temp_seg.contains("?vt=4"))
						ww.uid = temp_seg.substring(temp_seg.indexOf("/u/")+3, temp_seg.indexOf("?vt=4"));
					else 
						ww.uid = temp_seg.substring(temp_seg.indexOf("/u/")+3, temp_seg.indexOf(">")-1);
				}
				
				else
				{
					if (temp_seg.contains("?vt=4"))
						ww.uid = temp_seg.substring(temp_seg.indexOf("cn/")+3,temp_seg.indexOf("?vt=4"));
					else
						ww.uid = temp_seg.substring(temp_seg.indexOf("cn/")+3,temp_seg.indexOf(">")-1);
				}
					
					
				
				
				temp_seg = temp_text.substring(temp_text.lastIndexOf("赞[")+2,temp_text.lastIndexOf("转发[")-2);
				ww.zan = Integer.valueOf(temp_seg).intValue();
				
				temp_seg = temp_text.substring(temp_text.lastIndexOf("转发[")+3,temp_text.lastIndexOf("评论[")-2);
				ww.zf = Integer.valueOf(temp_seg).intValue();
				
				temp_seg = temp_text.substring(temp_text.lastIndexOf("评论[")+3,temp_text.lastIndexOf("收藏")-2);
				ww.pl = Integer.valueOf(temp_seg).intValue();
				

				temp_seg = temp_text.substring(temp_text.lastIndexOf("收藏")+3,temp_text.lastIndexOf("来自")-1);
				//System.out.println(temp_text);
				//System.out.println(temp_text);
				if(temp_seg.length()>18)
					ww.date = temp_seg.substring(0, 19);
				else
					ww.date = "2014-"+temp_seg.substring(0, 2)+"-"+temp_seg.substring(3, 5)+" "+temp_seg.substring(7, 12);
				//System.out.println(ww.date);
				last_date = ww.date.substring(0,4)+ww.date.substring(5,7)+ww.date.substring(8,10);
				
				if(temp_text.contains("转发了")&&temp_text.contains("的微博")){
					//转发微博
					ww.isoriginal = false;
					ww.content = temp_text.substring(temp_text.indexOf("转发理由:"), temp_text.lastIndexOf("赞[")-1);
					ww.content = ww.content.replaceAll("'", "");
					temp_seg = temp_xml.substring(temp_xml.indexOf("转发了"),temp_xml.indexOf("转发了")+70);
					if (temp_seg.contains("/u/"))
					{
						if(temp_seg.contains("?vt=4"))
							ww.pre_uid = temp_seg.substring(temp_seg.indexOf("/u/")+3, temp_seg.indexOf("?vt=4"));
						else 
							ww.pre_uid = temp_seg.substring(temp_seg.indexOf("/u/")+3, temp_seg.indexOf(">")-1);
					}
					else
					{
						if (temp_seg.contains("?vt=4"))
							ww.pre_uid = temp_seg.substring(temp_seg.indexOf("cn/")+3,temp_seg.indexOf("?vt=4"));
						else
							ww.pre_uid = temp_seg.substring(temp_seg.indexOf("cn/")+3,temp_seg.indexOf(">")-1);
					}
					
				
					ww.pre_wid = temp_xml.substring(temp_xml.indexOf("/comment/")+9,temp_xml.indexOf("/comment/")+18);
				}
				else
				{
					ww.content = temp_text.substring(temp_text.indexOf(":")+1,temp_text.indexOf("赞[")-1);
					ww.isoriginal = true;
					ww.pre_uid = null;
					ww.pre_wid = null;
				}
				
				/*System.out.println("成功抓取一条微博，信息如下：");
				System.out.println("wid="+ww.wid+"  uid="+ww.uid+"  content="+ww.content+"  zan="+ww.zan
						+"  zf="+ww.zf+"  pl="+ww.pl+"  date="+ww.date+"  isoriginal="+ww.isoriginal+
						"  pre_uid="+ww.pre_uid+"  pre_wid="+ww.pre_wid);
				*/
				//System.out.println("length = " + ww.content.length());
				weibo_buf.add(ww);
			}
			input_weibo.add(weibo_buf);
			System.out.println("已经导入第"+page+"页");
			System.out.println(last_date);
			webClient.closeAllWindows();
		}
		
		//rename.re_name(name);
		
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
