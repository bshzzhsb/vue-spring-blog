package self.hsb.blog.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import self.hsb.blog.vo.GitHubTrending;

import java.io.IOException;
import java.util.*;

/**
 * @author SipooHe
 * @date 2020/4/14 20:44
 */
public class GetGitHubTrending {

    public static List<GitHubTrending> getTrending() throws IOException {
        int rank = 1;
        List<GitHubTrending> list = new LinkedList<>();
        String github = "https://github.com/trending";
        Document document = Jsoup.connect(github).get();
        Elements articles = document.select("body > div.application-main > main > div.explore-pjax-container > div.Box > div > article");
        for (Element article : articles) {
            if (rank > 10)
                break;
            String developerAndTrendingName = article.select("h1 > a").text();
            String[] splits = developerAndTrendingName.split("/");
            String trendingName = splits[1].trim();
            String developer = splits[0].trim();
            String description = article.select("p.text-gray").text();
            String url = "https://github.com/"+developer+"/"+trendingName;
            String language = article.select("div.text-gray > span > span:last-child").text();
            list.add(new GitHubTrending(rank,trendingName, developer, description, language, url));
            rank++;
        }
        return list;
    }
}
