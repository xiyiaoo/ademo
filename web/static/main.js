/**
 * Created by xiyiaoo on 15-6-12.
 */
require.config({
    baseUrl: 'static',
    paths: {
        jquery: 'jquery/jquery',
        semantic: 'semantic/semantic'
    },
    shim: {
        jquery: {
            exports: 'jQuery'
        },
        semantic: ['jquery', 'css!semantic']
    }
});

require(['semantic', 'avalon/mmState'], function(){
    $('.ui.sidebar')
        .sidebar('attach events', '.launch.button')
    ;

    $('.ui.dropdown')
        .dropdown()
    ;

    /*$('.accordion')
        .accordion({
            selector: {
                trigger: '.title'
            }
        })
    ;*/

    var app = avalon.define({
        $id: 'app',
        menu: [
            {id: '1', name: 'Home', iconClass: 'home', url: '/user'},
            {id: '2', name: 'Topics', iconClass: 'block layout'},
            {id: '3', name: 'Friends', iconClass: 'smile'},
            {id: '4', name: 'History', iconClass: 'calendar'},
            {id: '5', name: 'Messages', iconClass: 'mail'},
            {id: '6', name: 'Discussions', iconClass: 'chat'},
            {id: '7', name: 'Achievements', iconClass: 'trophy'},
            {id: '8', name: 'Store', iconClass: 'shop'},
            {id: '9', name: 'Settings', iconClass: 'settings'}
        ],
        to: function(item){
            $('.ui.sidebar').sidebar('hide');
            app.active = item.id;
        },
        active: ''
    });

    avalon.scan();
    avalon.state('home', {
        url: '/',
        views: {
            container: {
                template: function(){
                    return '<span>I`m Home!</span>';
                }
            }
        }
    }).state('user', {
            url: '/user',
            views: {
                container: {
                    templateUrl: 'template/user/list',
                    controllerUrl: 'app/user/list.js'
                }
            }
        });
    avalon.state.config({
        onError: function() {
            console.log(arguments)
        },
        onLoad: function() {
            var url = mmState.currentState.url, i, len;
            if(app.active && url === '/'){
                app.active = '';
            } else if(!app.active && url !== '/'){
                len = app.menu.length;
                for(i = 0; i < len; i++) {
                    if(new RegExp(app.menu[i].url).test(url)){
                        app.active = app.menu[i].id;
                        return;
                    }
                }
            }
        }
    });
    avalon.history.start({
        html5Mode: false
    });
});