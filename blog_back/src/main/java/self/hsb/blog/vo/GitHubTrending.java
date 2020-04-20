package self.hsb.blog.vo;

/**
 * @author SipooHe
 * @date 2020/4/14 20:39
 */
public class GitHubTrending {

    Integer rank;
    String trendingName;
    String developer;
    String description;
    String language;
    String url;

    public GitHubTrending(Integer rank, String trendingName, String developer, String description, String language, String url) {
        this.rank = rank;
        this.trendingName = trendingName;
        this.developer = developer;
        this.description = description;
        this.language = language;
        this.url = url;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTrendingName() {
        return trendingName;
    }

    public void setTrendingName(String trendingName) {
        this.trendingName = trendingName;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "GitHubTrending{" +
                "rank=" + rank +
                ", trendingName='" + trendingName + '\'' +
                ", developer='" + developer + '\'' +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
