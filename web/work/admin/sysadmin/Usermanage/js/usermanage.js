
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
        var html = _template('/work/admin/sysadmin/Usermanage/html/usermanage.html', data);
        document.getElementById(divId).innerHTML = html;
        $(".tree").html(temphtml);
    }

    //创建树形图
    function createTreeList(data) {
        var html = '<ul>';
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].branch.length > 0) {
                    html += '<li class="parent_li"> <span><i class="glyphicon glyphicon-minus-sign"></i> ' + data[i].name + '</span>'
                    html += createTreeList(data[i].branch);
                    html += '</li>';
                }
                else {
                    if (data[i].class == 'staff') {
                        html += '<li> <span name="leaf"><i class="glyphicon glyphicon-leaf"></i> ' + data[i].name + '</span></li>';
                    }
                    else {
                        html += '<li> <span><i class="glyphicon glyphicon-leaf"></i> ' + data[i].name + '</span></li>';
                    }
                }
            }
        }
        html += '</ul>';
        return html;
    }

    function bindevent(){
        //编辑用户信息
        $("body").on('click',"[name='btnedit']",function(){
            $("[name='userinformtable'] td.col-lg-5").attr("contenteditable","true");
        });

        //保存用户信息
        $("body").on('click',"[name='btnsave']",function(){
            $("[name='userinformtable'] td.col-lg-5").attr("contenteditable","false");
        });

        //树形节点展开
        $('.tree li.parent_li > span').on('click', function (e) {

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
            document.getElementById('userinfo').innerHTML = html;
            $('#userManageTab a[href="#userinfo"]').tab('show');
        });

        $('#cu-form').submit(function(e){
            alert('提交创建用户表单');
        });
    }

});