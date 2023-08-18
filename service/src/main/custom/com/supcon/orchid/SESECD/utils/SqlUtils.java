package com.supcon.orchid.SESECD.utils;


import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.foundation.services.CompanyService;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "SESECD_SqlUtils")
public class SqlUtils extends BaseServiceImpl {

    @Autowired
    private CompanyService companyService;

    /**
     * @Description 根据CID返回要拼接的sql  有别名  用于下级查询
     * @Param tableName  表的别名
     */
    public String getSqlPartByCID(String tableName) {


        Long currentCompanyId = getCurrentCompanyId();
        StringBuilder cids = new StringBuilder(currentCompanyId.toString());
        //查询所有子公司的cid
        companyService.getAllChildren(currentCompanyId).forEach((company) -> {
            cids.append(",").append(company.getId());
        });
        return " AND " + tableName + ".CID in (" + cids + ")";
    }

    /**
     * @Description 根据CID返回要拼接的sql  无别名  用于下级查询
     */
    public String getSqlPartByCID() {
        Long currentCompanyId = getCurrentCompanyId();
        StringBuilder cids = new StringBuilder(currentCompanyId.toString());
        //查询所有子公司的cid
        companyService.getAllChildren(currentCompanyId).forEach((company) -> {
            cids.append(",").append(company.getId());
        });
        return " AND " + "CID in (" + cids + ")";
    }

    /**
     * @Description 根据CID返回要拼接的sql  无别名  用于下级查询
     */
    public String getSqlPartByCID(Long cid) {
        StringBuilder cids = new StringBuilder(cid.toString());
        //查询所有子公司的cid
        companyService.getAllChildren(cid).forEach((company) -> {
            cids.append(",").append(company.getId());
        });
        return " AND " + "CID in (" + cids + ")";
    }

    /**
     * @Description 根据CID返回要拼接的sql  无别名  用于参照查询
     */
    public String getHigherSqlPartByCID() {
        Long currentCompanyId = getCurrentCompanyId();
        Company currentCompany = this.companyService.get(currentCompanyId);
        String layRec = currentCompany.getLayRec();
        StringBuilder cids = new StringBuilder();
        if (!StringUtils.isEmpty(layRec)) {
            String[] higherCids = layRec.split("-");
            for (int i = 0; i < higherCids.length; ++i) {
                if (i > 0) {
                    cids.append(",");
                }
                cids.append(higherCids[i]);
            }
        } else {
            cids.append(currentCompanyId);
        }
        return " AND " + "CID in (" + cids + ")";
    }

    /**
     * @Description 根据CID返回要拼接的sql  有别名  用于参照查询
     */
    public String getHigherSqlPartByCID(String tableName) {
        Long currentCompanyId = getCurrentCompanyId();
        Company currentCompany = this.companyService.get(currentCompanyId);
        String layRec = currentCompany.getLayRec();
        StringBuilder cids = new StringBuilder();
        if (!StringUtils.isEmpty(layRec)) {
            String[] higherCids = layRec.split("-");
            for (int i = 0; i < higherCids.length; ++i) {
                if (i > 0) {
                    cids.append(",");
                }
                cids.append(higherCids[i]);
            }
        } else {
            cids.append(currentCompanyId);
        }
        return " AND " + tableName + ".CID in (" + cids + ")";
    }

}

