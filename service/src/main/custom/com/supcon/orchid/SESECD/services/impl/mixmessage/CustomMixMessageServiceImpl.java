package com.supcon.orchid.SESECD.services.impl.mixmessage;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.supcon.orchid.SESEAB.DTO.MemberDTO;
import com.supcon.orchid.SESECD.model.dto.MixMessageReqDTO;
import com.supcon.orchid.SESECD.services.mixmessage.ICustomMixMessageService;
import com.supcon.orchid.SESECD.services.notify.DefaultNotifyImpl;
import com.supcon.orchid.SESECD.services.notify.MsgModelDTO;
import com.supcon.orchid.SESECD.services.notify.NotifyFacade;
import com.supcon.orchid.SESECD.services.notify.request.CommonEmailRequest;
import com.supcon.orchid.SESECD.services.notify.request.CommonSmsRequest;
import com.supcon.orchid.SESECD.services.notify.request.ThirdEmailReceiverRequest;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.services.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 融合通讯接口实现类
 * </p>
 *
 * @author lufengdong
 * @create 2023-04-28 10:58
 */
@Service
@Transactional
public class CustomMixMessageServiceImpl extends BaseServiceImpl implements ICustomMixMessageService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NotifyFacade notifyFacade;

    @Autowired
    private DefaultNotifyImpl defaultNotify;

    @Override
    public void sendMsg(MixMessageReqDTO requestDTO) {
        Integer teamType = requestDTO.getTeamType();
        List<Map<String, Object>> phoneAndEmails = this.getPhoneAndEmails(requestDTO.getReceivers(), teamType);
        if (CollectionUtils.isEmpty(phoneAndEmails)) {
            log.error("消息发送失败，未查询到人员信息");
            return;
        }
        List<Integer> messageTypes = requestDTO.getMessageTypes();
        if (messageTypes.contains(1)) {
            log.error("待发送短信人员数量：{}", phoneAndEmails.size());
            try {
                List<String> phoneNumbers = phoneAndEmails.stream()
                        .filter(phones -> !ObjectUtils.isEmpty(phones.get("phone")))
                        .map(phones -> phones.get("phone").toString()).collect(Collectors.toList());
                CommonSmsRequest smsRequest = CommonSmsRequest.builder().mobileList(phoneNumbers).content(requestDTO.getContent()).build();
                String s = notifyFacade.handleSMS(smsRequest);

            } catch (Exception var1) {
                log.error("发送短息失败：{0}", var1);
            }
        }
        if (messageTypes.contains(2)) {
            log.error("待发送邮件人员数量：{}", phoneAndEmails.size());
            try {
                List<ThirdEmailReceiverRequest> receiverList = phoneAndEmails.stream()
                        .filter(emails -> !ObjectUtils.isEmpty(emails.get("email")))
                        .map(emails -> getThirdEmailReceiver(emails)).collect(Collectors.toList());
                CommonEmailRequest emailRequest = CommonEmailRequest.builder().title("邮件内容").content(requestDTO.getContent()).toList(receiverList).build();
                notifyFacade.handleEmail(emailRequest);
            } catch (Exception var1) {
                log.error("发送邮件失败：{0}", var1);
            }
        }
        if (teamType != 2 && messageTypes.contains(3)) {
            try {
                //发送应用消息--->站内信 方式
                List<Long> receivers = phoneAndEmails.stream().filter(info -> !ObjectUtils.isEmpty(info.get("id")))
                        .map(info -> Long.valueOf(info.get("id").toString())).collect(Collectors.toList());
                //发送站内信
                sendMessageByInternal(receivers, requestDTO.getContent());
            } catch (Exception var1) {
                log.error("发送应用消息失败：{0}", var1);
            }
        }
    }

