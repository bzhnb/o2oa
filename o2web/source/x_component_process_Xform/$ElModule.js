o2.xDesktop.requireApp("process.Xform", "$Module", null, false);
/** @classdesc $ElModule ElementUI组件类，此类为所有ElementUI组件的父类。
 * @class
 * @o2category FormComponents
 * @hideconstructor
 * */
o2.xApplication.process.Xform.$ElModule = MWF.APP$ElModule =  new Class(
    /** @lends o2.xApplication.process.Xform.$ElModule# */
    {
    Implements: [Events],
    Extends: MWF.APP$Module,
    options: {
        /**
         * 组件加载前触发。当前组件的queryLoad事件还没有在form里注册，通过this.form.get("fieldId")不能获取到当前组件，需要用this.target获取当前组件。
         * @event MWF.xApplication.process.Xform.$Module#queryLoad
         * @see {@link https://www.yuque.com/o2oa/ixsnyt/hm5uft#i0zTS|组件事件说明}
         */
        /**
         * 组件加载时触发.
         * @event MWF.xApplication.process.Xform.$Module#load
         * @see {@link https://www.yuque.com/o2oa/ixsnyt/hm5uft#i0zTS|组件事件说明}
         */
        /**
         * 组件加载后触发.
         * @event MWF.xApplication.process.Xform.$Module#postLoad
         * @see {@link https://www.yuque.com/o2oa/ixsnyt/hm5uft#i0zTS|组件事件说明}
         */
        "moduleEvents": ["load", "queryLoad", "postLoad"],
        "elEvents": ["focus", "blur", "change", "input", "clear"]
    },
    /**
     * @summary 组件的配置信息，同时也是Vue组件的data。
     * @member MWF.xApplication.process.Xform.Elinput#json {JsonObject}
     * @example
     *  //可以在脚本中获取此对象，下面两行代码是等价的，它们获取的是同一个对象
     * var json = this.form.get("elinput").json;       //获取组件的json对象
     * var json = this.form.get("elinput").vm.$data;   //获取Vue组件的data数据，
     *
     * //通过json对象操作Element组件
     * json.size = "mini";      //改变输入框大小
     * json.disabled = true;     //设置输入框为禁用
     */
    load: function(){
        this._loadModuleEvents();
        if (this.fireEvent("queryLoad")){
            this._queryLoaded();
            this._loadUserInterface();
        }
    },

    _loadUserInterface: function(){
        this.node.appendHTML(this._createElementHtml(), "before");
        var input = this.node.getPrevious();

        this.node.destroy();
        this.node = input;
        this.node.set({
            "id": this.json.id,
            "MWFType": this.json.type
        });
        this.node.addClass("o2_vue");
        this._createVueApp();
    },
    _createVueApp: function(){
        if (!this.vm) this._loadVue(this._mountVueApp.bind(this));
    },

    _loadVue: function(callback){
        if (!window.Vue){
            var vue = (o2.session.isDebugger) ? "vue_develop" : "vue";
            o2.loadAll({"css": "../o2_lib/vue/element/index.css", "js": [vue, "elementui"]}, { "sequence": true }, callback);
        }else{
            if (callback) callback();
        }
    },
    _mountVueApp: function(){
        if (!this.vueApp) this.vueApp = this._createVueExtend();

        /**
         * @summary Vue对象实例
         * @see https://vuejs.org/
         * @member {VueInstance}
         */
        this.vm = new Vue(this.vueApp);
        this.vm.$mount(this.node);
    },

    _createVueExtend: function(){
        var _self = this;
        var app = {
            data: this._createVueData(),
            mounted: function(){
                _self._afterMounted(this.$el);
            }
        };
        app.methods = this._createVueMethods(app);
        this.appendVueExtend(app);
        return app;
    },
    appendVueMethods: function(methods){},
    appendVueExtend: function(app){},
    _createVueMethods: function(){
        var methods = {};
        if (this.json.vueMethods && this.json.vueMethods.code){
            methods = this.form.Macro.exec(this.json.vueMethods.code, this);
        }
        this.appendVueMethods(methods);
        return methods || {};
    },
    _createVueData: function(){
        if (this.json.vueData && this.json.vueData.code){
            var d = this.form.Macro.exec(this.json.vueData.code, this);
            this.json = Object.merge(d, this.json);
        }
        this._appendVueData();
        return this.json;
    },

    _afterMounted: function(el){
        this.node = el;
        this.node.set({
            "id": this.json.id,
            "MWFType": this.json.type
        });
        this._loadVueCss();
        this._loadDomEvents();
        this._afterLoaded();
        this.fireEvent("postLoad");
        this.fireEvent("load");
    },
    _loadVueCss: function(){
        if (this.styleNode){
            this.node.removeClass(this.styleNode.get("id"));
        }
        if (this.json.vueCss && this.json.vueCss.code){
            this.styleNode = this.node.loadCssText(this.json.vueCss.code, {"notInject": true});
            this.styleNode.inject(this.node, "before");
        }
    },

    _appendVueData: function(){},
    _createElementHtml: function(){
        return "";
    }
}); 