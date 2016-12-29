
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

define(function(require,exports,module){

    exports.data=[];
    exports.init=function(divId,data){
        exports.data=data;
        usermanage(divId,exports.data);
        bindevent();
    };


    function usermanage(divId,data) {
        var temphtml = createTreeList(data.branch);
        var html = _template('/work/admin/sysadmin/UserDeptmanage/html/userdeptmanage.html', data);
        document.getElementById(divId).innerHTML = html;
        $(".tree").html(temphtml);
    }

    //创建树形图
    function createTreeList(data) {
        var html = '<ul>';
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].branch.length > 0) {
                    html += '<li class="parent_li">  <span ><span  class="label label-primary" style="color:white;"><i class="glyphicon glyphicon-home"></i> ' + data[i].name + '</span><i style="display: none;"><span class="glyphicon glyphicon-pencil" style="margin-left: 3px;"></span><span class="glyphicon glyphicon-plus"  style="margin-left: 3px;"></span><span class="glyphicon glyphicon-remove" style="margin-left: 3px;"></span></i></span>';
                    html += createTreeList(data[i].branch);
                    html += '</li>';
                }
                else {
                    if (data[i].class == 'staff') {
                        html += '<li> <span><span name="leaf" class="label label-default" style="color:white;"> <i class="glyphicon glyphicon-user"></i> ' + data[i].name + '</span><i style="display: none;"><span class="glyphicon glyphicon-remove" style="margin-left: 3px;"></span></i></span></li>';
                    }
                    else {
                        html += '<li> <span><span class="label label-primary" style="color:white;"><i class="glyphicon glyphicon-home"></i> ' + data[i].name + '</span><i style="display: none;"><span class="glyphicon glyphicon-pencil"  style="margin-left: 5px;"></span><span class="glyphicon glyphicon-plus" style="margin-left: 5px;"></span><span class="glyphicon glyphicon-remove" style="margin-left: 3px;"></span></i></span></li>';
                    }
                }
            }
        }
        html += '</ul>';
        return html;
    };

    function bindevent(){
        //编辑用户信息
        $("body").on('click',"[name='userinfoEdit']",function(){
            $("[name='userinformtable'] td.col-lg-5").attr("contenteditable","true");
        });

        //保存用户信息
        $("body").on('click',"[name='userinfoSave']",function(){
            $("[name='userinformtable'] td.col-lg-5").attr("contenteditable","false");
        });
        //编辑部门信息
        $("body").on('click',"[name='deptinfoEdit']",function(){
            $("[name='deptinfotable'] td.col-lg-5").attr("contenteditable","true");
        });

        //保存部门信息
        $("body").on('click',"[name='deptinfoSave']",function(){
            $("[name='deptinfotable'] td.col-lg-5").attr("contenteditable","false");
        });



        //树形节点展开i
        $('.tree li.parent_li >span> span.label ').on('click', function (e) {

            var children = $(this).parent().parent('li.parent_li').find(' > ul > li');

            if (children.is(":visible")) {

                children.hide('fast');

                $(this).attr('title', 'Expand this branch').find(' > i').addClass('glyphicon-plus-sign').removeClass('glyphicon-home');

            } else {

                children.show('fast');

                $(this).attr('title', 'Collapse this branch').find(' > i').addClass('glyphicon-home').removeClass('glyphicon-plus-sign');

            }

            e.stopPropagation();

        });


        //显示用户信息
        $('[name="leaf"]').on('click', function (e) {
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
            var html=_template('/work/admin/sysadmin/UserDeptmanage/html/userinfo.html',data);
            document.getElementById('userinfo').innerHTML = html;
            $('#userManageTab a[href="#userinfo"]').tab('show');
        });

        $('.tree li >span>i> span.glyphicon-pencil').on('click', function (e) {
            var data={
                deptname:'部门X',
                charge:'经理一',
                deptpopularity:'3',
                priordept:'无',
            };
            var html=_template('/work/admin/sysadmin/UserDeptmanage/html/deptinfo.html',data);
            document.getElementById('deptinfo').innerHTML = html;
            $('#userManageTab a[href="#deptinfo"]').tab('show');
        });


        $('#cu-form').submit(function(e){
            alert('提交创建用户表单');
        });

        //滑动显示
        var arrM = $(".tree li>span");
        for(var i = 0;i<arrM.length;i++){
            //alert(i)
            $(arrM[i]).hover(
                function(){
                    $(this).children('i').fadeIn(100);},
                function(){
                    $(this).children('i').fadeOut(100);;
                }
            );
        };

    }

});