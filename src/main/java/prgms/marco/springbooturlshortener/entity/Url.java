package prgms.marco.springbooturlshortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "url")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "short_url", unique = true)
    private String shortUrl;

    @Column(name = "origin_url", unique = true, nullable = false)
    private String originUrl;

    @Column(name = "req_count")
    private Long reqCount;

    @Transient
    private final UrlEncoder encoder = new UrlEncoder();

    protected Url() {
    }

    public static Url createUrl(String originUrl) {
        Url url = new Url();
        url.originUrl = originUrl;
        url.reqCount = 0L;
        return url;
    }


    public void convertIdToShortUrl(Long id) {
        this.shortUrl = encoder.encoding(id);
    }

    public void addRequestCount() {
        this.reqCount++;
    }

    public Long getId() {
        return id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public Long getReqCount() {
        return reqCount;
    }
}
