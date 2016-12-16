/**
 * Created by Administrator on 2016/12/12 0012.
 */
define(function(require,exports,module){
    var userManage=require('/work/admin/sysadmin/Usermanage/js/usermanage.js');
    var dataManage=require('/work/admin/sysadmin/Datamanage/js/datamanage.js');
    exports.data=[];
    exports.init=function(data,parm){
        exports.data=data;
        managePage(parm);
        bindevent();

    };



    function managePage(parm) {
        switch (parm) {
            case 'Usermanage':
                userManage.init('content',exports.data);
                break;
            case 'Deptmanage':
                break;
            case 'Dbmanage':
                dataManage.init('content',exports.data);
                break;
            case 'Formmanage':
                break;
        }
    }
   function bindevent(){
    $('[a-type="nav"]').click(function(){
       managePage($(this).attr('name'));
    });
   }

});








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