/**
 * Created by Administrator on 2016/12/12 0012.
 */

function managePage(parm){
    var data={
        username:'zhouzhiyuan999',
        authority:'普通用户',
        email:'594274532@qq.com',
        name:'周某某',
        age:'20',
        department:'开发部',
        phonenumber:'139xxxx0500',
        address:'北京市海淀区学院路30号',
        zipcode:'100083',
        tree:{

        }
    };

    var html=_template('/work/admin/sysadmin/Usermanage/html/usermanage.html',data);
    document.getElementById('content').innerHTML = html;
}

function _template(url,data){
    var source;
    $.ajaxSetup({
        async : false
    });
    $.get(url,function(result){
        source=result;
    });
    var render = template.compile(source);
    var html = render(data);
    $.ajaxSetup({
        async : true
    });
    return html;
}
