# Introduction

UrlBuilder is a simple Java builder class to construct urls. This class is useful when you have a base uri and need to create urls with things appended to this url or query parameters added to the url. It's a simple problem but one of those things where you end up with a lot of boiler plate code. After migrating this class between projects, I decided to put it up on Github for reuse.

# Examples

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
    

# Installation

It's a maven project. So, checking it out and doing a mvn clean install should do the trick.

Alternatively, you can exercise your rights under the license and simply copy and adapt. The [license](https://github.com/jillesvangurp/urlbuilder/blob/master/LICENSE) allows you to do this and I have no problems with this.

If you like to use pre-built jars, you can utilize my private maven repository as explained [here](http://www.jillesvangurp.com/2013/02/27/maven-and-my-github-projects/)

Should anyone like this licensed differently, please contact me.

If anyone wants to fix stuff just send me a pull request.

# Changelog
* 1.0
    * first release
