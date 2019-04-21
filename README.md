什么是数据绑定？
=
将HTTP请求中的参数绑定到Handler业务方法的形参。 springMVC框架会自动帮我们把前台页面表单中传递进来的各个参数所包含的数据封装为后端中的一个对象（形式参数）。也就是说，springMVC框架自动将前段传递进来的分散的参数封装为一个POJO。后端则直接可以对该POJO对象进行其他的操作，不用在后端自动来完成转换成对象的过程。<br>
image_file[fjdj](http://)

基本数据类型的数据绑定
-
在controller中可以这样定义：<br>
```java
    @RequestMapping(value = "/baseType")
    @ResponseBody
    public String baseType(@RequestParam(value = "id",required = false)int id){
        return "id" + id;
    }
```
包装数据类型：<br>
```java
    @RequestMapping(value = "/packageType")
    @ResponseBody
    public String packageType(@RequestParam(value = "id",required = false) Integer id){
        return "id" + id;
    }
```



