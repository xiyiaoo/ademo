/**
 * Created by xiyiaoo on 15-6-29.
 */
define(['avalon'], function(avalon) {

    return avalon.controller(function($ctrl) {
        // 视图渲染后，意思是avalon.scan完成
        $ctrl.$onRendered = function() {
            $('.accordion')
                .accordion({
                    selector: {
                        trigger: '.title'
                    }
                })
            ;
        };
        // 进入视图
        $ctrl.$onEnter = function() {
            console.log('$onEnter');
        };
        // 对应的视图销毁前
        $ctrl.$onBeforeUnload = function() {
            console.log('$onBeforeUnload');
        };
    })
});