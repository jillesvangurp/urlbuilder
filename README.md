# Introduction

UrlBuilder is a simple Java builder class to construct urls. This class is useful when you have a base uri and need to create urls with things appended to this url or query parameters added to the url. It's a simple problem but one of those things where you end up with a lot of boiler plate code. After migrating this class between projects, I decided to put it up on Github for reuse.

# Examples

Use a static import: `import static com.github.jillesvangurp.urlbuilder.UrlBuilder.url;`

    assertThat(
        url("http://localhost:1234").append("1", "2","3").build(),
        is("http://localhost:1234/1/2/3"));
    assertThat(
        url("http://localhost:1234/").append("1", "2","3").build(), 
        is("http://localhost:1234/1/2/3"));
    assertThat(
        url("http://localhost").append("<+>").build(), 
        is("http://localhost/%3C%2B%3E"));
    assertThat(
            url("localhost", 80).queryParam("yes", true).queryParam("number", 42).queryParam("str", "1").queryParam("str", "2").queryParam("dontescape", ":-)", false).build(), 
            is("http://localhost:80?yes=true&number=42&str=1&str=2&dontescape=:-)"));
    

# Get it from Maven Central

```
<dependency>
    <groupId>com.jillesvangurp</groupId>
    <artifactId>urlbuilder</artifactId>
    <version>1.1</version>
</dependency>
```

Note. check for the latest version. I do not always update the readme.

# Building

It's a maven project. So, checking it out and doing a mvn clean install should do the trick.

Should anyone like this licensed differently, please contact me.

If anyone wants to fix stuff just send me a pull request.

Alternatively, you can exercise your rights under the license and simply copy and adapt. The [license](https://github.com/jillesvangurp/urlbuilder/blob/master/LICENSE) allows you to do this and I have no problems with this.

# Changelog
* 1.1
    * deployed to maven central 
* 1.0
    * first release
