package com.liuli.boot.es.java.utils;

import com.liuli.boot.es.java.model.es.BaseEsModel;
import com.liuli.boot.es.java.model.es.ProductEsModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HtmlParserUtils {

    public static void main(String[] args) {
        HtmlParserUtils.parseDangdang("java", 500000, 1);
    }

    /**
     * http://search.dangdang.com/?key=java&act=input&page_index=2
     *
     * @param keyword
     * @return
     */
    @SneakyThrows
    public static List<BaseEsModel> parseDangdang(String keyword, int timeoutMillis, int pageIndex) {
        List<BaseEsModel> list = new ArrayList<>();

        String searchUrl = "http://search.dangdang.com/?key=" + keyword + "&act=input&page_index=" + pageIndex;
        //获取网页文档
        Document document = Jsoup.parse(new URL(searchUrl), timeoutMillis);
        //jd商品列表div#J_goodsList
        Elements shopList = document.getElementsByClass("con shoplist");

        // 获取商品的所有标签
        Element search_nature_rg = shopList.get(0).getElementById("search_nature_rg");
        Elements books = search_nature_rg.getElementsByTag("li");

        //遍历所有li标签
        for (Element li : books) {
            try {
                String sku = li.attr("id").substring(1);
                Elements img = li.select("a img");
                String imgUrl = img.eq(0).attr("data-original");
                String title = li.select(".name  > a").eq(0).attr("title");
                String detail = li.select(".detail").text();
                Elements prices = li.select(".price > span");
                //现价
                Double nowPrice = Double.parseDouble(prices.get(0).text().substring(1));
                //定价
                Double prePrice = Double.parseDouble(prices.get(1).text().substring(1));
                //折扣
                Double discount = Double.parseDouble(prices.get(2).text()
                        .replace("(", "")
                        .replace("折)", ""));
                //评论数
                Long commentNum = Long.parseLong(li.select(".search_star_line > a").get(0).text().replace("条评论", ""));
                // 作者+出版信息
                Elements authorPublishInfoEl = li.select(".search_book_author > span");
                String author = authorPublishInfoEl.get(0).select("a").attr("title");
                String publishDate = authorPublishInfoEl.get(1).text().substring(1);
                String press = authorPublishInfoEl.get(2).select("a").attr("title");

                ProductEsModel productEsModel = ProductEsModel.builder()
                        .author(author)
                        .comment(commentNum)
                        .detail(detail)
                        .discount(discount)
                        .nowPrice(nowPrice)
                        .prePrice(prePrice)
                        .press(press)
                        .publishDate(publishDate)
                        .sku(sku)
                        .name(title)
                        .priceUint("¥")
                        .img(imgUrl)
                        .build();
                productEsModel.setId(productEsModel.getSku());
                list.add(productEsModel);
            } catch (Exception ex) {
                log.error("{}", ex);
            }
        }
        return list;
    }
}
