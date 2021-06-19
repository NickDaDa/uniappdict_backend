# uniappdict_backend
uniapp dictionary translation backend

uniapp 前后端字典翻译功能，后端服务部分。
使用aop拦截指定类型的返回值，并对返回数据中加了特殊注解的数据进行翻译，翻译后新增_dictTxt对应文本信息，新增_dictGroup对应整个字典的selection -- option。
