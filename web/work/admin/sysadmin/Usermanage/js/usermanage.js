/**
 * Created by Administrator on 2016/12/12 0012.
 */


$(function(){

    //编辑用户信息
    $("body").on('click',"[name='btnedit']",function(){
        $("[name='userinformtable'] td.col-lg-5").attr("contenteditable","true");
    });

    //保存用户信息
    $("body").on('click',"[name='btnsave']",function(){
        $("[name='userinformtable'] td.col-lg-5").attr("contenteditable","false");
    });

    //树形节点展开
    $('body').on('click','.tree li.parent_li > span', function (e) {

        var children = $(this).parent('li.parent_li').find(' > ul > li');

        if (children.is(":visible")) {

            children.hide('fast');

            $(this).attr('title', 'Expand this branch').find(' > i').addClass('glyphicon-plus-sign').removeClass('glyphicon-minus-sign');

        } else {

            children.show('fast');

            $(this).attr('title', 'Collapse this branch').find(' > i').addClass('glyphicon-minus-sign').removeClass('glyphicon-plus-sign');

        }

        e.stopPropagation();

    });


    //显示用户信息
    $('body').on('click',"[name='leaf']", function (e) {
        //点击叶子节点 从服务器端获取用户信息并显示
        var data={
            username:$(this).html(),
            authority:'普通用户',
            email:'594274532@qq.com',
            name:'周某某',
            age:'20',
            department:'开发部',
            phonenumber:'139xxxx0500',
            address:'北京市海淀区学院路30号',
            zipcode:'100083',
        };
        var html=_template('/work/admin/sysadmin/Usermanage/html/userinfo.html',data);
        document.getElementById('userinfotemplate').innerHTML = html;

    });

});

//用户信息数据样本
//var data={
//    username:$(this).html(),
//    authority:'普通用户',
//    email:'594274532@qq.com',
//    name:'周某某',
//    age:'20',
//    department:'开发部',
//    phonenumber:'139xxxx0500',
//    address:'北京市海淀区学院路30号',
//    zipcode:'100083',
//};
