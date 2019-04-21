<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">

    $(function(){
        var course = {
            "id":"8",
            "name":"SSM框架整合",
            "price":"200"
        };
        $.ajax({
            url:"jsonType",
            data:JSON.stringify(course), //这里的data表示想要发送的数据
            type:"post",
            contentType:"application/json;charse=UTF-8",
            dataType:"json", //dataType指定后台返回给前台的数据的数据类型
            success:function(data){//这个data是后它返回的数据
                alert(data.name+"---"+data.price);
            }
        })
    })

</script>
<body>

</body>
</html>
