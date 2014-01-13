import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.FrameWindow;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class Test {

    private WebClient webClient;
    private String host = "http://profitcentr.com";

    public static void main(String[] a) throws FailingHttpStatusCodeException, IOException, InterruptedException {
        Test test = new Test();
        test.setCookie("menu_ref", "cf7e82e0135072da4eec658e5eebb6a5");
        test.setCookie("PHPSESSID", "13g1ikrj18rn4ujapg32q3q4n0");
        HtmlPage page = test.post();
        // test.webClient.getPage("");
        // System.out.println(page.getWebResponse().getContentAsString());
        //

        test.surf();
    }

    private void surf() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
        // "Се­рфинг сайтов"
        HtmlPage page = webClient.getPage(host);
        HtmlElement link = page.getFirstByXPath("//a[text()='Се­рфинг сайтов']");

        page = click(link);
        List<HtmlElement> links = (List<HtmlElement>) page.getByXPath("//a[contains(@href, 'viev_serf.php?ad=')]");
        for (HtmlElement surfLink : links) {
            HtmlPage npage = click(surfLink);

            FrameWindow frame = npage.getFrameByName("frminfo");
            for (int i = 0; i < 10; i++) {

                // HtmlPage framePage = frame.g;
                HtmlPage frmPage = (HtmlPage) frame.getEnclosedPage();
                System.out.println(frmPage.getWebResponse().getContentAsString());

                HtmlImage image = frmPage.<HtmlImage> getFirstByXPath(" //img[contains(@src, 'vcapt.php')]");
                if (image != null) {

                    // image =
                    // page.<HtmlImage>getFirstByXPath("//img[@src='blah']");
                    // File imageFile = new File("d:\\img.png");
                    // System.err.println("Saving image...");
                    // image.
                    // image.saveAs(imageFile);
                    HtmlElement okClick = frmPage.getFirstByXPath("//a[contains(@href, 'vlss.php?view=ok')]");
                    if (okClick != null) {
                        click(okClick);
                        Thread.sleep(1000);
                    }
                    // System.out.println(okPage.getWebResponse().getContentAsString());
                    break;

                }
                Thread.sleep(5000);
                // page1.g
            }
        }
    }

    private HtmlPage click(HtmlElement element) throws IOException {
        element.mouseMove();
        element.mouseDown();
        HtmlPage page = element.click();
        // System.out.println(page.getWebResponse().getContentAsString());
        // element.mouseUp();
        // Page page = element.mouseOut();
        // System.out.println(page.getWebResponse().getContentAsString());
        return page;
    }

    public Test() {
        webClient = new WebClient();
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
    }

    private HtmlPage post() throws FailingHttpStatusCodeException, IOException {
        WebRequest requst = new WebRequest(new URL(host + "/members.php"), HttpMethod.POST);

        requst.setRequestParameters(new ArrayList<NameValuePair>());
        requst.getRequestParameters().add(new NameValuePair("e_bonus", "e_bonus"));
        return webClient.getPage(requst);
    }

    private void setCookie(String name, String value) {
        // Cookie cookie = new Cookie(name, value);
        this.webClient.getCookieManager().addCookie(new Cookie("profitcentr.com", name, value));
    }

}
