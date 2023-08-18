function onLoad(){


    //设置行动状态
    var actionStateValue = ReactAPI.getComponentAPI("SystemCode").APIs("emcAction.actionState").getValue().value;
    if(actionStateValue === "SESECD_actionState/001"){

    }else if(actionStateValue === "SESECD_actionState/002"){
        ReactAPI.getComponentAPI("SystemCode").APIs("emcAction.actionState").removeOption("SESECD_actionState/001")
    }else if(actionStateValue === "SESECD_actionState/003"){
        ReactAPI.getComponentAPI("SystemCode").APIs("emcAction.actionState").setReadonly(true);
    }else if(actionStateValue === "SESECD_actionState/004"){
        ReactAPI.getComponentAPI("SystemCode").APIs("emcAction.actionState").setReadonly(true);
    }

}