//-------------------------------------------公共功能------------------------------------------------------------------//

    /**
     * 获取人员的手机号信息和email信息
     *
     * @param personIdList 人员编号集合
     */
    private List<Map<String, Object>> getPhoneAndEmails(List<Long> personIdList, Integer teamType) {
        StringBuilder builder;
        List<Map<String, Object>> resultList = new ArrayList<>();
        // 防止数据量太多，分批查询
        List<List<Long>> batches = Lists.partition(personIdList, 50);
        for (List<Long> batch : batches) {
            if (teamType == 1) {
                builder = getFromEmergencyTeam(batch);
            } else {
                builder = getFromExpert(batch);
            }
            log.error("手机号、邮件信息查询SQL ：{}", builder.toString());
            List<Map<String, Object>> batchList = jdbcTemplate.queryForList(builder.toString());
            if (CollectionUtils.isNotEmpty(batchList)) resultList.addAll(batchList);
        }
        return resultList;
    }

    /**
     * 获取应急小组成员邮箱和手机号信息的sql
     *
     * @return
     */
    private StringBuilder getFromEmergencyTeam(List<Long> personIdList) {
        String ids = StringUtils.join(personIdList, ",");
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT op.ID AS id,op.NAME as name ,op.EMAIL as email ,op.PHONE as phone FROM ORG_PERSON op ")
                .append(" LEFT JOIN SES_EAB_SETIONMEMBERS ses ON op.ID =SES.PERSON_ID ")
                .append(" WHERE ses.VALID = 1 AND op.VALID =1 AND ses.id IN (").append(ids).append(")");
        return sql;
    }

    /**
     * 获取应急物资--》应急专家成员邮箱和手机号信息的sql
     *
     * @return
     */
    private StringBuilder getFromExpert(List<Long> personIdList) {
        String ids = StringUtils.join(personIdList, ",");
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT see.NAME as name ,see.PHONE as phone,see.EMAIL as email FROM SES_ER_EXPERTS see ")
                .append(" WHERE see.VALID = 1 AND see.id IN (").append(ids).append(")");
        return sql;
    }

    /**
     * 邮件发送实体
     *
     * @param receiver
     * @return
     */
    private ThirdEmailReceiverRequest getThirdEmailReceiver(Map<String, Object> receiver) {
        ThirdEmailReceiverRequest receiverRequest = new ThirdEmailReceiverRequest();
        receiverRequest.setReceiver((String) receiver.get("name"));
        receiverRequest.setReceiver((String) receiver.get("email"));
        return receiverRequest;
    }

    /**
     * 站内信发送
     *
     * @param idList
     * @param message
     */
    public void sendMessageByInternal(List<Long> idList, String message) {
        String ids = StringUtils.join(idList, ",");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT op.ID AS staffId,op.CODE AS staffCode ,op.PHONE AS mobile FROM ORG_PERSON op ")
                .append(" WHERE op.VALID = 1 AND ").append(" op.ID IN ( ").append(ids).append(" )");
        log.error("获取到成员信息SQL:{}", sql.toString());
        List<MemberDTO> members = jdbcTemplate.query(sql.toString(), new Object[]{}, new BeanPropertyRowMapper<>(MemberDTO.class));
        log.error("获取到成员信息members:{}", JSON.toJSONString(members));
        List<String> receiverStaffCode = members.stream().filter(member -> member.getStaffId() != null)  //成员不为空的小组
                .map(MemberDTO::getStaffCode).collect(Collectors.toList());  //获取成员编码
        if (org.springframework.util.CollectionUtils.isEmpty(receiverStaffCode)) {
            log.error("List<String> receiverStaffCode 应急通讯组为空， 请检查当前数据");
            return;
        }
        log.error("通讯组成员:" + JSON.toJSONString(receiverStaffCode));
        MsgModelDTO msgModelDTO = MsgModelDTO.builder().msgType(ConstDict.MSG_TYPE_VXECD_APPLICATION_MESSAGE).receivers(receiverStaffCode).param(getParam(message)).build();
        //手动指定平台推送
        defaultNotify.notify(msgModelDTO);
    }

    /**
     * 组装消息发送体
     *
     * @param message 消息内容
     * @return
     */
    public Map getParam(String message) {
        log.error("组装消息发送体....");
        Map<String, Object> modelParam = new HashedMap();
        modelParam.put("message", message);//消息内容
        return modelParam;
    }

}
