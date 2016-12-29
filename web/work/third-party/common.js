/**
 * Created by Administrator on 2016/12/12 0012.
 */
//从服务器获取模板html并根据data生成
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