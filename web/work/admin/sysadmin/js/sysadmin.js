/**
 * Created by Administrator on 2016/12/12 0012.
 */

function managePage(parm){
    var data={
        branch:[
            {name:"部门一",class:"department",branch:[
                {name:"经理一",class:"staff",branch:[]},
                {name:"员工一",class:"staff",branch:[]},
                {name:"员工二",class:"staff",branch:[]},
            ]},
            {name:"部门二",class:"department",branch:[
                {name:"经理二",class:"staff",branch:[]},
                {name:"子部门一",class:"department",branch:[
                    {name:"员工一",class:"staff",branch:[]},
                    {name:"员工二",class:"staff",branch:[]},
                ]},
            ]},
            {name:"部门三",class:"department",branch:[]},
        ]
    };

    usermanage(data);
}



function usermanage(data){
    var temphtml=createTreeList(data.branch);
    var html=_template('/work/admin/sysadmin/Usermanage/html/usermanage.html',data);
    document.getElementById('content').innerHTML = html;
    $(".tree").html(temphtml);

}

//创建树形图
function createTreeList(data){
    var html='<ul>';
    if(data.length>0){
        for(var i=0;i<data.length;i++){
            if(data[i].branch.length>0){
                html+= '<li class="parent_li"> <span><i class="glyphicon glyphicon-minus-sign"></i> '+data[i].name+'</span>'
                html+=createTreeList(data[i].branch);
                html+='</li>';
            }
            else{
                if(data[i].class=='staff'){
                    html+= '<li> <span name="leaf"><i class="glyphicon glyphicon-leaf"></i> '+data[i].name+'</span></li>';
                }
               else{
                    html+= '<li> <span><i class="glyphicon glyphicon-leaf"></i> '+data[i].name+'</span></li>';
                }
            }
        }
    }
    html+='</ul>';
    return html;
}


//用户管理数据样本
//var data={
//    branch:[
//        {name:"部门一",class:"department",branch:[
//            {name:"经理一",class:"staff",branch:[]},
//            {name:"员工一",class:"staff",branch:[]},
//            {name:"员工二",class:"staff",branch:[]},
//        ]},
//        {name:"部门二",class:"department",branch:[
//            {name:"经理二",class:"staff",branch:[]},
//            {name:"子部门一",class:"department",branch:[
//                {name:"员工一",class:"staff",branch:[]},
//                {name:"员工二",class:"staff",branch:[]},
//            ]},
//        ]},
//        {name:"部门三",class:"department",branch:[]},
//    ]
//};
