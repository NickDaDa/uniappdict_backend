# uniappdict_backend
uniapp dictionary translation backend

uniapp 前后端字典翻译功能，后端服务部分。
使用aop拦截指定类型的返回值，并对返回数据中加了特殊注解的数据进行翻译，翻译后新增_dictTxt对应文本信息，新增_dictGroup对应整个字典的selection -- option。

数据样例：
{
    "message": "操作成功！",
    "result": [
        {
            "id": "402848817982113601798258257e0005",
            "reporter": null,
            "createName": "管理员",
            "updateName": null,
            "createBy": "admin",
            "updateDate": null,
            "updateBy": null,
            "createDate": "2021-05-19 09:58:05",
            "orderId": null,
            "devCode": null,
            "overhaulType": "3",
            "devClass": null,
            "faultModel": null,
            "faultClass": null,
            "confirmer": null,
            "frequency": "1",
            "faultUnit": null,
            "operator": "admin",
            "startDate": null,
            "orderStatus": "1",
            "receiveDate": null,
            "planDate": null,
            "appraiser": null,
            "finishDate": null,
            "orderClass": "0",
            "actualTime": null,
            "maintainer": null,
            "appraiseLevel": null,
            "predictTime": null,
            "confirmDate": null,
            "maintainPost": null,
            "maintainRecordTime": null,
            "maintainAppraiseTime": null
        }
    ],
    "timestamp": 1624072263144,
    "data": {
        "dict": {
            "orderStatus_dictGroup": [
                {
                    "code": "0",
                    "text": "已创建"
                },
                {
                    "code": "1",
                    "text": "已接单"
                },
                {
                    "code": "2",
                    "text": "维修中"
                },
                {
                    "code": "3",
                    "text": "维修完成"
                },
                {
                    "code": "4",
                    "text": "暂缓维修"
                },
                {
                    "code": "5",
                    "text": "转移"
                },
                {
                    "code": "6",
                    "text": "待确认"
                },
                {
                    "code": "7",
                    "text": "待记录，维修记录"
                },
                {
                    "code": "8",
                    "text": "待评价"
                },
                {
                    "code": "9",
                    "text": "完成"
                }
            ]
        },
        "list": [
            {
                "createBy": "admin",
                "overhaulType": "3",
                "orderStatus_dictTxt": "已接单",
                "orderStatus": "1",
                "id": "402848817982113601798258257e0005",
                "orderClass": "0",
                "createName": "管理员",
                "operator": "admin",
                "createDate": 1621389485000,
                "frequency": "1"
            }
        ]
    },
    "code": 200,
    "onlTable": null,
    "success": true
}
