# template-merge
一个模板合并工具，目前是java版本的，可以打包成jar使用

当你在做前端开发的时候，如有用过前端模板引擎，肯定会有遇到如下几点困惑：
*  页面模板很多时，模板写到页面里感觉会特别的乱，而且会给页面大小带来额外的开销   
*  单独的把页面模板放到一个目录时，用起来比较麻烦，需要$.get去取，来回请求开销也很大
*  或者我手工的把模板都写在一个模块里，但修改起来又增加了寻找的麻烦

以上这些你都不用担心了，template-merge工具可以帮你搞定。

## template-merge主要功能
打包前的目录templates下，有如下两个html文件：

header.html：

````
    <header>
        <h1 class="logo">${title}</h1>
        <nav>
            <ul>
                {{each data}}
                <li><a href="${url}">${name}</a></li>
                {{/each}}
            </ul>
        </nav>
    </header>
````

footer.html：

````
    <footer>
        <p>&copy; xxxxxxxxxx ${companyName} ${tel}</p>
    </footer>
````

打包后的效果：

````
    define({
        "footer":'<footer>    <p>&copy; xxxxxxxxxx ${companyName} ${tel}</p></footer>',
        "header":'<header>    <h1 class="logo">${title}</h1>    <nav>        <ul>            {{each data}}            <li><a href="${url}">${name}</a></li>            {{/each}}        </ul>    </nav></header>'
    });
````

## template-merge使用
命令行里输入：
````java -cp util.jar util.TranferUtil F:\templates F:\tpls.js Const 1````

`F:\templates` 模板文件目录(默认会合并.html后缀的文件)   
`F:\tpls.js` 合并的js文件     
`Const` 全局变量名     
`1` 是否清空原生成的文件，`1`为清空，`0`为不清空   


## 问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* https://github.com/weger/template-merge/issues
* weibo: [@枝江人](http://weibo.com/xctk)
* twitter: [@lovexctk](http://twitter.com/lovexctk)
