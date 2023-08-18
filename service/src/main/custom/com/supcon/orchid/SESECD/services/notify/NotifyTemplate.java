package com.supcon.orchid.SESECD.services.notify;

import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfig;
import com.supcon.orchid.SESECD.entities.SESECDParamOption;
import com.supcon.orchid.SESECD.services.paramconfig.CustomSESECDParamConfigService;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.foundation.services.StaffService;
import com.supcon.orchid.services.BaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 三方实现消息模板服务类
 */
@Service
public class NotifyTemplate extends BaseServiceImpl {

    @Autowired
    private CustomSESECDParamConfigService paramConfigService;

    @Autowired
    private StaffService staffService;
    /**
     * 通过主题编号获取内容模板的消息模板
     *
     * @param msgType 主题编号
     * @return 主题名称
     */
    public String getTemplateTextByMsgType(String msgType) {
        //获取消息模板配置下的所有消息模板
        SESECDEcdParamConfig msgConfig = paramConfigService.getConfigByConfigCode(ConstDict.MSG_TEMPLATE);
        return paramConfigService.getParameterOptions(msgConfig).get(msgType);
    }


    public List<String> getNotifyWay(String msgType){
        //获取消息模板配置下的所有消息模板
        SESECDEcdParamConfig msgConfig = paramConfigService.getConfigByConfigCode(ConstDict.MSG_TEMPLATE);
        List<SESECDParamOption> options = paramConfigService.getParameterOptionList(msgConfig);
        SESECDParamOption sesecdParamOption = options.stream().filter(option -> option.getOptionCode().equalsIgnoreCase(msgType)).findFirst().get();

        String remark = sesecdParamOption.getRemark();
        return Arrays.asList(remark.split(","));
    }

    /**
     * 解析内容模板
     *
     * @param input        内容模板
     * @param replacements 填充值
     * @return 解析后
     */
    public String parseTemplate(String input, Map<String, String> replacements) {
        if (StringUtils.isBlank(input)) {
            return "";
        }
        // 定义要替换的占位符的正则表达式
        Pattern pattern = Pattern.compile("\\$\\{([^{}]+)\\}");
        // 使用Matcher对象匹配所有符合要求的占位符
        Matcher matcher = pattern.matcher(input);
        // 循环替换所有匹配到的占位符
        while (matcher.find()) {
            String placeholder = matcher.group(0);
            String key = matcher.group(1);
            String value = replacements.get(key);
            if (value != null) {
                input = input.replace(placeholder, value);
            } else {
                input = input.replace(placeholder, " ");
            }
        }
        return input;
    }


    /**
     * 根据人员信息编码获取人员信息
     * @param staffCodes 员信息编码集合
     * @return 人员信息集合
     */
    public List<Staff> getStaffInfos(List<String> staffCodes){
        if(CollectionUtils.isEmpty(staffCodes)){
            return new ArrayList<>();
        }
        String staffs = StringUtils.join(staffCodes, ",");
        log.error("待发送人员编码：{}.", staffs);
        return staffService.findStaffByCodes(staffs);
    }
}
