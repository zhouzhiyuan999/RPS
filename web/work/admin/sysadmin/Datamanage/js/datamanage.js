/**
 * Created by gasingreen on 16/12/2016.
 */
//数据库数据样本
//var data={
//    dbid:$(this).html(),
//    dbname:'普通用户',
//    dbtime:'594274532@qq.com',
//    dbsize:'周某某',
//};

define(function (require, exports, module) {

    exports.data = [];
    exports.init = function (divId, data) {
        exports.data = data;
        dbmanage(divId, exports.data);
        bindevent();
    };
    function dbmanage(divId, data) {
        var html = _template('/work/admin/sysadmin/Datamanage/html/datamanage.html', data);
        document.getElementById(divId).innerHTML = html;
    }

    function bindevent() {
        // 数据过滤
        $('.filterable .btn-filter').click(function () {
            var $panel = $(this).parents('.filterable'),
                $filters = $panel.find('.filters input'),
                $tbody = $panel.find('.table tbody');
            if($filters.prop('disabled')==true){
                $filters.each(function() {
                    if ($(this).prop('disabled') == true && $(this).attr('placeholder') != '设置') {
                        $(this).prop('disabled', false);
                    }
                });
                $filters.first().focus();
            }
            else{
                    $filters.val('').prop('disabled', true);
                    $tbody.find('.no-result').remove();
                    $tbody.find('tr').show();
            }
        });
        $('.filterable .filters input').keyup(function (e) {
            /* Ignore tab key */
            var code = e.keyCode || e.which;
            if (code == '9') return;
            /* Useful DOM data and selectors */
            var $input = $(this),
                inputContent = $input.val().toLowerCase(),
                $panel = $input.parents('.filterable'),
                column = $panel.find('.filters th').index($input.parents('th')),
                $table = $panel.find('.table'),
                $rows = $table.find('tbody tr');
            /* Dirtiest filter function ever ;) */
            var $filteredRows = $rows.filter(function () {
                var value = $(this).find('td').eq(column).text().toLowerCase();
                return value.indexOf(inputContent) === -1;
            });
            /* Clean previous no-result if exist */
            $table.find('tbody .no-result').remove();
            /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
            $rows.show();
            $filteredRows.hide();
            /* Prepend no-result row if all rows are filtered */
            if ($filteredRows.length === $rows.length) {
                $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="' + $table.find('.filters th').length + '">No result found</td></tr>'));
            }
        });
       
        // 确认删除Modal，删除之后显示提示框以及撤销按钮
        $('#confirm-delete').on('click', '.btn-delete', function (e) {
            var $modalDiv = $(e.delegateTarget);
            var id = $(this).data('recordId');
            // $.ajax({url: '/api/record/' + id, type: 'DELETE'})
            // $.post('/api/record/' + id).then()
            $modalDiv.addClass('loading');
            setTimeout(function () {
                $modalDiv.modal('hide').removeClass('loading');
                $('.alert-normal-success').show();
                $('.recover').css("visibility", "visible");
            }, 500);
        });
        
        $(document).on('click', '.close', function () {
            $(this).parent().hide();
        });

    }
});
