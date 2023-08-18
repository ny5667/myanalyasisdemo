package com.supcon.orchid.SESECD.services.converged.comm;

import com.supcon.orchid.SESECD.model.dto.common.BroadcastDTO;
import com.supcon.orchid.SESECD.model.dto.common.BroadcastGroupDTO;
import com.supcon.orchid.SESECD.model.dto.common.MsgNotifyDTO;
import com.supcon.orchid.SESECD.model.vo.common.SourceTerminalVO;

import java.util.List;

/**
 * 融合通信策略服务
 * @Create by lwten on 2022/11/9 下午8:44
 */
public interface ConvergedCommStrategyService {

    /**
     * 电话呼叫
     * @param id
     * @Param terminalNo
     * @param destCaller
     */
    void callPhone(Long id,String terminalNo,List<String> destCaller);

    /**
     * 获取广播组
     * @return
     * @param id
     */
    List<BroadcastGroupDTO> listBroadcastGroup(Long id);

    /**
     * 发送广播
     * @param id
     * @param broadcast
     */
    void sendBroadcast(Long id,BroadcastDTO broadcast);


    /**
     * 获取
     * @return
     */
    List<SourceTerminalVO> listTerminal();

    /**
     * 消息通知
     * @param id
     * @param msgNotify
     */
    void notifyMsg(Long id, MsgNotifyDTO msgNotify);
}
