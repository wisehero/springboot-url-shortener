package prgms.marco.springbooturlshortener.web.controller;

import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prgms.marco.springbooturlshortener.dto.CreateShortUrlReq;
import prgms.marco.springbooturlshortener.dto.CreateShortUrlRes;
import prgms.marco.springbooturlshortener.service.UrlService;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> convertShortUrlToOrigin(@PathVariable String shortUrl) {
        String originUrl = urlService.findOriginUrlByShortUrl(shortUrl);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(originUrl));
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping
    public ResponseEntity<CreateShortUrlRes> createShortUrl(
        @RequestBody @Validated CreateShortUrlReq createShortUrlReq) {
        String shortUrl = urlService.createShortUrl(createShortUrlReq.getOriginUrl());
        CreateShortUrlRes createShortUrlRes = new CreateShortUrlRes(shortUrl);
        return ResponseEntity
            .created(URI.create("/api/v1/urls/" + shortUrl))
            .body(createShortUrlRes);
    }
}
