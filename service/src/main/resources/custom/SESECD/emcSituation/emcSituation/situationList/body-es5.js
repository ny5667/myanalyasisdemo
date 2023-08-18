//查看应急态势
function situationView() {
      var _gridId = "SESECD_1.0.0_emcSituation_situationList_emcSituation_sdg";
      var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
      var rows = grid.getSelecteds();
      if (rows.length != 1) {
            ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
            return;
      }
      var code = 'SESECD_1.0.0_emcSituation_situationList_self';
      var __pc__ = ReactAPI.getPowerCode(code);
      var url = "http://" + window.location.host + "/msService/SESECD/emcSituation/emcSituation/situationView?__pc__=" + __pc__[code] + "&id=" + rows[0].id + "&viewCode=SESECD_1.0.0_emcSituation_situationList&entityCode=SESECD_1.0.0_emcSituation&iscrosscompany=false&openType=frame&viewType=view";
      window.open(url, "_blank");
}

//修改应急态势
function situationUpdate() {
      debugger;
      var _gridId = "SESECD_1.0.0_emcSituation_situationList_emcSituation_sdg";
      var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
      var rows = grid.getSelecteds();
      if (rows.length != 1) {
            ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
            return;
      }
      var aa = rows[0].situationType.id;
      if (aa === 'SESECD_situation_type/001') {
            ReactAPI.showMessage("w", '态势已发布无法修改');
            return;
      }

      var code = 'SESECD_1.0.0_emcSituation_situationList_self';
      var __pc__ = ReactAPI.getPowerCode(code);
      var url = "http://" + window.location.host + "/msService/SESECD/emcSituation/emcSituation/situationEdit?__pc__=" + __pc__[code] + "&id=" + rows[0].id + "&viewCode=SESECD_1.0.0_emcSituation_situationList&entityCode=SESECD_1.0.0_emcSituation&iscrosscompany=false&openType=frame&viewType=edit";
      window.open(url, "_blank");
}