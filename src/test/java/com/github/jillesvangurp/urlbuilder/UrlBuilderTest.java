package com.github.jillesvangurp.urlbuilder;

import static com.github.jillesvangurp.urlbuilder.UrlBuilder.url;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.Test;

@Test
public class UrlBuilderTest {
    public void shouldAppendPostfixes() {
        assertThat(url("http://localhost:1234").append("1", "2","3").build(), is("http://localhost:1234/1/2/3"));
        assertThat(url("http://localhost:1234/").append("1", "2","3").build(), is("http://localhost:1234/1/2/3"));
    }

    public void shouldEscape() {
        assertThat(url("http://localhost").append("<+>").build(), is("http://localhost/%3C%2B%3E"));
    }

    public void shouldAppendMultipleParams() {
        assertThat(
                url("localhost", 80).queryParam("yes", true).queryParam("number", 42).queryParam("str", "1").queryParam("str", "2")
                        .queryParam("dontescape", ":-)", false).build(), is("http://localhost:80?yes=true&number=42&str=1&str=2&dontescape=:-)"));
    }
}
