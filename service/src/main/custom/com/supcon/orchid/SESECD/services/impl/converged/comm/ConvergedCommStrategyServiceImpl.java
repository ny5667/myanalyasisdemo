package com.supcon.orchid.SESECD.services.impl.converged.comm;

import com.supcon.orchid.SESECD.model.dto.convergedcomm.SignalConfigDTO;
import com.supcon.orchid.SESECD.model.dto.common.*;
import com.supcon.orchid.SESECD.model.vo.common.SourceTerminalVO;
import com.supcon.orchid.SESECD.entities.SESECDSignalConfig;
import com.supcon.orchid.SESECD.services.converged.comm.ConvergedCommOperatorService;
import com.supcon.orchid.SESECD.services.converged.comm.ConvergedCommStrategyService;
import com.supcon.orchid.services.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Create by lwten on 2022/11/9 下午8:48
 */
@Service("Custom_ConvergedCommStrategyServiceImpl")
@Slf4j
public class ConvergedCommStrategyServiceImpl extends BaseServiceImpl<SESECDSignalConfig> implements ConvergedCommStrategyService, ApplicationContextAware {

    private final Map<String, ConvergedCommOperatorService> providers = new HashMap<>();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ConvergedCommOperatorService> services = applicationContext.getBeansOfType(ConvergedCommOperatorService.class);
        if (!CollectionUtils.isEmpty(services)) {
            services.forEach((k, v) -> providers.put(v.getProvider(), v));
        }
    }

    @Override
    public void callPhone(Long id,String terminalNo, List<String> destCaller) {
        if (CollectionUtils.isEmpty(destCaller)){
            return;
        }
        SignalConfigDTO  providerConfig = getProviderConfig(id);
        if (Objects.isNull(providerConfig)) {
            return;
        }
        ConvergedCommOperatorService operatorService = providers.get(providerConfig.getSignalProvider());
        if (Objects.isNull(operatorService)){
            return;
        }
        CommCallPhoneDTO commCallPhone = new CommCallPhoneDTO();
        commCallPhone.setIp(providerConfig.getIp());
        commCallPhone.setPort(providerConfig.getPort());
        commCallPhone.setDestCaller(destCaller);
        if (StringUtils.isEmpty(terminalNo)){
            commCallPhone.setSourceCaller(providerConfig.getSourceCaller());
        }else{
            commCallPhone.setSourceCaller(terminalNo);
        }
        operatorService.callPhone(commCallPhone);
    }


    @Override
    public List<BroadcastGroupDTO> listBroadcastGroup(Long id) {
        SignalConfigDTO  providerConfig = getProviderConfig(id);
        if (Objects.isNull(providerConfig)) {
            return Collections.emptyList();
        }
        ConvergedCommOperatorService operatorService = providers.get(providerConfig.getSignalProvider());
        if (Objects.isNull(operatorService)){
            return Collections.emptyList();
        }
        CommReqDTO req = new CommReqDTO();
        req.setIp(providerConfig.getIp());
        req.setPort(providerConfig.getPort());
        return operatorService.listBroadcastGroup(req);
    }

    @Override
    public void sendBroadcast(Long id, BroadcastDTO broadcast) {
        SignalConfigDTO  providerConfig = getProviderConfig(id);
        if (Objects.isNull(providerConfig)) {
            return ;
        }
        ConvergedCommOperatorService operatorService = providers.get(providerConfig.getSignalProvider());
        if (Objects.isNull(operatorService)){
            return ;
        }
        broadcast.setIp(providerConfig.getIp());
        broadcast.setPort(providerConfig.getPort());
        operatorService.operatorBroadcast(broadcast);
    }

    @Override
    public List<SourceTerminalVO> listTerminal() {
        String sql =" select ID,TERMINAL_NAME,TERMINAL_NO from ECD_SOURCE_TERMINALS where valid =1 and cid=" + getCurrentCompanyId();
        log.info("{}",sql);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(SourceTerminalVO.class));
    }

    @Override
    public void notifyMsg(Long id, MsgNotifyDTO msgNotify) {
        SignalConfigDTO  providerConfig = getProviderConfig(id);
        if (Objects.isNull(providerConfig)) {
            return ;
        }
        ConvergedCommOperatorService operatorService = providers.get(providerConfig.getSignalProvider());
        if (Objects.isNull(operatorService)){
            return ;
        }
        msgNotify.setIp(providerConfig.getIp());
        msgNotify.setPort(providerConfig.getPort());
        operatorService.notifyMsg(msgNotify);
    }

    private SignalConfigDTO getProviderConfig() {
        String sql = "select ip,port,SOURCE_TERMINAL as sourceCaller, SIGNAL_PROVIDER as signalProvider from SES_ECD_SIGNAL_CONFIGS where valid =1 and ENABLE=1 and SIGNAL_TYPE='SESECD_signalType/001'";
        log.info("{}", sql);
        List<SignalConfigDTO> providers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SignalConfigDTO.class));
        if (CollectionUtils.isEmpty(providers)) {
            return null;
        } else {
            return providers.get(0);
        }
    }

    private SignalConfigDTO getProviderConfig(Long id) {
        if (Objects.isNull(id)){
            return getProviderConfig();
        }
        String sql = "select ip,port,SOURCE_TERMINAL as sourceCaller, SIGNAL_PROVIDER as signalProvider from SES_ECD_SIGNAL_CONFIGS where valid =1 and id=" + id;
        log.info("{}", sql);
        List<SignalConfigDTO> providers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SignalConfigDTO.class));
        if (CollectionUtils.isEmpty(providers)) {
            return null;
        } else {
            return providers.get(0);
        }
    }



}
