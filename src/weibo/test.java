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

import util.Tool;
import model.stock;
import model.weibo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class test {
	
	private static final WebClient webClient = new WebClient(
			BrowserVersion.INTERNET_EXPLORER_6);
	public static String last_date = "20140331";
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException  {
		// TODO Auto-generated method stub
		Login("************","**********");
		//create_table.createtable("weibo");
		getAllWeibo("TCL集团");
		rename.re_name("TCL集团");
		/*Vector<stock> stocks = input_stock.getAll();
		for(stock stock : stocks){
			//一个循环对于一只股票
			System.out.println("现在开始爬取关于 '"+stock.name+"'的微博");
			//获取一直股票的所有微博。
			create_table.createtable("weibo");
			getAllWeibo(stock.name);
			rename.re_name(stock.name);
		}*/
		
	}
	
	public static void getAllWeibo(String name) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		int count;
		int page = 1;
		boolean flag;
		String start_date = "20131001";
		String end_date = "2014-03-31";
		String cur_date = end_date;
		
		String url = null;
			url = new String("http://weibo.cn/search/mblog?hideSearchFrame=&"
				+ "keyword="+URLEncoder.encode(name, "utf-8")
				+ "&advancedfilter=1&starttime="+start_date+"&endtime="+end_date
				+ "&sort=time&page="+page);
		HtmlPage htmlpage=webClient.getPage(url);
		//搜索一共有多少条记录
		HtmlSpan referSpan = htmlpage.getFirstByXPath("//div[@class='c']/span[@class='cmt']");
		if (referSpan == null)
		{
			System.out.println("返回数为0.结果有误");
		}
			else	
		{
			String text = referSpan.asText();
			text = text.substring(text.indexOf("共")+1, text.indexOf("条"));
			count = Integer.valueOf(text).intValue();
			System.out.println("返回结果共有"+count+"条记录，正在分页搜索。。。");
		}
		flag = getWeiboPage(name,start_date,end_date,page);
		//自动改变搜索范围
		while(!last_date.substring(0,8).equals(start_date))
		{
			if(flag)
			{
				page++;
				flag = getWeiboPage(name,start_date,cur_date,page);
			}
			else
			{
				page = 1;
				//防止单日内记录太多死循环
				if(Integer.valueOf(last_date.substring(11,13)).intValue()<8)
					cur_date = Tool.add(last_date.substring(0,10), -1) + Integer.toString(16-Integer.valueOf(last_date.substring(11,13)).intValue());
				else
					cur_date = Tool.add(last_date.substring(0,10), -1) + Integer.toString(Integer.valueOf(last_date.substring(11,13)).intValue()-8);
				
				flag = getWeiboPage(name,start_date,cur_date,page);
				
			}
		
		}
			
		
		
	}

	//返回值，为真时表示可以继续翻页运行，为假时说明不能继续运行
	private static boolean getWeiboPage(String name, String start_date, String end_date, int page) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		boolean flagx;
		String url = null;
			url = new String("http://weibo.cn/search/mblog?hideSearchFrame=&"
					+ "keyword="+URLEncoder.encode(name, "utf-8")
					+ "&advancedfilter=1&starttime="+start_date+"&endtime="+end_date
					+ "&sort=time&+page="+page);
			HtmlPage htmlpage=webClient.getPage(url);
			if(htmlpage.asText().contains("下页"))
				flagx = true;
			else
				flagx = false;
			//System.out.println("flag");
			ArrayList<weibo> weibo_buf = new ArrayList<weibo>();
			//mysleep(4000);
			List<HtmlDivision> weibos = (List<HtmlDivision>) htmlpage.getByXPath("//div[@class='c']");
			for(int j=3;j<weibos.size()-2;++j){
				weibo ww = new weibo();
				String temp_xml = null;
				String temp_text = null;
				String temp_seg = null;
				temp_xml = (String)weibos.get(j).asXml();
				temp_text = (String)weibos.get(j).asText();
				//System.out.println(temp_text);
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
				
				HtmlSpan dateSpan = weibos.get(j).getFirstByXPath("//span[@class='ct']");
				temp_seg = dateSpan.asText();
				
				temp_seg = temp_text.substring(temp_text.lastIndexOf("收藏")+3,temp_text.lastIndexOf("来自")-1);
				if(temp_seg.length()>18)
					ww.date = temp_seg.substring(0, 19);
				else
					ww.date = "2014-"+temp_seg.substring(0, 2)+"-"+temp_seg.substring(3, 5)+" "+temp_seg.substring(7, 12);
				
				last_date = ww.date.substring(0,10)+"-"+ww.date.substring(11,13);
				
				if(temp_text.contains("转发了")){
					//转发微博
					ww.isoriginal = false;
					ww.content = temp_text.substring(temp_text.indexOf("转发理由:"), temp_text.lastIndexOf("赞[")-1);
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
				
				/**System.out.println("成功抓取一条微博，信息如下：");
				System.out.println("wid="+ww.wid+"  uid="+ww.uid+"  content="+ww.content+"  zan="+ww.zan
						+"  zf="+ww.zf+"  pl="+ww.pl+"  date="+ww.date+"  isoriginal="+ww.isoriginal+
						"  pre_uid="+ww.pre_uid+"  pre_wid="+ww.pre_wid);
				*/
				weibo_buf.add(ww);
			}
			input_weibo.add(weibo_buf);
			weibo_buf.clear();
			System.out.println("导入第"+page+"页");
			System.out.println(last_date);
			webClient.closeAllWindows();
			return flagx;
			
		
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
